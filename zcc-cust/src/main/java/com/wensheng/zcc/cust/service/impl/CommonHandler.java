package com.wensheng.zcc.cust.service.impl;

import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustCmpycontactorHistoryMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdCmpyHistoryMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdCmpyMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdPersonHistoryMapper;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcCmpycontactor;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustCmpycontactorHistory;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpyHistory;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPersonHistory;
import com.wensheng.zcc.cust.module.dao.mysql.ext.CustTrdCmpyExtExample;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CommonHandler {
  @Autowired
  CustTrdCmpyMapper custTrdCmpyMapper;

  @Autowired
  CustTrdCmpyHistoryMapper custTrdCmpyHistoryMapper;

  @Autowired
  CustCmpycontactorHistoryMapper custCmpycontactorHistoryMapper;

  @Autowired
  CustTrdPersonHistoryMapper custTrdPersonHistoryMapper;

  private static RestTemplate restTemplate;

  //得到 restTemplate
  public static RestTemplate getRestTemplate(){
    if(null == restTemplate){
      synchronized(CommonHandler.class){
        restTemplate = new RestTemplate();
        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        gsonHttpMessageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL) );
        restTemplate.getMessageConverters().removeIf(item -> item instanceof MappingJackson2HttpMessageConverter);
        restTemplate.getMessageConverters().removeIf(item -> item instanceof MappingJackson2XmlHttpMessageConverter);
        restTemplate.getMessageConverters().add(gsonHttpMessageConverter);
      }
    }
    return restTemplate;
  }

  /**
   *根据公司名称查询,是否已经有该公司。
   * @return
   */
  public List<CustTrdCmpy> queryCmpyByName(String cmpyName){
    //按照公司名称和公司历史名称进行查询
    CustTrdCmpyExtExample custTrdCmpyExtExample = new CustTrdCmpyExtExample();
    custTrdCmpyExtExample.createCriteria().andCmpyNameEqualTo(cmpyName);
    custTrdCmpyExtExample.or().andCmpyNameHistoryLike(String.format("%s%s%s","%",cmpyName,"%"));
    custTrdCmpyExtExample.setOrderByClause(" id desc");
    List<CustTrdCmpy> custTrdCmpies= custTrdCmpyMapper.selectByExample(custTrdCmpyExtExample);
    List<CustTrdCmpy> custTrdCmpyList = new ArrayList<>();
    for (CustTrdCmpy custTrdCmpy : custTrdCmpies) {
      if(cmpyName.equals(custTrdCmpy.getCmpyName())){
        custTrdCmpyList.add(custTrdCmpy);
        continue;
      }
      String[] cmpyNameHistoryArray =custTrdCmpy.getCmpyNameHistory().split(";");
      boolean match =false;
      for (int i = 0; i < cmpyNameHistoryArray.length; i++) {
        if(cmpyName.equals(cmpyNameHistoryArray[i])){
          match =true;
          continue;
        }
      }
      if(match){
        custTrdCmpyList.add(custTrdCmpy);
      }
    }
    return custTrdCmpyList;
  }

  /**
   * 创建公司历史
   * @param updateBy
   * @param updateMethod
   * @param remark
   * @param custTrdCmpyOriginal
   */
  public void creatCmpyHistory(Long updateBy, String updateMethod, String remark,
      CustTrdCmpy custTrdCmpyOriginal) {
    //保存公司历史信息
    CustTrdCmpyHistory custTrdCmpyHistory = new CustTrdCmpyHistory();
    AmcBeanUtils.copyProperties(custTrdCmpyOriginal, custTrdCmpyHistory);
    custTrdCmpyHistory.setCmpyId(custTrdCmpyOriginal.getId());
    custTrdCmpyHistory.setId(null);
    custTrdCmpyHistory.setCreateBy(updateBy);
    custTrdCmpyHistory.setCreateTime(new Date());
    custTrdCmpyHistory.setUpdateMethod(updateMethod);
    custTrdCmpyHistory.setRemark(remark);
    custTrdCmpyHistoryMapper.insertSelective(custTrdCmpyHistory);
  }

  /**
   * 创建公司联系人历史
   * @param updateBy
   * @param updateMethod
   * @param remark
   * @param originalCmpycontactor
   */
  public void creatCmpycontactorHistory(Long updateBy, String updateMethod, String remark,
      CustAmcCmpycontactor originalCmpycontactor) {
    CustCmpycontactorHistory custCmpycontactorHistory = new CustCmpycontactorHistory();
    AmcBeanUtils.copyProperties(originalCmpycontactor, custCmpycontactorHistory);
    custCmpycontactorHistory.setCmpycontactorId(originalCmpycontactor.getId());
    custCmpycontactorHistory.setId(null);
    custCmpycontactorHistory.setCreateBy(updateBy);
    custCmpycontactorHistory.setCreateTime(new Date());
    custCmpycontactorHistory.setUpdateMethod(updateMethod);
    custCmpycontactorHistory.setRemark(remark);
    custCmpycontactorHistoryMapper.insertSelective(custCmpycontactorHistory);
  }

  /**
   * 电话去重
   * @param phone
   * @return
   */
  public String removalPhone(String phone) {
    String[] phoneArray = phone.split(";");
    Set<String> phoneSet = new HashSet();
    for(int i=0;i<phoneArray.length;i++){
      phoneSet.add(phoneArray[i]);
    }
    StringBuffer sbPhoneNew = new StringBuffer();
    for (String phoneNew:phoneSet){
      if(sbPhoneNew.length()>0){
        sbPhoneNew.append(";");
      }
      sbPhoneNew.append(phoneNew);
    }
    return sbPhoneNew.toString();
  }

  /**
   * 创建自然人历史
   * @param updateBy
   * @param updateMethod
   * @param remark
   * @param originalCustTrdPerson
   */
  public void creatPersonHistory(Long updateBy, String updateMethod, String remark,
      CustTrdPerson originalCustTrdPerson) {
    CustTrdPersonHistory custTrdPersonHistory = new CustTrdPersonHistory();
    AmcBeanUtils.copyProperties(originalCustTrdPerson, custTrdPersonHistory);
    custTrdPersonHistory.setPersonId(originalCustTrdPerson.getId());
    custTrdPersonHistory.setId(null);
    custTrdPersonHistory.setCreateBy(updateBy);
    custTrdPersonHistory.setCreateTime(new Date());
    custTrdPersonHistory.setUpdateMethod(updateMethod);
    custTrdPersonHistory.setRemark(remark);
    custTrdPersonHistoryMapper.insertSelective(custTrdPersonHistory);
  }
}
