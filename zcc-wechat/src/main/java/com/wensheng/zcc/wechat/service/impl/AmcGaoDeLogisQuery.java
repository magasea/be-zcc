package com.wensheng.zcc.wechat.service.impl;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.wensheng.zcc.wechat.module.dao.mongo.WXUserGeoRecord;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
public class AmcGaoDeLogisQuery {
  @Value("${gaode.geocoder-reverse}")
  private String geoCoderReverseUrl;


  private RestTemplate restTemplate  = new RestTemplate();

  @Autowired
  MongoTemplate mongoTemplate;

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

//    ResponseEntity<Map> respMap =  restTemplate.exchange(url, HttpMethod.POST, httpEntity,
//        Map.class);
//    System.out.println(respMap.toString());
//
    return true;
  }

  public void updateGeoAddressOfUser(){
    Query query = new Query();
    query.addCriteria(Criteria.where("address").is(null));
    List<WXUserGeoRecord> wxUserGeoRecordList = mongoTemplate.find(query, WXUserGeoRecord.class);
    for(WXUserGeoRecord wxUserGeoRecord: wxUserGeoRecordList){
      if(getAddressFromGeoPoint(wxUserGeoRecord)){
        wxUserGeoRecord.setUpdateTime(LocalDate.ofInstant(Instant.now(), ZoneOffset.UTC));
        mongoTemplate.save(wxUserGeoRecord);
      }
    }
  }

  @Data
  class GaoDeReGeoResult{
    Integer status;
    String info;
    @SerializedName("infocode")
    String infoCode;
    @SerializedName("regeocode")
    ReGeoCode reGeoCode;

  }

  @Data
  class ReGeoCode{
    @SerializedName("formatted_address")
    String formattedAddress;
    AddressComponent addressComponent;
  }

  @Data
  class AddressComponent{
    String country;
    String province;
    List<String> city;
    String citycode;
    String district;
    String adcode;
    String township;
    String towncode;
    StreetNumber streetNumber;
    List<BusinessArea> businessAreas;
    Building building;
    Neighborhood neighborhood;
  }
  @Data
  class StreetNumber{
    String number;
    String location;
    String direction;
    Double distance;
    String street;
  }
  @Data
  class BusinessArea{
    String location;
    String name;
    Long id;
  }
  @Data
  class Building{
    List<String> name;
    List<Integer> type;
  }

  @Data
  class Neighborhood{
    List<String> name;
    List<Integer> type;
  }

}
