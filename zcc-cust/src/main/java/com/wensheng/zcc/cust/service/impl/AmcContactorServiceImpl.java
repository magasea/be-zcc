package com.wensheng.zcc.cust.service.impl;

import com.wensheng.zcc.cust.dao.mysql.mapper.CustAmcCmpycontactorMapper;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcCmpycontactor;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcCmpycontactorExample;
import com.wensheng.zcc.cust.service.AmcContactorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmcContactorServiceImpl implements AmcContactorService {

  @Autowired
  CustAmcCmpycontactorMapper custAmcCmpycontactorMapper;

  @Override
  public void createAmcCmpyContactor(CustAmcCmpycontactor custAmcCmpycontactor) {
    custAmcCmpycontactorMapper.insertSelective(custAmcCmpycontactor);
  }

  @Override
  public void updateAmcCmpyContactor(CustAmcCmpycontactor custAmcCmpycontactor) {
    custAmcCmpycontactorMapper.updateByPrimaryKeySelective(custAmcCmpycontactor);
  }

  @Override
  public List<CustAmcCmpycontactor> getCmpyAmcContactor(String cmpyName) {
    CustAmcCmpycontactorExample custAmcCmpycontactorExample = new CustAmcCmpycontactorExample();
    custAmcCmpycontactorExample.createCriteria().andCompanyEqualTo(cmpyName);
    StringBuilder sb = new StringBuilder("%");
    sb.append(cmpyName).append("%");
    custAmcCmpycontactorExample.or().andHistoryCmpyLike(sb.toString());
    List<CustAmcCmpycontactor> custAmcCmpycontactors =
        custAmcCmpycontactorMapper.selectByExample(custAmcCmpycontactorExample);
    return custAmcCmpycontactors;
  }
}
