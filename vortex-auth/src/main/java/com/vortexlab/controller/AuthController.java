package com.vortexlab.controller;

import com.vortexlab.common.Result;
import com.vortexlab.service.AuthService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private AuthService authService;

    @PostMapping("/login")
    public Result<String> login(
            @RequestParam String userName,
            @RequestParam String passWord
    ) {
        // 生成token
        String token = authService.login(userName, passWord);

        return Result.success(token);
    }
}
