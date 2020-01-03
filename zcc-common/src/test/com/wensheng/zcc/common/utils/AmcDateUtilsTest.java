package com.wensheng.zcc.common.utils;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles(value = "dev")
public class AmcDateUtilsTest {

  @Test
  public void getDefaultDate() {
    Date date = AmcDateUtils.getDataBaseDefaultOldDate();
    System.out.println(date.toString());
  }

  @Test
  public void getDateFromTimeStamp() {
    Date date = AmcDateUtils.toDate(1576684800000L);
    System.out.println(date.toString());
  }

}
