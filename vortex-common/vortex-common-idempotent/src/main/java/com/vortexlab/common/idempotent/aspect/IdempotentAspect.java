package com.vortexlab.common.idempotent.aspect;

import com.vortexlab.common.idempotent.annotation.Idempotent;
import com.vortexlab.common.idempotent.enums.IdempotentStatus;
import com.vortexlab.common.idempotent.exception.IdempotentException;
import com.vortexlab.common.idempotent.key.IdempotentKeyBuilder;
import com.vortexlab.common.idempotent.model.IdempotentRecord;
import com.vortexlab.common.idempotent.service.IdempotentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class IdempotentAspect {

    private final IdempotentService idempotentService;

    private final IdempotentKeyBuilder keyBuilder;

    private final HttpServletRequest request;

    @Around("@annotation(idempotent)")
    public Object around(ProceedingJoinPoint joinPoint, Idempotent idempotent) throws Throwable {
        // 生成幂等Key
        String key = keyBuilder.builder(idempotent, request, joinPoint.getArgs());

        boolean first = idempotentService.tryAcquire(key, idempotent.expire());

        // 不是第一次
        if (!first) {
            IdempotentRecord record = idempotentService.get(key);
            if (record != null && record.getStatus() == IdempotentStatus.SUCCESS) {
                return record.getResult();
            }

            throw new IdempotentException("请求正在处理中，请稍等~");
        }

        try {
            // 执行业务
            Object result = joinPoint.proceed();
            // 保存结果
            idempotentService.success(key, result, idempotent.expire());
            return result;
        } catch (Throwable e) {
            /*
             * 失败 删除Key
             */
            idempotentService.delete(key);
            throw e;
        }
    }
}