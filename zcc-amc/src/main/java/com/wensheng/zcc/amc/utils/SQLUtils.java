package com.wensheng.zcc.amc.utils;

import com.wensheng.zcc.amc.module.dao.helper.OrderByEnum;
import java.util.HashMap;
import java.util.Map;
import org.springframework.util.CollectionUtils;

/**
 * @author chenwei on 1/7/19
 * @project zcc-backend
 */
public class SQLUtils {

  public static String getOrderBy(HashMap<String, Integer> orderByParam) throws Exception {
    if(CollectionUtils.isEmpty(orderByParam)){
      throw new Exception("empty orderByParam");
    }
    StringBuilder sb = new StringBuilder();
    for(Map.Entry<String, Integer> item : orderByParam.entrySet()){
      sb.append(item.getKey()).append(" ").append(OrderByEnum.lookupByDisplayNameUtil(item.getValue())).append(",");
    }
    return sb.substring(0, sb.length() - 1);
  }

}
