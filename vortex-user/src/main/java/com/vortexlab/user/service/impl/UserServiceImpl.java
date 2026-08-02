package com.vortexlab.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vortexlab.common.cache.service.CacheService;
import com.vortexlab.user.entity.User;
import com.vortexlab.user.mapper.UserMapper;
import com.vortexlab.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final CacheService cacheService;

    private static final String USER_CACHE_PREFIX = "user:";

    @Override
    public User getById(Long id) {
        // 参数校验
        if (id == null || id <= 0) {
            return null;
        }

        // 构造缓存Key
        String key = USER_CACHE_PREFIX + id;

        return cacheService.getOrLoad(key, User.class, () -> super.getById(id));
    }


    /**
     * 更新用户
     * <p>
     * 缓存策略：
     * <p>
     * 1. 更新数据库
     * 2. 删除Redis
     * 3. 删除Caffeine
     * <p>
     * 使用Cache Aside Pattern
     */
    @Override
    public void updateUser(User user) {
        if (user == null || user.getId() == null) {
            return;
        }

        // 更新数据库
        super.updateById(user);

        String key = USER_CACHE_PREFIX + user.getId();

        // 删除缓存
        cacheService.delete(key);

        log.info("用户更新成功并删除缓存，userId={}", user.getId());
    }
}
