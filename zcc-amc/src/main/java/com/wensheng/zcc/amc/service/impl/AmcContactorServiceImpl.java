package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.dao.mysql.mapper.AmcAssetMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtContactorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtMapper;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAsset;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAssetExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtContactor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtContactorExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtExample;
import com.wensheng.zcc.amc.service.AmcContactorService;
import com.wensheng.zcc.common.params.AmcPage;
import com.wensheng.zcc.common.params.PageInfo;
import com.wensheng.zcc.common.params.sso.AmcDeptEnum;
import com.wensheng.zcc.common.params.sso.SSOAmcUser;
import com.wensheng.zcc.common.utils.AmcDateUtils;
import com.wensheng.zcc.common.utils.sso.SSOQueryParam;
import java.time.Instant;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class AmcContactorServiceImpl implements AmcContactorService {

  private RestTemplate restTemplate = new RestTemplate();

  @Value("${wssso.url}")
  String ssoUrl;

  @Autowired
  AmcDebtContactorMapper amcDebtContactorMapper;

  @Autowired
  AmcDebtMapper amcDebtMapper;

  @Autowired
  AmcAssetMapper amcAssetMapper;

  private void connectDebtWithContactor(List<AmcDebt> amcDebts){
    for(AmcDebt amcDebt: amcDebts){
      String amcDebtContactor = amcDebt.getAmcContactorName();
      AmcDebtContactorExample amcDebtContactorExample = new AmcDebtContactorExample();
      amcDebtContactorExample.createCriteria().andNameEqualTo(amcDebtContactor);
      List<AmcDebtContactor> amcDebtContactors = amcDebtContactorMapper.selectByExample(amcDebtContactorExample);
      if(!CollectionUtils.isEmpty(amcDebtContactors)){
        if(amcDebtContactors.size() > 1){
          log.error("duplicate name in amcDebtContactors:{}", amcDebtContactor);
          continue;
        }
        amcDebt.setAmcContactorId(amcDebtContactors.get(0).getId());
        AmcAssetExample amcAssetExample = new AmcAssetExample();
        amcAssetExample.createCriteria().andDebtIdEqualTo(amcDebt.getId());
        List<AmcAsset> amcAssets = amcAssetMapper.selectByExample(amcAssetExample);
        for(AmcAsset amcAsset: amcAssets){
          amcAsset.setAmcContactorId(amcDebtContactors.get(0).getId());
          amcAssetMapper.updateByPrimaryKeySelective(amcAsset);
        }
        amcDebtMapper.updateByPrimaryKeySelective(amcDebt);
      }
    }
  }

  @Override
  public void initializeDebtContactor(){
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.setOrderByClause(" id desc ");
    Integer offset = 0;
    Integer pageSize = 20;
    RowBounds rowBounds = new RowBounds(offset, pageSize);
    List<AmcDebt> amcDebts =  amcDebtMapper.selectByExampleWithRowbounds(amcDebtExample, rowBounds);
    while(!CollectionUtils.isEmpty(amcDebts)){
      connectDebtWithContactor(amcDebts);
      offset += pageSize;
      rowBounds = new RowBounds(offset, pageSize);
      amcDebts =  amcDebtMapper.selectByExampleWithRowbounds(amcDebtExample, rowBounds);
    }
  }


  @Override
  @Scheduled(cron = "${spring.task.scheduling.cronExprSSO}")
  public void syncContactorWithSSO() {
    SSOQueryParam ssoQueryParam = new SSOQueryParam();
    PageInfo pageInfo = new PageInfo();
    int pageNum = 1;
    int pageSize = 20;
    while(pageNum > 0){
      pageInfo.setPage(pageNum);
      pageInfo.setSize(pageSize);
      HttpHeaders headers = getHttpJsonHeader();
      ssoQueryParam.setPageInfo(pageInfo);
      ssoQueryParam.setDeptId(AmcDeptEnum.BUSINESS_DEPT.getId());
      HttpEntity<SSOQueryParam> entity = new HttpEntity<>(ssoQueryParam, headers);

      ResponseEntity response = restTemplate.exchange(ssoUrl, HttpMethod.POST, entity,
          new ParameterizedTypeReference<AmcPage<SSOAmcUser>>() {} );
      AmcPage<SSOAmcUser> resp = (AmcPage<SSOAmcUser>) response.getBody();
      if(!CollectionUtils.isEmpty(resp.getContent())){
        pageNum = pageNum + 1;
      }else{
        pageNum  = -1 ;
        break;
      }

      updateOrInsertContactor(resp.getContent());
    }
    AmcDebtContactorExample amcDebtContactorExample = new AmcDebtContactorExample();
    amcDebtContactorExample.createCriteria().andUpdateTimeLessThan(AmcDateUtils.getDateMonthsDiff(2));
    List<AmcDebtContactor> contactorsToDel = amcDebtContactorMapper.selectByExample(amcDebtContactorExample);
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    if(!CollectionUtils.isEmpty(contactorsToDel)){
      log.info("There is {} contactor need to be delete", contactorsToDel.size());
      for(AmcDebtContactor amcDebtContactor: contactorsToDel){
        amcDebtExample.clear();
        amcDebtExample.createCriteria().andAmcContactorIdEqualTo(amcDebtContactor.getId());
        List<AmcDebt> amcDebts =  amcDebtMapper.selectByExample(amcDebtExample);
        if(CollectionUtils.isEmpty(amcDebts)){
          log.info("Can delete contactor with id:{}", amcDebtContactor.getId());
          amcDebtContactorMapper.deleteByPrimaryKey(amcDebtContactor.getId());
        }
      }
    }
  }

  @Override
  public void syncContactorWithNewUser(SSOAmcUser amcUser) {
    if( ! (AmcDeptEnum.BUSINESS_DEPT.getId() ==  amcUser.getDeptId().intValue())){
      log.info("ignore none business dept user");
      return;
    }
    if(amcUser.getMobilePhone().equals("-1")|| amcUser.getUserName().equals("-1")||
        StringUtils.isEmpty(amcUser.getUserName()) || StringUtils.isEmpty(amcUser.getMobilePhone()) || amcUser.getUserCname().equals("-1")
        || StringUtils.isEmpty(amcUser.getUserCname())){
      log.info("not correct user info");
      return;
    }
    AmcDebtContactorExample amcDebtContactorExample = new AmcDebtContactorExample();
    amcDebtContactorExample.createCriteria().andPhoneNumberEqualTo(amcUser.getMobilePhone());
    List<AmcDebtContactor> amcDebtContactors = amcDebtContactorMapper.selectByExample(amcDebtContactorExample);

    if(CollectionUtils.isEmpty(amcDebtContactors)){
      log.info("need insert new created user");

    }else{
      log.error("there is duplicate user, no need to create");
      return;
    }
    AmcDebtContactor amcDebtContactor = new AmcDebtContactor();
    amcDebtContactor.setUpdateTime(AmcDateUtils.toUTCDate(Instant.now().getEpochSecond()));
    amcDebtContactor.setPhoneNumber(amcUser.getMobilePhone());
    amcDebtContactor.setNikname(String.format("%s经理", amcUser.getUserCname().substring(0, 1)));
    amcDebtContactor.setLocation(amcUser.getLocation());
    amcDebtContactor.setName(amcUser.getUserCname());
    amcDebtContactorMapper.insertSelective(amcDebtContactor);
  }

  @Override
  public AmcPage<SSOAmcUser> getSsoAmcUsers(SSOQueryParam ssoQueryParam) {
//    List<SSOAmcUser> amcUsers = new ArrayList<>();
    PageInfo pageInfo = new PageInfo();
    int pageNum = 1;
    int pageSize = 20;
    if(ssoQueryParam.getPageInfo().getSize() > 0){
       pageSize = ssoQueryParam.getPageInfo().getSize();
    }
//    int pageSize = 20;
//    while(pageNum > 0){
    pageInfo.setPage(ssoQueryParam.getPageInfo().getPage());
    pageInfo.setSize(pageSize);
    HttpHeaders headers = getHttpJsonHeader();
    ssoQueryParam.setPageInfo(pageInfo);
//    ssoQueryParam.setDeptId(AmcDeptEnum.BUSINESS_DEPT.getId());
    HttpEntity<SSOQueryParam> entity = new HttpEntity<>(ssoQueryParam, headers);

    ResponseEntity response = restTemplate.exchange(ssoUrl, HttpMethod.POST, entity,
        new ParameterizedTypeReference<AmcPage<SSOAmcUser>>() {} );
    AmcPage<SSOAmcUser> resp = (AmcPage<SSOAmcUser>) response.getBody();



    return resp;
  }

  private void updateOrInsertContactor(List<SSOAmcUser> ssoAmcUsers) {
    AmcDebtContactorExample amcDebtContactorExample = new AmcDebtContactorExample();
    AmcDebtContactor amcDebtContactor = null;
    for(SSOAmcUser ssoAmcUser: ssoAmcUsers){
      amcDebtContactorExample.clear();
      amcDebtContactor = null;
      if(ssoAmcUser.getMobilePhone().equals("-1")|| ssoAmcUser.getUserName().equals("-1")||
          StringUtils.isEmpty(ssoAmcUser.getUserName()) || StringUtils.isEmpty(ssoAmcUser.getMobilePhone()) || ssoAmcUser.getUserCname().equals("-1")
        || StringUtils.isEmpty(ssoAmcUser.getUserCname())){
        continue;
      }
      amcDebtContactorExample.createCriteria().andPhoneNumberEqualTo(ssoAmcUser.getMobilePhone());
      List<AmcDebtContactor> amcDebtContactors = amcDebtContactorMapper.selectByExample(amcDebtContactorExample);
      if(CollectionUtils.isEmpty(amcDebtContactors)){
        log.info("Need insert user for {}", ssoAmcUser.getMobilePhone());
        amcDebtContactor = new AmcDebtContactor();
        amcDebtContactor.setName(ssoAmcUser.getUserCname());
        amcDebtContactor.setLocation(ssoAmcUser.getLocation());
        amcDebtContactor.setPhoneNumber(ssoAmcUser.getMobilePhone());
        log.info(ssoAmcUser.getUserName());
        amcDebtContactor.setNikname(String.format("%s经理", ssoAmcUser.getUserCname().substring(0, 1)));
        amcDebtContactor.setUpdateTime(AmcDateUtils.toUTCDate(Instant.now().getEpochSecond()));
        amcDebtContactorMapper.insertSelective(amcDebtContactor);
      }else{
        if(amcDebtContactors.size() > 1){
          log.error("There is duplicate user with phone:{}, need make it clean", ssoAmcUser.getMobilePhone());
          amcDebtContactorMapper.deleteByExample(amcDebtContactorExample);
          amcDebtContactor = new AmcDebtContactor();
          amcDebtContactor.setName(ssoAmcUser.getUserCname());
          amcDebtContactor.setLocation(ssoAmcUser.getLocation());
          amcDebtContactor.setNikname(String.format("%s经理", ssoAmcUser.getUserCname().substring(0, 1)));
          amcDebtContactor.setUpdateTime(AmcDateUtils.toUTCDate(Instant.now().getEpochSecond()));
          amcDebtContactor.setPhoneNumber(ssoAmcUser.getMobilePhone());
          amcDebtContactorMapper.insertSelective(amcDebtContactor);
        }else{
//          if(amcDebtContactors.get(0).getUpdateTime().before(AmcDateUtils.getDateMonthsDiff(1))){
            log.info("it is one month early record, need update");
            amcDebtContactors.get(0).setLocation(ssoAmcUser.getLocation());
            amcDebtContactors.get(0).setName(ssoAmcUser.getUserCname());
            amcDebtContactors.get(0).setNikname(String.format("%s经理", ssoAmcUser.getUserCname().substring(0, 1)));
            amcDebtContactors.get(0).setUpdateTime(AmcDateUtils.toUTCDate(Instant.now().getEpochSecond()));
            amcDebtContactorMapper.updateByPrimaryKey(amcDebtContactors.get(0));
//          }
        }
      }

    }
  }

  public HttpHeaders getHttpJsonHeader(){
    HttpHeaders headers = new HttpHeaders();
    headers.getAccept().clear();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    return headers;
  }
}
