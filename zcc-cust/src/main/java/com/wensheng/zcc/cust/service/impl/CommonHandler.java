package com.wensheng.zcc.cust.service.impl;

import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustCmpycontactorHistoryMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdCmpyHistoryMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdCmpyMapper;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcCmpycontactor;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustCmpycontactorHistory;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpyHistory;
import com.wensheng.zcc.cust.module.dao.mysql.ext.CustTrdCmpyExtExample;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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

  public void creatCmpyHistory(Long updateBy, String updateMethod, CustTrdCmpy custTrdCmpyOriginal) {
    //保存公司历史信息
    CustTrdCmpyHistory custTrdCmpyHistory = new CustTrdCmpyHistory();
    AmcBeanUtils.copyProperties(custTrdCmpyOriginal, custTrdCmpyHistory);
    custTrdCmpyHistory.setCmpyId(custTrdCmpyOriginal.getId());
    custTrdCmpyHistory.setId(null);
    custTrdCmpyHistory.setCreateBy(updateBy);
    custTrdCmpyHistory.setCreateTime(new Date());
    custTrdCmpyHistory.setUpdateMethod(updateMethod);
    custTrdCmpyHistoryMapper.insertSelective(custTrdCmpyHistory);
  }

  public void creatCmpycontactorHistory(Long updateBy, String updateMethod, CustAmcCmpycontactor originalCmpycontactor) {
    CustCmpycontactorHistory custCmpycontactorHistory = new CustCmpycontactorHistory();
    AmcBeanUtils.copyProperties(originalCmpycontactor, custCmpycontactorHistory);
    custCmpycontactorHistory.setCmpycontactorId(originalCmpycontactor.getId());
    custCmpycontactorHistory.setId(null);
    custCmpycontactorHistory.setCreateBy(updateBy);
    custCmpycontactorHistory.setCreateTime(new Date());
    custCmpycontactorHistory.setUpdateMethod(updateMethod);
    custCmpycontactorHistoryMapper.insertSelective(custCmpycontactorHistory);
  }


}
