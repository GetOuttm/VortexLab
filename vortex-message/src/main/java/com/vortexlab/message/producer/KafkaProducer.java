package com.vortexlab.message.producer;

import com.vortexlab.message.base.BaseEvent;
import com.vortexlab.message.constant.KafkaTopicConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void send(String topic, String key, Object message) {
        kafkaTemplate.send(topic, key, message);
    }

    public void send(String topic, BaseEvent<?> event) {
        kafkaTemplate.send(topic, event.getMessageId(), event);
    }

    public void sendOrderSend(Object event) {
        send(KafkaTopicConstant.ORDER_CREATED, "order", event);
    }
}
