package com.wensheng.zcc.amc.utils;

import static org.junit.Assert.*;

import java.util.HashMap;
import org.junit.Test;

/**
 * @author chenwei on 1/7/19
 * @project zcc-backend
 */
public class SQLUtilsTest {

  @Test
  public void getOrderBy() throws Exception {

    HashMap<String, Integer> orderByParam = new HashMap<>();
    orderByParam.put("fileld1", 0);
    orderByParam.put("field2", 1);
    String orderByStr = SQLUtils.getOrderBy(orderByParam);
    System.out.println(orderByStr);
  }
}