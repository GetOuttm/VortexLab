package com.vortexlab.common.redis.config;

import com.vortexlab.common.redis.util.RedisUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@AutoConfiguration
public class RedisAutoConfiguration {

    /**
     * Redis 序列化配置
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisTemplate<String, Object> template) {
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        return template;
    }

    @Bean
    public RedisUtil redisUtil(RedisTemplate<String, Object> template) {
        return new RedisUtil(template);
    }
}
