package com.wensheng.zcc.amc.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author chenwei on 1/2/19
 * @project zcc-backend
 */
public class AmcDateUtils {

  /**
   *
   * @param input date format yyyy-MM-dd
   * @return
   */

  public static Date getDateFromStr(String input) throws ParseException {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    Date result =  df.parse(input);
    return  result;
//    System.out.println(result);
  }

}
