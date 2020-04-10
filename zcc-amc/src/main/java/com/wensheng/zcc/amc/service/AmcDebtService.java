package com.wensheng.zcc.amc.service;

import com.wensheng.zcc.amc.module.dao.helper.ImageClassEnum;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AmcOperLog;
import com.wensheng.zcc.amc.module.dao.mongo.entity.DebtImage;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCmpy;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcInfo;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditor;
import com.wensheng.zcc.amc.module.vo.AmcDebtExtVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtSummary;
import com.wensheng.zcc.amc.module.vo.AmcDebtUploadImg2WXRlt;
import com.wensheng.zcc.amc.module.vo.AmcDebtVo;
import com.wensheng.zcc.amc.module.vo.base.BaseActionVo;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

/**
 * @author chenwei on 1/4/19
 * @project zcc-backend
 */
public interface AmcDebtService {

  public DebtImage  saveImageInfo(String ossPath, String originName, Long debtId, String fileDesc, ImageClassEnum imageClass);

  public AmcDebtVo create(AmcDebt AmcDebt);
  public List<AmcDebt> queryByTitle(String debtTitle, Long deptPackId);

  public int del(Long amcDebtId);

  public AmcDebtVo update(AmcDebt AmcDebt);
  public AmcDebtVo updatePublishState(AmcDebt AmcDebt);

  public List<AmcDebt> queryAllByUserId( Long userId);

  public AmcDebtExtVo get(Long amcDebtId) throws Exception;


  public List<AmcDebtExtVo> getByIds(List<Long> amcDebtIds) throws Exception;

  public List<AmcDebtVo> query(AmcDebt queryCond, int offset, int size);

  List<AmcDebtVo> queryAllExt(Long offset, int size, Map<String, Sort.Direction> orderBy,
      Map<String, Object> queryParam) throws Exception;

  List<AmcDebtVo> queryAllDebt(Long offset, int size, Map<String, Sort.Direction> orderBy) throws Exception;

  List<AmcDebt> queryByDebtpackId(Long debtPackId);

  Long getTotalCount(Map<String, Object> queryParamMap) throws Exception;



  boolean isAmcContactexist(Long amcContact1);

  public AmcDebtor create(AmcDebtor amcDebtor) throws Exception;

  public AmcDebtor update(AmcDebtor debtor);

  public AmcCmpy create(AmcCmpy amcCmpy);

  public AmcCmpy update(AmcCmpy amcCmpy);

  public void connDebt2Debtors(List<AmcDebtor> amcDebtors, Long debtId);

  public void saveDebtDesc(String debtDesc, Long debtId);

  public AmcInfo getAmcInfo(Long amcDebtId) throws Exception;

  public List<AmcDebtor> getDebtors(Long amcDebtId);

  public AmcOrigCreditor getOriginCreditor(Long amcDebtId);

  public Long getCreditorsCount();

  public List<AmcOrigCreditor> getAllOrigCreditors(int offset, int size, Map<String, Direction> orderByParam)
      throws Exception;

  void delImage(DebtImage debtImage);

  AmcOrigCreditor createOrigCreditor(AmcOrigCreditor amcOrigCreditor);

  List<AmcDebtor> getAllUnasignedDebtors(Long offset, int size, int type, Map<String, Direction> orderByParam) throws Exception;

  Long getDebtorCount();

  List<AmcCmpy> getAllCompanies(Long offset, int size, Map<String, Direction> orderByParam) throws Exception;

  Long getTotalCompanyCount();

  Map<String, List<Long>> getAllTitles();

  AmcDebtSummary getSummaryInfo();

//  void connDebt2Cmpys(List<AmcDebtorCmpy> newCompanies, Long id);
//
//  void connDebt2Persons(List<AmcDebtorPerson> newPersons, Long id);

  AmcDebt getDebt(Long debtId);

  List<AmcDebt> getDebtByTiltle(String debtTitle);

  <T> void saveOperLog(BaseActionVo<T> amcDebt, String reviewComment);

  List<AmcOperLog> getOperLog(Long debtId, Integer actionId);

    void setRecomm(List<Long> debtIds, int id);

  public List<Long> getDebtIdsByPackIds(List<Long> debtPackIds);
  List<AmcDebtUploadImg2WXRlt> uploadAmcDebtImage2WechatByIds(List<Long> debtIds);

  public void searchGeoInfoForDebtByCourt();

  List<AmcDebtExtVo> queryAllNearByDebts(GeoJsonPoint geoJsonPoint);

  List<AmcDebt> getDebtSimpleByIds(List<Long> debtIds);
  List<AmcDebt> getDebtSimpleByTitleLike(String title);

  DebtImage uploadDebtImage(String imagePath, String ossPrePath, Long debtId, String desc) throws Exception;

}
