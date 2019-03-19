package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.module.dao.mongo.entity.WechatQrImage;
import com.wensheng.zcc.amc.service.MiniAppService;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author chenwei on 3/5/19
 * @project zcc-backend
 */
@Service
public class MiniAppServiceImpl implements MiniAppService {

  @Value("${wechat.miniapp.appId}")
  String appId;

  @Value("${wechat.miniapp.appSecret}")
  String appSecret;

  @Value("${wechat.miniapp.get_qrcode_url}")
  String qrCodeUrl;

  @Value("${wechat.miniapp.get_token_url}")
  String tokenUrl;

  @Value("${project.params.qrcode_image_path}")
  String qrcodeImagePath;

  @Autowired
  MongoTemplate wszccTemplate;


  private RestTemplate restTemplate = new RestTemplate();

  @Override
  public byte[] getImageBytes4QRCode(String scene, String page) {
    if(StringUtils.isEmpty(scene)){
      scene = "default";
    }


//    Query query = new Query();
//    query.addCriteria(Criteria.where("fileName").is(scene));
//    List<WechatQrImage> wechatQrImageList = wszccTemplate.find(query, WechatQrImage.class);
//    if(!CollectionUtils.isEmpty(wechatQrImageList)){
//      boolean returnNow = true;
//      if(Paths.get(qrcodeImagePath, scene).toFile().exists()){
//        InputStream in = getClass()
//            .getResourceAsStream(Paths.get(qrcodeImagePath, scene).toString());
//        HttpHeaders headers = new HttpHeaders();
//
//        headers.setContentType(MediaType.IMAGE_JPEG);
//        ResponseEntity<byte[]> responseEntity = null;
//        try {
//          responseEntity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.OK);
//        } catch (IOException e) {
//          e.printStackTrace();
//          returnNow = false;
//        }
//        if(returnNow){
//          return responseEntity;
//        }
//      }
//    }


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


    try {
      if (!Files.exists(Paths.get(qrcodeImagePath))){
        Files.createDirectories(Paths.get(qrcodeImagePath));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      Files.write(Paths.get(qrcodeImagePath, scene), in);

      if(Paths.get(qrcodeImagePath, scene).toFile().exists()){
        WechatQrImage wechatQrImage = new WechatQrImage();
        wechatQrImage.setFileName(scene);
        wszccTemplate.save(wechatQrImage);
      }



    } catch (IOException e) {
      e.printStackTrace();
    }
      return in;
  }
}
