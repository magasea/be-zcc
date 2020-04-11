package com.wensheng.zcc.common.utils;

import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

public class StringToolUtilsTest {

    @Test
    public void testString(){
        String test =  "你好你好，。";
        System.out.println(StringToolUtils.isNormalString(test));
    }

}