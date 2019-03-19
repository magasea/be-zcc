package com.wensheng.zcc.sso.service.impl;

import com.wensheng.zcc.sso.service.WechatService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

/**
 * @author chenwei on 3/19/19
 * @project miniapp-backend
 */
@Service
public class WechatServiceImpl implements WechatService {

  @Value("${weixin.loginUrl}")
  String loginUrl;

  private RestTemplate restTemplate = new RestTemplate();

  @PostConstruct
  private  void init(){
    List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
    //Add the Jackson Message converter
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    // Note: here we are making this converter to process any kind of response,
    // not only application/*json, which is the default behaviour
    converter.setSupportedMediaTypes(Arrays.asList(new MediaType[]{MediaType.ALL}));
    messageConverters.add(converter);
    restTemplate.setMessageConverters(messageConverters);
  }

  @Override
  public String loginWechat(String code) {
    String loginWechatUrl = loginUrl.replace("JSCODE", code);
    ResponseEntity<Map> responseEntity = restTemplate.getForEntity(loginWechatUrl,
        Map.class);
    StringBuilder sb = new StringBuilder();
    if(!CollectionUtils.isEmpty(responseEntity.getBody())){
      responseEntity.getBody().entrySet().forEach( item -> sb.append(item.toString()));
    }
    return sb.toString();
  }
}
