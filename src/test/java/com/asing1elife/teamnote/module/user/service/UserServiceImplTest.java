package com.asing1elife.teamnote.module.user.service;

import com.asing1elife.teamnote.model.simple.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void getEncodePassword() {
        String password = "123456";
        String salt = userService.generateSalt();

        String encodePassword = userService.getEncodePassword(password, salt);

        System.out.println(salt);
        System.out.println(encodePassword);
    }

    @Test
    public void getUserByToken() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1ODAxNzI2OTEsInVzZXJuYW1lIjoiMTUwMDI3MTU3NDcifQ.34TI75FjoJISyL9YTIdowSldvnDgmEPbO9AY6iVNeFk";

        User user = userService.getUserByToken(token);

        System.out.println(user);
    }
}