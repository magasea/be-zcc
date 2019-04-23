package com.wensheng.zcc.cust.service;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import java.util.List;

/**
 * @author chenwei on 4/17/19
 * @project miniapp-backend
 */
public interface CustInfoService {

  public CustTrdCmpy addCompany(CustTrdCmpy custTrdCmpy);

  CustTrdPerson addTrdPerson(CustTrdPerson custTrdPerson);

  List<CustTrdPerson> getTrdPersons();

  List<CustTrdCmpy> getCmpies();
}
