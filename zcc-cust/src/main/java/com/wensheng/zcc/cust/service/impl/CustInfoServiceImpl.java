package com.wensheng.zcc.cust.service.impl;

import com.google.common.collect.Lists;
import com.wensheng.zcc.cust.controller.helper.QueryParam;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdCmpyMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdPersonMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.ext.CustTrdCmpyExtMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.ext.CustTrdPersonExtMapper;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpyExample;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfo;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPersonExample;
import com.wensheng.zcc.cust.module.dao.mysql.ext.CustTrdCmpyTrdExt;
import com.wensheng.zcc.cust.module.dao.mysql.ext.CustTrdPersonTrdExt;
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
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

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
    String orderBy = SQLUtils.getOrderBy(orderByParam);
    CustTrdCmpyExample custTrdCmpyExample = new CustTrdCmpyExample();
    custTrdCmpyExample.setOrderByClause(orderBy);
    RowBounds rowBounds = new RowBounds(offset, size);

    List<CustTrdCmpyTrdExt> custTrdCmpyTrdExts = custTrdCmpyExtMapper.selectByExampleWithRowbounds(custTrdCmpyExample,
        rowBounds);

    return convertCmpyToVoes(custTrdCmpyTrdExts);
  }

  @Override
  public Long getCmpyTradeCount(QueryParam queryParam) {
    CustTrdCmpyExample custTrdCmpyExample = SQLUtils.getCustTrdExample(queryParam);

    return custTrdCmpyMapper.countByExample(custTrdCmpyExample);
  }

  @Override
  public List<CustTrdInfoVo> queryPersonTradePage(int offset, int size, QueryParam queryParam,
      Map<String, Direction> orderByParam) throws Exception {
    String orderBy = SQLUtils.getOrderBy(orderByParam);
    CustTrdPersonExample custTrdPersonExample = new CustTrdPersonExample();
    custTrdPersonExample.setOrderByClause(orderBy);
    RowBounds rowBounds = new RowBounds(offset, size);

    List<CustTrdPersonTrdExt> custTrdPersonTrdExtList =
        custTrdPersonExtMapper.selectByExampleWithRowbounds(custTrdPersonExample,
        rowBounds);

    return convertPersonToVoes(custTrdPersonTrdExtList);
  }



  @Override
  public Long getPersonTradeCount(QueryParam queryParam) {
    return null;
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
      }
      custTrdInfoVo.setTrdTotalAmount( totalAmount);
      custTrdInfoVo.setIntrestCities(new ArrayList<String>(cities));
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
      }
      custTrdInfoVo.setTrdTotalAmount( totalAmount);
      custTrdInfoVo.setIntrestCities(new ArrayList<String>(cities));
      custTrdInfoVo.setInvestType2Counts(invest2Counts);
      custTrdInfoVos.add(custTrdInfoVo);
    }

    return custTrdInfoVos;
  }


}
