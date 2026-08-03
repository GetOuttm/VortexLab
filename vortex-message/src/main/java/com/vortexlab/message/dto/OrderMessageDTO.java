package com.vortexlab.message.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderMessageDTO implements Serializable {

    private Long orderId;

    private Long userId;

    private Long productId;

    private Integer quantity;
}
