package com.wensheng.zcc.sso.service.util;

/**
 * @author chenwei on 1/11/19
 * @project zcc-backend
 */
public class ExceptionUtils {

  public static Exception getAmcException(AmcExceptions amcExceptions){
    return new Exception(String.format("%d:%s:%s",amcExceptions.code, amcExceptions.name, amcExceptions.reason));
  }

  public static Exception getAmcException(AmcExceptions amcExceptions, String additional){
    return new Exception(String.format("%d:%s:%s-%s",amcExceptions.code, amcExceptions.name, amcExceptions.reason,
        additional));
  }

  public static enum AmcExceptions{

    NO_SUCHUSER(1001, "No such user", "not fund such user"),
    NO_REQUIRED_PHONEINFO(1002, "No phone info", "没有手机信息"),
    INVALID_PASSWORD(1003, "Invalid Password", "密码不匹配或者为空"),
    INVALID_PHONE_VERCODE(1004, "Invalid vercode", "验证码不匹配或者验证码过期"),
    ;
    int code;
    String name;
    String reason;
    AmcExceptions(int code, String name, String reason){
        this.code = code;
        this.name = name;
        this.reason = reason;
      }
  }

}
