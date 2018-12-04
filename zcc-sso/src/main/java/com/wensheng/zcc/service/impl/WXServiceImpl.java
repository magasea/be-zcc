package com.wensheng.zcc.service.impl;


import com.wensheng.zcc.dao.ZCCUserDao;
import com.wensheng.zcc.model.ZccMiniappUser;
import com.wensheng.zcc.service.WXService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;


@Service(value = "wxServiceImpl")
public class WXServiceImpl implements WXService {
    Logger logger = LoggerFactory.getLogger(getClass());
    private RestTemplate restTemplate = new RestTemplate();


    @Autowired
    Gson gson = new Gson();

    @Autowired
    ZCCUserDao zccUserDao;


    @Value("${weixin.loginUrl}")
    String wxLoginUrl;


    @Value("${weixin.appSec}")
    String appSec;

    @Value("${weixin.appId}")
    String appId;


    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loginWX(String code) throws Exception {
        MyGsonHttpMessageConverter myGsonHttpMessageConverter = new MyGsonHttpMessageConverter();

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(myGsonHttpMessageConverter);
        restTemplate.setMessageConverters(messageConverters);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(wxLoginUrl)
                .queryParam("appid", appId)
                .queryParam("secret", appSec)
                .queryParam("js_code", code)
                .queryParam("grant_type", "authorization_code");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        Object response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                Map.class);
        System.out.println(((ResponseEntity<Map>) response).getBody().toString());
        Map<String, String> respMap = ((ResponseEntity<Map>) response).getBody();

        if(!StringUtils.isEmpty(respMap.get("errcode"))){
            logger.error("Failed to get vaild openId with "+ code +" with hint:"+ respMap.get("errmsg"));
            throw new Exception(respMap.toString());
        }

        ZccMiniappUser zccMiniappUser =  zccUserDao.findByWechatId(respMap.get("openid"));
        if (zccMiniappUser == null){
            logger.info("need to insert the user");
            zccMiniappUser = new ZccMiniappUser();
            zccMiniappUser.setWechatId(respMap.get("openid"));
            zccMiniappUser.setUsername(respMap.get("openid"));
            zccMiniappUser.setPassword(bcryptEncoder.encode(respMap.get("openid")));
            zccUserDao.save(zccMiniappUser);

        }else{
            zccMiniappUser.setInitcode(code);
            zccUserDao.save(zccMiniappUser);
        }
        return new org.springframework.security.core.userdetails.User(respMap.get("openid"), zccMiniappUser.getPassword(), getWechatClientAuthority());



    }

    @Override
    public UserDetails getByInitCode(String code) {
        ZccMiniappUser user = zccUserDao.findByInitcode(code);
        return new org.springframework.security.core.userdetails.User(user.getWechatId(), user.getWechatId(), getWechatClientAuthority());
    }




    public UserDetails loadUserByWechatId(String wechatId) throws UsernameNotFoundException {
        ZccMiniappUser user = zccUserDao.findByWechatId(wechatId);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getWechatId(), user.getWechatId(), getWechatClientAuthority());
    }

    private List<SimpleGrantedAuthority> getWechatClientAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_CLIENT"));
    }




    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        ZccMiniappUser user = zccUserDao.findByUsername(userName);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getWechatClientAuthority());
    }

    class MyGsonHttpMessageConverter extends GsonHttpMessageConverter {
        public MyGsonHttpMessageConverter() {
            List<MediaType> types = Arrays.asList(
                    new MediaType("text", "html", DEFAULT_CHARSET),
                    new MediaType("application", "json", DEFAULT_CHARSET),
                    new MediaType("application", "*+json", DEFAULT_CHARSET),
                    new MediaType("text", "plain", DEFAULT_CHARSET)
            );
            super.setSupportedMediaTypes(types);
        }
    }
}
