package com.wensheng.zcc.common.utils;

import com.wenshengamc.zcc.common.Common.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
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
    DateFormat df = null;
    if(input.contains("年")){
      df = new SimpleDateFormat("yyyy'年'MM'月'dd'日'", Locale.CHINA);
    }else {
       df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

    }
    Date result =  df.parse(input);
    return  result;
//    System.out.println(result);
  }

  public static LocalDateTime getLocalDateTime(){
    return LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
  }

  public static Timestamp fromLocalDate(LocalDate localDate) {
    Instant instant = localDate.atStartOfDay().toInstant(ZoneOffset.UTC);
    return Timestamp.newBuilder()
        .setSeconds(instant.getEpochSecond())
        .setNanos(instant.getNano())
        .build();
  }

  public static LocalDate toLocalDate(Timestamp timestamp) {
    return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos()), ZoneId.of("UTC"))
        .toLocalDate();
  }

}
