package com.vortexlab.common.idempotent.annotation;

import com.vortexlab.common.idempotent.enums.IdempotentType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {

    /**
     * 幂等模式
     */
    IdempotentType type() default IdempotentType.PARAM;

    /**
     * Key前缀
     */
    String prefix() default "idempotent:";

    /**
     * 自定义Key
     */
    String key() default "";

    /**
     * Header名称
     */
    String headerName() default "Idempotent-Token";

    /**
     * 过期时间
     */
    long expire() default 60;

    /**
     * 提示信息
     */
    String message() default "请求处理中，请勿重复提交";
}
