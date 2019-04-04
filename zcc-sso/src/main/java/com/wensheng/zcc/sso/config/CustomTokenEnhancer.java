package com.wensheng.zcc.sso.config;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUser;
import com.wensheng.zcc.sso.service.AmcUserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.util.CollectionUtils;

public class CustomTokenEnhancer implements TokenEnhancer {

    @Autowired
    AmcUserService amcUserService;


    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        final Map<String, Object> additionalInfo = new HashMap<>();
        List<AmcUser> amcUserList = amcUserService.getAmcUserByPhoneNum(authentication.getName());
        if(!CollectionUtils.isEmpty(amcUserList)){
            additionalInfo.put("nickName", amcUserList.get(0).getNickName());
            additionalInfo.put("title", amcUserList.get(0).getTitle());
            additionalInfo.put("username", amcUserList.get(0).getUserName());
            additionalInfo.put("userId", amcUserList.get(0).getId());
        }
        additionalInfo.put("organization", authentication.getName() + randomAlphabetic(4));

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}
