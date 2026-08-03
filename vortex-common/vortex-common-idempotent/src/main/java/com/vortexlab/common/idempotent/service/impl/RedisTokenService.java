package com.vortexlab.common.idempotent.service.impl;

import com.vortexlab.common.idempotent.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisTokenService implements TokenService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String PREFIX = "idempotent:toekn:";

    @Override
    public String createToken() {
        String token = UUID.randomUUID().toString().replace("-", "");

        redisTemplate.opsForValue()
                .set(PREFIX + token,"1",10 , TimeUnit.MINUTES);

        return token;
    }

    @Override
    public boolean consumeToken(String token) {
        String key = PREFIX + token;

        Boolean exists = redisTemplate.hasKey(key);

        if (!Boolean.TRUE.equals(exists)) {
            return false;
        }

        /**
         * 删除token Redis单线程  保证消费一次
         */
        redisTemplate.delete(key);
        return true;
    }
}
