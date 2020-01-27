package com.asing1elife.teamnote.module.login.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LoginServiceImplTest {

    @Autowired
    private LoginServiceImpl loginService;

    @Test
    public void login() {
        String username = "15002715747@139.com";
        // String username = "15002715747";
        String password = "123456";

        String result = loginService.login(username, password);

        System.out.println(result);
    }
}