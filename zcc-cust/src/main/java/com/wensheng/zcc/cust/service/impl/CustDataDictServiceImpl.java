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

  final Integer DataDictDeleteStatus = 0;

  @Autowired
  CustDataDictMapper custDataDictMapper;

  @Override
  public void createCustDataDict(CustDataDict custDataDict) throws Exception {
    CustDataDictExample custDataDictExample = new CustDataDictExample();
    custDataDictExample.createCriteria().andPcodeEqualTo(custDataDict.getPcode());
    List<CustDataDict> custDataDictList=  custDataDictMapper.selectByExample(custDataDictExample);
    custDataDict.setValue((custDataDictList.size()+1)+"");
    custDataDictMapper.insertSelective(custDataDict);
  }

  @Override
  public void updateCustDataDict(CustDataDict custDataDict) throws Exception {
    CustDataDict custDataDictNew = new CustDataDict();
    custDataDictNew.setId(custDataDict.getId());
    custDataDictNew.setStatus(custDataDict.getStatus());
    custDataDictNew.setName(custDataDict.getName());
    custDataDictMapper.updateByPrimaryKeySelective(custDataDictNew);
  }

  @Override
  public List<CustDataDict> getParentDataDict() throws Exception {
    CustDataDictExample custDataDictExample = new CustDataDictExample();
    custDataDictExample.createCriteria().andPidIsNull().andDelNotEqualTo(DataDictDeleteStatus);
    custDataDictExample.or().andPidEqualTo(-1L);
    List<CustDataDict> custDataDictList=  custDataDictMapper.selectByExample(custDataDictExample);
    return custDataDictList;
  }

  @Override
  public List<CustDataDict> getDataDictByCode(String code) throws Exception {
    CustDataDictExample custDataDictExample = new CustDataDictExample();
    custDataDictExample.createCriteria().andPcodeEqualTo(code).andDelNotEqualTo(DataDictDeleteStatus);
    custDataDictExample.setOrderByClause("id desc");
    List<CustDataDict> custDataDictList=  custDataDictMapper.selectByExample(custDataDictExample);
    return custDataDictList;
  }
  @Override
  public void deleteDataDict(Long id) throws Exception {
    CustDataDict custDataDictNew = new CustDataDict();
    custDataDictNew.setId(id);
    //0为删除
    custDataDictNew.setDel(DataDictDeleteStatus);
    custDataDictMapper.updateByPrimaryKeySelective(custDataDictNew);
  }
}
