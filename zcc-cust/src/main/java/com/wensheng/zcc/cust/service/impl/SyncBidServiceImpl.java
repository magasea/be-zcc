package com.wensheng.zcc.cust.service.impl;

import com.google.gson.Gson;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.common.utils.AmcDateUtils;
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
import com.wensheng.zcc.cust.module.helper.sync.BidTypeEnum;
import com.wensheng.zcc.cust.module.helper.sync.CustTypeSyncEnum;
import com.wensheng.zcc.cust.module.sync.CustCmpyInfoFromSync;
import com.wensheng.zcc.cust.module.sync.CustPersonInfoFromSync;
import com.wensheng.zcc.cust.module.sync.BidTrdInfoFromSync;
import com.wensheng.zcc.cust.module.sync.PageWrapperBidResp;
import com.wensheng.zcc.cust.module.sync.PageWrapperResp;
import com.wensheng.zcc.cust.service.SyncBidService;
import com.wensheng.zcc.cust.utils.TypeConvertUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
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

public class SyncBidServiceImpl implements SyncBidService {

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
    @Value("${cust.syncUrls.bidTradeResources}")
    private String bidTradeResources;

    @Value("${cust.syncUrls.getBidCompanyInfoById}")
    private String getCompanyInfoById;

    @Value("${cust.syncUrls.getCompanyInfoByUpdateTime}")
    private String getCompanyInfoByUpdateTime;

    @Value("${cust.syncUrls.getPersonInfoById}")
    private String getPersonInfoById;

    @Value("${cust.syncUrls.getPersonInfoByUpdateTime}")
    private String getPersonInfoByUpdateTime;

//  String[] provinceCodes = {"350000000000"};
String[] provinceCodes = {"410000000000","130000000000","230000000000","220000000000","210000000000","110000000000",
    "370000000000","330000000000", "360000000000"};
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

  public PageWrapperBidResp<BidTrdInfoFromSync> getTradeInfosByProvince( String provinceCode, int pageNum, int pageSize){
//      String provEncod = encodeUtf8(province);
    String url = String.format(bidTradeResources, pageNum, pageSize, provinceCode);
    try {

      return restTemplate.exchange(url, HttpMethod.GET, null,
          new ParameterizedTypeReference<PageWrapperBidResp<BidTrdInfoFromSync>>() {
          }).getBody();
    }catch (Exception ex){
      log.error("Failed to get record for pageNum:{} of province:{}", pageNum, provinceCode,  ex);
      String result = restTemplate.exchange(url, HttpMethod.GET, null,
          String.class).getBody();
      log.error(result);
      return null;
    }
  }
    public CustCmpyInfoFromSync getCmpyInfoById(String id){
      String url = String.format(getCompanyInfoById, id);
      return restTemplate.getForEntity(url, CustCmpyInfoFromSync.class).getBody();
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
            syncTrdInfoForProvince(provinceCode, null);
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
  public  String syncWithTrdInfo(List<String> provinces, String dateString){

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
                syncTrdInfoForProvince(provinceCode, dateString);
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
        syncWithTrdInfo(null,null);
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


  private void syncTrdInfoForProvince(String provinceCode, String dateString) {
      //入参转换为时间
      SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
      formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
      Date inputDate = null;
      try {
        inputDate = formatter.parse(dateString);
      } catch (ParseException e) {
        log.error("入参无法转换为时间：{}",dateString);
        return;
      }

      errorTrdInfos = new HashMap<>();
      errCmpyInfos = new HashMap<>();
      errPersonInfos = new HashMap<>();
      pageForLog = 0;
      int pageNum = 0;
      int pageSize = 20;
      boolean haveNext = true;
      while(haveNext){
        pageForLog = pageNum;
        PageWrapperBidResp<BidTrdInfoFromSync> pageWrapperResp = getTradeInfosByProvince(provinceCode, pageNum, pageSize);
        if(pageWrapperResp.getTotalPage() < pageNum || CollectionUtils.isEmpty(pageWrapperResp.getDataList())){
          haveNext = false;
          continue;
        }
        else{
          for(BidTrdInfoFromSync bidTrdInfoFromSync: pageWrapperResp.getDataList()){
            handleTrdInfo(bidTrdInfoFromSync, inputDate);
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



  private void  handleTrdInfo(BidTrdInfoFromSync trdInfoFromSync, Date inputDate) {

    int action = -1;
    Date updateDate = AmcDateUtils.toUTCDate(trdInfoFromSync.getUpdateTime());

    //对比输入的时间，更新数据updateDate需在输入时间之后
    if(inputDate.after(updateDate)){
      log.info("同步爬虫数据在入参时间之前：{}",trdInfoFromSync);
      return;
    }



    CustTrdInfoExample custTrdInfoExample = new CustTrdInfoExample();
    custTrdInfoExample.createCriteria().andInfoIdEqualTo(trdInfoFromSync.getAuctionID());
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
    copySyncTrd2TrdInfo(trdInfoFromSync, custTrdInfo);
    Long buyerId = -1L ;
    if(trdInfoFromSync.getBuyerType() == CustTypeSyncEnum.COMPANY.getId()){
      buyerId = syncCmpyInfoById(trdInfoFromSync, true, action == 1);
      custTrdInfo.setBuyerType(CustTypeEnum.COMPANY.getId());
    }else if(trdInfoFromSync.getBuyerType() == CustTypeSyncEnum.PERSON.getId()){
      buyerId = syncPersonInfoById(trdInfoFromSync, true, action == 1);
      custTrdInfo.setBuyerType(CustTypeEnum.PERSON.getId());
    }
    if(buyerId < 0){
      log.error("Failed to sync trd buyer with id:{}", trdInfoFromSync.getBuyerId());
      return;
    }
    custTrdInfo.setBuyerId(buyerId);

//    Long sellerId = -1L;
//    if(trdInfoFromSync.getSellerTypePrep() == CustTypeSyncEnum.COMPANY.getId()){
//      sellerId = syncCmpyInfoById(trdInfoFromSync, false, action == 1);
//      if(sellerId < 0){
//        log.error("sellerId:{}", sellerId);
//        return;
//      }
//      custTrdInfo.setSellerType(CustTypeEnum.COMPANY.getId());
//      CustTrdSeller custTrdSeller = custTrdSellerMapper.selectByPrimaryKey(sellerId);
//      custTrdInfo.setSellerName(custTrdSeller.getName());
//
//    }else if(trdInfoFromSync.getSellerTypePrep() == CustTypeSyncEnum.PERSON.getId()){
//      sellerId = syncPersonInfoById(trdInfoFromSync, false, action == 1);
//      if(sellerId < 0){
//        log.error("Failed to sync trd seller with id:{}", trdInfoFromSync.getSellerIdPrep());
//        return;
//      }
//      custTrdInfo.setSellerType(CustTypeEnum.PERSON.getId());
//      CustTrdSeller custTrdSeller = custTrdSellerMapper.selectByPrimaryKey(sellerId);
//      custTrdInfo.setSellerName(custTrdSeller.getName());
//    }
//
//    custTrdInfo.setSellerId(sellerId);
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
        custTrdInfo.getId(), trdInfoFromSync.getAuctionID(), custTrdInfo.getBuyerId(), trdInfoFromSync.getBuyerId(),
        custTrdInfo.getSellerId(), -1);

  }

  private synchronized Long syncPersonInfoById(BidTrdInfoFromSync trdInfoFromSync, boolean isBuyer, boolean isNewTrd) {
     if(trdInfoFromSync.getBuyerId().equals("-1")){
       //insert person with name directlly
       CustTrdPersonExample custTrdPersonExample = new CustTrdPersonExample();
       custTrdPersonExample.createCriteria().andNameEqualTo(StringUtils.isEmpty(trdInfoFromSync.getBuyerName())?"-1":
           trdInfoFromSync.getBuyerName()).andMobileNumEqualTo("-1");
       List<CustTrdPerson> custTrdPeople =  custTrdPersonMapper.selectByExample(custTrdPersonExample);
       if(CollectionUtils.isEmpty(custTrdPeople)){
         CustTrdPerson custTrdPerson = new CustTrdPerson();
         custTrdPerson.setCreateTime(AmcDateUtils.getCurrentDate());
         custTrdPerson.setName(trdInfoFromSync.getBuyerName());
         custTrdPersonMapper.insertSelective(custTrdPerson);
         return custTrdPerson.getId();
       }else{
         return custTrdPeople.get(0).getId();
       }

     }

      CustPersonInfoFromSync custPersonInfoFromSync = getPersonInfoById(isBuyer? trdInfoFromSync.getBuyerId():
          "-1");
    if( null == custPersonInfoFromSync){
      log.error("Failed to get {} person info with id:{} with trd:{}", isBuyer?"buyer":"seller", isBuyer?
          trdInfoFromSync.getBuyerId():
          -1, trdInfoFromSync.getAuctionID());
      if(!errorTrdInfos.containsKey(trdInfoFromSync.getAuctionID())){
        errorTrdInfos.put(trdInfoFromSync.getAuctionID(),"");
      }
      errorTrdInfos.put(trdInfoFromSync.getAuctionID(), String.format("page:[%d] %s\n%s", pageForLog,
          errorTrdInfos.get(trdInfoFromSync.getAuctionID()),
          String.format("Failed to get %s person info with id:%s with trd:%s", isBuyer?"buyer":"seller", isBuyer?
              trdInfoFromSync.getBuyerId():
              -1, trdInfoFromSync.getAuctionID())));
      return -1L;
    }
    if(StringUtils.isEmpty(custPersonInfoFromSync.getName())){
      errorTrdInfos.put(trdInfoFromSync.getAuctionID(), String.format("page:[%d] %s\n%s", pageForLog,
          errorTrdInfos.get(trdInfoFromSync.getAuctionID()),
          String.format("get %s person info with id:%s with trd:%s and the person have name:{}", isBuyer?"buyer":
              "seller", isBuyer? trdInfoFromSync.getBuyerId(): -1,
              trdInfoFromSync.getAuctionID()), custPersonInfoFromSync.getName()));
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
      custTrdPersonMapper.insertSelective(custTrdPerson);

    }else if(action == 2 ){
      custTrdPerson = custTrdPeople.get(0);
      if(isBuyer){
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

  private Long syncCmpyInfoById(BidTrdInfoFromSync trdInfoFromSync, boolean isBuyer, boolean isNewTrd) {
    CustCmpyInfoFromSync custCmpyInfoFromSync = getCmpyInfoById(isBuyer? trdInfoFromSync.getBuyerId():
        "-1");
    if( null == custCmpyInfoFromSync){
      log.error("Failed to get {} cmpy info with id:{} with trd:{}", isBuyer? "buyer":"seller", isBuyer?
          trdInfoFromSync.getBuyerId():
          -1, trdInfoFromSync.getAuctionID());
      if(!errorTrdInfos.containsKey(trdInfoFromSync.getAuctionID())){
        errorTrdInfos.put(trdInfoFromSync.getAuctionID(),"");
      }
      errorTrdInfos.put(trdInfoFromSync.getAuctionID(), String.format("page:[%d] %s\n%s", pageForLog,
      errorTrdInfos.get(trdInfoFromSync.getAuctionID()),String.format("Failed to get %s cmpy info with id:%s with trd:%s",
              isBuyer? "buyer":"seller", isBuyer?
                  trdInfoFromSync.getBuyerId():
                  -1, trdInfoFromSync.getAuctionID())));
      return -1L;
    }

    if(!isBuyer){
      CustTrdSellerExample custTrdSellerExample = new CustTrdSellerExample();
      custTrdSellerExample.createCriteria().andNameEqualTo(custCmpyInfoFromSync.getCmpyName());
      List<CustTrdSeller> custTrdSellers = custTrdSellerMapper.selectByExample(custTrdSellerExample);
      int sellerAction = -1;
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
    if("佛山市高明区合建市政建设有限公司阳山县石全石美石业有限公司开平市置力物业发展有限公司杭州捷邦供应链有限公司".contains(custCmpyInfoFromSync.getCmpyName())){
      System.out.println(custCmpyInfoFromSync.getCmpyName());
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
      custTrdCmpyMapper.insertSelective(custTrdCmpy);

    }else if(action == 2 ){
      CustTrdCmpy custTrdCmpyHis = custTrdCmpyList.get(0);
      AmcBeanUtils.copyProperties(custTrdCmpy, custTrdCmpyHis);
      if(isBuyer){
        int count = getTrdCntForCmpy(custTrdCmpyHis.getId()).intValue();
        if(isNewTrd){
          int basicQuality = checkBasicDataQuality(custTrdCmpyHis);
          if(basicQuality <= 0){
            custTrdCmpyHis.setDataQuality( (count + 1)/2);
          }else{
            custTrdCmpyHis.setDataQuality(custTrdCmpyHis.getDataQuality()+1);
          }
        }
      }
      custTrdCmpyMapper.updateByPrimaryKeySelective(custTrdCmpyHis);
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
  private void copyCmpySync2CmpyInfo(CustCmpyInfoFromSync custCmpyInfoFromSync, CustTrdCmpy custTrdCmpy){
    custTrdCmpy.setAnnuReptAddr(custCmpyInfoFromSync.getAnnuReptAddr());
    custTrdCmpy.setAnnuReptPhone(custCmpyInfoFromSync.getAnnuReptPhone());
    custTrdCmpy.setCmpyAddr(custCmpyInfoFromSync.getCmpyAddr());
    custTrdCmpy.setCmpyName(StringUtils.isEmpty(custCmpyInfoFromSync.getCmpyName())? null: custCmpyInfoFromSync.getCmpyName());
    custTrdCmpy.setCmpyPhone(custCmpyInfoFromSync.getCmpyPhone());
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

  private void copySyncTrd2TrdInfo(BidTrdInfoFromSync trdInfoFromSync, CustTrdInfo custTrdInfo){
//    custTrdInfo.setSellerId(sellerRecord.getId());
    //make default value
    custTrdInfo.setPubDate(AmcDateUtils.getDataBaseDefaultOldDate());
    custTrdInfo.setTrdDate(AmcDateUtils.getDataBaseDefaultOldDate());
    custTrdInfo.setTrdContactorAddr("-1");
    custTrdInfo.setTrdContactorName("-1");
    custTrdInfo.setTotalDebtAmount( -1L);
    custTrdInfo.setTrdAmount(trdInfoFromSync.getTrdAmount());
    if(trdInfoFromSync.getBuyerType() == CustTypeSyncEnum.COMPANY.getId() ){
      custTrdInfo.setBuyerType(CustTypeEnum.COMPANY.getId());
    }else if(trdInfoFromSync.getBuyerType() == CustTypeSyncEnum.PERSON.getId()){
      custTrdInfo.setBuyerType(CustTypeEnum.PERSON.getId());
    }
//    if(trdInfoFromSync.getSellerTypePrep() == CustTypeSyncEnum.COMPANY.getId() ){
//      custTrdInfo.setSellerType(CustTypeEnum.COMPANY.getId());
//    }else if(trdInfoFromSync.getSellerTypePrep() == CustTypeSyncEnum.PERSON.getId()){
//      custTrdInfo.setSellerType(CustTypeEnum.PERSON.getId());
//    }

    custTrdInfo.setInfoId(trdInfoFromSync.getAuctionID());
    custTrdInfo.setTrdType(SyncTrdTypeEnum.AUCTION.getId());
    custTrdInfo.setItemType(TypeConvertUtil.getItemTypeFromBidtypeEnum(BidTypeEnum.lookupByIdUtil(trdInfoFromSync.getBidType())).getId());
    custTrdInfo.setItemSubType(trdInfoFromSync.getBidType());
    custTrdInfo.setInfoTitle(StringUtils.isEmpty(trdInfoFromSync.getAuctionTitle())? null:trdInfoFromSync.getAuctionTitle() );
    if(trdInfoFromSync.getTrdDate() != null && trdInfoFromSync.getTrdDate() > 0L ){
      custTrdInfo.setTrdDate(AmcDateUtils.toUTCDate(trdInfoFromSync.getTrdDate()));
    }
//    if(trdInfoFromSync.getPublishDate() != null && trdInfoFromSync.getPublishDate() > 0L){
//      custTrdInfo.setPubDate(AmcDateUtils.toUTCDate(trdInfoFromSync.getPublishDate()));
//
//    }

    if(trdInfoFromSync.getUpdateTime() != null && trdInfoFromSync.getUpdateTime() > 0L ){
      custTrdInfo.setUpdateTime(AmcDateUtils.toUTCDate(trdInfoFromSync.getUpdateTime()));
    }
    custTrdInfo.setInfoUrl(trdInfoFromSync.getTrdContentUrl());
    if(StringUtils.isEmpty(trdInfoFromSync.getTrdProvinceCode())){
      log.error("trdInfoFromSync.getTrdProvinceCode() is empty");
      if(!errorTrdInfos.containsKey(trdInfoFromSync.getAuctionID())){
        errorTrdInfos.put(trdInfoFromSync.getAuctionID(), "");
      }
      errorTrdInfos.put(trdInfoFromSync.getAuctionID(), String.format("page:[%d] %s\n%s", pageForLog,
          errorTrdInfos.get(trdInfoFromSync.getAuctionID()),
          "trdInfoFromSync.getTrdProvinceCode() is empty"));
      return;
    }
    if(!trdInfoFromSync.getTrdProvinceCode().endsWith("000000")){
      log.error("getTrdProvinceCode:{}", trdInfoFromSync.getTrdProvinceCode());
    }
    custTrdInfo.setTrdProvince(trdInfoFromSync.getTrdProvinceCode().substring(0, 6));
    custTrdInfo.setDebtProvince(trdInfoFromSync.getBidProvinceCode().substring(0, 6));

    if(StringUtils.isEmpty(trdInfoFromSync.getBidCityCode())){
      if(!errorTrdInfos.containsKey(trdInfoFromSync.getAuctionID())){
        errorTrdInfos.put(trdInfoFromSync.getAuctionID(), "");
      }
      log.error("BidCityCode is empty {} of the trd  id:{}",
          trdInfoFromSync.getBidCityCode(), trdInfoFromSync.getAuctionID() );
      errorTrdInfos.put(trdInfoFromSync.getAuctionID(), String.format("page:[%d] %s\n%s",
       pageForLog,   errorTrdInfos.get(trdInfoFromSync.getAuctionID()),
            "trdInfoFromSync.getBidCityCode() is empty"));
    }else{
      if(!trdInfoFromSync.getBidCityCode().endsWith("000000")){
        log.error("getBidCityCode:{}", trdInfoFromSync.getBidCityCode());
        errorTrdInfos.put(trdInfoFromSync.getAuctionID(), String.format("page:[%d] %s\n%s",
         pageForLog,   errorTrdInfos.get(trdInfoFromSync.getAuctionID()),
            String.format("trdInfoFromSync.getBidCityCode() is %s",trdInfoFromSync.getBidCityCode())));
      }
      custTrdInfo.setDebtCity(trdInfoFromSync.getBidCityCode().substring(0, 6));
    }
//    custTrdInfo.setTrdAmountOrig(trdInfoFromSync.getDebtTotalAmount());
//    custTrdInfo.setTrdContactorName(StringUtils.isEmpty(trdInfoFromSync.getSellerContactManPrep()) ? "-1": trdInfoFromSync.getSellerContactManPrep());
//    StringBuilder sb = new StringBuilder();
//    if(!StringUtils.isEmpty(trdInfoFromSync.getSellerContactPhonePrep())){
//      sb.append(trdInfoFromSync.getSellerContactPhonePrep()).append(" ").append(trdInfoFromSync.getSellerContactAddressPrep());
//    }else if(!StringUtils.isEmpty(trdInfoFromSync.getSellerContactAddressPrep())){
//      sb.append(trdInfoFromSync.getSellerContactAddressPrep());
//    }
//    if(sb.length() > 0){
//      custTrdInfo.setTrdContactorAddr(sb.toString());
//    }



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


}


