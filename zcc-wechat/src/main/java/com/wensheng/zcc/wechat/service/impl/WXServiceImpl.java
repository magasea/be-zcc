package com.wensheng.zcc.wechat.service.impl;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatTag;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatUser;
import com.wensheng.zcc.wechat.utils.wechat.AesException;
import com.wensheng.zcc.wechat.utils.wechat.SHA1;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.xml.parsers.ParserConfigurationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;

@Service
public class WXServiceImpl {

  @Value("${weixin.appId}")
  String appId;

  @Value("${weixin.appSecret}")
  String appSecret;

  @Value("${weixin.encodingAesKey}")
  String encodingAesKey;

  @Value("${weixin.token}")
  String token;

  @Value("${weixin.get_public_token_url}")
  String getPublicTokenUrl;

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

  @Value("${weixin.untaguser_batch_url}")
  String unTagUserBatchUrl;

  @Value("${weixin.taguser_batch_url}")
  String tagUserBatchUrl;

//  get_tag_usersids_url
  @Value("${weixin.get_useroftagid_url}")
  String getUsersOfTagIdUrl;

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
  String xmlFormat = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
  String afterAesEncrypt = "jn1L23DB+6ELqJ+6bruv21Y6MD7KeIfP82D6gU39rmkgczbWwt5+3bnyg5K55bgVtVzd832WzZGMhkP72vVOfg==";
  String randomStr = "aaaabbbbccccdddd";

  String replyMsg2 = "<xml><ToUserName><![CDATA[oia2Tj我是中文jewbmiOUlr6X-1crbLOvLw]]></ToUserName><FromUserName><![CDATA[gh_7f083739789a]]></FromUserName><CreateTime>1407743423</CreateTime><MsgType><![CDATA[video]]></MsgType><Video><MediaId><![CDATA[eYJ1MbwPRJtOvIEabaxHs7TX2D-HV71s79GUxqdUkjm6Gs2Ed1KF3ulAOA9H1xG0]]></MediaId><Title><![CDATA[testCallBackReplyVideo]]></Title><Description><![CDATA[testCallBackReplyVideo]]></Description></Video></xml>";
  String afterAesEncrypt2 = "jn1L23DB+6ELqJ+6bruv23M2GmYfkv0xBh2h+XTBOKVKcgDFHle6gqcZ1cZrk3e1qjPQ1F4RsLWzQRG9udbKWesxlkupqcEcW7ZQweImX9+wLMa0GaUzpkycA8+IamDBxn5loLgZpnS7fVAbExOkK5DYHBmv5tptA9tklE/fTIILHR8HLXa5nQvFb3tYPKAlHF3rtTeayNf0QuM+UW/wM9enGIDIJHF7CLHiDNAYxr+r+OrJCmPQyTy8cVWlu9iSvOHPT/77bZqJucQHQ04sq7KZI27OcqpQNSto2OdHCoTccjggX5Z9Mma0nMJBU+jLKJ38YB1fBIz+vBzsYjrTmFQ44YfeEuZ+xRTQwr92vhA9OxchWVINGC50qE/6lmkwWTwGX9wtQpsJKhP+oS7rvTY8+VdzETdfakjkwQ5/Xka042OlUb1/slTwo4RscuQ+RdxSGvDahxAJ6+EAjLt9d8igHngxIbf6YyqqROxuxqIeIch3CssH/LqRs+iAcILvApYZckqmA7FNERspKA5f8GoJ9sv8xmGvZ9Yrf57cExWtnX8aCMMaBropU/1k+hKP5LVdzbWCG0hGwx/dQudYR/eXp3P0XxjlFiy+9DMlaFExWUZQDajPkdPrEeOwofJb";

  public String checkWechatResp(Long timeStamp, String nonce, String echoStr, String signature)
      throws AesException, ParserConfigurationException, IOException, SAXException {

    String signatureLocal = SHA1.getSHA1(token, timeStamp.toString(), nonce, null);
    if( signature.equals(signatureLocal) ){
      return echoStr;
    }else{
      return "false";
    }

//    WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
//    String afterEncrpt = pc.encryptMsg(echoStr, timeStamp.toString(), nonce);
//
//    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//    DocumentBuilder db = dbf.newDocumentBuilder();
//    StringReader sr = new StringReader(afterEncrpt);
//    InputSource is = new InputSource(sr);
//    Document document = db.parse(is);
//
//    Element root = document.getDocumentElement();
//    NodeList nodelist1 = root.getElementsByTagName("Encrypt");
//    NodeList nodelist2 = root.getElementsByTagName("MsgSignature");
//
//    String encrypt = nodelist1.item(0).getTextContent();
//    String msgSignature = nodelist2.item(0).getTextContent();
//    String fromXML = String.format(xmlFormat, encrypt);
//
//    // 第三方收到公众号平台发送的消息
//    String afterDecrpt = pc.decryptMsg(msgSignature, timeStamp.toString(), nonce, fromXML);
//    return afterDecrpt;
  }

  @Cacheable("token")
  public String getPublicToken(){
    String url = String.format(getPublicTokenUrl, appId, appSecret );
    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    HttpEntity<?> entity = new HttpEntity<>(headers);

    ResponseEntity response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
    System.out.println(((ResponseEntity<Map>) response).getBody().toString());
    String token =(String) ((Map)response.getBody()).get("access_token");
    return token;
  }


  public UserIdsResp getWechatPublicUserIds(String openId){
    String token = getPublicToken();
    StringBuilder url = new StringBuilder(String.format(getPublicUsersUrl, token, "" ));
    if(StringUtils.isEmpty(openId)){
      url.append("&next_openid=").append(openId);
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    HttpEntity<?> entity = new HttpEntity<>(headers);

    UserIdsResp response = restTemplate.exchange(url.toString(), HttpMethod.GET, entity, UserIdsResp.class).getBody();
    System.out.println(response.toString());

    return response;
  }


  public List<WechatUser> getWechatPublicUserInfo(List<String> openIds){
    String token = getPublicToken();

    UserListReq userListReq = new UserListReq();
    userListReq.setUser_list(new ArrayList<>());
    openIds.forEach(item-> userListReq.getUser_list().add(new UserIdInfoReq(item, "zh_CN")));
    String url = String.format(getPublicUsersInfoUrl, token );
    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    HttpEntity<UserListReq> entity = new HttpEntity<>( userListReq, headers);
    ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
    System.out.println(((ResponseEntity<Map>) response).getBody().toString());
    List<WechatUser> data =(List) ((Map)response.getBody()).get("user_info_list");
    return data;
  }

  public List<TagInfoExt> getWechatPublicUserTag(){
    String token = getPublicToken();

    String url = String.format(getUserTagsUrl, token );
    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    HttpEntity<?> entity = new HttpEntity<>(headers);
    ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
    System.out.println(((ResponseEntity<Map>) response).getBody().toString());
    List<TagInfoExt> data =(List) ((Map)response.getBody()).get("tags");
    return data;
  }

  public void createWechatPublicUserTag(String tagName){
    String token = getPublicToken();

    String url = String.format(createUserTagUrl, token );
    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    Tag tag = new Tag();
    tag.setTag(new HashMap<>());
    tag.getTag().put("name", tagName);
    HttpEntity<Tag> entity = new HttpEntity<Tag>(tag, headers);
    ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
    System.out.println(((ResponseEntity<Map>) response).getBody().toString());

  }

  public void delWechatPublicUserTag(Long tagId){
    String token = getPublicToken();

    String url = String.format(delUserTagUrl, token );
    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    Map<String, Long> tag = new HashMap<>();
    tag.put("id", tagId);
    HttpEntity<Map> entity = new HttpEntity<>(tag, headers);
    ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
    System.out.println(((ResponseEntity<Map>) response).getBody().toString());

  }

  public String untagWechatPublicUserBatch(List<String> openIds, Long tagId){
    String token = getPublicToken();

    String url = String.format(unTagUserBatchUrl, token );
    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    TagUserBatchReq tagUserBatchReq = new TagUserBatchReq();
    tagUserBatchReq.setOpenIdList( openIds);
    tagUserBatchReq.setTagId(tagId);
    HttpEntity<TagUserBatchReq> entity = new HttpEntity<>(tagUserBatchReq, headers);
    ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
    System.out.println(((ResponseEntity<Map>) response).getBody().toString());
    Map<String, String> respMap = (Map)response.getBody();
    return String.format("%s:%s", respMap.get("errcode"), respMap.get("errmsg") );

  }

  public String tagWechatPublicUserBatch(List<String> openIds, Long tagId){
    String token = getPublicToken();

    String url = String.format(tagUserBatchUrl, token );
    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    TagUserBatchReq tagUserBatchReq = new TagUserBatchReq();
    tagUserBatchReq.setOpenIdList(openIds);
    tagUserBatchReq.setTagId(tagId);

    HttpEntity<TagUserBatchReq> entity = new HttpEntity<>(tagUserBatchReq, headers);
    ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
    System.out.println(((ResponseEntity<Map>) response).getBody().toString());
    Map<String, String> respMap = (Map)response.getBody();
    return String.format("%s:%s", respMap.get("errcode"), respMap.get("errmsg") );

  }

  public List<Long> getTagOfUser(String openId){
    String token = getPublicToken();

    String url = String.format(getTagsOfUserUrl, token );
    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    Map<String, String> openIdMap = new HashMap<>();
    openIdMap.put("openid", openId);
    HttpEntity<Map> entity = new HttpEntity<>(openIdMap, headers);
    ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, TagsOfUser.class);
    System.out.println(response.getBody().toString());
    TagsOfUser resp = (TagsOfUser)response.getBody();
    if(StringUtils.isEmpty(resp.errmsg)){
      return resp.getTags();
    }else{
      return new ArrayList<>();
    }

  }

  public UserIdsResp getUsersOfTagId(Long tagId, String openId) throws Exception {
    String token = getPublicToken();

    String url = String.format(getUsersOfTagIdUrl, token );
    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    Map<String, Object> openIdMap = new HashMap<>();
    if(!StringUtils.isEmpty(openId)){
      openIdMap.put("next_openid", openId);
    }
    openIdMap.put("tagid", tagId);
    HttpEntity<Map> entity = new HttpEntity<>(openIdMap, headers);
    ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, UserIdsResp.class);
    UserIdsResp respBody = ((ResponseEntity<UserIdsResp>) response).getBody();
    System.out.println(respBody.toString());
    if(!StringUtils.isEmpty(respBody.errmsg)){
      throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_WECHAT_PARAMETER, String.format("%s:%s",
          respBody.getErrcode(), respBody.getErrmsg()));
    }

    return respBody;
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
  public class TagsOfUser extends GeneralResp{
    @SerializedName("tagid_list")
    List<Long> tags;

  }

  @Data
  public class TagInfoExt extends WechatTag {
    Long count;
  }
  @Data
  public class Tag{
    Map<String, String> tag;
  }

  @Data
  public class GeneralResp{
    Long errcode;
    String errmsg;

  }

  @Data
  public class TagUserBatchReq{
    @SerializedName("openid_list")
    List<String> openIdList;
    @SerializedName("tagid")
    Long tagId;
  }

}
