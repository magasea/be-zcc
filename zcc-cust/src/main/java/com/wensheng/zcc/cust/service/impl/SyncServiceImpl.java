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
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfo;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdSeller;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdSellerExample;
import com.wensheng.zcc.cust.module.helper.CustTypeEnum;
import com.wensheng.zcc.cust.module.helper.sync.CustTypeSyncEnum;
import com.wensheng.zcc.cust.module.helper.sync.TrdInfoSyncTypeEnum;
import com.wensheng.zcc.cust.module.sync.CustInfoFromSync;
import com.wensheng.zcc.cust.module.sync.PageWrapperResp;
import com.wensheng.zcc.cust.module.sync.TrdInfoFromSync;
import com.wensheng.zcc.cust.service.ScriptSysService;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@Order(2)
public class SyncServiceImpl implements ScriptSysService {

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

    private final String getTrdInfoByBuyerType = "http://cl.wenshengamc.com/debts/getAll?type=%d&timeStart=&timeEnd=&seller=&page=%d&area=";
    private final String getDebtCust = "http://10.20.102.242:8085/debtCustomers/getJson?page=%d&pageSize=10&provinceName=%s";
    private final String getTrdInfoDetailByCust = "http://10.20.102.242:8085/debtCustomerDebts/getJson?customerId=%s&page=%d&pageSize=20";

    @PostConstruct
    void init(){
      restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
    }


//    @Scheduled(cron = "${spring.task.scheduling.cronExpr}")
//    public void doSyncTask(){
//      try {
//        doSynchWithScriptOn();
//      } catch (Exception e) {
//        e.printStackTrace();
//        log.error("failed to do scheduled task for syn with custs", e);
//      }
//
//      try {
//
//        doSynchWithCusts();
//      } catch (ParseException e) {
//        e.printStackTrace();
//        log.error("failed to do scheduled task for syn with trdInfo", e);
//      }
//
//
//    }

    @Override
    public void doSynchWithScriptOn(String province) throws Exception {

        //1. get cust list
        boolean stillHaveNext = true;
        int pageNum = 1;
//        while(stillHaveNext){
//            TrdInfoFromSync trdInfoFromSync = getTrdInfoList(TrdInfoSyncTypeEnum.DEBTPROCED, pageNum);
//            ProcessTrdInfoData(trdInfoFromSync);
//            if(++pageNum <= trdInfoFromSync.getPageNum() && !CollectionUtils.isEmpty(trdInfoFromSync.getList())){
//                stillHaveNext = true;
//            }else{
//                stillHaveNext = false;
//            }
//        }
        while(stillHaveNext){
            PageWrapperResp<CustInfoFromSync> custInfoFromSyncPageWrapperResp = getCustList(pageNum, province);
            ProcessCustInfoData(custInfoFromSyncPageWrapperResp);
            if (++pageNum <= custInfoFromSyncPageWrapperResp.getPages() && !CollectionUtils.isEmpty(custInfoFromSyncPageWrapperResp.getList())) {

                stillHaveNext = true;
            }else{
                stillHaveNext = false;
            }
        }
        //2. get


    }

  @Override
  public void doSynchWithCusts() throws ParseException {
    synchTrdInfoForCmpyCust();
    synchTrdInfoForPersonCust();
  }

  private void synchTrdInfoForPersonCust() throws ParseException {
      Query query;
      List<CustTrdPerson> custTrdPersonList = custTrdPersonMapper.selectByExample(null);
      for(CustTrdPerson custTrdPerson: custTrdPersonList){
        query = new Query();
        query.addCriteria(Criteria.where("custId").is(custTrdPerson.getId()).and("type").is(CustTypeEnum.PERSON.getId()));
        List<ImportCustRecord> importCustRecords = mongoTemplate.find(query, ImportCustRecord.class);
        if(CollectionUtils.isEmpty(importCustRecords)){
          log.error("there is no trad info for custId:{}", custTrdPerson.getId());
        }else if(importCustRecords.size() > 2){
          log.error("there is multiple origCustId:{} for the custId:{}",importCustRecords.
              stream().map(item -> item.getOriginId()).collect(Collectors.joining(",")),
              custTrdPerson.getId()) ;
        }else{
          String origCustId = importCustRecords.get(0).getOriginId();
          synchCustTrd(origCustId, custTrdPerson.getId(), CustTypeEnum.PERSON.getId());
        }
        query = null;
      }


  }

  private void synchCustTrd(String origCustId, Long custId, Integer custType) throws ParseException {
      int pageNum = 1;
      boolean hasNext = true;
      while(hasNext){
        PageWrapperResp<TrdInfoFromSync> pageWrapperResp =  getTrdInfoByCust(origCustId, pageNum);
        processTradInfo(pageWrapperResp.getList(), custId, custType);
        if(pageNum > pageWrapperResp.getPages() || CollectionUtils.isEmpty(pageWrapperResp.getList())){
          hasNext = false;
          break;
        }
        pageNum++;
      }

  }

  private void processTradInfo(List<TrdInfoFromSync> trdInfoFromSyncs, Long custId, Integer custType)
      throws ParseException {
      Query query ;
      boolean dataQualityIncrease2 = true;
      boolean eachDataQuality = false;
      int increaseStep = 0;
    for(TrdInfoFromSync trdInfoOrig: trdInfoFromSyncs){
      eachDataQuality = false;
      log.info(gson.toJson(trdInfoOrig));
      query = new Query();
      query.addCriteria(Criteria.where("originId").is(trdInfoOrig.getId()).and("origCustId").is(trdInfoOrig.getCustomerId()));
      List<ImportTrdInfoRecord> importTrdInfoRecords =  mongoTemplate.find(query, ImportTrdInfoRecord.class);
      if(CollectionUtils.isEmpty(importTrdInfoRecords)){
        eachDataQuality = makeNewTrdInfo(trdInfoOrig, custId, custType);
      }else{
        CustTrdInfo custTrdInfo = custTrdInfoMapper.selectByPrimaryKey(importTrdInfoRecords.get(0).getTrdId());
        if(custTrdInfo == null){
          eachDataQuality = makeNewTrdInfo(trdInfoOrig, custId, custType);
        }else{
          eachDataQuality =  copyTrdFromSync2Local(trdInfoOrig, custTrdInfo);
        }

      }
      if(eachDataQuality){
        increaseStep = 1;
      }

      dataQualityIncrease2 = dataQualityIncrease2&eachDataQuality;
    }
    if(dataQualityIncrease2){
      increaseStep = 2;
    }
    if(CustTypeEnum.COMPANY.getId() == custType){
      CustTrdCmpy custTrdCmpy = custTrdCmpyMapper.selectByPrimaryKey(custId);
      if(!StringUtils.isEmpty(custTrdCmpy.getCmpyPhone()) &&!custTrdCmpy.getCmpyPhone().equals("-1") && !StringUtils.isEmpty(custTrdCmpy.getCmpyAddr())&& !custTrdCmpy.getCmpyAddr().equals("-1")){
        custTrdCmpy.setDataQuality(custTrdCmpy.getDataQuality() + increaseStep);
      }else{
        custTrdCmpy.setDataQuality(0);
      }
      custTrdCmpyMapper.updateByPrimaryKeySelective(custTrdCmpy);
    }else if(CustTypeEnum.PERSON.getId() == custType){
      CustTrdPerson custTrdPerson = custTrdPersonMapper.selectByPrimaryKey(custId);
      if(!StringUtils.isEmpty(custTrdPerson.getMobileNum()) && !custTrdPerson.getMobileNum().equals("-1")){
        custTrdPerson.setDataQuality(custTrdPerson.getDataQuality() + increaseStep);
      }else if(increaseStep == 0){
        custTrdPerson.setDataQuality(1);
      }
      custTrdPersonMapper.updateByPrimaryKeySelective(custTrdPerson);
    }

  }

  private boolean copyTrdFromSync2Local(TrdInfoFromSync trdInfoOrig, CustTrdInfo custTrdInfo) throws ParseException {
      CustTrdInfo custTrdInfoUpdate = getTrdInfoFromOrigTrdInfo(trdInfoOrig, custTrdInfo.getBuyerId(),
          custTrdInfo.getBuyerType());
      custTrdInfoUpdate.setId(custTrdInfo.getId());
      custTrdInfoMapper.updateByPrimaryKeySelective(custTrdInfoUpdate);
      return custTrdInfoUpdate.getTotalAmount() > 0;

  }

  private boolean makeNewTrdInfo(TrdInfoFromSync trdInfoOrig, Long custId, Integer custType) throws ParseException {

    CustTrdInfo custTrdInfo = getTrdInfoFromOrigTrdInfo(trdInfoOrig, custId,custType);

    custTrdInfoMapper.insertSelective(custTrdInfo);
    Query query = new Query();
    query.addCriteria(Criteria.where("originId").is(trdInfoOrig.getId()));
    List<ImportTrdInfoRecord> importTrdInfoRecords = mongoTemplate.find(query, ImportTrdInfoRecord.class);
    if(CollectionUtils.isEmpty(importTrdInfoRecords)){
      Update update = new Update();
      update.set("custId", custId);
      update.set("origCustId", trdInfoOrig.getCustomerId());
      update.set("originId", trdInfoOrig.getId());
      update.set("trdId", custTrdInfo.getId());

      mongoTemplate.upsert(query, update, ImportTrdInfoRecord.class);
    }else{
          ImportTrdInfoRecord importTrdInfoRecord = importTrdInfoRecords.get(0);
    importTrdInfoRecord.setCustId(custId);
    importTrdInfoRecord.setOrigCustId(trdInfoOrig.getCustomerId());
    importTrdInfoRecord.setOriginId(trdInfoOrig.getId());
    importTrdInfoRecord.setTrdId(custTrdInfo.getId());
    mongoTemplate.save(importTrdInfoRecord);
    }
    return custTrdInfo.getTotalAmount() > 0;

  }

  private CustTrdInfo getTrdInfoFromOrigTrdInfo(TrdInfoFromSync trdInfoOrig, Long custId, Integer custType)
      throws ParseException {
    CustTrdSellerExample custTrdSellerExample = new CustTrdSellerExample();
    custTrdSellerExample.createCriteria().andNameEqualTo(trdInfoOrig.getSeller());
    CustTrdSeller sellerRecord = null;
    CustTrdInfo custTrdInfo = new CustTrdInfo();
    custTrdInfo.setBuyerId(custId);
    custTrdInfo.setBuyerType(custType);
    List<CustTrdSeller> custTrdSellers = custTrdSellerMapper.selectByExample(custTrdSellerExample);
    if(CollectionUtils.isEmpty(custTrdSellers) && !StringUtils.isEmpty(trdInfoOrig.getSeller())){
      CustTrdSeller custTrdSeller = new CustTrdSeller();
      custTrdSeller.setName(trdInfoOrig.getSeller());
      custTrdSeller.setType(trdInfoOrig.getSeller().length() > 5? CustTypeEnum.COMPANY.getId(): CustTypeEnum.PERSON.getId());
      custTrdSellerMapper.insertSelective(custTrdSeller);
      sellerRecord = custTrdSeller;
    }else{
      sellerRecord = custTrdSellers.get(0);
    }

    custTrdInfo.setSellerId(sellerRecord.getId());
    custTrdInfo.setTotalAmount(trdInfoOrig.getTrdAmount());
    custTrdInfo.setBuyerId(custId);
    custTrdInfo.setBuyerType(custType);
    custTrdInfo.setInfoId(trdInfoOrig.getId());
    custTrdInfo.setTrdType(trdInfoOrig.getInvType());
    custTrdInfo.setInfoTitle(trdInfoOrig.getTitle());
    if(trdInfoOrig.getUpdateTime() != null && !trdInfoOrig.getUpdateTime().equals("null")){
      custTrdInfo.setInfoTime(new Date(trdInfoOrig.getUpdateTime()));
    }

    if(trdInfoOrig.getTradeTime() != null && !trdInfoOrig.getTradeTime().equals("null")){
      custTrdInfo.setTrdDate(new Date(trdInfoOrig.getTradeTime()));
    }else{
      custTrdInfo.setTrdDate(AmcDateUtils.getDateFromStr("1900-01-01 00:00:00"));
    }
    custTrdInfo.setInfoUrl(trdInfoOrig.getUrl());
    custTrdInfo.setTrdProvince(trdInfoOrig.getTrdProvince());
    custTrdInfo.setTrdCity(trdInfoOrig.getTrdCity());
    custTrdInfo.setTrdAmountOrig(trdInfoOrig.getTrdAmountOrig());
    custTrdInfo.setTrdContactorName(trdInfoOrig.getName());
    custTrdInfo.setTrdContactorAddr(trdInfoOrig.getAddress());
    custTrdInfo.setDataStatus(trdInfoOrig.getDataStatus());
    return custTrdInfo;
  }

  private void synchTrdInfoForCmpyCust() throws ParseException {
    Query query;
    List<CustTrdCmpy> custTrdCmpyList = custTrdCmpyMapper.selectByExample(null);
    for(CustTrdCmpy custTrdCmpy: custTrdCmpyList){
      query = new Query();
      query.addCriteria(Criteria.where("custId").is(custTrdCmpy.getId()).and("type").is(CustTypeEnum.COMPANY.getId()));
      List<ImportCustRecord> importCustRecords = mongoTemplate.find(query, ImportCustRecord.class);
      if(CollectionUtils.isEmpty(importCustRecords)){
        log.error("there is no trad info for custId:{}", custTrdCmpy.getId());
      }else if(importCustRecords.size() > 2){
        log.error("there is multiple origCustId:{} for the custId:{}",importCustRecords.
                stream().map(item -> item.getOriginId()).collect(Collectors.joining(",")),
            custTrdCmpy.getId()) ;
      }else{
        String origCustId = importCustRecords.get(0).getOriginId();
        synchCustTrd(origCustId, custTrdCmpy.getId(), CustTypeEnum.COMPANY.getId());
      }
      query = null;
    }
  }

  private void ProcessCustInfoData(PageWrapperResp<CustInfoFromSync> custInfoFromSyncPageWrapperResp)
        throws Exception {

        for(CustInfoFromSync custItem: custInfoFromSyncPageWrapperResp.getList()){
            processCustInfoItem(custItem);
        }
    }

    @Transactional
    void processCustInfoItem(CustInfoFromSync custItem) throws Exception {
        log.info(gson.toJson(custItem));
        Query query = new Query();



        if(custItem.getType() == CustTypeSyncEnum.COMPANY.getId()){
          query.addCriteria(Criteria.where("originId").is(custItem.getId()).and("type").is(CustTypeEnum.COMPANY.getId()));
          List<ImportCustRecord> importCustCmpyRecordList =  mongoTemplate.find(query, ImportCustRecord.class);
            log.info("got company cust:{}", gson.toJson(custItem));


            if(CollectionUtils.isEmpty(importCustCmpyRecordList)){
                makeNewCustCmpyBySync(custItem, CustTypeEnum.COMPANY.getId());

            }else{
                CustTrdCmpy custTrdCmpy =  custTrdCmpyMapper.selectByPrimaryKey(importCustCmpyRecordList.get(0).getCustId());
                if(custTrdCmpy == null){
                    makeNewCustCmpyBySync(custItem, CustTypeEnum.COMPANY.getId());
                }else{
                    copyCustFromSync2Local(custItem, custTrdCmpy);
                    makeRelationWithCity(custTrdCmpy, custItem.getCitys());
                    custTrdCmpyMapper.updateByPrimaryKeySelective(custTrdCmpy);
                }
            }
        }else if(custItem.getType() == CustTypeSyncEnum.PERSON.getId()){

          query.addCriteria(Criteria.where("originId").is(custItem.getId()).and("type").is(CustTypeEnum.PERSON.getId()));
          List<ImportCustRecord> importCustPersonRecordList =  mongoTemplate.find(query, ImportCustRecord.class);
            if(CollectionUtils.isEmpty(importCustPersonRecordList)) {
                makeNewCustPersonBySync(custItem, CustTypeEnum.PERSON.getId());
            }else{
                CustTrdPerson custTrdPerson = custTrdPersonMapper.selectByPrimaryKey(importCustPersonRecordList.get(0).getCustId());
                if(custTrdPerson == null){
                    makeNewCustPersonBySync(custItem, CustTypeEnum.PERSON.getId());
                }else{
                    copyCustFromSync2Local(custItem, custTrdPerson);
                    makeRelationWithCity(custTrdPerson, custItem.getCitys());
                    custTrdPersonMapper.updateByPrimaryKeySelective(custTrdPerson);
                }
            }
        }
    }

  private void makeNewCustPersonBySync(CustInfoFromSync custItem, int type) throws Exception {

    CustTrdPerson custTrdPerson = new CustTrdPerson();


    copyCustFromSync2Local(custItem, custTrdPerson);
    custTrdPersonMapper.insertSelective(custTrdPerson);
    makeRelationWithCity(custTrdPerson, custItem.getCitys());
    Query query = new Query();
    query.addCriteria(Criteria.where("originId").is(custItem.getId()).and("type").is(type));
    Update update = new Update();
    update.set("custId", custTrdPerson.getId());
    update.set("originId", custItem.getId());
    update.set("type", type);

    mongoTemplate.upsert(query, update, ImportCustRecord.class);
  }

  private void makeNewCustCmpyBySync(CustInfoFromSync custItem, int type) throws Exception {
        CustTrdCmpy custTrdCmpy = new CustTrdCmpy();
        copyCustFromSync2Local(custItem, custTrdCmpy);
        custTrdCmpyMapper.insertSelective(custTrdCmpy);
        Query query = new Query();
        query.addCriteria(Criteria.where("originId").is(custItem.getId()).and("type").is(type));
        Update update = new Update();
        update.set("custId", custTrdCmpy.getId());
        update.set("originId", custItem.getId());
        update.set("type", type);

        mongoTemplate.upsert(query, update, ImportCustRecord.class);
    }

    private void copyCustFromSync2Local(CustInfoFromSync custItem, CustTrdCmpy custTrdCmpy)
        throws Exception {
        if(!StringUtils.isEmpty(custItem.getPhone())){
          custTrdCmpy.setCmpyPhone(custItem.getPhone());
//          String[] items = SyncUtils.getPhoneList(custItem.getPhone());
//          if(items.length >= 2){
//            custTrdCmpy.setCmpyPhone(items[0]);
//            custTrdCmpy.setAnnuReptPhone( String.join(",",Arrays.copyOf(items, 1)));
//          }else{
//            custTrdCmpy.setCmpyPhone(items[0]);
//            custTrdCmpy.setAnnuReptPhone(items[0]);
//          }
        }
        custTrdCmpy.setCmpyAddr(custItem.getAddress());
        custTrdCmpy.setCmpyName(custItem.getName());
        custTrdCmpy.setLegalReptive(custItem.getLinkMan());
        custTrdCmpy.setDataStatus(custItem.getDataStatus());
        int initDataQuality = -1;
        if(!StringUtils.isEmpty(custItem.getDebtCustomerCompany().getId())){
          custTrdCmpy.setCmpyAddr(custItem.getDebtCustomerCompany().getCmpyAddr());
          custTrdCmpy.setAnnuReptAddr(custItem.getDebtCustomerCompany().getAnnuReptAddr());
          custTrdCmpy.setCmpyPhone(custItem.getDebtCustomerCompany().getCmpyPhone());
          custTrdCmpy.setAnnuReptPhone(custItem.getDebtCustomerCompany().getAnnuReptPhone());
          custTrdCmpy.setLegalReptive(custItem.getDebtCustomerCompany().getLegalReptive());
          custTrdCmpy.setCmpyName(custItem.getDebtCustomerCompany().getCmpyName());
          custTrdCmpy.setUniSocialCode(custItem.getDebtCustomerCompany().getUniSocialCode());
          if(!StringUtils.isEmpty(custItem.getDebtCustomerCompany().getUniSocialCode())){
            initDataQuality += 1;
          }
          if(!StringUtils.isEmpty(custItem.getDebtCustomerCompany().getCmpyPhone()) || !StringUtils.isEmpty(custItem.getDebtCustomerCompany().getAnnuReptPhone())){
            initDataQuality += 1;
          }
        }
        custTrdCmpy.setDataQuality(initDataQuality);
    }

    private void copyCustFromSync2Local(CustInfoFromSync custItem, CustTrdPerson custTrdPerson)
        throws Exception {
        custTrdPerson.setAddr(custItem.getAddress());
      int initDataQuality = -1;
      if(!StringUtils.isEmpty(custItem.getPhone())){
        initDataQuality += 1;
        custTrdPerson.setMobileNum(custItem.getPhone());
//        String[] items = SyncUtils.getPhoneList(custItem.getPhone());
//        if(items.length >= 2){
//          custTrdPerson.setMobileNum(items[0]);
//          custTrdPerson.setTelNum( String.join(",",Arrays.copyOf(items, 1)));
//        }else{
//          custTrdPerson.setMobileNum(items[0]);
//          custTrdPerson.setTelNum(items[0]);
//        }
      }
      if(!StringUtils.isEmpty(custItem.getAddress())){
        initDataQuality += 1;
      }

        custTrdPerson.setEmail(custItem.getEmail());
        custTrdPerson.setName(custItem.getName());
        custTrdPerson.setDataStatus(custItem.getDataStatus());
        custTrdPerson.setDataQuality(initDataQuality);
    }

  private <T> void makeRelationWithCity(T custItem, String citys) throws Exception {
      if(StringUtils.isEmpty(citys) || citys.equalsIgnoreCase("null")){
        log.error("custItem's citys:{}", citys);
        return;
      }
    String[] cities = citys.split(",");
    for(String city: cities){
      String cityCode = getCodeByCityCode(city);
      if(!StringUtils.isEmpty(cityCode)){
        if(custItem instanceof CustTrdCmpy){
          CustTrdCmpy custTrdCmpy = (CustTrdCmpy) custItem;
          makeInterestInfo(custTrdCmpy, cityCode);
        }else if(custItem instanceof  CustTrdPerson){
          CustTrdPerson custTrdPerson = (CustTrdPerson) custItem;
          makeInterestInfo(custTrdPerson, cityCode);
        }

      }
    }
  }



  private   <T> void makeInterestInfo (T custItem, String city) {

    CustIntrstInfo custIntrstInfo = new CustIntrstInfo();
    CustIntrstInfoExample custIntrstInfoExample = new CustIntrstInfoExample();
    boolean isPerson = false;
    if(custItem instanceof CustTrdPerson){
      CustTrdPerson custTrdPerson = (CustTrdPerson) custItem;
      custIntrstInfoExample.createCriteria().andCustIdEqualTo(custTrdPerson.getId()).andCustTypeEqualTo(CustTypeEnum.PERSON.getId());
      isPerson = true;

    }else if(custItem instanceof CustTrdCmpy){
      CustTrdCmpy custTrdCmpy = (CustTrdCmpy) custItem;
      custIntrstInfoExample.createCriteria().andCustIdEqualTo(custTrdCmpy.getId()).andCustTypeEqualTo(CustTypeEnum.COMPANY.getId());

      isPerson = false;
    }
    List<CustIntrstInfo> custIntrstInfos = custIntrstInfoMapper.selectByExample(custIntrstInfoExample);
    Set<String> interestCities = custIntrstInfos.stream().map(item -> item.getIntrstCity()).collect(Collectors.toSet());
    if(interestCities.contains(city)){
      log.info("relation ship already established");
    }else{
      if(isPerson){
        CustTrdPerson custTrdPerson = (CustTrdPerson) custItem;
        custIntrstInfo.setCustId(custTrdPerson.getId());
        custIntrstInfo.setCustType(CustTypeEnum.PERSON.getId());

      }else{
        CustTrdCmpy custTrdCmpy = (CustTrdCmpy) custItem;
        custIntrstInfo.setCustId(custTrdCmpy.getId());
        custIntrstInfo.setCustType(CustTypeEnum.COMPANY.getId());

      }
      custIntrstInfo.setIntrstCity(city);
      custIntrstInfoMapper.insertSelective(custIntrstInfo);
    }
  }

  private String getCodeByCityCode(String cityName) throws Exception {
      if(StringUtils.isEmpty(cityName) || cityName.equalsIgnoreCase("null")){
        log.error(" invalid city name:{}", cityName);
        return null;
      }
        CustRegionExample custRegionExample = new CustRegionExample();
        custRegionExample.createCriteria().andNameEqualTo(cityName);
        List<CustRegion> custRegions = custRegionMapper.selectByExample(custRegionExample);
        if(CollectionUtils.isEmpty(custRegions)){
          if(cityName.contains("市")){
            cityName = cityName.substring(0, cityName.indexOf("市"));
            return getCodeByCityCode(cityName);
          }else{
            log.error("",ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.INVALID_REGION_NAME, cityName));
            return null;
//            throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.INVALID_REGION_NAME, cityName);
          }

        }else if(custRegions.size() > 1){
          log.error("",ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.INVALID_REGION_NAME, String.format
              ("There is more than one name like:%s code is:%s", cityName, custRegions.stream().map(item -> item.getId().toString()).collect(Collectors.joining(",")))));
          return null;
//            throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.INVALID_REGION_NAME, String.format
//                    ("There is more than one name like:%s code is:%s", cityName, custRegions.stream().map(item -> item.getId().toString()).collect(Collectors.joining(","))));
        }
        return custRegions.get(0).getId().toString();
    }


//    private void ProcessTrdInfoData(TrdInfoFromSync trdInfoFromSync) {
//        trdInfoFromSync.getList().stream().forEach(trdItem -> {
//            try {
//                processTrdInfoItem(trdItem);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//
//    }

//    private void processTrdInfoItem(TrdInfoFromSync.TrdItem trdItem) throws Exception {
//        CustTrdInfo custTrdInfo = new CustTrdInfo();
//        custTrdInfo.setBuyerType(SyncEnumUtils.convertTo(BuyerTypeSyncEnum.lookupByDisplayNameUtil(trdItem.getBuyerType())).getId());
//        updateOrModifyBuyer(trdItem.getId());
//
//    }

    private void updateOrModifyBuyer(String id) {
    }

    private TrdInfoFromSync getTrdInfoList(TrdInfoSyncTypeEnum trdInfoTypeEnum, int pageNum){

        return restTemplate.getForEntity(String.format(getTrdInfoByBuyerType, trdInfoTypeEnum.getId(), pageNum), TrdInfoFromSync.class).getBody();
    }

    private PageWrapperResp<CustInfoFromSync> getCustList(int pageNum, String province){
//      String provEncod = encodeUtf8(province);
      String url = String.format(getDebtCust, pageNum, province);
        return restTemplate.exchange(url, HttpMethod.GET, null,
            new ParameterizedTypeReference<PageWrapperResp<CustInfoFromSync>>() {}).getBody();
    }

  private PageWrapperResp<TrdInfoFromSync> getTrdInfoByCust(String originCustId, int pageNum){

    return restTemplate.exchange(String.format(getTrdInfoDetailByCust, originCustId, pageNum), HttpMethod.GET, null,
        new ParameterizedTypeReference<PageWrapperResp<TrdInfoFromSync>>() {}).getBody();
  }

    public void cleanUp(){
      mongoTemplate.dropCollection(ImportCustRecord.class);
    }

  /**
   * 将字符串转换成UTF-8编码
   * @param str
   * @return
   */
  public static String encodeUtf8(String str){
    String encode = "";
    try{
      encode = URLEncoder.encode(str, "UTF-8");
    }catch(Exception e){
      encode = str;
      log.info("将字符串[" + str + "]转换成UTF-8格式化出错！");
    }
    return encode;
  }
}
