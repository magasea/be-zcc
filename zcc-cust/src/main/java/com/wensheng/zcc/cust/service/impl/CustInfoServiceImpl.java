package com.wensheng.zcc.cust.service.impl;

import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdCmpyMapper;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.service.CustInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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


  @Override
  public CustTrdCmpy addCompany(CustTrdCmpy custTrdCmpy) {
    custTrdCmpyMapper.insertSelective(custTrdCmpy);
    return custTrdCmpy;
  }
}
