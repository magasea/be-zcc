package com.wensheng.zcc.sso.service;

import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcCompany;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcDept;
import com.wensheng.zcc.sso.module.vo.AmcCmpyDeptVo;

/**
 * @author chenwei on 3/15/19
 * @project zcc-backend
 */
public interface AmcBasicService {

  AmcCompany createCompany(AmcCompany amcCompany);

  AmcDept createDept(AmcDept amcDept);

  AmcCmpyDeptVo createModifyCmpyDept(AmcCmpyDeptVo amcCmpyDeptVo) throws Exception;
}
