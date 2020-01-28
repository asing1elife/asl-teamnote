/*****************************************************************************
 * Copyright (c) 2020, www.shixun.online
 *
 * All rights reserved
 *
 *****************************************************************************/
package com.asing1elife.teamnote.module.login.service;

import com.asing1elife.teamnote.core.service.DefaultService;
import com.asing1elife.teamnote.model.UserModel;
import com.asing1elife.teamnote.module.user.service.UserServiceImpl;
import com.asing1elife.teamnote.shiro.jwt.JWTUtil;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl extends DefaultService {

    @Autowired
    private UserServiceImpl userService;

    /**
     * 登录
     */
    public String login(String username, String password) {
        UserModel user = userService.getUserByUsername(username);

        if (user == null) {
            throw new UnknownAccountException();
        }

        // 将前端传入的密码进行加密
        String encodePassword = userService.getEncodePassword(password, user.getSalt());

        // 判断密码是否一致
        if (!encodePassword.equals(user.getPassword())) {
            throw new IncorrectCredentialsException();
        }

        // 传入JWT进行对比
        return JWTUtil.sign(username, encodePassword);
    }

}
