package com.wensheng.zcc.cust.service;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcCmpycontactor;
import com.wensheng.zcc.cust.module.vo.CustAmcCmpycontactorExtVo;
import java.util.List;

public interface AmcContactorService {

  void createAmcCmpyContactor(CustAmcCmpycontactor custAmcCmpycontactor) throws Exception;

  void updateAmcCmpyContactor(CustAmcCmpycontactor custAmcCmpycontactor);

  List<CustAmcCmpycontactor> getCmpyAmcContactor(String cmpyName);

  List<CustAmcCmpycontactorExtVo> getCmpyAmcContactor(Long cmpyId);

  void initCmpyAmcContactor();

}
