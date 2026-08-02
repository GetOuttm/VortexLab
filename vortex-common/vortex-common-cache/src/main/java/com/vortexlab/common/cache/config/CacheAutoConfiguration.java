package com.vortexlab.common.cache.config;

import com.vortexlab.common.cache.distributed.RedisCache;
import com.vortexlab.common.cache.local.CaffeineCache;
import com.vortexlab.common.cache.local.GuavaCache;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

@AutoConfiguration
public class CacheAutoConfiguration {

    @Bean
    public CaffeineCache caffeineCache() {
        return new CaffeineCache();
    }

    @Bean
    public GuavaCache guavaCache() {
        return new GuavaCache();
    }

    @Bean
    public RedisCache redisCache(RedisTemplate<String, Object> template) {
        return new RedisCache(template);
    }
}
