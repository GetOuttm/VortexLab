package com.vortexlab.gateway.filter;

import com.vortexlab.common.security.jwt.JwtUtil;
import com.vortexlab.gateway.constant.GatewayConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关全局鉴权过滤器。
 * <p>
 * 所有经过 Gateway 的请求（除登录接口外）均需在请求头中携带 token，
 * 校验通过后才转发至下游微服务（vortex-user、vortex-order 等）。
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;

    /**
     * 请求鉴权入口：白名单放行 → 提取 Token → 校验 → 转发或拒绝。
     *
     * @param exchange 当前请求/响应上下文
     * @param chain    过滤器链，校验通过后调用 {@code chain.filter} 继续转发
     * @return 异步响应 Mono
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {

        String path = exchange.getRequest()
                .getURI()
                .getPath();

        // 白名单放行
        for (String white : GatewayConstant.WHITE_LIST) {
            if (path.equals(white)) {
                return chain.filter(exchange);
            }
        }

        // 从请求头获取 Token
        String token = exchange.getRequest()
                .getHeaders()
                .getFirst(GatewayConstant.TOKEN_BEARER);

        // Token 缺失，返回 401 未授权
        if (token == null || token.isEmpty()) {
            return unauthorized(exchange);
        }

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (!jwtUtil.validateToken(token)) {
            return unauthorized(exchange);
        }

        return chain.filter(exchange);
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        // Token 无效或已过期，返回 401 未授权
        exchange.getResponse()
                .setStatusCode(
                        HttpStatus.UNAUTHORIZED
                );
        return exchange.getResponse().setComplete();
    }

    /**
     * 过滤器执行顺序，数值越小优先级越高。
     * 设为 0 确保在其他业务过滤器之前执行鉴权。
     */
    @Override
    public int getOrder() {
        return -100;
    }
}
