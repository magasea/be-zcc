package com.wensheng.zcc.wechat.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wensheng.zcc.common.utils.AmcDateUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import com.wensheng.zcc.wechat.module.vo.WXGetTicketResp;
import com.wensheng.zcc.wechat.module.vo.WXMaterialCount;
import com.wensheng.zcc.wechat.module.vo.WXSign4Url;
import com.wensheng.zcc.wechat.service.WXBasicService;
import com.wensheng.zcc.wechat.service.WXToolService;
import com.wensheng.zcc.wechat.utils.WxToolsUtil;
import com.wensheng.zcc.wechat.utils.wechat.SHA1;
import java.time.Instant;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class WXToolServiceImpl implements WXToolService {

  @Autowired
  WXBasicService wxBasicService;

  @Value("${weixin.get_ticket_url}")
  String getTicketUrl;

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
    String fixRandomStr = "WENSHENGAMC";
    Long timeStamp = Instant.now().getEpochSecond();
    String signatureLocal = SHA1.getSHAFixSort(ticket, timeStamp.toString(), fixRandomStr,url);
    wxSign4Url.setRandomStr(randomStr);
    wxSign4Url.setSignKey(signatureLocal);
    wxSign4Url.setTimeStamp(timeStamp);
    return wxSign4Url;

  }
}
