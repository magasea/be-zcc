package com.wensheng.zcc.cust.service.impl;

import com.google.gson.Gson;
import com.wensheng.zcc.common.utils.AmcDateUtils;
import com.wensheng.zcc.common.utils.StringToolUtils;
import com.wensheng.zcc.cust.config.aop.LogExecutionTime;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustIntrstInfoMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustRegionMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdCmpyMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdInfoMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdPersonMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdSellerMapper;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpyExample;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfo;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfoExample;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPersonExample;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdSeller;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdSellerExample;
import com.wensheng.zcc.cust.module.helper.CustTypeEnum;
import com.wensheng.zcc.cust.module.helper.SyncTrdTypeEnum;
import com.wensheng.zcc.cust.module.helper.sync.CustTypeSyncEnum;
import com.wensheng.zcc.cust.module.sync.CustCmpyInfoFromSync;
import com.wensheng.zcc.cust.module.sync.CustPersonInfoFromSync;
import com.wensheng.zcc.cust.module.sync.CustTrdInfoTempSync;
import com.wensheng.zcc.cust.module.sync.PageWrapperResp;
import com.wensheng.zcc.cust.module.sync.TrdInfoFromSync;
import com.wensheng.zcc.cust.service.SyncService;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.apache.kafka.common.protocol.types.Field.Str;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Service;
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
    CustTrdSellerMapper custTrdSellerMapper;

    @Autowired
  CustTrdInfoMapper custTrdInfoMapper;

    private final String makeTrdDataUrl = "http://10.20.200.100:8085/debts/get/%s";

//    @Autowired
//  CustTrdSellerMapper custTrdSellerMapper;

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

    @Value("${cust.syncUrls.getTrdContactorInfoByTrdId}")
    private String getTrdContactorInfoByTrdId;

  @Value("${cust.syncUrls.getCompanyInfoByName}")
  String getCompanyInfoByNameUrl;


  //  String[] provinceCodes = {"350000000000"};
String[] provinceCodes = {"410000000000","130000000000","230000000000","220000000000","210000000000","110000000000",
    "370000000000","330000000000", "360000000000","310000000000", "320000000000",
    "420000000000"};
//  String[] provinceCodes = {"230000000000","220000000000","210000000000"};
//  String[] provinceCodes = {"130000000000"};

  Map<String, String> errorTrdInfos;
  Map<String, String> errCmpyInfos;
  Map<String, String> errPersonInfos;

  boolean isTest = true;
  int pageForLog = 0;
  static volatile Boolean  isInSync = false;

  private final static String originUrlDomainName = "cl.wenshengamc.com";
  private final static String updatedUrlDomainName = "cl.wsamc.com";


    @PostConstruct
    void init(){
      GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
      gsonHttpMessageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL) );
      restTemplate.getMessageConverters().removeIf(item -> item instanceof MappingJackson2HttpMessageConverter);
      restTemplate.getMessageConverters().removeIf(item -> item instanceof MappingJackson2XmlHttpMessageConverter);
      restTemplate.getMessageConverters().add(gsonHttpMessageConverter);
      isInSync = false;
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


@Override
  public CustCmpyInfoFromSync getCmpyInfoByName(String cmpyName){
    String url = String.format(getCompanyInfoByNameUrl, cmpyName);
    return restTemplate.getForEntity(url, CustCmpyInfoFromSync.class).getBody();
  }

  public PatchTrdContactor getTrdContactorByTrdId(String id){
    String url = String.format(getTrdContactorInfoByTrdId, id);
    return restTemplate.getForEntity(url, PatchTrdContactor.class).getBody();
  }

  public CustPersonInfoFromSync getPersonInfoById(String id){
    String url = String.format(getPersonInfoById, id);
    log.info(url);
    CustPersonInfoFromSync custPersonInfoFromSync = null;
    try{
      custPersonInfoFromSync = restTemplate.getForEntity(url, CustPersonInfoFromSync.class).getBody();
    }catch (Exception ex){
      log.error("Got error:", ex);
    }
    return custPersonInfoFromSync;
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

  @Override
//    @Scheduled(cron = "${spring.task.scheduling.cronExprTrd}")
  @LogExecutionTime
  public  String syncWithTrdInfoSchedule(){

    synchronized(isInSync){

      if( isInSync){
        log.info("It is current in synchronization");
        return "It is current in synchronization";
      }else{
        isInSync = true;
      }
      synchronized (isInSync){


        try{
          for (String provinceCode: provinceCodes){
            syncTrdInfoForProvince(provinceCode);
          }
        }catch(Exception ex){
          log.error(" error:", ex);
          isInSync = false;
          return "error:"+ex.getMessage();
        }finally {
          isInSync = false;
        }
      }

    }
    isInSync = false;
    return "Successed";
  }


  @Override
  @LogExecutionTime
  public  String syncWithTrdInfo(List<String> provinces){

      synchronized(isInSync){

          if( isInSync){
            log.info("It is current in synchronization");
            return "It is current in synchronization";
          }else{
            isInSync = true;
          }
          synchronized (isInSync){
            if(!CollectionUtils.isEmpty(provinces)){
              provinceCodes = provinces.toArray(new String[provinces.size()]);
              log.info("Begin to syn :{}", Arrays.toString(provinceCodes));
            }

            try{
              for (String provinceCode: provinceCodes){
                syncTrdInfoForProvince(provinceCode);
              }
            }catch(Exception ex){
              log.error(" error:", ex);
              isInSync = false;
              return "error:"+ex.getMessage();
            }finally {
              isInSync = false;
            }
          }

      }
      isInSync = false;
      return "Successed";
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
        syncWithTrdInfo(null);
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
    Date updateTime = AmcDateUtils.toUTCDate(custPersonInfoFromSync.getUpdateTime());
    if(CollectionUtils.isEmpty(custTrdPeople)){
      //make new person
      action = 1;
    }else if(custTrdPeople.get(0).getUpdateTime().before(updateTime) ){
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
    Date updateTime = AmcDateUtils.toUTCDate(custCmpyInfoFromSync.getUpdateTime());
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
      copyCmpySync2CmpyInfo(custCmpyInfoFromSync, custTrdCmpy);
      custTrdCmpyMapper.updateByPrimaryKeySelective(custTrdCmpy);
    }
    return false;

  }


  private void syncTrdInfoForProvince(String provinceCode) {
      errorTrdInfos = new HashMap<>();
      errCmpyInfos = new HashMap<>();
      errPersonInfos = new HashMap<>();
      pageForLog = 0;
      int pageNum = 0;
      int pageSize = 20;
      boolean haveNext = true;
      while(haveNext){
        pageForLog = pageNum;
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
      log.info("===========================================start error " + provinceCode
          + "summary=========================================");
      if(!CollectionUtils.isEmpty(errorTrdInfos)){
        errorTrdInfos.forEach((k, v) ->
        log.error("trdInfo id:{}, error msg:{}", k,v));
      }



    if(!CollectionUtils.isEmpty(errCmpyInfos)){
      errCmpyInfos.forEach((k, v) ->
          log.error("cmpy id:{}, error msg:{}", k,v));
    }

    if(!CollectionUtils.isEmpty(errPersonInfos)){
      errPersonInfos.forEach((k, v) ->
          log.error("person id:{}, error msg:{}", k,v));
    }
    log.info("===========================================end error " + provinceCode
        + "summary=========================================");

    errorTrdInfos.clear();
    errPersonInfos.clear();
    errCmpyInfos.clear();
    pageForLog = 0;
  }



  private void  handleTrdInfo(TrdInfoFromSync trdInfoFromSync) {

    int action = -1;
    Date updateDate = AmcDateUtils.toUTCDate(trdInfoFromSync.getUpdateDate());
    CustTrdInfoExample custTrdInfoExample = new CustTrdInfoExample();
    custTrdInfoExample.createCriteria().andInfoIdEqualTo(trdInfoFromSync.getId());
    List<CustTrdInfo> custTrdInfos = custTrdInfoMapper.selectByExample(custTrdInfoExample);
    if(CollectionUtils.isEmpty(custTrdInfos)){
      //make new trdInfo
      action = 1;
    }else{
      //update trdInfo
      if(custTrdInfos.get(0).getUpdateTime().before(updateDate) || isTest){
        action = 2;
        if(custTrdInfos.size() > 1){
          for(int idx = 0; idx < custTrdInfos.size() ; idx++){
            if(idx == 0){
              continue;
            }else{
              custTrdInfoMapper.deleteByPrimaryKey(custTrdInfos.get(idx).getId());
            }
          }
        }
      }else{
        log.info("Db record dateTime:{} , current sync info dateTime:{}", custTrdInfos.get(0).getUpdateTime(),
            updateDate);
        return;
      }

    }

    CustTrdInfo custTrdInfo = new CustTrdInfo();
    int result = copySyncTrd2TrdInfo(trdInfoFromSync, custTrdInfo);
    if(result < 0){
      return;
    }
    Long buyerId = -1L ;
    if(trdInfoFromSync.getBuyerTypePrep() == CustTypeSyncEnum.COMPANY.getId()){
      buyerId = syncCmpyInfoById(trdInfoFromSync, true, action == 1);
      custTrdInfo.setBuyerType(CustTypeEnum.COMPANY.getId());
    }else if(trdInfoFromSync.getBuyerTypePrep() == CustTypeSyncEnum.PERSON.getId()){
      buyerId = syncPersonInfoById(trdInfoFromSync, true, action == 1);
      custTrdInfo.setBuyerType(CustTypeEnum.PERSON.getId());
    }
    if(buyerId < 0){
      log.error("Failed to sync trd buyer with id:{}", trdInfoFromSync.getBuyerIdPrep());
      return;
    }
    custTrdInfo.setBuyerId(buyerId);

    Long sellerId = -1L;
    if(trdInfoFromSync.getSellerTypePrep() == CustTypeSyncEnum.COMPANY.getId()){
      sellerId = syncCmpyInfoById(trdInfoFromSync, false, action == 1);
      if(sellerId < 0){
        log.error("sellerId:{}", sellerId);
        return;
      }
      custTrdInfo.setSellerType(CustTypeEnum.COMPANY.getId());
      CustTrdSeller custTrdSeller = custTrdSellerMapper.selectByPrimaryKey(sellerId);
      custTrdInfo.setSellerName(custTrdSeller.getName());

    }else if(trdInfoFromSync.getSellerTypePrep() == CustTypeSyncEnum.PERSON.getId()){
      sellerId = syncPersonInfoById(trdInfoFromSync, false, action == 1);
      if(sellerId < 0){
        log.error("Failed to sync trd seller with id:{}", trdInfoFromSync.getSellerIdPrep());
        return;
      }
      custTrdInfo.setSellerType(CustTypeEnum.PERSON.getId());
      CustTrdSeller custTrdSeller = custTrdSellerMapper.selectByPrimaryKey(sellerId);
      custTrdInfo.setSellerName(custTrdSeller.getName());
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
    log.info("[trd id:{} originId:{}] [buyer id:{} originId:{}] [seller id:{} originId:{}] ",
        custTrdInfo.getId(), trdInfoFromSync.getId(), custTrdInfo.getBuyerId(), trdInfoFromSync.getBuyerIdPrep(),
        custTrdInfo.getSellerId(), trdInfoFromSync.getSellerIdPrep());

  }

  private synchronized Long syncPersonInfoById(TrdInfoFromSync trdInfoFromSync, boolean isBuyer, boolean isNewTrd) {
      CustPersonInfoFromSync custPersonInfoFromSync = getPersonInfoById(isBuyer? trdInfoFromSync.getBuyerIdPrep():
          trdInfoFromSync.getSellerIdPrep());
    if( null == custPersonInfoFromSync){
      log.error("Failed to get {} person info with id:{} with trd:{}", isBuyer?"buyer":"seller", isBuyer?
          trdInfoFromSync.getBuyerIdPrep():
          trdInfoFromSync.getSellerIdPrep(), trdInfoFromSync.getId());
      if(!errorTrdInfos.containsKey(trdInfoFromSync.getId())){
        errorTrdInfos.put(trdInfoFromSync.getId(),"");
      }
      errorTrdInfos.put(trdInfoFromSync.getId(), String.format("page:[%d] %s\n%s", pageForLog,
          errorTrdInfos.get(trdInfoFromSync.getId()),
          String.format("Failed to get %s person info with id:%s with trd:%s", isBuyer?"buyer":"seller", isBuyer?
              trdInfoFromSync.getBuyerIdPrep():
              trdInfoFromSync.getSellerIdPrep(), trdInfoFromSync.getId())));
      return -1L;
    }
    if(StringUtils.isEmpty(custPersonInfoFromSync.getName())){
      errorTrdInfos.put(trdInfoFromSync.getId(), String.format("page:[%d] %s\n%s", pageForLog,
          errorTrdInfos.get(trdInfoFromSync.getId()),
          String.format("get %s person info with id:%s with trd:%s and the person have name:{}", isBuyer?"buyer":
              "seller", isBuyer? trdInfoFromSync.getBuyerIdPrep(): trdInfoFromSync.getSellerIdPrep(),
              trdInfoFromSync.getId()), custPersonInfoFromSync.getName()));
      return -1L;
    }
    Date updateTime = AmcDateUtils.toUTCDate(custPersonInfoFromSync.getUpdateTime());
    int sellPersonAction = -1;
    if(!isBuyer){
      //handle this info into CUST_TRD_SELLER table
      CustTrdSellerExample custTrdSellerExample = new CustTrdSellerExample();
      custTrdSellerExample.createCriteria().andNameEqualTo(StringUtils.isEmpty(custPersonInfoFromSync.getName())?"-1":
          custPersonInfoFromSync.getName()).andMobileNumEqualTo(StringUtils.isEmpty(
              custPersonInfoFromSync.getMobileNum())? "-1": custPersonInfoFromSync.getMobileNum());
      List<CustTrdSeller> custTrdSellers =  custTrdSellerMapper.selectByExample(custTrdSellerExample);

      if(CollectionUtils.isEmpty(custTrdSellers)){
        //make new seller person
        sellPersonAction = 1;
      }else if(custTrdSellers.get(0).getUpdateTime().before(updateTime)||isNewTrd){
        //update seller person
        if(custTrdSellers.size() > 1){
          CustTrdInfoExample custTrdInfoExample = new CustTrdInfoExample();
          for(int idx = 0; idx < custTrdSellers.size(); idx ++){
            if(idx == 0){
              continue;
            }
            //before delete check the related trd info
            custTrdInfoExample.clear();
            custTrdInfoExample.createCriteria().andSellerIdEqualTo(custTrdSellers.get(idx).getId()).andSellerTypeEqualTo(CustTypeEnum.PERSON.getId());
            List<CustTrdInfo> custTrdInfos = custTrdInfoMapper.selectByExample(custTrdInfoExample);
            for(CustTrdInfo custTrdInfoOld: custTrdInfos){
              custTrdInfoOld.setSellerId(custTrdSellers.get(0).getId());
              custTrdInfoMapper.updateByPrimaryKeySelective(custTrdInfoOld);
            }
            custTrdSellerMapper.deleteByPrimaryKey(custTrdSellers.get(idx).getId());
          }
        }
        sellPersonAction = 2;

      }else{
        log.info("no change for this personInfo:{} current record time:{}", custTrdSellers.get(0).getUpdateTime(), custPersonInfoFromSync.getUpdateTime() );
        return custTrdSellers.get(0).getId();
      }

      CustTrdSeller custTrdSeller = new CustTrdSeller();

      if(sellPersonAction == 1){
        copyPersonSync2SellerInfo(custPersonInfoFromSync, custTrdSeller);
        custTrdSellerMapper.insertSelective(custTrdSeller);

      }else if(sellPersonAction == 2 ){
        custTrdSeller = custTrdSellers.get(0);
        copyPersonSync2SellerInfo(custPersonInfoFromSync, custTrdSeller);
        custTrdSellerMapper.updateByPrimaryKeySelective(custTrdSeller);
      }
      return custTrdSeller.getId();
    }




    CustTrdPersonExample custTrdPersonExample = new CustTrdPersonExample();
    custTrdPersonExample.createCriteria().andNameEqualTo(StringUtils.isEmpty(custPersonInfoFromSync.getName())?"-1":
        custPersonInfoFromSync.getName()).andMobileNumEqualTo(
        StringUtils.isEmpty(custPersonInfoFromSync.getMobileNum())? "-1": custPersonInfoFromSync.getMobileNum()).
        andIdCardNumEqualTo(StringUtils.isEmpty(custPersonInfoFromSync.getIdCardNum())? "-1": custPersonInfoFromSync.getIdCardNum());
    List<CustTrdPerson> custTrdPeople =  custTrdPersonMapper.selectByExample(custTrdPersonExample);
    int action = -1;

    if(CollectionUtils.isEmpty(custTrdPeople)){
      //make new person
      action = 1;
    }else if(custTrdPeople.get(0).getUpdateTime().before(updateTime)|| isNewTrd || custTrdPeople.get(0).getDataQuality() <= 2){
      //update person
      action = 2;
      if(custTrdPeople.size() > 0){
        CustTrdInfoExample custTrdInfoExample = new CustTrdInfoExample();
        for(int idx = 0; idx < custTrdPeople.size(); idx ++){
          if(idx == 0){
            continue;
          }
          //before delete check the related trd info
          custTrdInfoExample.clear();
          custTrdInfoExample.createCriteria().andBuyerIdEqualTo(custTrdPeople.get(idx).getId()).andBuyerTypeEqualTo(CustTypeEnum.PERSON.getId());
          List<CustTrdInfo> custTrdInfos = custTrdInfoMapper.selectByExample(custTrdInfoExample);
          for(CustTrdInfo custTrdInfoOld: custTrdInfos){
            custTrdInfoOld.setBuyerId(custTrdPeople.get(0).getId());
            custTrdInfoMapper.updateByPrimaryKeySelective(custTrdInfoOld);
          }
          custTrdPersonMapper.deleteByPrimaryKey(custTrdPeople.get(idx).getId());
        }
      }
    }else{
      log.info("no change for this personInfo:{} current record time:{}", custTrdPeople.get(0).getUpdateTime(), custPersonInfoFromSync.getUpdateTime() );
      return custTrdPeople.get(0).getId();
    }
    CustTrdPerson custTrdPerson = new CustTrdPerson();
    if(action == 2){
      custTrdPerson = custTrdPeople.get(0);
    }
    copyPersonSync2PersonInfo(custPersonInfoFromSync, custTrdPerson);
    if(action == 1){
      if(isBuyer){
        int quality = checkBasicDataQuality(custTrdPerson);
        if(quality <= 1){
          errPersonInfos.put(custPersonInfoFromSync.getId(), String.format("data quality too low:%s",
              custPersonInfoFromSync.toString()));
        }
        custTrdPerson.setDataQuality(checkBasicDataQuality(custTrdPerson));
      }
      custTrdPerson.setCreateTime(AmcDateUtils.getCurrentDate());
      custTrdPerson.setSyncTime(AmcDateUtils.getCurrentDate());
      custTrdPersonMapper.insertSelective(custTrdPerson);

    }else if(action == 2 ){
      custTrdPerson = custTrdPeople.get(0);
      if(isBuyer){
        custTrdPerson.setSyncTime(AmcDateUtils.getCurrentDate());
        int count = getTrdCntForPerson(custTrdPerson.getId()).intValue();
        if(isNewTrd){
          int basicQuality = checkBasicDataQuality(custTrdPerson);
          if(basicQuality <= 0){
            custTrdPerson.setDataQuality( (count + 1)/2);
          }else{
            custTrdPerson.setDataQuality(custTrdPerson.getDataQuality()+1);
          }
        }
      }
      custTrdPersonMapper.updateByPrimaryKeySelective(custTrdPerson);
    }
    return custTrdPerson.getId();
  }

  private void copyPersonSync2SellerInfo(CustPersonInfoFromSync custPersonInfoFromSync, CustTrdSeller custTrdSeller) {
    custTrdSeller.setAddr(custPersonInfoFromSync.getAddress());
    custTrdSeller.setMobileNum(custPersonInfoFromSync.getMobileNum());
    custTrdSeller.setName(custPersonInfoFromSync.getName());
    custTrdSeller.setOrigId(custPersonInfoFromSync.getId());
    custTrdSeller.setProvinceCode(custPersonInfoFromSync.getProvinceCode());
    custTrdSeller.setCityCode(custPersonInfoFromSync.getCityCode());
    custTrdSeller.setTel(custPersonInfoFromSync.getTelNum());
    custTrdSeller.setType(CustTypeEnum.PERSON.getId());

    if(custPersonInfoFromSync.getUpdateTime() != null && custPersonInfoFromSync.getUpdateTime() > 0){
      custTrdSeller.setUpdateTime(AmcDateUtils.toUTCDate(custPersonInfoFromSync.getUpdateTime()));
    }else{
      custTrdSeller.setUpdateTime(AmcDateUtils.getDataBaseDefaultOldDate());
    }
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
    custTrdPerson.setCity(StringUtils.isEmpty(custPersonInfoFromSync.getCityCode())? null:
        custPersonInfoFromSync.getCityCode().length() >= 11 ? custPersonInfoFromSync.getCityCode().substring(0, 6):
            custPersonInfoFromSync.getCityCode());

    custTrdPerson.setEmail(custPersonInfoFromSync.getEmail());
    custTrdPerson.setGender(custPersonInfoFromSync.getGender());
    custTrdPerson.setIdCardNum(StringUtils.isEmpty(custPersonInfoFromSync.getIdCardNum())?null:
        custPersonInfoFromSync.getIdCardNum()) ;
    custTrdPerson.setMobileNum(StringUtils.isEmpty(custPersonInfoFromSync.getMobileNum())? null:
        custPersonInfoFromSync.getMobileNum()) ;
    custTrdPerson.setName(StringUtils.isEmpty(custPersonInfoFromSync.getName())? null: custPersonInfoFromSync.getName());
    custTrdPerson.setProvince(custPersonInfoFromSync.getProvince());
    custTrdPerson.setTelNum(StringUtils.isEmpty(custPersonInfoFromSync.getTelNum())?null: custPersonInfoFromSync.getTelNum());
    Date updateTime = AmcDateUtils.toUTCDate(custPersonInfoFromSync.getUpdateTime());
    custTrdPerson.setUpdateTime(updateTime);
    custTrdPerson.setSyncTime(AmcDateUtils.getCurrentDate());

    //add logic to check the person have been updated by wensheng stuff with their information

  }

  /**
   * use id to sync company info
   * @param trdInfoFromSync
   * @return
   */

  private Long syncCmpyInfoById(TrdInfoFromSync trdInfoFromSync, boolean isBuyer, boolean isNewTrd) {
    CustCmpyInfoFromSync custCmpyInfoFromSync = getCmpyInfoById(isBuyer? trdInfoFromSync.getBuyerIdPrep():
        trdInfoFromSync.getSellerIdPrep());
    if( null == custCmpyInfoFromSync){
      log.error("Failed to get {} cmpy info with id:{} with trd:{}", isBuyer? "buyer":"seller", isBuyer?
          trdInfoFromSync.getBuyerIdPrep():
          trdInfoFromSync.getSellerIdPrep(), trdInfoFromSync.getId());
      if(!errorTrdInfos.containsKey(trdInfoFromSync.getId())){
        errorTrdInfos.put(trdInfoFromSync.getId(),"");
      }
      errorTrdInfos.put(trdInfoFromSync.getId(), String.format("page:[%d] %s\n%s", pageForLog,
      errorTrdInfos.get(trdInfoFromSync.getId()),String.format("Failed to get %s cmpy info with id:%s with trd:%s",
              isBuyer? "buyer":"seller", isBuyer?
                  trdInfoFromSync.getBuyerIdPrep():
                  trdInfoFromSync.getSellerIdPrep(), trdInfoFromSync.getId())));
      return -1L;
    }

    if(!isBuyer){
      CustTrdSellerExample custTrdSellerExample = new CustTrdSellerExample();
      custTrdSellerExample.createCriteria().andNameEqualTo(custCmpyInfoFromSync.getCmpyName());
      List<CustTrdSeller> custTrdSellers = custTrdSellerMapper.selectByExample(custTrdSellerExample);
      int sellerAction = -1;
      if(null == custCmpyInfoFromSync.getUpdateTime() || custCmpyInfoFromSync.getUpdateTime() <= 0L){
        log.error("Failed to get {} cmpy updateTime with id:{} with trd:{} updateTime:{}", isBuyer? "buyer":"seller",
            isBuyer? trdInfoFromSync.getBuyerIdPrep(): trdInfoFromSync.getSellerIdPrep(), trdInfoFromSync.getId(),
            custCmpyInfoFromSync.getUpdateTime());

        if(!errorTrdInfos.containsKey(trdInfoFromSync.getId())){
          errorTrdInfos.put(trdInfoFromSync.getId(),"");
        }
        errorTrdInfos.put(trdInfoFromSync.getId(), String.format("page:[%d] %s\n%s", pageForLog,
            errorTrdInfos.get(trdInfoFromSync.getId()),String.format("Failed to get %s cmpy info updateTime with "
                    + "id:%s with trd:%s",
                isBuyer? "buyer":"seller", isBuyer?
                    trdInfoFromSync.getBuyerIdPrep():
                    trdInfoFromSync.getSellerIdPrep(), trdInfoFromSync.getId())));
        return -1L;
      }
      Date updateTime = AmcDateUtils.toUTCDate(custCmpyInfoFromSync.getUpdateTime());
      if(CollectionUtils.isEmpty(custTrdSellers)){
        //make new sell info
        sellerAction = 1;
      }else if(updateTime.after(custTrdSellers.get(0).getUpdateTime())){
        //update seller info

        sellerAction = 2;
        if(custTrdSellers.size() > 0){
          CustTrdInfoExample custTrdInfoExample = new CustTrdInfoExample();
          for(int idx = 0; idx < custTrdSellers.size(); idx++){
            if(idx == 0){
              continue;
            }
            //before delete check the related id in trdInfo and update it with current cmpy id
            custTrdInfoExample.clear();
            custTrdInfoExample.createCriteria().andSellerIdEqualTo(custTrdSellers.get(idx).getId()).andSellerTypeEqualTo(CustTypeEnum.COMPANY.getId());
            List<CustTrdInfo> custTrdInfos = custTrdInfoMapper.selectByExample(custTrdInfoExample);
            if(!CollectionUtils.isEmpty(custTrdInfos)){
              for(CustTrdInfo custTrdInfoOld: custTrdInfos){
                custTrdInfoOld.setSellerId(custTrdSellers.get(0).getId());
                custTrdInfoMapper.updateByPrimaryKeySelective(custTrdInfoOld);
              }
            }
            custTrdSellerMapper.deleteByPrimaryKey(custTrdSellers.get(idx).getId());
          }
        }
      }else{
        log.info("no change for this cmpyInfo:{} record time:{}", custTrdSellers.get(0).getUpdateTime(), custCmpyInfoFromSync.getUpdateTime());
        return custTrdSellers.get(0).getId();
      }
      CustTrdSeller custTrdSeller = new CustTrdSeller();

      if(sellerAction == 1){
        copyCmpySync2SellerInfo(custCmpyInfoFromSync, custTrdSeller);
        custTrdSellerMapper.insertSelective(custTrdSeller);

      }else if(sellerAction == 2 || isNewTrd){
        custTrdSeller = custTrdSellers.get(0);
        copyCmpySync2SellerInfo(custCmpyInfoFromSync, custTrdSeller);
        custTrdSellerMapper.updateByPrimaryKeySelective(custTrdSeller);
      }
      return custTrdSeller.getId();
    }

    CustTrdCmpyExample custTrdCmpyExample = new CustTrdCmpyExample();
    custTrdCmpyExample.createCriteria().andCmpyNameEqualTo(custCmpyInfoFromSync.getCmpyName());
    List<CustTrdCmpy> custTrdCmpyList = custTrdCmpyMapper.selectByExample(custTrdCmpyExample);
    int action = -1;
    Date updateTime = AmcDateUtils.toUTCDate(custCmpyInfoFromSync.getUpdateTime());
    if(CollectionUtils.isEmpty(custTrdCmpyList)){
      //make new cmpy info
      action = 1;
    }else if(updateTime.after(custTrdCmpyList.get(0).getUpdateTime()) || isNewTrd || custTrdCmpyList.get(0).getDataQuality() <= 2){
      //update cmpy info
      if(custTrdCmpyList.size() > 1){
        CustTrdInfoExample custTrdInfoExample = new CustTrdInfoExample();
        for(int idx = 0; idx < custTrdCmpyList.size(); idx++){
          if(idx == 0){
            continue;
          }
          //before delete check the related id in trdInfo and update it with current cmpy id
          custTrdInfoExample.clear();
          custTrdInfoExample.createCriteria().andBuyerIdEqualTo(custTrdCmpyList.get(idx).getId()).andBuyerTypeEqualTo(CustTypeEnum.COMPANY.getId());
          List<CustTrdInfo> custTrdInfos = custTrdInfoMapper.selectByExample(custTrdInfoExample);
          if(!CollectionUtils.isEmpty(custTrdInfos)){
            for(CustTrdInfo custTrdInfoOld: custTrdInfos){
              custTrdInfoOld.setBuyerId(custTrdCmpyList.get(0).getId());
              custTrdInfoMapper.updateByPrimaryKeySelective(custTrdInfoOld);
            }
          }
          custTrdCmpyMapper.deleteByPrimaryKey(custTrdCmpyList.get(idx).getId());
        }
      }
      action = 2;
    }else{
      log.info("no change for this cmpyInfo:{} record time:{}", custTrdCmpyList.get(0).getUpdateTime(), custCmpyInfoFromSync.getUpdateTime());
      return custTrdCmpyList.get(0).getId();
    }
    CustTrdCmpy custTrdCmpy = new CustTrdCmpy();
    if(action == 2){
      custTrdCmpy = custTrdCmpyList.get(0);
    }
    copyCmpySync2CmpyInfo(custCmpyInfoFromSync, custTrdCmpy);
    if(action == 1){
      if(isBuyer){
        int quality = checkBasicDataQuality(custTrdCmpy);
        if(quality < 4){
          errCmpyInfos.put(custCmpyInfoFromSync.getId(), String.format("basic quality low:%s",custCmpyInfoFromSync.toString()));
        }
        custTrdCmpy.setDataQuality(quality);
      }
      custTrdCmpy.setCreateTime(AmcDateUtils.getCurrentDate());
      custTrdCmpy.setSyncTime(AmcDateUtils.getCurrentDate());
      custTrdCmpyMapper.insertSelective(custTrdCmpy);

    }else if(action == 2 ){
      custTrdCmpy = custTrdCmpyList.get(0);
      custTrdCmpy.setSyncTime(AmcDateUtils.getCurrentDate());
      if(isBuyer){
        int count = getTrdCntForCmpy(custTrdCmpy.getId()).intValue();
        if(isNewTrd){
          int basicQuality = checkBasicDataQuality(custTrdCmpy);
          if(basicQuality <= 0){
            custTrdCmpy.setDataQuality( (count + 1)/2);
          }else{
            custTrdCmpy.setDataQuality(custTrdCmpy.getDataQuality()+1);
          }
        }
      }
      custTrdCmpyMapper.updateByPrimaryKeySelective(custTrdCmpy);
    }
    return custTrdCmpy.getId();

  }

  private void copyCmpySync2SellerInfo(CustCmpyInfoFromSync custCmpyInfoFromSync, CustTrdSeller custTrdSeller) {
    custTrdSeller.setTel(custCmpyInfoFromSync.getAnnuReptPhone());
    custTrdSeller.setMobileNum(custCmpyInfoFromSync.getCmpyPhone());
    custTrdSeller.setOrigId(custCmpyInfoFromSync.getId());
    custTrdSeller.setCityCode(custCmpyInfoFromSync.getCityCode());
    custTrdSeller.setProvinceCode(custCmpyInfoFromSync.getProvinceCode());
    custTrdSeller.setAddr(custCmpyInfoFromSync.getCmpyAddr());
    custTrdSeller.setType(CustTypeEnum.COMPANY.getId());
    if(null != custCmpyInfoFromSync.getUpdateTime() && custCmpyInfoFromSync.getUpdateTime() > 0){
      custTrdSeller.setUpdateTime(AmcDateUtils.toUTCDate(custCmpyInfoFromSync.getUpdateTime()));
    }else{
      custTrdSeller.setUpdateTime(AmcDateUtils.getDataBaseDefaultOldDate());
    }
    custTrdSeller.setName(custCmpyInfoFromSync.getCmpyName());
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

  public void copyCmpySync2CmpyInfo(CustCmpyInfoFromSync custCmpyInfoFromSync, CustTrdCmpy custTrdCmpy){
    custTrdCmpy.setAnnuReptAddr(custCmpyInfoFromSync.getAnnuReptAddr());
    custTrdCmpy.setAnnuReptPhone(custCmpyInfoFromSync.getAnnuReptPhone());
    custTrdCmpy.setCmpyAddr(custCmpyInfoFromSync.getCmpyAddr());
    custTrdCmpy.setCmpyName(StringUtils.isEmpty(custCmpyInfoFromSync.getCmpyName())? "-1": custCmpyInfoFromSync.getCmpyName());
    custTrdCmpy.setCmpyPhone(custCmpyInfoFromSync.getCmpyPhone());
    custTrdCmpy.setCmpyProvince(StringUtils.isEmpty(custCmpyInfoFromSync.getCmpyProvince())?"-1":
        custCmpyInfoFromSync.getCmpyProvince().substring(0,6));
    custTrdCmpy.setAnnuReptAddr(custCmpyInfoFromSync.getAnnuReptAddr());
    custTrdCmpy.setLegalReptive(custCmpyInfoFromSync.getLegalReptive());
    custTrdCmpy.setUniSocialCode(custCmpyInfoFromSync.getUniSocialCode());
    custTrdCmpy.setUpdateTime(AmcDateUtils.toUTCDate(custCmpyInfoFromSync.getUpdateTime()));
    custTrdCmpy.setSyncTime(AmcDateUtils.getCurrentDate());

  }
  /**
   * copy information from trdInfoFromSync to custTrdInfo
   * except buyerId and sellerId
   * the buyerId and sellId will be handled in other function
   * @param trdInfoFromSync
   * @param custTrdInfo
   */

  private int copySyncTrd2TrdInfo(TrdInfoFromSync trdInfoFromSync, CustTrdInfo custTrdInfo){
//    custTrdInfo.setSellerId(sellerRecord.getId());
    //make default value
    custTrdInfo.setPubDate(AmcDateUtils.getDataBaseDefaultOldDate());
    custTrdInfo.setTrdDate(AmcDateUtils.getDataBaseDefaultOldDate());
    custTrdInfo.setTrdContactorAddr("-1");
    custTrdInfo.setTrdContactorName("-1");
    custTrdInfo.setTotalDebtAmount( Long.valueOf(trdInfoFromSync.getTrdAmountPrep()));
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
    custTrdInfo.setItemType(trdInfoFromSync.getTrdType());
    custTrdInfo.setItemSubType(trdInfoFromSync.getTrdType());
    custTrdInfo.setTrdType(SyncTrdTypeEnum.DEBTPUB.getId());

    custTrdInfo.setInfoTitle(StringUtils.isEmpty(trdInfoFromSync.getTrdTitle())? null:trdInfoFromSync.getTrdTitle() );
    if(trdInfoFromSync.getTrdDate() != null && trdInfoFromSync.getTrdDate() > 0L ){
      custTrdInfo.setTrdDate(AmcDateUtils.toUTCDate(trdInfoFromSync.getTrdDate()));
    }
    if(trdInfoFromSync.getPublishDate() != null && trdInfoFromSync.getPublishDate() > 0L){
      custTrdInfo.setPubDate(AmcDateUtils.toUTCDate(trdInfoFromSync.getPublishDate()));

    }

    if(trdInfoFromSync.getUpdateDate() != null && trdInfoFromSync.getUpdateDate() > 0L ){
      custTrdInfo.setUpdateTime(AmcDateUtils.toUTCDate(trdInfoFromSync.getUpdateDate()));
    }
    custTrdInfo.setInfoUrl(trdInfoFromSync.getTrdContentOss());
    if(StringUtils.isEmpty(trdInfoFromSync.getTrdProvincePrep())){
      log.error("trdInfoFromSync.getTrdProvincePrep() is empty");
      if(!errorTrdInfos.containsKey(trdInfoFromSync.getId())){
        errorTrdInfos.put(trdInfoFromSync.getId(), "");
      }
      errorTrdInfos.put(trdInfoFromSync.getId(), String.format("page:[%d] %s\n%s", pageForLog,
          errorTrdInfos.get(trdInfoFromSync.getId()),
          "trdInfoFromSync.getTrdProvincePrep() is empty"));
      return -1;
    }
    if(!trdInfoFromSync.getTrdProvincePrep().endsWith("000000")){
      log.error("getTrdProvincePrep:{}", trdInfoFromSync.getTrdProvincePrep());
    }
    custTrdInfo.setTrdProvince(trdInfoFromSync.getTrdProvincePrep().substring(0, 6));
    if(null == trdInfoFromSync.getDebtProvincePrep()){
      log.error("getDebtProvincePrep:{}", trdInfoFromSync.getDebtProvincePrep());
      errorTrdInfos.put(trdInfoFromSync.getId(), String.format("page:[%d] %s\n%s",
          pageForLog,   errorTrdInfos.get(trdInfoFromSync.getId()),
          String.format("trdInfoFromSync.getDebtProvincePrep() is %s",trdInfoFromSync.getDebtProvincePrep())));
      return -1;
    }
    custTrdInfo.setDebtProvince(trdInfoFromSync.getDebtProvincePrep().substring(0, 6));

    if(StringUtils.isEmpty(trdInfoFromSync.getDebtCityPrep())){
      if(!errorTrdInfos.containsKey(trdInfoFromSync.getId())){
        errorTrdInfos.put(trdInfoFromSync.getId(), "");
      }
      log.error("DebtCityPrep is empty {} of the trd  id:{}",
          trdInfoFromSync.getDebtProvincePrep(), trdInfoFromSync.getId() );
      errorTrdInfos.put(trdInfoFromSync.getId(), String.format("page:[%d] %s\n%s",
       pageForLog,   errorTrdInfos.get(trdInfoFromSync.getId()),
            "trdInfoFromSync.getDebtCityPrep() is empty"));
    }else{
      if(!trdInfoFromSync.getDebtCityPrep().endsWith("000000")){
        log.error("getDebtCityPrep:{}", trdInfoFromSync.getDebtCityPrep());
        errorTrdInfos.put(trdInfoFromSync.getId(), String.format("page:[%d] %s\n%s",
         pageForLog,   errorTrdInfos.get(trdInfoFromSync.getId()),
            String.format("trdInfoFromSync.getDebtCityPrep() is %s",trdInfoFromSync.getDebtCityPrep())));
      }
      custTrdInfo.setDebtCity(trdInfoFromSync.getDebtCityPrep().substring(0, 6));
    }
    custTrdInfo.setTrdAmountOrig(trdInfoFromSync.getTrdAmount());
    custTrdInfo.setTrdContactorName(StringUtils.isEmpty(trdInfoFromSync.getBuyerContactManPrep()) ? "-1":
        trdInfoFromSync.getBuyerContactManPrep());
    StringBuilder sb = new StringBuilder();
    if(!StringUtils.isEmpty(trdInfoFromSync.getBuyerContactPhonePrep())){

      custTrdInfo.setTrdContactorPhone(trdInfoFromSync.getBuyerContactPhonePrep());
      sb.append(trdInfoFromSync.getBuyerContactPhonePrep());
    }
    if(!StringUtils.isEmpty(trdInfoFromSync.getBuyerContactAddressPrep())){
      custTrdInfo.setTrdContactorAddress(trdInfoFromSync.getBuyerContactAddressPrep());
      if(sb.length() > 1){
        sb.append(" ");
      }
      sb.append(trdInfoFromSync.getBuyerContactAddressPrep());
    }
    if(sb.length() > 0){
      custTrdInfo.setTrdContactorAddr(sb.toString());
    }

    return 1;

  }
//http://10.20.200.100:8085/debts/get/3a7cd9cf68c5effc316981d6537d1595
@Override
  public boolean makeUpDataForMissDateOfTrade() throws ParseException {

    CustTrdInfoExample custTrdInfoExample = new CustTrdInfoExample();
    custTrdInfoExample.createCriteria().andTrdDateLessThan(AmcDateUtils.getDateFromStr("1902-01-01 00:00:00"));

    List<CustTrdInfo> custTrdInfos =  custTrdInfoMapper.selectByExample(custTrdInfoExample);

    for(CustTrdInfo custTrdInfo : custTrdInfos){
      CustTrdInfoTempSync custTrdInfoTempSync = getTrdFromSyncById(custTrdInfo.getInfoId());
      if(custTrdInfoTempSync != null && custTrdInfoTempSync.getUpdateTime() != null ){
        custTrdInfo.setTrdDate(AmcDateUtils.toUTCDate(custTrdInfoTempSync.getUpdateTime()));
        custTrdInfoMapper.updateByPrimaryKeySelective(custTrdInfo);
      }

    }
    return true;

  }

  @Override
  public boolean makeUpDataForProvinceCodeOfTrade() throws ParseException {
    CustTrdInfoExample custTrdInfoExample = new CustTrdInfoExample();
    custTrdInfoExample.createCriteria().andTrdProvinceIsNull();
    custTrdInfoExample.or().andTrdProvinceEqualTo("");
    custTrdInfoExample.or().andTrdProvinceEqualTo("-1");
    List<CustTrdInfo> custTrdInfos =  custTrdInfoMapper.selectByExample(custTrdInfoExample);
    for(CustTrdInfo custTrdInfo : custTrdInfos){
      CustTrdInfoTempSync custTrdInfoTempSync = getTrdFromSyncById(custTrdInfo.getInfoId());
      if(custTrdInfoTempSync != null && !StringUtils.isEmpty(custTrdInfoTempSync.getProvinceCode() ) ){
        log.info("now set trdProvince to :{}", custTrdInfoTempSync.getProvinceCode());
        custTrdInfo.setTrdProvince(custTrdInfoTempSync.getProvinceCode());
        custTrdInfoMapper.updateByPrimaryKeySelective(custTrdInfo);
      }

    }
    return false;
  }

  @Override
  public boolean makeCheckProvinceCodeOfTrade() throws ParseException {


    List<CustTrdInfo> custTrdInfos =  custTrdInfoMapper.selectByExample(null);
    String provinceCode = null;
    for(CustTrdInfo custTrdInfo : custTrdInfos){
//      System.out.println(custTrdInfo.getTrdProvince().substring(0,2));
      provinceCode = custTrdInfo.getTrdProvince().substring(0,2);
      if(!custTrdInfo.getDebtCity().startsWith(provinceCode)){
        log.error("province code:{} city code:{} infoId:{}", custTrdInfo.getTrdProvince(), custTrdInfo.getDebtCity(),
            custTrdInfo.getInfoId());
      }


    }
    return true;

  }

  @Override
  public boolean makeUpBuyerContactorOfTrade() throws Exception {


//    try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
//      stream.forEach(System.out::println);
//    }
    List<String> trdProvinces = new ArrayList<>();
    for(String province: provinceCodes){
      trdProvinces.add(province.substring(0, 6));
    }

    for(String province: trdProvinces){
      CustTrdInfoExample custTrdInfoExample = new CustTrdInfoExample();
      custTrdInfoExample.setOrderByClause(" id desc ");
      custTrdInfoExample.createCriteria().andTrdProvinceEqualTo(province).andTrdTypeEqualTo(1);

      Integer offset = 0;
      Integer pageSize = 100;
      RowBounds rowBounds = new RowBounds(offset, pageSize);
      List<CustTrdInfo> custTrdInfos = custTrdInfoMapper.selectByExampleWithRowbounds(custTrdInfoExample, rowBounds);
      StringBuilder sb = new StringBuilder();
      while(!CollectionUtils.isEmpty(custTrdInfos) && offset < 100000000){
        for(CustTrdInfo custTrdInfo: custTrdInfos ){
          PatchTrdContactor patchTrdContactor = null;
          try{
            patchTrdContactor =  getTrdContactorByTrdId(custTrdInfo.getInfoId());
          }catch (Exception ex){
            log.error("failed to get trd contactor by id:{}", custTrdInfo.getInfoId(), ex);
            continue;
          }

          if(!StringUtils.isEmpty(patchTrdContactor.getBuyerMan())){
            custTrdInfo.setTrdContactorName(patchTrdContactor.getBuyerMan());
          }
          sb.setLength(0);
          if(!StringUtils.isEmpty(patchTrdContactor.getBuyerPhone())){
            sb.append(patchTrdContactor.getBuyerPhone());
          }
          if(!StringUtils.isEmpty(patchTrdContactor.getBuyerAddress())){
            if(sb.length() > 0) {
              sb.append(" ");
            }
            sb.append(patchTrdContactor.buyerAddress);
          }
          if(!StringUtils.isEmpty(patchTrdContactor.getBuyerEmail())){
            if(sb.length() > 0){
              sb.append(" ");
            }
            sb.append(patchTrdContactor.getBuyerEmail());
          }
          custTrdInfo.setTrdContactorAddr(sb.toString());
          custTrdInfoMapper.updateByPrimaryKeySelective(custTrdInfo);
          sb.setLength(0);

        }
        offset += pageSize;
        rowBounds = new RowBounds(offset, pageSize);
        custTrdInfos = custTrdInfoMapper.selectByExampleWithRowbounds(custTrdInfoExample, rowBounds);
        rowBounds = null;

      }
      custTrdInfoExample = null;
      log.info("province:{} patch finished", province);
    }


    return false;
  }

  private CustTrdInfoTempSync getTrdFromSyncById(String id){

    String url = String.format(makeTrdDataUrl, id);
    CustTrdInfoTempSync custTrdInfoTempSync = null;
    try{
      custTrdInfoTempSync = restTemplate.getForEntity(url, CustTrdInfoTempSync.class).getBody();
    }catch (Exception ex){
      log.error("Error handling:{}",  id, ex);
    }
    return custTrdInfoTempSync;

  }

  public void updateBuyerCompanyInfoByIds(String id){
    CustCmpyInfoFromSync custCmpyInfoFromSync = getCmpyInfoById(id);
    CustTrdCmpy custTrdCmpy = new CustTrdCmpy();
    copyCmpySync2CmpyInfo(custCmpyInfoFromSync, custTrdCmpy);
    CustTrdCmpyExample custTrdCmpyExample = new CustTrdCmpyExample();
    custTrdCmpyExample.createCriteria().andCmpyNameEqualTo(custCmpyInfoFromSync.getCmpyName());
    List<CustTrdCmpy> custTrdCmpyList = custTrdCmpyMapper.selectByExample(custTrdCmpyExample);
    if(CollectionUtils.isEmpty(custTrdCmpyList)){
      log.error("there is no such company in db, just ignore it:{}", id);
    }else{
      copyCmpySync2CmpyInfo(custCmpyInfoFromSync, custTrdCmpyList.get(0));
      custTrdCmpyMapper.updateByPrimaryKeySelective(custTrdCmpyList.get(0));
    }

  }

  @Override
  public void updateSellerNameFromCTS() {
    RowBounds rowBounds = null;
    Long count =  custTrdInfoMapper.countByExample(null);
    CustTrdInfoExample custTrdInfoExample = new CustTrdInfoExample();
    custTrdInfoExample.setOrderByClause("id DESC");
    for(int idx = 0; idx < count/100; idx ++){
      rowBounds = new RowBounds(idx*100, 100);

      List<CustTrdInfo> custTrdInfos =  custTrdInfoMapper.selectByExampleWithRowbounds(custTrdInfoExample, rowBounds);
      updateCustTrdInfos(custTrdInfos);
    }

  }

  @Override
  public void patchTrdUrl() {
    RowBounds rowBounds = null;
    Long count =  custTrdInfoMapper.countByExample(null);
    CustTrdInfoExample custTrdInfoExample = new CustTrdInfoExample();
    custTrdInfoExample.setOrderByClause("id DESC");
    for(int idx = 0; idx < count/100; idx ++){
      rowBounds = new RowBounds(idx*100, 100);

      List<CustTrdInfo> custTrdInfos =  custTrdInfoMapper.selectByExampleWithRowbounds(custTrdInfoExample, rowBounds);
      patchTrdInfo(custTrdInfos);
    }
  }

  @Override
  public void patchTrdDupAdd() {
    int offset = 0;
    int pageSize = 50;
    CustTrdInfoExample custTrdInfoExample = new CustTrdInfoExample();
    custTrdInfoExample.setOrderByClause(" id desc ");
    custTrdInfoExample.createCriteria().andTrdContactorAddressIsNotNull().andTrdContactorAddressNotEqualTo("-1");
    RowBounds rowBounds = new RowBounds(offset, pageSize);
    List<CustTrdInfo> custTrdInfos = custTrdInfoMapper.selectByExampleWithRowbounds(custTrdInfoExample, rowBounds);
    boolean hasMore = false;
    if(CollectionUtils.isEmpty(custTrdInfos)){
      return;
    }else{
      hasMore = true;
    }
    while(hasMore){
      for(CustTrdInfo custTrdInfo: custTrdInfos){
        String addrToCheck = custTrdInfo.getTrdContactorAddr();
        String pureAddr = custTrdInfo.getTrdContactorAddress();
        if(StringToolUtils.countWord(addrToCheck, pureAddr) > 1){
          log.info("Need to trim word:{} from base:{}", pureAddr, addrToCheck);
          addrToCheck = addrToCheck.substring(0, addrToCheck.lastIndexOf(pureAddr));
          log.info("now it is:{}", addrToCheck);
          custTrdInfo.setTrdContactorAddr(addrToCheck);
          custTrdInfoMapper.updateByPrimaryKey(custTrdInfo);
        }else{
          log.info("no trim needed for:{}",addrToCheck );
        }


      }
    }
  }

  private void patchTrdInfo(List<CustTrdInfo> custTrdInfos) {
    for(CustTrdInfo custTrdInfo: custTrdInfos){
      if(!StringUtils.isEmpty(custTrdInfo.getInfoUrl()) && custTrdInfo.getInfoUrl().contains(originUrlDomainName)){
        log.info(custTrdInfo.getInfoUrl());
        custTrdInfo.setInfoUrl(custTrdInfo.getInfoUrl().replace(originUrlDomainName, updatedUrlDomainName));
        log.info(custTrdInfo.getInfoUrl());
        custTrdInfoMapper.updateByPrimaryKeySelective(custTrdInfo);
      }
    }
  }

  private void updateCustTrdInfos(List<CustTrdInfo> custTrdInfos) {
    for(CustTrdInfo custTrdInfo: custTrdInfos){

      CustTrdSeller custTrdSeller =  custTrdSellerMapper.selectByPrimaryKey(custTrdInfo.getSellerId());
      if(custTrdSeller == null){
        log.error("Failed to get seller for id:{} of custTrdInfo:{}", custTrdInfo.getSellerId(), custTrdInfo.getId());
        continue;
      }else{
        custTrdInfo.setSellerName(custTrdSeller.getName());
        custTrdInfoMapper.updateByPrimaryKeySelective(custTrdInfo);
      }
    }
  }

  @Data
  class PatchTrdContactor{
    String buyerMan;
    String buyerPhone;
    String buyerAddress;
    String buyerEmail;
    String buyerIdcard;
  }

}


