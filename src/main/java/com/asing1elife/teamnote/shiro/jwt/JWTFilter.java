/*****************************************************************************
 * Copyright (c) 2020, www.shixun.online
 *
 * All rights reserved
 *
 *****************************************************************************/
package com.asing1elife.teamnote.shiro.jwt;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JWTFilter extends BasicHttpAuthenticationFilter {

    /**
     * 检测用户是否登录
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        // 检测header里面是否包含Authorization字段即可
        // 检测到则返回true，否则返回false
        return getAuthorization(request) != null;

    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        // 从头文件中获取token
        String authorization = getAuthorization(request);

        JWTToken token = new JWTToken(authorization);

        // 执行登录的操作又CustomShiroRealm完成，登录失败会抛出异常
        getSubject(request, response).login(token);

        // 没有抛出异常则直接返回true，表示登录成功
        return true;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        // 首先判断前端是否在头文件中传入了Token
        if (isLoginAttempt(request, response)) {
            try {
                // 执行登录操作
                executeLogin(request, response);
            } catch (Exception e) {
                throw new UnauthenticatedException();
            }
        }

        // 没有传入则直接返回true
        // 这里不需要担心前端故意不传头文件来尝试绕过验证
        // 在需要鉴权的Controller中会添加@RequiresAuthentication注解来表示该请求需要鉴权
        return true;
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = getResponse(request, response);

        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }

        return super.preHandle(request, response);
    }

    /**
     * 从头文件中获取token
     */
    private String getAuthorization(ServletRequest request) {
        HttpServletRequest req = (HttpServletRequest) request;

        return req.getHeader(AUTHORIZATION_HEADER);
    }

    private HttpServletResponse getResponse(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));

        return httpServletResponse;
    }
}