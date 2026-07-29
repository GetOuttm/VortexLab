package com.vortexlab.controller;

import com.vortexlab.api.UserClient;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单服务控制器。
 * <p>
 * 演示微服务间通过 Feign 调用用户服务。
 * 所有接口均经过 Gateway 鉴权，需携带有效 Token。
 * </p>
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    /** 用户服务 Feign 客户端，用于远程查询用户信息 */
    @Resource
    private UserClient userClient;

    /**
     * 创建订单（演示接口）。
     * <p>
     * 创建前先通过 Feign 调用 vortex-user 获取用户信息。
     * </p>
     *
     * @param userId 下单用户 ID
     * @return 订单创建结果描述
     */
    @GetMapping("/create/{userId}")
    public String create(@PathVariable("userId") Long userId) {

        // 远程调用用户服务，获取用户信息
        String user = userClient.getUser(userId);

        return "订单创建成功，用户：" + user;
    }
}
