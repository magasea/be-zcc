package com.wensheng.zcc.comnfunc.service.impl;

import com.wensheng.zcc.comnfunc.module.dto.AmcRegion;
import com.wensheng.zcc.common.module.dto.AmcRegionInfo;
import com.wensheng.zcc.comnfunc.module.dto.AmcRegionItem;
import com.wensheng.zcc.comnfunc.module.dto.BaiduResponse;
import com.wensheng.zcc.comnfunc.module.dto.Response;
import com.wensheng.zcc.comnfunc.service.RegionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
@Service
public class RegionServiceImpl implements RegionService {

    private RestTemplate restTemplate = null;

    @Value("${amc.urls.getRegion}")
    String getRegionUrl;

    @Value("${amc.urls.getGeoInfo}")
    String getGeoInfoUrl;

    @PostConstruct
    void init(){
        restTemplate = new RestTemplate();
        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        gsonHttpMessageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL) );
        restTemplate.getMessageConverters().removeIf(item -> item instanceof MappingJackson2HttpMessageConverter);
        restTemplate.getMessageConverters().removeIf(item -> item instanceof MappingJackson2XmlHttpMessageConverter);
        restTemplate.getMessageConverters().add(gsonHttpMessageConverter);
    }


    @Override
    public List<AmcRegionItem> getRegionByName(String regName) throws Exception {
        String url = String.format(getRegionUrl, regName);
        AmcRegion resp = restTemplate.getForEntity(url, AmcRegion.class).getBody();

        return Arrays.asList(resp.getData());
    }

    @Override
    public AmcRegionInfo getRegionInfoByLngLat(Double lng, Double lat) {
        String url = String.format(getGeoInfoUrl, String.format("%s,%s", lng,lat));

        Response resp = restTemplate.getForEntity(url, Response.class).getBody();
        AmcRegionInfo amcRegionInfo = new AmcRegionInfo();
        amcRegionInfo.setProvName(resp.getResult().getStatsResult().getProvince().get(1));
        amcRegionInfo.setProvCode(resp.getResult().getStatsResult().getProvince().get(0));
        amcRegionInfo.setCityName(resp.getResult().getStatsResult().getCity().get(1));
        amcRegionInfo.setCityCode(resp.getResult().getStatsResult().getCity().get(0));
        return amcRegionInfo;
    }
}
