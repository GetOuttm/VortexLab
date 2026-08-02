package com.vortexlab.common.cache.service;

public interface CacheService {

    /**
     * 查询缓存
     */
    Object get(String key);

    /**
     * 写入缓存
     */
    void set(String key, Object value);

    /**
     * 写入缓存并设置过期时间
     */
    void set(String key, Object value, long expire);

    /**
     * 删除缓存
     */
    void delete(String key);

    /**
     * 查询缓存
     * <p>
     * 如果不存在，则执行数据库查询
     * <p>
     * 最终将结果写入缓存。
     */
    <T> T getOrLoad(String key, Class<T> clazz, CacheLoader<T> loader);

    /**
     * 数据加载器
     */
    @FunctionalInterface
    interface CacheLoader<T> {
        T load();
    }
}
