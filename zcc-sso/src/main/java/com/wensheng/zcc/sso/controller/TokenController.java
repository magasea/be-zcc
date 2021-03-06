package com.wensheng.zcc.sso.controller;

import com.wensheng.zcc.sso.aop.LogExecutionTime;
import com.wensheng.zcc.sso.service.AmcSsoService;
import com.wensheng.zcc.sso.service.util.TokenUtil;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TokenController {

    @Resource(name = "tokenServices")
    private ConsumerTokenServices tokenServices;

    @Resource(name = "tokenStore")
    private TokenStore tokenStore;

    @Value("${spring.security.oauth2.client.registration.amc-admin.client-id}")
    private String amcAdminClientId;

    @Autowired
    AmcSsoService amcSsoService;

    @RequestMapping(method = RequestMethod.POST, value = "/oauth/token/revokeById/{tokenId}")
    @ResponseBody
    public void revokeToken(HttpServletRequest request, @PathVariable String tokenId) {
        tokenServices.revokeToken(tokenId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/oauth/token/revoke")
    @ResponseBody
    public void revokeToken(HttpServletRequest request) {
        String authorization = TokenUtil.extractHeaderToken( request);
        if (authorization != null) {
            tokenServices.revokeToken(authorization);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/oauth/token/revokeByUserId/{userId}")
    @ResponseBody
    public void revokeToken(HttpServletRequest request, @PathVariable Long userId) {
//        tokenServices
//        tokenServices.revokeToken(tokenId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tokens")
    @ResponseBody
    public List<String> getTokens() {
        Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientId(amcAdminClientId);
        return Optional.ofNullable(tokens).orElse(Collections.emptyList()).stream().map(OAuth2AccessToken::getValue).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/tokens/revokeRefreshToken/{tokenId:.*}")
    @ResponseBody
    public String revokeRefreshToken(@PathVariable String tokenId) {
        if (tokenStore instanceof JdbcTokenStore) {
            ((JdbcTokenStore) tokenStore).removeRefreshToken(tokenId);
        }
        return tokenId;
    }

    @RequestMapping(method = RequestMethod.GET, value ="/amc/zcc/sso/getTokenByUserId/{userId}")
    @ResponseBody
    public OAuth2AccessToken getTokenByUserId(@PathVariable Long userId)
    {
        return amcSsoService.generateToken(userId);
    }

    @RequestMapping(method = RequestMethod.GET, value ="/amc/zcc/sso/getTokenByMobilephone/{mobilephone}")
    @ResponseBody
    public OAuth2AccessToken getTokenByUserId(@PathVariable String mobilephone)
    {
        return amcSsoService.generateToken(mobilephone);
    }

  @RequestMapping(method = RequestMethod.POST, value ="/amc/zcc/sso/exchangeTokenFromSSO")
  @ResponseBody
  @LogExecutionTime
  public OAuth2AccessToken getTokenBySSOToken(@RequestParam String acccessToken) throws Exception {
    return amcSsoService.generateTokenFromToken(acccessToken);
  }
}
