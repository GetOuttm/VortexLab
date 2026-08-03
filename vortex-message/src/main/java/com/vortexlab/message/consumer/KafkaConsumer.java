package com.vortexlab.message.consumer;

import com.vortexlab.message.constant.KafkaTopicConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListeners;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {


    @KafkaListener(topics = KafkaTopicConstant.ORDER_CREATED,groupId = "stock-service")
    public void consume(Object message) {
        log.info("收到订单时间：{}",message);
    }
}
