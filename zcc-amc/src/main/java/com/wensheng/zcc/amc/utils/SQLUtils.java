package com.wensheng.zcc.amc.utils;

import com.wensheng.zcc.amc.controller.helper.QueryParam;
import com.wensheng.zcc.amc.module.dao.helper.PublishStateEnum;
import com.wensheng.zcc.amc.module.dao.helper.QueryParamEnum;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtExample;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
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



  public static Map<String, Object> getQueryParams(QueryParam queryParam) {
    Map<String, Object> queryParamMap = new HashMap<>();

    if (queryParam.getDebtId() > 0) {
      queryParamMap.put(QueryParamEnum.DebtId.name(), queryParam.getDebtId());
    }

    if (!CollectionUtils.isEmpty(queryParam.getArea()) && queryParam.getArea().size() > 1) {
      List<Long> areas = SQLUtils.areaFilterUpdate4DB(queryParam.getArea());
      queryParamMap.put(QueryParamEnum.Area.name(), areas);
    }
    if (!CollectionUtils.isEmpty(queryParam.getBaseAmount()) && queryParam.getBaseAmount().size() > 1) {
      List<Long> amounts = SQLUtils.amountFilterUpdate4DB(queryParam.getBaseAmount());
      queryParamMap.put(QueryParamEnum.BaseAmount.name(), amounts);
    }
    if (!CollectionUtils.isEmpty(queryParam.getLandArea()) && queryParam.getLandArea().size() > 1) {
      List<Long> landAreas = SQLUtils.areaFilterUpdate4DB(queryParam.getLandArea());

      queryParamMap.put(QueryParamEnum.LandArea.name(), landAreas);
    }

    if(queryParam.getPublishStates() != null && !CollectionUtils.isEmpty(queryParam.getPublishStates())){
      queryParamMap.put(QueryParamEnum.PublishStates.name(), queryParam.getPublishStates());
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

    if (!CollectionUtils.isEmpty(queryParam.getRecommand())) {
      queryParamMap.put(QueryParamEnum.Recommand.name(), queryParam.getRecommand());
    }

    if (queryParam.getAmcContactorId() != null && queryParam.getAmcContactorId() > 0) {
      queryParamMap.put("AmcContactorId", queryParam.getAmcContactorId());
    }
    if(queryParam.getCourtId() != null && queryParam.getCourtId() > 0){
      queryParamMap.put(QueryParamEnum.CourtId.name(), queryParam.getCourtId());
    }
    if(queryParam.getValuation() != null && !CollectionUtils.isEmpty(queryParam.getValuation())){
      queryParamMap.put(QueryParamEnum.Valuation.name(), queryParam.getValuation());
    }
    if(queryParam.getDebtPackIds() != null && !CollectionUtils.isEmpty(queryParam.getDebtPackIds())){
      queryParamMap.put(QueryParamEnum.DebtPackId.name(), queryParam.getDebtPackIds());
    }
    return queryParamMap;

  }
    public static List<Long> areaFilterUpdate4DB(List<Long> origArea){
      List<Long> result = origArea.stream().map(item -> item*100).collect(Collectors.toList());


      return result;
    }

  public static List<Long> amountFilterUpdate4DB(List<Long> origAmount){
    List<Long> result = origAmount.stream().map(item ->  item > 0?item*100:-1).collect(Collectors.toList());


    return result;
  }
}
