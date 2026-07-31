package com.vortexlab.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.vortexlab.common.core.base.BaseEntity;
import lombok.Data;

@Data
@TableName("USER")
public class User extends BaseEntity {

    private String userName;

    private String passWord;

    private String nickName;
}
