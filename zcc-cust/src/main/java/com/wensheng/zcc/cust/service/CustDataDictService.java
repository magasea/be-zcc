package com.wensheng.zcc.cust.service;


import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustDataDict;
import java.util.List;

public interface CustDataDictService {

  void createCustDataDict(CustDataDict custDataDict) throws Exception;

  void updateCustDataDict(CustDataDict custDataDict) throws Exception;

  List<CustDataDict> getParentDataDict() throws Exception;

  List<CustDataDict> getDataDictByCode(String Code) throws Exception;
}
