package com.wensheng.zcc.cust.service;

import com.wensheng.zcc.cust.controller.helper.QueryParam;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import com.wensheng.zcc.cust.module.vo.CustInfoGeoNear;
import com.wensheng.zcc.cust.module.vo.CustTrdFavorVo;
import com.wensheng.zcc.cust.module.vo.CustTrdInfoExcelVo;
import com.wensheng.zcc.cust.module.vo.CustTrdInfoVo;
import com.wensheng.zcc.cust.module.vo.CustTrdPersonVo;
import com.wensheng.zcc.cust.module.vo.CustsCountByTime;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

/**
 * @author chenwei on 4/17/19
 * @project miniapp-backend
 */
public interface CustInfoService {

  public CustTrdCmpy addCompany(CustTrdCmpy custTrdCmpy) throws Exception;
  public void updateCompany(CustTrdCmpy custTrdCmpy);

  CustTrdPerson addTrdPerson(CustTrdPerson custTrdPerson);

  List<CustTrdPerson> getTrdPersons();

  List<CustTrdCmpy> getCmpies();

  List<CustTrdInfoVo> queryCmpyTradePage(int offset, int size, QueryParam queryParam, Map<String, Direction> orderByParam)
      throws Exception;

  List<CustTrdInfoExcelVo> queryCmpyTrade(int offset, int size, QueryParam queryParam,
      Map<String, Direction> orderByParam) throws Exception;

  Long getCmpyTradeCount(QueryParam queryParam);

  List<CustTrdInfoVo> queryPersonTradePage(int offset, int size, QueryParam queryParam,
      Map<String, Direction> orderByParam)
      throws Exception;

  List<CustTrdInfoExcelVo> queryPersonTrade(int offset, int size,  QueryParam queryParam, Map<String, Direction> orderByParam) throws Exception;


  Long getPersonTradeCount(QueryParam queryParam);

  CustTrdCmpy getCompany(Long companyId);

  CustTrdPerson getPerson(Long personId);
  CustTrdPersonVo getPersonEditable(Long personId);

  public List<CustInfoGeoNear> queryAllNearByCusts(GeoJsonPoint geoJsonPoint);

  boolean modCustPerson(CustTrdPersonVo custTrdPersonVo);

  List<CustTrdCmpy> getCmpyFromDate(Date beginDate);
  List<CustTrdPerson> getPersonFromDate(Date beginDate);

  CustsCountByTime getCustCountByTime(LocalDateTime startTime);

  void patchDuplicateCmpyName();

  void patchCmpyProvince(String province);


  CustTrdFavorVo getCustFavor(Long custId, Integer custType);

  List<CustTrdCmpy> getCompanyByName(String companyName) throws Exception;
}
