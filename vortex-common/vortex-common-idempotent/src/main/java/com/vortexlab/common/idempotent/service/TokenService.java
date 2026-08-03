package com.vortexlab.common.idempotent.service;

public interface TokenService {

    /**
     * 创建token
     */
    String createToken();

    /**
     * 消费token
     */
    boolean consumeToken(String token);
}
