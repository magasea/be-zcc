package com.wensheng.zcc.comnfunc.service.impl;

import com.wensheng.zcc.comnfunc.module.dto.AmcRegion;
import com.wensheng.zcc.comnfunc.module.dto.AmcRegionItem;
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
}
