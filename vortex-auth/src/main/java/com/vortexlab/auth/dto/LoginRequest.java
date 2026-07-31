package com.vortexlab.auth.dto;

import lombok.Data;

@Data
public class LoginRequest {

    private String userName;

    private String passWord;
}
