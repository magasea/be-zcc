package com.wensheng.service.impl;

import com.wensheng.service.WXService;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;



@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration
public class WXServiceImplTest {

    @Autowired
    WXService wxServiceImpl;



    @Test
    public void loginWX() throws Exception {
        String code = "061oFbse1Xl90s0FlDse1perse1oFbs-";
        UserDetails userDetails =  wxServiceImpl.loginWX(code);
    }
}