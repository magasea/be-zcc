package com.wensheng.zcc.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Random;
import java.util.regex.Pattern;

public class AmcNumberUtils {
  public static Long getLongFromStringWithMult100(String input) throws IllegalArgumentException{
    int pointPos = input.indexOf(".");
    Long smallPartL = 0L;
    if(pointPos > 0 && pointPos+1 <= input.length()){
      String smallPart = input.substring(pointPos+1);
      if(smallPart.length() < 2){
        smallPartL = Long.parseLong(smallPart)*10;
      }else{
        smallPart = smallPart.substring(0, 2);
        smallPartL = Long.parseLong(smallPart);
      }
    }
    String bigPart = "0";
    if(pointPos > 0){
     bigPart = input.substring(0, pointPos);
    }else{
      bigPart = input;
    }
    if(bigPart.contains(",")){
      bigPart = bigPart.replace(",","");
    }
    Long bigPartL = Long.parseLong(bigPart)*100;
    return bigPartL + smallPartL;
  }

  public static Long getLongFromDoubleWithMult100(Double input) throws IllegalArgumentException{
    NumberFormat nf = NumberFormat.getNumberInstance();
    nf.setMaximumFractionDigits(2);
    nf.setRoundingMode(RoundingMode.HALF_UP);
    String rounded = nf.format(input);
    return getLongFromStringWithMult100(rounded);

  }

  public static BigDecimal getDecimalFromLongDiv100(Long input){
      return BigDecimal.valueOf(input).divide(BigDecimal.valueOf(100));
  }

  public static Long getLongFromDecimalWithMult100(BigDecimal input){
    return input.multiply(BigDecimal.valueOf(100)).longValue();
  }

  /**
   * get squre meter from mu unit
   */
  public static Long getSQMFromMu(BigDecimal mu) throws Exception {
//    mu.multiply(BigDecimal.)
    if( mu.compareTo(BigDecimal.ZERO) < 0 ){
      throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_LANDAREA_NUMBER, mu.toString());
    }
    BigDecimal result = mu.multiply(new BigDecimal("666.6667")).setScale(2, RoundingMode.HALF_UP);
    return result.multiply(BigDecimal.valueOf(100)).longValue();
  }

  public static BigDecimal getMuFromSQM(Long sqm){
    BigDecimal result =
        BigDecimal.valueOf(sqm).divide(new BigDecimal("66666.67"),2, RoundingMode.HALF_UP);
    return result;
  }

  public static Long getLongFromDecimalWithMult1000000(BigDecimal input){
    return input.multiply(BigDecimal.valueOf(1000000)).longValue();
  }

  public static BigDecimal getBigDecimalFromLongDiv1000000(Long input){
    return BigDecimal.valueOf(input).divide(BigDecimal.valueOf(1000000));
  }

  private static volatile Pattern patternNumber = Pattern.compile("-?\\d+(\\.\\d+)?");

  public static boolean isNumeric(String strNum) {
    if (strNum == null) {
      return false;
    }
    return patternNumber.matcher(strNum).matches();
  }

  /**
   * 获取6位随机验证码
   * @return
   */
  public static String getValidationCode(){
    return String.valueOf((new Random().nextInt(899999) + 100000));
  }

}
