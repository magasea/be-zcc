package com.wensheng.zcc.common.utils;

/**
 * @author chenwei on 1/11/19
 * @project zcc-backend
 */
public class ExceptionUtils {

  public static Exception getAmcException(AmcExceptions amcExceptions){
    return new Exception(String.format("%s:%s:%s",amcExceptions.code, amcExceptions.name, amcExceptions.reason));
  }

  public static Exception getAmcException(AmcExceptions amcExceptions, String additional){
    return new Exception(String.format("%s:%s:%s--%s",amcExceptions.code, amcExceptions.name, amcExceptions.reason,
        additional));
  }



}
