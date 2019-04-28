package com.wensheng.zcc.cust.utils.sync;

import com.google.common.collect.Lists;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SyncUtils {

  public static String[] getPhoneList(String input) throws Exception {
    String[] items;
    if(input.contains(" ") ){
       items = input.split(" ");
      if(items.length > 4){
        throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_PHONE);
      }
    }else if( input.contains(",")){
      items = input.split(" ");
      if(items.length > 4){
        throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_PHONE);
      }

    }else if(input.contains("/")){
      items = input.split("/");
      if(items.length > 4){
        throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_PHONE);
      }

    }else{
      items =  new String[]{input};
    }
    Arrays.sort(items, new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        return o1.length() - o2.length();
      }
    });
    return items;
  }

}
