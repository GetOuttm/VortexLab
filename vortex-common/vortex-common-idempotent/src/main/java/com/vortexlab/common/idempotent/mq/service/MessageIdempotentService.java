package com.vortexlab.common.idempotent.mq.service;

public interface MessageIdempotentService {

    boolean check(String messageId, long expire);
}
