package com.wensheng.zcc.amc.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.Test;

/**
 * @author chenwei on 1/3/19
 * @project zcc-backend
 */
public class AmcNumberUtilsTest {

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

  @Test
  public void getLongFromStringWithMult100() {
  }

  @Test
  public void getLongFromDoubleWithMult100() {
    double dexp = 12345678;
    System.out.printf("dexp: %f\n", dexp);
    System.out.printf("dexp: %.00f\n", dexp);

  }

  @Test
  public void getBigDecimalHalfUp() throws Exception {
    BigDecimal input = BigDecimal.ONE;
    System.out.println(input.multiply(new BigDecimal("666.6667")).setScale(2, RoundingMode.HALF_UP ));

    Long result = AmcNumberUtils.getSQMFromMu(new BigDecimal("2.11"));
    System.out.println(result);
    BigDecimal resultMu = AmcNumberUtils.getMuFromSQM(result);
    System.out.println(resultMu);
  }
}