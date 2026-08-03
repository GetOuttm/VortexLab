package com.vortexlab.realtime.config;

import org.apache.kafka.common.serialization.Serdes;

import org.apache.kafka.streams.StreamsConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaStreamConfig {

    @Bean
    public Properties streamsProperties() {
        Properties properties = new Properties();

        /**
         * 应用名称
         */
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "vortex-order-stat");

        /**
         * Kafka地址
         */
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        /**
         * Key序列化
         */
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        /**
         * Value序列化
         */
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        return properties;

    }
}
