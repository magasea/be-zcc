package com.wensheng.zcc.sso.service.impl;

import com.google.api.Page;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wensheng.zcc.common.params.AmcPage;
import com.wensheng.zcc.common.params.AmcRolesEnum;
import com.wensheng.zcc.common.params.PageInfo;
import com.wensheng.zcc.common.params.sso.AmcDeptEnum;
import com.wensheng.zcc.common.params.sso.AmcSSOTitleEnum;
import com.wensheng.zcc.common.params.sso.AmcUserValidEnum;
import com.wensheng.zcc.common.params.sso.SSOAmcUser;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import com.wensheng.zcc.common.utils.sso.SSOQueryParam;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUser;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUserRole;
import com.wensheng.zcc.sso.module.vo.WechatCode2SessionVo;
import com.wensheng.zcc.sso.service.AmcSsoService;
import com.wensheng.zcc.sso.service.AmcUserService;
import com.wensheng.zcc.sso.service.KafkaService;
import com.wensheng.zcc.sso.service.UserService;
import com.wensheng.zcc.sso.service.util.JwtTokenUtil;
import com.wensheng.zcc.sso.service.util.UserUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * @author chenwei on 3/19/19
 * @project miniapp-backend
 */
@Service
@Slf4j
public class AmcSsoServiceImpl implements AmcSsoService {

  @Value("${weixin.loginUrl}")
  String loginUrl;

  @Value("${weixin.open.loginUrl}")
  String loginOpenUrl;

  @Value("${weixin.open.getUserInfoUrl}")
  String getUserInfoUrl;

  @Value("${spring.security.oauth2.client.registration.amc-client.client-id}")
  private String amcClientId;

  @Value("${spring.security.oauth2.client.registration.amc-client.secret}")
  private String amcSecret;


  @Value("${spring.security.oauth2.client.registration.amc-client.scopes}")
  private String amcScopes;

  @Value("${spring.security.oauth2.client.registration.amc-client.authorizedGrantTypes}")
  private String amcAuthorizedGrantTypes;

  @Value("${spring.security.oauth2.client.registration.amc-client.redirectUris}")
  private String amcRedirectUris;


  @Value("${weixin.appId}")
  String appId;

  @Value("${weixin.appSecret}")
  String appSecret;

  @Autowired
  KafkaService kafkaService;

  @Autowired
  SecretService secretService;

  @Autowired
  JwtTokenUtil jwtTokenUtil;

  private final int accessTokenValidSeconds = 365*24*60*60;


  private int refreshTokenValidSeconds = 2592000;

  private RestTemplate restTemplate = new RestTemplate();

  private Gson gson = new Gson();




  @Autowired
  UserService userService;

  @Autowired
  TokenStore tokenStore;

  @Autowired
  DefaultTokenServices tokenServices ;

  @Autowired
  TokenEnhancer tokenEnhancer;

  @Autowired
  JwtAccessTokenConverter accessTokenConverter;

  @Autowired
  AmcUserService amcUserService;

  @Value("${sso.url}")
  String ssoUrl;

  @Autowired
  JwtAccessTokenConverter jwtAccessTokenConverter;


  public static final int INIT_VECTOR_LENGTH = 16;




  private InMemoryClientDetailsService clientDetailsService = new InMemoryClientDetailsService() ;

  @PostConstruct
  private  void init(){
    GsonBuilder gson = new GsonBuilder();
    restTemplate.getMessageConverters().removeIf(item -> item instanceof MappingJackson2HttpMessageConverter);
    restTemplate.getMessageConverters().removeIf(item -> item instanceof MappingJackson2XmlHttpMessageConverter);
    GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
    gsonHttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON));

    gsonHttpMessageConverter.setGson(gson.create());

    restTemplate.getMessageConverters().add(gsonHttpMessageConverter);
    BaseClientDetails baseClientDetails = new BaseClientDetails();
    baseClientDetails.setClientId(amcClientId);
    baseClientDetails.setAccessTokenValiditySeconds(accessTokenValidSeconds);
    if(!StringUtils.isEmpty(amcAuthorizedGrantTypes)){
      baseClientDetails.setAuthorizedGrantTypes(Arrays.stream(amcAuthorizedGrantTypes.split(",")).collect(Collectors.toList()));
    }
    baseClientDetails.setClientSecret(amcSecret);
    if(!StringUtils.isEmpty(amcScopes)){
      baseClientDetails.setScope(Arrays.stream(amcScopes.split(",")).collect(Collectors.toList()));
    }
    Map<String, BaseClientDetails> clientParam = new HashMap<>();
    clientParam.put(amcClientId, baseClientDetails);
//    clientDetailsService.setClientDetailsStore(clientParam);
//    final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
//    tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer, accessTokenConverter));




//    tokenWechatServices.setClientDetailsService(clientDetailsService);
  }



  private OAuth2AccessToken generateTokenByUserId(Long userId){
    HashMap<String, String> authorizationParameters = new HashMap<String, String>();
    authorizationParameters.put("scope", amcScopes);
    UserDetails userDetails = userService.loadUserByUserId(userId);

    authorizationParameters.put("client_id", amcClientId);
    authorizationParameters.put("grant", amcAuthorizedGrantTypes);

    List<String> scopes = new ArrayList<>();
    if(!StringUtils.isEmpty(amcScopes)){
      Arrays.stream(amcScopes.split(",")).forEach(item -> scopes.add(item.trim()));
    }
    Set<String> scopesSet = scopes.stream().collect(Collectors.toSet());

    OAuth2Request authorizationRequest = new OAuth2Request(authorizationParameters, amcClientId, userDetails.getAuthorities(),true,
        scopesSet,
        null,
        amcRedirectUris, null, null);



    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
        null, userDetails.getAuthorities()) ;

    OAuth2Authentication auth2Authentication = new OAuth2Authentication(authorizationRequest, authenticationToken);


    OAuth2AccessToken token = tokenServices.createAccessToken(auth2Authentication);
//    token = accessTokenConverter.enhance(token, auth2Authentication);

    return token;
  }


  private OAuth2AccessToken generateTokenByMobilephone(String mobilephone){
    HashMap<String, String> authorizationParameters = new HashMap<String, String>();
    authorizationParameters.put("scope", amcScopes);
    UserDetails userDetails = userService.loadUserByUserPhone(mobilephone);

    authorizationParameters.put("client_id", amcClientId);
    authorizationParameters.put("grant", amcAuthorizedGrantTypes);

    List<String> scopes = new ArrayList<>();
    if(!StringUtils.isEmpty(amcScopes)){
      Arrays.stream(amcScopes.split(",")).forEach(item -> scopes.add(item.trim()));
    }
    Set<String> scopesSet = scopes.stream().collect(Collectors.toSet());

    OAuth2Request authorizationRequest = new OAuth2Request(authorizationParameters, amcClientId, userDetails.getAuthorities(),true,
        scopesSet,
        null,
        amcRedirectUris, null, null);



    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
        null, userDetails.getAuthorities()) ;

    OAuth2Authentication auth2Authentication = new OAuth2Authentication(authorizationRequest, authenticationToken);


    OAuth2AccessToken token = tokenServices.createAccessToken(auth2Authentication);
//    token = accessTokenConverter.enhance(token, auth2Authentication);

    return token;
  }




  public String decodePhone(String encryptedData, String iv, String sessionKey){
    try {
      byte[] sessionKeyBytes = Base64.decode(sessionKey);
      byte[] encryptedDataBytes = Base64.decode(encryptedData);
      byte[] ivBytes =  Base64.decode(iv);

      Security.addProvider(new BouncyCastleProvider());


      SecretKeySpec skeySpec = new SecretKeySpec(sessionKeyBytes, "AES");
      AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
      parameters.init(new IvParameterSpec(ivBytes, 0, INIT_VECTOR_LENGTH));
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
      cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(ivBytes, 0, INIT_VECTOR_LENGTH));

      byte[] decrypted = cipher.doFinal(encryptedDataBytes);
      if (null != decrypted && decrypted.length > 0) {
        String result = new String(decrypted, "UTF-8");
        return  result;
      }
    } catch (Exception ex) {
      log.error("Failed to decode phoneNumber", ex);
      ex.printStackTrace();
    }


    return null;
  }

  @Override
  public OAuth2AccessToken generateToken(Long userId) {
    return generateTokenByUserId(userId);
  }

  @Override
  public OAuth2AccessToken generateToken(String mobilePhone) {
    return generateTokenByMobilephone(mobilePhone);
  }

  @Override
  public UserDetails getUserDetailByUserId(Long userId) {
    UserDetails userDetails = userService.loadUserByUserId(userId);
    return userDetails;
  }

  @Override
  public UserDetails getUserDetailByUserPhone(String phoneNum) {
    return null;
  }

  @Override
  public boolean syncUserWithSSO() {
    SSOQueryParam ssoQueryParam = new SSOQueryParam();
    PageInfo pageInfo = new PageInfo();
    int pageNum = 1;
    int pageSize = 20;
    while(pageNum > 0){
      pageInfo.setPage(pageNum);
      pageInfo.setSize(pageSize);
      HttpHeaders headers = getHttpJsonHeader();
      ssoQueryParam.setPageInfo(pageInfo);

      HttpEntity<SSOQueryParam> entity = new HttpEntity<>(ssoQueryParam, headers);

      ResponseEntity response = restTemplate.exchange(ssoUrl, HttpMethod.POST, entity,
          new ParameterizedTypeReference<AmcPage<SSOAmcUser>>() {} );
      AmcPage<SSOAmcUser> resp = (AmcPage<SSOAmcUser>) response.getBody();
      if(!CollectionUtils.isEmpty(resp.getContent())){
        pageNum = pageNum + 1;
      }else{
        pageNum  = -1 ;
        break;
      }

      amcUserService.updateOrInsertSSOUser(resp.getContent());
    }


    return false;
  }

  @Override
  public OAuth2AccessToken generateTokenFromToken(String acccessToken) throws Exception {
//    String token = acccessToken.replace("[", "");
//    token = token.replace("]", "");
//    DefaultClaims claims = (DefaultClaims) jwtTokenUtil.getAllClaimsFromToken(token);
////    OAuth2Authentication oAuth2Authentication = tokenServices.loadAuthentication(acccessToken);
//    Map<String, Object> detailsParam =
//        new HashMap<>();
//    detailsParam.put("mobilephone","");
//    accessTokenConverter.getAccessTokenConverter().extractAccessToken(acccessToken, detailsParam);
//    if(detailsParam.containsKey("mobilephone") && null != detailsParam.get("mobilephone")) {
//      String mobilephone = (String) detailsParam.get("mobilephone");
//      OAuth2AccessToken oauthAccessToken = generateToken(mobilephone);
//      return oauthAccessToken;
//    }else{
//      throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_SSO_TOKEN, "no mobilephone there");
//    }

    Jws<Claims> jws = Jwts.parser()
        .setSigningKeyResolver(secretService.getSigningKeyResolver())
        .parseClaimsJws(acccessToken);

    DefaultClaims defaultClaims = (DefaultClaims) jws.getBody();
    if(defaultClaims.containsKey("mobilephone")){
      String mobilephone = (String)defaultClaims.get("mobilephone");
      OAuth2AccessToken oauthAccessToken = null;
//      try{
//        oauthAccessToken = generateToken(mobilephone);
//      }catch (Exception ex){
//        log.error("failed to generateToken", ex);
//      }
      if(oauthAccessToken == null && defaultClaims.containsKey("deptId") && defaultClaims.containsKey("title")
          && defaultClaims.containsKey("location") && defaultClaims.containsKey("lgroup")){
        if( (Integer)defaultClaims.get("deptId") > 0 && (Integer)defaultClaims.get("title") > 0){
          createUserFromSSO(defaultClaims);
          oauthAccessToken = generateToken(mobilephone);
        }
      }
      return oauthAccessToken;
    }
    throw new BadCredentialsException("this token is not valid");


  }

  private boolean createUserFromSSO(DefaultClaims defaultClaims){
    //create the user from sso
    //1. create user
    //2. find the match role
    //3. get token for this user

    AmcUser amcUser = new AmcUser();
    amcUser.setUserName((String)defaultClaims.get("username"));
    amcUser.setUserCname((String)defaultClaims.get("usercname"));
    amcUser.setMobilePhone((String)defaultClaims.get("mobilephone"));
    amcUser.setDeptId(Long.valueOf((Integer)defaultClaims.get("deptId")));
    amcUser.setCompanyId(Long.valueOf((Integer)defaultClaims.get("cmpyid")));
    amcUser.setTitle((Integer)defaultClaims.get("title"));
    amcUser.setLocation((Integer)defaultClaims.get("location"));
    amcUser.setLgroup((Integer)defaultClaims.get("lgroup"));
    amcUser.setNickName((String)defaultClaims.get("nickName"));
    amcUser.setValid(AmcUserValidEnum.VALID.getId());
    AmcUser result = amcUserService.createUser(amcUser);

    if(null == result){
      return false;
    }

    return true;
  }

  @Data
  private class PhoneObject{
    String phoneNumber;
    String countryCode;

  }

  public HttpHeaders getHttpJsonHeader(){
    HttpHeaders headers = new HttpHeaders();
    headers.getAccept().clear();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    return headers;
  }
}
