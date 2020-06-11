package com.wensheng.zcc.wechat.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.mysql.cj.x.protobuf.MysqlxCursor.Open;
import com.wensheng.zcc.common.module.dto.WXUserWatchObject;
import com.wensheng.zcc.common.module.dto.WechatUserInfo;
import com.wensheng.zcc.common.utils.AmcDateUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import com.wensheng.zcc.wechat.dao.mysql.mapper.WechatUserMapper;
import com.wensheng.zcc.wechat.dao.mysql.mapper.WechatUserStaticMapper;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatUser;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatUserExample;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatUserStatic;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatUserStaticExample;
import com.wensheng.zcc.wechat.module.vo.WXGetTicketResp;
import com.wensheng.zcc.wechat.module.vo.WXSign4Url;
import com.wensheng.zcc.wechat.service.WXBasicService;
import com.wensheng.zcc.wechat.service.WXToolService;
import com.wensheng.zcc.wechat.utils.WxToolsUtil;
import com.wensheng.zcc.wechat.utils.wechat.SHA1;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class WXToolServiceImpl implements WXToolService {

  @Autowired
  WXBasicService wxBasicService;

  @Value("${weixin.get_ticket_url}")
  String getTicketUrl;

  @Value("${recomm.urls.notifyUserLogin}")
  String notifyUserLogin;

  @Value("${recomm.urls.getUsrVisitInfo}")
  String getUsrVisitInfo;

  @Autowired
  WechatUserMapper wechatUserMapper;

  @Autowired
  WechatUserStaticMapper wechatUserStaticMapper;

  @Autowired
  MongoTemplate mongoTemplate;


  private Gson gson = new Gson();

  private RestTemplate restTemplate = new RestTemplate();
  @PostConstruct
  void init(){
    GsonBuilder gson = new GsonBuilder();
    restTemplate.getMessageConverters().removeIf(item -> item instanceof MappingJackson2HttpMessageConverter);
    restTemplate.getMessageConverters().removeIf(item -> item instanceof MappingJackson2XmlHttpMessageConverter);
    GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
    gsonHttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON));

    gsonHttpMessageConverter.setGson(gson.create());
//    ByteArrayHttpMessageConverter byteArrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
//    FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
    restTemplate.getMessageConverters().add(gsonHttpMessageConverter);
//    restTemplate.getMessageConverters().add(formHttpMessageConverter);
//    restTemplate.getMessageConverters().add(byteArrayHttpMessageConverter);
//    restTemplate.getMessageConverters().add(new FormHttpMessageConverter());

  }
  public HttpHeaders getHttpJsonHeader(){
    HttpHeaders headers = new HttpHeaders();
    headers.getAccept().clear();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    return headers;
  }

  @Override
  public WXSign4Url makeSignKey(String url) throws Exception {
    WXSign4Url wxSign4Url = new WXSign4Url();
    String token = wxBasicService.getPublicToken();
    String getTicketUrlFinal = String.format(getTicketUrl, token);
    HttpHeaders headers = getHttpJsonHeader();
    HttpEntity entity = new HttpEntity<>(null, headers);
    ResponseEntity<WXGetTicketResp> resp = restTemplate.exchange(getTicketUrlFinal, HttpMethod.GET, entity,
        WXGetTicketResp.class);

    if(resp.getBody().getErrcode() != null && resp.getBody().getErrcode() != 0){
      throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_WECHAT_PARAMETER, String.format("%s:%s",
          resp.getBody().getErrcode(), resp.getBody().getErrmsg()));
    }

    String ticket = resp.getBody().getTicket();
    log.info(ticket);
    String randomStr = WxToolsUtil.getRandomStr();
//    String fixRandomStr = "WENSHENGAMC";
    Long timeStamp = Instant.now().getEpochSecond();
    String signatureLocal = SHA1.getSHAFixSort(ticket, timeStamp.toString(), randomStr,url);
    wxSign4Url.setRandomStr(randomStr);
    wxSign4Url.setSignKey(signatureLocal);
    wxSign4Url.setTimeStamp(timeStamp);
    wxSign4Url.setUrl(url);
    wxSign4Url.setTicket(ticket);
    wxSign4Url.setAccessToken(token);
    return wxSign4Url;

  }

  @Override
  @Scheduled(cron = "${spring.task.scheduling.cronSyncUserFromRecommExpr}")
  public void syncUserVisitInfoWithRecomm() {
    int page = 0;
    int pageSize = 20;
    HttpHeaders httpHeaders = getHttpJsonHeader();
    Map<String, Integer> param = new HashMap<>();
    param.put("page", page);
    param.put("pageSize", pageSize);
    HttpEntity<Map> entity = new HttpEntity<>(param, httpHeaders);
    ResponseEntity<RecommUserInfoResp> resp = restTemplate.exchange(getUsrVisitInfo, HttpMethod.POST, entity, RecommUserInfoResp.class);
    while(!CollectionUtils.isEmpty(resp.getBody().getInfos())){
      processRecommUserInfo(resp.getBody().getInfos());
      page += 1;
      param.clear();
      param.put("page", page);
      param.put("pageSize", pageSize);
      resp = restTemplate.exchange(getUsrVisitInfo, HttpMethod.POST, entity, RecommUserInfoResp.class);
    }

  }

  private void processRecommUserInfo(List<RecmmUserInfo> infos) {
    WechatUserExample wechatUserExample = new WechatUserExample();
    WechatUserStaticExample wechatUserStaticExample = new WechatUserStaticExample();
    Query query = null;
    for(RecmmUserInfo recmmUserInfo: infos){
      wechatUserExample.clear();
      wechatUserStaticExample.clear();
      wechatUserExample.createCriteria().andOpenIdEqualTo(recmmUserInfo.getOpenId());
      List<WechatUser> wechatUsers = wechatUserMapper.selectByExample(wechatUserExample);
      if(CollectionUtils.isEmpty(wechatUsers)){
        log.error("There is no such user in zcc for openId:{}", recmmUserInfo.getOpenId());
        continue;
      }
      wechatUserStaticExample.createCriteria().andWechatUserIdEqualTo(wechatUsers.get(0).getId());
      List<WechatUserStatic> wechatUserStatics = wechatUserStaticMapper.selectByExample(wechatUserStaticExample);
      WechatUserStatic wechatUserStatic = null;
      if(CollectionUtils.isEmpty(wechatUserStatics)){
        wechatUserStatic = new WechatUserStatic();
        wechatUserStatic.setWechatUserId(wechatUsers.get(0).getId());
        wechatUserStaticMapper.insertSelective(wechatUserStatic);

      }else{
        wechatUserStatic = wechatUserStatics.get(0);
      }
      if(recmmUserInfo.getTimestamp() != null){
        wechatUserStatic.setLastTime(AmcDateUtils.toUTCDateFromLocal(recmmUserInfo.getTimestamp()));
      }
      wechatUserStatic.setOnlineTime(recmmUserInfo.getTimeSpent());
      query = new Query();
      query.addCriteria(Criteria.where("openId").is(recmmUserInfo.getOpenId()));
      List<WXUserWatchObject> wxUserWatchObjects = mongoTemplate.find(query, WXUserWatchObject.class);
      wechatUserStatic.setWatchCount(Long.valueOf(wxUserWatchObjects.size()));
      wechatUserStaticMapper.updateByPrimaryKeySelective(wechatUserStatic);
      query = null;

    }
  }

  @Override
  public void notifyWechatUserLogin(WechatUserInfo wechatUserInfo) {
    HttpHeaders httpHeaders = getHttpJsonHeader();
    HttpEntity<WechatUserInfo> httpEntity = new HttpEntity<>(wechatUserInfo, httpHeaders);
    ResponseEntity resp = restTemplate.exchange(notifyUserLogin, HttpMethod.POST, httpEntity, String.class);
  }

  @Override
  public void patchUserFirstLoginTime(String jsonItems) {
    Type listType = new TypeToken<ArrayList<OpenIdTimestamp>>(){}.getType();
    List<OpenIdTimestamp> openIdTimestamps = gson.fromJson(jsonItems, listType);
    for(OpenIdTimestamp openIdTimestamp: openIdTimestamps){
      log.info(gson.toJson(openIdTimestamp));
      WechatUserExample wechatUserExample = new WechatUserExample();
      wechatUserExample.createCriteria().andOpenIdEqualTo(openIdTimestamp.getOpenId());
      List<WechatUser> wechatUsers = wechatUserMapper.selectByExample(wechatUserExample);
      if(!CollectionUtils.isEmpty(wechatUsers)){
        WechatUserStaticExample wechatUserStaticExample = new WechatUserStaticExample();
        wechatUserStaticExample.createCriteria().andWechatUserIdEqualTo(wechatUsers.get(0).getId());
        List<WechatUserStatic> wechatUserStatics = wechatUserStaticMapper.selectByExample(wechatUserStaticExample);
        if(CollectionUtils.isEmpty(wechatUserStatics)){
          WechatUserStatic wechatUserStatic = new WechatUserStatic();
          wechatUserStatic.setWechatUserId(wechatUsers.get(0).getId());
          wechatUserStatic.setFirstLoginTime(AmcDateUtils.toUTCDateFromLocal(openIdTimestamp.getTimeStamp()));
          wechatUserStaticMapper.insertSelective(wechatUserStatic);
        }else{
          wechatUserStatics.get(0).setFirstLoginTime(AmcDateUtils.toUTCDateFromLocal(openIdTimestamp.getTimeStamp()));
          wechatUserStaticMapper.updateByPrimaryKeySelective(wechatUserStatics.get(0));
        }
      }else{
        log.error( "no such openId:{}", openIdTimestamp.getOpenId());
      }
    }
  }

  @Data
  class RecommUserInfoResp{
    Integer page;
    Integer pageSize;
    List<String> openIds;
    List<RecmmUserInfo> infos;
  }

  @Data
  class RecmmUserInfo{
    @SerializedName("openid")
    String openId;
    Long timeSpent;
    String disposal;
    String link;
    String title;
    Long timestamp;
  }

  @Data
  class OpenIdTimestamp{
    @SerializedName("_id")
    String openId;
    @SerializedName("timestamp")
    Long timeStamp;

  }


}
