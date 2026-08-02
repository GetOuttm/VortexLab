package com.vortexlab.common.cache.local;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

public class GuavaCache {

    private final Cache<String, Object> cache;

    public GuavaCache() {
        cache = Caffeine.newBuilder()
                .maximumSize(5000)
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .build();
    }

    public void put(String key, Object value) {
        cache.put(key, value);
    }

    public Object get(String key) {
        return cache.getIfPresent(key);
    }
}
