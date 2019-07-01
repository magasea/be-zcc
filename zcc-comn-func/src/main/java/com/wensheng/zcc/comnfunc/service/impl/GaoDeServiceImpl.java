package com.wensheng.zcc.comnfunc.service.impl;

import com.google.gson.Gson;
import com.wensheng.zcc.common.module.dto.GaoDeReGeoResult;
import com.wensheng.zcc.common.module.dto.ReGeoCode;
import com.wensheng.zcc.common.module.dto.WXUserGeoRecord;
import com.wensheng.zcc.comnfunc.service.GaoDeService;
import java.util.Collections;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
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
import org.springframework.web.client.RestTemplate;

@Service
public class GaoDeServiceImpl implements GaoDeService {

  @Value("${gaode.geocoder-reverse}")
  private String geoCoderReverseUrl;


  private RestTemplate restTemplate  = new RestTemplate();



  private Gson gson = new Gson();

  @PostConstruct
  void init(){
    GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
    gsonHttpMessageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL) );
    restTemplate.getMessageConverters().removeIf(item -> item instanceof MappingJackson2HttpMessageConverter);
    restTemplate.getMessageConverters().removeIf(item -> item instanceof MappingJackson2XmlHttpMessageConverter);
    restTemplate.getMessageConverters().add(gsonHttpMessageConverter);
  }


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


}
