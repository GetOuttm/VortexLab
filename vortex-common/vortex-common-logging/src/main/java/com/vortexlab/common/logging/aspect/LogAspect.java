package com.vortexlab.common.logging.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Around("execution(* com.vortexlab..controller..*(..))")
    public Object around(ProceedingJoinPoint point) {
        long start = System.currentTimeMillis();

        String method = point.getSignature().toShortString();

        log.info("请求开始：{}", method);

        Object result = point.proceed();

        long time = System.currentTimeMillis() - start;

        log.info("请求结束：{}，耗时：{}ms",method,time);

        return result;
    }
}
