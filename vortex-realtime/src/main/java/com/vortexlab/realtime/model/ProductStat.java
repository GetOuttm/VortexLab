package com.vortexlab.realtime.model;

import lombok.Data;

@Data
public class ProductStat {

    private Long productId;

    /**
     * 总销量
     */
    private Long total;
}
