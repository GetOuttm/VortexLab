package com.vortexlab.common.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configurable
public class RedisConfig {

    /**
     * RedisTemplate统一配置
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        // redis连接工厂
        template.setConnectionFactory(factory);

        // key序列化
        StringRedisSerializer keySerializer = new StringRedisSerializer();

        // value序列化
        Jackson2JsonRedisSerializer<Object> valueSerializer = new Jackson2JsonRedisSerializer<>(createObjectMapper(), Object.class);

        // key
        template.setKeySerializer(keySerializer);
        template.setHashKeySerializer(keySerializer);

        // value
        template.setValueSerializer(valueSerializer);
        template.setHashValueSerializer(valueSerializer);

        template.afterPropertiesSet();
        return template;
    }

    /**
     * Jackson ObjectMapper
     */
    private ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // java8 时间
        objectMapper.registerModule(new JavaTimeModule());

        /*
         * Redis反序列化时需要知道具体Java类型。
         *
         * 例如：
         * User
         * Order
         * Product
         * 都可以正确恢复。
         */
        BasicPolymorphicTypeValidator validator = BasicPolymorphicTypeValidator
                .builder()
                .allowIfSubType("com.vortexlab.")
                .build();

        objectMapper.activateDefaultTyping(
                validator,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.WRAPPER_OBJECT
        );

        objectMapper.setVisibility(
                PropertyAccessor.ALL,
                JsonAutoDetect.Visibility.ANY
        );

        return objectMapper;
    }
}
