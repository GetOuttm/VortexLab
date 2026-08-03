package com.vortexlab.message.base;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BaseEvent<T> implements Serializable {

    /**
     * 全局消息ID
     */
    private String messageId;

    /**
     * 事件类型
     */
    private String eventType;

    /**
     * 来源服务
     */
    private String source;

    /**
     * 链路ID
     */
    private String traceId;

    /**
     * 创建时间
     */
    private LocalDateTime timestamp;

    /**
     * 业务数据
     */
    private T data;

}