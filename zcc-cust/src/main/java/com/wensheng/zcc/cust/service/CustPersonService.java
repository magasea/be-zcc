package com.wensheng.zcc.cust.service;


import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcCmpycontactor;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import com.wensheng.zcc.cust.module.vo.MergeCustVo;
import com.wensheng.zcc.cust.module.vo.helper.MergeCustRestult;
import com.wensheng.zcc.cust.module.vo.helper.ModifyResult;
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
  ModifyResult updateCustPerson(CustTrdPerson custTrdPerson) throws Exception;

  /**
   * 根据id查自然人集合
   * @param
   * @throws Exception
   */
  List<CustTrdPerson> selectPersonByIdList(List<Long> idList) throws Exception;

  /**
   * 合并联系人
   * @throws Exception
   */
  void mergeCustPerson(MergeCustRestult mergeCustRestult, MergeCustVo mergeCustVo) throws Exception;

}
