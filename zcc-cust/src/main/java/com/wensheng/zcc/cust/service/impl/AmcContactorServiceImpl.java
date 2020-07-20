package com.wensheng.zcc.cust.service.impl;

import com.google.gson.Gson;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.AmcExceptions;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustAmcCmpycontactorMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustCmpycontactorHistoryMapper;
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
import com.wensheng.zcc.cust.module.helper.CustTypeEnum;
import com.wensheng.zcc.cust.module.helper.PresonStatusEnum;
import com.wensheng.zcc.cust.module.sync.AdressResp;
import com.wensheng.zcc.cust.module.vo.CustAmcCmpycontactorExtVo;
import com.wensheng.zcc.cust.module.vo.CustAmcCmpycontactorTrdInfoVo;
import com.wensheng.zcc.cust.module.vo.MergeCustVo;
import com.wensheng.zcc.cust.module.vo.helper.MergeCustRestult;
import com.wensheng.zcc.cust.module.vo.helper.ModifyResult;
import com.wensheng.zcc.cust.service.AmcContactorService;

import java.text.Collator;
import java.util.*;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class AmcContactorServiceImpl implements  AmcContactorService{

  @Autowired
  CustAmcCmpycontactorMapper custAmcCmpycontactorMapper;

  @Autowired
  CustCmpycontactorHistoryMapper custCmpycontactorHistoryMapper;

  @Autowired
  CustTrdCmpyMapper custTrdCmpyMapper;

  @Autowired
  CustTrdInfoMapper custTrdInfoMapper;

  @Autowired
  CustAmcCmpycontactorExtMapper custAmcCmpycontactorExtMapper;

  @Autowired
  CommonHandler commonHandler;

  @Value("${cust.syncUrls.getAddressCodeByAddress}")
  private String getAddressCodeByAddress;

  private static volatile Gson gson = new Gson();

  @Override
  public void createAmcCmpyContactor(CustAmcCmpycontactor custAmcCmpycontactor) throws Exception {
//    //兼容老逻辑手机号放在mobileUpdate
//    if(!StringUtils.isEmpty(custAmcCmpycontactor.getMobile())){
//      custAmcCmpycontactor.setMobileUpdate(custAmcCmpycontactor.getMobile());
//    }
//    //兼容老逻辑固话号放在phoneUpdate
//    if(!StringUtils.isEmpty(custAmcCmpycontactor.getPhone())){
//      custAmcCmpycontactor.setPhoneUpdate(custAmcCmpycontactor.getPhone());
//    }

    //first check name and phone unique
    List<CustAmcCmpycontactor>  custAmcCmpycontactors = queryCmpyContactorBymobileList(custAmcCmpycontactor);

    if(!CollectionUtils.isEmpty(custAmcCmpycontactors)){
      //cannot insert
      log.error("There is already person name:{} company name:{} phone:{} reject insert",
          custAmcCmpycontactor.getName(),
          custAmcCmpycontactor.getCompany(), custAmcCmpycontactor.getMobileUpdate());
      throw ExceptionUtils.getAmcException(AmcExceptions.DUPLICATE_RECORD_INSERT_ERROR, String.format("已经 "
              + "有姓名为:%s 所属公司Id为:%s 电话为:%s 的记录, 请勿重复插入",
          custAmcCmpycontactor.getName(),
          custAmcCmpycontactor.getCmpyId(), custAmcCmpycontactor.getMobileUpdate()));
    }
    custAmcCmpycontactor.setCreateTime(new Date());
    log.info("人工新增联系人custAmcCmpycontactor：{}",custAmcCmpycontactor);
    custAmcCmpycontactorMapper.insertSelective(custAmcCmpycontactor);
  }

  /**
   * 根据公司名称,联系人姓名,联系人电话号查找公司联系人
   * @param custAmcCmpycontactor
   * @return
   */
  private List<CustAmcCmpycontactor> queryCmpyContactorBymobileList(CustAmcCmpycontactor custAmcCmpycontactor) {
    List<String> mobileUpdateList = null;
    if (null != custAmcCmpycontactor.getMobileUpdate()) {
      mobileUpdateList = Arrays.asList(custAmcCmpycontactor.getMobileUpdate().split(";"));
    }
    return custAmcCmpycontactorExtMapper.selectCmpyContactorBymobileList(custAmcCmpycontactor.getCompany(),
            custAmcCmpycontactor.getName(), mobileUpdateList);
  }

  @Override
  public ModifyResult updateAmcCmpyContactor(CustAmcCmpycontactor custAmcCmpycontactor) throws Exception{
    ModifyResult modifyResult = new ModifyResult();
    modifyResult.setSuccess(true);
    CustAmcCmpycontactor originalCmpycontactor =custAmcCmpycontactorMapper.selectByPrimaryKey(custAmcCmpycontactor.getId());
    if(null == originalCmpycontactor){
      log.error("There is no person in db id:{}", custAmcCmpycontactor.getId());
      throw ExceptionUtils.getAmcException(AmcExceptions.NO_ORIGINAL_INFO_ERROR, String.format(
          "数据库中没有对应的公司联系人id为:%s", custAmcCmpycontactor.getId()));
    }

    //first check name and phone unique
    List<CustAmcCmpycontactor>  custAmcCmpycontactors = queryCmpyContactorBymobileList(custAmcCmpycontactor);

    if(!CollectionUtils.isEmpty(custAmcCmpycontactors)){
      if(custAmcCmpycontactors.size() ==1 && custAmcCmpycontactors.get(0).getId().equals(custAmcCmpycontactor.getId())){
        log.info("查询到自身");
      }else {
        //cannot update
        log.error("There is already person name:{} company name:{} phone:{} reject insert",
            custAmcCmpycontactor.getName(),
            custAmcCmpycontactor.getCompany(), custAmcCmpycontactor.getMobileUpdate());
//        throw ExceptionUtils.getAmcException(AmcExceptions.DUPLICATE_RECORD_UPDATE_ERROR, String.format("已经 "
//                + "有姓名为:%s 所属公司Id为:%s 电话为:%s 的记录, 请勿重复插入",
//            custAmcCmpycontactor.getName(),
//            custAmcCmpycontactor.getCmpyId(), custAmcCmpycontactor.getMobileUpdate()));

        List<Long> duplicateIdList = new ArrayList();
        custAmcCmpycontactors.forEach( cmpycontactor -> duplicateIdList.add(cmpycontactor.getId()));
        modifyResult.setSuccess(false);
        modifyResult.setErrCode("DUPLICATE_RECORD_UPDATE_ERROR");
        modifyResult.setIdList(duplicateIdList);
        return modifyResult;

      }
    }

    //电话号存入历史数据
    creatMobilePhoneHistory(custAmcCmpycontactor, originalCmpycontactor);
    //创建历史信息
    commonHandler.creatCmpycontactorHistory(custAmcCmpycontactor.getUpdateBy(), "updateAmcCmpyContactor",
        "人工修改",originalCmpycontactor);
    custAmcCmpycontactorMapper.updateByPrimaryKeySelective(custAmcCmpycontactor);
    modifyResult.setResult(custAmcCmpycontactor);
    return modifyResult;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void mergeCmpycontactor(MergeCustRestult mergeCustRestult,
      MergeCustVo mergeCustVo) throws Exception {

    List<Long> fromContactorIds = mergeCustVo.getFromPersonIds();
    Long toContactorId = mergeCustVo.getToPersonId();
    Long updateBy = mergeCustVo.getUpdateBy();
    String phoneUpdate  = mergeCustVo.getPhoneUpdate();
    String mobileUpdate  = mergeCustVo.getMobileUpdate();

    CustAmcCmpycontactorExample custAmcCmpycontactorExample = new CustAmcCmpycontactorExample();
    fromContactorIds.add(toContactorId);
    custAmcCmpycontactorExample.createCriteria().andIdIn(fromContactorIds);
    List<CustAmcCmpycontactor> custAmcCmpycontactors =
        custAmcCmpycontactorMapper.selectByExample(custAmcCmpycontactorExample);
    if(fromContactorIds.size() != custAmcCmpycontactors.size()){
      throw new Exception("未查到对应的公司联系人");
    }

    Set<Long> compIds = new HashSet<>();
    Map<Long,CustAmcCmpycontactor> custAmcCmpycontactorHashMap= new HashMap<>();
    //转换为map
    for (CustAmcCmpycontactor custAmcCmpycontactor : custAmcCmpycontactors){
      compIds.add(custAmcCmpycontactor.getCmpyId());
      custAmcCmpycontactorHashMap.put(custAmcCmpycontactor.getId(),custAmcCmpycontactor);
    }
    //判断是否是同一公司
    if(compIds.size()!=1){
      throw new Exception("不是同一个公司联系人，无法合并");
    }

    // 合并到的联系人
    CustAmcCmpycontactor toCustAmcCmpycontactor = custAmcCmpycontactorHashMap.get(toContactorId);
    CustAmcCmpycontactor toCustAmcCmpycontactorNew = new CustAmcCmpycontactor();
    toCustAmcCmpycontactorNew.setId(toContactorId);
    toCustAmcCmpycontactorNew.setMobileUpdate(toCustAmcCmpycontactor.getMobileUpdate());
    toCustAmcCmpycontactorNew.setMobilePrep(toCustAmcCmpycontactor.getMobilePrep());
    toCustAmcCmpycontactorNew.setMobileHistory(toCustAmcCmpycontactor.getMobileHistory());
    toCustAmcCmpycontactorNew.setPhoneUpdate(toCustAmcCmpycontactor.getPhoneUpdate());
    toCustAmcCmpycontactorNew.setPhonePrep(toCustAmcCmpycontactor.getPhonePrep());
    toCustAmcCmpycontactorNew.setPhoneHistory(toCustAmcCmpycontactor.getPhoneHistory());
    toCustAmcCmpycontactorNew.setUpdateBy(updateBy);

    //查询要合并的原订单
    for (Long cmpycontactorId : fromContactorIds) {
      if(cmpycontactorId.equals(toContactorId)){
        continue;
      }
      CustAmcCmpycontactor originalCmpycontactor =custAmcCmpycontactorHashMap.get(cmpycontactorId);
      creatCustAmcCmpycontactorNew(toCustAmcCmpycontactorNew, originalCmpycontactor);

      CustAmcCmpycontactor custAmcCmpycontactor = new CustAmcCmpycontactor();
      custAmcCmpycontactor.setId(cmpycontactorId);
      custAmcCmpycontactor.setStatus(PresonStatusEnum.MERGED_STATUS.getId());

      //查询要更改的交易
      CustTrdInfoExample custTrdInfoExample = new CustTrdInfoExample();
      custTrdInfoExample.createCriteria().andTrdCmpycontactorIdEqualTo(cmpycontactorId);
      List<CustTrdInfo> custTrdInfoList = custTrdInfoMapper.selectByExample(custTrdInfoExample);
      StringBuffer sbTrdInfoBak = new StringBuffer();

      if (!CollectionUtils.isEmpty(custTrdInfoList)){
        sbTrdInfoBak.append("trdInfoId:");
        for(CustTrdInfo custTrdInfo : custTrdInfoList){
          if(sbTrdInfoBak.length()>0){
            sbTrdInfoBak.append(",");
          }
          sbTrdInfoBak.append(custTrdInfo.getId());

          CustTrdInfo custTrdInfoNew = new CustTrdInfo();
          custTrdInfoNew.setId(custTrdInfo.getId());
          custTrdInfoNew.setTrdCmpycontactorId(toContactorId);
          //修改更改的交易指向新联系人
          custTrdInfoMapper.updateByPrimaryKeySelective(custTrdInfoNew);
        }
      }
      if(sbTrdInfoBak.length()>0){
        sbTrdInfoBak.append(";");
      }
      sbTrdInfoBak.append("toPersonId:");
      sbTrdInfoBak.append(toContactorId);
      custAmcCmpycontactor.setUpdateBy(updateBy);
      custAmcCmpycontactor.setUpdateTime(new Date());
      custAmcCmpycontactor.setTrdInfoBak(sbTrdInfoBak.toString());
      commonHandler.creatCmpycontactorHistory(updateBy, "mergeCmpycontactor",
          String.format("人工合并至%d",toContactorId), originalCmpycontactor);
      custAmcCmpycontactorMapper.updateByPrimaryKeySelective(custAmcCmpycontactor);
    }

    //去重
    phoneRepeatRemove(toCustAmcCmpycontactorNew);

    if(!StringUtils.isEmpty(phoneUpdate)){
      toCustAmcCmpycontactorNew.setPhoneHistory(
          commonHandler.mergePhoneHistory(toCustAmcCmpycontactorNew.getPhoneUpdate(), phoneUpdate,
              toCustAmcCmpycontactorNew.getPhoneHistory()));
      toCustAmcCmpycontactorNew.setPhoneUpdate(phoneUpdate);
    }
    if(!StringUtils.isEmpty(mobileUpdate)){
      toCustAmcCmpycontactorNew.setMobileHistory(
          commonHandler.mergePhoneHistory(toCustAmcCmpycontactorNew.getMobileUpdate(), mobileUpdate,
              toCustAmcCmpycontactorNew.getMobileHistory()));
      toCustAmcCmpycontactorNew.setMobileUpdate(mobileUpdate);
    }

    //判断电话是否超出限制
    String[] phoneArray = toCustAmcCmpycontactorNew.getPhoneUpdate().split(";");
    String[] mobileArray = toCustAmcCmpycontactorNew.getMobileUpdate().split(";");
    if(phoneArray.length>3 || mobileArray.length>3){
      mergeCustRestult.setSuccess(false);
      mergeCustRestult.setMobileUpdate(toCustAmcCmpycontactorNew.getMobileUpdate());
      mergeCustRestult.setPhoneUpdate(toCustAmcCmpycontactorNew.getPhoneUpdate());
      mergeCustRestult.setErrCode("PHONE_COUNT_GREATER_MAX");
      throw new Exception("PHONE_COUNT_GREATER_MAX");
    }

    //修改合并人的电话
    commonHandler.creatCmpycontactorHistory(updateBy, "mergeCmpycontactor",
        "合并记录", toCustAmcCmpycontactor);
    toCustAmcCmpycontactorNew.setUpdateTime(new Date());
    custAmcCmpycontactorMapper.updateByPrimaryKeySelective(toCustAmcCmpycontactorNew);
  }


  /**
   * 去重
   * @param toCustAmcCmpycontactorNew
   */
  private void phoneRepeatRemove(CustAmcCmpycontactor toCustAmcCmpycontactorNew) {
    toCustAmcCmpycontactorNew.setMobileUpdate(commonHandler.removalPhone(toCustAmcCmpycontactorNew.getMobileUpdate()));
    toCustAmcCmpycontactorNew.setMobilePrep(commonHandler.removalPhone(toCustAmcCmpycontactorNew.getMobilePrep()));
    toCustAmcCmpycontactorNew.setMobileHistory(commonHandler.removalPhone(toCustAmcCmpycontactorNew.getMobileHistory()));
    toCustAmcCmpycontactorNew.setPhoneUpdate(commonHandler.removalPhone(toCustAmcCmpycontactorNew.getPhoneUpdate()));
    toCustAmcCmpycontactorNew.setPhonePrep(commonHandler.removalPhone(toCustAmcCmpycontactorNew.getPhonePrep()));
    toCustAmcCmpycontactorNew.setPhoneHistory(commonHandler.removalPhone(toCustAmcCmpycontactorNew.getPhoneHistory()));
  }

  private void creatCustAmcCmpycontactorNew(CustAmcCmpycontactor toCustAmcCmpycontactorNew,
      CustAmcCmpycontactor originalCmpycontactor) {
    if(null != originalCmpycontactor.getMobileUpdate() &&!"-1".equals(originalCmpycontactor.getMobileUpdate())){
      if(null == toCustAmcCmpycontactorNew.getMobileUpdate() || "-1".equals(toCustAmcCmpycontactorNew.getMobileUpdate())){
        toCustAmcCmpycontactorNew.setMobileUpdate(originalCmpycontactor.getMobileUpdate());
      }else {
        toCustAmcCmpycontactorNew.setMobileUpdate(toCustAmcCmpycontactorNew.getMobileUpdate()+";"+originalCmpycontactor.getMobileUpdate());
      }
    }

    if(null != originalCmpycontactor.getMobilePrep() &&!"-1".equals(originalCmpycontactor.getMobilePrep())){
      if(null == toCustAmcCmpycontactorNew.getMobilePrep() || "-1".equals(toCustAmcCmpycontactorNew.getMobilePrep())){
        toCustAmcCmpycontactorNew.setMobilePrep(originalCmpycontactor.getMobilePrep());
      }else {
        toCustAmcCmpycontactorNew.setMobilePrep(toCustAmcCmpycontactorNew.getMobilePrep()+";"+originalCmpycontactor.getMobilePrep());
      }
    }

    if(null != originalCmpycontactor.getMobileHistory() &&!"-1".equals(originalCmpycontactor.getMobileHistory())){
      if(null == toCustAmcCmpycontactorNew.getMobileHistory() || "-1".equals(toCustAmcCmpycontactorNew.getMobileHistory())){
        toCustAmcCmpycontactorNew.setMobileHistory(originalCmpycontactor.getMobileHistory());
      }else {
        toCustAmcCmpycontactorNew.setMobileHistory(toCustAmcCmpycontactorNew.getMobileHistory()+";"+originalCmpycontactor.getMobileHistory());
      }
    }

    if(null != originalCmpycontactor.getPhoneUpdate() &&!"-1".equals(originalCmpycontactor.getPhoneUpdate())){
      if(null == toCustAmcCmpycontactorNew.getPhoneUpdate() || "-1".equals(toCustAmcCmpycontactorNew.getPhoneUpdate())){
        toCustAmcCmpycontactorNew.setPhoneUpdate(originalCmpycontactor.getPhoneUpdate());
      }else {
        toCustAmcCmpycontactorNew.setPhoneUpdate(toCustAmcCmpycontactorNew.getPhoneUpdate()+";"+originalCmpycontactor.getPhoneUpdate());
      }
    }

    if(null != originalCmpycontactor.getPhonePrep() &&!"-1".equals(originalCmpycontactor.getPhonePrep())){
      if(null == toCustAmcCmpycontactorNew.getPhonePrep() || "-1".equals(toCustAmcCmpycontactorNew.getPhonePrep())){
        toCustAmcCmpycontactorNew.setPhonePrep(originalCmpycontactor.getPhonePrep());
      }else {
        toCustAmcCmpycontactorNew.setPhonePrep(toCustAmcCmpycontactorNew.getPhonePrep()+";"+originalCmpycontactor.getPhonePrep());
      }
    }

    if(null != originalCmpycontactor.getPhoneHistory() &&!"-1".equals(originalCmpycontactor.getPhoneHistory())){
      if(null == toCustAmcCmpycontactorNew.getPhoneHistory() || "-1".equals(toCustAmcCmpycontactorNew.getPhoneHistory())){
        toCustAmcCmpycontactorNew.setPhoneHistory(originalCmpycontactor.getPhoneHistory());
      }else {
        toCustAmcCmpycontactorNew.setPhoneHistory(toCustAmcCmpycontactorNew.getPhoneHistory()+";"+originalCmpycontactor.getPhoneHistory());
      }
    }
  }

  private void creatMobilePhoneHistory(CustAmcCmpycontactor custAmcCmpycontactor,
      CustAmcCmpycontactor originalCmpycontactor) {
    //若改变了手机号和固话号，存入历史数据时要去重
    if(null != custAmcCmpycontactor.getMobileUpdate() &&
            !custAmcCmpycontactor.getMobileUpdate().equals(originalCmpycontactor.getMobileUpdate())){
      //对比出修改的手机号
      StringBuilder sbMobileChange = new StringBuilder();
      String[] mobileUpdateOriginalArray = originalCmpycontactor.getMobileUpdate().split(";");
      for (int i = 0; i < mobileUpdateOriginalArray.length; i++) {
        if(!custAmcCmpycontactor.getMobileUpdate().contains(mobileUpdateOriginalArray[i])){
          if(sbMobileChange.length() >1){
            sbMobileChange.append(";");
          }
          sbMobileChange.append(mobileUpdateOriginalArray[i]);
        }
      }

      if("-1".equals(originalCmpycontactor.getMobileHistory())){
        custAmcCmpycontactor.setMobileHistory(sbMobileChange.toString());
      }else {
        String mobileHistory = String.format("%s;%s",originalCmpycontactor.getMobileHistory(),
            sbMobileChange.toString());
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
      //对比出修改的固话号
      StringBuilder sbPhoneChange = new StringBuilder();
      String[] phoneUpdateOriginalArray = originalCmpycontactor.getPhoneUpdate().split(";");
      for (int i = 0; i < phoneUpdateOriginalArray.length; i++) {
        if(!custAmcCmpycontactor.getPhoneUpdate().contains(phoneUpdateOriginalArray[i])){
          if(sbPhoneChange.length() >1){
            sbPhoneChange.append(";");
          }
          sbPhoneChange.append(phoneUpdateOriginalArray[i]);
        }
      }

      if("-1".equals(originalCmpycontactor.getPhoneHistory())){
        custAmcCmpycontactor.setPhoneHistory(sbPhoneChange.toString());
      }else {
        String phoneHistory = String.format("%s;%s",originalCmpycontactor.getPhoneHistory(),
            sbPhoneChange.toString());
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
  }

  @Override
  public List<CustAmcCmpycontactor> getCmpyAmcContactor(String cmpyName) {
    CustAmcCmpycontactorExample custAmcCmpycontactorExample = new CustAmcCmpycontactorExample();
    custAmcCmpycontactorExample.createCriteria().andCompanyEqualTo(cmpyName).andStatusNotEqualTo(PresonStatusEnum.MERGED_STATUS.getId());
    List<CustAmcCmpycontactor> custAmcCmpycontactors =
        custAmcCmpycontactorMapper.selectByExample(custAmcCmpycontactorExample);
    return custAmcCmpycontactors;
  }

  @Override
  public List<CustAmcCmpycontactorExtVo> getCmpyAmcContactor(Long cmpyId) {
    List<CustAmcCmpycontactorExtVo> custAmcCmpycontactorExtVos = null;
    CustAmcCmpycontactorExample custAmcCmpycontactorExample = new CustAmcCmpycontactorExample();
    custAmcCmpycontactorExample.setOrderByClause(" id desc ");
    custAmcCmpycontactorExample.createCriteria().andCmpyIdEqualTo(cmpyId).andStatusNotEqualTo(PresonStatusEnum.MERGED_STATUS.getId());
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
    custAmcCmpycontactorExample.createCriteria().andCmpyIdEqualTo(cmpyId).andStatusNotEqualTo(PresonStatusEnum.MERGED_STATUS.getId());
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
      if(custTrdInfo.getTrdCmpycontactorId()!=null && custTrdInfo.getTrdCmpycontactorId() != -1L){
        //组装数据
        if(null != custAmcCmpycontactorMap.get(custTrdInfo.getTrdCmpycontactorId())){
          custAmcCmpycontactorMap.get(custTrdInfo.getTrdCmpycontactorId()).getCustTrdInfoList().add(custTrdInfo);

          //判断是否需要更新联系人
          if(custAmcCmpycontactorMap.get(custTrdInfo.getTrdCmpycontactorId()).
              getCustAmcCmpycontactor().getUpdateTime().before(custTrdInfo.getUpdateTime())){
            //初始化公司联系人
            CustAmcCmpycontactor custAmcCmpycontactor = initCmpycontactor(custTrdInfo, cmpyId, custTrdCmpy.getCmpyName());
            custAmcCmpycontactor.setId(custTrdInfo.getTrdCmpycontactorId());
            custAmcCmpycontactor.setUpdateTime(new Date());

            commonHandler.creatCmpycontactorHistory(null, "getCmpyAmcContactor",
                "同步爬虫数据修改", custAmcCmpycontactorMap.get(custTrdInfo.getTrdCmpycontactorId()).getCustAmcCmpycontactor());
            custAmcCmpycontactorMapper.updateByPrimaryKeySelective(custAmcCmpycontactor);
          }

        }else {
          log.error("有交易信息与联系人不一致的情况，交易信息id：{}",custTrdInfo.getId());
        }

      }else if(StringUtils.isEmpty(custTrdInfo.getTrdContactorName()) || custTrdInfo.getTrdContactorName().equals("-1") ||
              StringUtils.isEmpty(custTrdInfo.getTrdContactorAddr()) || custTrdInfo.getTrdContactorAddr().equals("-1")){
        //信息不全不需要初始化数据
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

    // Get the iterator over the HashMap
    Iterator<Map.Entry<Long, CustAmcCmpycontactorExt> >
        iterator = custAmcCmpycontactorMap.entrySet().iterator();
    // Iterate over the HashMap
    while (iterator.hasNext()) {
      Map.Entry<Long, CustAmcCmpycontactorExt> entry = iterator.next();
      if (entry.getValue() == null || entry.getValue().getCustAmcCmpycontactor() == null) {
        continue;
      }
      if (entry.getValue().getCustAmcCmpycontactor().getCreateBy() != null
          && entry.getValue().getCustAmcCmpycontactor().getCreateBy() == -1L
          && entry.getValue().getCustAmcCmpycontactor().getUpdateBy() == -1L
          && "-1".equals(entry.getValue().getCustAmcCmpycontactor().getPhoneUpdate())
          && "-1".equals(entry.getValue().getCustAmcCmpycontactor().getMobileUpdate())
          && CollectionUtils.isEmpty(entry.getValue().getCustTrdInfoList())) {
        commonHandler.creatCmpycontactorHistory(null, "getCmpyAmcContactor",
            "同步爬虫数据删除重复联系人", entry.getValue().getCustAmcCmpycontactor());
        custAmcCmpycontactorMapper
            .deleteByPrimaryKey(entry.getValue().getCustAmcCmpycontactor().getId());
        iterator.remove();
      }
    }

    custAmcCmpycontactorExtVos = convertToVos(new ArrayList(custAmcCmpycontactorMap.values()));
    listSort(custAmcCmpycontactorExtVos);
    custAmcCmpycontactorMap = null;
    return custAmcCmpycontactorExtVos;
  }

  @Override
  public List<CustAmcCmpycontactor> selectContactorByIdlist(List<Long> idList){
    CustAmcCmpycontactorExample custAmcCmpycontactorExample = new CustAmcCmpycontactorExample();
    custAmcCmpycontactorExample.createCriteria().andIdIn(idList);
    List<CustAmcCmpycontactor> custAmcCmpycontactors =
        custAmcCmpycontactorMapper.selectByExample(custAmcCmpycontactorExample);
    return custAmcCmpycontactors;
  }


  public static void listSort(List<CustAmcCmpycontactorExtVo> custAmcCmpycontactorExtVoList) {
    Collections.sort(custAmcCmpycontactorExtVoList, new Comparator<CustAmcCmpycontactorExtVo>() {
      @Override
      public int compare(CustAmcCmpycontactorExtVo o1, CustAmcCmpycontactorExtVo o2) {
        String name1=o1.getName();
        String name2=o2.getName();
        Collator instance = Collator.getInstance(Locale.CHINA);
        return instance.compare(name1, name2);
      }
    });
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

    //是否有对应的地址省市区编码
    if("-1".equals(custAmcCmpycontactor.getCounty()) && !"-1".equals(custAmcCmpycontactor.getAddress())){
      try{
        getAdressCode(custAmcCmpycontactor);
      }catch (Exception e){
        log.error("查询地址省市区编码出错，不影响主流程，Exception：{}",e);
      }
    }

    if(custAmcCmpycontactor == null || custAmcCmpycontactor.getCmpyId() == -1L){
      custAmcCmpycontactorTrdInfoVo.setCustAmcCmpycontactor(custAmcCmpycontactor);
      return custAmcCmpycontactorTrdInfoVo;
    }

    CustTrdInfoExample custTrdInfoExample = new CustTrdInfoExample();
//    custTrdInfoExample.createCriteria().andBuyerIdEqualTo(custAmcCmpycontactor.getCmpyId())
//        .andBuyerTypeEqualTo(CustTypeEnum.COMPANY.getId()).andTrdContactorNameEqualTo(custAmcCmpycontactor.getName());
    custTrdInfoExample.createCriteria().andTrdCmpycontactorIdEqualTo(contactorId);
    List<CustTrdInfo> custTrdInfos = custTrdInfoMapper.selectByExample(custTrdInfoExample);
//    custAmcCmpycontactorTrdInfoVo.setCustTrdInfoList(new ArrayList<>());
//    for(CustTrdInfo custTrdInfo : custTrdInfos){
//      if(custTrdInfo.getTrdContactorName().equals(custAmcCmpycontactor.getName())){
//        //get phone info to compare
//        String phone = getPhoneFromTrdInfo(custTrdInfo);
//        if(custAmcCmpycontactor.getTrdPhone().equals(phone)){
//          custAmcCmpycontactorTrdInfoVo.getCustTrdInfoList().add(custTrdInfo);
//        }
//      }
//    }
    custAmcCmpycontactorTrdInfoVo.setCustTrdInfoList(custTrdInfos);
    return custAmcCmpycontactorTrdInfoVo;
  }

  private void getAdressCode(CustAmcCmpycontactor custAmcCmpycontactor) {
    RestTemplate restTemplate = CommonHandler.getRestTemplate();
//    String adressResp1 = restTemplate.exchange(String.format(getAddressCodeByAddress,custAmcCmpycontactor.getAddress()),
//        HttpMethod.GET, null, String.class).getBody();
//    System.out.println(adressResp1);
    AdressResp adressResp = restTemplate.exchange(String.format(getAddressCodeByAddress,custAmcCmpycontactor.getAddress()),
      HttpMethod.GET, null, new ParameterizedTypeReference<AdressResp>() {}).getBody();
    if(null!=adressResp.getStatus() &&  "1".equals(adressResp.getStatus())){
      custAmcCmpycontactor.setProvince(adressResp.getResult().getStatsResult().getProvince().get(0).substring(0,6));
      custAmcCmpycontactor.setCity(adressResp.getResult().getStatsResult().getCity().get(0).substring(0,6));
      custAmcCmpycontactor.setCounty(adressResp.getResult().getStatsResult().getCounty().get(0).substring(0,6));

      CustAmcCmpycontactor custAmcCmpycontactorNew = new  CustAmcCmpycontactor();
      custAmcCmpycontactorNew.setProvince(adressResp.getResult().getStatsResult().getProvince().get(0).substring(0,6));
      custAmcCmpycontactorNew.setCity(adressResp.getResult().getStatsResult().getCity().get(0).substring(0,6));
      custAmcCmpycontactorNew.setCounty(adressResp.getResult().getStatsResult().getCounty().get(0).substring(0,6));
      custAmcCmpycontactorNew.setId(custAmcCmpycontactor.getId());
      custAmcCmpycontactorMapper.updateByPrimaryKeySelective(custAmcCmpycontactorNew);
    }
  }

  private List<CustAmcCmpycontactorExtVo> getPureContactors(List<CustAmcCmpycontactor> custAmcCmpycontactors) {
    List<CustAmcCmpycontactorExtVo> custAmcCmpycontactorExtVos = new ArrayList<>();
    for(CustAmcCmpycontactor custAmcCmpycontactor: custAmcCmpycontactors){
      CustAmcCmpycontactorExtVo custAmcCmpycontactorExtVo = new CustAmcCmpycontactorExtVo();
      //兼容老数据0421
      compatibleOldPhone(custAmcCmpycontactor);
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
      custAmcCmpycontactorExtVo.setName(custAmcCmpycontactorExt.getCustAmcCmpycontactor().getName());
      convertToVo(custAmcCmpycontactorExt, custAmcCmpycontactorExtVo);
      custAmcCmpycontactorExtVos.add(custAmcCmpycontactorExtVo);
    }
    return custAmcCmpycontactorExtVos;

  }

  private void convertToVo(CustAmcCmpycontactorExt custAmcCmpycontactorExt,
      CustAmcCmpycontactorExtVo custAmcCmpycontactorExtVo) {

    AmcBeanUtils.copyProperties(custAmcCmpycontactorExt.getCustAmcCmpycontactor(), custAmcCmpycontactorExtVo.getCustAmcCmpycontactor());
    CustAmcCmpycontactor custAmcCmpycontactor = custAmcCmpycontactorExtVo.getCustAmcCmpycontactor();

    //兼容老数据0421
    compatibleOldPhone(custAmcCmpycontactor);

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

  private void compatibleOldPhone(CustAmcCmpycontactor custAmcCmpycontactor) {
    if("-1".equals(custAmcCmpycontactor.getMobilePrep()) && "-1".equals(custAmcCmpycontactor.getPhonePrep())){
      custAmcCmpycontactor.setMobilePrep(custAmcCmpycontactor.getMobile());
      custAmcCmpycontactor.setPhonePrep(custAmcCmpycontactor.getPhone());
    }
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
        .andTrdPhoneEqualTo(custAmcCmpycontactor.getTrdPhone())
        .andStatusNotEqualTo(PresonStatusEnum.MERGED_STATUS.getId());
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
    //判断是否需要初始化联系人
    if(null == custTrdInfo.getTrdContactorName() || custTrdInfo.getTrdContactorName().equals("-1") ||
            custTrdInfo.getTrdContactorAddr().equals("-1") || null == custTrdInfo.getTrdContactorAddr()){
      //not valid info for trdContactorName and trdContactorPhone
      return null;
    }
    //初始化公司联系人
    CustAmcCmpycontactor custAmcCmpycontactor = initCmpycontactor(custTrdInfo, cmpyId, cmpyName);

    //再次判断是否需要初始化联系人
    if(StringUtils.isEmpty(custAmcCmpycontactor.getMobile()) || StringUtils.isEmpty(custAmcCmpycontactor.getName()) ||
            StringUtils.isEmpty(custAmcCmpycontactor.getTrdPhone())){
      //no phone or no name no need
      return null;
    }

    //判断是否已有对应的联系人
    List<CustAmcCmpycontactor>custAmcCmpycontactors =null;
    if("-1".equals(custTrdInfo.getTrdContactorTel()) && "-1".equals(custTrdInfo.getTrdContactorMobile()) ){
      //兼容电话和手机号未分开的情况
      CustAmcCmpycontactorExample custAmcCmpycontactorExample = new CustAmcCmpycontactorExample();
      custAmcCmpycontactorExample.createCriteria().andCmpyIdEqualTo(cmpyId)
              .andNameEqualTo(custAmcCmpycontactor.getName())
              .andTrdPhoneEqualTo(custAmcCmpycontactor.getTrdPhone())
          .andStatusNotEqualTo(PresonStatusEnum.MERGED_STATUS.getId());
      custAmcCmpycontactorExample.setOrderByClause(" id desc ");
       custAmcCmpycontactors = custAmcCmpycontactorMapper.selectByExample(custAmcCmpycontactorExample);
    }else {
      List<String> mobilePrepList = null;
      if (!"-1".equals(custTrdInfo.getTrdContactorMobile())) {
        mobilePrepList = Arrays.asList(custTrdInfo.getTrdContactorMobile().split(";"));
      }
      if(!CollectionUtils.isEmpty(mobilePrepList)){
        custAmcCmpycontactors = custAmcCmpycontactorExtMapper.selectCmpyContactorBymobileList(
            cmpyName, custTrdInfo.getTrdContactorName(), mobilePrepList);
      }
    }

    //数据库无对应的联系人新增
    if(CollectionUtils.isEmpty(custAmcCmpycontactors)){
      custAmcCmpycontactor.setCreateTime(new Date());
      custAmcCmpycontactorMapper.insertSelective(custAmcCmpycontactor);
      custTrdInfo.setTrdCmpycontactorId(custAmcCmpycontactor.getId());
      custTrdInfoMapper.updateByPrimaryKeySelective(custTrdInfo);
      return custAmcCmpycontactor;
    }else{
      //数据库有对应的联系人则将交易指定到对应的联系人上
      custTrdInfo.setTrdCmpycontactorId(custAmcCmpycontactors.get(0).getId());
      custTrdInfoMapper.updateByPrimaryKeySelective(custTrdInfo);
      CustAmcCmpycontactor originalCmpycontactor = custAmcCmpycontactors.get(0);
      Long cmpycontactorId = custAmcCmpycontactors.get(0).getId();

      //判断是否需要更新联系人
      if(custAmcCmpycontactors.get(0).getUpdateTime().before(custTrdInfo.getUpdateTime())){
        custAmcCmpycontactor.setId(cmpycontactorId);
        commonHandler.creatCmpycontactorHistory(null, "getCmpyAmcContactor",
            "同步爬虫数据修改", custAmcCmpycontactors.get(0));
        custAmcCmpycontactorMapper.updateByPrimaryKeySelective(custAmcCmpycontactor);
      }
      return custAmcCmpycontactors.get(0);
    }
  }

  private CustAmcCmpycontactor initCmpycontactor(CustTrdInfo custTrdInfo, Long cmpyId, String cmpyName) {
    //初始化对应的联系人
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
    custAmcCmpycontactor.setUpdateTime(new Date());
    return custAmcCmpycontactor;
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
                .andTrdPhoneEqualTo(custAmcCmpycontactor.getTrdPhone())
                .andStatusNotEqualTo(PresonStatusEnum.MERGED_STATUS.getId());
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
            .andTrdPhoneEqualTo(custAmcCmpycontactor.getTrdPhone())
            .andStatusNotEqualTo(PresonStatusEnum.MERGED_STATUS.getId());

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


  @Override
  public void patchCmpycontactorRevisePhone(){
    ArrayList<String> signList = new ArrayList();
    signList.add("、");
    signList.add("/");
    signList.add("，");
    signList.add("；");

    for (String  sign: signList) {
      List<CustAmcCmpycontactor> custAmcCmpycontactorList = custAmcCmpycontactorExtMapper.
          selectCmpyContactorByPhoneSign(sign);
      for (CustAmcCmpycontactor custAmcCmpycontactor : custAmcCmpycontactorList) {

        StringBuilder sbPhonePrep = new StringBuilder();
        StringBuilder sbMobilePrep = new StringBuilder();
        String trdPhone = custAmcCmpycontactor.getTrdPhone();
        trdPhone = trdPhone.replace(";","；");
        trdPhone = trdPhone.replace(",","，");
        String[] phoneMobiles =trdPhone.split(sign);

        for (int i = 0; i <phoneMobiles.length ; i++) {
          Boolean isMobile = checkMobile(phoneMobiles[i]);
          //手机号
          if(isMobile){
            if(sbMobilePrep.length() >=1){
              sbMobilePrep.append(";");
            }
            sbMobilePrep.append(phoneMobiles[i]);
          }else {
            //固话
            if(sbPhonePrep.length() >=1){
              sbPhonePrep.append(";");
            }
            sbPhonePrep.append(phoneMobiles[i]);
          }
        }
        //存入数据库
        CustAmcCmpycontactor custAmcCmpycontactorNew = new CustAmcCmpycontactor();
        custAmcCmpycontactorNew.setId(custAmcCmpycontactor.getId());
        if(sbPhonePrep.length()>=1){
          custAmcCmpycontactorNew.setPhonePrep(sbPhonePrep.toString());
        }
        if(sbMobilePrep.length()>=1){
          custAmcCmpycontactorNew.setMobilePrep(sbMobilePrep.toString());
        }
        custAmcCmpycontactorMapper.updateByPrimaryKeySelective(custAmcCmpycontactorNew);

      }
    }


    //只有手机号和固话
    List<CustAmcCmpycontactor> custAmcCmpycontactorList = custAmcCmpycontactorExtMapper.selectCmpyContactorByRightPhone();
    for (CustAmcCmpycontactor custAmcCmpycontactor : custAmcCmpycontactorList){
      String trdPhone = custAmcCmpycontactor.getTrdPhone();
      Boolean isMobile = checkMobile(trdPhone);

      CustAmcCmpycontactor custAmcCmpycontactorNew = new CustAmcCmpycontactor();
      custAmcCmpycontactorNew.setId(custAmcCmpycontactor.getId());
      if(isMobile){
        custAmcCmpycontactorNew.setMobilePrep(trdPhone);
      }else {
        custAmcCmpycontactorNew.setPhonePrep(trdPhone);
      }
      custAmcCmpycontactorMapper.updateByPrimaryKeySelective(custAmcCmpycontactorNew);
    }

    //全部固话
    List<CustAmcCmpycontactor> custAmcCmpycontactorListAllTel = custAmcCmpycontactorExtMapper.selectCmpyContactorByUnknowPhone();
    for (CustAmcCmpycontactor custAmcCmpycontactor : custAmcCmpycontactorListAllTel) {
      CustAmcCmpycontactor custAmcCmpycontactorNew = new CustAmcCmpycontactor();
      custAmcCmpycontactorNew.setId(custAmcCmpycontactor.getId());
      custAmcCmpycontactorNew.setPhonePrep(custAmcCmpycontactor.getTrdPhone());
      custAmcCmpycontactorMapper.updateByPrimaryKeySelective(custAmcCmpycontactorNew);
    }
  }

  public  Boolean checkMobile(String phone){
    phone = phone.trim();
    if(phone.length() == 11 && '1'==phone.charAt(0)){
      return true;
    }
    return false;
  }

}
