package com.wensheng.zcc.common.utils;

import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@ActiveProfiles(value = "dev")
public class AmcDateUtilsTest {

  @Test
  public void getDefaultDate() {
    Date date = AmcDateUtils.getDataBaseDefaultOldDate();
    System.out.println(date.toString());
  }

  @Test
  public void getDateFromTimeStamp() {
    Date date = AmcDateUtils.toUTCDate(1575043200000L);
    System.out.println(date.toString());
  }


  @Test
  public void toUTCDateFromLocal() {
    Date date = AmcDateUtils.toUTCDateFromLocal(1592449966L);
    System.out.println(date);
  }
}
