/*****************************************************************************
 * Copyright (c) 2020, www.shixun.online
 *
 * All rights reserved
 *
 *****************************************************************************/
package com.asing1elife.teamnote.module.user.service;

import com.asing1elife.teamnote.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAuthServiceImpl {

    @Autowired
    private UserServiceImpl userService;

    public UserModel getPrincipal(String name) {
        return userService.getUserByUsername(name);
    }

    /**
     * 暂时没有权限
     */
    public List<String> getPermissions(Long id) {
        return null;
    }
}
