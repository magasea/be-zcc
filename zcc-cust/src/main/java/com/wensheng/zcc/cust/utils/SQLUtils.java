package com.wensheng.zcc.cust.utils;

import com.wensheng.zcc.cust.controller.helper.QueryParam;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpyExample;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPersonExample;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @author chenwei on 1/7/19
 * @project zcc-backend
 */
public class SQLUtils {

  private final static String ALIAS_CUST_TRD_INFO = "cti";
  private final static String ALIAS_CUST_TRD_CMPY = "ctc";


  public static String getOrderBy(Map<String, Sort.Direction> orderByParam) throws Exception {
    if(CollectionUtils.isEmpty(orderByParam)){
      throw new Exception("empty orderByParam");
    }
    StringBuilder sb = new StringBuilder();
    for(Entry<String, Sort.Direction> item : orderByParam.entrySet()){
      sb.append(item.getKey()).append(" ").append(item.getValue().name()).append(",");
    }
    return sb.substring(0, sb.length() - 1);
  }

  public static String getOrderBy(Map<String, Sort.Direction> orderByParam, RowBounds rowBounds) throws Exception {
    if(CollectionUtils.isEmpty(orderByParam)){
      throw new Exception("empty orderByParam");
    }
    StringBuilder sb = new StringBuilder();
    for(Entry<String, Sort.Direction> item : orderByParam.entrySet()){
      sb.append(item.getKey()).append(" ").append(item.getValue().name()).append(",");
    }
    if(rowBounds != null && rowBounds.getOffset() >= 0 && rowBounds.getLimit() > 0){
       sb.deleteCharAt(sb.length() -1).append(" LIMIT ").append(rowBounds.getOffset()).append(
           " , ").append(rowBounds.getLimit());
    }
    return sb.toString();
  }


  public static CustTrdCmpyExample getCustCmpyTrdExample(QueryParam queryParam) {
    CustTrdCmpyExample custTrdCmpyExample = new CustTrdCmpyExample();
    CustTrdCmpyExample.Criteria criteria = custTrdCmpyExample.createCriteria();

    if(!StringUtils.isEmpty(queryParam.getName())){
        criteria.andCmpyNameLike(String.format("%s%", queryParam.getName()));
    }
    return custTrdCmpyExample;
  }
  public static String getFilterByForCustCmpyTrd(QueryParam queryParam){
    StringBuilder sb = new StringBuilder();
    if(!StringUtils.isEmpty(queryParam.getCity())){
      sb.append(ALIAS_CUST_TRD_INFO);
      if(queryParam.getCity().endsWith("000")){
        sb.append(".").append("trd_city like '").append(queryParam.getCity().substring(0,
            queryParam.getCity().length() -3)).append("%'");
      }else{
        sb.append(".").append("trd_city =" ).append(queryParam.getCity());
      }
    }
    return sb.toString();
  }

  public static CustTrdPersonExample getCustPersonTrdExample(QueryParam queryParam) {
    CustTrdPersonExample custTrdPersonExample = new CustTrdPersonExample();
    CustTrdPersonExample.Criteria criteria = custTrdPersonExample.createCriteria();
    return custTrdPersonExample;
  }
}
