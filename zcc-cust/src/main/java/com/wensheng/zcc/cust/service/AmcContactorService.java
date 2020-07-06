package com.wensheng.zcc.cust.service;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcCmpycontactor;
import com.wensheng.zcc.cust.module.vo.CustAmcCmpycontactorExtVo;
import com.wensheng.zcc.cust.module.vo.CustAmcCmpycontactorTrdInfoVo;
import com.wensheng.zcc.cust.module.vo.MergeCustVo;
import com.wensheng.zcc.cust.module.vo.helper.MergeCustRestult;
import com.wensheng.zcc.cust.module.vo.helper.ModifyResult;
import java.util.List;

public interface AmcContactorService {

  void createAmcCmpyContactor(CustAmcCmpycontactor custAmcCmpycontactor) throws Exception;

  ModifyResult updateAmcCmpyContactor(CustAmcCmpycontactor custAmcCmpycontactor) throws Exception;

  MergeCustRestult mergeCmpycontactor(MergeCustVo mergeCustVo) throws Exception;

  List<CustAmcCmpycontactor> getCmpyAmcContactor(String cmpyName);

  List<CustAmcCmpycontactorExtVo> getCmpyAmcContactor(Long cmpyId);

  List<CustAmcCmpycontactorExtVo> getCmpyAmcContactorNew(Long cmpyId);

  List<CustAmcCmpycontactor> selectContactorByIdlist(List<Long> idList);

  CustAmcCmpycontactorTrdInfoVo getCmpyAmcContactorDetail(Long contactorId);

  void initCmpyAmcContactor();

  void initCmpyAmcContactorOfCmpy(Long cmpyId);

  void patchCmpycontactorRevisePhone();

}
