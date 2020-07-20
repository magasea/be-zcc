package com.wensheng.zcc.comnfunc.service.impl;

import com.google.gson.Gson;
import com.wensheng.zcc.common.module.LatLng;
import com.wensheng.zcc.common.module.dto.GaoDeReGeoResult;
import com.wensheng.zcc.common.module.dto.ReGeoCode;
import com.wensheng.zcc.common.module.dto.WXUserGeoRecord;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.comnfunc.module.vo.base.GaodeGeoQueryIPResp;
import com.wensheng.zcc.comnfunc.module.vo.base.GaodeGeoQueryResp;
import com.wensheng.zcc.comnfunc.module.vo.base.GaodeGeoQueryVal;
import com.wensheng.zcc.comnfunc.module.vo.base.GaodeRegeoQueryResp;
import com.wensheng.zcc.comnfunc.module.vo.base.GaodeRegeoQueryVal;
import com.wensheng.zcc.comnfunc.service.GaoDeService;
import com.wensheng.zcc.comnfunc.utils.SSLUtil;
import com.wenshengamc.zcc.common.Common.GeoJson;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class GaoDeServiceImpl implements GaoDeService {

  @Value("${gaode.geocoder-reverse}")
  private String geoCoderReverseUrl;

  @Value("${gaode.geocoder}")
  private String geoCoderUrl;

  @Value("${gaode.regeocoder}")
  private String regeoCoderUrl;

  @Value("${gaode.geoIp2Addr}")
  private String geoIp2AddrUrl;


  private RestTemplate restTemplate  = new RestTemplate();




  private Gson gson = new Gson();

  @PostConstruct
  void init() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {


    SSLUtil.turnOffSslChecking();

    GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
    gsonHttpMessageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL) );
    restTemplate.getMessageConverters().removeIf(item -> item instanceof MappingJackson2HttpMessageConverter);
    restTemplate.getMessageConverters().removeIf(item -> item instanceof MappingJackson2XmlHttpMessageConverter);
    restTemplate.getMessageConverters().add(gsonHttpMessageConverter);
  }


  @Override
  public boolean getAddressFromGeoPoint(WXUserGeoRecord wxUserGeoRecord){
    Point geoJson = (Point) wxUserGeoRecord.getLocation();

    String geoStr = String.format("%s,%s",geoJson.getX(), geoJson.getY());
    String url = String.format(geoCoderReverseUrl, geoStr);
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<Map> httpEntity = new HttpEntity<>(headers);
    ResponseEntity<GaoDeReGeoResult> resp =  restTemplate.exchange(url, HttpMethod.POST, httpEntity,
        GaoDeReGeoResult.class);

    GaoDeReGeoResult gaoDeReGeoResult = resp.getBody();
    ReGeoCode reGeoCode = gaoDeReGeoResult.getReGeoCode();
    wxUserGeoRecord.setAddress(reGeoCode.getFormattedAddress());
    if(!CollectionUtils.isEmpty(reGeoCode.getAddressComponent().getCity() )){
      wxUserGeoRecord.setCity(reGeoCode.getAddressComponent().getCity().get(0));
    }else{
      wxUserGeoRecord.setCity(reGeoCode.getAddressComponent().getProvince());
    }
    wxUserGeoRecord.setProvince(reGeoCode.getAddressComponent().getProvince());


//
    return true;
  }

  @Override
  public List<GaodeGeoQueryVal> getGeoInfoFromAddress(String address,  String city) throws Exception {



    StringBuilder geoStr = new StringBuilder( String.format(geoCoderUrl,address));
    if(!StringUtils.isEmpty(city)){
      geoStr.append(String.format("&city=%s", city));
    }
//    ResponseEntity<Object> respTest =  restTemplate.exchange(geoStr.toString(), HttpMethod.GET, null,
//        Object.class);
    GaodeGeoQueryResp gaoDeReGeoResult = null;
    try{
      ResponseEntity<GaodeGeoQueryResp> resp =  restTemplate.exchange(geoStr.toString(), HttpMethod.GET, null,
          GaodeGeoQueryResp.class);

      gaoDeReGeoResult = resp.getBody();
      if(gaoDeReGeoResult == null || gaoDeReGeoResult.getGeocodes() == null || CollectionUtils.isEmpty(gaoDeReGeoResult.getGeocodes())){
        geoStr.setLength(0);
        geoStr.append(String.format(geoCoderUrl,city));
        if(!StringUtils.isEmpty(city)){
          geoStr.append(String.format("&city=%s", city));
        }
        resp =  restTemplate.exchange(geoStr.toString(), HttpMethod.GET, null,
            GaodeGeoQueryResp.class);


        gaoDeReGeoResult = resp.getBody();
      }

    }catch (Exception ex){
      ex.printStackTrace();
      ResponseEntity<String> respStr =  restTemplate.exchange(geoStr.toString(), HttpMethod.GET, null,
          String.class);
      log.error("Failed to get geo info for :{} and city:{} from:{}", address, city, respStr);
      log.error("now use city geo info directlly");

//      throw new Exception(String.format("Failed to get geo info for :%s and city:%s", address, city));
    }finally {
      if(gaoDeReGeoResult == null){
        log.error("failed to get geo info for:{} {}", address, city);
        throw new Exception(String.format("Failed to get geo info for :%s and city:%s", address, city));
      }
    }
    List<GaodeGeoQueryVal> results = new ArrayList(gaoDeReGeoResult.getGeocodes());
    if(CollectionUtils.isEmpty(results)){
      ResponseEntity<String> respStr =  restTemplate.exchange(geoStr.toString(), HttpMethod.GET, null,
          String.class);
      log.error("Failed to get geo info for :{} and city:{} from:{}", address, city, respStr);
      throw new Exception(String.format("Failed to get geo info for :%s and city:%s", address, city));
    }
    System.out.println(results.get(0).getLocation());
    System.out.println(results.get(0).getAdcode());
//    List<GaodeGeoQueryVal> results = new ArrayList();
    return results;


  }

  @Override
  public GaodeRegeoQueryVal getAddressFromGeoPoint(LatLng latLng) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append(latLng.getLat()).append(",").append(latLng.getLng());
    String gaodeUrl = String.format(regeoCoderUrl, sb.toString());

    ResponseEntity<GaodeRegeoQueryResp> resp =  null;
    try{
      resp = restTemplate.exchange(gaodeUrl, HttpMethod.GET, null,
          GaodeRegeoQueryResp.class);
    }catch (Exception ex){
      log.error("Failed to get geo info by:{}", latLng, ex);
      ResponseEntity<String> respStr = restTemplate.exchange(gaodeUrl, HttpMethod.GET, null,
          String.class);
      log.info(respStr.getBody());
    }


    GaodeRegeoQueryVal gaoRegeoResult = resp.getBody().getRegeocode();

    return gaoRegeoResult;
  }

  @Override
  public GaodeGeoQueryIPResp getAddressByIp(String ipadd) {
    String gaodeUrl = String.format(geoIp2AddrUrl, ipadd);

    ResponseEntity<GaodeGeoQueryIPResp> resp = null;
    try{
      resp = restTemplate.exchange(gaodeUrl, HttpMethod.GET, null,
          GaodeGeoQueryIPResp.class);
    }catch (Exception ex){
      log.error("Failed to get address by IP:{}",ipadd);
      ResponseEntity<String> errorResp = restTemplate.exchange(gaodeUrl, HttpMethod.GET, null,
          String.class);
      log.error("Error info:{}", errorResp.getBody());
      return null;
    }


    GaodeGeoQueryIPResp gaoRegeoResult = resp.getBody();

    return gaoRegeoResult;

  }


}
