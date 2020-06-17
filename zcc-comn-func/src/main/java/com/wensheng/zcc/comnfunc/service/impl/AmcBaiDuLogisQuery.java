package com.wensheng.zcc.comnfunc.service.impl;

import com.google.gson.Gson;
import com.wensheng.zcc.common.module.LatLng;
import com.wensheng.zcc.common.module.dto.AmcRegionInfo;
import com.wensheng.zcc.comnfunc.module.dto.BaiduResponse;
import com.wensheng.zcc.comnfunc.module.dto.baidu.AddressDetail;
import com.wensheng.zcc.comnfunc.module.dto.baidu.Content;
import com.wensheng.zcc.comnfunc.module.dto.baidu.Point;
import com.wenshengamc.zcc.comnfunc.gaodegeo.ComnFuncServiceGrpc;
import com.wenshengamc.zcc.comnfunc.gaodegeo.ComnFuncServiceGrpc.ComnFuncServiceBlockingStub;
import com.wenshengamc.zcc.comnfunc.gaodegeo.GeoIp2LocationResp;
import com.wenshengamc.zcc.comnfunc.gaodegeo.GeoIpReq;
import io.grpc.ManagedChannel;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
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

  @Value("${baidu.logis.getIpAdd}")
  private String baiduGeoIp2AddUrl;


  @Value("${env.aliyunhost}")
  private String aliyunHost;

  @Value("${env.comnfuncport}")
  private int comnfuncPort;

  private RestTemplate restTemplate  = new RestTemplate();

  private Gson gson = new Gson();

  private String activeProfile = null;

  @Autowired
  Environment environment;
  @Autowired
  @Qualifier("comnFuncPubChannel")
  ManagedChannel comnFuncPubChannel;

  ComnFuncServiceBlockingStub comnFuncPubStub;

  @Autowired
  RegionServiceImpl regionService;



  @PostConstruct
  void init(){
    GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
    gsonHttpMessageConverter.setSupportedMediaTypes(
        Arrays.asList(MediaType.ALL, MediaType.TEXT_HTML) );
    restTemplate.getMessageConverters().add(gsonHttpMessageConverter);
    comnFuncPubStub = ComnFuncServiceGrpc.newBlockingStub(comnFuncPubChannel);
    for(String envName: environment.getActiveProfiles()){
      activeProfile = envName;

    }

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
    String wholeStr = new String("/geocoding/v3/?" + paramsStr + baiduAppSK);

    // 对上面wholeStr再作utf8编码
    String tempStr = URLEncoder.encode(wholeStr, "UTF-8");

    // 调用下面的MD5方法得到最后的sn签名7de5a22212ffaa9e326444c75a58f9a0
    String sn = MD5(tempStr);
    System.out.println(sn);
    String url = String.format(baiduGeoURL, address, baiduAppAK, sn );
    System.out.println(url);
    BaiduResp resp = null;
    try{
      resp =
          restTemplate.exchange(url, HttpMethod.GET, null,
              BaiduResp.class).getBody();
    }catch (Exception ex){
      log.error("Failed to get result");
      String respstr =
          restTemplate.exchange(url, HttpMethod.GET, null,
              String.class).getBody();
      log.error(respstr);
    }


    return resp.getResult().getLocation();


  }

  public Content getAddressByIp(String  ip) throws UnsupportedEncodingException {

    String profileName = activeProfile;

    if(profileName.equals("dev")||profileName.equals("test")||profileName.equals("preProd")){
      //call aliyun comnfunc service
      GeoIpReq.Builder girBuilder = GeoIpReq.newBuilder();
      girBuilder.setIpadd(ip);
      GeoIp2LocationResp cityByIp =  null;
      try {
        cityByIp = comnFuncPubStub.getCityByIp(girBuilder.build());
      }catch (Exception ex){
        log.error("Failed to call rpc service to get address by ip", ex);
        return null;
      }


      Content content = new Content();
      AddressDetail addressDetail = new AddressDetail();

      Point point = new Point();
      point.setX(String.valueOf(cityByIp.getLng()));
      point.setY(String.valueOf(cityByIp.getLat()));
      content.setPoint(point);
//      String  url = String.format("http://%s:%s//amc/commFunc/getGeoByIp?ip=%s",aliyunHost, comnfuncPort, ip);

      return content;

    }
// 计算sn跟参数对出现顺序有关，get请求请使用LinkedHashMap保存<key,value>，该方法根据key的插入顺序排序；post请使用TreeMap保存<key,value>，该方法会自动将key按照字母a-z顺序排序。所以get请求可自定义参数顺序（sn参数必须在最后）发送请求，但是post请求必须按照字母a-z顺序填充body（sn参数必须在最后）。以get请求为例：http://api.map.baidu.com/geocoder/v3/?address=百度大厦&output=json&ak=yourak，paramsMap中先放入address，再放output，然后放ak，放入顺序必须跟get请求中对应参数的出现顺序保持一致。

    Map paramsMap = new LinkedHashMap<String, String>();
    paramsMap.put("ip", ip);
//    paramsMap.put("output", "json");
    paramsMap.put("coor","bd09ll");
    paramsMap.put("ak", baiduAppAK);

    // 调用下面的toQueryString方法，对LinkedHashMap内所有value作utf8编码，拼接返回结果address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourak
    String paramsStr = toQueryString(paramsMap);

    // 对paramsStr前面拼接上/geocoder/v2/?，后面直接拼接yoursk得到/geocoder/v2/?address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourakyoursk
    String wholeStr = new String("/location/ip?" + paramsStr + baiduAppSK);

    // 对上面wholeStr再作utf8编码
    String tempStr = URLEncoder.encode(wholeStr, "UTF-8");

    // 调用下面的MD5方法得到最后的sn签名7de5a22212ffaa9e326444c75a58f9a0
//    String sn = MD5(tempStr);
//    System.out.println(sn);
    String url = String.format(baiduGeoIp2AddUrl, ip );
    System.out.println(url);

    BaiduResponse resp =
        restTemplate.exchange(url, HttpMethod.GET, null, BaiduResponse.class).getBody();
    System.out.println(gson.toJson(resp));
    return resp.getContent();


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
