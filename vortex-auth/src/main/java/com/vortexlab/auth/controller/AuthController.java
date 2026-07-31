package com.vortexlab.auth.controller;

import com.vortexlab.auth.dto.LoginRequest;
import com.vortexlab.auth.service.AuthService;
import com.vortexlab.auth.vo.LoginVO;
import com.vortexlab.common.core.response.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginRequest request) {
        return Result.success(authService.login(request));
    }
}
