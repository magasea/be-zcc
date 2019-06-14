package com.wensheng.zcc.common.utils;

import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import java.io.File;

public class SystemUtils {
  public static boolean checkAndMakeDir(String path) throws Exception {
    File directory = new File(path);
    try{
      if (! directory.exists()){
        directory.mkdirs();
      }
    }catch (Exception ex){

      throw ExceptionUtils.getAmcException(AmcExceptions.DIRECTORY_OPER_FAILED);
    }
    return true;
  }

}
