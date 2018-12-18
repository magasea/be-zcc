package com.wensheng.zcc.amc.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import org.junit.Test;
import org.springframework.util.NumberUtils;

public class NumberUtilsTest {

  @Test
  public void testGetLongFromStringWithMult100() {
   String testInput = "30000.1";
   Long output = AmcNumberUtils.getLongFromStringWithMult100(testInput);
   System.out.println(output);
    testInput = "30000.01";
   output = AmcNumberUtils.getLongFromStringWithMult100(testInput);
   System.out.println(output);
    testInput = "30,000.01";
   output = AmcNumberUtils.getLongFromStringWithMult100(testInput);
   System.out.println(output);

  testInput = "30,000";
   output = AmcNumberUtils.getLongFromStringWithMult100(testInput);
   System.out.println(output);

  }
}