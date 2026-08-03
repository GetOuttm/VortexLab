package com.vortexlab.message.center.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageRecord {

    private Long id;

    private String messageId;

    private String topic;

    private String messageBody;

    private Integer status;

    private Integer retryCount;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}