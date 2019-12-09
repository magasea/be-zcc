package com.wensheng.zcc.cust.utils;

import org.junit.Test;
import org.springframework.util.StringUtils;


public class SyncEnumUtilsTest {

  @Test
  public  void testString() {
    String cityName = "肇庆市";
    System.out.println(cityName.substring(0, cityName.indexOf("市")));

    System.out.println(StringUtils.trimWhitespace("  你 好 "));

    System.out.println(new String("1234567890").substring(0,7));
  }

}
