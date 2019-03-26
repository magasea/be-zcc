package com.wensheng.zcc.sso.config;

import java.util.Arrays;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    private int accessTokenValidSeconds = 3600;

    private int refreshTokenValidSeconds = 2592000;

    @Value("${spring.security.oauth2.client.registration.amc-admin.client-id}")
    private String amcAdminClientId;


    @Value("${spring.security.oauth2.client.registration.amc-admin.secret}")
    private String amcAdminSecret;


    @Value("${spring.security.oauth2.client.registration.amc-admin.scopes}")
    private String amcAdminScopes;

    @Value("${spring.security.oauth2.client.registration.amc-admin.authorizedGrantTypes}")
    private String amcAdminAuthorizedGrantTypes;

    @Value("${spring.security.oauth2.client.registration.amc-admin.redirectUris}")
    private String amcAdminRedirectUris;
    
    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("sampleClientId").authorizedGrantTypes("implicit").scopes("read", "write", "foo", "bar").autoApprove(false).accessTokenValiditySeconds(3600).redirectUris("http://localhost:8083/","http://localhost:8086/")

                .and().withClient("testImplicitClientId").authorizedGrantTypes("implicit").scopes("read", "write", "foo", "bar").autoApprove(true).redirectUris("xxx")

                .and().withClient(amcAdminClientId).secret(passwordEncoder.encode(amcAdminSecret)).authorizedGrantTypes(amcAdminAuthorizedGrantTypes.split(",")).scopes(amcAdminScopes.split(",")).autoApprove(false).redirectUris(amcAdminRedirectUris.split(",")).accessTokenValiditySeconds(accessTokenValidSeconds).refreshTokenValiditySeconds(refreshTokenValidSeconds)
        ;

    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    @Bean
    public DefaultTokenServices tokenWechatServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));
        endpoints.tokenStore(tokenStore()).tokenEnhancer(tokenEnhancerChain).authenticationManager(authenticationManager);
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("123");
        // final KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("mytest.jks"), "mypass".toCharArray());
        // converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mytest"));
        return converter;
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }

    @Bean
    public TokenEnhancer wechatTokenEnhancer(){ return new WechatTokenEnhancer(); }


}
