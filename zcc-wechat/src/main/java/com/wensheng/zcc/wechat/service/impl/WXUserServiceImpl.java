package com.wensheng.zcc.wechat.service.impl;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.wensheng.zcc.common.module.LatLng;
import com.wensheng.zcc.common.module.dto.WXUserGeoRecord;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.common.utils.AmcDateUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import com.wensheng.zcc.common.utils.GeoUtils;
import com.wensheng.zcc.wechat.dao.mysql.mapper.WechatUserMapper;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatTag;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatUser;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatUserExample;
import com.wensheng.zcc.wechat.module.vo.GeneralResp;
import com.wensheng.zcc.wechat.module.vo.TagCreate;
import com.wensheng.zcc.wechat.module.vo.TagDel;
import com.wensheng.zcc.wechat.module.vo.TagMod;
import com.wensheng.zcc.wechat.module.vo.WXUserGeoInfo;
import com.wensheng.zcc.wechat.module.vo.WechatTagVo;
import com.wensheng.zcc.wechat.service.WXBasicService;
import com.wensheng.zcc.wechat.service.WXUserService;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class WXUserServiceImpl implements WXUserService {

  @Autowired
  WXBasicService wxBasicService;


  @Value("${weixin.get_public_users_url}")
  String getPublicUsersUrl;

  @Value("${weixin.get_public_usersinfo_url}")
  String getPublicUsersInfoUrl;

  @Value("${weixin.get_usertags_url}")
  String getUserTagsUrl;

  @Value("${weixin.get_tagsofuser_url}")
  String getTagsOfUserUrl;

  @Value("${weixin.create_usertag_url}")
  String createUserTagUrl;


  @Value("${weixin.delete_usertag_url}")
  String delUserTagUrl;

  @Value("${weixin.mod_usertag_url}")
  String modUserTagUrl;


  @Value("${weixin.untaguser_batch_url}")
  String unTagUserBatchUrl;

  @Value("${weixin.taguser_batch_url}")
  String tagUserBatchUrl;

//  get_tag_usersids_url
  @Value("${weixin.get_useroftagid_url}")
  String getUsersOfTagIdUrl;

  @Autowired
  MongoTemplate mongoTemplate;


  @Autowired
  ComnfuncGrpcService comnfuncGrpcService;

  @Autowired
  WechatUserMapper wechatUserMapper;


  private Gson gson = new Gson();

  private RestTemplate restTemplate = new RestTemplate();
  @PostConstruct
  void init(){
    restTemplate.getMessageConverters().removeIf(item -> item instanceof MappingJackson2HttpMessageConverter);
    GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
    restTemplate.getMessageConverters().add(gsonHttpMessageConverter);
  }

  String nonce = "xxxxxx";
  String replyMsg = "我是中文abcd123";
  String xmlFormat = "<xml><ToUserName><![CDATA[toWxName]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
  String afterAesEncrypt = "jn1L23DB+6ELqJ+6bruv21Y6MD7KeIfP82D6gU39rmkgczbWwt5+3bnyg5K55bgVtVzd832WzZGMhkP72vVOfg==";
  String randomStr = "aaaabbbbccccdddd";

  String replyMsg2 = "<xml><ToUserName><![CDATA[oia2Tj我是中文jewbmiOUlr6X-1crbLOvLw]]></ToUserName><FromUserName><![CDATA[gh_7f083739789a]]></FromUserName><CreateTime>1407743423</CreateTime><MsgType><![CDATA[video]]></MsgType><Video><MediaId><![CDATA[eYJ1MbwPRJtOvIEabaxHs7TX2D-HV71s79GUxqdUkjm6Gs2Ed1KF3ulAOA9H1xG0]]></MediaId><Title><![CDATA[testCallBackReplyVideo]]></Title><Description><![CDATA[testCallBackReplyVideo]]></Description></Video></xml>";
  String afterAesEncrypt2 = "jn1L23DB+6ELqJ+6bruv23M2GmYfkv0xBh2h+XTBOKVKcgDFHle6gqcZ1cZrk3e1qjPQ1F4RsLWzQRG9udbKWesxlkupqcEcW7ZQweImX9+wLMa0GaUzpkycA8+IamDBxn5loLgZpnS7fVAbExOkK5DYHBmv5tptA9tklE/fTIILHR8HLXa5nQvFb3tYPKAlHF3rtTeayNf0QuM+UW/wM9enGIDIJHF7CLHiDNAYxr+r+OrJCmPQyTy8cVWlu9iSvOHPT/77bZqJucQHQ04sq7KZI27OcqpQNSto2OdHCoTccjggX5Z9Mma0nMJBU+jLKJ38YB1fBIz+vBzsYjrTmFQ44YfeEuZ+xRTQwr92vhA9OxchWVINGC50qE/6lmkwWTwGX9wtQpsJKhP+oS7rvTY8+VdzETdfakjkwQ5/Xka042OlUb1/slTwo4RscuQ+RdxSGvDahxAJ6+EAjLt9d8igHngxIbf6YyqqROxuxqIeIch3CssH/LqRs+iAcILvApYZckqmA7FNERspKA5f8GoJ9sv8xmGvZ9Yrf57cExWtnX8aCMMaBropU/1k+hKP5LVdzbWCG0hGwx/dQudYR/eXp3P0XxjlFiy+9DMlaFExWUZQDajPkdPrEeOwofJb";

  public HttpHeaders getHttpJsonHeader(){
    HttpHeaders headers = new HttpHeaders();
    headers.getAccept().clear();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    return headers;
  }


  public UserIdsResp getWechatPublicUserIds(String openId){
    String token = wxBasicService.getPublicToken();
    StringBuilder url = new StringBuilder(String.format(getPublicUsersUrl, token, "" ));
    if(StringUtils.isEmpty(openId)){
      url.append("&next_openid=").append(openId);
    }
    HttpHeaders headers = getHttpJsonHeader();
    HttpEntity<?> entity = new HttpEntity<>(headers);

    UserIdsResp response = restTemplate.exchange(url.toString(), HttpMethod.GET, entity, UserIdsResp.class).getBody();
    System.out.println(response.toString());

    return response;
  }


  public List<WechatUser> getWechatPublicUserInfo(List<String> openIds){
    String token = wxBasicService.getPublicToken();

    UserListReq userListReq = new UserListReq();
    userListReq.setUser_list(new ArrayList<>());
    openIds.forEach(item-> userListReq.getUser_list().add(new UserIdInfoReq(item, "zh_CN")));
    String url = String.format(getPublicUsersInfoUrl, token );
    HttpHeaders headers = getHttpJsonHeader();
    HttpEntity<UserListReq> entity = new HttpEntity<>( userListReq, headers);
    ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
//    System.out.println(((ResponseEntity<Map>) response).getBody().toString());
    List<WechatUser> data =(List) ((Map)response.getBody()).get("user_info_list");
    return data;
  }




  public List<TagInfoExt> getWechatPublicUserTag(){
    String token = wxBasicService.getPublicToken();
    System.out.println(token);
    String url = String.format(getUserTagsUrl, token );
    HttpHeaders headers = getHttpJsonHeader();
    HttpEntity<?> entity = new HttpEntity<>(headers);

    ResponseEntity<TagInfoResp> response =
        restTemplate.exchange(url, HttpMethod.POST, entity, TagInfoResp.class);
//    System.out.println(((ResponseEntity<Map>) response).getBody().toString());
    List<TagInfoExt> data =response.getBody().getTags();
    return data;
  }

  public synchronized long createWechatPublicUserTag(String tagName){
    String token = wxBasicService.getPublicToken();

    String url = String.format(createUserTagUrl, token );
    HttpHeaders headers = getHttpJsonHeader();

    Map<String, TagCreate> tagMap = new HashMap<>();
    TagCreate tag = new TagCreate();

    tag.setName(tagName);
    tagMap.put("tag", tag);
    HttpEntity<Map> entity = new HttpEntity<Map>(tagMap, headers);
    ResponseEntity<WechatTag> response = restTemplate.exchange(url, HttpMethod.POST, entity, WechatTag.class);
    System.out.println(response.getBody().toString());
    return response.getBody().getId();

  }

  public synchronized  void delWechatPublicUserTag(Long tagId) throws Exception {
    String token = wxBasicService.getPublicToken();

    String url = String.format(delUserTagUrl, token );
    HttpHeaders headers = getHttpJsonHeader();
    TagDel tag = new TagDel();
    tag.setId(tagId);
    Map<String, TagDel> tagMap = new HashMap<>();
    tagMap.put("tag", tag);
    HttpEntity<Map> entity = new HttpEntity(tagMap, headers);
    ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, GeneralResp.class);
    System.out.println(((GeneralResp)response.getBody()).toString());
    GeneralResp resp = (GeneralResp)response.getBody();
    if(resp.errcode != null && resp.errcode != 0){
      throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_WECHAT_PARAMETER, String.format("%s:%s",
          resp.errcode, resp.errmsg));
    }

  }

  public synchronized  void modWechatPublicUserTag(TagMod tagMod) throws Exception {
    String token = wxBasicService.getPublicToken();

    String url = String.format(modUserTagUrl, token );
    HttpHeaders headers = getHttpJsonHeader();

    Map<String, TagMod> tagMap = new HashMap<>();
    tagMap.put("tag", tagMod);
    HttpEntity<Map> entity = new HttpEntity(tagMap, headers);
    ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, GeneralResp.class);
    System.out.println(((GeneralResp)response.getBody()).toString());
    GeneralResp resp = (GeneralResp)response.getBody();
    if(resp.errcode != null && resp.errcode != 0){
      throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_WECHAT_PARAMETER, String.format("%s:%s",
          resp.errcode, resp.errmsg));
    }

  }

  public synchronized String  untagWechatPublicUserBatch(List<String> openIds, Long tagId){
    String token = wxBasicService.getPublicToken();

    String url = String.format(unTagUserBatchUrl, token );
    HttpHeaders headers = getHttpJsonHeader();
    TagUserBatchReq tagUserBatchReq = new TagUserBatchReq();
    tagUserBatchReq.setOpenIdList( openIds);
    tagUserBatchReq.setTagId(tagId);
    HttpEntity<TagUserBatchReq> entity = new HttpEntity<>(tagUserBatchReq, headers);
    ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
    System.out.println(((ResponseEntity<Map>) response).getBody().toString());
    Map<String, String> respMap = (Map)response.getBody();
    return String.format("%s:%s", respMap.get("errcode"), respMap.get("errmsg") );

  }

  public synchronized String tagWechatPublicUserBatch(List<String> openIds, Long tagId){
    String token = wxBasicService.getPublicToken();

    String url = String.format(tagUserBatchUrl, token );
    HttpHeaders headers = getHttpJsonHeader();
    TagUserBatchReq tagUserBatchReq = new TagUserBatchReq();
    tagUserBatchReq.setOpenIdList(openIds);
    tagUserBatchReq.setTagId(tagId);

    HttpEntity<TagUserBatchReq> entity = new HttpEntity<>(tagUserBatchReq, headers);
    ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
    System.out.println(((ResponseEntity<Map>) response).getBody().toString());
    Map<String, String> respMap = (Map)response.getBody();
    return String.format("%s:%s", respMap.get("errcode"), respMap.get("errmsg") );

  }

  public List<Long> getTagOfUser(String openId) throws Exception {
    String token = wxBasicService.getPublicToken();

    String url = String.format(getTagsOfUserUrl, token );
    HttpHeaders headers = getHttpJsonHeader();
    Map<String, String> openIdMap = new HashMap<>();
    openIdMap.put("openid", openId);
    HttpEntity<Map> entity = new HttpEntity<>(openIdMap, headers);
    ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, TagsOfUser.class);
    System.out.println(response.getBody().toString());
    TagsOfUser resp = (TagsOfUser)response.getBody();
    if(resp.errcode != null && resp.errcode != 0){
      return resp.getTags();
    }else{
      throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_WECHAT_PARAMETER, String.format("%s:%s",
          resp.getErrcode(), resp.getErrmsg()));
    }

  }

  public UserIdsResp getUsersOfTagId(Long tagId, String openId) throws Exception {
    String token = wxBasicService.getPublicToken();

    String url = String.format(getUsersOfTagIdUrl, token );
    HttpHeaders headers = getHttpJsonHeader();
    Map<String, Object> openIdMap = new HashMap<>();
    if(!StringUtils.isEmpty(openId)){
      openIdMap.put("next_openid", openId);
    }
    openIdMap.put("tagid", tagId);
    HttpEntity<Map> entity = new HttpEntity<>(openIdMap, headers);
    ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, UserIdsResp.class);
    UserIdsResp respBody = ((ResponseEntity<UserIdsResp>) response).getBody();
    System.out.println(respBody.toString());
    if(respBody.errcode != null && respBody.errcode != 0){
      throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_WECHAT_PARAMETER, String.format("%s:%s",
          respBody.getErrcode(), respBody.getErrmsg()));
    }

    return respBody;
  }

  public String recordLocation(String xmlLocation) {

    XmlMapper xmlMapper = new XmlMapper();
    try {
      WXUserGeoInfo wxUserGeoInfo
          = xmlMapper.readValue(xmlLocation, WXUserGeoInfo.class);

      WXUserGeoRecord wxUserGeoRecord = new WXUserGeoRecord();
      wxUserGeoRecord.setLocation(new GeoJsonPoint( wxUserGeoInfo.getLongitude(), wxUserGeoInfo.getLatitude()));
      wxUserGeoRecord.setOpenId(wxUserGeoInfo.getFromUserName());
      wxUserGeoRecord.setCreateTime(Instant.ofEpochSecond(wxUserGeoInfo.getCreateTime()).atZone(ZoneId.of("UTC")).toLocalDate());
      GeoJsonPoint geoJsonPoint = new GeoJsonPoint( wxUserGeoInfo.getLongitude(), wxUserGeoInfo.getLatitude());
//      Circle area = new Circle(new Point(wxUserGeoInfo.getLatitude(),  wxUserGeoInfo.getLongitude()),
//          new Distance(10, Metrics.KILOMETERS));
      NearQuery nearQuery = NearQuery.near(geoJsonPoint).maxDistance(100.00).inKilometers();
//      Aggregation aggregation = Aggregation.newAggregation(WXUserGeoInfo.class, nearQuery);
//      Point point = new Point(wxUserGeoInfo.getLongitude(), wxUserGeoInfo.getLatitude());
//      mongoTemplate.query(WXUserGeoRecord.class).as(WXUserGeoRecord.class).near(NearQuery.near(geoJsonPoint,
//          KILOMETERS).maxDistance(100.00)).matching(Query.query(Criteria.where(
//          "openId").is(wxUserGeoRecord.getOpenId()))
//      ).all().all();
      Query query = new Query();
      query.addCriteria(Criteria.where("openId").is(wxUserGeoRecord.getOpenId()));
      List<WXUserGeoRecord> wxUserGeoRecords = mongoTemplate.find(query, WXUserGeoRecord.class);
      if(CollectionUtils.isEmpty(wxUserGeoRecords)){
        mongoTemplate.save(wxUserGeoRecord);
      }else {
        double distance = GeoUtils.distanceWithoutEL(
            wxUserGeoInfo.getLatitude(), ((GeoJsonPoint)wxUserGeoRecords.get(0).getLocation()).getY(),wxUserGeoInfo.getLongitude(),
            ((GeoJsonPoint)wxUserGeoRecords.get(0).getLocation()).getX());
        if(distance < 100.00){
          log.error("needn't update geo info for this user:{}", wxUserGeoInfo.getFromUserName());
          return null;
        }else{
          mongoTemplate.save(wxUserGeoRecord);
        }
      }


    } catch (IOException e) {
      log.error(String.format("Failed to parse:%s", xmlLocation), e);
      e.printStackTrace();
    }

    return null;
  }

  @Override
  @Scheduled(cron = "${spring.task.scheduling.cronTagUserExpr}")
  public void tagUserTask(){
    Query query = new Query();
    query.addCriteria(Criteria.where("createTime").gte(AmcDateUtils.getDateMonthsDiff(3)));
    List<WXUserGeoRecord> wxUserGeoRecords = mongoTemplate.find(query, WXUserGeoRecord.class);
    if(CollectionUtils.isEmpty(wxUserGeoRecords)){
      return;
    }else{
      LatLng latLng = new LatLng();
      for(WXUserGeoRecord wxUserGeoRecord: wxUserGeoRecords){
        latLng.setLng(((GeoJsonPoint)wxUserGeoRecord.getLocation()).getCoordinates().get(0));
        latLng.setLat(((GeoJsonPoint)wxUserGeoRecord.getLocation()).getCoordinates().get(1));
        String province =  comnfuncGrpcService.getProvinceByGeopoint(latLng);
        tagUser(wxUserGeoRecord.getOpenId(), province);
      }
    }
  }

  @Override
  @Scheduled(cron = "${spring.task.scheduling.cronTagUserExpr}")
  public void syncUserInfoFromWX() {
    String token = wxBasicService.getPublicToken();

    UserListReq userListReq = new UserListReq();
    userListReq.setUser_list(new ArrayList<>());

    String url = String.format(getPublicUsersInfoUrl, token );
    HttpHeaders headers = getHttpJsonHeader();
    HttpEntity<UserListReq> entity = new HttpEntity<>( userListReq, headers);
    ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
//    System.out.println(((ResponseEntity<Map>) response).getBody().toString());

    List<WechatUser> data =(List) ((Map)response.getBody()).get("user_info_list");
    pubDataInDb(data);
    int count = 0;
    while(data.size() > 10000){

      String finalOpenId = data.get(data.size() - 1).getOpenId();
      String nextUrl = String.format(getPublicUsersInfoUrl, token, finalOpenId);
      response = restTemplate.exchange(nextUrl, HttpMethod.POST, entity, Map.class);
      data =(List) ((Map)response.getBody()).get("user_info_list");
      pubDataInDb(data);
      count ++;
      if(count > 100){
        log.error("Please check why it take more than 100 times");
        break;
      }
    }

  }

  private void pubDataInDb(List<WechatUser> data) {
    WechatUserExample wechatUserExample = new WechatUserExample();
    for(WechatUser wechatUser: data){
      wechatUserExample.createCriteria().andOpenIdEqualTo(wechatUser.getOpenId());
      List<WechatUser> wechatUsers = wechatUserMapper.selectByExample(wechatUserExample);
      if(CollectionUtils.isEmpty(wechatUsers)){
        wechatUserMapper.insertSelective(wechatUser);
      }else{
        AmcBeanUtils.copyProperties(wechatUser, wechatUsers.get(0));
        wechatUserMapper.updateByPrimaryKeySelective(wechatUsers.get(0));
      }
    }
  }

  private void tagUser(String openId, String province) {
    List<TagInfoExt> tagInfoExts = getWechatPublicUserTag();
    List<String> openIds = new ArrayList<>();
    openIds.add(openId);
    Long tagId = -1L;
    boolean hasTagAlready = false;
    for(TagInfoExt tagInfoExt: tagInfoExts){
      if(StringUtils.isEmpty(tagInfoExt.getName())){
        continue;
      }
      if(tagInfoExt.getName().equals(province)){
        hasTagAlready = true;
        tagId = tagInfoExt.getId();
      }
    }
    if(!hasTagAlready){
      log.info("need create tag:{}", province);
      try{
        List<Long> historyTagIds =  getTagOfUser(openId);


        for(Long historyTagId: historyTagIds){
          untagWechatPublicUserBatch(openIds, historyTagId);
        }
      }catch (Exception ex){
        log.error("get tag of user error:", ex);
      }

      tagId =  createWechatPublicUserTag(province);
      tagWechatPublicUserBatch(openIds, tagId);
    }else{
      tagWechatPublicUserBatch(openIds, tagId);
    }

    return;
  }


  @Data
  public class UserListReq{
    List<UserIdInfoReq> user_list;
  }

  @Data
  @AllArgsConstructor
  public class UserIdInfoReq{
    String openid;
    String lang;
  }

  @Data
  public class UserIdsInfo{
    @SerializedName( "openid")
    List<String> openId;
    @SerializedName(value = "next_openid")
    String nextOpenId;
  }
  @Data
  public class UserIdsResp extends GeneralResp{
    Long total;
    Long count;
    UserIdsInfo data;
  }
  @Data
  public class TagsOfUser extends GeneralResp {
    @SerializedName("tagid_list")
    List<Long> tags;

  }

  @Data
  public class TagInfoExt extends WechatTagVo {
    Long count;
  }

  @Data class TagInfoResp extends GeneralResp{
    List<TagInfoExt> tags;
  }





  @Data
  public class TagUserBatchReq{
    @SerializedName("openid_list")
    List<String> openIdList;
    @SerializedName("tagid")
    Long tagId;
  }

}
