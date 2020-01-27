/*****************************************************************************
 * Copyright (c) 2020, www.shixun.online
 *
 * All rights reserved
 *
 *****************************************************************************/
package com.asing1elife.teamnote.shiro.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class JWTUtil {

    // 过期时间30天
    private static final long EXPIRE_TIME = 24 * 60 * 30 * 1000;

    /**
     * 校验token是否正确
     */
    public static boolean verify(String token, String username, String password) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(password);

            JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();

            // 如果验证失败会抛出异常
            verifier.verify(token);

            // 没有异常就表示验证正确
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取登录名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);

            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名
     */
    public static String sign(String username, String password) {
        try {
            // 指定过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);

            Algorithm algorithm = Algorithm.HMAC256(password);

            return JWT.create()
              .withClaim("username", username)
              .withExpiresAt(date)
              .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

}
