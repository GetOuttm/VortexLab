package com.vortexlab.message.constant;

public interface KafkaTopicConstant {

    /**
     * 订单创建
     */
    String ORDER_CREATED = "order-created";

    /**
     * 库存扣减
     */
    String STOCK_DEDUCT = "stock-deduct";

    String ORDER_CREATED_RETRY = "order-created-retry";

    String ORDER_CREATED_DLQ = "order-created-dlq";

}
