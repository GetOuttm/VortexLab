package com.vortexlab.gateway.constant;

public interface GatewayConstant {

    /**
     * Token Header
     */
    String TOKEN_HEADER = "Authorization";

 String TOKEN_BEARER = "Bearer ";

    /**
     * 白名单
     */
    String[] WHITE_LIST = {
            "/auth/login",
            "/user/register"
    };
}
