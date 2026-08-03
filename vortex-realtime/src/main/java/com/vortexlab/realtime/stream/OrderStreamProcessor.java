package com.vortexlab.realtime.stream;

import com.vortexlab.realtime.constant.StreamTopicConstant;


import org.apache.kafka.common.serialization.Serdes;

import org.apache.kafka.streams.StreamsBuilder;

import org.apache.kafka.streams.kstream.*;

import org.springframework.stereotype.Component;


@Component
public class OrderStreamProcessor {

    public OrderStreamProcessor(
            StreamsBuilder builder
    ){
        KStream<String,String> stream =
                builder.stream(
                        StreamTopicConstant.ORDER_TOPIC,
                        Consumed.with(
                                Serdes.String(),
                                Serdes.String()
                        )
                );

        stream
                .mapValues(
                        value -> {
                            // JSON解析
                            return value;
                        }
                )
                .groupByKey()
                .count()
                .toStream()
                .to(
                        StreamTopicConstant.PRODUCT_STAT_TOPIC
                );
    }
}
