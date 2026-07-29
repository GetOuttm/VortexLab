package com.vortexlab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 用户服务启动类。
 * <p>
 * 提供登录、用户查询等接口，Token 签发后存入 Redis。
 * 注册到 Nacos，默认端口：8081
 * </p>
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
