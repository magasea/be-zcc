package com.wensheng.zcc.cust.service;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfo;
import java.util.List;

/**
 * @author chenwei on 4/18/19
 * @project miniapp-backend
 */
public interface TrdInfoService {
  CustTrdInfo addTrdInfo(CustTrdInfo custTrdInfo);

  List<CustTrdInfo> getTrdInfo();


  List<CustTrdInfo> getTrdInfo(Long custId, int id);
}
