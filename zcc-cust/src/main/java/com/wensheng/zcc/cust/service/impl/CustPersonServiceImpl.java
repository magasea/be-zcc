package com.wensheng.zcc.cust.service.impl;

import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdPersonMapper;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPersonExample;
import com.wensheng.zcc.cust.service.CustPersonService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class CustPersonServiceImpl implements CustPersonService {

  @Autowired
  CustTrdPersonMapper custTrdPersonMapper;

  @Override
  public void createCustPerson(CustTrdPerson custTrdPerson) throws Exception {
    //校验重复


    //补齐时间


    //添加数据库
    custTrdPersonMapper.insert(custTrdPerson);
  }

  @Override
  public void updateCustPerson(CustTrdPerson custTrdPerson) throws Exception {
    //校验重复

    //修改自然人
    custTrdPersonMapper.updateByPrimaryKeySelective(custTrdPerson);
  }

  @Override
  public void mergeCustPerson(List<String> fromPersonIds, String toPersonId) throws Exception {

  }
}
