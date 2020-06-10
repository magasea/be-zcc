package com.wensheng.zcc.cust.service;


import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcCmpycontactor;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import java.util.List;

public interface CustPersonService {

  /**
   *  人工新建自然人
   * @param custTrdPerson
   * @throws Exception
   */
  void createCustPerson(CustTrdPerson custTrdPerson) throws Exception;

  /**
   * 人工修改自然人
   * @param custTrdPerson
   * @throws Exception
   */
  void updateCustPerson(CustTrdPerson custTrdPerson) throws Exception;

  /**
   * 合并联系人
   * @param fromPersonIds
   * @param toPersonId
   * @throws Exception
   */
  void mergeCustPerson(List<Long> fromPersonIds, Long toPersonId, Long updateBy) throws Exception;

}
