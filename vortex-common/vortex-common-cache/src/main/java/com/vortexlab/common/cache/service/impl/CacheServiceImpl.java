package com.vortexlab.common.cache.service.impl;

import com.vortexlab.common.cache.constant.CacheConstant;
import com.vortexlab.common.cache.distributed.RedisCache;
import com.vortexlab.common.cache.local.CaffeineCache;
import com.vortexlab.common.cache.service.CacheService;
import com.vortexlab.common.cache.util.CacheTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService {

    private final CaffeineCache caffeineCache;

    private final RedisCache redisCache;

    private final RedissonClient redissonClient;

    @Override
    public Object get(String key) {
        // 一级缓存
        Object localValue = caffeineCache.get(key);

        if (localValue != null) {
            return localValue;
        }

        // 二级缓存
        Object redisValue = redisCache.get(key);

        if (redisValue != null) {
            // Redis命中以后
            // 回填Caffeine
            caffeineCache.put(key, redisValue);
            return redisValue;
        }

        return null;
    }

    @Override
    public void set(String key, Object value) {
        set(key, value, CacheTimeUtil.randomExpire());
    }

    @Override
    public void set(String key, Object value, long expire) {
        redisCache.set(key, value, expire);
        caffeineCache.put(key, value);
    }

    @Override
    public void delete(String key) {
        redisCache.remove(key);
        caffeineCache.remove(key);
    }

    @Override
    public <T> T getOrLoad(String key, Class<T> clazz, CacheLoader<T> loader) {
        // caffeine
        Object value = caffeineCache.get(key);
        if (value != null) {
            return covert(value, clazz);
        }

        // redis
        value = redisCache.get(key);
        if (value != null) {
            // 空值缓存
            if (isNullValue(value)) {
                caffeineCache.put(key, CacheConstant.NULL_VALUE);
                return null;
            }

            caffeineCache.put(key, value);
            return covert(value, clazz);
        }

        // redis没有
        String lockKey = "lock:cache:" + key;

        RLock lock = redissonClient.getLock(lockKey);
        boolean locked = false;

        try {
            // 获取分布式锁
            locked = lock.tryLock(3, 10, TimeUnit.SECONDS);
            if (!locked) {
                log.warn("获取缓存锁失败，key={}", key);
                /*
                 * 不直接访问数据库。 再尝试一次Redis。
                 */
                value = redisCache.get(key);
                if (value != null) {
                    if (isNullValue(value)) {
                        return null;
                    }

                    caffeineCache.put(key, value);
                    return covert(value, clazz);
                }
                return null;
            }
            // Double Check
            value = redisCache.get(key);
            if (value != null) {
                if (isNullValue(value)) {
                    caffeineCache.put(key, CacheConstant.NULL_VALUE);
                    return null;
                }
                caffeineCache.put(key, value);
                return covert(value, clazz);
            }

            // 查询数据库
            T result = loader.load();

            // 数据不存在
            if (result == null) {
                // Redis保存空值
                redisCache.set(key, CacheConstant.NULL_VALUE, CacheTimeUtil.nullExpire());
                // Caffeine保存空值
                caffeineCache.put(key, CacheConstant.NULL_VALUE);
                log.debug("用户不存在，缓存空值，userId={}", id);
                return null;
            }

            // 写缓存
            long expire = CacheTimeUtil.randomExpire();
            // 写Redis
            redisCache.set(key, result, expire);
            // 写Caffeine
            caffeineCache.put(key, result);
            return result;
        } catch (InterruptedException e) {
            // 恢复线程中断状态
            Thread.currentThread().interrupt();
            log.error("缓存锁等待被中断,key={}", key, e);
            return null;
        } finally {
            if (locked && lock.isHeldByCurrentThread()) {
                // 释放锁
                lock.unlock();
            }
        }
    }

    private boolean isNullValue(Object value) {
        return CacheConstant.NULL_VALUE.equals(value);
    }

    private <T> T covert(Object value, Class<T> clazz) {
        return clazz.cast(value);
    }
}
