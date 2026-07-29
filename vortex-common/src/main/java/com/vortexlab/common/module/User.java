package com.vortexlab.common.module;

import lombok.Data;

@Data
public class User {

    private Long id;

    private String userName;

    private String passWord;

    public User(Long id, String userName) {
        this.id = id;
        this.userName = userName;
    }
}
