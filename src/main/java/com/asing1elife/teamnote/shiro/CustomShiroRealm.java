/*****************************************************************************
 * Copyright (c) 2020, www.shixun.online
 *
 * All rights reserved
 *
 *****************************************************************************/
package com.asing1elife.teamnote.shiro;

import com.asing1elife.teamnote.model.UserModel;
import com.asing1elife.teamnote.model.dictionary.SimpleStatus;
import com.asing1elife.teamnote.module.user.service.UserAuthServiceImpl;
import com.asing1elife.teamnote.shiro.jwt.JWTToken;
import com.asing1elife.teamnote.shiro.jwt.JWTUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomShiroRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserAuthServiceImpl userAuthService;

    /**
     * 使用JWT替换原生Token
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("doGetAuthenticationInfo -> {}", principalCollection.toString());

        // 从前端传入的Token中获取用户名
        String username = JWTUtil.getUsername(principalCollection.toString());

        // 从数据库获取用户信息
        UserModel user = userAuthService.getPrincipal(username);

        // 获取用户权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<String> permissions = userAuthService.getPermissions(user.getId());
        info.addStringPermissions(permissions);

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取Token
        String token = (String) authenticationToken.getCredentials();

        // 从Token中获取用户名
        String username = JWTUtil.getUsername(token);

        // Token不可用
        if (username == null) {
            throw new UnauthenticatedException();
        }

        // 获取用户
        UserModel user = userAuthService.getPrincipal(username);

        // 用户不存在
        if (user == null) {
            throw new UnknownAccountException();
        }

        // 用户被禁用
        if (user.getStatus().getCode().equalsIgnoreCase(SimpleStatus.SIST_Disable.getCode())) {
            throw new DisabledAccountException();
        }

        // 密码验证失败
        if (!JWTUtil.verify(token, username, user.getPassword())) {
            throw new IncorrectCredentialsException();
        }

        // return new SimpleAuthenticationInfo((PrincipalCollection) user, token);
        return new SimpleAuthenticationInfo(token, token, "realm");
    }

}
