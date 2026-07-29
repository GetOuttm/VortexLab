package com.vortexlab.ontroller;

import com.alibaba.nacos.api.model.v2.Result;
import com.vortexlab.common.util.JwtUtil;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * 用户服务控制器。
 * <p>
 * 提供登录（签发 JWT）和用户查询接口。
 * 登录成功后 Token 同时写入 Redis，用于后续会话管理与登出控制。
 * </p>
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /** Redis 操作模板，用于存储用户登录 Token */
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /** Redis 中登录 Token 的 Key 前缀，完整 Key 格式：login:{userId} */
    private static final String LOGIN_KEY_PREFIX = "login:";

    /**
     * 用户登录接口。
     * <p>
     * 根据 userId 生成 JWT Token，并将 Token 存入 Redis。
     * 客户端后续请求需在 Gateway 请求头中携带该 Token。
     * </p>
     *
     * @param userId 用户 ID（当前为演示逻辑，直接传 ID 即可登录）
     * @return 包含 JWT Token 的统一响应
     */
    @PostMapping("/login")
    public Result<String> login(@RequestParam Long userId) {

        // 生成 JWT Token
        String token = JwtUtil.createToken(userId);

        // 将 Token 存入 Redis，Key 为 login:{userId}
        stringRedisTemplate.opsForValue()
                .set(LOGIN_KEY_PREFIX + userId, token);

        return Result.success(token);
    }

    /**
     * 根据 ID 查询用户信息。
     * <p>
     * 该接口受 Gateway 鉴权保护，需携带有效 Token 才能访问。
     * </p>
     *
     * @param id 用户 ID
     * @return 用户信息（当前为演示数据）
     */
    @GetMapping("/{id}")
    public Result<String> getUser(@PathVariable("id") Long id) {

        return Result.success("user-" + id);
    }
}
