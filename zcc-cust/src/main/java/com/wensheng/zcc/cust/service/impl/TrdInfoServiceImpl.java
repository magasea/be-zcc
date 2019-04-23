package com.wensheng.zcc.cust.service.impl;

import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdInfoMapper;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfo;
import com.wensheng.zcc.cust.service.TrdInfoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chenwei on 4/18/19
 * @project miniapp-backend
 */
@Service
public class TrdInfoServiceImpl implements TrdInfoService {

  @Autowired
  CustTrdInfoMapper custTrdInfoMapper;

  @Override
  public CustTrdInfo addTrdInfo(CustTrdInfo custTrdInfo) {
    custTrdInfoMapper.insertSelective(custTrdInfo);
    return custTrdInfo;
  }

  @Override
  public List<CustTrdInfo> getTrdInfo() {
    return custTrdInfoMapper.selectByExample(null);
  }
}
