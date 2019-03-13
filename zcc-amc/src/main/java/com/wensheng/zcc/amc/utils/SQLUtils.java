package com.wensheng.zcc.amc.utils;

import com.wensheng.zcc.amc.module.dao.helper.OrderByEnum;
import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

/**
 * @author chenwei on 1/7/19
 * @project zcc-backend
 */
public class SQLUtils {

  public static String getOrderBy(Map<String, Sort.Direction> orderByParam) throws Exception {
    if(CollectionUtils.isEmpty(orderByParam)){
      throw new Exception("empty orderByParam");
    }
    StringBuilder sb = new StringBuilder();
    for(Map.Entry<String, Sort.Direction> item : orderByParam.entrySet()){
      sb.append(item.getKey()).append(" ").append(item.getValue().name()).append(",");
    }
    return sb.substring(0, sb.length() - 1);
  }

  public static String getOrderBy(Map<String, Sort.Direction> orderByParam, RowBounds rowBounds) throws Exception {
    if(CollectionUtils.isEmpty(orderByParam)){
      throw new Exception("empty orderByParam");
    }
    StringBuilder sb = new StringBuilder();
    for(Map.Entry<String, Sort.Direction> item : orderByParam.entrySet()){
      sb.append(item.getKey()).append(" ").append(item.getValue().name()).append(",");
    }
    if(rowBounds != null && rowBounds.getOffset() >= 0 && rowBounds.getLimit() > 0){
       sb.deleteCharAt(sb.length() -1).append(" LIMIT ").append(rowBounds.getOffset()).append(
           " , ").append(rowBounds.getLimit());
    }
    return sb.toString();
  }

}
