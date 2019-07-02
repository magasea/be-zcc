package com.wensheng.zcc.cust.service.impl;

import com.google.gson.Gson;
import com.wensheng.zcc.cust.controller.helper.QueryParam;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustRegionMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdCmpyMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdPersonMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.ext.CustTrdCmpyExtMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.ext.CustTrdPersonExtMapper;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpyExample;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfo;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPersonExample;
import com.wensheng.zcc.cust.module.dao.mysql.ext.CustTrdCmpyExtExample;
import com.wensheng.zcc.cust.module.dao.mysql.ext.CustTrdCmpyTrdExt;
import com.wensheng.zcc.cust.module.dao.mysql.ext.CustTrdPersonExtExample;
import com.wensheng.zcc.cust.module.dao.mysql.ext.CustTrdPersonTrdExt;
import com.wensheng.zcc.cust.module.helper.InvestTypeEnum;
import com.wensheng.zcc.cust.module.vo.CustTrdInfoExcelVo;
import com.wensheng.zcc.cust.module.vo.CustTrdInfoVo;
import com.wensheng.zcc.cust.service.CustInfoService;
import com.wensheng.zcc.cust.utils.SQLUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author chenwei on 4/17/19
 * @project miniapp-backend
 */
@Service
@Slf4j
public class CustInfoServiceImpl implements CustInfoService {

  @Autowired
  CustTrdCmpyMapper custTrdCmpyMapper;

  @Autowired
  CustTrdPersonMapper custTrdPersonMapper;

  @Autowired
  CustTrdCmpyExtMapper custTrdCmpyExtMapper;

  @Autowired
  CustTrdPersonExtMapper custTrdPersonExtMapper;

  @Autowired
  CustRegionMapper custRegionMapper;

  Gson gson = new Gson();

  @Override
  public CustTrdCmpy addCompany(CustTrdCmpy custTrdCmpy) {
    custTrdCmpyMapper.insertSelective(custTrdCmpy);
    return custTrdCmpy;
  }

  @Override
  public CustTrdPerson addTrdPerson(CustTrdPerson custTrdPerson) {
    custTrdPersonMapper.insertSelective(custTrdPerson);
    return custTrdPerson;
  }

  @Override
  public List<CustTrdPerson> getTrdPersons() {
    return custTrdPersonMapper.selectByExample(null);
  }

  @Override
  public List<CustTrdCmpy> getCmpies() {
    return custTrdCmpyMapper.selectByExample(null);
  }

  @Override
  public List<CustTrdInfoVo> queryCmpyTradePage(int offset, int size, QueryParam queryParam,
      Map<String, Direction> orderByParam) throws Exception {
    List<CustTrdCmpyTrdExt> custTrdCmpyTrdExts = queryCmpy(offset, size, queryParam, orderByParam);
    return convertCmpyToVoes(custTrdCmpyTrdExts);
  }

  private List<CustTrdCmpyTrdExt> queryCmpy(int offset, int size, QueryParam queryParam,
      Map<String, Direction> orderByParam) throws Exception {
    String orderBy = SQLUtils.getOrderBy(orderByParam);
    CustTrdCmpyExtExample custTrdCmpyExtExample = SQLUtils.getCustCmpyTrdExample(queryParam);
    custTrdCmpyExtExample.setOrderByClause(orderBy);
    String filterBy = SQLUtils.getFilterByForCustTrd(queryParam);
    filterBy = filterBy + " and ctc.cmpy_phone > -1 ";

    List<CustTrdCmpyTrdExt> custTrdCmpyTrdExts = new ArrayList<>();
    custTrdCmpyExtExample.setLimitByClause(String.format(" %d , %d ", offset, size));
    List<Long> preGroupResults = new ArrayList<>();
    if(!StringUtils.isEmpty(filterBy)){



      custTrdCmpyExtExample.setFilterByClause(filterBy);
      preGroupResults =
          custTrdCmpyExtMapper.selectByPreFilter(custTrdCmpyExtExample);
      log.info("preGroupResults:{}", gson.toJson(preGroupResults));

    }else{

      preGroupResults =
          custTrdCmpyExtMapper.selectByPreFilter(custTrdCmpyExtExample);
      log.info("preGroupResults:{}", gson.toJson(preGroupResults));
//      CustTrdCmpyExtExample.Criteria criteria = custTrdCmpyExtExample.createCriteria();
//      criteria.andIdIn(preGroupResults);
//      custTrdCmpyExtExample.getOredCriteria().add(criteria);

    }
    StringBuilder sb = new StringBuilder(" ctc.id in ( ");
    preGroupResults.forEach(item -> sb.append(item).append(","));
    sb.setLength(sb.length() -1);
    sb.append(")");
    custTrdCmpyExtExample.setWhereClause(sb.toString());
    custTrdCmpyExtExample.setFilterByClause(null);
    custTrdCmpyTrdExts = custTrdCmpyExtMapper.selectByExample(custTrdCmpyExtExample);
    return custTrdCmpyTrdExts;
  }

  @Override
  public List<CustTrdInfoExcelVo> queryCmpyTrade(int offset, int size, QueryParam queryParam, Map<String, Direction> orderByParam)
      throws Exception {
    List<CustTrdCmpyTrdExt> custTrdCmpyTrdExts = queryCmpy( offset,  size,  queryParam,  orderByParam);
    return convertCmpyToExcelVoes(custTrdCmpyTrdExts);
  }

  private List<CustTrdInfoExcelVo> convertCmpyToExcelVoes(List<CustTrdCmpyTrdExt> custTrdCmpyTrdExts) {
    List<CustTrdInfoExcelVo> custTrdInfoExcelVos = new ArrayList<>();
    for(CustTrdCmpyTrdExt custTrdCmpyTrdExt: custTrdCmpyTrdExts){
      CustTrdInfoExcelVo custTrdInfoExcelVo = new CustTrdInfoExcelVo();
      custTrdInfoExcelVo.setCustId(custTrdCmpyTrdExt.getId());
      custTrdInfoExcelVo.setAddress(String.format("%s;%s",custTrdCmpyTrdExt.getCustTrdCmpy().getCmpyAddr(),
          custTrdCmpyTrdExt.getCustTrdCmpy().getAnnuReptAddr()));
      custTrdInfoExcelVo.setCustName(custTrdCmpyTrdExt.getCustTrdCmpy().getCmpyName());
      custTrdInfoExcelVo.setPhone(String.format("%s;%s",custTrdCmpyTrdExt.getCustTrdCmpy().getCmpyPhone(),
          custTrdCmpyTrdExt.getCustTrdCmpy().getAnnuReptPhone()));
      custTrdInfoExcelVo.setTrdCount(custTrdCmpyTrdExt.getCustTrdInfoList().size());
      Long totalAmount = 0L;

      Map<String, Integer> invest2Counts = new HashMap<>();
      Map<String, Integer> city2Counts = new HashMap<>();
      String cityName = null;
      String trdTypeName = null;
      for(CustTrdInfo custTrdInfo: custTrdCmpyTrdExt.getCustTrdInfoList()){
        totalAmount += custTrdInfo.getTotalAmount();

        if(custTrdInfo.getTrdType() == null){
          continue;
        }
        trdTypeName = InvestTypeEnum.lookupByIdUntil(custTrdInfo.getTrdType()).getName();
        if(!invest2Counts.containsKey(trdTypeName)){
          invest2Counts.put(trdTypeName, 1);
        }else{
          invest2Counts.put(trdTypeName, invest2Counts.get(trdTypeName)+1);
        }
        cityName = custRegionMapper.selectByPrimaryKey(Long.valueOf(custTrdInfo.getTrdCity())).getName();
        if(!city2Counts.containsKey(cityName)){
          city2Counts.put(cityName, 1);
        }else {
          city2Counts.put(cityName, city2Counts.get(cityName)+1);
        }
      }
      custTrdInfoExcelVo.setTrdTotalAmount( totalAmount);
      custTrdInfoExcelVo.setIntrestCities(city2Counts);
      custTrdInfoExcelVo.setInvestType2Counts(invest2Counts);
      custTrdInfoExcelVos.add(custTrdInfoExcelVo);
    }

    return custTrdInfoExcelVos;
  }

  @Override
  public Long getCmpyTradeCount(QueryParam queryParam) {
    CustTrdCmpyExample custTrdCmpyExample = SQLUtils.getCustCmpyTrdExample(queryParam);
    String filterBy = SQLUtils.getFilterByForCustTrd(queryParam);
    CustTrdCmpyExtExample custTrdCmpyExtExample = new CustTrdCmpyExtExample();
    custTrdCmpyExample.getOredCriteria().forEach(item -> custTrdCmpyExtExample.getOredCriteria().add(item));
    custTrdCmpyExtExample.setFilterByClause(filterBy);
    return custTrdCmpyExtMapper.countByFilter(custTrdCmpyExtExample);




  }

  @Override
  public List<CustTrdInfoVo> queryPersonTradePage(int offset, int size, QueryParam queryParam,
      Map<String, Direction> orderByParam) throws Exception {


    List<CustTrdPersonTrdExt> custTrdPersonTrdExts = queryPerson(offset, size, queryParam, orderByParam);


    return convertPersonToVoes(custTrdPersonTrdExts);
  }

  private List<CustTrdPersonTrdExt> queryPerson(int offset, int size, QueryParam queryParam,
      Map<String, Direction> orderByParam) throws Exception {
    String orderBy = SQLUtils.getOrderBy(orderByParam);
    CustTrdPersonExtExample custTrdPersonExtExample = SQLUtils.getCustPersonTrdExample(queryParam);
    custTrdPersonExtExample.setOrderByClause(orderBy);
    RowBounds rowBounds = new RowBounds(offset, size);
    String filterBy = SQLUtils.getFilterByForCustTrd(queryParam);
    filterBy = filterBy + " and ctp.mobile_num > -1 ";
    if(!StringUtils.isEmpty(filterBy)) {
      custTrdPersonExtExample.setFilterByClause(filterBy);
    }
    custTrdPersonExtExample.setLimitByClause(String.format(" %d , %d ", offset, size));
    List<CustTrdPersonTrdExt> custTrdPersonTrdExtList = new ArrayList<>();
    List<Long> ids = custTrdPersonExtMapper.selectByPreFilter(custTrdPersonExtExample);
    StringBuilder sb = new StringBuilder(" ctp.id in ( ");
    ids.forEach(item -> sb.append(item).append(","));
    sb.setLength(sb.length() -1);
    sb.append(")");
    custTrdPersonExtExample.setWhereClause(sb.toString());
    custTrdPersonExtExample.setFilterByClause(null);

    custTrdPersonTrdExtList = custTrdPersonExtMapper.selectByExample(custTrdPersonExtExample);
    return  custTrdPersonTrdExtList;
  }

  @Override
  public List<CustTrdInfoExcelVo> queryPersonTrade(int offset, int size,  QueryParam queryParam, Map<String,
      Direction> orderByParam)
      throws Exception {
    List<CustTrdPersonTrdExt> custTrdPersonTrdExts = queryPerson(offset, size, queryParam, orderByParam);


    return convertPersonToExcelVoes(custTrdPersonTrdExts);
  }

  private List<CustTrdInfoExcelVo> convertPersonToExcelVoes(List<CustTrdPersonTrdExt> custTrdPersonTrdExts) {
    List<CustTrdInfoExcelVo> custTrdInfoExcelVos = new ArrayList<>();
    for(CustTrdPersonTrdExt custTrdPersonTrdExt: custTrdPersonTrdExts){
      CustTrdInfoExcelVo custTrdInfoExcelVo = new CustTrdInfoExcelVo();
      custTrdInfoExcelVo.setCustId(custTrdPersonTrdExt.getId());
      custTrdInfoExcelVo.setAddress(String.format("%s",custTrdPersonTrdExt.getCustTrdPerson().getAddr()));
      custTrdInfoExcelVo.setCustName(custTrdPersonTrdExt.getCustTrdPerson().getName());
      custTrdInfoExcelVo.setPhone(String.format("%s;%s",
          custTrdPersonTrdExt.getCustTrdPerson().getMobileNum(),
          custTrdPersonTrdExt.getCustTrdPerson().getTelNum()));
      custTrdInfoExcelVo.setTrdCount(custTrdPersonTrdExt.getCustTrdInfoList().size());
      Long totalAmount = 0L;
      Map<String, Integer> invest2Counts = new HashMap<>();
      Map<String, Integer> city2Counts = new HashMap<>();
      String cityName;
      String trdTypeName;
      for(CustTrdInfo custTrdInfo: custTrdPersonTrdExt.getCustTrdInfoList()){
        totalAmount += custTrdInfo.getTotalAmount();
        if(custTrdInfo.getTrdType() == null){
          continue;
        }
        trdTypeName = InvestTypeEnum.lookupByIdUntil(custTrdInfo.getTrdType()).getName();
        cityName = custRegionMapper.selectByPrimaryKey(Long.valueOf(custTrdInfo.getTrdCity())).getName();
        if(!invest2Counts.containsKey(trdTypeName)){
          invest2Counts.put(trdTypeName, 1);
        }else{
          invest2Counts.put(trdTypeName, invest2Counts.get(trdTypeName)+1);
        }
        if(!city2Counts.containsKey(cityName)){
          city2Counts.put(cityName, 1);
        }else {
          city2Counts.put(cityName, city2Counts.get(cityName)+1);
        }
      }
      custTrdInfoExcelVo.setTrdTotalAmount( totalAmount);
      custTrdInfoExcelVo.setIntrestCities(city2Counts);
      custTrdInfoExcelVo.setInvestType2Counts(invest2Counts);
      custTrdInfoExcelVos.add(custTrdInfoExcelVo);
    }

    return custTrdInfoExcelVos;
  }


  @Override
  public Long getPersonTradeCount(QueryParam queryParam) {
    CustTrdPersonExample custTrdPersonExample = SQLUtils.getCustPersonTrdExample(queryParam);



    String filterBy = SQLUtils.getFilterByForCustTrd(queryParam);
    CustTrdPersonExtExample custTrdPersonExtExample = new CustTrdPersonExtExample();
    custTrdPersonExample.getOredCriteria().forEach(item -> custTrdPersonExtExample.getOredCriteria().add(item));
    custTrdPersonExtExample.setFilterByClause(filterBy);
    return custTrdPersonExtMapper.countByFilter(custTrdPersonExtExample);
  }

  @Override
  public CustTrdCmpy getCompany(Long companyId) {

    return custTrdCmpyMapper.selectByPrimaryKey(companyId);

  }

  @Override
  public CustTrdPerson getPerson(Long personId) {
    return custTrdPersonMapper.selectByPrimaryKey(personId);
  }

  private List<CustTrdInfoVo> convertCmpyToVoes(List<CustTrdCmpyTrdExt> custTrdCmpyTrdExts) {
    List<CustTrdInfoVo> custTrdInfoVos = new ArrayList<>();
    for(CustTrdCmpyTrdExt custTrdCmpyTrdExt: custTrdCmpyTrdExts){
      CustTrdInfoVo custTrdInfoVo = new CustTrdInfoVo();
      custTrdInfoVo.setCustId(custTrdCmpyTrdExt.getId());
      custTrdInfoVo.setAddress(String.format("%s;%s",custTrdCmpyTrdExt.getCustTrdCmpy().getCmpyAddr(),
          custTrdCmpyTrdExt.getCustTrdCmpy().getAnnuReptAddr()));
      custTrdInfoVo.setCustName(custTrdCmpyTrdExt.getCustTrdCmpy().getCmpyName());
      custTrdInfoVo.setPhone(String.format("%s;%s",custTrdCmpyTrdExt.getCustTrdCmpy().getCmpyPhone(),
          custTrdCmpyTrdExt.getCustTrdCmpy().getAnnuReptPhone()));
      custTrdInfoVo.setTrdCount(custTrdCmpyTrdExt.getCustTrdInfoList().size());
      Long totalAmount = 0L;
      Set<String> cities = new HashSet<>();
      Map<Integer, Integer> invest2Counts = new HashMap<>();
      Map<String, Integer> city2Counts = new HashMap<>();
      for(CustTrdInfo custTrdInfo: custTrdCmpyTrdExt.getCustTrdInfoList()){
        totalAmount += custTrdInfo.getTotalAmount();
        cities.add(custTrdInfo.getTrdCity());
        if(custTrdInfo.getTrdType() == null){
          continue;
        }
        if(!invest2Counts.containsKey(custTrdInfo.getTrdType())){
          invest2Counts.put(custTrdInfo.getTrdType(), 1);
        }else{
          invest2Counts.put(custTrdInfo.getTrdType(), invest2Counts.get(custTrdInfo.getTrdType())+1);
        }

        if(!city2Counts.containsKey(custTrdInfo.getTrdCity())){
          city2Counts.put(custTrdInfo.getTrdCity(), 1);
        }else {
          city2Counts.put(custTrdInfo.getTrdCity(), city2Counts.get(custTrdInfo.getTrdCity())+1);
        }
      }
      custTrdInfoVo.setTrdTotalAmount( totalAmount);
      custTrdInfoVo.setIntrestCities(city2Counts);
      custTrdInfoVo.setInvestType2Counts(invest2Counts);
      custTrdInfoVos.add(custTrdInfoVo);
    }

    return custTrdInfoVos;
  }

  private List<CustTrdInfoVo> convertPersonToVoes(List<CustTrdPersonTrdExt> custTrdPersonTrdExtList) {
    List<CustTrdInfoVo> custTrdInfoVos = new ArrayList<>();
    for(CustTrdPersonTrdExt custTrdPersonTrdExt: custTrdPersonTrdExtList){
      CustTrdInfoVo custTrdInfoVo = new CustTrdInfoVo();
      custTrdInfoVo.setCustId(custTrdPersonTrdExt.getId());
      custTrdInfoVo.setAddress(String.format("%s",custTrdPersonTrdExt.getCustTrdPerson().getAddr()));
      custTrdInfoVo.setCustName(custTrdPersonTrdExt.getCustTrdPerson().getName());
      custTrdInfoVo.setPhone(String.format("%s;%s",
          custTrdPersonTrdExt.getCustTrdPerson().getMobileNum(),
          custTrdPersonTrdExt.getCustTrdPerson().getTelNum()));
      custTrdInfoVo.setTrdCount(custTrdPersonTrdExt.getCustTrdInfoList().size());
      Long totalAmount = 0L;
      Set<String> cities = new HashSet<>();
      Map<Integer, Integer> invest2Counts = new HashMap<>();
      Map<String, Integer> city2Counts = new HashMap<>();
      for(CustTrdInfo custTrdInfo: custTrdPersonTrdExt.getCustTrdInfoList()){
        totalAmount += custTrdInfo.getTotalAmount();
        cities.add(custTrdInfo.getTrdCity());
        if(custTrdInfo.getTrdType() == null){
          continue;
        }
        if(!invest2Counts.containsKey(custTrdInfo.getTrdType())){
          invest2Counts.put(custTrdInfo.getTrdType(), 1);
        }else{
          invest2Counts.put(custTrdInfo.getTrdType(), invest2Counts.get(custTrdInfo.getTrdType())+1);
        }
        if(!city2Counts.containsKey(custTrdInfo.getTrdCity())){
          city2Counts.put(custTrdInfo.getTrdCity(), 1);
        }else {
          city2Counts.put(custTrdInfo.getTrdCity(), city2Counts.get(custTrdInfo.getTrdCity())+1);
        }
      }
      custTrdInfoVo.setTrdTotalAmount( totalAmount);
      custTrdInfoVo.setIntrestCities(city2Counts);
      custTrdInfoVo.setInvestType2Counts(invest2Counts);
      custTrdInfoVos.add(custTrdInfoVo);
    }

    return custTrdInfoVos;
  }


}
