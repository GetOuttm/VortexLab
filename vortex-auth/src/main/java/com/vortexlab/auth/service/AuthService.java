package com.vortexlab.auth.service;

import com.vortexlab.auth.dto.LoginRequest;
import com.vortexlab.auth.vo.LoginVO;

public interface AuthService {

    LoginVO login(LoginRequest request);
}
