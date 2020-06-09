package com.wensheng.zcc.cust.service.impl;

import com.google.common.collect.Lists;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdCmpyMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdPersonMapper;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpyExample;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPersonExample;
import com.wensheng.zcc.cust.module.helper.CustTypeEnum;
import com.wensheng.zcc.cust.module.helper.PresonStatusEnum;
import com.wensheng.zcc.cust.module.vo.helper.PageResult;
import com.wensheng.zcc.cust.module.vo.recom.Cust4Asset;
import com.wensheng.zcc.cust.module.vo.recom.Cust4Debt;
import com.wensheng.zcc.cust.module.vo.recom.RecomQueryAssetRlt;
import com.wensheng.zcc.cust.module.vo.recom.RecomQueryDebtRlt;
import com.wensheng.zcc.cust.service.RecommService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class RecommServiceImpl implements RecommService {

  @Value("${recom.urls.getAssetCusts}")
  String getAssetCustsUrl;

  @Value("${recom.urls.getDebtCusts}")
  String getDebtCustsUrl;


  @Value("${recom.urls.getCombinedCustByDebt}")
  String getCombinedCustByDebt;

  private RestTemplate restTemplate;

  @PostConstruct
  private void init(){
    restTemplate = new RestTemplate();
    GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
    gsonHttpMessageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
    restTemplate.getMessageConverters().removeIf(item -> item instanceof MappingJackson2HttpMessageConverter);
    restTemplate.getMessageConverters().add(gsonHttpMessageConverter);
  }

  @Autowired
  CustTrdPersonMapper custTrdPersonMapper;

  @Autowired
  CustTrdCmpyMapper custTrdCmpyMapper;

  @Override
  public Cust4Debt queryDebtCusts(Long debtId) {
    String url = String.format(getDebtCustsUrl, debtId);
    log.info(url);
    Cust4Debt cust4Debt = new Cust4Debt();
    cust4Debt.setCustTrdCmpyList(new ArrayList<>());
    cust4Debt.setCustTrdPeople(new ArrayList<>());
    cust4Debt.setDebtId(debtId);

    RecomQueryDebtRlt[] recomQueryDebtRlts = null;
    try{
      recomQueryDebtRlts = restTemplate.getForObject(url, RecomQueryDebtRlt[].class);

    }catch (Exception ex){
      log.error("Got error:", ex);
    }

    if(recomQueryDebtRlts == null || recomQueryDebtRlts.length  == 0){
      log.error("Failed to get debtCusts for :{}", debtId);
      return cust4Debt;
    }

    List<Long> cmpyIds = new ArrayList<>();


    List<Long> personIds = new ArrayList<>();

    for(RecomQueryDebtRlt recomQueryDebtRlt: recomQueryDebtRlts){
      if(recomQueryDebtRlt.getType().equals(CustTypeEnum.COMPANY.getEname())){
        cmpyIds.add(recomQueryDebtRlt.getCustId());
      }else if(recomQueryDebtRlt.getType().equals(CustTypeEnum.PERSON.getEname())){
        personIds.add(recomQueryDebtRlt.getCustId());
      }
    }

    if(!CollectionUtils.isEmpty(cmpyIds)){
//      cust4Asset.setCustTrdCmpyList();
      CustTrdCmpyExample custTrdCmpyExample = new CustTrdCmpyExample();
      custTrdCmpyExample.createCriteria().andIdIn(cmpyIds);
      List<CustTrdCmpy> custTrdCmpyList = custTrdCmpyMapper.selectByExample(custTrdCmpyExample);
      cust4Debt.setCustTrdCmpyList(custTrdCmpyList);
    }
    if(!CollectionUtils.isEmpty(personIds)){
      CustTrdPersonExample custTrdPersonExample = new CustTrdPersonExample();
      custTrdPersonExample.createCriteria().andIdIn(personIds).andStatusNotEqualTo(PresonStatusEnum.MERGED_STATUS.getId());
      List<CustTrdPerson> custTrdPersonList = custTrdPersonMapper.selectByExample(custTrdPersonExample);
      cust4Debt.setCustTrdPeople(custTrdPersonList);
    }

    return cust4Debt;
  }

  @Override
  public Cust4Asset queryAssetCusts(Long assetId) {
    String url = String.format(getAssetCustsUrl, assetId);
    log.info(url);
    Cust4Asset cust4Asset = new Cust4Asset();
    cust4Asset.setCustTrdCmpyList(new ArrayList<>());
    cust4Asset.setCustTrdPeople(new ArrayList<>());
    RecomQueryAssetRlt[] result = null;

    RecomQueryAssetRlt[] recomQueryAssetRltList = null;
    try{

       result = restTemplate.getForObject(url,
          RecomQueryAssetRlt[].class);
//      recomQueryAssetRltList = result.getResults();
//
//
//      result = restTemplate.exchange(url, HttpMethod.GET, null,
//          new ParameterizedTypeReference<PageResult<RecomQueryAssetRlt>>() {}).getBody();
//      recomQueryAssetRltList = result.getResults();

    }catch (Exception ex){
      log.error("Got error:", ex);
    }
    List<Long> cmpyIds = new ArrayList<>();
    List<Long> personIds = new ArrayList<>();
    for(RecomQueryAssetRlt recomQueryAssetRlt: result){
      if(recomQueryAssetRlt.getType().equals(CustTypeEnum.COMPANY.getEname())){
        cmpyIds.add(recomQueryAssetRlt.getCustId());
      }else if(recomQueryAssetRlt.getType().equals(CustTypeEnum.PERSON.getEname())){
        personIds.add(recomQueryAssetRlt.getCustId());
      }
    }



    if(!CollectionUtils.isEmpty(cmpyIds)){
//      cust4Asset.setCustTrdCmpyList();
      CustTrdCmpyExample custTrdCmpyExample = new CustTrdCmpyExample();
      custTrdCmpyExample.createCriteria().andIdIn(cmpyIds);
      List<CustTrdCmpy> custTrdCmpyList = custTrdCmpyMapper.selectByExample(custTrdCmpyExample);
      cust4Asset.setCustTrdCmpyList(custTrdCmpyList);
    }
    if(!CollectionUtils.isEmpty(personIds)){
      CustTrdPersonExample custTrdPersonExample = new CustTrdPersonExample();
      custTrdPersonExample.createCriteria().andIdIn(personIds).andStatusNotEqualTo(PresonStatusEnum.MERGED_STATUS.getId());
      List<CustTrdPerson> custTrdPersonList = custTrdPersonMapper.selectByExample(custTrdPersonExample);
      cust4Asset.setCustTrdPeople(custTrdPersonList);
    }
    cust4Asset.setAssetId(assetId);




    return cust4Asset;
  }
}
