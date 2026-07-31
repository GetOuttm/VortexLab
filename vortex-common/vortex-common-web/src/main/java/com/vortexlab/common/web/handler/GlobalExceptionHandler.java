package com.vortexlab.common.web.handler;

import com.vortexlab.common.core.exception.BusinessException;
import com.vortexlab.common.core.response.Result;
import com.vortexlab.common.core.response.ResultCode;
import jakarta.validation.ConstraintDeclarationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public Result<Void> businessException(BusinessException e) {
        log.warn("业务异常 {}", e.getMessage());

        return Result.failed(e.getCode(), e.getMessage());
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleValid(MethodArgumentNotValidException e) {
        String message = e.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();
        log.warn("参数校验异常 {}", message);


        return Result.failed(ResultCode.PARAM_ERROR.getCode(), message);
    }

    /**
     * 单参数校验
     */
    @ExceptionHandler(ConstraintDeclarationException.class)
    public Result<Void> handleConstraint(ConstraintDeclarationException e) {
        log.warn("单参数校验异常 {}", e.getMessage());

        return Result.failed(ResultCode.PARAM_ERROR.getCode(), e.getMessage());
    }

    /**
     * 系统异常-未知异常
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> exception(Exception e) {
        log.warn("系统异常 {}", e.getMessage());

        return Result.failed(500, "系统异常");
    }
}
