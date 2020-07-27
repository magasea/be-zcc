package com.wensheng.zcc.wechat.service.impl;

import static io.grpc.netty.shaded.io.netty.handler.codec.http.HttpConstants.DEFAULT_CHARSET;

import com.ctc.wstx.util.StringUtil;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.wensheng.zcc.common.module.LatLng;
import com.wensheng.zcc.common.module.dto.AmcRegionInfo;
import com.wensheng.zcc.common.module.dto.WXUserFavor;
import com.wensheng.zcc.common.module.dto.WXUserGeoRecord;
import com.wensheng.zcc.common.module.dto.WXUserWatchObject;
import com.wensheng.zcc.common.module.dto.WechatUserInfo;
import com.wensheng.zcc.common.params.AmcDebtAssetTypeEnum;
import com.wensheng.zcc.common.params.PageInfo;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.common.utils.AmcDateUtils;
import com.wensheng.zcc.common.utils.AmcExceptions;
import com.wensheng.zcc.common.utils.AmcNumberUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.GeoUtils;
import com.wensheng.zcc.wechat.controller.helper.QueryParam;
import com.wensheng.zcc.wechat.controller.helper.WechatUserSortEnum;
import com.wensheng.zcc.wechat.dao.mysql.mapper.WechatUserMapper;
import com.wensheng.zcc.wechat.dao.mysql.mapper.WechatUserPendingPhoneMapper;
import com.wensheng.zcc.wechat.dao.mysql.mapper.WechatUserStaticMapper;
import com.wensheng.zcc.wechat.dao.mysql.mapper.ext.WechatUserExtMapper;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatUser;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatUserExample;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatUserPendingPhone;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatUserPendingPhoneExample;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatUserStatic;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatUserStaticExample;
import com.wensheng.zcc.wechat.module.dao.mysql.ext.WechatUserExtExample;
import com.wensheng.zcc.wechat.module.dto.GaodeIpCity;
import com.wensheng.zcc.wechat.module.dto.ResponseOfWxUserCummulate;
import com.wensheng.zcc.wechat.module.dto.ResponseOfWxUserSummary;
import com.wensheng.zcc.wechat.module.dto.UserCumulateItem;
import com.wensheng.zcc.wechat.module.dto.UserSummaryItem;
import com.wensheng.zcc.wechat.module.vo.AmcWechatUserInfo;
import com.wensheng.zcc.wechat.module.vo.GeneralResp;
import com.wensheng.zcc.wechat.module.vo.TagCreate;
import com.wensheng.zcc.wechat.module.vo.TagDel;
import com.wensheng.zcc.wechat.module.vo.TagMod;
import com.wensheng.zcc.wechat.module.vo.UserLngLat;
import com.wensheng.zcc.wechat.module.vo.WXUserGeoInfo;
import com.wensheng.zcc.wechat.module.vo.WXUserStatistics;
import com.wensheng.zcc.wechat.module.vo.WXUserWatchCount;
import com.wensheng.zcc.wechat.module.vo.WXUserWatchOnCheckVo;
import com.wensheng.zcc.wechat.module.vo.WXUserWatchOnObject;
import com.wensheng.zcc.wechat.module.vo.WXUserWatchOnVo;
import com.wensheng.zcc.wechat.module.vo.WechatTagVo;
import com.wensheng.zcc.wechat.module.vo.WechatUserInfoResp;
import com.wensheng.zcc.wechat.module.vo.WechatUserInfoVo;
import com.wensheng.zcc.wechat.module.vo.WechatUserVo;
import com.wensheng.zcc.wechat.service.WXBasicService;
import com.wensheng.zcc.wechat.service.WXToolService;
import com.wensheng.zcc.wechat.service.WXUserService;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

//import com.mysql.cj.x.protobuf.MysqlxDatatypes.Scalar.String;

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



  //call recomm system
  @Value("${recomm.urls.sendSubMsg}")
  String sendSubMsg;

  @Value("${recomm.urls.sendWechatMsg}")
  String sendWechatMsg;

  @Value("${weixin.open.getUserInfoUrl}")
  String getUserInfoUrl;

  @Value("${weixin.get_user_summary_url}")
  String getUserSummaryUrl;

  @Value("${weixin.get_user_cumulate_url}")
  String getUserCumulate;

  @Autowired
  WXToolService wxToolService;

  @Autowired
  MongoTemplate mongoTemplate;

  private SimpleDateFormat sdfOfDay = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");

  static final int WXQUERY_LIMIT_DAYS = 7;

  @Autowired
  ComnfuncGrpcService comnfuncGrpcService;

  @Autowired
  WechatUserMapper wechatUserMapper;

  @Autowired
  WechatUserExtMapper wechatUserExtMapper;

  @Autowired
  WechatUserStaticMapper wechatUserStaticMapper;

  @Autowired
  WechatUserPendingPhoneMapper wechatUserPendingPhoneMapper;

  private final Long MAX_TIME_LAG = 300L;

  private final Long DAY_SECONDS = 24*60*60L;

  private final int TIMES_SEND_VCODE_LIMIT = 5;


  private Gson gson = new Gson();

  private RestTemplate restTemplate = new RestTemplate();
  @PostConstruct
  void init(){
    restTemplate.getMessageConverters().removeIf(item -> item instanceof MappingJackson2HttpMessageConverter);
    GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
    gsonHttpMessageConverter.setSupportedMediaTypes(Arrays.asList(new MediaType("text","plain", DEFAULT_CHARSET ),
        new MediaType("application","json", DEFAULT_CHARSET)));
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
    headers.set("Accept", MediaType.TEXT_PLAIN_VALUE);
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
//    System.out.println(response.toString());

    return response;
  }


  public List<WechatUser> getWechatPublicUserInfo(List<String> openIds){
    String token = wxBasicService.getPublicToken();

    UserListReq userListReq = new UserListReq();
    userListReq.setUser_list(new ArrayList<>());
    List<WechatUser> data = new ArrayList<>();
    if(CollectionUtils.isEmpty(openIds)){
      return null;
    }else {

      Iterable<List<String>> subSets = Iterables.partition(openIds, 100);
      Iterator<List<String>> iterator = subSets.iterator();
      while(iterator.hasNext()){
        List<String> subSeg = iterator.next();
        userListReq.getUser_list().clear();
        subSeg.forEach(item-> userListReq.getUser_list().add(new UserIdInfoReq(item, "zh_CN")));
        String url = String.format(getPublicUsersInfoUrl, token );
        HttpHeaders headers = getHttpJsonHeader();
        HttpEntity<UserListReq> entity = new HttpEntity<>( userListReq, headers);
        ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
        if(response != null && response.getBody() != null && !(((Map)response.getBody()).containsKey("errcode"))){
          data.addAll(data.size(), ( List) ((Map)response.getBody()).get("user_info_list"));
        }
      }
    }

//    openIds.forEach(item-> userListReq.getUser_list().add(new UserIdInfoReq(item, "zh_CN")));


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

  public synchronized long createWechatPublicUserTag(String tagName) throws Exception {
    String token = wxBasicService.getPublicToken();

    String url = String.format(createUserTagUrl, token );
    HttpHeaders headers = getHttpJsonHeader();

    Map<String, TagCreate> tagMap = new HashMap<>();
    TagCreate tag = new TagCreate();

    tag.setName(tagName);
    tagMap.put("tag", tag);
    HttpEntity<Map> entity = new HttpEntity<Map>(tagMap, headers);
    ResponseEntity<TagCreateResp> responseCheck = restTemplate.exchange(url, HttpMethod.POST, entity, TagCreateResp.class);
    if(responseCheck.getBody().getErrcode() != null){

      throw new Exception(String.format("%s %s", responseCheck.getBody().getErrcode(),
          responseCheck.getBody().getErrmsg()));
    }
    return responseCheck.getBody().getTag().id;

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

  public synchronized WXUserStatistics getUserStaticFromWX(String dateStart, String dateEnd)
      throws ParseException {
    String token = wxBasicService.getPublicToken();
    Date dateStartCalender = AmcDateUtils.getDateFromStr(dateStart);
    Date dateEndCalender = AmcDateUtils.getDateFromStr(dateEnd);
    LocalDate localDateStart = AmcDateUtils.convertToLocalDateViaDate(dateStartCalender);
    LocalDate localDateEnd = AmcDateUtils.convertToLocalDateViaDate(dateEndCalender);
    if(ChronoUnit.DAYS.between(localDateStart, localDateEnd) >= WXQUERY_LIMIT_DAYS){
      return callMoreDaysStaticFromWX(dateStartCalender, dateEndCalender, token);

    }
    String userSumUrl = String.format(getUserSummaryUrl, token );
    HttpHeaders headers = getHttpJsonHeader();
    TimePeriod timePeriod = new TimePeriod();
    timePeriod.setBeginDate( dateStart);
    timePeriod.setEndDate(dateEnd);
    HttpEntity<TimePeriod> entity = new HttpEntity<>(timePeriod, headers);
    ResponseEntity<ResponseOfWxUserSummary> responseUserSummary = restTemplate.exchange(userSumUrl, HttpMethod.POST, entity, ResponseOfWxUserSummary.class);

    String userCumulateUrl = String.format(getUserCumulate, token );
    WXUserStatistics wxUserStatic = new WXUserStatistics();
    wxUserStatic.setUserSummary(responseUserSummary.getBody());
    ResponseEntity<ResponseOfWxUserCummulate> responseUserCumulate = restTemplate.exchange(userCumulateUrl, HttpMethod.POST, entity, ResponseOfWxUserCummulate.class);
    wxUserStatic.setUserCummulate(responseUserCumulate.getBody());
    return wxUserStatic;

  }

  private WXUserStatistics callMoreDaysStaticFromWX(Date dateStartCalender, Date dateEndCalender, String token) {
    WXUserStatistics wxUserStatic = new WXUserStatistics();
    wxUserStatic.setUserCummulate(new ResponseOfWxUserCummulate());
    wxUserStatic.getUserCummulate().setList(new ArrayList<>());
    wxUserStatic.setUserSummary(new ResponseOfWxUserSummary());
    wxUserStatic.getUserSummary().setList(new ArrayList<>());
    LocalDate localDateStart = AmcDateUtils.convertToLocalDateViaDate(dateStartCalender);
    LocalDate localDateEnd = AmcDateUtils.convertToLocalDateViaDate(dateEndCalender);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    while(ChronoUnit.DAYS.between(localDateStart, localDateEnd) >= (WXQUERY_LIMIT_DAYS-1) && localDateStart.isBefore(localDateEnd)){

      fillWith7DaysStatics(DateUtils.addDays(dateEndCalender,-(WXQUERY_LIMIT_DAYS-1)), dateEndCalender, token, wxUserStatic);
      dateEndCalender = DateUtils.addDays(dateEndCalender, -(WXQUERY_LIMIT_DAYS-1));
       localDateStart = AmcDateUtils.convertToLocalDateViaDate(dateStartCalender);
       localDateEnd = AmcDateUtils.convertToLocalDateViaDate(dateEndCalender);
    }
    String userSumUrl = String.format(getUserSummaryUrl, token );
    HttpHeaders headers = getHttpJsonHeader();
    TimePeriod timePeriod = new TimePeriod();
    timePeriod.setBeginDate( simpleDateFormat.format(dateStartCalender));
    timePeriod.setEndDate(simpleDateFormat.format(dateEndCalender));
    HttpEntity<TimePeriod> entity = new HttpEntity<>(timePeriod, headers);
    ResponseEntity<ResponseOfWxUserSummary> responseUserSummary = restTemplate.exchange(userSumUrl, HttpMethod.POST, entity, ResponseOfWxUserSummary.class);

    String userCumulateUrl = String.format(getUserCumulate, token );

    if(responseUserSummary.getBody().getList() != null && !CollectionUtils.isEmpty(responseUserSummary.getBody().getList())){
      wxUserStatic.getUserSummary().setList(ListUtils.union(wxUserStatic.getUserSummary().getList(),responseUserSummary.getBody().getList()));
    }
    ResponseEntity<ResponseOfWxUserCummulate> responseUserCumulate = restTemplate.exchange(userCumulateUrl, HttpMethod.POST, entity, ResponseOfWxUserCummulate.class);
    if(responseUserCumulate.getBody().getList() != null && !CollectionUtils.isEmpty(responseUserCumulate.getBody().getList())){
      wxUserStatic.getUserCummulate().setList(ListUtils.union(wxUserStatic.getUserCummulate().getList(), responseUserCumulate.getBody().getList()));
    }
    wxUserStatic.getUserCummulate().getList().sort(
        new Comparator<UserCumulateItem>() {
          @Override
          public int compare(UserCumulateItem o1, UserCumulateItem o2) {

            try {
              return AmcDateUtils.getDateFromStr(o1.getRefDate()).compareTo(AmcDateUtils.getDateFromStr(o2.getRefDate()));
            } catch (ParseException e) {
              e.printStackTrace();
              return o1.getRefDate().compareTo(o2.getRefDate());
            }
          }
        });

    wxUserStatic.getUserSummary().getList().sort(new Comparator<UserSummaryItem>() {
      @Override
      public int compare(UserSummaryItem o1, UserSummaryItem o2) {
        try {
          return AmcDateUtils.getDateFromStr(o1.getRefDate()).compareTo(AmcDateUtils.getDateFromStr(o2.getRefDate()));
        } catch (ParseException e) {
          e.printStackTrace();
          return o1.getRefDate().compareTo(o2.getRefDate());
        }
      }
    });
    return wxUserStatic;
  }

  private void fillWith7DaysStatics(Date dateStartCalender, Date dateEndCalender, String token,
      WXUserStatistics wxUserStatic) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String userSumUrl = String.format(getUserSummaryUrl, token );
    HttpHeaders headers = getHttpJsonHeader();
    TimePeriod timePeriod = new TimePeriod();
    timePeriod.setBeginDate( simpleDateFormat.format(dateStartCalender));
    timePeriod.setEndDate(simpleDateFormat.format(dateEndCalender));
    HttpEntity<TimePeriod> entity = new HttpEntity<>(timePeriod, headers);
    ResponseEntity<ResponseOfWxUserSummary> responseUserSummary = restTemplate.exchange(userSumUrl, HttpMethod.POST, entity, ResponseOfWxUserSummary.class);

    String userCumulateUrl = String.format(getUserCumulate, token );
    if(responseUserSummary.getBody().getList() != null && !CollectionUtils.isEmpty(responseUserSummary.getBody().getList())){
      wxUserStatic.getUserSummary().setList(ListUtils.union(wxUserStatic.getUserSummary().getList(), responseUserSummary.getBody().getList()));
    }
    ResponseEntity<ResponseOfWxUserCummulate> responseUserCumulate = restTemplate.exchange(userCumulateUrl, HttpMethod.POST, entity, ResponseOfWxUserCummulate.class);
    if(responseUserCumulate.getBody().getList() != null && !CollectionUtils.isEmpty(responseUserCumulate.getBody().getList())){
      wxUserStatic.getUserCummulate().setList(ListUtils.union(wxUserStatic.getUserCummulate().getList(), responseUserCumulate.getBody().getList()));
    }
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
  public void tagUserTask() throws Exception {
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

    UserIdsResp userIdsResp = getWechatPublicUserIds(null);
    UserListReq userListReq = new UserListReq();
    userListReq.setUser_list(new ArrayList<>());
    if(null != userIdsResp.data.getOpenId() && !CollectionUtils.isEmpty(userIdsResp.getData().getOpenId())){
      for(String openId: userIdsResp.getData().getOpenId()){
        userListReq.getUser_list().add(new UserIdInfoReq(openId,null));

      }
    }else{
      return;
    }




    String url = String.format(getPublicUsersInfoUrl, token );
    HttpHeaders headers = getHttpJsonHeader();
    HttpEntity<UserListReq> entity = new HttpEntity<>( userListReq, headers);
    ResponseEntity<WechatUserInfoResp> response = restTemplate.exchange(url, HttpMethod.POST, entity,
        WechatUserInfoResp.class);
//    System.out.println(((ResponseEntity<Map>) response).getBody().toString());


    List<WechatUserInfoVo> wechatUserInfoVos =  response.getBody().getUserInfoVoList();
    WechatUserExample wechatUserExample = new WechatUserExample();
    WechatUserInfo wechatUserInfo = null;
    WechatUser wechatUser = null;
    for(WechatUserInfoVo userInfoVo: wechatUserInfoVos){
      wechatUser = new WechatUser();
      wechatUserInfo = new WechatUserInfo();
      AmcBeanUtils.copyProperties(userInfoVo, wechatUser);
      AmcBeanUtils.copyProperties(userInfoVo, wechatUserInfo);
      wechatUser.setSubscribeTime(AmcDateUtils.toUTCDate(userInfoVo.getSubscribeTime()));
      wechatUserExample.clear();
      if(!StringUtils.isEmpty(wechatUser.getUnionId())){
        wechatUserExample.createCriteria().andUnionIdEqualTo(wechatUser.getUnionId());
      }else{
        wechatUserExample.createCriteria().andOpenIdEqualTo(wechatUser.getOpenId());
      }
      List<WechatUser> wechatUsers = wechatUserMapper.selectByExample(wechatUserExample);
      if(CollectionUtils.isEmpty(wechatUsers)){
        //insert
        wechatUserMapper.insertSelective(wechatUser);
        refreshUserTags(wechatUser.getId(), userInfoVo.getTagIdList());
      }else{
        //update
        AmcBeanUtils.copyProperties(wechatUser, wechatUsers.get(0));
        wechatUserMapper.updateByPrimaryKeySelective(wechatUsers.get(0));
        refreshUserTags(wechatUser.getId(), userInfoVo.getTagIdList());
      }
      
      if(CollectionUtils.isEmpty(userInfoVo.getTagIdList())){
        
      }
      wxToolService.notifyWechatUserLogin(wechatUserInfo);
      wechatUserInfo = null;
      wechatUser = null;
    }

//    pubDataInDb(data);
//    int count = 0;
//    while(data.size() > 10000){
//
//      String finalOpenId = data.get(data.size() - 1).getOpenId();
//      String nextUrl = String.format(getPublicUsersInfoUrl, token, finalOpenId);
//      response = restTemplate.exchange(nextUrl, HttpMethod.POST, entity, Map.class);
//      data =(List) ((Map)response.getBody()).get("user_info_list");
//      pubDataInDb(data);
//      count ++;
//      if(count > 100){
//        log.error("Please check why it take more than 100 times");
//        break;
//      }
//    }

  }



  @Override
  public boolean sendPhoneVcode(String openId, String phone) throws Exception {
//throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_JSON_CONTENT_ERROR,String.format("没有找到该用户", openId));
    WechatUserExample wechatUserExample = new WechatUserExample();
    wechatUserExample.createCriteria().andOpenIdEqualTo(openId);
    WechatUser wechatUser = null;
    List<WechatUser> wechatUsers = wechatUserMapper.selectByExample(wechatUserExample);
    if(CollectionUtils.isEmpty(wechatUsers)){
      throw ExceptionUtils.getAmcException(AmcExceptions.WECHAT_USER_ERROR,String.format("没有找到该用户", openId));
    }else{
      wechatUser = wechatUsers.get(0);
      if(!StringUtils.isEmpty(wechatUsers.get(0).getMobile())&&wechatUsers.get(0).getMobile().equals(phone)&&wechatUsers.get(0).getVerifyCode().equals("-1")){
        throw ExceptionUtils.getAmcException(AmcExceptions.DUPLICATE_ITEM_ERROR, String.format("该手机号码:[%s]已经绑定，无需再次绑定",phone));
      }
    }


    boolean isVcodeExpired = false;
    if(!StringUtils.isEmpty(wechatUser.getVerifyCode()) && !wechatUser.getVerifyCode().equals("-1")){
      Date currDate = AmcDateUtils.getCurrentDate();
      if(currDate.toInstant().getEpochSecond() < (wechatUser.getVcodeTime().toInstant().getEpochSecond() + 60)){
        throw ExceptionUtils.getAmcException(AmcExceptions.WECHAT_USER_ERROR, "请过1分钟后再次申请");
      }else if(currDate.toInstant().getEpochSecond() > (wechatUser.getVcodeTime().toInstant().getEpochSecond() + MAX_TIME_LAG)){
        isVcodeExpired = true;
      }
    }else if(StringUtils.isEmpty(wechatUser.getVerifyCode()) || wechatUser.getVerifyCode().equals("-1")){
      isVcodeExpired = true;
    }
    wechatUserExample.clear();
    wechatUserExample.createCriteria().andMobileEqualTo(phone);
    wechatUsers = wechatUserMapper.selectByExample(wechatUserExample);
    if(!CollectionUtils.isEmpty(wechatUsers)){
      for(WechatUser wechatUserItem: wechatUsers){
        if(!StringUtils.isEmpty(wechatUserItem.getMobile()) && wechatUserItem.getVerifyCode().equals("-1")){
          //already bind phone
          throw ExceptionUtils.getAmcException(AmcExceptions.DUPLICATE_ITEM_ERROR, String.format("该手机号码:[%s]已经被别人绑定,请换一个手机号",phone));
        }
      }
    }

    String vcode = null;
    if(isVcodeExpired){
      vcode = AmcNumberUtils.getValidationCode();
      String result =  comnfuncGrpcService.sendVCode(phone, vcode);
//    wechatUsers.get(0).setMobile(phone);
      wechatUser.setVerifyCode(vcode);
      wechatUser.setVcodeTime(AmcDateUtils.getCurrentDate());
      wechatUserMapper.updateByPrimaryKeySelective(wechatUser);
    }


    return true;
  }


  @Override
  public boolean sendChangePhoneVerifyCode(String openId, String phone) throws Exception {


    WechatUserExample wechatUserExample = new WechatUserExample();
    wechatUserExample.createCriteria().andOpenIdEqualTo(openId);
    WechatUser wechatUser = null;
    List<WechatUser> wechatUsers = wechatUserMapper.selectByExample(wechatUserExample);
    if(CollectionUtils.isEmpty(wechatUsers)){
      throw ExceptionUtils.getAmcException(AmcExceptions.WECHAT_USER_ERROR,String.format("没有找到该用户", openId));
    }else{
      wechatUser = wechatUsers.get(0);
//      if(!StringUtils.isEmpty(wechatUsers.get(0).getMobile())&&wechatUsers.get(0).getMobile().equals(phone)&&wechatUsers.get(0).getVerifyCode().equals("-1")){
//        throw ExceptionUtils.getAmcException(AmcExceptions.DUPLICATE_ITEM_ERROR, String.format("该手机号码:[%s]已经绑定，无需再次绑定",phone));
//      }
    }
    //检查用户是否已经绑定手机号
    if(!StringUtils.isEmpty(wechatUser.getMobile()) && !wechatUser.getMobile().equals("-1")
        && !StringUtils.isEmpty(wechatUser.getVerifyCode()) && wechatUser.getVerifyCode().equals("-1")){
      //用户已经绑定手机号
      if(phone.equals(wechatUser.getMobile())){
        throw ExceptionUtils.getAmcException(AmcExceptions.DUPLICATE_ITEM_ERROR, String.format("该手机号码:[%s]已经绑定，无需再次绑定",phone));
      }else{
        //用户想绑新手机号
        checkPhoneIsUsed(phone);
        requestVCode4NewPhone(wechatUser, phone);

      }
    }else{
     checkPhoneIsUsed(phone);
      return requestVCode4NewPhone(wechatUser, phone);
    }
    return true;

  }
  private void checkPhoneIsUsed(String phone) throws Exception {
    WechatUserExample wechatUserExample = new WechatUserExample();
    wechatUserExample.createCriteria().andMobileEqualTo(phone);
    List<WechatUser> wechatUsersCheckMobile = wechatUserMapper.selectByExample(wechatUserExample);
    if(!CollectionUtils.isEmpty(wechatUsersCheckMobile)){
      for(WechatUser wechatUserItem: wechatUsersCheckMobile){
        if(!StringUtils.isEmpty(wechatUserItem.getMobile()) && wechatUserItem.getVerifyCode().equals("-1")){
          //already bind phone
          throw ExceptionUtils.getAmcException(AmcExceptions.DUPLICATE_ITEM_ERROR, String.format("该手机号码:[%s]已经被别人绑定,请换一个手机号",phone));
        }
      }
    }
  }

  private boolean requestVcode4Phone(WechatUser wechatUser, String phone) throws Exception {
//    WechatUserExample wechatUserExample = new WechatUserExample();
//    wechatUserExample.createCriteria().andUserIdEqualTo(wechatUser.getId());
//    List<WechatUserPendingPhone> wechatUserPendingPhones = wechatUserPendingPhoneMapper.selectByExample(wechatUserPendingPhoneExample);
//    WechatUserPendingPhone wechatUserPendingPhone = null;
    boolean useInDBPhone = false;
    boolean isVcodeExpired = false;
    String vcode = null;
    if(StringUtils.isEmpty(wechatUser.getMobile()) || wechatUser.getMobile().equals("-1")){
      vcode = AmcNumberUtils.getValidationCode();
      wechatUser.setMobile(phone);
      wechatUser.setVerifyCode(vcode);
      wechatUser.setVcodeTime(AmcDateUtils.getCurrentDate());
      sendVcode(phone, vcode);
      wechatUser.setSentTimes(1);
      wechatUserMapper.updateByPrimaryKeySelective(wechatUser);
      return true;
    }else{
      //todo check logic 7/10-2020
      if(wechatUser.getMobile().equals(phone)){
        //检查是否重复发送

      }else{
        //用户换手机号申请验证码
        wechatUser.setMobile(phone);
      }
      if(!StringUtils.isEmpty(wechatUser.getVerifyCode()) && !wechatUser.getVerifyCode().equals("-1")){
        Date currDate = AmcDateUtils.getCurrentDate();
        if( currDate.toInstant().getEpochSecond() -
            wechatUser.getVcodeTime().toInstant().getEpochSecond() > DAY_SECONDS ){
          wechatUser.setSentTimes(1);
        }
        if(wechatUser.getSentTimes() >= TIMES_SEND_VCODE_LIMIT){
          throw ExceptionUtils.getAmcException(AmcExceptions.WECHAT_USER_ERROR, String.format("一天内发送验证码次数超过%s", TIMES_SEND_VCODE_LIMIT));
        }


        if(currDate.toInstant().getEpochSecond() < (wechatUser.getVcodeTime().toInstant().getEpochSecond() + 60)){
          throw ExceptionUtils.getAmcException(AmcExceptions.WECHAT_USER_ERROR, "请过1分钟后再次申请");
        }else if(currDate.toInstant().getEpochSecond() > (wechatUser.getVcodeTime().toInstant().getEpochSecond() + MAX_TIME_LAG)){
          isVcodeExpired = true;
        }else {
          isVcodeExpired = false;
        }

        if(isVcodeExpired){
          vcode = AmcNumberUtils.getValidationCode();
        }else{
          vcode = wechatUser.getVerifyCode();
        }
        wechatUser.setVerifyCode(vcode);

        wechatUser.setVcodeTime(AmcDateUtils.getCurrentDate());
        sendVcode(phone, vcode);

        if(wechatUser.getSentTimes() <= -1){
          wechatUser.setSentTimes(1);
        }else{
          wechatUser.setSentTimes(wechatUser.getSentTimes()+1);
        }
        wechatUserMapper.updateByPrimaryKeySelective(wechatUser);
      }
    }


    return true;
  }

  private boolean requestVCode4NewPhone(WechatUser wechatUser, String phone) throws Exception {
    String vcode = null;


    WechatUserPendingPhoneExample wechatUserPendingPhoneExample = new WechatUserPendingPhoneExample();
    wechatUserPendingPhoneExample.createCriteria().andUserIdEqualTo(wechatUser.getId());
    List<WechatUserPendingPhone> wechatUserPendingPhones = wechatUserPendingPhoneMapper.selectByExample(wechatUserPendingPhoneExample);
    WechatUserPendingPhone wechatUserPendingPhone = null;
    boolean useInDBPhone = false;
    boolean isVcodeExpired = false;
    if(CollectionUtils.isEmpty(wechatUserPendingPhones)){
      vcode = AmcNumberUtils.getValidationCode();
      wechatUserPendingPhone = new WechatUserPendingPhone();
      wechatUserPendingPhone.setPendingPhone(phone);
      wechatUserPendingPhone.setUserId(wechatUser.getId());
      wechatUserPendingPhone.setVerifyCode(vcode);
      wechatUserPendingPhone.setVcodeTime(AmcDateUtils.getCurrentDate());
      sendVcode(phone, vcode);
      wechatUserPendingPhone.setSentTime(1);
      wechatUserPendingPhoneMapper.insertSelective(wechatUserPendingPhone);
      return true;
    }else{
      wechatUserPendingPhone = wechatUserPendingPhones.get(0);
      useInDBPhone = true;
      if(wechatUserPendingPhone.getPendingPhone().equals(phone)){
        //检查是否重复发送
        if(!StringUtils.isEmpty(wechatUserPendingPhone.getVerifyCode()) && !wechatUserPendingPhone.getVerifyCode().equals("-1")){
          Date currDate = AmcDateUtils.getCurrentDate();
          if( currDate.toInstant().getEpochSecond() -
              wechatUserPendingPhone.getVcodeTime().toInstant().getEpochSecond() > DAY_SECONDS ){
            //count reset
            wechatUserPendingPhone.setSentTime(0);
          }
          if(wechatUserPendingPhone.getSentTime() >= TIMES_SEND_VCODE_LIMIT){
            throw ExceptionUtils.getAmcException(AmcExceptions.WECHAT_USER_ERROR, String.format("您一天内获取验证码已超过 %s 次，请明天再尝试", TIMES_SEND_VCODE_LIMIT));
          }


          if(currDate.toInstant().getEpochSecond() < (wechatUserPendingPhone.getVcodeTime().toInstant().getEpochSecond() + 60)){
            throw ExceptionUtils.getAmcException(AmcExceptions.WECHAT_USER_ERROR, "1分钟之内只能获取一次验证码");
          }else if(currDate.toInstant().getEpochSecond() > (wechatUserPendingPhone.getVcodeTime().toInstant().getEpochSecond() + MAX_TIME_LAG)){
            isVcodeExpired = true;
          }else {
            isVcodeExpired = false;
          }

          if(isVcodeExpired){
            vcode = AmcNumberUtils.getValidationCode();
          }else{
            vcode = wechatUserPendingPhone.getVerifyCode();
          }
          wechatUserPendingPhone.setVerifyCode(vcode);
          wechatUserPendingPhone.setUserId(wechatUser.getId());
          wechatUserPendingPhone.setVcodeTime(AmcDateUtils.getCurrentDate());
          sendVcode(phone, vcode);
          if(wechatUserPendingPhone.getSentTime() <= -1){
            wechatUserPendingPhone.setSentTime(1);
          }else{
            wechatUserPendingPhone.setSentTime(wechatUserPendingPhone.getSentTime()+1);
          }
          wechatUserPendingPhoneMapper.updateByPrimaryKeySelective(wechatUserPendingPhone);
        }
      }else{
        //用户换手机号
        wechatUserPendingPhone.setPendingPhone(phone);
        vcode = AmcNumberUtils.getValidationCode();
        wechatUserPendingPhone.setVerifyCode(vcode);
        wechatUserPendingPhone.setUserId(wechatUser.getId());
        wechatUserPendingPhone.setVcodeTime(AmcDateUtils.getCurrentDate());
        sendVcode(phone, vcode);
        wechatUserPendingPhone.setSentTime(1);
        wechatUserPendingPhoneMapper.updateByPrimaryKeySelective(wechatUserPendingPhone);
      }
    }


    return true;
  }

  private void sendVcode(String phone, String vcode) throws Exception {
    String result = null;
    try{
       result =  comnfuncGrpcService.sendVCode(phone, vcode);
    }catch (Exception ex){
      log.error("Failed call send vcode ");
      throw ExceptionUtils.getAmcException(AmcExceptions.WECHAT_USER_ERROR, String.format("发送验证码失败，请稍后再申请"));
    }

    if(!result.equals(vcode)){
      log.error("Failed to send vcode out");
      throw ExceptionUtils.getAmcException(AmcExceptions.WECHAT_USER_ERROR, String.format("发送验证码失败，请稍后再申请"));

//    wechatUsers.get(0).setMobile(phone)
    }
  }

//  @Override
//  public boolean sendPhoneVcodeTest(String openId, String phone, String code) throws Exception {
//
//    WechatUserExample wechatUserExample = new WechatUserExample();
//    wechatUserExample.createCriteria().andOpenIdEqualTo(openId);
//    WechatUser wechatUser = null;
//    List<WechatUser> wechatUsers = wechatUserMapper.selectByExample(wechatUserExample);
//    if(CollectionUtils.isEmpty(wechatUsers)){
//      wechatUser = new WechatUser();
//      wechatUser.setMobile(phone);
//      wechatUser.setOpenId(openId);
//      wechatUserMapper.insertSelective(wechatUser);
//    }else{
//      wechatUser = wechatUsers.get(0);
//    }
//
//    if(!StringUtils.isEmpty(wechatUser.getVerifyCode()) && !wechatUser.getVerifyCode().equals("-1")){
//      Date currDate = AmcDateUtils.getCurrentDate();
//      if(currDate.toInstant().getEpochSecond() < (wechatUser.getVcodeTime().toInstant().getEpochSecond() + MAX_TIME_LAG/10)){
//        throw ExceptionUtils.getAmcException(AmcExceptions.WECHAT_USER_ERROR, "请过2分钟后再次申请");
//      }
//    }
//    String result =  comnfuncGrpcService.sendVCode(phone, code);
////    wechatUsers.get(0).setMobile(phone);
//    wechatUser.setVerifyCode(code);
//    wechatUser.setVcodeTime(AmcDateUtils.getCurrentDate());
//    wechatUserMapper.updateByPrimaryKeySelective(wechatUser);
//
//    return true;
//  }

//  @Override
//  public boolean bindPhone(String openId, String phone, String code) throws Exception {
//    WechatUserExample wechatUserExample = new WechatUserExample();
//    wechatUserExample.createCriteria().andOpenIdEqualTo(openId);
//    List<WechatUser> wechatUsers = wechatUserMapper.selectByExample(wechatUserExample);
//    if(CollectionUtils.isEmpty(wechatUsers)){
//      throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_JSON_CONTENT_ERROR,String.format("没有找到该用户", openId));
//    }
//
//    Date currDate = AmcDateUtils.getCurrentDate();
//    Long timeLag = currDate.toInstant().getEpochSecond() - wechatUsers.get(0).getVcodeTime().toInstant().getEpochSecond();
//    if(timeLag > MAX_TIME_LAG*5){
//      throw ExceptionUtils.getAmcException(AmcExceptions.WECHAT_USER_ERROR,"验证码超时, 请再次申请");
//    }
//    if(code.equals(wechatUsers.get(0).getVerifyCode())){
//      wechatUsers.get(0).setMobile(phone);
//      wechatUsers.get(0).setVerifyCode("-1");
//      wechatUserMapper.updateByPrimaryKeySelective(wechatUsers.get(0));
//    }else{
//      throw ExceptionUtils.getAmcException(AmcExceptions.WECHAT_USER_ERROR,String.format("验证码不匹配", openId));
//    }
//
//    return true;
//  }
  @Override
  @Transactional
  public boolean bindNewPhone(String openId, String phone, String code) throws Exception {
    WechatUserExample wechatUserExample = new WechatUserExample();
    wechatUserExample.createCriteria().andOpenIdEqualTo(openId);
    List<WechatUser> wechatUsers = wechatUserMapper.selectByExample(wechatUserExample);
    if(CollectionUtils.isEmpty(wechatUsers)){
      throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_JSON_CONTENT_ERROR,String.format("没有找到该用户", openId));
    }
    WechatUser wechatUser = wechatUsers.get(0);
    //if the user bind init phone
    Date currDate = AmcDateUtils.getCurrentDate();
    if(wechatUser.getMobile().equals(phone) && wechatUser.getVerifyCode().equals("-1")){
      throw ExceptionUtils.getAmcException(AmcExceptions.WECHAT_USER_ERROR,String.format("您的手机号码::%s已经绑定", phone));
    }
//    if(wechatUser.getMobile().equals(phone)){
//      Long timeLag = currDate.toInstant().getEpochSecond() - wechatUsers.get(0).getVcodeTime().toInstant().getEpochSecond();
//      if(timeLag > MAX_TIME_LAG*5){
//        throw ExceptionUtils.getAmcException(AmcExceptions.WECHAT_USER_ERROR,"验证码超时, 请再次申请");
//      }
//      if(code.equals(wechatUsers.get(0).getVerifyCode())){
//        wechatUsers.get(0).setMobile(phone);
//        wechatUsers.get(0).setVerifyCode("-1");
//        wechatUserMapper.updateByPrimaryKeySelective(wechatUsers.get(0));
//      }else{
//        throw ExceptionUtils.getAmcException(AmcExceptions.WECHAT_USER_ERROR,String.format("验证码不匹配", openId));
//      }

//    }else{
      WechatUserPendingPhoneExample wechatUserPendingPhoneExample = new WechatUserPendingPhoneExample();
      wechatUserPendingPhoneExample.createCriteria().andUserIdEqualTo(wechatUser.getId());
      List<WechatUserPendingPhone> wechatUserPendingPhones = wechatUserPendingPhoneMapper.selectByExample(wechatUserPendingPhoneExample);
      if(CollectionUtils.isEmpty(wechatUserPendingPhones)){
        throw ExceptionUtils.getAmcException(AmcExceptions.WECHAT_USER_ERROR, String.format("您要绑定的手机号:%s 在系统中不存在,请重新申请验证码", phone));
      }
      if(!wechatUserPendingPhones.get(0).getPendingPhone().equals(phone)){
        throw ExceptionUtils.getAmcException(AmcExceptions.WECHAT_USER_ERROR, String.format("您的手机号码与验证码不匹配，请检查", phone, wechatUserPendingPhones.get(0).getPendingPhone()));
      }
      Long timeLag = currDate.toInstant().getEpochSecond() - wechatUserPendingPhones.get(0).getVcodeTime().toInstant().getEpochSecond();
      if(timeLag > MAX_TIME_LAG*5){
        throw ExceptionUtils.getAmcException(AmcExceptions.WECHAT_USER_ERROR,"验证码超时, 请再次申请");
      }
      if(code.equals(wechatUserPendingPhones.get(0).getVerifyCode())){
        wechatUser.setMobile(phone);
        wechatUser.setVerifyCode("-1");
        wechatUserMapper.updateByPrimaryKeySelective(wechatUsers.get(0));
      }else{
        throw ExceptionUtils.getAmcException(AmcExceptions.WECHAT_USER_ERROR,String.format("您的手机号码与验证码不匹配，请检查", openId));
      }
      wechatUserPendingPhoneMapper.deleteByPrimaryKey(wechatUserPendingPhones.get(0).getId());

//    }

    return true;
  }

    @Override
  public String userSubscribe(String xmlMsg) {
    HttpEntity<String> entity = new HttpEntity<>(xmlMsg, getHttpJsonHeader());
    ResponseEntity<String> result =  restTemplate.exchange(sendSubMsg, HttpMethod.POST, entity, String.class);
    return result.getBody();
  }

  @Override
  public String userMsg(String xmlMsg) {
    HttpEntity<String> entity = new HttpEntity<>(xmlMsg, getHttpJsonHeader());
    ResponseEntity<String> result =  restTemplate.exchange(sendWechatMsg, HttpMethod.POST, entity, String.class);
    return result.getBody();

  }

  @Override
  public boolean watchOnObject(String openId, String phone, Long objectId, Integer objectType)
      throws Exception {
    if(StringUtils.isEmpty(openId) || openId.equals("-1")){
      throw ExceptionUtils.getAmcException(AmcExceptions.MISSING_MUST_PARAM, openId);
    }
    Query query = new Query();
    query.addCriteria(Criteria.where("openId").is(openId).and("objectId").is(objectId).and("type").is(objectType));
    List<WXUserWatchObject> wxUserWatchObjects = mongoTemplate.find(query, WXUserWatchObject.class);
    if(CollectionUtils.isEmpty(wxUserWatchObjects)){
      WXUserWatchObject wxUserWatchObject = new WXUserWatchObject();
      wxUserWatchObject.setObjectId(objectId);
      wxUserWatchObject.setOpenId(openId);
      wxUserWatchObject.setPhone(phone);
      wxUserWatchObject.setType(objectType);
      wxUserWatchObject.setCreateTime(AmcDateUtils.getCurrentDate());
      wxUserWatchObject.setUpdateTime(AmcDateUtils.getCurrentDate());
      mongoTemplate.save(wxUserWatchObject);
    }else{
      wxUserWatchObjects.get(0).setUpdateTime(AmcDateUtils.getCurrentDate());
      mongoTemplate.save(wxUserWatchObjects.get(0));
    }
    //update watch count
    query = new Query();
    query.addCriteria(Criteria.where("openId").is(openId));
    wxUserWatchObjects = mongoTemplate.find(query, WXUserWatchObject.class);


    WechatUserExample wechatUserExample = new WechatUserExample();
    wechatUserExample.createCriteria().andOpenIdEqualTo(openId);
    List<WechatUser> wechatUsers = wechatUserMapper.selectByExample(wechatUserExample);
    if(CollectionUtils.isEmpty(wechatUsers)){
      log.error("This openId:{} is not available in wechatUser table", openId);
      return true;
    }
    WechatUserStatic wechatUserStatic = new WechatUserStatic();
    wechatUserStatic.setWatchCount(Long.valueOf(wxUserWatchObjects.size()));
    WechatUserStaticExample wechatUserStaticExample= new WechatUserStaticExample();
    wechatUserStaticExample.createCriteria().andWechatUserIdEqualTo(wechatUsers.get(0).getId());
    wechatUserStaticMapper.updateByExampleSelective(wechatUserStatic, wechatUserStaticExample);

    return true;
  }

  @Override
  public List<WXUserWatchOnObject> getUserWatchedOn(String openId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("openId").is(openId));
    List<WXUserWatchObject> wxUserWatchObjects = mongoTemplate.find(query, WXUserWatchObject.class);

    List<WXUserWatchOnObject> wxUserWatchOnObjects = new ArrayList<>();
    for(WXUserWatchObject wxUserWatchObject: wxUserWatchObjects){
      WXUserWatchOnObject wxUserWatchOnObject = new WXUserWatchOnObject();
      AmcBeanUtils.copyProperties(wxUserWatchObject, wxUserWatchOnObject);
      wxUserWatchOnObjects.add(wxUserWatchOnObject);
    }
    return wxUserWatchOnObjects;
  }

  @Override
  public List<WXUserWatchObject> getObjectWatchedUsers(Long objectId, Integer type) {
    Query query = new Query();
    query.addCriteria(Criteria.where("objectId").is(objectId).and("type").is(type));
    List<WXUserWatchObject> wxUserWatchObjects = mongoTemplate.find(query, WXUserWatchObject.class);
    return wxUserWatchObjects;
  }

  @Override
  public boolean saveUserFavor(WXUserFavor wxUserFavor) {
    if(StringUtils.isEmpty(wxUserFavor.getOpenId())){
      log.error("no openId there , no need to save");
      return false;
    }else{
      WechatUserExample wechatUserExample = new WechatUserExample();
      wechatUserExample.createCriteria().andOpenIdEqualTo(wxUserFavor.getOpenId());
      List<WechatUser> wechatUsers = wechatUserMapper.selectByExample(wechatUserExample);
      if(CollectionUtils.isEmpty(wechatUsers)){
        WechatUser wechatUser = new WechatUser();
        wechatUser.setOpenId(wxUserFavor.getOpenId());
        wechatUserMapper.insertSelective(wechatUser);
      }
    }
    Query query = new Query();
    query.addCriteria(Criteria.where("openId").is(wxUserFavor.getOpenId()));
    List<WXUserFavor> wxUserFavors = mongoTemplate.find(query, WXUserFavor.class);
    if(CollectionUtils.isEmpty(wxUserFavors)){
      //make new favors
      if(!StringUtils.isEmpty(wxUserFavor.getUserPrefCityName())){
        GeoJsonPoint geoInfo = comnfuncGrpcService.getGeoInfo(wxUserFavor.getUserPrefCityName());
        if(geoInfo != null && !CollectionUtils.isEmpty(geoInfo.getCoordinates())){
          log.info("need update user pref city coordinates");
          wxUserFavor.setUserPrefCityLng(geoInfo.getCoordinates().get(0));
          wxUserFavor.setUserPrefCityLat(geoInfo.getCoordinates().get(1));
        }
      }
      wxUserFavor.setCreateTime(AmcDateUtils.getCurrentDate());
      mongoTemplate.save(wxUserFavor);
    }else{
      wxUserFavor.setCreateTime(null);
      if(!StringUtils.isEmpty(wxUserFavor.getUserPrefCityName()) &&
          !wxUserFavor.getUserPrefCityName().equals(wxUserFavors.get(0).getUserPrefCityName()) ){
        GeoJsonPoint geoInfo = comnfuncGrpcService.getGeoInfo(wxUserFavor.getUserPrefCityName());
        if(geoInfo != null && !CollectionUtils.isEmpty(geoInfo.getCoordinates())){
          log.info("need update user pref city coordinates");
          wxUserFavor.setUserPrefCityLng(geoInfo.getCoordinates().get(0));
          wxUserFavor.setUserPrefCityLat(geoInfo.getCoordinates().get(1));
        }
      }
      AmcBeanUtils.copyProperties(wxUserFavor, wxUserFavors.get(0));
      if(wxUserFavors.get(0).getCreateTime() == null){
        log.info("need to set create time ");
        wxUserFavors.get(0).setCreateTime(AmcDateUtils.getCurrentDate());
      }
      wxUserFavors.get(0).setUpdateTime(AmcDateUtils.getCurrentDate());
      mongoTemplate.save(wxUserFavors.get(0));
    }
    return true;
  }

  private void refreshUserTags(Long id, List<Integer> tagIdList) {
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

  private void tagUser(String openId, String province) throws Exception {
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

  public List<WXUserWatchObject> getUserWatched(String openId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("openId").is(openId));
    List<WXUserWatchObject> wxUserWatchObjects = mongoTemplate.find(query, WXUserWatchObject.class);
    return wxUserWatchObjects;
  }

  @Override
  public WXUserFavor getUserFavor(String openId, String ipadd) {
    Query query = new Query();
    query.addCriteria(Criteria.where("openId").is(openId));
    WXUserFavor wxUserFavor = null;
    List<WXUserFavor> wxUserFavors = mongoTemplate.find(query, WXUserFavor.class);
    boolean needMakeNewFavor = false;
    if(CollectionUtils.isEmpty(wxUserFavors)){
      if(StringUtils.isEmpty(ipadd)){
       return null;
      }else{
        needMakeNewFavor = true;
      }


    }
    if(needMakeNewFavor){
      wxUserFavor = new WXUserFavor();
      wxUserFavor.setOpenId(openId);
    }else{
      wxUserFavor = wxUserFavors.get(0);
    }
    log.info("ipadd:{} openId:{}", ipadd, openId);
    if(!StringUtils.isEmpty(ipadd) && !ipadd.equals(wxUserFavor.getLastIp())){
      //call comonfunc to get ipCity
      GaodeIpCity cityByIp = comnfuncGrpcService.getCityByIp(ipadd);
      log.info(gson.toJson(cityByIp));
      if(cityByIp != null){
        wxUserFavor.setLastIp(ipadd);
        if(!StringUtils.isEmpty(wxUserFavor.getLastIpCity())){
          wxUserFavor.setHistIpCity(wxUserFavor.getLastIpCity());
        }

        wxUserFavor.setLastIpCity(cityByIp.getCity());
        wxUserFavor.setLastIpCityCode(cityByIp.getAdcode());
        wxUserFavor.setLastIpProv(cityByIp.getProvince());
        wxUserFavor.setLastIpLat(cityByIp.getLat());
        wxUserFavor.setLastIpLng(cityByIp.getLng());
      }
      mongoTemplate.save(wxUserFavor);

    }
    if(!StringUtils.isEmpty(wxUserFavor.getUserPrefCityName()) && wxUserFavor.getUserPrefCityLat() == null){
      GeoJsonPoint geoInfo = comnfuncGrpcService.getGeoInfo(wxUserFavor.getUserPrefCityName());
      if(geoInfo != null && !CollectionUtils.isEmpty(geoInfo.getCoordinates())){
        wxUserFavor.setUserPrefCityLng(geoInfo.getCoordinates().get(0));
        wxUserFavor.setUserPrefCityLat(geoInfo.getCoordinates().get(1));
        mongoTemplate.save(wxUserFavor);

      }
    }
    return wxUserFavor;
  }

  @Override
  public List<WXUserWatchOnObject> checkUserFavor(WXUserWatchOnCheckVo wxUserWatchOnCheckVo) {
    Query query = new Query();
    query.addCriteria(Criteria.where("openId").is(wxUserWatchOnCheckVo.getOpenId()));
    Set<String> wxUserWatchOnObjectSet =  new HashSet<>();
    for(WXUserWatchOnObject wxUserWatchOnObject: wxUserWatchOnCheckVo.getWxUserWatchOnObjects()){
      wxUserWatchOnObjectSet.add(String.format("%s-%s", wxUserWatchOnObject.getType(), wxUserWatchOnObject.getObjectId()));
    }
    List<WXUserWatchOnObject> wxUserWatchOnObjectsResult = new ArrayList<>();
    List<WXUserWatchObject> wxUserWatchObjects = mongoTemplate.find(query, WXUserWatchObject.class);
    if(CollectionUtils.isEmpty(wxUserWatchObjects)){
      return null;
    }else{
      for(WXUserWatchObject wxUserWatchObject: wxUserWatchObjects){
        if(wxUserWatchOnObjectSet.contains(String.format("%s-%s", wxUserWatchObject.getType(), wxUserWatchObject.getObjectId()))){
          WXUserWatchOnObject wxUserWatchOnObject = new WXUserWatchOnObject();
          AmcBeanUtils.copyProperties(wxUserWatchObject, wxUserWatchOnObject);
          wxUserWatchOnObjectsResult.add(wxUserWatchOnObject);
        }
      }
      return wxUserWatchOnObjectsResult;
    }

  }

  @Override
  public AmcRegionInfo getUserLocation(UserLngLat userLngLat) {
    log.info(gson.toJson(userLngLat));

    AmcRegionInfo regionInfo = null;
    if(userLngLat.getLng() != null && userLngLat.getLat() != null){
      regionInfo = comnfuncGrpcService.getRegionInfo(userLngLat.getLng(), userLngLat.getLat());
    }
    GeoJsonPoint geoJsonPoint = null;
    if(userLngLat.getLng() == null && !StringUtils.isEmpty(userLngLat.getLastIpCityName())&& !StringUtils.isEmpty(userLngLat.getLastIp())){
      geoJsonPoint = comnfuncGrpcService.getGeoInfo(userLngLat.getLastIpCityName());
    }
    if(geoJsonPoint != null && regionInfo == null){
      log.info("query again for regionInfo");
      regionInfo = comnfuncGrpcService.getRegionInfo(geoJsonPoint.getCoordinates().get(0), geoJsonPoint.getCoordinates().get(1));
    }
    if(userLngLat.getOpenId() == null || StringUtils.isEmpty(userLngLat.getOpenId())){
      return regionInfo;
    }
    Query query = new Query();
    query.addCriteria(Criteria.where("openId").is(userLngLat.getOpenId()));
    List<WXUserFavor> wxUserFavorList = mongoTemplate.find(query, WXUserFavor.class);
    if(CollectionUtils.isEmpty(wxUserFavorList)){
      WXUserFavor wxUserFavor = new WXUserFavor();
      wxUserFavor.setOpenId(userLngLat.getOpenId());
      wxUserFavor.setLastLat(userLngLat.getLat());
      wxUserFavor.setLastLng(userLngLat.getLng());
      wxUserFavor.setLastLocationTime(AmcDateUtils.getCurrentDate());
      if(regionInfo != null){
        wxUserFavor.setLocationCode(regionInfo.getCityCode());
        wxUserFavor.setLocationCityName(regionInfo.getCityName());
        wxUserFavor.setLocationProvName(regionInfo.getProvName());
      }
      if(geoJsonPoint != null ){
        wxUserFavor.setLastIpLng(geoJsonPoint.getCoordinates().get(0));
        wxUserFavor.setLastIpLat(geoJsonPoint.getCoordinates().get(1));
      }

      mongoTemplate.save(wxUserFavor);
    }
    else{
      wxUserFavorList.get(0).setLastLng(userLngLat.getLng());
      wxUserFavorList.get(0).setLastLat(userLngLat.getLat());
      wxUserFavorList.get(0).setLastLocationTime(AmcDateUtils.getCurrentDate());
      if(regionInfo != null){
        wxUserFavorList.get(0).setLocationCode(regionInfo.getCityCode());
        wxUserFavorList.get(0).setLocationCityName(regionInfo.getCityName());
        wxUserFavorList.get(0).setLocationProvName(regionInfo.getProvName());
      }
      if(geoJsonPoint != null ){
        wxUserFavorList.get(0).setLastIpLng(geoJsonPoint.getCoordinates().get(0));
        wxUserFavorList.get(0).setLastIpLat(geoJsonPoint.getCoordinates().get(1));
      }

      mongoTemplate.save(wxUserFavorList.get(0));
    }
    return regionInfo;
  }

  @Override
  public AmcWechatUserInfo getUserInfo(String openId) {
    WechatUserExample wechatUserExample = new WechatUserExample();
    wechatUserExample.createCriteria().andOpenIdEqualTo(openId);
    List<WechatUser> wechatUsers = wechatUserMapper.selectByExample(wechatUserExample);
    if(CollectionUtils.isEmpty(wechatUsers)){
      return null;
    }else{
      AmcWechatUserInfo amcWechatUserInfo = new AmcWechatUserInfo();
      if(!wechatUsers.get(0).getVerifyCode().equals("-1") && !wechatUsers.get(0).getMobile().equals("-1")){
        log.error("user:{} havent finish verify mobile:{}", wechatUsers.get(0).getOpenId(), wechatUsers.get(0).getMobile());
        wechatUsers.get(0).setMobile(null);
      }
      amcWechatUserInfo.setWechatUser(wechatUsers.get(0));
      Query query = new Query();
      query.addCriteria(Criteria.where("openId").is(openId));

      List<WXUserWatchObject> wxUserWatchObjects = mongoTemplate.find(query, WXUserWatchObject.class);

      List<WXUserFavor> wxUserFavors = mongoTemplate.find(query, WXUserFavor.class);
      if(!CollectionUtils.isEmpty(wxUserWatchObjects)){
        amcWechatUserInfo.setWxUserWatchObjectList(wxUserWatchObjects);
      }
      if(!CollectionUtils.isEmpty(wxUserFavors)){
        amcWechatUserInfo.setWxUserFavor(wxUserFavors.get(0));
      }
      return amcWechatUserInfo;
    }
  }

  @Override
  public boolean unWatchOn(String openId, String phone, Long objectId, Integer type) {
    Query query = new Query();
    query.addCriteria(Criteria.where("openId").is(openId).and("objectId").is(objectId).and("type").is(type));
    List<WXUserWatchObject> wxUserWatchObjects = mongoTemplate.find(query, WXUserWatchObject.class);
    if(CollectionUtils.isEmpty(wxUserWatchObjects)){
      return true;
    }else{
      for(WXUserWatchObject wxUserWatchObject: wxUserWatchObjects){
        mongoTemplate.remove(wxUserWatchObject);
      }
    }
    //update watch count
    query = new Query();
    query.addCriteria(Criteria.where("openId").is(openId));
    wxUserWatchObjects = mongoTemplate.find(query, WXUserWatchObject.class);


    WechatUserExample wechatUserExample = new WechatUserExample();
    wechatUserExample.createCriteria().andOpenIdEqualTo(openId);
    List<WechatUser> wechatUsers = wechatUserMapper.selectByExample(wechatUserExample);
    if(CollectionUtils.isEmpty(wechatUsers)){
      log.error("This openId:{} is not available in wechatUser table", openId);
      return true;
    }
    WechatUserStatic wechatUserStatic = new WechatUserStatic();
    wechatUserStatic.setWatchCount(Long.valueOf(wxUserWatchObjects.size()));
    WechatUserStaticExample wechatUserStaticExample= new WechatUserStaticExample();
    wechatUserStaticExample.createCriteria().andWechatUserIdEqualTo(wechatUsers.get(0).getId());
    wechatUserStaticMapper.updateByExampleSelective(wechatUserStatic, wechatUserStaticExample);
    return true;
  }

  public Page<WechatUser> getUserInfos(PageInfo pageInfo) {
    return null;
  }

  @Override
  public List<WechatUserVo> getAllWechatUsers(int offset, int size, QueryParam queryParam)
      throws Exception {
    WechatUserExtExample wechatUserExample = getExampleByParam(queryParam);
    if(queryParam.getPageInfo().getSort() == null){
      wechatUserExample.setOrderByClause(" wu.id desc ");
    }else{
      StringBuilder sortSB = new StringBuilder();
      for(String sortStr: queryParam.getPageInfo().getSort()){
        WechatUserSortEnum wechatUserSortEnum = WechatUserSortEnum.lookupByDisplayNameUtil(sortStr);
        if(sortSB.length() > 0 && wechatUserSortEnum != null){
          sortSB.append(",");
        }
        makeSortInfo(sortSB, wechatUserSortEnum);
      }
      if(sortSB.length() > 0){
        wechatUserExample.setOrderByClause(sortSB.toString());
      }
    }
    RowBounds rowBounds = new RowBounds(offset, size);
    List<WechatUserVo> wechatUsers =  wechatUserExtMapper.selectByExampleWithRowboundsExt(wechatUserExample, rowBounds);

    Query query = new Query();
    List<String> openIds =  wechatUsers.stream().map(item->item.getWechatUser().getOpenId()).collect(
        Collectors.toList());
    query.addCriteria(Criteria.where("openId").in(openIds));
    List<WXUserWatchObject> wxUserWatchObjects = mongoTemplate.find(query, WXUserWatchObject.class);

    List<WXUserGeoRecord> wxUserGeoRecords = mongoTemplate.find(query, WXUserGeoRecord.class);

    List<WXUserFavor> wxUserFavors = mongoTemplate.find(query, WXUserFavor.class);
    connectWXUserVos(wxUserWatchObjects, wxUserGeoRecords, wxUserFavors , wechatUsers);
    return wechatUsers;
  }

  @Override
  public List<WechatUser> getSimpleWechatUsers(int offset, int size, QueryParam queryParam)
      throws ParseException {
    WechatUserExample wechatUserExample = new WechatUserExample();

    wechatUserExample.setOrderByClause(" id desc ");


    RowBounds rowBounds = new RowBounds(offset, size);
    List<WechatUser> wechatUsers =  wechatUserMapper.selectByExampleWithRowbounds(wechatUserExample, rowBounds);

    return wechatUsers;

  }

  private void makeSortInfo(StringBuilder sortSB, WechatUserSortEnum wechatUserSortEnum) {
    switch (wechatUserSortEnum){
      case FIRSTLOGIN:
        sortSB.append(" wus.first_login_time desc ");
        break;
      case LASTLOGIN:
        sortSB.append(" wus.last_time desc ");
        break;
      case ONLINETIME:
        sortSB.append(" wus.online_time desc ");
        break;
      case WATCHCOUNT:
        sortSB.append(" wus.watch_count desc ");
        break;
    }
  }

  private void connectWXUserVos(List<WXUserWatchObject> wxUserWatchObjects,
      List<WXUserGeoRecord> wxUserGeoRecords,
      List<WXUserFavor> wxUserFavors,
      List<WechatUserVo> wechatUsers) {
    Map<String, List<WXUserWatchObject>> wxUserWatchObjectMap = new HashMap<>();
    if(!CollectionUtils.isEmpty(wxUserWatchObjects)){
      for(WXUserWatchObject wxUserWatchObject: wxUserWatchObjects){
        if(!wxUserWatchObjectMap.containsKey(wxUserWatchObject.getOpenId())){
          wxUserWatchObjectMap.put(wxUserWatchObject.getOpenId(), new ArrayList<>());
        }
        wxUserWatchObjectMap.get(wxUserWatchObject.getOpenId()).add(wxUserWatchObject);
      }

    }
    Map<String, WXUserGeoRecord> wxUserGeoRecordMap = null;
    if(!CollectionUtils.isEmpty(wxUserGeoRecords)){
      wxUserGeoRecordMap = wxUserGeoRecords.stream().collect(Collectors.toMap(item->item.getOpenId(), item->item));
    }
    Map<String, WXUserFavor> wxUserFavorMap = null;
    if(!CollectionUtils.isEmpty(wxUserFavors)){
      wxUserFavorMap = wxUserFavors.stream().collect(Collectors.toMap(item->item.getOpenId(), item->item));
    }
    for(WechatUserVo wechatUserVo: wechatUsers){
      if(wxUserWatchObjectMap != null && wxUserWatchObjectMap.containsKey(wechatUserVo.getWechatUser().getOpenId())){
        List<WXUserWatchObject> watchDebts = wxUserWatchObjectMap.get(wechatUserVo.getWechatUser().getOpenId())
            .stream().filter(item-> item.getType() == AmcDebtAssetTypeEnum.AMC_DEBT.getId()).collect(
                Collectors.toList());

        List<WXUserWatchObject> watchAssets = wxUserWatchObjectMap.get(wechatUserVo.getWechatUser().getOpenId())
            .stream().filter(item-> item.getType() == AmcDebtAssetTypeEnum.AMC_ASSET.getId()).collect(
                Collectors.toList());
        wechatUserVo.setWxUserWatchDebts(watchDebts);
        wechatUserVo.setWxUserWatchAssets(watchAssets);
      }
      if(wxUserGeoRecordMap != null && wxUserGeoRecordMap.containsKey(wechatUserVo.getWechatUser().getOpenId())){
        wechatUserVo.setWxUserGeoRecord(wxUserGeoRecordMap.get(wechatUserVo.getWechatUser().getOpenId()));
      }

      if(wxUserFavorMap != null && wxUserFavorMap.containsKey(wechatUserVo.getWechatUser().getOpenId())){
        wechatUserVo.setWxUserFavor(wxUserFavorMap.get(wechatUserVo.getWechatUser().getOpenId()));
      }
    }

  }

  private WechatUserExtExample getExampleByParam(QueryParam queryParam) throws Exception {
    WechatUserExtExample wechatUserExample = new WechatUserExtExample();
    boolean hasCond = false;
    WechatUserExample.Criteria criteria = wechatUserExample.createCriteria();
    if(queryParam.getPhoneBinded() != null ){
      if(queryParam.getPhoneBinded().equals(true)){
        criteria.andMobileNotEqualTo("-1").andVerifyCodeEqualTo("-1");
        hasCond = true;
      }
    }


    if(!StringUtils.isEmpty(queryParam.getWechatNickName())){
      StringBuilder sb = new StringBuilder("%");
      sb.append(queryParam.getWechatNickName()).append("%");
      criteria.andNickNameLike(sb.toString());
      hasCond = true;
    }
    if(!StringUtils.isEmpty(queryParam.getStateInfo())){
      StringBuilder sb = new StringBuilder("%");
      sb.append(queryParam.getStateInfo()).append("%");
      criteria.andStateInfoLike(sb.toString());
      hasCond = true;
    }

    if(!StringUtils.isEmpty(queryParam.getMobileNum())){
      StringBuilder sb = new StringBuilder("%");
      sb.append(queryParam.getMobileNum()).append("%");
      criteria.andMobileLike(sb.toString());
      hasCond = true;
    }
    if(!StringUtils.isEmpty(queryParam.getNote())){
      StringBuilder sb = new StringBuilder("%");
      sb.append(queryParam.getNote()).append("%");
      criteria.andNoteLike(sb.toString());
      hasCond = true;
    }


    if(!CollectionUtils.isEmpty(queryParam.getFirstLoginTime()) && queryParam.getFirstLoginTime().size() >= 2){
//      wechatUserExample.setAndClause();
      StringBuilder sbFirstLoginTimeSql = new StringBuilder("");
      if(!hasCond){
        sbFirstLoginTimeSql.append(" where wus.first_login_time ");
      }else{
        sbFirstLoginTimeSql.append(" and wus.first_login_time ");
      }

      if(queryParam.getFirstLoginTime().get(0).equals("-1")){
        sbFirstLoginTimeSql.append(" < '").append((queryParam.getFirstLoginTime().get(1))).append("'");
      }else if(queryParam.getFirstLoginTime().get(1).equals("-1")){
        sbFirstLoginTimeSql.append(" > '").append((queryParam.getFirstLoginTime().get(0))).append("'");
      }else{
        if(sdfOfDay.parse(getBeginDate(queryParam.getFirstLoginTime().get(0))).before(sdfOfDay.parse(getBeginDate(queryParam.getFirstLoginTime().get(1))))){
          sbFirstLoginTimeSql.append(" > '").append(getBeginDate(queryParam.getFirstLoginTime().get(0))).append("'");
          sbFirstLoginTimeSql.append(" and wus.first_login_time < '").append((getEndDate(queryParam.getFirstLoginTime().get(1)))).append("'");
        }else if(sdfOfDay.parse(queryParam.getFirstLoginTime().get(0)).after(sdfOfDay.parse(queryParam.getFirstLoginTime().get(1)))){
          sbFirstLoginTimeSql.append(" < '").append(getEndDate(queryParam.getFirstLoginTime().get(0)));
          sbFirstLoginTimeSql.append("' and wus.first_login_time > '").append(getBeginDate(queryParam.getFirstLoginTime().get(1))).append("'");
        }
      }
      wechatUserExample.setAndClause(sbFirstLoginTimeSql.toString());
    }
    return wechatUserExample;
  }

  private String getBeginDate(String beginDate) throws Exception {
    if(StringUtils.isEmpty(beginDate)){
      throw ExceptionUtils.getAmcException(AmcExceptions.MISSING_MUST_PARAM, beginDate);
    }
    if(beginDate.length() <= 11){
      return String.format("%s %s",StringUtils.trim(beginDate), "00:00:00");
    }
    return beginDate;
  }

  private String getEndDate(String endDate) throws Exception {
    if(StringUtils.isEmpty(endDate)){
      throw ExceptionUtils.getAmcException(AmcExceptions.MISSING_MUST_PARAM, endDate);
    }
    if(endDate.length() <= 11){
      return String.format("%s %s",StringUtils.trim(endDate), "23:59:59");
    }
    return endDate;
  }

  @Override
  public List<WXUserWatchCount> getWXUserWatchCount(List<WXUserWatchOnObject> objectList) {
    List<WXUserWatchCount> wxUserWatchCounts = new ArrayList<>();
    for(WXUserWatchOnObject wxUserWatchOnObject: objectList){
      WXUserWatchCount wxUserWatchCount = new WXUserWatchCount();
      Query query = new Query();
      query.addCriteria(Criteria.where("objectId").is(wxUserWatchOnObject.getObjectId()).and("type").is(wxUserWatchOnObject.getType()));
      List<WXUserWatchObject> wxUserWatchObjects = mongoTemplate.find(query, WXUserWatchObject.class);
      wxUserWatchCount.setObjId(wxUserWatchOnObject.getObjectId());
      wxUserWatchCount.setObjType(wxUserWatchOnObject.getType());
      wxUserWatchCount.setCount(wxUserWatchObjects.size());
      wxUserWatchCounts.add(wxUserWatchCount);
    }
    return wxUserWatchCounts;
  }

  @Override
  public WechatUserInfo saveWechatUserInfo(String openId, String accessToken, String stateInfo) {
    String url = String.format(getUserInfoUrl, accessToken, openId);
    ResponseEntity<WechatUserInfo> responseEntity = null;
    WechatUserInfo wechatUserInfo = null;
    try{
      responseEntity = restTemplate.getForEntity(url,
          WechatUserInfo.class);
      wechatUserInfo = responseEntity.getBody();
      log.info(wechatUserInfo.getHeadImgUrl());
    }catch (Exception ex){
      log.error("Failed to get userInfo by accessToken:{}, openId:{}", accessToken, openId, ex);
      ResponseEntity<String> responseStrEntity = restTemplate.getForEntity(url,
          String.class);
      log.info(responseStrEntity.getBody());
      wechatUserInfo = gson.fromJson(responseStrEntity.getBody(), WechatUserInfo.class);
    }

    if(wechatUserInfo == null || (wechatUserInfo.getNickName() == null && wechatUserInfo.getOpenId() == null)){
      return null;
    }

    WechatUserExample wechatUserExample = new WechatUserExample();
    wechatUserExample.createCriteria().andOpenIdEqualTo(openId);

    List<WechatUser> wechatUsers = wechatUserMapper.selectByExample(wechatUserExample);
    WechatUser wechatUser = new WechatUser();
    AmcBeanUtils.copyProperties(wechatUserInfo, wechatUser);
    wechatUser.setProvince(wechatUserInfo.getProvince());
    wechatUser.setUnionId(wechatUserInfo.getUnionId());
    wechatUser.setNickName(wechatUserInfo.getNickName());
    wechatUser.setHeadImgUrl(wechatUserInfo.getHeadImgUrl());
    wechatUser.setCountry(wechatUserInfo.getCountry());
    wechatUser.setLanguage(wechatUserInfo.getLanguage());
    wechatUser.setCity(wechatUserInfo.getCity());
    wechatUser.setSex(wechatUserInfo.getSex());
    if(!StringUtils.isEmpty(stateInfo)){
      wechatUser.setStateInfo(stateInfo);
    }
    if(CollectionUtils.isEmpty(wechatUsers)){


      wechatUserMapper.insertSelective(wechatUser);
      WechatUserStatic wechatUserStatic = new WechatUserStatic();
      wechatUserStatic.setFirstLoginTime(AmcDateUtils.getCurrentDate());
      wechatUserStatic.setWechatUserId(wechatUser.getId());
      wechatUserStaticMapper.insertSelective(wechatUserStatic);
    }else if(wechatUsers.get(0).getStateInfo() != null && !wechatUsers.get(0).getStateInfo().equals("-1")){
      wechatUser.setStateInfo(null);
      AmcBeanUtils.copyProperties(wechatUser, wechatUsers.get(0));
      wechatUserMapper.updateByPrimaryKeySelective(wechatUsers.get(0));

    }else if(wechatUsers.get(0).getStateInfo() == null || wechatUsers.get(0).getStateInfo().equals("-1")){
      wechatUser.setStateInfo(stateInfo);
      AmcBeanUtils.copyProperties(wechatUser, wechatUsers.get(0));
      wechatUserMapper.updateByPrimaryKeySelective(wechatUsers.get(0));
    }
    wxToolService.notifyWechatUserLogin(wechatUserInfo);
    return wechatUserInfo;
  }

  @Override
  public WechatUserInfo saveRpcWXVisitorInfo(WechatUserInfo wechatUserInfo, String accessToken, String stateInfo) {

    WechatUserExample wechatUserExample = new WechatUserExample();
    wechatUserExample.createCriteria().andOpenIdEqualTo(wechatUserInfo.getOpenId());
    List<WechatUser> wechatUsers = wechatUserMapper.selectByExample(wechatUserExample);
    if(CollectionUtils.isEmpty(wechatUsers)){
      WechatUserInfo saveAgainResult = saveWechatUserInfo(wechatUserInfo.getOpenId(), accessToken, stateInfo);
      if(saveAgainResult != null && !StringUtils.isEmpty(saveAgainResult.getNickName())){
        return saveAgainResult;
      }else{
        //use the rpc userInfo directlly
        WechatUser wechatUser = new WechatUser();
        wechatUser.setOpenId(wechatUserInfo.getOpenId());
        wechatUser.setCity(wechatUserInfo.getCity());
        wechatUser.setCountry(wechatUserInfo.getCountry());
        wechatUser.setHeadImgUrl(wechatUserInfo.getHeadImgUrl());
        wechatUser.setNickName(wechatUserInfo.getNickName());
        wechatUser.setUnionId(wechatUserInfo.getUnionId());
        wechatUser.setProvince(wechatUserInfo.getProvince());
        if(!StringUtils.isEmpty(stateInfo)){
          wechatUser.setStateInfo(stateInfo);
        }
        wechatUserMapper.insertSelective(wechatUser);
      }
    }
    return wechatUserInfo;
  }

  @Override
  public void updateUserVisitInfo(String openId, Long lastTime, Long timeSpent, Long firstLoginTime)
      throws ParseException {
    WechatUserExample wechatUserExample = new WechatUserExample();
    wechatUserExample.createCriteria().andOpenIdEqualTo(openId);
    List<WechatUser> wechatUsers = wechatUserMapper.selectByExample(wechatUserExample);

    if(!CollectionUtils.isEmpty(wechatUsers)){
      WechatUserStaticExample wechatUserStaticExample = new WechatUserStaticExample();
      wechatUserStaticExample.createCriteria().andWechatUserIdEqualTo(wechatUsers.get(0).getId());
      List<WechatUserStatic> wechatUserStatics = wechatUserStaticMapper.selectByExample(wechatUserStaticExample);
      WechatUserStatic wechatUserStatic = null;
      if(CollectionUtils.isEmpty(wechatUserStatics)){
        //make new wechatUserStatic
        wechatUserStatic = new WechatUserStatic();
        wechatUserStatic.setWechatUserId(wechatUsers.get(0).getId());
        wechatUserStaticMapper.insertSelective(wechatUserStatic);

      }else{
        wechatUserStatic = wechatUserStatics.get(0);
      }
      if(firstLoginTime != null && wechatUserStatic.getFirstLoginTime().before(AmcDateUtils.getDateFromStr("1901-01-01"))){
        wechatUserStatic.setFirstLoginTime(AmcDateUtils.toUTCDateFromLocal(firstLoginTime));

      }
      if(timeSpent != null){
        wechatUserStatic.setOnlineTime(timeSpent);
      }
      if(lastTime != null){
        wechatUserStatic.setLastTime(AmcDateUtils.toUTCDateFromLocal(lastTime));
      }
      wechatUserStaticMapper.updateByPrimaryKeySelective(wechatUserStatic);
    }
  }


  @Override
  public String sendEvent(String xmlMsg) {
    return null;
  }

  @Override
  public void updateWXUser(WechatUser wechatUser) {
    WechatUser wechatUserUpdate = new WechatUser();
    wechatUserUpdate.setId(wechatUser.getId());
    wechatUserUpdate.setNote(wechatUser.getNote());
    wechatUserUpdate.setAmcTag(wechatUser.getAmcTag());
    wechatUserUpdate.setUpdateTime(AmcDateUtils.getCurrentDate());
    wechatUserMapper.updateByPrimaryKeySelective(wechatUserUpdate);

  }

  @Override
  public boolean unWatchOnList(List<WXUserWatchOnVo> wxUserWatchOnVos) {
    for(WXUserWatchOnVo wxUserWatchOnVo: wxUserWatchOnVos){
      Query query = new Query();
      query.addCriteria(Criteria.where("openId").is(wxUserWatchOnVo.getOpenId()).and("objectId")
          .is(wxUserWatchOnVo.getObjectId()).and("type").is(wxUserWatchOnVo.getType()));
      List<WXUserWatchObject> wxUserWatchObjects = mongoTemplate.find(query, WXUserWatchObject.class);
      if(CollectionUtils.isEmpty(wxUserWatchObjects)){
        continue;
      }else{
        for(WXUserWatchObject wxUserWatchObject: wxUserWatchObjects){
          mongoTemplate.remove(wxUserWatchObject);
        }
      }

    }
    //update watch count
    Query query = new Query();
    query.addCriteria(Criteria.where("openId").is(wxUserWatchOnVos.get(0).getOpenId()));
    List<WXUserWatchObject> wxUserWatchObjects = mongoTemplate.find(query, WXUserWatchObject.class);


    WechatUserExample wechatUserExample = new WechatUserExample();
    wechatUserExample.createCriteria().andOpenIdEqualTo(wxUserWatchOnVos.get(0).getOpenId());
    List<WechatUser> wechatUsers = wechatUserMapper.selectByExample(wechatUserExample);
    if(CollectionUtils.isEmpty(wechatUsers)){
      log.error("This openId:{} is not available in wechatUser table", wxUserWatchOnVos.get(0).getOpenId());
      return true;
    }
    WechatUserStatic wechatUserStatic = new WechatUserStatic();
    wechatUserStatic.setWatchCount(Long.valueOf(wxUserWatchObjects.size()));
    WechatUserStaticExample wechatUserStaticExample= new WechatUserStaticExample();
    wechatUserStaticExample.createCriteria().andWechatUserIdEqualTo(wechatUsers.get(0).getId());
    wechatUserStaticMapper.updateByExampleSelective(wechatUserStatic, wechatUserStaticExample);
    return true;
  }



  public Long getAllWechatUserCount(QueryParam queryParam) throws Exception {
    WechatUserExtExample exampleByParam = getExampleByParam(queryParam);
    return wechatUserExtMapper.countByExample(exampleByParam);
  }

  private int getUserWatchOnCount(String openId){
    Query query = new Query();
    query.addCriteria(Criteria.where("openId").is(openId));
    List<WXUserWatchObject> wxUserWatchObjects = mongoTemplate.find(query, WXUserWatchObject.class);
    return wxUserWatchObjects.size();
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



  @Data class TagCreateResp extends GeneralResp{
    TagCreateInfo tag;
  }

  @Data class TagCreateInfo{
    Long id;
    String name;
  }

  @Data
  public class TagUserBatchReq{
    @SerializedName("openid_list")
    List<String> openIdList;
    @SerializedName("tagid")
    Long tagId;
  }

  @Data
  public class TimePeriod{
    @SerializedName("begin_date")
    String beginDate;
    @SerializedName("end_date")
    String endDate;
  }

}
