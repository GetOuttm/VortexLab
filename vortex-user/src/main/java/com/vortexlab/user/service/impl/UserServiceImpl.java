package com.vortexlab.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vortexlab.user.entity.User;
import com.vortexlab.user.mapper.UserMapper;
import com.vortexlab.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User getById(Long id) {
        return super.getById(id);
    }
}
