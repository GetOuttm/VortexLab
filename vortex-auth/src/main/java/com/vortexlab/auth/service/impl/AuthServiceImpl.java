package com.vortexlab.auth.service.impl;

import com.vortexlab.api.user.UserClient;
import com.vortexlab.api.user.dto.UserDTO;
import com.vortexlab.auth.dto.LoginRequest;
import com.vortexlab.auth.service.AuthService;
import com.vortexlab.auth.vo.LoginVO;
import com.vortexlab.common.core.exception.BusinessException;
import com.vortexlab.common.core.response.ResultCode;
import com.vortexlab.common.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserClient userClient;

    private final JwtUtil jwtUtil;

    private final StringRedisTemplate redis;

    @Override
    public LoginVO login(LoginRequest request) {
        UserDTO user = userClient.getUserById(1L);

        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_EXIST);
        }

        String token = jwtUtil.createToken(user.getId());

        redis.opsForValue()
                .set("login:" + user.getId(),
                        token,
                        7,
                        TimeUnit.DAYS);

        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        return loginVO;
    }
}
