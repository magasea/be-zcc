package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.service.RegionService;
import com.wensheng.zcc.common.module.dto.Region;
import com.wensheng.zcc.common.params.AmcPage;
import com.wensheng.zcc.common.params.sso.SSOAmcUser;
import com.wensheng.zcc.common.utils.sso.SSOQueryParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
@Service
public class RegionServiceImpl implements RegionService {
    private RestTemplate restTemplate = new RestTemplate();

    @Value("${cust.getRegionByNameUrl}")
    String getRegionByNameUrl;


    @Value("${cust.getRegionByIdUrl}")
    String getRegionByIdUrl;

    @PostConstruct
    void init(){
        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        gsonHttpMessageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL) );
        restTemplate.getMessageConverters().removeIf(item -> item instanceof MappingJackson2HttpMessageConverter);
        restTemplate.getMessageConverters().removeIf(item -> item instanceof MappingJackson2XmlHttpMessageConverter);
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        restTemplate.getMessageConverters().add(gsonHttpMessageConverter);
    }

    @Override
    public List<Region> getRegionByName(String regionName) {
        HttpHeaders headers = getHttpJsonHeader();

        HttpEntity<String> entity = new HttpEntity<>(regionName, headers);

        ResponseEntity response = restTemplate.exchange(getRegionByNameUrl, HttpMethod.POST, entity,
                new ParameterizedTypeReference<List<Region>>() {} );
        List<Region> resp = (List<Region>) response.getBody();

        return resp;
    }

    @Override
    public Region getRegionById(Long regionId) {
        HttpHeaders headers = getHttpJsonHeader();

        HttpEntity<Long> entity = new HttpEntity<>(regionId, headers);

        ResponseEntity response = restTemplate.exchange(getRegionByIdUrl, HttpMethod.POST, entity,
                Region.class);
        Region resp = (Region) response.getBody();

        return resp;
    }

    public HttpHeaders getHttpJsonHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.getAccept().clear();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }
}
