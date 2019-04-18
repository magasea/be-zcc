package com.wensheng.zcc.cust.service.impl;

import com.wensheng.zcc.cust.dao.mysql.mapper.CustRegionMapper;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustRegion;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustRegionExample;
import com.wensheng.zcc.cust.service.BasicInfoService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chenwei on 4/18/19
 * @project miniapp-backend
 */
@Service
@Slf4j
public class BasicInfoServiceImpl implements BasicInfoService {
  @Autowired
  CustRegionMapper custRegionMapper;

  @Override
  public List<CustRegion> getAllCustRegion() {

    return custRegionMapper.selectByExample(null);
  }

  @Override
  public List<CustRegion> getSubCustRegion(Long parentId) {
    CustRegionExample custRegionExample = new CustRegionExample();
    custRegionExample.createCriteria().andParentIdEqualTo(parentId);
    return custRegionMapper.selectByExample(custRegionExample);
  }
}
