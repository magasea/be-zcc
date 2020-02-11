package com.wensheng.zcc.amc.config;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.wensheng.zcc.amc.service.UserService;
import com.wensheng.zcc.common.module.amc.vo.AmcUserDetail;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
//@Component
public class CustomFilter extends OAuth2ClientContextFilter {

  @Autowired
  UserService userService;

  RestTemplate restTemplate;
  @Autowired
  JwtAccessTokenConverter accessTokenConverter;

  @Autowired
  TokenStore tokenStore;


  @Value("${sso.ssoUserDetailUrl}")
  String ssoUserDetailUrl;

  @Value("${sso.ssoUserTokenUrl}")
  String ssoUserTokenUrl;

  @PostConstruct
  void init(){
    GsonBuilder gson = new GsonBuilder();
    restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().removeIf(item -> item instanceof MappingJackson2HttpMessageConverter);
    restTemplate.getMessageConverters().removeIf(item -> item instanceof MappingJackson2XmlHttpMessageConverter);
    GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
    gsonHttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON));
    Type collectionStringType = new TypeToken<List<String>>() {}.getType();
// Create gson object
    gson
        .registerTypeAdapter(collectionStringType, new ListFromStringTypeAdapter())
        .create();
//    gson.registerTypeAdapter(GrantedAuthority.class, new SimpleGrantedAuthority("test"));
    gsonHttpMessageConverter.setGson(gson.create());

    restTemplate.getMessageConverters().add(gsonHttpMessageConverter);
  }

  @Override
  public void doFilter(
      ServletRequest request,
      ServletResponse response,
      FilterChain chain) throws IOException, ServletException {
    HttpServletRequest servletRequest = (HttpServletRequest) request;
    HttpServletResponse servletResponse = (HttpServletResponse) response;
    Enumeration<String> headerNames = servletRequest.getHeaderNames();
    if(null != headerNames){
      while(headerNames.hasMoreElements()){
        if(headerNames.nextElement().equals("authorization")){
          String originToken = servletRequest.getHeader("authorization");
          System.out.println(originToken);
          Map<String, String> values = new LinkedHashMap();
          if(StringUtils.isEmpty(originToken) || !originToken.contains(" ") || !originToken.contains("Bearer")){
            break;
          }
          OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(originToken.split(" ")[1]);
              Map<String, Object> detailsParam =
        (Map<String, Object>)  oAuth2Authentication.getDetails();
    if(detailsParam.containsKey("mobilephone") && null != detailsParam.get("mobilephone")){
      String mobilephone = (String) detailsParam.get("mobilephone");
      LocalToken oauthAccessToken = getTokenFromSso((HttpServletRequest)request, mobilephone);
//      System.out.println(oauthAccessToken);
      OAuth2Authentication oAuth2AuthenticationUpdate =
          tokenStore.readAuthentication(oauthAccessToken.getAccessToken());

      SecurityContextHolder.getContext().setAuthentication(oAuth2AuthenticationUpdate);
      servletResponse.setHeader("Authorization", String.format("Bearer :%s", oauthAccessToken.getAccessToken()));
      servletResponse.setHeader("refresh_token", String.format(oauthAccessToken.getRefreshToken()));
    }
        }
      }
    }


//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    if(!StringUtils.isEmpty(authentication.getName()) && authentication.getName().equals("anonymousUser")){
//      chain.doFilter(request, response);
//      return;
//    }
//    Map<String, Object> detailsParam =
//        (Map<String, Object>) ((OAuth2AuthenticationDetails)authentication.getDetails()).getDecodedDetails();
//    if(detailsParam.containsKey("userId") && null != detailsParam.get("userId")){
//      Integer userId = (Integer) detailsParam.get("userId");
//      UserDetails userObject = getUserDetailsFromSso((HttpServletRequest)request, Long.valueOf(userId));
//      Authentication authenticationUpdate = new UsernamePasswordAuthenticationToken(userObject, userObject,
//          userObject.getAuthorities());
//      SecurityContextHolder.getContext().setAuthentication(authenticationUpdate);
//      HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//      OauthAccessToken oauthAccessToken = getTokenFromSso((HttpServletRequest)request, Long.valueOf(userId));
//      httpServletResponse.setHeader("Authorization", String.format("Bearer :%s", oauthAccessToken.getToken()));
//      httpServletResponse.setHeader("refresh_token", String.format(oauthAccessToken.getRefreshToken()));
//
//    }
    chain.doFilter(request, response);
  }

  private UserDetails getUserDetailsFromSso(HttpServletRequest request, Long userId){

    String url = String.format(ssoUserDetailUrl, userId);
    HttpHeaders headers = getHttpJsonHeader(request);

    HttpEntity entity = new HttpEntity(null, headers);
    ResponseEntity<AmcUserDetail> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity,
        AmcUserDetail.class);
    UserDetails resp = responseEntity.getBody();


    return resp;
  }

  private LocalToken getTokenFromSso(HttpServletRequest request, String mobilephone){

    String url = String.format(ssoUserTokenUrl, mobilephone);
    HttpHeaders headers = getHttpJsonHeader(request);

    HttpEntity entity = new HttpEntity(null, headers);
    ResponseEntity<LocalToken> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity,
        LocalToken.class);
    LocalToken resp = responseEntity.getBody();


    return resp;
  }

//  private LocalToken getTokenFromSso(HttpServletRequest request, String mobilephone){
//
//    String url = String.format(ssoUserTokenUrl, mobilephone);
//    HttpHeaders headers = getHttpJsonHeader(request);
//
//    HttpEntity entity = new HttpEntity(null, headers);
//    ResponseEntity<LocalToken> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity,
//        LocalToken.class);
//    LocalToken resp = responseEntity.getBody();
//
//
//    return resp;
//  }

  public HttpHeaders getHttpJsonHeader(HttpServletRequest servletRequest){
    HttpHeaders headers = new HttpHeaders();
    headers.getAccept().clear();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

    Enumeration<String> headerNames = servletRequest.getHeaderNames();
    if(null != headerNames){
      while(headerNames.hasMoreElements()){
        if(headerNames.nextElement().equals("Authorization")){
          String originToken = servletRequest.getHeader("Authorization");
          headers.set("Authorization", originToken);
        }
      }
    }
    return headers;
  }
  @Data
  public class LocalToken {
    @SerializedName("access_token")
    public String accessToken;
    @SerializedName("refresh_token")
    public String refreshToken;

  }


  public class ListFromStringTypeAdapter extends TypeAdapter<List<String>> {

    public List<String> read(JsonReader reader) throws IOException {
      if (reader.peek() == JsonToken.NULL) {
        reader.nextNull();
        return null;
      }
      if (reader.peek() == JsonToken.STRING) {
        // ** This is the part where we fix the issue **
        // If we receive a String, get this and put it in a list.
        // Result will be that item in a list.
        List<String> list = new ArrayList<>();
        list.add(reader.nextString());
        return list;
      } else {
        // Else we expect to receive the array.
        // Based on original collection implementation:
        // https://github.com/google/gson/blob/0636635cbffa08157bdbd558b1212e4d806474eb/gson/src/main/java/com/google/gson/internal/bind/CollectionTypeAdapterFactory.java
        List<String> list = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
          String value = reader.nextString();
          list.add(value);
        }
        reader.endArray();
        return list;
      }
    }

    public void write(JsonWriter writer, List<String> list) throws IOException {
      // Simply writing the array, we don't need to modify anything here.
      // Based on original collection type adapter implementation:
      // https://github.com/google/gson/blob/0636635cbffa08157bdbd558b1212e4d806474eb/gson/src/main/java/com/google/gson/internal/bind/CollectionTypeAdapterFactory.java
      if (list == null) {
        writer.nullValue();
        return;
      }
      writer.beginArray();
      for (String string : list) {
        writer.value(string);
      }
      writer.endArray();
    }
  }

}