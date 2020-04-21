package com.wensheng.zcc.cust.service.impl;

import com.google.gson.Gson;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustAmcCmpycontactorMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdCmpyMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdInfoMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.ext.CustAmcCmpycontactorExtMapper;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcCmpycontactor;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcCmpycontactorExample;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpyExample;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfo;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfoExample;
import com.wensheng.zcc.cust.module.dao.mysql.ext.CustAmcCmpycontactorExt;
import com.wensheng.zcc.cust.module.dao.mysql.ext.CustTrdCmpyExtExample;
import com.wensheng.zcc.cust.module.helper.CustTypeEnum;
import com.wensheng.zcc.cust.module.vo.CustAmcCmpycontactorExtVo;
import com.wensheng.zcc.cust.module.vo.CustAmcCmpycontactorTrdInfoVo;
import com.wensheng.zcc.cust.service.AmcContactorService;

import java.util.*;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class AmcContactorServiceImpl implements AmcContactorService {

  @Autowired
  CustAmcCmpycontactorMapper custAmcCmpycontactorMapper;

  @Autowired
  CustTrdCmpyMapper custTrdCmpyMapper;

  @Autowired
  CustTrdInfoMapper custTrdInfoMapper;

  @Autowired
  CustAmcCmpycontactorExtMapper custAmcCmpycontactorExtMapper;

  private static volatile Gson gson = new Gson();

  @Override
  public void createAmcCmpyContactor(CustAmcCmpycontactor custAmcCmpycontactor) throws Exception {
    //手机号放在mobileUpdate
    if(!StringUtils.isEmpty(custAmcCmpycontactor.getMobile())){
      custAmcCmpycontactor.setMobileUpdate(custAmcCmpycontactor.getMobile());
    }
    //固话号放在phoneUpdate
    if(!StringUtils.isEmpty(custAmcCmpycontactor.getPhone())){
      custAmcCmpycontactor.setPhoneUpdate(custAmcCmpycontactor.getPhone());
    }

    //first check name and phone unique
    List<CustAmcCmpycontactor>  custAmcCmpycontactors = queryCmpyContactorByPhoneNo(custAmcCmpycontactor);

    if(!CollectionUtils.isEmpty(custAmcCmpycontactors)){
      //cannot insert
      log.error("There is already person name:{} company name:{} phone:{} reject insert",
          custAmcCmpycontactor.getName(),
          custAmcCmpycontactor.getCompany(), custAmcCmpycontactor.getMobile());
      throw ExceptionUtils.getAmcException(AmcExceptions.DUPLICATE_RECORD_INSERT_ERROR, String.format("已经 "
              + "有姓名为:%s 所属公司Id为:%s 电话为:%s 的记录, 请勿重复插入",
          custAmcCmpycontactor.getName(),
          custAmcCmpycontactor.getCmpyId(), custAmcCmpycontactor.getMobile()));
    }

    custAmcCmpycontactorMapper.insertSelective(custAmcCmpycontactor);
  }

  /**
   * 根据公司名称,联系人姓名,联系人电话号查找公司联系人
   * @param custAmcCmpycontactor
   * @return
   */
  private List<CustAmcCmpycontactor> queryCmpyContactorByPhoneNo(CustAmcCmpycontactor custAmcCmpycontactor){

    List<String> phoneUpdateList = Arrays.asList(custAmcCmpycontactor.getPhoneUpdate().split(";"));
    List<String> mobileUpdateList = Arrays.asList(custAmcCmpycontactor.getMobileUpdate().split(";"));
    return custAmcCmpycontactorExtMapper.selectCmpyContactor(custAmcCmpycontactor.getCompany(),
            custAmcCmpycontactor.getName(), phoneUpdateList, mobileUpdateList);
  }

  @Override
  public void updateAmcCmpyContactor(CustAmcCmpycontactor custAmcCmpycontactor) throws Exception{
    CustAmcCmpycontactor originalCmpycontactor =custAmcCmpycontactorMapper.selectByPrimaryKey(custAmcCmpycontactor.getId());
    if(null == originalCmpycontactor){
      log.error("There is no person in db name:{} company name:{} phone:{} reject insert",
              custAmcCmpycontactor.getName(),
              custAmcCmpycontactor.getCompany(), custAmcCmpycontactor.getMobile());
      throw ExceptionUtils.getAmcException(AmcExceptions.NO_CMPY_CONTACTOR_ERROR, String.format("数据库中没有对应的公司联系人"
                      + "姓名为:%s 所属公司Id为:%s 电话为:%s", custAmcCmpycontactor.getName(),
              custAmcCmpycontactor.getCmpyId(), custAmcCmpycontactor.getMobile()));
    }

    //手机号放在mobileUpdate
    if(!StringUtils.isEmpty(custAmcCmpycontactor.getMobile())){
      custAmcCmpycontactor.setMobileUpdate(custAmcCmpycontactor.getMobile());
    }
    //固话号放在phoneUpdate
    if(!StringUtils.isEmpty(custAmcCmpycontactor.getPhone())){
      custAmcCmpycontactor.setPhoneUpdate(custAmcCmpycontactor.getPhone());
    }

    //若改变了手机号和固话号，存入历史数据时要去重
    if(null != custAmcCmpycontactor.getMobileUpdate() &&
            !custAmcCmpycontactor.getMobileUpdate().equals(originalCmpycontactor.getMobileUpdate())){
      if("-1".equals(originalCmpycontactor.getMobileHistory())){
        custAmcCmpycontactor.setMobileHistory(originalCmpycontactor.getMobileUpdate());
      }else {
        String mobileHistory = String.format("%s;%s",originalCmpycontactor.getMobileHistory(),
                custAmcCmpycontactor.getMobileUpdate());
        Set<String> mobileHistorySet = new HashSet(Arrays.asList(mobileHistory.split(";")));
        StringBuilder sbmobileHistory = new StringBuilder();
        for (String mobile : mobileHistorySet) {
          if(sbmobileHistory.length() >1){
            sbmobileHistory.append(";");
          }
          sbmobileHistory.append(mobile);
        }
        custAmcCmpycontactor.setMobileHistory(sbmobileHistory.toString());
      }
    }

    if(null != custAmcCmpycontactor.getPhoneUpdate() &&
            !custAmcCmpycontactor.getPhoneUpdate().equals(originalCmpycontactor.getPhoneUpdate())){
      if("-1".equals(originalCmpycontactor.getPhoneHistory())){
        custAmcCmpycontactor.setPhoneHistory(originalCmpycontactor.getPhoneUpdate());
      }else {
        String phoneHistory = String.format("%s;%s",originalCmpycontactor.getPhoneHistory(),
                custAmcCmpycontactor.getPhoneUpdate());
        Set<String> phoneHistorySet = new HashSet(Arrays.asList(phoneHistory.split(";")));
        StringBuilder sbPhoneHistory = new StringBuilder();
        for (String phone : phoneHistorySet) {
          if(sbPhoneHistory.length() >1){
            sbPhoneHistory.append(";");
          }
          sbPhoneHistory.append(phone);
        }
        custAmcCmpycontactor.setPhoneHistory(sbPhoneHistory.toString());
      }
    }

    custAmcCmpycontactorMapper.updateByPrimaryKeySelective(custAmcCmpycontactor);
  }

  @Override
  public List<CustAmcCmpycontactor> getCmpyAmcContactor(String cmpyName) {
    CustAmcCmpycontactorExample custAmcCmpycontactorExample = new CustAmcCmpycontactorExample();
    custAmcCmpycontactorExample.createCriteria().andCompanyEqualTo(cmpyName);
    List<CustAmcCmpycontactor> custAmcCmpycontactors =
        custAmcCmpycontactorMapper.selectByExample(custAmcCmpycontactorExample);
    return custAmcCmpycontactors;
  }

  @Override
  public List<CustAmcCmpycontactorExtVo> getCmpyAmcContactor(Long cmpyId) {
    List<CustAmcCmpycontactorExtVo> custAmcCmpycontactorExtVos = null;
    CustAmcCmpycontactorExample custAmcCmpycontactorExample = new CustAmcCmpycontactorExample();
    custAmcCmpycontactorExample.setOrderByClause(" id desc ");
    custAmcCmpycontactorExample.createCriteria().andCmpyIdEqualTo(cmpyId);
    List<CustAmcCmpycontactor> custAmcCmpycontactors =
            custAmcCmpycontactorMapper.selectByExample(custAmcCmpycontactorExample);
    if(CollectionUtils.isEmpty(custAmcCmpycontactors)){
      return new ArrayList<>();
    }

    CustTrdInfoExample custTrdInfoExample = new CustTrdInfoExample();
    custTrdInfoExample.createCriteria().andBuyerIdEqualTo(cmpyId).andBuyerTypeEqualTo(CustTypeEnum.COMPANY.getId());
    List<CustTrdInfo> custTrdInfos = custTrdInfoMapper.selectByExample(custTrdInfoExample);
    if(CollectionUtils.isEmpty(custTrdInfos)){
      custAmcCmpycontactorExtVos = getPureContactors(custAmcCmpycontactors);
      return custAmcCmpycontactorExtVos;
    }

    Map<String, CustAmcCmpycontactorExt> custAmcCmpycontactorMap = new HashMap<>();
//    List<CustAmcCmpycontactorExt> custAmcCmpycontactorExts = new ArrayList<>();
    for(CustAmcCmpycontactor custAmcCmpycontactor: custAmcCmpycontactors){

      CustAmcCmpycontactorExt custAmcCmpycontactorExt = new CustAmcCmpycontactorExt();
      custAmcCmpycontactorExt.setCustAmcCmpycontactor(custAmcCmpycontactor);
      custAmcCmpycontactorExt.setCustTrdInfoList(new ArrayList<>());
//      custAmcCmpycontactorExts.add(custAmcCmpycontactorExt);

      custAmcCmpycontactorMap.put(String.format("%s%s",custAmcCmpycontactor.getName(), custAmcCmpycontactor.getTrdPhone()),
          custAmcCmpycontactorExt);


    }

    StringBuilder key = new StringBuilder();


    for(CustTrdInfo custTrdInfo: custTrdInfos){
      if(StringUtils.isEmpty(custTrdInfo.getTrdContactorName()) || custTrdInfo.getTrdContactorName().equals("-1") ||
              StringUtils.isEmpty(custTrdInfo.getTrdContactorAddr()) || custTrdInfo.getTrdContactorAddr().equals("-1")){
        continue;
      }
      key.setLength(0);
      key.append(custTrdInfo.getTrdContactorName());
      String phone = getPhoneFromTrdInfo(custTrdInfo);
      if(null == phone){
        continue;
      }
      key.append(phone);

      if(!custAmcCmpycontactorMap.containsKey(key.toString())){


//        log.error(" the trdInfo:{} doesn't belong to any contancator", custTrdInfo.getId());
        CustTrdCmpy custTrdCmpy = custTrdCmpyMapper.selectByPrimaryKey(cmpyId);
        CustAmcCmpycontactor custAmcCmpycontactor = initCmpyAmcContactorByTrdInfo(custTrdInfo, cmpyId, custTrdCmpy.getCmpyName() );
        if(null == custAmcCmpycontactor){
          continue;
        }
        CustAmcCmpycontactorExt custAmcCmpycontactorExt = new CustAmcCmpycontactorExt();
        custAmcCmpycontactorExt.setCustAmcCmpycontactor(custAmcCmpycontactor);
        custAmcCmpycontactorExt.setCustTrdInfoList(new ArrayList<>());
//        custAmcCmpycontactorExts.add(custAmcCmpycontactorExt);
        custAmcCmpycontactorMap.put(String.format("%s%s", custAmcCmpycontactor.getName(), custAmcCmpycontactor.getTrdPhone()), custAmcCmpycontactorExt);
      }
      custAmcCmpycontactorMap.get(key.toString()).getCustTrdInfoList().add(custTrdInfo);

    }
    //loop map to delete dirty contactor

    // Get the iterator over the HashMap
    Iterator<Map.Entry<String, CustAmcCmpycontactorExt> >
            iterator = custAmcCmpycontactorMap.entrySet().iterator();

    // Iterate over the HashMap
    while (iterator.hasNext()){
      Map.Entry<String, CustAmcCmpycontactorExt> entry = iterator.next();
      if(entry.getValue() == null||entry.getValue().getCustAmcCmpycontactor() == null ){
        continue;
      }
      if(entry.getValue().getCustAmcCmpycontactor().getCreateBy() != null && entry.getValue().getCustAmcCmpycontactor().getCreateBy() == -1L
              && entry.getValue().getCustAmcCmpycontactor().getUpdateBy() == -1L &&CollectionUtils.isEmpty(entry.getValue().getCustTrdInfoList()) ){
        custAmcCmpycontactorMapper.deleteByPrimaryKey(entry.getValue().getCustAmcCmpycontactor().getId());
        iterator.remove();
      }

    }


     custAmcCmpycontactorExtVos = convertToVos(new ArrayList(custAmcCmpycontactorMap.values()));
    custAmcCmpycontactorMap = null;
    return custAmcCmpycontactorExtVos;
  }

  @Override
  public List<CustAmcCmpycontactorExtVo> getCmpyAmcContactorNew(Long cmpyId) {
    List<CustAmcCmpycontactorExtVo> custAmcCmpycontactorExtVos = null;

    //查询公司联系人
    CustAmcCmpycontactorExample custAmcCmpycontactorExample = new CustAmcCmpycontactorExample();
    custAmcCmpycontactorExample.setOrderByClause(" id desc ");
    custAmcCmpycontactorExample.createCriteria().andCmpyIdEqualTo(cmpyId);
    List<CustAmcCmpycontactor> custAmcCmpycontactors =
            custAmcCmpycontactorMapper.selectByExample(custAmcCmpycontactorExample);

    //存储联系人和交易信息MAP
    Map<Long, CustAmcCmpycontactorExt> custAmcCmpycontactorMap = new HashMap<>();

    //查询交易信息
    CustTrdInfoExample custTrdInfoExample = new CustTrdInfoExample();
    custTrdInfoExample.createCriteria().andBuyerIdEqualTo(cmpyId).andBuyerTypeEqualTo(CustTypeEnum.COMPANY.getId());
    List<CustTrdInfo> custTrdInfos = custTrdInfoMapper.selectByExample(custTrdInfoExample);
    if(CollectionUtils.isEmpty(custTrdInfos)){
      custAmcCmpycontactorExtVos = getPureContactors(custAmcCmpycontactors);
      return custAmcCmpycontactorExtVos;
    }

    //根据数据库公司联系人初始化custAmcCmpycontactorMap
    for(CustAmcCmpycontactor custAmcCmpycontactor: custAmcCmpycontactors){
      CustAmcCmpycontactorExt custAmcCmpycontactorExt = new CustAmcCmpycontactorExt();
      custAmcCmpycontactorExt.setCustAmcCmpycontactor(custAmcCmpycontactor);
      custAmcCmpycontactorExt.setCustTrdInfoList(new ArrayList<>());
      custAmcCmpycontactorMap.put(custAmcCmpycontactor.getId(), custAmcCmpycontactorExt);
    }

    //查询公司信息
    CustTrdCmpy custTrdCmpy = custTrdCmpyMapper.selectByPrimaryKey(cmpyId);

    //根据交易信息组装数据custAmcCmpycontactorMap
    for (CustTrdInfo custTrdInfo : custTrdInfos) {
      //有trdCmpycontactorId，组装数据
      if(custTrdInfo.getTrdCmpycontactorId()==null || custTrdInfo.getTrdCmpycontactorId() != -1l){
        if(null != custAmcCmpycontactorMap.get(custTrdInfo.getTrdCmpycontactorId())){
          custAmcCmpycontactorMap.get(custTrdInfo.getTrdCmpycontactorId()).getCustTrdInfoList().add(custTrdInfo);
        }
      }else if(StringUtils.isEmpty(custTrdInfo.getTrdContactorName()) || custTrdInfo.getTrdContactorName().equals("-1") ||
              StringUtils.isEmpty(custTrdInfo.getTrdContactorAddr()) || custTrdInfo.getTrdContactorAddr().equals("-1")){
        continue;
      }else {
        //给交易初始化公司联系人
        CustAmcCmpycontactor custAmcCmpycontactor = initCmpyAmcContactorByTrdInfoNew(custTrdInfo, cmpyId, custTrdCmpy.getCmpyName() );
        if(null == custAmcCmpycontactor){
          continue;
        }
        if(null != custAmcCmpycontactorMap.get(custAmcCmpycontactor.getId())){
          custAmcCmpycontactorMap.get(custAmcCmpycontactor.getId()).getCustTrdInfoList().add(custTrdInfo);
        }else {
          CustAmcCmpycontactorExt custAmcCmpycontactorExt = new CustAmcCmpycontactorExt();
          custAmcCmpycontactorExt.setCustAmcCmpycontactor(custAmcCmpycontactor);
          custAmcCmpycontactorExt.setCustTrdInfoList(new ArrayList<>());
          custAmcCmpycontactorExt.getCustTrdInfoList().add(custTrdInfo);
          //组装数据
          custAmcCmpycontactorMap.put(custAmcCmpycontactor.getId(), custAmcCmpycontactorExt);
        }
      }
    }

    custAmcCmpycontactorExtVos = convertToVos(new ArrayList(custAmcCmpycontactorMap.values()));
    custAmcCmpycontactorMap = null;
    return custAmcCmpycontactorExtVos;
  }


  private String getPhoneFromTrdInfo(CustTrdInfo custTrdInfo){
    if(StringUtils.isEmpty(custTrdInfo.getTrdContactorPhone()) && !custTrdInfo.getTrdContactorPhone().equals("-1")){
      return custTrdInfo.getTrdContactorPhone();
    }else if( !StringUtils.isEmpty(custTrdInfo.getTrdContactorAddr())){

      String[] phoneAndAddr = null;
      if(!StringUtils.isEmpty(custTrdInfo.getTrdContactorAddr())){
        phoneAndAddr  = custTrdInfo.getTrdContactorAddr().split(" ");
        if(phoneAndAddr.length >= 2){
          return phoneAndAddr[0];

        }else{
          if(Character.isDigit(phoneAndAddr[0].charAt(0))){
            return phoneAndAddr[0];
          }
        }
      }
    }
    return null;
  }

  @Override
  public CustAmcCmpycontactorTrdInfoVo getCmpyAmcContactorDetail(Long contactorId) {
    CustAmcCmpycontactorTrdInfoVo custAmcCmpycontactorTrdInfoVo = new CustAmcCmpycontactorTrdInfoVo();
    CustAmcCmpycontactor custAmcCmpycontactor = custAmcCmpycontactorMapper.selectByPrimaryKey(contactorId);
    if(custAmcCmpycontactor == null || custAmcCmpycontactor.getCmpyId() == -1L){
      custAmcCmpycontactorTrdInfoVo.setCustAmcCmpycontactor(custAmcCmpycontactor);
      return custAmcCmpycontactorTrdInfoVo;
    }

    CustTrdInfoExample custTrdInfoExample = new CustTrdInfoExample();
    custTrdInfoExample.createCriteria().andBuyerIdEqualTo(custAmcCmpycontactor.getCmpyId())
        .andBuyerTypeEqualTo(CustTypeEnum.COMPANY.getId()).andTrdContactorNameEqualTo(custAmcCmpycontactor.getName());
    List<CustTrdInfo> custTrdInfos = custTrdInfoMapper.selectByExample(custTrdInfoExample);
    custAmcCmpycontactorTrdInfoVo.setCustTrdInfoList(new ArrayList<>());
    for(CustTrdInfo custTrdInfo : custTrdInfos){
      if(custTrdInfo.getTrdContactorName().equals(custAmcCmpycontactor.getName())){
        //get phone info to compare
        String phone = getPhoneFromTrdInfo(custTrdInfo);
        if(custAmcCmpycontactor.getTrdPhone().equals(phone)){
          custAmcCmpycontactorTrdInfoVo.getCustTrdInfoList().add(custTrdInfo);
        }
      }
    }
    return custAmcCmpycontactorTrdInfoVo;
  }

  private List<CustAmcCmpycontactorExtVo> getPureContactors(List<CustAmcCmpycontactor> custAmcCmpycontactors) {
    List<CustAmcCmpycontactorExtVo> custAmcCmpycontactorExtVos = new ArrayList<>();
    for(CustAmcCmpycontactor custAmcCmpycontactor: custAmcCmpycontactors){
      CustAmcCmpycontactorExtVo custAmcCmpycontactorExtVo = new CustAmcCmpycontactorExtVo();
      custAmcCmpycontactorExtVo.setCustAmcCmpycontactor(custAmcCmpycontactor);
      custAmcCmpycontactorExtVo.setFavorCityPrep(new HashMap<>());
      custAmcCmpycontactorExtVo.setFavorTypePrep(new HashMap<>());
      custAmcCmpycontactorExtVos.add(custAmcCmpycontactorExtVo);
    }
    return custAmcCmpycontactorExtVos;
  }

  private List<CustAmcCmpycontactorExtVo> convertToVos(List<CustAmcCmpycontactorExt> custAmcCmpycontactorExts) {
    List<CustAmcCmpycontactorExtVo> custAmcCmpycontactorExtVos = new ArrayList<>();
    for(CustAmcCmpycontactorExt custAmcCmpycontactorExt: custAmcCmpycontactorExts){
      CustAmcCmpycontactorExtVo custAmcCmpycontactorExtVo = new CustAmcCmpycontactorExtVo();
      custAmcCmpycontactorExtVo.setCustAmcCmpycontactor(new CustAmcCmpycontactor());
      custAmcCmpycontactorExtVo.setFavorTypePrep(new HashMap<>());
      custAmcCmpycontactorExtVo.setFavorCityPrep(new HashMap<>());
      convertToVo(custAmcCmpycontactorExt, custAmcCmpycontactorExtVo);
      custAmcCmpycontactorExtVos.add(custAmcCmpycontactorExtVo);
    }
    return custAmcCmpycontactorExtVos;

  }

  private void convertToVo(CustAmcCmpycontactorExt custAmcCmpycontactorExt,
      CustAmcCmpycontactorExtVo custAmcCmpycontactorExtVo) {

    AmcBeanUtils.copyProperties(custAmcCmpycontactorExt.getCustAmcCmpycontactor(), custAmcCmpycontactorExtVo.getCustAmcCmpycontactor());
    Map<String, Integer> favorCities = new HashMap<>();
    Map<String, Integer> favorTypes = new HashMap<>();
    int cityCnt = 0;
    int typeCnt = 0;
    for(CustTrdInfo custTrdInfo: custAmcCmpycontactorExt.getCustTrdInfoList()){
      if(!StringUtils.isEmpty(custTrdInfo.getDebtCity())){
        if(favorCities.containsKey(custTrdInfo.getDebtCity())){
          cityCnt = favorCities.get(custTrdInfo.getDebtCity());
        }else{
          cityCnt = 0;
        }
        cityCnt += 1;
        favorCities.put(custTrdInfo.getDebtCity(), cityCnt);
      }

      if(custTrdInfo.getTrdType() > 0){
        String favSubType = String.format("%d%d", custTrdInfo.getTrdType(), custTrdInfo.getItemType());
        if(favorTypes.containsKey(favSubType)){
          typeCnt = favorTypes.get(favSubType);
        }else{
          typeCnt = 0;
        }
        typeCnt += 1;
        favorTypes.put(favSubType, typeCnt);
      }
    }
    custAmcCmpycontactorExtVo.setFavorCityPrep(favorCities);
    custAmcCmpycontactorExtVo.setFavorTypePrep(favorTypes);

  }


  private CustAmcCmpycontactor initCmpyAmcContactorByTrdInfo(CustTrdInfo custTrdInfo, Long cmpyId, String cmpyName){
    if(null == custTrdInfo.getTrdContactorName() || custTrdInfo.getTrdContactorName().equals("-1") || custTrdInfo.getTrdContactorAddr().equals("-1") || null == custTrdInfo.getTrdContactorAddr()){
      //not valid info for trdContactorName and trdContactorPhone
      return null;
    }
    CustAmcCmpycontactor custAmcCmpycontactor = new CustAmcCmpycontactor();
    custAmcCmpycontactor.setName(custTrdInfo.getTrdContactorName());
    custAmcCmpycontactor.setCompany(cmpyName);
    custAmcCmpycontactor.setCmpyId(cmpyId);
    if(!StringUtils.isEmpty(custTrdInfo.getTrdContactorPhone()) && !custTrdInfo.getTrdContactorPhone().equals("-1")

    ){

//              custAmcCmpycontactor.setMobile(custTrdInfo.getTrdContactorPhone());

      custAmcCmpycontactor.setAddress(custTrdInfo.getTrdContactorAddress());
      custAmcCmpycontactor.setTrdPhone(custTrdInfo.getTrdContactorPhone());
      custAmcCmpycontactor.setMobile(custTrdInfo.getTrdContactorPhone());
    }else{
      String[] phoneAndAddr = null;
      if(!StringUtils.isEmpty(custTrdInfo.getTrdContactorAddr())){
        phoneAndAddr  = custTrdInfo.getTrdContactorAddr().split(" ");
        if(phoneAndAddr.length >= 2){
          custAmcCmpycontactor.setTrdPhone(phoneAndAddr[0]);

          custAmcCmpycontactor.setMobile(phoneAndAddr[0]);

          custAmcCmpycontactor.setAddress(phoneAndAddr[1]);
        }else{
          if(Character.isDigit(phoneAndAddr[0].charAt(0))){

            custAmcCmpycontactor.setMobile(phoneAndAddr[0]);

            custAmcCmpycontactor.setTrdPhone(phoneAndAddr[0]);
          }else{
            custAmcCmpycontactor.setAddress(phoneAndAddr[0]);
          }
        }
      }
    }
    if(StringUtils.isEmpty(custAmcCmpycontactor.getMobile()) || StringUtils.isEmpty(custAmcCmpycontactor.getName()) || StringUtils.isEmpty(custAmcCmpycontactor.getTrdPhone())){
      //no phone or no name no need
      return null;
    }
    CustAmcCmpycontactorExample custAmcCmpycontactorExample = new CustAmcCmpycontactorExample();
    custAmcCmpycontactorExample.createCriteria().andCmpyIdEqualTo(cmpyId)
            .andNameEqualTo(custAmcCmpycontactor.getName())
            .andTrdPhoneEqualTo(custAmcCmpycontactor.getTrdPhone());
    List<CustAmcCmpycontactor> custAmcCmpycontactors = custAmcCmpycontactorMapper.selectByExample(custAmcCmpycontactorExample);
    if(CollectionUtils.isEmpty(custAmcCmpycontactors)){
      custAmcCmpycontactorMapper.insertSelective(custAmcCmpycontactor);
      return custAmcCmpycontactor;
    }
    return  null;

  }

  /**
   *
   * @param custTrdInfo
   * @param cmpyId
   * @param cmpyName
   * @return
   */
  private CustAmcCmpycontactor initCmpyAmcContactorByTrdInfoNew(CustTrdInfo custTrdInfo, Long cmpyId, String cmpyName){
    if(null == custTrdInfo.getTrdContactorName() || custTrdInfo.getTrdContactorName().equals("-1") ||
            custTrdInfo.getTrdContactorAddr().equals("-1") || null == custTrdInfo.getTrdContactorAddr()){
      //not valid info for trdContactorName and trdContactorPhone
      return null;
    }

    CustAmcCmpycontactor custAmcCmpycontactor = new CustAmcCmpycontactor();
    custAmcCmpycontactor.setName(custTrdInfo.getTrdContactorName());
    custAmcCmpycontactor.setCompany(cmpyName);
    custAmcCmpycontactor.setCmpyId(cmpyId);
    //对应的电话部分
    if(!StringUtils.isEmpty(custTrdInfo.getTrdContactorPhone()) && !custTrdInfo.getTrdContactorPhone().equals("-1")){
      custAmcCmpycontactor.setAddress(custTrdInfo.getTrdContactorAddress());
      custAmcCmpycontactor.setTrdPhone(custTrdInfo.getTrdContactorPhone());
      custAmcCmpycontactor.setMobile(custTrdInfo.getTrdContactorPhone());
      custAmcCmpycontactor.setPhonePrep(custTrdInfo.getTrdContactorTel());
      custAmcCmpycontactor.setMobilePrep(custTrdInfo.getTrdContactorMobile());
    }else{
      String[] phoneAndAddr = null;
      if(!StringUtils.isEmpty(custTrdInfo.getTrdContactorAddr())){
        phoneAndAddr  = custTrdInfo.getTrdContactorAddr().split(" ");
        if(phoneAndAddr.length >= 2){
          custAmcCmpycontactor.setTrdPhone(phoneAndAddr[0]);

          custAmcCmpycontactor.setMobile(phoneAndAddr[0]);

          custAmcCmpycontactor.setAddress(phoneAndAddr[1]);
        }else{
          if(Character.isDigit(phoneAndAddr[0].charAt(0))){

            custAmcCmpycontactor.setMobile(phoneAndAddr[0]);

            custAmcCmpycontactor.setTrdPhone(phoneAndAddr[0]);
          }else{
            custAmcCmpycontactor.setAddress(phoneAndAddr[0]);
          }
        }
      }
    }
    if(StringUtils.isEmpty(custAmcCmpycontactor.getMobile()) || StringUtils.isEmpty(custAmcCmpycontactor.getName()) ||
            StringUtils.isEmpty(custAmcCmpycontactor.getTrdPhone())){
      //no phone or no name no need
      return null;
    }

    List<CustAmcCmpycontactor>custAmcCmpycontactors =null;
    if("-1".equals(custTrdInfo.getTrdContactorTel()) && "-1".equals(custTrdInfo.getTrdContactorMobile()) ){
      CustAmcCmpycontactorExample custAmcCmpycontactorExample = new CustAmcCmpycontactorExample();
      custAmcCmpycontactorExample.createCriteria().andCmpyIdEqualTo(cmpyId)
              .andNameEqualTo(custAmcCmpycontactor.getName())
              .andTrdPhoneEqualTo(custAmcCmpycontactor.getTrdPhone());
       custAmcCmpycontactors = custAmcCmpycontactorMapper.selectByExample(custAmcCmpycontactorExample);
    }else {
      List<String> phoneUpdateList = Arrays.asList(custTrdInfo.getTrdContactorTel().split(";"));
      List<String> mobileUpdateList = Arrays.asList(custTrdInfo.getTrdContactorMobile().split(";"));
      custAmcCmpycontactors = custAmcCmpycontactorExtMapper.selectCmpyContactor(
              cmpyName, custTrdInfo.getTrdContactorName(), phoneUpdateList, mobileUpdateList);
    }

    if(CollectionUtils.isEmpty(custAmcCmpycontactors)){
      custAmcCmpycontactorMapper.insertSelective(custAmcCmpycontactor);
      custTrdInfo.setTrdCmpycontactorId(custAmcCmpycontactor.getId());
      custTrdInfoMapper.updateByPrimaryKeySelective(custTrdInfo);
      return custAmcCmpycontactor;
    }else{
      custTrdInfo.setTrdCmpycontactorId(custAmcCmpycontactors.get(0).getId());
      custTrdInfoMapper.updateByPrimaryKeySelective(custTrdInfo);
      return custAmcCmpycontactors.get(0);
    }
  }



  @Override
  public void initCmpyAmcContactor() {
    //travers company table
    CustTrdCmpyExample custTrdCmpyExample = new CustTrdCmpyExample();
    custTrdCmpyExample.setOrderByClause(" id desc ");
    int offset = 0;
    int pageSize = 20;
    RowBounds rowBounds = new RowBounds(offset, pageSize);
    List<CustTrdCmpy> custTrdCmpyList =
    custTrdCmpyMapper.selectByExampleWithRowbounds(custTrdCmpyExample, rowBounds);
    boolean keepDoing = false;
    if(CollectionUtils.isEmpty(custTrdCmpyList)){
      keepDoing = false;
      return;
    }else{
      keepDoing = true;
    }

    CustTrdInfoExample custTrdInfoExample = new CustTrdInfoExample();
    CustAmcCmpycontactor custAmcCmpycontactor = null;
    CustAmcCmpycontactorExample custAmcCmpycontactorExample = new CustAmcCmpycontactorExample();
    while(keepDoing){
      if(custTrdCmpyList.size() < pageSize){
        keepDoing = false;
      }
      for(CustTrdCmpy custTrdCmpy: custTrdCmpyList){
        //get all trds of the cmpy
        custTrdInfoExample.clear();
        custTrdInfoExample.createCriteria().andBuyerTypeEqualTo(CustTypeEnum.COMPANY.getId()).andBuyerIdEqualTo(custTrdCmpy.getId());

        List<CustTrdInfo> custTrdInfos = custTrdInfoMapper.selectByExample(custTrdInfoExample);
        if(CollectionUtils.isEmpty(custTrdInfos)){
          continue;
        }
        for(CustTrdInfo custTrdInfo: custTrdInfos){
          if(!StringUtils.isEmpty(custTrdInfo.getTrdContactorName()) && !StringUtils.isEmpty(custTrdInfo.getTrdContactorAddr())){
            if(custTrdInfo.getTrdContactorName().equals("-1") || custTrdInfo.getTrdContactorAddr().equals("-1") ){
              //not valid info for trdContactorName and trdContactorPhone
              continue;
            }
            custAmcCmpycontactor = new CustAmcCmpycontactor();
            if(custTrdInfo.getTrdContactorName().length() >= 36 ){
              log.error(gson.toJson(custTrdInfo));
              continue;
            }
            custAmcCmpycontactor.setName(custTrdInfo.getTrdContactorName());
            custAmcCmpycontactor.setCompany(custTrdCmpy.getCmpyName());
            custAmcCmpycontactor.setCmpyId(custTrdCmpy.getId());
            if(!StringUtils.isEmpty(custTrdInfo.getTrdContactorPhone()) && !custTrdInfo.getTrdContactorPhone().equals("-1")

            ){

//              custAmcCmpycontactor.setMobile(custTrdInfo.getTrdContactorPhone());

              custAmcCmpycontactor.setAddress(custTrdInfo.getTrdContactorAddress());
              custAmcCmpycontactor.setTrdPhone(custTrdInfo.getTrdContactorPhone());
            }else{
              String[] phoneAndAddr = null;
              if(!StringUtils.isEmpty(custTrdInfo.getTrdContactorAddr())){
                phoneAndAddr  = custTrdInfo.getTrdContactorAddr().split(" ");
                if(phoneAndAddr.length >= 2){
                  custAmcCmpycontactor.setTrdPhone(phoneAndAddr[0]);

                  custAmcCmpycontactor.setMobile(phoneAndAddr[0]);

                  custAmcCmpycontactor.setAddress(phoneAndAddr[1]);
                }else{
                  if(Character.isDigit(phoneAndAddr[0].charAt(0))){

                    custAmcCmpycontactor.setMobile(phoneAndAddr[0]);

                    custAmcCmpycontactor.setTrdPhone(phoneAndAddr[0]);
                  }else{
                    custAmcCmpycontactor.setAddress(phoneAndAddr[0]);
                  }
                }
              }
            }
            if(StringUtils.isEmpty(custAmcCmpycontactor.getTrdPhone()) || StringUtils.isEmpty(custAmcCmpycontactor.getName())){
              //no phone or no name no need
              continue;
            }

            //now check the contactor exist or not
            custAmcCmpycontactorExample.clear();
            custAmcCmpycontactorExample.createCriteria().andCmpyIdEqualTo(custTrdCmpy.getId())
                .andNameEqualTo(custAmcCmpycontactor.getName())
                .andTrdPhoneEqualTo(custAmcCmpycontactor.getTrdPhone());

            List<CustAmcCmpycontactor> custAmcCmpycontactors =
                custAmcCmpycontactorMapper.selectByExample(custAmcCmpycontactorExample);
            if(CollectionUtils.isEmpty(custAmcCmpycontactors)){
              // can insert
              custAmcCmpycontactorMapper.insertSelective(custAmcCmpycontactor);
            }else{
              // can update

              if(custAmcCmpycontactors.get(0).getUpdateBy() != -1L ){
                log.info("This record: name:{} cmpyId:{} id:{} cannot be update by system because the information is "
                        + "updated by others", custAmcCmpycontactors.get(0).getName(),
                    custAmcCmpycontactors.get(0).getCmpyId(), custAmcCmpycontactors.get(0).getId());
                continue;
              }
              AmcBeanUtils.copyProperties(custAmcCmpycontactor, custAmcCmpycontactors.get(0));
              custAmcCmpycontactorMapper.updateByPrimaryKeySelective(custAmcCmpycontactors.get(0));
            }

          }else{
            continue;
          }

        }
      }
      //try to get next batch
      offset = offset + pageSize;
      rowBounds = new RowBounds(offset, pageSize);
      custTrdCmpyList =
          custTrdCmpyMapper.selectByExampleWithRowbounds(custTrdCmpyExample, rowBounds);
      if(CollectionUtils.isEmpty(custTrdCmpyList)){
        keepDoing = false;
      }else{
        keepDoing = true;
      }


    }


  }

  @Override
  public void initCmpyAmcContactorOfCmpy(Long cmpyId) {


    CustTrdCmpy custTrdCmpy = custTrdCmpyMapper.selectByPrimaryKey(cmpyId);
    if(null == custTrdCmpy){
      return;
    }

    CustTrdInfoExample custTrdInfoExample = new CustTrdInfoExample();

    //get all trds of the cmpy
    custTrdInfoExample.createCriteria().andBuyerTypeEqualTo(CustTypeEnum.COMPANY.getId()).andBuyerIdEqualTo(cmpyId);

    List<CustTrdInfo> custTrdInfos = custTrdInfoMapper.selectByExample(custTrdInfoExample);
    if(CollectionUtils.isEmpty(custTrdInfos)){
      return;
    }
    for(CustTrdInfo custTrdInfo: custTrdInfos){
      if(!StringUtils.isEmpty(custTrdInfo.getTrdContactorName()) && !StringUtils.isEmpty(custTrdInfo.getTrdContactorAddr())){
        if(custTrdInfo.getTrdContactorName().equals("-1") || custTrdInfo.getTrdContactorAddr().equals("-1") ){
          //not valid info for trdContactorName and trdContactorPhone
          continue;
        }
        CustAmcCmpycontactor custAmcCmpycontactor = new CustAmcCmpycontactor();
        if(custTrdInfo.getTrdContactorName().length() >= 36 ){
          log.error(gson.toJson(custTrdInfo));
          continue;
        }
        custAmcCmpycontactor.setName(custTrdInfo.getTrdContactorName());
        custAmcCmpycontactor.setCompany(custTrdCmpy.getCmpyName());
        custAmcCmpycontactor.setCmpyId(custTrdCmpy.getId());
        if(!StringUtils.isEmpty(custTrdInfo.getTrdContactorPhone()) && !custTrdInfo.getTrdContactorPhone().equals("-1")

        ){
//              custAmcCmpycontactor.setMobile(custTrdInfo.getTrdContactorPhone());
          custAmcCmpycontactor.setAddress(custTrdInfo.getTrdContactorAddress());
          custAmcCmpycontactor.setTrdPhone(custTrdInfo.getTrdContactorPhone());
        }else{
          String[] phoneAndAddr = null;
          if(!StringUtils.isEmpty(custTrdInfo.getTrdContactorAddr())){
            phoneAndAddr  = custTrdInfo.getTrdContactorAddr().split(" ");
            if(phoneAndAddr.length >= 2){
              custAmcCmpycontactor.setTrdPhone(phoneAndAddr[0]);

              custAmcCmpycontactor.setMobile(phoneAndAddr[0]);

              custAmcCmpycontactor.setAddress(phoneAndAddr[1]);
            }else{
              if(Character.isDigit(phoneAndAddr[0].charAt(0))){

                custAmcCmpycontactor.setMobile(phoneAndAddr[0]);

                custAmcCmpycontactor.setTrdPhone(phoneAndAddr[0]);
              }else{
                custAmcCmpycontactor.setAddress(phoneAndAddr[0]);
              }
            }
          }
        }
        if(StringUtils.isEmpty(custAmcCmpycontactor.getMobile()) || StringUtils.isEmpty(custAmcCmpycontactor.getName())){
          //no phone or no name no need
          continue;
        }
        CustAmcCmpycontactorExample custAmcCmpycontactorExample = new CustAmcCmpycontactorExample();
        //now check the contactor exist or not
        custAmcCmpycontactorExample.createCriteria().andCmpyIdEqualTo(custTrdCmpy.getId())
            .andNameEqualTo(custAmcCmpycontactor.getName())
            .andTrdPhoneEqualTo(custAmcCmpycontactor.getTrdPhone());

        List<CustAmcCmpycontactor> custAmcCmpycontactors =
            custAmcCmpycontactorMapper.selectByExample(custAmcCmpycontactorExample);
        if(CollectionUtils.isEmpty(custAmcCmpycontactors)){
          // can insert
          custAmcCmpycontactorMapper.insertSelective(custAmcCmpycontactor);
        }else{
          // can update
          if(custAmcCmpycontactors.get(0).getUpdateBy() != -1L ){
            log.info("This record: name:{} cmpyId:{} id:{} cannot be update by system because the information is "
                    + "updated by others", custAmcCmpycontactors.get(0).getName(),
                custAmcCmpycontactors.get(0).getCmpyId(), custAmcCmpycontactors.get(0).getId());
            continue;
          }
          custAmcCmpycontactorMapper.updateByPrimaryKeySelective(custAmcCmpycontactors.get(0));
        }

      }else{
        continue;
      }

    }





  }
}
