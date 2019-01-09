package com.wensheng.zcc.amc.utils;

import static org.junit.Assert.*;

import java.util.HashMap;
import org.junit.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

/**
 * @author chenwei on 1/7/19
 * @project zcc-backend
 */
public class SQLUtilsTest {

  @Test
  public void getOrderBy() throws Exception {

    HashMap<String, Sort.Direction> orderByParam = new HashMap<>();
    orderByParam.put("fileld1", Direction.ASC);
    orderByParam.put("field2", Direction.DESC);
    String orderByStr = SQLUtils.getOrderBy(orderByParam);
    System.out.println(orderByStr);
  }
}