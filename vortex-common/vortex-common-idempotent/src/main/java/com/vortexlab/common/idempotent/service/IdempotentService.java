package com.vortexlab.common.idempotent.service;

import com.vortexlab.common.idempotent.model.IdempotentRecord;

public interface IdempotentService {

    /**
     * 尝试创建幂等记录
     */
    boolean tryAcquire(String key, long expire);

    /**
     * 获取幂等记录
     */
    IdempotentRecord get(String key);

    /**
     * 保存执行结果
     */
    void success(String key, Object result, long expire);

    /**
     * 删除
     */
    void delete(String key);
}
