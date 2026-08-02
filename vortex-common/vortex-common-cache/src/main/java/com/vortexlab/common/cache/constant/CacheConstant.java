package com.vortexlab.common.cache.constant;

public interface CacheConstant {

    /**
     * 空值缓存标记
     */
    String NULL_VALUE = "NULL";

    /**
     * 默认缓存时间：10分钟
     */
    long DEFAULT_EXPIRE = 600;

    /**
     * 空值缓存时间：60秒
     */
    long NULL_EXPIRE = 60;

    /**
     * 随机增加最大时间：5分钟
     */
    long RANDOM_EXPIRE = 300;
}
