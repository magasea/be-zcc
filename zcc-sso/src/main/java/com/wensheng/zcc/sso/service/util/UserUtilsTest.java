package com.wensheng.zcc.sso.service.util;

import org.junit.Test;

/**
 * @author chenwei on 1/30/19
 * @project zcc-backend
 */
public class UserUtilsTest {

  @Test
  public void getEncode() {
    System.out.println(UserUtils.getEncode("123"));
//    $2a$10$cAd8q1JDkVVmoeBmT5kYW.tNdUvnN33RFKesuW.XSrAZJsXkIoMfi

    System.out.println(UserUtils.match("123", "$2a$10$cAd8q1JDkVVmoeBmT5kYW.tNdUvnN33RFKesuW.XSrAZJsXkIoMfi"));

  }
}