package com.wensheng.zcc.cust.service;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcCmpycontactor;
import java.util.List;

public interface AmcContactorService {

  void createAmcCmpyContactor(CustAmcCmpycontactor custAmcCmpycontactor);

  void updateAmcCmpyContactor(CustAmcCmpycontactor custAmcCmpycontactor);

  List<CustAmcCmpycontactor> getCmpyAmcContactor(String cmpyName);
}
