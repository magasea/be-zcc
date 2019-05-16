package com.wensheng.zcc.cust.service;

import com.wensheng.zcc.cust.controller.helper.QueryParam;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import com.wensheng.zcc.cust.module.vo.CustTrdInfoVo;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort.Direction;

/**
 * @author chenwei on 4/17/19
 * @project miniapp-backend
 */
public interface CustInfoService {

  public CustTrdCmpy addCompany(CustTrdCmpy custTrdCmpy);

  CustTrdPerson addTrdPerson(CustTrdPerson custTrdPerson);

  List<CustTrdPerson> getTrdPersons();

  List<CustTrdCmpy> getCmpies();

  List<CustTrdInfoVo> queryCmpyTradePage(int offset, int size, QueryParam queryParam, Map<String, Direction> orderByParam)
      throws Exception;

  Long getCmpyTradeCount(QueryParam queryParam);

  List<CustTrdInfoVo> queryPersonTradePage(int offset, int size, QueryParam queryParam, Map<String, Direction> orderByParam)
      throws Exception;

  Long getPersonTradeCount(QueryParam queryParam);

  CustTrdCmpy getCompany(Long companyId);

  CustTrdPerson getPerson(Long personId);
}