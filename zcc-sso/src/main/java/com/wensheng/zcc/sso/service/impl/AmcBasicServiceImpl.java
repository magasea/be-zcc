package com.wensheng.zcc.sso.service.impl;

import com.wensheng.zcc.sso.dao.mysql.mapper.AmcCompanyMapper;
import com.wensheng.zcc.sso.dao.mysql.mapper.AmcDeptMapper;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcCompany;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcCompanyExample;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcDept;
import com.wensheng.zcc.sso.module.vo.AmcCmpyDeptVo;
import com.wensheng.zcc.sso.service.AmcBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chenwei on 3/15/19
 * @project zcc-backend
 */
@Service
public class AmcBasicServiceImpl implements AmcBasicService {
  @Autowired
  AmcCompanyMapper amcCompanyMapper;

  @Autowired
  AmcDeptMapper amcDeptMapper;

  @Override
  public AmcCompany createCompany(AmcCompany amcCompany) {
    int count = amcCompanyMapper.insertSelective(amcCompany);
    return amcCompany;
  }

  @Override
  public AmcDept createDept(AmcDept amcDept) {
    int count = amcDeptMapper.insertSelective(amcDept);
    return amcDept;
  }

  @Override
  public AmcCmpyDeptVo createCmpyDept(AmcCmpyDeptVo amcCmpyDeptVo) {
//    AmcCompanyExample amcCompanyExample = new AmcCompanyExample();
//    if(amcCmpyDeptVo)
//    amcCompanyExample.createCriteria().andNameEqualTo(amcCmpyDeptVo.getAmcCompany().getName())
    return null;
  }
}
