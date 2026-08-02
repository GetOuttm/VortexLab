package com.vortexlab.common.cache.util;

import com.vortexlab.common.cache.constant.CacheConstant;

import java.util.concurrent.ThreadLocalRandom;

public class CacheTimeUtil {

    public CacheTimeUtil() {
    }

    /**
     * 获取正常缓存随机过期时间
     * <p>
     * 600 ~ 899 秒
     */
    public static long randomExpire() {
        return CacheConstant.DEFAULT_EXPIRE + ThreadLocalRandom.current().nextLong(CacheConstant.RANDOM_EXPIRE);
    }

    /**
     * 获取空值缓存时间
     */
    public static long nullExpire() {
        return CacheConstant.NULL_EXPIRE;
    }
}
