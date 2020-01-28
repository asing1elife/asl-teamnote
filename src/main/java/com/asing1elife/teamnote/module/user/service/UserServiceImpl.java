/*****************************************************************************
 * Copyright (c) 2020, www.shixun.online
 *
 * All rights reserved
 *
 *****************************************************************************/
package com.asing1elife.teamnote.module.user.service;

import com.asing1elife.teamnote.core.service.BaseService;
import com.asing1elife.teamnote.core.util.BeanUtil;
import com.asing1elife.teamnote.core.util.DigestUtil;
import com.asing1elife.teamnote.core.util.EncodeUtil;
import com.asing1elife.teamnote.model.UserModel;
import com.asing1elife.teamnote.model.simple.User;
import com.asing1elife.teamnote.module.user.repository.UserRepository;
import com.asing1elife.teamnote.shiro.jwt.JWTUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseService<UserModel, UserRepository> {

    private static final int PASSWORD_HASH_ITERATIONS = 1024;
    private static final int PASSWORD_SALT_SIZE = 8;

    /**
     * 获取当前用户
     */
    public UserModel getCurrentUser() {
        String token = (String) SecurityUtils.getSubject().getPrincipal();

        return getUserByToken(token);
    }

    /**
     * 获取当前用户id
     */
    public long getCurrentUserId() {
        UserModel currentUser = getCurrentUser();

        return currentUser == null ? 0L : currentUser.getId();
    }

    /**
     * 根据Token获取用户基本信息
     */
    public User getSimpleUserByToken(String token) {
        UserModel userModel = getUserByToken(token);

        User user = new User();

        BeanUtil.copyProperties(user, userModel);

        return user;
    }

    /**
     * 根据Token获取用户
     */
    private UserModel getUserByToken(String token) {
        // 从Token中获取用户名
        String username = JWTUtil.getUsername(token);

        return getUserByUsername(username);
    }

    /**
     * 根据登录名获取用户
     */
    public UserModel getUserByUsername(String username) {
        return repository.getByEmailOrMobile(username, username);
    }

    /**
     * 获取加密后的密码
     */
    public String getEncodePassword(String password, String salt) {
        byte[] saltBytes = EncodeUtil.hexDecode(salt);
        byte[] passwordBytes = DigestUtil.sha1(password.getBytes(), saltBytes, PASSWORD_HASH_ITERATIONS);

        return EncodeUtil.hexEncode(passwordBytes);
    }

    /**
     * 生成密码混淆值
     */
    public String generateSalt() {
        byte[] saltBytes = DigestUtil.generateSalt(PASSWORD_SALT_SIZE);

        return EncodeUtil.hexEncode(saltBytes);
    }

}
