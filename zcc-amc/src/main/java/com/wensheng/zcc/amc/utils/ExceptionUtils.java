package com.wensheng.zcc.amc.utils;

/**
 * @author chenwei on 1/11/19
 * @project zcc-backend
 */
public class ExceptionUtils {

  public static Exception getAmcException(AmcExceptions amcExceptions){
    return new Exception(String.format("%d:%s:%s",amcExceptions.code, amcExceptions.name, amcExceptions.reason));

  }

  public static enum AmcExceptions{

    NO_AMCDEBTPACK_AVAILABLE(1001, "NO_AMCDEBTPACK_AVAILABLE", "no amcdebtpack available"),
    NO_AMCDEBT_AVAILABLE(1002, "NO_AMCDEBT_AVAILABLE", "no amcdebt available"),
    NO_AMCASSET_AVAILABLE(1003, "NO_AMCASSET_AVAILABLE", "no amcasset available"),
    INVALID_ACTION(1004, "INVALID_ACTION", "violate edit rule"),
    INVALID_CREDITOR(1005, "INVALID_CREDITOR", "没有该原始债权人");
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
