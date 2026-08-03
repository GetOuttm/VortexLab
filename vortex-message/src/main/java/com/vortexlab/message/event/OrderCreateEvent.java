package com.vortexlab.message.event;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class OrderCreateEvent implements Serializable {

    /**
     * 消息id
     */
    private String messageId;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
