package com.vortexlab.common.cache.local;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

public class CaffeineCache {

    private final Cache<String, Object> cache;

    public CaffeineCache() {
        this.cache = Caffeine.newBuilder()
                // 最大数量
                .maximumSize(10000)
                // 写入10分钟过期
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build();
    }

    /**
     * 写缓存
     */
    public void put(String key, Object value) {
        cache.put(key, value);
    }

    /**
     * 查询缓存
     */
    public Object get(String key) {
        return cache.getIfPresent(key);
    }


    /**
     * 删除缓存
     */
    public void remove(String key) {
        cache.invalidate(key);
    }

    /**
     * 判断是否存在
     */
    public boolean contains(String key) {
        return cache.getIfPresent(key) != null;
    }

    /**
     * 清空缓存
     */
    public void clear() {
        cache.invalidateAll();
    }
}
