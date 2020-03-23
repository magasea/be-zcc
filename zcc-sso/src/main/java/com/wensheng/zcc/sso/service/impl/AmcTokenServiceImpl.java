package com.wensheng.zcc.sso.service.impl;

import com.wensheng.zcc.sso.service.AmcTokenService;
import java.util.Collection;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AmcTokenServiceImpl implements AmcTokenService {


  @Resource(name = "tokenServices")
  private ConsumerTokenServices tokenServices;

  @Resource(name = "tokenStore")
  private TokenStore tokenStore;

  @Value("${spring.security.oauth2.client.registration.amc-client.client-id}")
  private String amcClientId;

  @Override
  public boolean revokeTokenByMobilePhone(String mobilePhone) {
    Collection<OAuth2AccessToken> accessTokens = tokenStore.findTokensByClientIdAndUserName(amcClientId,
        mobilePhone);
    for(OAuth2AccessToken oAuth2AccessToken : accessTokens){
      tokenServices.revokeToken(oAuth2AccessToken.getValue());
    }
    return true;
  }

  @Override
  public boolean revokeTokenAll() {
    Collection<OAuth2AccessToken> accessTokens = tokenStore.findTokensByClientId(amcClientId);
    for(OAuth2AccessToken oAuth2AccessToken : accessTokens){
      tokenServices.revokeToken(oAuth2AccessToken.getValue());
    }
    return true;
  }


  @Scheduled(cron = "${spring.task.scheduling.cronExprTokenRevok}")
  @Override
  public void checkAccessTokens() {

//    for (OAuth2AccessToken oauthAccessToken : oAuth2AccessTokens) {
//      if (oauthAccessToken.isExpired()) {
//        result = tokenServices.revokeToken(oauthAccessToken.getValue());
//        log.info("revoke expired token:{} result:{}", oauthAccessToken.getValue(), result);
//      }
//    }
    Collection<OAuth2AccessToken>  oAuth2AccessTokens = tokenStore.findTokensByClientId(amcClientId);
    for (OAuth2AccessToken oauthAccessToken : oAuth2AccessTokens) {
      if (oauthAccessToken.isExpired()) {
         boolean result = tokenServices.revokeToken(oauthAccessToken.getValue());
        log.info("revoke expired token:{} result:{}", oauthAccessToken.getValue(), result);
      }
    }
  }
}
