package com.wensheng.zcc.amc.controller;

import java.io.InputStream;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author chenwei on 2/21/19
 * @project zcc-backend
 */
@Controller
@Slf4j
@RequestMapping("/api/client/miniapp/user")
public class MiniAppUserController {
  @Value("${wechat.miniapp.appId}")
  String appId;

  @Value("${wechat.miniapp.appSecret}")
  String appSecret;

  @Value("${wechat.miniapp.get_qrcode_url}")
  String qrCodeUrl;

  @Value("${wechat.miniapp.get_token_url}")
  String tokenUrl;

  private RestTemplate restTemplate = new RestTemplate();

  @RequestMapping(value = "/miniapp-qrcode-image", method = RequestMethod.GET)
  public ResponseEntity<byte[]> getMiniappQrImage(@RequestParam("scene") String scene,
      @RequestParam("page") String page) {
    if(StringUtils.isEmpty(scene)){
      scene = "default";
    }



    UriComponentsBuilder tokenBuilder = UriComponentsBuilder.fromHttpUrl(tokenUrl).queryParam("appid", appId)
        .queryParam("secret", appSecret).queryParam("grant_type", "client_credential");
    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    HttpEntity<?> entity = new HttpEntity<>(headers);

    ResponseEntity response = restTemplate.exchange(tokenBuilder.toUriString(), HttpMethod.GET, entity, Map.class);
    System.out.println(((ResponseEntity<Map>) response).getBody().toString());
    String token =(String) ((Map)response.getBody()).get("access_token");


    String input = "{\"scene\":\""+scene+"\",\"page\":\""+page+"\"}";

    UriComponentsBuilder qrBuilder = UriComponentsBuilder.fromHttpUrl(qrCodeUrl).queryParam("access_token",
        token.toString());

    headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<String> requestEntity= new HttpEntity<String>( input, headers);

    response = restTemplate.exchange(qrBuilder.toUriString(), HttpMethod.POST, requestEntity,
        byte[].class);

    headers = new HttpHeaders();
    byte[] in = (byte[])response.getBody();


    ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(in, headers, HttpStatus.OK);
    return responseEntity;
  }


}
