package com.wensheng.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration
public class ZCCUserDaoTest {
    @Autowired
    ZCCUserDao zccUserDao;

    @Test
    public void findByWechatId() {
        String openId = "o0wQK4xcAFSD15DAu32letH9-ws8";
        zccUserDao.findByWechatId(openId);
    }
}
