package com.vortexlab.common.idempotent.service.impl;

import com.vortexlab.common.idempotent.enums.IdempotentStatus;
import com.vortexlab.common.idempotent.model.IdempotentRecord;
import com.vortexlab.common.idempotent.service.IdempotentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisIdempotentService implements IdempotentService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean tryAcquire(String key, long expire) {
        IdempotentRecord record = new IdempotentRecord();

        record.setStatus(IdempotentStatus.PROCESSING);

        record.setTimestamp(System.currentTimeMillis());

        Boolean success = redisTemplate.opsForValue()
                .setIfAbsent(
                        key,
                        record,
                        expire,
                        TimeUnit.SECONDS
                );

        return Boolean.TRUE.equals(success);
    }

    @Override
    public IdempotentRecord get(String key) {
        Object value = redisTemplate.opsForValue().get(key);

        if (value instanceof IdempotentRecord) {
            return (IdempotentRecord) value;
        }
        return null;
    }

    @Override
    public void success(String key, Object result, long expire) {
        IdempotentRecord record = new IdempotentRecord();

        record.setStatus(IdempotentStatus.SUCCESS);

        record.setResult(result);

        record.setTimestamp(System.currentTimeMillis());

        redisTemplate.opsForValue()
                .set(key, record, expire, TimeUnit.SECONDS);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
