package com.wensheng.zcc.sso.config;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import com.wensheng.zcc.common.module.amc.vo.AmcUserDetail;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

@Slf4j
public class CustomTokenEnhancer implements TokenEnhancer {



    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        final Map<String, Object> additionalInfo = new HashMap<>();
        AmcUserDetail amcUserDetail = (AmcUserDetail) authentication.getPrincipal();
//        List<AmcUser> amcUserList = amcUserService.getAmcUserByPhoneNum(authentication.getName());
//        if(!CollectionUtils.isEmpty(amcUserList)){
        additionalInfo.put("nickName", amcUserDetail.getNickName());
        additionalInfo.put("title", amcUserDetail.getTitle());
        additionalInfo.put("username", amcUserDetail.getUserName());
        additionalInfo.put("userId", amcUserDetail.getId());
        additionalInfo.put("ssoUserId", amcUserDetail.getSsoUserId());
        additionalInfo.put("location", amcUserDetail.getLocation());
        additionalInfo.put("deptId", amcUserDetail.getDeptId());
        additionalInfo.put("cmpyId", amcUserDetail.getCompanyId());
        log.info("cmpyId:{}", amcUserDetail.getCompanyId());
//        }

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}
