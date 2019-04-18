package com.wensheng.zcc.cust.service.impl;

import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdCmpyMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdPersonMapper;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
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

  @Autowired
  CustTrdPersonMapper custTrdPersonMapper;


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
}
