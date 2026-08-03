package com.vortexlab.common.idempotent.enums;

public enum IdempotentType {

    /**
     * 根据请求参数生成Key
     */
    PARAM,

    /**
     * 根据请求Header生成Key
     */
    HEADER,

    /**
     * Token模式
     */
    TOKEN,

    /**
     * 自定义Key
     */
    CUSTOM
}
