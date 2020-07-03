package com.wensheng.zcc.amc.service;

import com.wensheng.zcc.amc.module.dao.helper.ImageClassEnum;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AmcOperLog;
import com.wensheng.zcc.amc.module.dao.mongo.entity.DebtAdditional;
import com.wensheng.zcc.amc.module.dao.mongo.entity.DebtImage;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCmpy;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcInfo;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleFloor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.ext.AmcDebtExt;
import com.wensheng.zcc.amc.module.dto.AmcContactorDTO;
import com.wensheng.zcc.amc.module.dto.grpc.WXUserRegionFavor;
import com.wensheng.zcc.amc.module.vo.AmcDebtExtVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtSummary;
import com.wensheng.zcc.amc.module.vo.AmcDebtUploadImg2WXRlt;
import com.wensheng.zcc.amc.module.vo.AmcDebtVo;
import com.wensheng.zcc.amc.module.vo.AmcSaleGetListInPage;
import com.wensheng.zcc.amc.module.vo.AmcSaleGetRandomListInPage;
import com.wensheng.zcc.common.module.dto.AmcFilterContentDebt;
import com.wensheng.zcc.amc.module.vo.base.BaseActionVo;
import com.wensheng.zcc.common.params.sso.SSOAmcUser;
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

  public AmcDebtExtVo get(Long amcDebtId, boolean needAddInfo) throws Exception;


  public List<AmcDebtExtVo> getByIds(List<Long> amcDebtIds) throws Exception;

  public List<AmcDebtVo> query(AmcDebt queryCond, int offset, int size);

  public List<AmcDebtVo> queryBySeqIds(List<Long> debtIds);
  public List<AmcDebtVo> getMostVisitedDebts(int num) throws Exception;
  public List<AmcDebtVo> getMostVisitedDebtsByRecomm(int num);

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


  void saveDebtAddition(DebtAdditional debtAdditional, Long debtId);

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

  public List<AmcDebtVo> queryNearByDebtsWithLimit(GeoJsonPoint geoJsonPoint, int limit) throws Exception;

  List<AmcDebtVo> queryAllNearByDebts(GeoJsonPoint geoJsonPoint, Long[] distances) throws Exception;

  List<AmcDebt> getDebtSimpleByIds(List<Long> debtIds);

  List<AmcDebtVo> getByIdsSimpleWithoutAddition(List<Long> debtIds) throws Exception;

  List<AmcDebt> getDebtSimpleByTitleLike(String title);

  List<AmcDebtVo> getDebtsByTitleLike(String title) throws Exception;

  Long getDebtIdByTitle(String title) throws Exception;

  DebtImage uploadDebtImage(String imagePath, String ossPrePath, Long debtId, String desc) throws Exception;

  String getDebtOssPrePath(Long debtId);

  List<AmcContactorDTO> getDebtContactorByDebtIds(List<Long> debtIds);

  SSOAmcUser getDebtContactorByDebtId(Long debtId);

  List<AmcDebtVo> getFloorFilteredDebt(AmcFilterContentDebt filterDebt) throws Exception;

  List<AmcDebtVo> getFloorFilteredDebt(AmcFilterContentDebt filterDebt, AmcSaleGetListInPage pageInfo)
      throws Exception;

  List<Long> getLatestIds();


  List<AmcDebtVo> getFloorFilteredRandomDebts(AmcFilterContentDebt filterDebt, AmcSaleGetRandomListInPage pageInfo)
      throws Exception;

  void patchDebtClue(String cellDebtTitle, String cellDebtClue) throws Exception;

    void patchDebtCourt(String cellDebtTitle, Long courtId) throws Exception;

  List<AmcDebtVo> getUserLocalDebts(WXUserRegionFavor wxUserRegionFavor,
      AmcSaleFloor amcSaleFloor) throws Exception;

  List<AmcDebtExt> findDebtsAssetsOfUser(Long userId);
}
