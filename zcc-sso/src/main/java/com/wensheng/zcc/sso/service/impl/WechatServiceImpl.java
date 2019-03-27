package com.wensheng.zcc.sso.service.impl;

import com.google.gson.Gson;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.sso.dao.mysql.mapper.AmcWechatUserMapper;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcWechatUser;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcWechatUserExample;
import com.wensheng.zcc.sso.module.helper.AmcPermEnum;
import com.wensheng.zcc.sso.module.helper.AmcRolesEnum;
import com.wensheng.zcc.sso.module.vo.WechatCode2SessionVo;
import com.wensheng.zcc.sso.module.vo.WechatLoginResult;
import com.wensheng.zcc.sso.module.vo.WechatPhoneRegistry;
import com.wensheng.zcc.sso.service.WechatService;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
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
public class WechatServiceImpl implements WechatService {

  @Value("${weixin.loginUrl}")
  String loginUrl;


  @Value("${spring.security.oauth2.client.registration.amc-client-thirdpart.client-id}")
  private String amcWechatClientId;

  @Value("${spring.security.oauth2.client.registration.amc-client-thirdpart.secret}")
  private String amcWechatSecret;


  @Value("${spring.security.oauth2.client.registration.amc-client-thirdpart.scopes}")
  private String amcWechatScopes;

  @Value("${spring.security.oauth2.client.registration.amc-client-thirdpart.authorizedGrantTypes}")
  private String amcWechatAuthorizedGrantTypes;

  @Value("${spring.security.oauth2.client.registration.amc-client-thirdpart.redirectUris}")
  private String amcWechatRedirectUris;


  @Value("${weixin.appId}")
  String appId;

  @Value("${weixin.appSecret}")
  String appSecret;

  private final int accessTokenValidSeconds = 30*24*60*60;

  private RestTemplate restTemplate = new RestTemplate();

  private Gson gson = new Gson();

  @Autowired
  AmcWechatUserMapper amcWechatUserMapper;

  @Autowired
  DefaultTokenServices tokenWechatServices;

  @Autowired
  TokenEnhancer wechatTokenEnhancer;

  @Autowired
  JwtAccessTokenConverter accessTokenConverter;

  public static final int INIT_VECTOR_LENGTH = 16;




  private InMemoryClientDetailsService clientDetailsService = new InMemoryClientDetailsService() ;

  @PostConstruct
  private  void init(){
    List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
    //Add the Jackson Message converter
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    // Note: here we are making this converter to process any kind of response,
    // not only application/*json, which is the default behaviour
    converter.setSupportedMediaTypes(Arrays.asList(new MediaType[]{MediaType.ALL}));
    messageConverters.add(converter);
    restTemplate.setMessageConverters(messageConverters);
    BaseClientDetails baseClientDetails = new BaseClientDetails();
    baseClientDetails.setClientId(amcWechatClientId);
    baseClientDetails.setAccessTokenValiditySeconds(accessTokenValidSeconds);
    if(!StringUtils.isEmpty(amcWechatAuthorizedGrantTypes)){
      baseClientDetails.setAuthorizedGrantTypes(Arrays.stream(amcWechatAuthorizedGrantTypes.split(",")).collect(Collectors.toList()));
    }
    baseClientDetails.setClientSecret(amcWechatSecret);
    if(!StringUtils.isEmpty(amcWechatScopes)){
      baseClientDetails.setScope(Arrays.stream(amcWechatScopes.split(",")).collect(Collectors.toList()));
    }
    Map<String, BaseClientDetails> clientParam = new HashMap<>();
    clientParam.put(amcWechatClientId, baseClientDetails);
//    clientDetailsService.setClientDetailsStore(clientParam);
    tokenWechatServices.setTokenEnhancer(wechatTokenEnhancer);


//    tokenWechatServices.setClientDetailsService(clientDetailsService);
  }

  @Override
  public WechatLoginResult loginWechat(String code) {
    String loginWechatUrl = loginUrl.replace("JSCODE", code);
    ResponseEntity<WechatCode2SessionVo> responseEntity = restTemplate.getForEntity(loginWechatUrl,
        WechatCode2SessionVo.class);
    String info = gson.toJson(responseEntity.getBody());
    log.info(String.format("got response from wechat:%s", info));
    WechatLoginResult wechatLoginResult = new WechatLoginResult();
    wechatLoginResult.setResp(info);
    if(StringUtils.isEmpty(responseEntity.getBody().getErrcode())){
      wechatLoginResult.setOAuth2AccessToken(generateToken(responseEntity.getBody()));
    }
    CUWechatUser((responseEntity.getBody()));
    return wechatLoginResult;
  }

  @Override
  public AmcWechatUser CUWechatUser(WechatCode2SessionVo wechatCode2SessionVo) {
    AmcWechatUserExample amcWechatUserExample = new AmcWechatUserExample();
    if(StringUtils.isEmpty(wechatCode2SessionVo.getSessionKey())){
      return null;
    }

    amcWechatUserExample.createCriteria().andWechatOpenidEqualTo(wechatCode2SessionVo.getOpenid());
    AmcWechatUser amcWechatUser = new AmcWechatUser();

    amcWechatUser.setSessionKey(wechatCode2SessionVo.getSessionKey());
    amcWechatUser.setWechatOpenid(wechatCode2SessionVo.getOpenid());
    amcWechatUser.setWechatUnionId(wechatCode2SessionVo.getUnionid());
    List<AmcWechatUser> amcWechatUsersHistory =  amcWechatUserMapper.selectByExample(amcWechatUserExample);
    if(CollectionUtils.isEmpty(amcWechatUsersHistory)){
      amcWechatUserMapper.insertSelective(amcWechatUser);

    }else{
      if(amcWechatUsersHistory.get(0).getSessionKey().equals(wechatCode2SessionVo.getSessionKey())){
        log.info(String.format("session key:%s not changed", wechatCode2SessionVo.getSessionKey()));
        return amcWechatUsersHistory.get(0);
      }
      amcWechatUserMapper.updateByExampleSelective(amcWechatUser, amcWechatUserExample);

    }
    return amcWechatUser;
  }

  @Override
  public String registryPhone(WechatPhoneRegistry wechatPhoneRegistry) {
    AmcWechatUserExample amcWechatUserExample = new AmcWechatUserExample();
    amcWechatUserExample.createCriteria().andWechatOpenidEqualTo(wechatPhoneRegistry.getOpenId());
    List<AmcWechatUser> amcWechatUsers = amcWechatUserMapper.selectByExample(amcWechatUserExample);
    if(!CollectionUtils.isEmpty(amcWechatUsers)){
      String sessionKey = amcWechatUsers.get(0).getSessionKey();
      String phoneNumber = decodePhone(wechatPhoneRegistry.getEncryptedData(), wechatPhoneRegistry.getIv(),
          sessionKey);
      PhoneObject phoneObject = null;
      if(!StringUtils.isEmpty(phoneNumber)){
        phoneObject =   gson.fromJson(phoneNumber, PhoneObject.class);
        amcWechatUsers.get(0).setPhoneNumber(String.format("%s-%s",phoneObject.countryCode,phoneObject.phoneNumber));
        amcWechatUserMapper.updateByPrimaryKeySelective(amcWechatUsers.get(0));
      }
      return String.format("%s-%s",phoneObject.countryCode,phoneObject.phoneNumber);
    }

    return null;
  }

  private OAuth2AccessToken generateToken(WechatCode2SessionVo wechatCode2SessionVo){
    HashMap<String, String> authorizationParameters = new HashMap<String, String>();
    authorizationParameters.put("scope", amcWechatScopes);
    authorizationParameters.put("username", wechatCode2SessionVo.getOpenid());
    authorizationParameters.put("client_id", amcWechatClientId);
    authorizationParameters.put("grant", amcWechatAuthorizedGrantTypes);
    List<String> scopes = new ArrayList<>();
    if(!StringUtils.isEmpty(amcWechatScopes)){
      Arrays.stream(amcWechatScopes.split(",")).forEach(item -> scopes.add(item.trim()));
    }
    Set<String> scopesSet = scopes.stream().collect(Collectors.toSet());
    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(AmcRolesEnum.ROLE_ZCC_CLIENT.name()));

    authorities.add(new SimpleGrantedAuthority(AmcPermEnum.PERM_AMC_VIEW.name()));


    OAuth2Request authorizationRequest = new OAuth2Request(authorizationParameters, amcWechatClientId, authorities,true, scopesSet, null,
        amcWechatRedirectUris, null, null);


    // Create principal and auth token
    User userPrincipal = new User(wechatCode2SessionVo.getOpenid(), wechatCode2SessionVo.getSessionKey(), true, true, true, true,
        authorities);
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userPrincipal,
        wechatCode2SessionVo.getSessionKey(), authorities) ;

    OAuth2Authentication auth2Authentication = new OAuth2Authentication(authorizationRequest, authenticationToken);


    OAuth2AccessToken token = tokenWechatServices.createAccessToken(auth2Authentication);
    token = accessTokenConverter.enhance(token, auth2Authentication);

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
      ex.printStackTrace();
    }


    return null;
  }
  @Data
  private class PhoneObject{
    String phoneNumber;
    String countryCode;

  }
}
