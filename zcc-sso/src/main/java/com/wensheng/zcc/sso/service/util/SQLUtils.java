package com.wensheng.zcc.sso.service.util;


import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUserExample;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUserExample.Criteria;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.ext.AmcUserExtExample;
import java.util.Map;
import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class SQLUtils {

  public static AmcUserExtExample getAmcUserExample(QueryParam queryParam) {
    AmcUserExtExample amcUserExample = new AmcUserExtExample();
    Criteria criteria = amcUserExample.createCriteria();

    Criteria criteriaOrCName = amcUserExample.or();
    if(!StringUtils.isEmpty(queryParam.getName())){
      StringBuilder sb = new StringBuilder().append("%").append(queryParam.getName()).append("%");
      criteriaOrCName.andUserCnameLike(sb.toString());
      criteria.andUserNameLike(sb.toString());
    }
    if( queryParam.getDeptId() > 0 ){
      criteria.andDeptIdEqualTo( Long.valueOf(queryParam.getDeptId()));
      criteriaOrCName.andDeptIdEqualTo( Long.valueOf(queryParam.getDeptId()));
    }
    if(queryParam.getLocation() > 0){
      criteria.andLocationEqualTo(queryParam.getLocation());
      criteriaOrCName.andLocationEqualTo(queryParam.getLocation());

    }
    if(!StringUtils.isEmpty(queryParam.getMobilePhone())){
      criteria.andMobilePhoneEqualTo(queryParam.getMobilePhone());
      criteriaOrCName.andMobilePhoneEqualTo(queryParam.getMobilePhone());
    }
    return amcUserExample;
  }
  public static String getOrderBy(Map<String, Direction> orderByParam, RowBounds rowBounds) throws Exception {
    if(CollectionUtils.isEmpty(orderByParam)){
      throw new Exception("empty orderByParam");
    }
    StringBuilder sb = new StringBuilder();
    for(Map.Entry<String, Direction> item : orderByParam.entrySet()){
      sb.append(item.getKey()).append(" ").append(item.getValue().name()).append(",");
    }
    if(rowBounds != null && rowBounds.getOffset() >= 0 && rowBounds.getLimit() > 0){
      sb.deleteCharAt(sb.length() -1).append(" LIMIT ").append(rowBounds.getOffset()).append(
          " , ").append(rowBounds.getLimit());
    }
    return sb.toString();
  }

}
