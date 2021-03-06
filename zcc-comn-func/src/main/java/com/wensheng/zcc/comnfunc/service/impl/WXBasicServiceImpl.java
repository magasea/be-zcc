package com.wensheng.zcc.comnfunc.service.impl;

import com.google.gson.Gson;

import com.wensheng.zcc.comnfunc.service.WXBasicService;
import com.wensheng.zcc.comnfunc.utils.wechat.AesException;
import com.wensheng.zcc.comnfunc.utils.wechat.SHA1;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
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

@CacheConfig(cacheNames = {"TOKEN"})
@Service
public class WXBasicServiceImpl implements WXBasicService {

//  @Value("${weixin.appId}")
//  String appId;
//
//  @Value("${weixin.appSecret}")
//  String appSecret;

//  @Value("${weixin.encodingAesKey}")
//  String encodingAesKey;


  String token = "wensheng";


  @Value("${weixin.prod.get_public_token_url}")
  String getProdPublicTokenUrl;

  @Value("${weixin.test.get_public_token_url}")
  String getTestPublicTokenUrl;


  private Gson gson = new Gson();

  private RestTemplate restTemplate = new RestTemplate();
  @PostConstruct
  void init(){
    restTemplate.getMessageConverters().removeIf(item -> item instanceof MappingJackson2HttpMessageConverter);
    GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
    restTemplate.getMessageConverters().add(gsonHttpMessageConverter);
  }

  public String checkWechatResp(Long timeStamp, String nonce, String echoStr, String signature)
      throws AesException {

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

  @Cacheable(unless = "#result == null")
  @Override
  public String getPublicToken(String provileName){
    String url = null;
    if(provileName.equals("dev") || provileName.equals("test")){
      url = getTestPublicTokenUrl;
    }else{
      url = getProdPublicTokenUrl;
    }

    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    HttpEntity<?> entity = new HttpEntity<>(headers);

    ResponseEntity response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
    System.out.println(((ResponseEntity<Map>) response).getBody().toString());
    String token =(String) ((Map)response.getBody()).get("access_token");
    return token;
  }

}
