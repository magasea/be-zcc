package com.wensheng.zcc.cust.service.impl;

import com.wensheng.zcc.cust.dao.mysql.mapper.CustDataDictMapper;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustDataDict;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustDataDictExample;
import com.wensheng.zcc.cust.service.CustDataDictService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustDataDictServiceImpl implements CustDataDictService {

  @Autowired
  CustDataDictMapper custDataDictMapper;

  @Override
  public void createCustDataDict(CustDataDict custDataDict) throws Exception {
    custDataDictMapper.insertSelective(custDataDict);
  }

  @Override
  public void updateCustDataDict(CustDataDict custDataDict) throws Exception {
    custDataDictMapper.updateByPrimaryKeySelective(custDataDict);
  }

  @Override
  public List<CustDataDict> getParentDataDict() throws Exception {
    CustDataDictExample custDataDictExample = new CustDataDictExample();
    custDataDictExample.createCriteria().andPidIsNull();
    custDataDictExample.or().andPidEqualTo(-1L);
    List<CustDataDict> custDataDictList=  custDataDictMapper.selectByExample(custDataDictExample);
    return custDataDictList;
  }

  @Override
  public List<CustDataDict> getDataDictByCode(String code) throws Exception {
    CustDataDictExample custDataDictExample = new CustDataDictExample();
    custDataDictExample.createCriteria().andPcodeEqualTo(code);
    custDataDictExample.setOrderByClause("id desc");
    List<CustDataDict> custDataDictList=  custDataDictMapper.selectByExample(custDataDictExample);
    return custDataDictList;
  }
}
