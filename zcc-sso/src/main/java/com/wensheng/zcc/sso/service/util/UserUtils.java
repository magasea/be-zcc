package com.wensheng.zcc.sso.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author chenwei on 1/30/19
 * @project zcc-backend
 */
public class UserUtils {

  @Autowired
  private static BCryptPasswordEncoder passwordEncoder;


  public static String getEncode(String password){
    return passwordEncoder.encode("123");
  }

}
