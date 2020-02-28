package com.wensheng.zcc.cust.service;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcUserpriv;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustRegion;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenwei on 4/18/19
 * @project miniapp-backend
 */
public interface BasicInfoService {
  List<CustRegion> getAllCustRegion();
  List<CustRegion> getSubCustRegion(Long parentId);
  public String getRegionNameByCode(Long code) throws Exception ;

  List<CustAmcUserpriv> getAmcUserPriv();
  void  createOrUpdateAmcUserPriv(List<CustAmcUserpriv> custAmcUserprivs);
  Map<String, Integer> getAmcUserPrivMap();

  }
