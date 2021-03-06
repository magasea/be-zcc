package com.wensheng.zcc.cust.utils;

import com.wensheng.zcc.cust.controller.helper.QueryParam;
import com.wensheng.zcc.cust.module.dao.mysql.ext.CustTrdCmpyExtExample;
import com.wensheng.zcc.cust.module.dao.mysql.ext.CustTrdPersonExtExample;
import com.wensheng.zcc.cust.module.helper.InvestScaleEnum;
import com.wensheng.zcc.cust.module.helper.SelectCustTypeEnum;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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


  public static CustTrdCmpyExtExample getCustCmpyTrdExample(QueryParam queryParam) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    CustTrdCmpyExtExample custTrdCmpyExtExample = new CustTrdCmpyExtExample();
    CustTrdCmpyExtExample.Criteria criteria = custTrdCmpyExtExample.createCriteria();

    if(!StringUtils.isEmpty(queryParam.getName())){
        criteria.andCmpyNameLike(new StringBuilder("%").append(StringUtils.trimWhitespace(queryParam.getName())).append("%").toString() );
    }
    if(!CollectionUtils.isEmpty(queryParam.getCustCity())){
      criteria.andCmpyProvinceIn( queryParam.getCustCity());
    }
    if(null != queryParam.getLatestStartDay()){
      Date latestStartDay;
      try {
        latestStartDay = formatter.parse(String.format("%s 00:00:00",queryParam.getLatestStartDay()));
      } catch (ParseException e) {
        throw new RuntimeException(String.format("入参时间格式不对"));
      }
      criteria.andUpdateTimeGreaterThanOrEqualTo(latestStartDay);
    }
    if (null != queryParam.getLatestEndDay()){
      Date latestEndDay;
      try {
        latestEndDay = formatter.parse(String.format("%s 23:59:59",queryParam.getLatestEndDay()));
      } catch (ParseException e) {
        throw new RuntimeException(String.format("入参时间格式不对"));
      }
      criteria.andUpdateTimeGreaterThanOrEqualTo(latestEndDay);
    }
    return custTrdCmpyExtExample;
  }

  public static String getCustWhereClause(QueryParam queryParam) {
    StringBuilder sb = new StringBuilder();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    //选择更新时间
    if (!StringUtils.isEmpty(queryParam.getUpdateStartDay())) {
      try {
        formatter.parse(queryParam.getUpdateStartDay());
      } catch (Exception e) {
        throw new RuntimeException(String.format("入参时间格式不对"));
      }
      sb.append(" and ");
      sb.append("ctp.update_time >= ")
          .append(String.format("'%s 00:00:00'", queryParam.getUpdateStartDay()));
    }
    if (!StringUtils.isEmpty(queryParam.getUpdateEndDay())) {
      try {
        formatter.parse(queryParam.getUpdateEndDay());
      } catch (Exception e) {
        throw new RuntimeException(String.format("入参时间格式不对"));
      }
      sb.append(" and ");
      sb.append(" ctp.update_time < ")
          .append(String.format("'%s 23:59:59'", queryParam.getUpdateEndDay()));
    }
    //选择创建时间
    if (!StringUtils.isEmpty(queryParam.getCreateStartDay())) {
      try {
        formatter.parse(queryParam.getCreateStartDay());
      } catch (Exception e) {
        throw new RuntimeException(String.format("入参时间格式不对"));
      }
      sb.append(" and ");
      sb.append("ctp.create_time >= ")
          .append(String.format("'%s 00:00:00'", queryParam.getCreateStartDay()));
    }
    if (!StringUtils.isEmpty(queryParam.getCreateEndDay())) {
      try {
        formatter.parse(queryParam.getCreateEndDay());
      } catch (Exception e) {
        throw new RuntimeException(String.format("入参时间格式不对"));
      }
      sb.append(" and ");
      sb.append(" ctp.create_time < ")
          .append(String.format("'%s 23:59:59'", queryParam.getCreateEndDay()));
    }

    //最新更改
    if(SelectCustTypeEnum.UPDATE.getEname().equals(queryParam.getSelectCustType())){
      if(!StringUtils.isEmpty(queryParam.getLatestStartDay())){
        try {
          formatter.parse(queryParam.getLatestStartDay());
        }catch (Exception e){
          throw new RuntimeException(String.format("入参时间格式不对"));
        }
        sb.append(" and ");
        sb.append("ctp.update_time >= ").append(String.format("'%s 00:00:00'", queryParam.getLatestStartDay()));
      }
      if(!StringUtils.isEmpty(queryParam.getLatestEndDay())){
        try {
          formatter.parse(queryParam.getLatestEndDay());
        }catch (Exception e){
          throw new RuntimeException(String.format("入参时间格式不对"));
        }
        sb.append(" and ");
        sb.append(" ctp.update_time < ").append(String.format("'%s 23:59:59'", queryParam.getLatestEndDay()));
      }
    }
    //最新创建
    if(SelectCustTypeEnum.CREATE.getEname().equals(queryParam.getSelectCustType())){
      if(!StringUtils.isEmpty(queryParam.getLatestStartDay())){
        try {
          formatter.parse(queryParam.getLatestStartDay());
        }catch (Exception e){
          throw new RuntimeException(String.format("入参时间格式不对"));
        }
        sb.append(" and ");
        sb.append("ctp.create_time >= ").append(String.format("'%s 00:00:00'", queryParam.getLatestStartDay()));
      }
      if(!StringUtils.isEmpty(queryParam.getLatestEndDay())){
        try {
          formatter.parse(queryParam.getLatestEndDay());
        }catch (Exception e){
          throw new RuntimeException(String.format("入参时间格式不对"));
        }
        sb.append(" and ");
        sb.append(" ctp.create_time < ").append(String.format("'%s 23:59:59'", queryParam.getLatestEndDay()));
      }
    }
    //最新交易
    if(SelectCustTypeEnum.TRADE.getEname().equals(queryParam.getSelectCustType())){
      if(!StringUtils.isEmpty(queryParam.getLatestStartDay())){
        try {
          formatter.parse(queryParam.getLatestStartDay());
        }catch (Exception e){
          throw new RuntimeException(String.format("入参时间格式不对"));
        }
        sb.append(" and ");
        sb.append("cti.update_time >= ").append(String.format("'%s 00:00:00'", queryParam.getLatestStartDay()));
      }
      if(!StringUtils.isEmpty(queryParam.getLatestEndDay())){
        try {
          formatter.parse(queryParam.getLatestEndDay());
        }catch (Exception e){
          throw new RuntimeException(String.format("入参时间格式不对"));
        }
        sb.append(" and ");
        sb.append(" cti.update_time < ").append(String.format("'%s 23:59:59'", queryParam.getLatestEndDay()));
      }
    }
    return sb.toString();
  }

  public static String getFilterByForCustTrd(QueryParam queryParam){
    StringBuilder sb = new StringBuilder();
    if(!StringUtils.isEmpty(queryParam.getCity())){
      sb.append(" and ");
      sb.append(ALIAS_CUST_TRD_INFO);
      if(queryParam.getCity().endsWith("000")){
        String locationCode = queryParam.getCity();
        for(int idx = locationCode.length() -1; idx >= 0; idx--){
          if(Character.compare(locationCode.charAt(idx), '0') == 0){
            continue;
          }else{
            locationCode = locationCode.substring(0, idx+1);
            if(locationCode.length()%2!=0){
              locationCode=locationCode+"0";
            }
            break;
          }
        }
        sb.append(".").append("debt_city like '").append(locationCode).append("%'");
      }else{
        sb.append(".").append("debt_city =" ).append(queryParam.getCity());
      }
    }
    if(!CollectionUtils.isEmpty(queryParam.getInvestDebtScales())){
      if(queryParam.getInvestDebtScales().size() == 1 && queryParam.getInvestDebtScales().get(0) == InvestScaleEnum.INVEST_SCALE_LVL2.getId() ){
        sb.append(" and ( cti.total_debt_amount < 10000000 )" );
      }else if(queryParam.getInvestDebtScales().size() == 1 && queryParam.getInvestDebtScales().get(0) == InvestScaleEnum.INVEST_SCALE_LVL5.getId()){
        sb.append(" and ( cti.total_debt_amount >= 100000000 )" );
      }else{
        InvestScaleEnum leftScale , rightScale;

        if(InvestScaleEnum.lookupByIdUntil(queryParam.getInvestDebtScales().get(0)).getAmount() < InvestScaleEnum.lookupByIdUntil(queryParam.getInvestDebtScales().get(1)).getAmount()){
          leftScale = InvestScaleEnum.lookupByIdUntil(queryParam.getInvestDebtScales().get(0));
          rightScale = InvestScaleEnum.lookupByIdUntil(queryParam.getInvestDebtScales().get(1));
        }else{
          leftScale = InvestScaleEnum.lookupByIdUntil(queryParam.getInvestDebtScales().get(1));
          rightScale = InvestScaleEnum.lookupByIdUntil(queryParam.getInvestDebtScales().get(2));
        }
        sb.append(" and  ( cti.total_debt_amount between ").append(leftScale.getAmount()).append(" and ").append(rightScale.getAmount()).append(")");
      }
    }

    if(!CollectionUtils.isEmpty(queryParam.getInvestTrdScales())){
      if(queryParam.getInvestTrdScales().size() == 1 && queryParam.getInvestTrdScales().get(0) == InvestScaleEnum.INVEST_SCALE_LVL2.getId() ){
        sb.append(" and ( cti.trd_amount < 1000000000 )" );
      }else if(queryParam.getInvestTrdScales().size() == 1 && queryParam.getInvestTrdScales().get(0) == InvestScaleEnum.INVEST_SCALE_LVL5.getId()){
        sb.append(" and ( cti.trd_amount >= 10000000000 )" );
      }else{
        InvestScaleEnum leftScale , rightScale;

        if(InvestScaleEnum.lookupByIdUntil(queryParam.getInvestTrdScales().get(0)).getAmount() < InvestScaleEnum.lookupByIdUntil(queryParam.getInvestTrdScales().get(1)).getAmount()){
          leftScale = InvestScaleEnum.lookupByIdUntil(queryParam.getInvestTrdScales().get(0));
          rightScale = InvestScaleEnum.lookupByIdUntil(queryParam.getInvestTrdScales().get(1));
        }else{
          leftScale = InvestScaleEnum.lookupByIdUntil(queryParam.getInvestTrdScales().get(1));
          rightScale = InvestScaleEnum.lookupByIdUntil(queryParam.getInvestTrdScales().get(2));
        }
        sb.append(" and  ( cti.trd_amount between ").append(leftScale.getAmount()*100).append(" and ").append(rightScale.getAmount()*100).append(")");
      }
    }

    if(queryParam.getItemType() != null && queryParam.getItemType() != -1){
      sb.append(" and ").append(" cti.item_type = ").append(queryParam.getItemType());
    }
    if(queryParam.getTrdType() != null && queryParam.getTrdType() != -1){
      sb.append(" and ").append(" cti.trd_type = ").append(queryParam.getTrdType());
    }
    return sb.toString();
  }

  public static String getTrdCmpyExtWhereClause(QueryParam queryParam){
    StringBuilder sb = new StringBuilder();
    if(!StringUtils.isEmpty(queryParam.getName())){
      if(sb.length()>0){
        sb.append(" and ");
      }
      sb.append("ctc.cmpy_name like ").append(String.format("%s%s%s","'%",queryParam.getName(),"%'"));
    }
    if(!CollectionUtils.isEmpty(queryParam.getCustCity())){
      if(sb.length()>0){
        sb.append(" and ");
      }
      List<String> custCity =queryParam.getCustCity();
      StringBuffer proviceSb = new StringBuffer();
      for(String provice : custCity){
        if(proviceSb.length()>0){
          proviceSb.append(",");
        }
        proviceSb.append("'");
        proviceSb.append(provice);
        proviceSb.append("'");
      }
      sb.append("ctc.cmpy_province in ").append("(").append(proviceSb.toString()).append(")");
    }
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    //选择更新时间
    if(!StringUtils.isEmpty(queryParam.getUpdateStartDay())){
      try {
        formatter.parse(queryParam.getUpdateStartDay());
      }catch (Exception e){
        throw new RuntimeException(String.format("入参时间格式不对"));
      }
      if(sb.length()>0){
        sb.append(" and ");
      }
      sb.append("ctc.update_time >= ").append(String.format("'%s 00:00:00'", queryParam.getUpdateStartDay()));
    }
    if(!StringUtils.isEmpty(queryParam.getUpdateEndDay())){
      try {
        formatter.parse(queryParam.getUpdateEndDay());
      }catch (Exception e){
        throw new RuntimeException(String.format("入参时间格式不对"));
      }
      if(sb.length() > 0){
        sb.append(" and ");
      }
      sb.append(" ctc.update_time < ").append(String.format("'%s 23:59:59'", queryParam.getUpdateEndDay()));
    }

    //选择创建时间
    if(!StringUtils.isEmpty(queryParam.getCreateStartDay())){
      try {
        formatter.parse(queryParam.getCreateStartDay());
      }catch (Exception e){
        throw new RuntimeException(String.format("入参时间格式不对"));
      }
      if(sb.length()>0){
        sb.append(" and ");
      }
      sb.append("ctc.create_time >= ").append(String.format("'%s 00:00:00'", queryParam.getCreateStartDay()));
    }
    if(!StringUtils.isEmpty(queryParam.getCreateEndDay())){
      try {
        formatter.parse(queryParam.getCreateEndDay());
      }catch (Exception e){
        throw new RuntimeException(String.format("入参时间格式不对"));
      }
      if(sb.length() > 0){
        sb.append(" and ");
      }
      sb.append(" ctc.create_time < ").append(String.format("'%s 23:59:59'", queryParam.getCreateEndDay()));
    }

    //最新更改
    if(SelectCustTypeEnum.UPDATE.getEname().equals(queryParam.getSelectCustType())){
      if(!StringUtils.isEmpty(queryParam.getLatestStartDay())){
        try {
          formatter.parse(queryParam.getLatestStartDay());
        }catch (Exception e){
          throw new RuntimeException(String.format("入参时间格式不对"));
        }
        if(sb.length()>0){
          sb.append(" and ");
        }
        sb.append("ctc.update_time >= ").append(String.format("'%s 00:00:00'", queryParam.getLatestStartDay()));
      }
      if(!StringUtils.isEmpty(queryParam.getLatestEndDay())){
        try {
          formatter.parse(queryParam.getLatestEndDay());
        }catch (Exception e){
          throw new RuntimeException(String.format("入参时间格式不对"));
        }
        if(sb.length() > 0){
          sb.append(" and ");
        }
        sb.append(" ctc.update_time < ").append(String.format("'%s 23:59:59'", queryParam.getLatestEndDay()));
      }
    }

    //最新创建
    if(SelectCustTypeEnum.CREATE.getEname().equals(queryParam.getSelectCustType())){
      if(!StringUtils.isEmpty(queryParam.getLatestStartDay())){
        try {
          formatter.parse(queryParam.getLatestStartDay());
        }catch (Exception e){
          throw new RuntimeException(String.format("入参时间格式不对"));
        }
        if(sb.length()>0){
          sb.append(" and ");
        }
        sb.append("ctc.create_time >= ").append(String.format("'%s 00:00:00'", queryParam.getLatestStartDay()));
      }
      if(!StringUtils.isEmpty(queryParam.getLatestEndDay())){
        try {
          formatter.parse(queryParam.getLatestEndDay());
        }catch (Exception e){
          throw new RuntimeException(String.format("入参时间格式不对"));
        }
        if(sb.length() > 0){
          sb.append(" and ");
        }
        sb.append(" ctc.create_time < ").append(String.format("'%s 23:59:59'", queryParam.getLatestEndDay()));
      }
    }

    //最新交易
    if(SelectCustTypeEnum.TRADE.getEname().equals(queryParam.getSelectCustType())){
      if(!StringUtils.isEmpty(queryParam.getLatestStartDay())){
        try {
          formatter.parse(queryParam.getLatestStartDay());
        }catch (Exception e){
          throw new RuntimeException(String.format("入参时间格式不对"));
        }
        if(sb.length()>0){
          sb.append(" and ");
        }
        sb.append("cti.update_time >= ").append(String.format("'%s 00:00:00'", queryParam.getLatestStartDay()));
      }
      if(!StringUtils.isEmpty(queryParam.getLatestEndDay())){
        try {
          formatter.parse(queryParam.getLatestEndDay());
        }catch (Exception e){
          throw new RuntimeException(String.format("入参时间格式不对"));
        }
        if(sb.length() > 0){
          sb.append(" and ");
        }
        sb.append(" cti.update_time < ").append(String.format("'%s 23:59:59'", queryParam.getLatestEndDay()));
      }
    }
    return sb.toString();
  }

  public static CustTrdPersonExtExample getCustPersonTrdExample(QueryParam queryParam) {
    CustTrdPersonExtExample custTrdPersonExtExample = new CustTrdPersonExtExample();
    CustTrdPersonExtExample.Criteria criteria = custTrdPersonExtExample.createCriteria();
    criteria.andStatusNotEqualTo(2);
    if(!StringUtils.isEmpty(queryParam.getName())){
      criteria.andNameLike(new StringBuilder("%").append(StringUtils.trimWhitespace(queryParam.getName())).append(
          "%").toString() );
    }
//    if(!CollectionUtils.isEmpty(queryParam.getCustCity())){
//      criteria.andProvinceIn(queryParam.getCustCity());
//    }
    return custTrdPersonExtExample;
  }
}
