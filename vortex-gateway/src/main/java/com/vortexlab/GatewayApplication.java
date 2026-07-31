package com.vortexlab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * API 网关启动类。
 * <p>
 * 负责路由转发、统一鉴权（AuthFilter），注册到 Nacos 服务发现。
 * 默认端口：9000
 * </p>
 */
@SpringBootApplication(scanBasePackages = "com.vortexlab")
@EnableDiscoveryClient
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
