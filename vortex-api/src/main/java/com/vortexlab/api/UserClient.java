package com.vortexlab.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 用户服务 Feign 远程调用客户端。
 * <p>
 * 供 vortex-order 等微服务通过服务名调用 vortex-user 的用户查询接口。
 * </p>
 */
@FeignClient(name = "vortex-user")
public interface UserClient {

    /**
     * 远程查询用户信息。
     *
     * @param id 用户 ID
     * @return 用户信息字符串
     */
    @GetMapping("/user/{id}")
    String getUser(@PathVariable("id") Long id);
}
