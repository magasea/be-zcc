package com.wensheng.zcc.amc.utils;

import com.wensheng.zcc.amc.controller.helper.QueryParam;
import com.wensheng.zcc.amc.module.dao.helper.PublishStateEnum;
import com.wensheng.zcc.amc.module.dao.helper.QueryParamEnum;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtExample;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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

  public static AmcDebtExample getAmcDebtExampleWithQueryParam(Map<String, Object> queryParam){
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    AmcDebtExample.Criteria criteria = amcDebtExample.createCriteria();
    criteria.andPublishStateNotEqualTo(PublishStateEnum.DELETED.getStatus());
    if(!CollectionUtils.isEmpty(queryParam)){
      for(Entry<String, Object> item: queryParam.entrySet()){

        if(item.getKey().equals(QueryParamEnum.EditStatus.name())){
          criteria.andPublishStateEqualTo((Integer) item.getValue());
        }



        if(item.getKey().equals(QueryParamEnum.AmcContactorId.name())){
          criteria.andAmcContactorIdEqualTo((Long)item.getValue());
        }

        if(item.getKey().equals(QueryParamEnum.Title.name())){
          StringBuilder sb = new StringBuilder().append("%").append(item.getValue()).append("%");
          criteria.andTitleLike(sb.toString());
        }
      }
    }
    return amcDebtExample;
  }


  public static Map<String, Object> getQueryParams(QueryParam queryParam) {
    Map<String, Object> queryParamMap = new HashMap<>();

    if (queryParam.getDebtId() > 0) {
      queryParamMap.put(QueryParamEnum.DebtId.name(), queryParam.getDebtId());
    }

    if (!CollectionUtils.isEmpty(queryParam.getArea()) && queryParam.getArea().size() > 1) {
      List<Long> areas = SQLUtils.areaFilterUpdate4DB(queryParam.getArea());
      queryParamMap.put(QueryParamEnum.Area.name(), areas);
    }
    if (!CollectionUtils.isEmpty(queryParam.getLandArea()) && queryParam.getLandArea().size() > 1) {
      List<Long> landAreas = SQLUtils.areaFilterUpdate4DB(queryParam.getLandArea());

      queryParamMap.put(QueryParamEnum.LandArea.name(), landAreas);
    }
    if (queryParam.getEditStatus() != null && queryParam.getEditStatus() > -1) {
      queryParamMap.put(QueryParamEnum.EditStatus.name(), queryParam.getEditStatus());
    }
    if (queryParam.getSealStatus() != null && queryParam.getSealStatus() > -1) {
      queryParamMap.put(QueryParamEnum.SealedState.name(), queryParam.getSealStatus());
    }
    if (queryParam.getAssetType() != null && queryParam.getAssetType() > -1) {
      queryParamMap.put(QueryParamEnum.AssetType.name(), queryParam.getAssetType());
    }
    if (queryParam.getLocation() != null && !CollectionUtils.isEmpty(queryParam.getLocation())) {
      queryParamMap.put(QueryParamEnum.Location.name(), queryParam.getLocation());
    }

    if (!StringUtils.isEmpty(queryParam.getTitle())) {
      queryParamMap.put(QueryParamEnum.Title.name(), queryParam.getTitle());
    }

    if (!StringUtils.isEmpty(queryParam.getRecommand())) {
      queryParamMap.put(QueryParamEnum.Recommand.name(), queryParam.getRecommand());
    }

    if (queryParam.getAmcContactorId() != null && queryParam.getAmcContactorId() > 0) {
      queryParamMap.put("AmcContactorId", queryParam.getAmcContactorId());
    }
    return queryParamMap;

  }
    public static List<Long> areaFilterUpdate4DB(List<Long> origArea){
      List<Long> result = origArea.stream().map(item -> item*100).collect(Collectors.toList());


      return result;
    }
}
