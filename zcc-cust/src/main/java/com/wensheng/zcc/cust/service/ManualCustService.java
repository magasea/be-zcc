package com.wensheng.zcc.cust.service;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import com.wensheng.zcc.cust.module.vo.CustTrdPersonExtVo;

public interface ManualCustService {

  boolean addCustPerson(CustTrdPerson custTrdPerson);
  boolean addCustCompany(CustTrdCmpy custTrdCmpy);
  boolean modCustPerson(CustTrdPerson custTrdPerson);
  boolean modCustCompany(CustTrdCmpy custTrdCmpy);
}
