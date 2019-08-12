package com.wensheng.zcc.cust.service.impl;

import com.google.gson.Gson;
import com.wensheng.zcc.common.utils.AmcDateUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustIntrstInfoMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustRegionMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdCmpyMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdInfoMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdPersonMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdSellerMapper;
import com.wensheng.zcc.cust.module.dao.mongo.ImportCustRecord;
import com.wensheng.zcc.cust.module.dao.mongo.ImportTrdInfoRecord;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustIntrstInfo;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustIntrstInfoExample;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustRegion;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustRegionExample;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpyExample;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfo;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfoExample;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPersonExample;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdSeller;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdSellerExample;
import com.wensheng.zcc.cust.module.helper.CustTypeEnum;
import com.wensheng.zcc.cust.module.helper.sync.CustTypeSyncEnum;
import com.wensheng.zcc.cust.module.helper.sync.TrdInfoSyncTypeEnum;
import com.wensheng.zcc.cust.module.sync.CustCmpyInfoFromSync;
import com.wensheng.zcc.cust.module.sync.CustInfoFromSync;
import com.wensheng.zcc.cust.module.sync.CustPersonInfoFromSync;
import com.wensheng.zcc.cust.module.sync.PageWrapperResp;
import com.wensheng.zcc.cust.module.sync.TrdInfoFromSync;
import com.wensheng.zcc.cust.service.ScriptSysService;
import com.wensheng.zcc.cust.service.SyncService;
import io.grpc.netty.shaded.io.netty.util.internal.StringUtil;
import java.net.URLEncoder;
import java.text.ParseException;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/*
1. call sync trade info interface
  check trd info url exist or not exist
  exist -> update history record
  not exist -> insert new trd info
2. call sync person/company info interface
  use person id or company id to query person or company
  check person exist or not
  exist then update and return id
  not exist insert and return id

  check company exist or not
  exist then update update and return id
  not exist then insert and return id
 */


@Service
@Slf4j

public class SyncServiceImpl implements SyncService {

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    CustTrdCmpyMapper custTrdCmpyMapper;

    @Autowired
    CustTrdPersonMapper custTrdPersonMapper;

    @Autowired
    CustRegionMapper custRegionMapper;

    @Autowired
    CustIntrstInfoMapper custIntrstInfoMapper;


    @Autowired
  CustTrdInfoMapper custTrdInfoMapper;

    @Autowired
  CustTrdSellerMapper custTrdSellerMapper;

    private Gson gson = new Gson();
    @Value("${cust.syncUrls.debtTradeResources}")
    private String debtTradeResources;

    @Value("${cust.syncUrls.getCompanyInfoById}")
    private String getCompanyInfoById;

    @Value("${cust.syncUrls.getCompanyInfoByUpdateTime}")
    private String getCompanyInfoByUpdateTime;

    @Value("${cust.syncUrls.getPersonInfoById}")
    private String getPersonInfoById;

    @Value("${cust.syncUrls.getPersonInfoByUpdateTime}")
    private String getPersonInfoByUpdateTime;

    String[] provinceCodes = {"350000"};

    boolean isTest = true;

    @PostConstruct
    void init(){
      restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
    }

    public PageWrapperResp<TrdInfoFromSync> getTradeInfosByProvince( String provinceCode, int pageNum, int pageSize){
//      String provEncod = encodeUtf8(province);
        String url = String.format(debtTradeResources, pageNum, pageSize, provinceCode);
        return restTemplate.exchange(url, HttpMethod.GET, null,
            new ParameterizedTypeReference<PageWrapperResp<TrdInfoFromSync>>() {}).getBody();
    }

    public CustCmpyInfoFromSync getCmpyInfoById(String id){
      String url = String.format(getCompanyInfoById, id);
      return restTemplate.getForEntity(url, CustCmpyInfoFromSync.class).getBody();
    }


  public CustPersonInfoFromSync getPersonInfoById(String id){
    String url = String.format(getPersonInfoById, id);
    return restTemplate.getForEntity(url, CustPersonInfoFromSync.class).getBody();
  }

  public PageWrapperResp<CustCmpyInfoFromSync> getCustCmpyInfoByDate(int pageNum, int pageSize, String fromDate,
      String provinceCode){

      String url = String.format(getCompanyInfoByUpdateTime, pageNum, pageSize, fromDate, provinceCode);
      return restTemplate.exchange(url, HttpMethod.GET, null,
          new ParameterizedTypeReference<PageWrapperResp<CustCmpyInfoFromSync>>(){}).getBody();

  }

  public PageWrapperResp<CustPersonInfoFromSync> getCustPersonInfoByDate(int pageNum, int pageSize, String fromDate,
      String provinceCode){

    String url = String.format(getPersonInfoByUpdateTime, pageNum, pageSize, fromDate, provinceCode);
    return restTemplate.exchange(url, HttpMethod.GET, null,
        new ParameterizedTypeReference<PageWrapperResp<CustPersonInfoFromSync>>(){}).getBody();

  }

//  @Scheduled(cron = "${spring.task.scheduling.cronExprTrd}")
  @Override
  public void syncWithTrdInfo(){
      for (String provinceCode: provinceCodes){
        syncTrdInfoForProvince(provinceCode);
      }
    }
//  @Scheduled(cron = "${spring.task.scheduling.cronExprCust}")
  @Override
  public void syncCustInfo(){
    for(String province: provinceCodes){
      boolean needSyncTrdInfoBaseOnCmpyInfo = false;
      boolean needSyncTrdInfoBaseOnPersonInfo = false;
      needSyncTrdInfoBaseOnCmpyInfo = syncCustCmpyInfo(province);

      needSyncTrdInfoBaseOnPersonInfo = syncCustPersonInfo(province);
      if(needSyncTrdInfoBaseOnCmpyInfo || needSyncTrdInfoBaseOnPersonInfo){
        syncWithTrdInfo();
      }
    }
  }

  private boolean syncCustPersonInfo(String province) {
    String dateMonthAgo = AmcDateUtils.getDateStrMonthsDiff(1);
    int pageNum = 0;
    int pageSize = 10;

    boolean haveNext = true;
    boolean needSyncTrdInfo = false;
    while(haveNext){
      PageWrapperResp<CustPersonInfoFromSync> pageWrapperResp = getCustPersonInfoByDate(pageNum, pageSize, dateMonthAgo,
          province);
      if(pageWrapperResp.getPages() < pageNum || CollectionUtils.isEmpty(pageWrapperResp.getList())){
        haveNext = false;
        continue;
      }
      else{
        for(CustPersonInfoFromSync custPersonInfoFromSync: pageWrapperResp.getList()){
          if(updatePersonInfo(custPersonInfoFromSync)){
            needSyncTrdInfo = true;
          }
        }
        pageNum++;
      }
    }
    return needSyncTrdInfo;
  }

  private boolean updatePersonInfo(CustPersonInfoFromSync custPersonInfoFromSync) {
    CustTrdPersonExample custTrdPersonExample = new CustTrdPersonExample();
    custTrdPersonExample.createCriteria().andNameEqualTo(custPersonInfoFromSync.getName()).andMobileNumEqualTo(
        StringUtils.isEmpty(custPersonInfoFromSync.getMobileNum())? "-1": custPersonInfoFromSync.getMobileNum()).
        andIdCardNumEqualTo(StringUtils.isEmpty(custPersonInfoFromSync.getIdCardNum())? "-1": custPersonInfoFromSync.getIdCardNum());
    List<CustTrdPerson> custTrdPeople =  custTrdPersonMapper.selectByExample(custTrdPersonExample);
    int action = -1;
    Date updateTime = AmcDateUtils.toDate(custPersonInfoFromSync.getUpdateTime());
    if(CollectionUtils.isEmpty(custTrdPeople)){
      //make new person
      action = 1;
    }else if(custTrdPeople.get(0).getUpdateTime().before(updateTime) || isTest){
      //update person
      action = 2;
    }else{
      log.info("no change for this personInfo:{} current record time:{}", custTrdPeople.get(0).getUpdateTime(), custPersonInfoFromSync.getUpdateTime() );
      return false;
    }
    CustTrdPerson custTrdPerson = new CustTrdPerson();
    copyPersonSync2PersonInfo(custPersonInfoFromSync, custTrdPerson);
    if(action == 1){
      log.info("need do sync for trd because person not in db, name:{} mobileNum:{}",
          custPersonInfoFromSync.getName(), custPersonInfoFromSync.getMobileNum());
      return true;

    }else if(action == 2 ){
      custTrdPerson = custTrdPeople.get(0);

      custTrdPersonMapper.updateByPrimaryKeySelective(custTrdPerson);
    }
    return false;
  }

  private boolean syncCustCmpyInfo(String province) {
    String dateMonthAgo = AmcDateUtils.getDateStrMonthsDiff(1);
    int pageNum = 0;
    int pageSize = 10;

    boolean haveNext = true;
    boolean needSyncTrdInfo = false;
    while(haveNext){
      PageWrapperResp<CustCmpyInfoFromSync> pageWrapperResp = getCustCmpyInfoByDate(pageNum, pageSize, dateMonthAgo,
          province);
      if(pageWrapperResp.getPages() < pageNum || CollectionUtils.isEmpty(pageWrapperResp.getList())){
        haveNext = false;
        continue;
      }
      else{
        for(CustCmpyInfoFromSync custCmpyInfoFromSync: pageWrapperResp.getList()){
          if(updateCmpyInfo(custCmpyInfoFromSync)){
            needSyncTrdInfo = true;
          }
        }
        pageNum++;
      }
    }
    return needSyncTrdInfo;
  }

  private boolean updateCmpyInfo(CustCmpyInfoFromSync custCmpyInfoFromSync) {

    CustTrdCmpyExample custTrdCmpyExample = new CustTrdCmpyExample();
    custTrdCmpyExample.createCriteria().andCmpyNameEqualTo(custCmpyInfoFromSync.getCmpyName());
    List<CustTrdCmpy> custTrdCmpyList = custTrdCmpyMapper.selectByExample(custTrdCmpyExample);
    int action = -1;
    Date updateTime = AmcDateUtils.toDate(custCmpyInfoFromSync.getUpdateTime());
    if(CollectionUtils.isEmpty(custTrdCmpyList)){
      //make new cmpy info
      action = 1;
    }else if(updateTime.after(custTrdCmpyList.get(0).getUpdateTime())){
      //update cmpy info
      action = 2;
    }else{
      log.info("no change for this cmpyInfo:{} record time:{}", custTrdCmpyList.get(0).getUpdateTime(), custCmpyInfoFromSync.getUpdateTime());
      return false;
    }
    CustTrdCmpy custTrdCmpy = new CustTrdCmpy();
    copyCmpySync2CmpyInfo(custCmpyInfoFromSync, custTrdCmpy);
    if(action == 1){
      log.info("need sync trd info because company not in db, name:{}", custCmpyInfoFromSync.getCmpyName());
      return true;

    }else if(action == 2 ){
      custTrdCmpy = custTrdCmpyList.get(0);
      custTrdCmpyMapper.updateByPrimaryKeySelective(custTrdCmpy);
    }
    return false;

  }


  private void syncTrdInfoForProvince(String provinceCode) {
      int pageNum = 0;
      int pageSize = 20;
      boolean haveNext = true;
      while(haveNext){
        PageWrapperResp<TrdInfoFromSync> pageWrapperResp = getTradeInfosByProvince(provinceCode, pageNum, pageSize);
        if(pageWrapperResp.getPages() < pageNum || CollectionUtils.isEmpty(pageWrapperResp.getList())){
          haveNext = false;
          continue;
        }
        else{
          for(TrdInfoFromSync trdInfoFromSync: pageWrapperResp.getList()){
            handleTrdInfo(trdInfoFromSync);
          }
          pageNum++;
        }
      }


  }



  private void handleTrdInfo(TrdInfoFromSync trdInfoFromSync) {

    int action = -1;
    Date updateDate = AmcDateUtils.toDate(trdInfoFromSync.getUpdateDate());
    CustTrdInfoExample custTrdInfoExample = new CustTrdInfoExample();
    custTrdInfoExample.createCriteria().andInfoUrlEqualTo(trdInfoFromSync.getUrl());
    List<CustTrdInfo> custTrdInfos = custTrdInfoMapper.selectByExample(custTrdInfoExample);
    if(CollectionUtils.isEmpty(custTrdInfos)){
      //make new trdInfo
      action = 1;
    }else{
      //update trdInfo
      if(custTrdInfos.get(0).getUpdateTime().before(updateDate)){
        action = 2;
      }else{
        log.info("Db record dateTime:{} , current sync info dateTime:{}", custTrdInfos.get(0).getUpdateTime(),
            updateDate);
        return;
      }

    }

    CustTrdInfo custTrdInfo = new CustTrdInfo();
    copySyncTrd2TrdInfo(trdInfoFromSync, custTrdInfo);
    Long buyerId = -1L ;
    if(trdInfoFromSync.getBuyerTypePrep() == CustTypeSyncEnum.COMPANY.getId()){
      buyerId = syncCmpyInfoById(trdInfoFromSync, true);
      custTrdInfo.setBuyerType(CustTypeEnum.COMPANY.getId());
    }else if(trdInfoFromSync.getBuyerTypePrep() == CustTypeSyncEnum.PERSON.getId()){
      buyerId = syncPersonInfoById(trdInfoFromSync, true);
      custTrdInfo.setBuyerType(CustTypeEnum.PERSON.getId());
    }
    if(buyerId < 0){
      log.error("Failed to sync trd buyer with id:{}", trdInfoFromSync.getBuyerIdPrep());
      return;
    }
    custTrdInfo.setBuyerId(buyerId);

    Long sellerId = -1L;
    if(trdInfoFromSync.getSellerTypePrep() == CustTypeSyncEnum.COMPANY.getId()){
      sellerId = syncCmpyInfoById(trdInfoFromSync, false);
      custTrdInfo.setSellerType(CustTypeEnum.COMPANY.getId());
    }else if(trdInfoFromSync.getSellerTypePrep() == CustTypeSyncEnum.PERSON.getId()){
      sellerId = syncPersonInfoById(trdInfoFromSync, false);
      custTrdInfo.setSellerType(CustTypeEnum.PERSON.getId());
    }
    if(sellerId < 0){
      log.error("Failed to sync trd seller with id:{}", trdInfoFromSync.getSellerIdPrep());
      return;
    }
    custTrdInfo.setSellerId(sellerId);
    if(action == 1){
      //make new trdInfo
      custTrdInfoMapper.insertSelective(custTrdInfo);
    }else if(action == 2){
      //update trdInfo

      custTrdInfo.setId(custTrdInfos.get(0).getId());
      custTrdInfoMapper.updateByPrimaryKeySelective(custTrdInfo);

    }else{
      log.error("action:{}", action);
    }

  }

  private Long syncPersonInfoById(TrdInfoFromSync trdInfoFromSync, boolean isBuyer) {
      CustPersonInfoFromSync custPersonInfoFromSync = getPersonInfoById(isBuyer? trdInfoFromSync.getBuyerIdPrep():
          trdInfoFromSync.getSellerIdPrep());
    if( null == custPersonInfoFromSync){
      log.error("Failed to get {} person info with id:{} with trd id:{}", isBuyer?"buyer":"seller", isBuyer?
          trdInfoFromSync.getBuyerIdPrep():
          trdInfoFromSync.getSellerIdPrep(), trdInfoFromSync.getId());
      return -1L;
    }
    CustTrdPersonExample custTrdPersonExample = new CustTrdPersonExample();
    custTrdPersonExample.createCriteria().andNameEqualTo(StringUtils.isEmpty(custPersonInfoFromSync.getName())?"-1":
        custPersonInfoFromSync.getName()).andMobileNumEqualTo(
        StringUtils.isEmpty(custPersonInfoFromSync.getMobileNum())? "-1": custPersonInfoFromSync.getMobileNum()).
        andIdCardNumEqualTo(StringUtils.isEmpty(custPersonInfoFromSync.getIdCardNum())? "-1": custPersonInfoFromSync.getIdCardNum());
    List<CustTrdPerson> custTrdPeople =  custTrdPersonMapper.selectByExample(custTrdPersonExample);
    int action = -1;
    Date updateTime = AmcDateUtils.toDate(custPersonInfoFromSync.getUpdateTime());
    if(CollectionUtils.isEmpty(custTrdPeople)){
      //make new person
      action = 1;
    }else if(custTrdPeople.get(0).getUpdateTime().before(updateTime)|| isTest){
      //update person
      action = 2;
    }else{
      log.info("no change for this personInfo:{} current record time:{}", custTrdPeople.get(0).getUpdateTime(), custPersonInfoFromSync.getUpdateTime() );
      return custTrdPeople.get(0).getId();
    }
    CustTrdPerson custTrdPerson = new CustTrdPerson();
    copyPersonSync2PersonInfo(custPersonInfoFromSync, custTrdPerson);
    if(action == 1){
      if(isBuyer){
        custTrdPerson.setDataQuality(checkBasicDataQuality(custTrdPerson));
      }
      custTrdPersonMapper.insertSelective(custTrdPerson);

    }else if(action == 2 ){
      custTrdPerson = custTrdPeople.get(0);
      if(isBuyer){
        int count = getTrdCntForPerson(custTrdPerson.getId()).intValue();
        custTrdPerson.setDataQuality(count <= 0 || custTrdPerson.getDataQuality() < 0 ? -1: custTrdPerson.getDataQuality()+1);
      }
      custTrdPersonMapper.updateByPrimaryKeySelective(custTrdPerson);
    }
    return custTrdPerson.getId();
  }

  private int checkBasicDataQuality(CustTrdPerson custTrdPerson) {
      int dataQuality = 0;

    if (StringUtils.isEmpty(custTrdPerson.getAddr())) {
      dataQuality = dataQuality ;
    } else {
      dataQuality = dataQuality + 1;
    }
    if (StringUtils.isEmpty(custTrdPerson.getCity())) {
      dataQuality = dataQuality ;
    } else {
      dataQuality = dataQuality + 1;
    }
    if (StringUtils.isEmpty(custTrdPerson.getEmail())) {
      dataQuality = dataQuality ;
    } else {
      dataQuality = dataQuality + 1;
    }
    if (custTrdPerson.getGender() <= 0) {
      dataQuality = dataQuality ;
    } else {
      dataQuality = dataQuality + 1;
    }
    if (StringUtils.isEmpty(custTrdPerson.getIdCardNum())) {
      dataQuality = dataQuality ;
    } else {
      dataQuality = dataQuality + 1;
    }


    if (StringUtils.isEmpty(custTrdPerson.getProvince())) {
      dataQuality = dataQuality ;
    } else {
      dataQuality = dataQuality + 1;
    }
    if (StringUtils.isEmpty(custTrdPerson.getTelNum())) {
      dataQuality = dataQuality ;
    } else {
      dataQuality = dataQuality + 1;
    }
    //key field cannot be empty
    if (StringUtils.isEmpty(custTrdPerson.getName())) {
      dataQuality = -1 ;
    } else {
      dataQuality = dataQuality + 1;
    }

    if (StringUtils.isEmpty(custTrdPerson.getMobileNum())) {
      dataQuality = -1 ;
    } else {
      dataQuality = dataQuality + 1;
    }
    return dataQuality;
  }

  private void copyPersonSync2PersonInfo(CustPersonInfoFromSync custPersonInfoFromSync, CustTrdPerson custTrdPerson) {
    custTrdPerson.setAddr(custPersonInfoFromSync.getAddress());
    custTrdPerson.setCity(StringUtils.isEmpty(custPersonInfoFromSync.getCityCode())? null:custPersonInfoFromSync.getCityCode());
    custTrdPerson.setEmail(custPersonInfoFromSync.getEmail());
    custTrdPerson.setGender(custPersonInfoFromSync.getGender());
    custTrdPerson.setIdCardNum(StringUtils.isEmpty(custPersonInfoFromSync.getIdCardNum())?null:
        custPersonInfoFromSync.getIdCardNum()) ;
    custTrdPerson.setMobileNum(StringUtils.isEmpty(custPersonInfoFromSync.getMobileNum())? null:
        custPersonInfoFromSync.getMobileNum()) ;
    custTrdPerson.setName(StringUtils.isEmpty(custPersonInfoFromSync.getName())? null: custPersonInfoFromSync.getName());
    custTrdPerson.setProvince(custPersonInfoFromSync.getProvince());
    custTrdPerson.setTelNum(StringUtils.isEmpty(custPersonInfoFromSync.getTelNum())?null: custPersonInfoFromSync.getTelNum());
    Date updateTime = AmcDateUtils.toDate(custPersonInfoFromSync.getUpdateTime());
    custTrdPerson.setUpdateTime(updateTime);
  }

  /**
   * use id to sync company info
   * @param trdInfoFromSync
   * @return
   */

  private Long syncCmpyInfoById(TrdInfoFromSync trdInfoFromSync, boolean isBuyer) {
    CustCmpyInfoFromSync custCmpyInfoFromSync = getCmpyInfoById(isBuyer? trdInfoFromSync.getBuyerIdPrep():
        trdInfoFromSync.getSellerIdPrep());
    if( null == custCmpyInfoFromSync){
      log.error("Failed to get {} cmpy info with id:{} of trd:{}", isBuyer? "buyer":"seller", isBuyer?
          trdInfoFromSync.getBuyerIdPrep():
          trdInfoFromSync.getSellerIdPrep(), trdInfoFromSync.getId());
      return -1L;
    }
    CustTrdCmpyExample custTrdCmpyExample = new CustTrdCmpyExample();
    custTrdCmpyExample.createCriteria().andCmpyNameEqualTo(custCmpyInfoFromSync.getCmpyName());
    List<CustTrdCmpy> custTrdCmpyList = custTrdCmpyMapper.selectByExample(custTrdCmpyExample);
    int action = -1;
    Date updateTime = AmcDateUtils.toDate(custCmpyInfoFromSync.getUpdateTime());
    if(CollectionUtils.isEmpty(custTrdCmpyList)){
      //make new cmpy info
      action = 1;
    }else if(updateTime.after(custTrdCmpyList.get(0).getUpdateTime())){
      //update cmpy info
      action = 2;
    }else{
      log.info("no change for this cmpyInfo:{} record time:{}", custTrdCmpyList.get(0).getUpdateTime(), custCmpyInfoFromSync.getUpdateTime());
      return custTrdCmpyList.get(0).getId();
    }
    CustTrdCmpy custTrdCmpy = new CustTrdCmpy();
    copyCmpySync2CmpyInfo(custCmpyInfoFromSync, custTrdCmpy);
    if(action == 1){
      if(isBuyer){
        custTrdCmpy.setDataQuality(checkBasicDataQuality(custTrdCmpy));
      }
      custTrdCmpyMapper.insertSelective(custTrdCmpy);

    }else if(action == 2 ){
      custTrdCmpy = custTrdCmpyList.get(0);
      if(isBuyer){
        int count = getTrdCntForCmpy(custTrdCmpy.getId()).intValue();
        custTrdCmpy.setDataQuality(count <= 0 || custTrdCmpy.getDataQuality() < 0 ? -1 :
            custTrdCmpy.getDataQuality() + 1);
      }
      custTrdCmpyMapper.updateByPrimaryKeySelective(custTrdCmpy);
    }
    return custTrdCmpy.getId();

  }
  private Long getTrdCntForCmpy(Long cmpyId){
    CustTrdInfoExample custTrdInfoExample = new CustTrdInfoExample();
    custTrdInfoExample.createCriteria().andBuyerIdEqualTo(cmpyId).andBuyerTypeEqualTo(CustTypeEnum.COMPANY.getId());
    return custTrdInfoMapper.countByExample(custTrdInfoExample);
  }

  private Long getTrdCntForPerson(Long personId){
    CustTrdInfoExample custTrdInfoExample = new CustTrdInfoExample();
    custTrdInfoExample.createCriteria().andBuyerIdEqualTo(personId).andBuyerTypeEqualTo(CustTypeEnum.PERSON.getId());
    return custTrdInfoMapper.countByExample(custTrdInfoExample);
  }

  private int checkBasicDataQuality(CustTrdCmpy custTrdCmpy){
    int dataQuality = 0;
    if (StringUtils.isEmpty(custTrdCmpy.getAnnuReptAddr())) {
      dataQuality = dataQuality;
    } else {
      dataQuality++;
    }

    if (StringUtils.isEmpty(custTrdCmpy.getAnnuReptPhone())) {
      dataQuality = dataQuality ;
    } else {
      dataQuality = dataQuality + 1;
    }
    if (StringUtils.isEmpty(custTrdCmpy.getCmpyAddr())) {
      dataQuality = dataQuality ;
    } else {
      dataQuality = dataQuality + 1;
    }

    if (StringUtils.isEmpty(custTrdCmpy.getCmpyPhone())) {
      dataQuality = dataQuality ;
    } else {
      dataQuality = dataQuality + 1;
    }
    if (StringUtils.isEmpty(custTrdCmpy.getAnnuReptAddr())) {
      dataQuality = dataQuality;
    } else {
      dataQuality = dataQuality + 1;
    }
    if (StringUtils.isEmpty(custTrdCmpy.getLegalReptive())) {
      dataQuality = dataQuality;
    } else {
      dataQuality = dataQuality + 1;
    }
    if (StringUtils.isEmpty(custTrdCmpy.getUniSocialCode())) {
      dataQuality = dataQuality;
    } else {
      dataQuality = dataQuality + 1;
    }

    //key information cannot be null
    if (StringUtils.isEmpty(custTrdCmpy.getCmpyName())) {
      dataQuality = -1 ;
    } else {
      dataQuality = dataQuality + 1;
    }
    return dataQuality;
  }
  private void copyCmpySync2CmpyInfo(CustCmpyInfoFromSync custCmpyInfoFromSync, CustTrdCmpy custTrdCmpy){
    custTrdCmpy.setAnnuReptAddr(custCmpyInfoFromSync.getAnnuReptAddr());
    custTrdCmpy.setAnnuReptPhone(custCmpyInfoFromSync.getAnnuReptPhone());
    custTrdCmpy.setCmpyAddr(custCmpyInfoFromSync.getCmpyAddr());
    custTrdCmpy.setCmpyName(StringUtils.isEmpty(custCmpyInfoFromSync.getCmpyName())? null: custCmpyInfoFromSync.getCmpyName());
    custTrdCmpy.setCmpyPhone(custCmpyInfoFromSync.getCmpyPhone());
    custTrdCmpy.setAnnuReptAddr(custCmpyInfoFromSync.getAnnuReptAddr());
    custTrdCmpy.setLegalReptive(custCmpyInfoFromSync.getLegalReptive());
    custTrdCmpy.setUniSocialCode(custCmpyInfoFromSync.getUniSocialCode());
    custTrdCmpy.setUpdateTime(AmcDateUtils.toDate(custCmpyInfoFromSync.getUpdateTime()));

  }
  /**
   * copy information from trdInfoFromSync to custTrdInfo
   * except buyerId and sellerId
   * the buyerId and sellId will be handled in other function
   * @param trdInfoFromSync
   * @param custTrdInfo
   */

  private void copySyncTrd2TrdInfo(TrdInfoFromSync trdInfoFromSync, CustTrdInfo custTrdInfo){
//    custTrdInfo.setSellerId(sellerRecord.getId());
    custTrdInfo.setTotalAmount(trdInfoFromSync.getTotalAmount().longValue());
    if(trdInfoFromSync.getBuyerTypePrep() == CustTypeSyncEnum.COMPANY.getId() ){
      custTrdInfo.setBuyerType(CustTypeEnum.COMPANY.getId());
    }else if(trdInfoFromSync.getBuyerTypePrep() == CustTypeSyncEnum.PERSON.getId()){
      custTrdInfo.setBuyerType(CustTypeEnum.PERSON.getId());
    }
    if(trdInfoFromSync.getSellerTypePrep() == CustTypeSyncEnum.COMPANY.getId() ){
      custTrdInfo.setSellerType(CustTypeEnum.COMPANY.getId());
    }else if(trdInfoFromSync.getSellerTypePrep() == CustTypeSyncEnum.PERSON.getId()){
      custTrdInfo.setSellerType(CustTypeEnum.PERSON.getId());
    }

    custTrdInfo.setInfoId(trdInfoFromSync.getId());
    custTrdInfo.setTrdType(trdInfoFromSync.getTrdType());
    custTrdInfo.setInfoTitle(StringUtils.isEmpty(trdInfoFromSync.getTrdTitle())? null:trdInfoFromSync.getTrdTitle() );
    if(trdInfoFromSync.getTrdDate() != null ){
      custTrdInfo.setTrdDate(AmcDateUtils.toDate(trdInfoFromSync.getTrdDate()));
    }

    if(trdInfoFromSync.getUpdateDate() != null ){
      custTrdInfo.setUpdateTime(AmcDateUtils.toDate(trdInfoFromSync.getUpdateDate()));
    }
    custTrdInfo.setInfoUrl(trdInfoFromSync.getUrl());
    custTrdInfo.setTrdProvince(trdInfoFromSync.getDebtProvincePrep());
    custTrdInfo.setTrdCity(trdInfoFromSync.getDebtCityPrep());
    custTrdInfo.setTrdAmountOrig(trdInfoFromSync.getTrdAmount());
    custTrdInfo.setTrdAmount(trdInfoFromSync.getTrdAmountPrep());
    custTrdInfo.setTrdContactorName(trdInfoFromSync.getLinkMan());
    custTrdInfo.setTrdContactorAddr(trdInfoFromSync.getLinkAddress());

  }


 }


