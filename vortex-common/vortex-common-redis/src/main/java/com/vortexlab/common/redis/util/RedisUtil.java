package com.vortexlab.common.redis.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置缓存
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue()
                .set(key, value);
    }

    /**
     * 设置过期时间
     */
    public void set(String key, Object value, Long timeout, TimeUnit unit) {
        redisTemplate.opsForValue()
                .set(key, value, timeout, unit);
    }

    /**
     * 获取
     */
    public void get(String key) {
        redisTemplate.opsForValue()
                .get(key);
    }

    /**
     * 删除
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }
}
