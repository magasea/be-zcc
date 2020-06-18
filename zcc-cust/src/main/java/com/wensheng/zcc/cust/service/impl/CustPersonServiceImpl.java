package com.wensheng.zcc.cust.service.impl;

import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdInfoMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdPersonMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.ext.CustTrdPersonExtMapper;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcCmpycontactor;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfo;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfoExample;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPersonExample;
import com.wensheng.zcc.cust.module.helper.CustTypeEnum;
import com.wensheng.zcc.cust.module.helper.PresonStatusEnum;
import com.wensheng.zcc.cust.module.vo.helper.ModifyResult;
import com.wensheng.zcc.cust.service.CustPersonService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class CustPersonServiceImpl implements CustPersonService {

  @Autowired
  CustTrdPersonMapper custTrdPersonMapper;
  @Autowired
  CustTrdPersonExtMapper custTrdPersonExtMapper;
  @Autowired
  CustTrdInfoMapper custTrdInfoMapper;
  @Autowired
  CommonHandler commonHandler;


  @Override
  public void createCustPerson(CustTrdPerson custTrdPerson) throws Exception {
    //校验重复
    List<CustTrdPerson> custTrdPeopleList = getPersonByMobileList(custTrdPerson);
    if(!CollectionUtils.isEmpty(custTrdPeopleList)){
      log.error("There is already person name:{} phone:{} reject insert",
          custTrdPerson.getName(), custTrdPerson.getMobileUpdate());
      throw ExceptionUtils.getAmcException(AmcExceptions.DUPLICATE_RECORD_INSERT_ERROR, String.format(
          "已经有姓名为:%s 电话为:%s 的记录, 请勿重复插入",
          custTrdPerson.getName(), custTrdPerson.getMobileUpdate()));
    }

    //补齐时间
    custTrdPerson.setCreateTime(new Date());

    //添加数据库
    custTrdPersonMapper.insertSelective(custTrdPerson);
  }

  private List<CustTrdPerson> getPersonByMobileList(CustTrdPerson custTrdPerson) {
    List<String> mobileUpdateList = null;
    if (null != custTrdPerson.getMobileUpdate()) {
      mobileUpdateList = Arrays.asList(custTrdPerson.getMobileUpdate().split(";"));
    }
    return custTrdPersonExtMapper.selectPersonByMobileList(
                                                      custTrdPerson.getName(), mobileUpdateList);
  }

  @Override
  public ModifyResult updateCustPerson(CustTrdPerson custTrdPerson) throws Exception {

    ModifyResult modifyResult = new ModifyResult();
    modifyResult.setSuccess(true);
    //查询要修改的自然人
    CustTrdPerson originalCustTrdPerson = custTrdPersonMapper.selectByPrimaryKey(custTrdPerson.getId());
    if(null == originalCustTrdPerson){
      log.error("There is no person in db id:{}", custTrdPerson.getId());
      throw ExceptionUtils.getAmcException(AmcExceptions.NO_ORIGINAL_INFO_ERROR, String.format(
          "数据库中没有对应的自然人人id为:%s", custTrdPerson.getId()));
    }

    //校验重复
    List<CustTrdPerson> custTrdPersonList = getPersonByMobileList(custTrdPerson);
    if(!CollectionUtils.isEmpty(custTrdPersonList)){
      if(custTrdPersonList.size() ==1 && custTrdPersonList.get(0).getId().equals(custTrdPerson.getId())){
        log.info("查询到自身");
      }else {
        //cannot update
        log.error("There is already person name:{} phone:{} reject insert",
            custTrdPerson.getName(), custTrdPerson.getMobileUpdate());
//        throw ExceptionUtils.getAmcException(AmcExceptions.DUPLICATE_RECORD_INSERT_ERROR, String.format(
//            "已经有姓名为:%s 电话为:%s 的记录, 请勿重复插入",
//            custTrdPerson.getName(),custTrdPerson.getMobileUpdate()));

        List<Long> duplicateIdList = new ArrayList();
        custTrdPersonList.forEach( person -> duplicateIdList.add(person.getId()));
        modifyResult.setSuccess(false);
        modifyResult.setErrCode("DUPLICATE_RECORD_UPDATE_ERROR");
        modifyResult.setIdList(duplicateIdList);
        return modifyResult;
      }
    }

    //电话号存入历史数据
    creatMobilePhoneHistory(custTrdPerson, originalCustTrdPerson);

    //记录修改历史
    commonHandler.creatPersonHistory(custTrdPerson.getUpdateBy(),
        "updateCustPerson","人工修改",originalCustTrdPerson);

    //修改自然人
    custTrdPersonMapper.updateByPrimaryKeySelective(custTrdPerson);

    return modifyResult;
  }


  @Override
  public List<CustTrdPerson> selectPersonByIdList(List<Long> idList) throws Exception{
    CustTrdPersonExample custTrdPersonExample = new CustTrdPersonExample();
    custTrdPersonExample.createCriteria().andIdIn(idList);
    List<CustTrdPerson> custTrdPersonList = custTrdPersonMapper.selectByExample(custTrdPersonExample);
    return custTrdPersonList;
  }

  /**
   * 历史电话号
   * @param custTrdPerson
   * @param originalCustTrdPerson
   */
  private void creatMobilePhoneHistory(CustTrdPerson custTrdPerson, CustTrdPerson originalCustTrdPerson) {
   
    //若改变了手机号和固话号，存入历史数据时要去重
    if(null != custTrdPerson.getMobileUpdate() &&
        !custTrdPerson.getMobileUpdate().equals(originalCustTrdPerson.getMobileUpdate())){
      //对比出修改的手机号
      StringBuilder sbMobileChange = new StringBuilder();
      String[] mobileUpdateOriginalArray = originalCustTrdPerson.getMobileUpdate().split(";");
      for (int i = 0; i < mobileUpdateOriginalArray.length; i++) {
        if(!custTrdPerson.getMobileUpdate().contains(mobileUpdateOriginalArray[i])){
          if(sbMobileChange.length() >1){
            sbMobileChange.append(";");
          }
          sbMobileChange.append(mobileUpdateOriginalArray[i]);
        }
      }

      if("-1".equals(originalCustTrdPerson.getMobileHistory())){
        custTrdPerson.setMobileHistory(sbMobileChange.toString());
      }else {
        String mobileHistory = String.format("%s;%s",originalCustTrdPerson.getMobileHistory(),
            sbMobileChange.toString());
        Set<String> mobileHistorySet = new HashSet(Arrays.asList(mobileHistory.split(";")));
        StringBuilder sbmobileHistory = new StringBuilder();
        for (String mobile : mobileHistorySet) {
          if(sbmobileHistory.length() >1){
            sbmobileHistory.append(";");
          }
          sbmobileHistory.append(mobile);
        }
        custTrdPerson.setMobileHistory(sbmobileHistory.toString());
      }
    }

    if(null != custTrdPerson.getPhoneUpdate() &&
        !custTrdPerson.getPhoneUpdate().equals(originalCustTrdPerson.getPhoneUpdate())){
      //对比出修改的固话号
      StringBuilder sbPhoneChange = new StringBuilder();
      String[] phoneUpdateOriginalArray = originalCustTrdPerson.getPhoneUpdate().split(";");
      for (int i = 0; i < phoneUpdateOriginalArray.length; i++) {
        if(!custTrdPerson.getPhoneUpdate().contains(phoneUpdateOriginalArray[i])){
          if(sbPhoneChange.length() >1){
            sbPhoneChange.append(";");
          }
          sbPhoneChange.append(phoneUpdateOriginalArray[i]);
        }
      }

      if("-1".equals(originalCustTrdPerson.getPhoneHistory())){
        custTrdPerson.setPhoneHistory(sbPhoneChange.toString());
      }else {
        String phoneHistory = String.format("%s;%s",originalCustTrdPerson.getPhoneHistory(),
            sbPhoneChange.toString());
        Set<String> phoneHistorySet = new HashSet(Arrays.asList(phoneHistory.split(";")));
        StringBuilder sbPhoneHistory = new StringBuilder();
        for (String phone : phoneHistorySet) {
          if(sbPhoneHistory.length() >1){
            sbPhoneHistory.append(";");
          }
          sbPhoneHistory.append(phone);
        }
        custTrdPerson.setPhoneHistory(sbPhoneHistory.toString());
      }
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void mergeCustPerson(List<Long> fromPersonIds, Long toPersonId, Long updateBy) throws Exception {

    CustTrdPersonExample custTrdPersonExample = new CustTrdPersonExample();
    fromPersonIds.add(toPersonId);
    custTrdPersonExample.createCriteria().andIdIn(fromPersonIds);
    List<CustTrdPerson>  custTrdPersonList = custTrdPersonMapper.selectByExample(custTrdPersonExample);
    if(fromPersonIds.size() != custTrdPersonList.size()){
      throw new Exception("未查到对应的自然人");
    }

    Map<Long, CustTrdPerson> custTrdPersonHashMap= new HashMap<>();
    for(CustTrdPerson custTrdPerson : custTrdPersonList){
      custTrdPersonHashMap.put(custTrdPerson.getId(), custTrdPerson);
    }

    // 合并到的自然人
    CustTrdPerson toCustTrdPerson = custTrdPersonHashMap.get(toPersonId);
    CustTrdPerson toCustTrdPersonNew = new CustTrdPerson();
    toCustTrdPersonNew.setId(toPersonId);
    toCustTrdPersonNew.setMobileUpdate(toCustTrdPerson.getMobileUpdate());
    toCustTrdPersonNew.setMobilePrep(toCustTrdPerson.getMobilePrep());
    toCustTrdPersonNew.setMobileHistory(toCustTrdPerson.getMobileHistory());
    toCustTrdPersonNew.setPhoneUpdate(toCustTrdPerson.getPhoneUpdate());
    toCustTrdPersonNew.setPhonePrep(toCustTrdPerson.getPhonePrep());
    toCustTrdPersonNew.setPhoneHistory(toCustTrdPerson.getPhoneHistory());

    //查询要合并的原订单
    for (Long personId : fromPersonIds) {
      if(personId.equals(toPersonId)){
        continue;
      }
      CustTrdPerson originalCustTrdPerson = custTrdPersonHashMap.get(personId);
      creatCustTrdPersonNew(toCustTrdPersonNew,originalCustTrdPerson);
      CustTrdPerson custTrdPerson = new CustTrdPerson();
      custTrdPerson.setId(personId);
      custTrdPerson.setStatus(PresonStatusEnum.MERGED_STATUS.getId());
      //查询要更改的交易
      CustTrdInfoExample custTrdInfoExample = new CustTrdInfoExample();
      custTrdInfoExample.createCriteria().andBuyerIdEqualTo(personId).andBuyerTypeEqualTo(CustTypeEnum.PERSON.getId());
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
          custTrdInfoNew.setBuyerId(toPersonId);
          //修改更改的交易指向新自然人
          custTrdInfoMapper.updateByPrimaryKeySelective(custTrdInfoNew);
        }
      }
      if(sbTrdInfoBak.length()>0){
        sbTrdInfoBak.append(";");
      }
      sbTrdInfoBak.append("toPersonId:");
      sbTrdInfoBak.append(toPersonId);
      custTrdPerson.setTrdInfoBak(sbTrdInfoBak.toString());
      commonHandler.creatPersonHistory(custTrdPerson.getUpdateBy(),
          "mergeCustPerson", String.format("人工合并至：%d",toPersonId), originalCustTrdPerson);
      custTrdPersonMapper.updateByPrimaryKeySelective(custTrdPerson);
    }

    //去重
    toCustTrdPersonNew.setMobileUpdate(commonHandler.removalPhone(toCustTrdPersonNew.getMobileUpdate()));
    toCustTrdPersonNew.setMobilePrep(commonHandler.removalPhone(toCustTrdPersonNew.getMobilePrep()));
    toCustTrdPersonNew.setMobileHistory(commonHandler.removalPhone(toCustTrdPersonNew.getMobileHistory()));
    toCustTrdPersonNew.setPhoneUpdate(commonHandler.removalPhone(toCustTrdPersonNew.getPhoneUpdate()));
    toCustTrdPersonNew.setPhonePrep(commonHandler.removalPhone(toCustTrdPersonNew.getPhonePrep()));
    toCustTrdPersonNew.setPhoneHistory(commonHandler.removalPhone(toCustTrdPersonNew.getPhoneHistory()));

    //修改合并人的电话
    commonHandler.creatPersonHistory(updateBy, "mergeCustPerson",
        "合并记录", toCustTrdPerson);
    custTrdPersonMapper.updateByPrimaryKeySelective(toCustTrdPersonNew);
  }

  private void creatCustTrdPersonNew(CustTrdPerson toCustTrdPersonNew,
      CustTrdPerson originalCustTrdPerson) {
    if(null != originalCustTrdPerson.getMobileUpdate() &&!"-1".equals(originalCustTrdPerson.getMobileUpdate())){
      if(null == toCustTrdPersonNew.getMobileUpdate() || "-1".equals(toCustTrdPersonNew.getMobileUpdate())){
        toCustTrdPersonNew.setMobileUpdate(originalCustTrdPerson.getMobileUpdate());
      }else {
        toCustTrdPersonNew.setMobileUpdate(toCustTrdPersonNew.getMobileUpdate()+";"+originalCustTrdPerson.getMobileUpdate());
      }
    }

    if(null != originalCustTrdPerson.getMobilePrep() &&!"-1".equals(originalCustTrdPerson.getMobilePrep())){
      if(null == toCustTrdPersonNew.getMobilePrep() || "-1".equals(toCustTrdPersonNew.getMobilePrep())){
        toCustTrdPersonNew.setMobilePrep(originalCustTrdPerson.getMobilePrep());
      }else {
        toCustTrdPersonNew.setMobilePrep(toCustTrdPersonNew.getMobilePrep()+";"+originalCustTrdPerson.getMobilePrep());
      }
    }

    if(null != originalCustTrdPerson.getMobileHistory() &&!"-1".equals(originalCustTrdPerson.getMobileHistory())){
      if(null == toCustTrdPersonNew.getMobileHistory() || "-1".equals(toCustTrdPersonNew.getMobileHistory())){
        toCustTrdPersonNew.setMobileHistory(originalCustTrdPerson.getMobileHistory());
      }else {
        toCustTrdPersonNew.setMobileHistory(toCustTrdPersonNew.getMobileHistory()+";"+originalCustTrdPerson.getMobileHistory());
      }
    }

    if(null != originalCustTrdPerson.getPhoneUpdate() &&!"-1".equals(originalCustTrdPerson.getPhoneUpdate())){
      if(null == toCustTrdPersonNew.getPhoneUpdate() || "-1".equals(toCustTrdPersonNew.getPhoneUpdate())){
        toCustTrdPersonNew.setPhoneUpdate(originalCustTrdPerson.getPhoneUpdate());
      }else {
        toCustTrdPersonNew.setPhoneUpdate(toCustTrdPersonNew.getPhoneUpdate()+";"+originalCustTrdPerson.getPhoneUpdate());
      }
    }

    if(null != originalCustTrdPerson.getPhonePrep() &&!"-1".equals(originalCustTrdPerson.getPhonePrep())){
      if(null == toCustTrdPersonNew.getPhonePrep() || "-1".equals(toCustTrdPersonNew.getPhonePrep())){
        toCustTrdPersonNew.setPhonePrep(originalCustTrdPerson.getPhonePrep());
      }else {
        toCustTrdPersonNew.setPhonePrep(toCustTrdPersonNew.getPhonePrep()+";"+originalCustTrdPerson.getPhonePrep());
      }
    }

    if(null != originalCustTrdPerson.getPhoneHistory() &&!"-1".equals(originalCustTrdPerson.getPhoneHistory())){
      if(null == toCustTrdPersonNew.getPhoneHistory() || "-1".equals(toCustTrdPersonNew.getPhoneHistory())){
        toCustTrdPersonNew.setPhoneHistory(originalCustTrdPerson.getPhoneHistory());
      }else {
        toCustTrdPersonNew.setPhoneHistory(toCustTrdPersonNew.getPhoneHistory()+";"+originalCustTrdPerson.getPhoneHistory());
      }
    }
  }
}
