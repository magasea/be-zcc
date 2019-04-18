package com.wensheng.zcc.cust.service;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustRegion;
import java.util.List;

/**
 * @author chenwei on 4/18/19
 * @project miniapp-backend
 */
public interface BasicInfoService {
  List<CustRegion> getAllCustRegion();
  List<CustRegion> getSubCustRegion(Long parentId);
}
