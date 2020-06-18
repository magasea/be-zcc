package com.wensheng.zcc.cust.service.impl;

import com.wensheng.zcc.cust.dao.mysql.mapper.CustDataDictMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdCmpyMapper;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustDataDict;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustDataDictExample;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpyExample;
import com.wensheng.zcc.cust.service.CustDataDictService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class CustDataDictServiceImpl implements CustDataDictService {

  final Integer DataDictDeleteStatus = 0;

  @Autowired
  CustDataDictMapper custDataDictMapper;

  @Autowired
  CustTrdCmpyMapper custTrdCmpyMapper;

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
    CustDataDict custDataDict = custDataDictMapper.selectByPrimaryKey(id);
    if("CMPY_TYPE".equals(custDataDict.getPcode())){
      //校验是否可以删除
      CustTrdCmpyExample custTrdCmpyExample = new CustTrdCmpyExample();
      custTrdCmpyExample.createCriteria().andCmpyTypeEqualTo(Integer.parseInt(custDataDict.getValue()));
      List<CustTrdCmpy> custTrdCmpyList = custTrdCmpyMapper.selectByExample(custTrdCmpyExample);
      if(!CollectionUtils.isEmpty(custTrdCmpyList)){
        throw new Exception("公司类型存在引用，无法删除");
      }
    }

    CustDataDict custDataDictNew = new CustDataDict();
    custDataDictNew.setId(id);
    //0为删除
    custDataDictNew.setDel(DataDictDeleteStatus);
    custDataDictMapper.updateByPrimaryKeySelective(custDataDictNew);
  }
}
