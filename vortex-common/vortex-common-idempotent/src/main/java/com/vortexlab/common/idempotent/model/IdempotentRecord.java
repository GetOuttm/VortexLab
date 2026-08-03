package com.vortexlab.common.idempotent.model;

import com.vortexlab.common.idempotent.enums.IdempotentStatus;
import lombok.Data;

import java.io.Serializable;

@Data
public class IdempotentRecord implements Serializable {

    /**
     * 状态
     */
    private IdempotentStatus status;

    /**
     * 返回结果
     */
    private Object result;

    /**
     * 创建时间
     */
    private Long timestamp;
}
