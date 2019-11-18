package com.wensheng.zcc.amc.service.impl;

import com.google.gson.Gson;
import com.wensheng.zcc.common.module.LatLng;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class AmcBaiDuLogisQuery {
  @Value("${baidu.logis.appId}")
  private String baiduAppId;

  @Value("${baidu.logis.appName}")
  private String baiduAppName;

  @Value("${baidu.logis.appAK}")
  private String baiduAppAK;

  @Value("${baidu.logis.appSK}")
  private String baiduAppSK;

  @Value("${baidu.logis.getGeoURL}")
  private String baiduGeoURL;

  private RestTemplate restTemplate  = new RestTemplate();

  private Gson gson = new Gson();

  @PostConstruct
  void init(){
    GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
    gsonHttpMessageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL) );
    restTemplate.getMessageConverters().add(gsonHttpMessageConverter);
  }

  public LatLng getLogisByAddress(String  address) throws UnsupportedEncodingException {



// 计算sn跟参数对出现顺序有关，get请求请使用LinkedHashMap保存<key,value>，该方法根据key的插入顺序排序；post请使用TreeMap保存<key,value>，该方法会自动将key按照字母a-z顺序排序。所以get请求可自定义参数顺序（sn参数必须在最后）发送请求，但是post请求必须按照字母a-z顺序填充body（sn参数必须在最后）。以get请求为例：http://api.map.baidu.com/geocoder/v3/?address=百度大厦&output=json&ak=yourak，paramsMap中先放入address，再放output，然后放ak，放入顺序必须跟get请求中对应参数的出现顺序保持一致。

    Map paramsMap = new LinkedHashMap<String, String>();
    paramsMap.put("address", address);
    paramsMap.put("output", "json");
    paramsMap.put("ak", baiduAppAK);


    // 调用下面的toQueryString方法，对LinkedHashMap内所有value作utf8编码，拼接返回结果address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourak
    String paramsStr = toQueryString(paramsMap);

    // 对paramsStr前面拼接上/geocoder/v2/?，后面直接拼接yoursk得到/geocoder/v2/?address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourakyoursk
    String wholeStr = new String("/geocoder/v2/?" + paramsStr + baiduAppSK);

    // 对上面wholeStr再作utf8编码
    String tempStr = URLEncoder.encode(wholeStr, "UTF-8");

    // 调用下面的MD5方法得到最后的sn签名7de5a22212ffaa9e326444c75a58f9a0
    String sn = MD5(tempStr);
    System.out.println(sn);
    String url = String.format(baiduGeoURL, address, baiduAppAK, sn );
    System.out.println(url);
    log.info(url);

      BaiduResp resp =
          restTemplate.exchange(url, HttpMethod.GET, null,
              new ParameterizedTypeReference<BaiduResp>() {}).getBody();





    return resp.getResult().getLocation();


  }

  // 对Map内所有value作utf8编码，拼接返回结果
  public String toQueryString(Map<?, ?> data)
      throws UnsupportedEncodingException {
    StringBuffer queryString = new StringBuffer();
    for (Entry<?, ?> pair : data.entrySet()) {
      queryString.append(pair.getKey() + "=");
      queryString.append(URLEncoder.encode((String) pair.getValue(),
          "UTF-8") + "&");
    }
    if (queryString.length() > 0) {
      queryString.deleteCharAt(queryString.length() - 1);
    }
    return queryString.toString();
  }

  // 来自stackoverflow的MD5计算方法，调用了MessageDigest库函数，并把byte数组结果转换成16进制
  public String MD5(String md5) {
    try {
      java.security.MessageDigest md = java.security.MessageDigest
          .getInstance("MD5");
      byte[] array = md.digest(md5.getBytes());
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < array.length; ++i) {
        sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
            .substring(1, 3));
      }
      return sb.toString();
    } catch (java.security.NoSuchAlgorithmException e) {
    }
    return null;
  }
  @Data
  private class BaiduResp{
    BigDecimal status;
    GeoResult result;
  }
  @Data
  private class GeoResult{
    int status;
    String message;
    LatLng location;
    int precise;
    int confidence;
    int comprehension;
  }

}
