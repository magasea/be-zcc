package com.wensheng.zcc.cust.service.impl;

import com.google.gson.Gson;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.common.utils.AmcNumberUtils;
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
import com.wensheng.zcc.cust.module.dao.mysql.ext.CustAmcCmpycontactorExtExample;
import com.wensheng.zcc.cust.module.helper.CustTypeEnum;
import com.wensheng.zcc.cust.module.vo.CustAmcCmpycontactorExtVo;
import com.wensheng.zcc.cust.module.vo.CustAmcCmpycontactorTrdInfoVo;
import com.wensheng.zcc.cust.service.AmcContactorService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    //first check name and phone unique
    CustAmcCmpycontactorExample custAmcCmpycontactorExample = new CustAmcCmpycontactorExample();
    custAmcCmpycontactorExample.createCriteria().andNameEqualTo(custAmcCmpycontactor.getName()).
        andCompanyEqualTo(custAmcCmpycontactor.getCompany()).andMobileEqualTo(custAmcCmpycontactor.getMobile());
    List<CustAmcCmpycontactor> custAmcCmpycontactors =
        custAmcCmpycontactorMapper.selectByExample(custAmcCmpycontactorExample);
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

  @Override
  public void updateAmcCmpyContactor(CustAmcCmpycontactor custAmcCmpycontactor) {
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
    List<CustAmcCmpycontactorExt> custAmcCmpycontactorExts = new ArrayList<>();
    for(CustAmcCmpycontactor custAmcCmpycontactor: custAmcCmpycontactors){

      CustAmcCmpycontactorExt custAmcCmpycontactorExt = new CustAmcCmpycontactorExt();
      custAmcCmpycontactorExt.setCustAmcCmpycontactor(custAmcCmpycontactor);
      custAmcCmpycontactorExt.setCustTrdInfoList(new ArrayList<>());
      custAmcCmpycontactorExts.add(custAmcCmpycontactorExt);

      custAmcCmpycontactorMap.put(String.format("%s%s",custAmcCmpycontactor.getName(), custAmcCmpycontactor.getTrdPhone()),
          custAmcCmpycontactorExt);


    }


    for(CustTrdInfo custTrdInfo: custTrdInfos){
      String key = String.format("%s%s",custTrdInfo.getTrdContactorName(), custTrdInfo.getTrdContactorPhone());
      if(custAmcCmpycontactorMap.containsKey(key)){
        custAmcCmpycontactorMap.get(key).getCustTrdInfoList().add(custTrdInfo);
      }else{
//        log.error(" the trdInfo:{} doesn't belong to any contancator", custTrdInfo.getId());
      }

    }
     custAmcCmpycontactorExtVos = convertToVos(custAmcCmpycontactorExts);
    return custAmcCmpycontactorExtVos;
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
    custTrdInfoExample.createCriteria().andBuyerIdEqualTo(custAmcCmpycontactor.getCmpyId()).andBuyerTypeEqualTo(CustTypeEnum.COMPANY.getId());
    List<CustTrdInfo> custTrdInfos = custTrdInfoMapper.selectByExample(custTrdInfoExample);
    custAmcCmpycontactorTrdInfoVo.setCustTrdInfoList(new ArrayList<>());
    for(CustTrdInfo custTrdInfo : custTrdInfos){
      if(custTrdInfo.getTrdContactorName().equals(custAmcCmpycontactor.getName()) && custAmcCmpycontactor.getTrdPhone().equals(custTrdInfo.getTrdContactorPhone())){
        custAmcCmpycontactorTrdInfoVo.getCustTrdInfoList().add(custTrdInfo);
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

              custAmcCmpycontactor.setMobile(custTrdInfo.getTrdContactorPhone());

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

            //now check the contactor exist or not
            custAmcCmpycontactorExample.clear();
            custAmcCmpycontactorExample.createCriteria().andCmpyIdEqualTo(custTrdCmpy.getId())
                .andNameEqualTo(custAmcCmpycontactor.getName())
                .andMobileEqualTo(custAmcCmpycontactor.getMobile());

            List<CustAmcCmpycontactor> custAmcCmpycontactors =
                custAmcCmpycontactorMapper.selectByExample(custAmcCmpycontactorExample);
            if(CollectionUtils.isEmpty(custAmcCmpycontactors)){
              // can insert
              custAmcCmpycontactorMapper.insertSelective(custAmcCmpycontactor);
            }else{
              // can update

              if(custAmcCmpycontactors.get(0).getUpdateBy() != -1L){
                String mobile = custAmcCmpycontactors.get(0).getMobile();
                String phone = custAmcCmpycontactors.get(0).getPhone();
                AmcBeanUtils.copyProperties(custAmcCmpycontactor, custAmcCmpycontactors.get(0));
                custAmcCmpycontactors.get(0).setMobile(mobile);
                custAmcCmpycontactors.get(0).setPhone(phone);
              }
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
}
