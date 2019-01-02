package com.wensheng.zcc.amc.utils;

import java.math.RoundingMode;
import java.text.NumberFormat;

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

}
