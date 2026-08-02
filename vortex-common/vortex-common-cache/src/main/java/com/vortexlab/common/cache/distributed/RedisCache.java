package com.vortexlab.common.cache.distributed;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class RedisCache {

    public final RedisTemplate<String, Object> redisTemplate;

    /**
     * 写Redis
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 写Redis并设置过期时间
     */
    public void set(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MINUTES);
    }

    /**
     * 查询Redis
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除Redis
     */
    public void remove(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 判断Key是否存在
     */
    public boolean exists(String key) {
        Boolean result = redisTemplate.hasKey(key);
        return Boolean.TRUE.equals(result);
    }

    /**
     * 设置过期时间
     */
    public boolean expire(String key, long timeout) {
        Boolean result = redisTemplate.expire(
                key,
                timeout,
                TimeUnit.SECONDS
        );
        return Boolean.TRUE.equals(result);
    }

    /**
     * 获取剩余过期时间
     */
    public long getExpire(String key) {
        Long result = redisTemplate.getExpire(
                key,
                TimeUnit.SECONDS
        );

        return result == null ? -1 : result;
    }
}
