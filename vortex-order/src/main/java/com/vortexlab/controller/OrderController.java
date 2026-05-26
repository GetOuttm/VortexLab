package com.vortexlab.controller;

import com.vortexlab.api.UserClient;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private UserClient userClient;

    @GetMapping("/create/{userId}")
    public String create(@PathVariable("userId") Long userId) {

        String user = userClient.getUser(userId);

        return "订单创建成功，用户：" + user;
    }
}