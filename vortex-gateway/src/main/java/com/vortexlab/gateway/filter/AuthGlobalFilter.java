package com.vortexlab.gateway.filter;

import com.vortexlab.common.util.JwtUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
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
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Resource
    private ReactiveStringRedisTemplate redis;

    /**
     * 客户端传递 Token 的请求头名称
     */
    private static final String TOKEN_HEADER = "token";

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

        // 登录接口无需鉴权，直接放行
        if (path.contains("/auth/login")) {
            return chain.filter(exchange);
        }

        // 从请求头获取 Token
        String token = exchange.getRequest()
                .getHeaders()
                .getFirst(TOKEN_HEADER);

        // Token 缺失，返回 401 未授权
        if (token == null || token.isEmpty()) {

            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse().setComplete();
        }

        Long userId;

        try {
            // 校验 Token 签名与有效期，失败则进入 catch
            userId=    JwtUtil.parseToken(token);

        } catch (Exception e) {
            log.error("JWT解析失败", e);
            return unauthorized(exchange);
        }

        // redis 校验登录状态
        return redis.opsForValue()
                .get("login:" + userId)
                .flatMap(redisToken ->
                        token.equals(redisToken)
                                ? chain.filter(exchange)
                                : unauthorized(exchange)
                )
                .switchIfEmpty(unauthorized(exchange));
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
        return 0;
    }
}
