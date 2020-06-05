package com.wensheng.zcc.cust.service.impl;

import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdPersonMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.ext.CustTrdPersonExtMapper;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import com.wensheng.zcc.cust.module.helper.PresonStatusEnum;
import com.wensheng.zcc.cust.service.CustPersonService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class CustPersonServiceImpl implements CustPersonService {

  @Autowired
  CustTrdPersonMapper custTrdPersonMapper;
  @Autowired
  CustTrdPersonExtMapper custTrdPersonExtMapper;
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
    custTrdPersonMapper.insert(custTrdPerson);
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
  public void updateCustPerson(CustTrdPerson custTrdPerson) throws Exception {
    //查询要修改的自然人
    CustTrdPerson originalCustTrdPerson = custTrdPersonMapper.selectByPrimaryKey(custTrdPerson.getId());
    if(null == originalCustTrdPerson){
      log.error("There is no person in db id:{}", custTrdPerson.getId());
      throw ExceptionUtils.getAmcException(AmcExceptions.NO_ORIGINAL_INFO_ERROR, String.format(
          "数据库中没有对应的自然人人id为:%s", custTrdPerson.getId()));
    }

    //校验重复
    List<CustTrdPerson> custTrdPeopleList = getPersonByMobileList(custTrdPerson);
    if(!CollectionUtils.isEmpty(custTrdPeopleList)){
      if(custTrdPeopleList.size() ==1 && custTrdPeopleList.get(0).getId().equals(custTrdPerson.getId())){
        log.info("查询到自身");
      }else {
        //cannot update
        log.error("There is already person name:{} phone:{} reject insert",
            custTrdPerson.getName(), custTrdPerson.getMobileUpdate());
        throw ExceptionUtils.getAmcException(AmcExceptions.DUPLICATE_RECORD_INSERT_ERROR, String.format(
            "已经有姓名为:%s 电话为:%s 的记录, 请勿重复插入",
            custTrdPerson.getName(),custTrdPerson.getMobileUpdate()));
      }
    }

    //电话号存入历史数据
    creatMobilePhoneHistory(custTrdPerson, originalCustTrdPerson);

    //记录修改历史
    commonHandler.creatPersonHistory(custTrdPerson.getUpdateBy(),
        "updateCustPerson","人工修改",originalCustTrdPerson);

    //修改自然人
    custTrdPersonMapper.updateByPrimaryKeySelective(custTrdPerson);
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
  public void mergeCustPerson(List<Long> fromPersonIds, String toPersonId) throws Exception {
    List<CustTrdPerson> custTrdPersonList = new ArrayList<>();
    //查询要合并的原订单
    for (Long personId : fromPersonIds) {
      CustTrdPerson custTrdPerson = new CustTrdPerson();
      custTrdPerson.setId(personId);
      custTrdPerson.setStatus(PresonStatusEnum.MERGED_STATUS.getId());
      custTrdPersonList.add(custTrdPerson);
    }

    //

  }


}
