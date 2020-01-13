package com.wensheng.zcc.wechat.utils;

import java.nio.charset.Charset;
import java.util.Random;

public class WxToolsUtil {
  public static String getRandomStr() {
    byte[] array = new byte[7]; // length is bounded by 7
    new Random().nextBytes(array);
    String generatedString = new String(array, Charset.forName("UTF-8"));

    System.out.println(generatedString);
    return generatedString;
  }

}
