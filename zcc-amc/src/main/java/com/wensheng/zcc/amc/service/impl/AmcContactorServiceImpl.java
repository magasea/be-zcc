package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtContactorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtMapper;
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

  private void updateOrInsertContactor(List<SSOAmcUser> ssoAmcUsers) {
    AmcDebtContactorExample amcDebtContactorExample = new AmcDebtContactorExample();
    AmcDebtContactor amcDebtContactor = null;
    for(SSOAmcUser ssoAmcUser: ssoAmcUsers){
      amcDebtContactorExample.clear();
      amcDebtContactor = null;
      amcDebtContactorExample.createCriteria().andPhoneNumberEqualTo(ssoAmcUser.getMobilePhone());
      List<AmcDebtContactor> amcDebtContactors = amcDebtContactorMapper.selectByExample(amcDebtContactorExample);
      if(CollectionUtils.isEmpty(amcDebtContactors)){
        log.info("Need insert user for {}", ssoAmcUser.getMobilePhone());
        amcDebtContactor = new AmcDebtContactor();
        amcDebtContactor.setName(ssoAmcUser.getUserName());
        amcDebtContactor.setLocation(ssoAmcUser.getLocation());
        amcDebtContactor.setNikname(ssoAmcUser.getUserName());
        amcDebtContactorMapper.insertSelective(amcDebtContactor);
      }else{
        if(amcDebtContactors.size() > 1){
          log.error("There is duplicate user with phone:{}, need make it clean", ssoAmcUser.getMobilePhone());
          amcDebtContactorMapper.deleteByExample(amcDebtContactorExample);
          amcDebtContactor = new AmcDebtContactor();
          amcDebtContactor.setName(ssoAmcUser.getUserName());
          amcDebtContactor.setLocation(ssoAmcUser.getLocation());
          amcDebtContactor.setNikname(ssoAmcUser.getUserName());
          amcDebtContactorMapper.insertSelective(amcDebtContactor);
        }else{
          if(amcDebtContactors.get(0).getUpdateTime().before(AmcDateUtils.getDateMonthsDiff(1))){
            log.info("it is one month early record, need update");
            amcDebtContactors.get(0).setLocation(ssoAmcUser.getLocation());
            amcDebtContactors.get(0).setName(ssoAmcUser.getUserName());
            amcDebtContactors.get(0).setUpdateTime(AmcDateUtils.toDate(Instant.now().getEpochSecond()));
            amcDebtContactorMapper.updateByPrimaryKey(amcDebtContactors.get(0));
          }
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
