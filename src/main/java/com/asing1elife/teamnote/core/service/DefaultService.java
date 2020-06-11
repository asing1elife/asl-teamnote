package com.asing1elife.teamnote.core.service;

import com.asing1elife.teamnote.core.exception.CustomException;
import com.asing1elife.teamnote.model.simple.User;
import com.asing1elife.teamnote.module.user.service.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class DefaultService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    private UserServiceImpl userService;

    /**
     * 获取用户信息
     */
    protected User getUser() {
        Object tokenObj = SecurityUtils.getSubject().getPrincipal();

        if (tokenObj == null) {
            throw new CustomException("没有检测到用户登录数据");
        }

        String token = String.valueOf(tokenObj);

        User user = userService.getSimpleUserByToken(token);

        if (user == null) {
            logger.error("没有检测到有效用户登录数据，token -> {}", token);
            throw new CustomException("没有检测到有效用户登录数据");
        }

        return user;
    }

}
