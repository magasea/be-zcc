package com.wensheng.zcc.cust.service.impl;

import com.google.gson.Gson;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustIntrstInfoMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustRegionMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdCmpyMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdPersonMapper;
import com.wensheng.zcc.cust.module.dao.mongo.ImportCustRecord;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustIntrstInfo;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustIntrstInfoExample;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustRegion;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustRegionExample;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfo;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import com.wensheng.zcc.cust.module.helper.CustTypeEnum;
import com.wensheng.zcc.cust.module.helper.sync.BuyerTypeSyncEnum;
import com.wensheng.zcc.cust.module.helper.sync.CustTypeSyncEnum;
import com.wensheng.zcc.cust.module.helper.sync.TrdInfoSyncTypeEnum;
import com.wensheng.zcc.cust.module.sync.CustInfoFromSync;
import com.wensheng.zcc.cust.module.sync.PageWrapperResp;
import com.wensheng.zcc.cust.module.sync.TrdInfoFromSync;
import com.wensheng.zcc.cust.service.ScriptSysService;
import com.wensheng.zcc.cust.utils.sync.SyncEnumUtils;
import com.wensheng.zcc.cust.utils.sync.SyncUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
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
public class ScriptSysServiceImpl implements ScriptSysService {

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

    private Gson gson = new Gson();

    private final String getTrdInfoByBuyerType = "http://cl.wenshengamc.com/debts/getAll?type=%d&timeStart=&timeEnd=&seller=&page=%d&area=";
    private final String getDebtCust = "http://10.20.100.228:8085/debtCustomers/getJson?page=%d&pageSize=10";
    private final String getTrdInfoDetailByCust = "http://10.20.200.228:8085/debtCustomerDebts/getJson?customerId=%s&page=%d&pageSize=%d";

    @PostConstruct
    void init(){
      restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
    }


    @Override
    public void doSynchWithScriptOn() throws Exception {

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
            PageWrapperResp<CustInfoFromSync> custInfoFromSyncPageWrapperResp = getCustList(pageNum);
            ProcessCustInfoData(custInfoFromSyncPageWrapperResp);
            if (++pageNum <= custInfoFromSyncPageWrapperResp.getPages() && !CollectionUtils.isEmpty(custInfoFromSyncPageWrapperResp.getList())) {

                stillHaveNext = true;
            }else{
                stillHaveNext = false;
            }
        }
        //2. get


    }

    private void ProcessCustInfoData(PageWrapperResp<CustInfoFromSync> custInfoFromSyncPageWrapperResp)
        throws Exception {

        for(CustInfoFromSync custItem: custInfoFromSyncPageWrapperResp.getList()){
            processCustInfoItem(custItem);
        }
    }

    @Transactional
    void processCustInfoItem(CustInfoFromSync custItem) throws Exception {
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
          String[] items = SyncUtils.getPhoneList(custItem.getPhone());
          if(items.length >= 2){
            custTrdCmpy.setCmpyPhone(items[0]);
            custTrdCmpy.setAnnuReptPhone( String.join(",",Arrays.copyOf(items, 1)));
          }else{
            custTrdCmpy.setCmpyPhone(items[0]);
            custTrdCmpy.setAnnuReptPhone(items[0]);
          }
        }
        custTrdCmpy.setCmpyAddr(custItem.getAddress());
        custTrdCmpy.setCmpyName(custItem.getName());
        custTrdCmpy.setLegalReptive(custItem.getLinkMan());
    }

    private void copyCustFromSync2Local(CustInfoFromSync custItem, CustTrdPerson custTrdPerson)
        throws Exception {
        custTrdPerson.setAddr(custItem.getAddress());
      if(!StringUtils.isEmpty(custItem.getPhone())){
        String[] items = SyncUtils.getPhoneList(custItem.getPhone());
        if(items.length >= 2){
          custTrdPerson.setMobileNum(items[0]);
          custTrdPerson.setTelNum( String.join(",",Arrays.copyOf(items, 1)));
        }else{
          custTrdPerson.setMobileNum(items[0]);
          custTrdPerson.setTelNum(items[0]);
        }
      }
        custTrdPerson.setCity(getCodeByCityCode(custItem.getCitys()));
        custTrdPerson.setEmail(custItem.getEmail());
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


    private void ProcessTrdInfoData(TrdInfoFromSync trdInfoFromSync) {
        trdInfoFromSync.getList().stream().forEach(trdItem -> {
            try {
                processTrdInfoItem(trdItem);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    private void processTrdInfoItem(TrdInfoFromSync.TrdItem trdItem) throws Exception {
        CustTrdInfo custTrdInfo = new CustTrdInfo();
        custTrdInfo.setBuyerType(SyncEnumUtils.convertTo(BuyerTypeSyncEnum.lookupByDisplayNameUtil(trdItem.getBuyerType())).getId());
        updateOrModifyBuyer(trdItem.getId());

    }

    private void updateOrModifyBuyer(String id) {
    }

    private TrdInfoFromSync getTrdInfoList(TrdInfoSyncTypeEnum trdInfoTypeEnum, int pageNum){

        return restTemplate.getForEntity(String.format(getTrdInfoByBuyerType, trdInfoTypeEnum.getId(), pageNum), TrdInfoFromSync.class).getBody();
    }

    private PageWrapperResp<CustInfoFromSync> getCustList(int pageNum){

        return restTemplate.exchange(String.format(getDebtCust, pageNum), HttpMethod.GET, null, new ParameterizedTypeReference<PageWrapperResp<CustInfoFromSync>>() {}).getBody();
    }

    public void cleanUp(){
      mongoTemplate.dropCollection(ImportCustRecord.class);


    }
}
