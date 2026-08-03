package com.vortexlab.common.idempotent.mq.service.impl;

import com.vortexlab.common.idempotent.mq.service.MessageIdempotentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisMessageIdempotentService implements MessageIdempotentService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean check(String messageId, long expire) {
        Boolean result = redisTemplate.opsForValue()
                .setIfAbsent("mq:idempotent:" + messageId, 1, expire, TimeUnit.SECONDS);

        return Boolean.TRUE.equals(result);
    }
}
