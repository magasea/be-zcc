package com.wensheng.zcc.amc.utils;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Date;
import org.junit.Test;

/**
 * @author chenwei on 1/2/19
 * @project zcc-backend
 */
public class AmcDateUtilsTest {
  @Test
  public void testDateInput() throws ParseException {
    String date1 = "2018-11-20";
    Date output = AmcDateUtils.getDateFromStr(date1);
    System.out.println(output.toString());
  }

}