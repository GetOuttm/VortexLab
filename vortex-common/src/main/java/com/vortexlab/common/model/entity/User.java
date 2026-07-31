package com.vortexlab.common.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("USER")
public class User {

    private Long id;

    private String userName;

    private String passWord;

    public User(Long id, String userName) {
        this.id = id;
        this.userName = userName;
    }
}
