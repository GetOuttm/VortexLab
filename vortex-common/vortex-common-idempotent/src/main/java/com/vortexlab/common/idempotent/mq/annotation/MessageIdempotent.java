package com.vortexlab.common.idempotent.mq.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageIdempotent {

    /**
     * 消息id字段
     */
    String key() default "messageId";

    /**
     * 过期时间
     */
    long expire() default 86400;
}
