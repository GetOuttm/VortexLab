package com.vortexlab.service.impl;

import com.vortexlab.common.util.JwtUtil;
import com.vortexlab.service.AuthService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private StringRedisTemplate redis;

    @Override
    public String login(String userName, String passWord) {
        // 模拟查询数据库
        Long userId = 1L;

        // 生成token
        String token = JwtUtil.createToken(userId);

        // 保存登录状态
        redis.opsForValue()
                .set("login:" + userId,
                        token,
                        7,
                        TimeUnit.DAYS);

        return token;
    }
}
