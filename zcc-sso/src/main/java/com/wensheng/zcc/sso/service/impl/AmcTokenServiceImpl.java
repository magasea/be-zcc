package com.wensheng.zcc.sso.service.impl;

import com.wensheng.zcc.sso.service.AmcTokenService;
import java.util.Collection;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

@Service
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
}
