package com.wensheng.zcc.comnfunc.service.impl;

import com.wensheng.zcc.comnfunc.service.GaoDeService;
import java.util.Map;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class GaoDeServiceImpl implements GaoDeService {

//  public boolean getAddressFromGeoPoint(WXUserGeoRecord wxUserGeoRecord){
//    Point geoJson = (Point) wxUserGeoRecord.getLocation();
//
//    String geoStr = String.format("%s,%s",geoJson.getX(), geoJson.getY());
//    String url = String.format(geoCoderReverseUrl, geoStr);
//    HttpHeaders headers = new HttpHeaders();
//    HttpEntity<Map> httpEntity = new HttpEntity<>(headers);
//    ResponseEntity<GaoDeReGeoResult> resp =  restTemplate.exchange(url, HttpMethod.POST, httpEntity,
//        GaoDeReGeoResult.class);
//
//    GaoDeReGeoResult gaoDeReGeoResult = resp.getBody();
//    ReGeoCode reGeoCode = gaoDeReGeoResult.getReGeoCode();
//    wxUserGeoRecord.setAddress(reGeoCode.getFormattedAddress());
//    if(!CollectionUtils.isEmpty(reGeoCode.getAddressComponent().getCity() )){
//      wxUserGeoRecord.setCity(reGeoCode.getAddressComponent().getCity().get(0));
//    }else{
//      wxUserGeoRecord.setCity(reGeoCode.getAddressComponent().getProvince());
//    }
//    wxUserGeoRecord.setProvince(reGeoCode.getAddressComponent().getProvince());
//
////    ResponseEntity<Map> respMap =  restTemplate.exchange(url, HttpMethod.POST, httpEntity,
////        Map.class);
////    System.out.println(respMap.toString());
////
//    return true;
//  }


}
