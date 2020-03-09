package com.wensheng.zcc.cust.config.aop;

import com.google.gson.Gson;
import com.wensheng.zcc.common.params.sso.AmcLocationEnum;
import com.wensheng.zcc.common.utils.AmcDateUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcCmpycontactor;
import com.wensheng.zcc.cust.service.BasicInfoService;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @author chenwei on 1/15/19
 * @project zcc-backend
 */
@Aspect
@Configuration
@Slf4j
public class AmcAspect {




  Gson gson = new Gson();


  @Autowired
  BasicInfoService basicInfoService;


  @Around("@annotation(LogExecutionTime)")
  public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable{
    final long start = System.currentTimeMillis();
    final Object proceed = joinPoint.proceed();
    final long executime = System.currentTimeMillis() - start;
    log.info(String.format("%s executed in %d ms", joinPoint.getSignature(), executime));
    return proceed;
  }

  @Around("@annotation(QueryChecker) ")
  public Object aroundDoQuery(ProceedingJoinPoint joinPoint) throws Throwable {
    log.info("now get the point cut");
    if(joinPoint.getArgs().length < 1 || joinPoint.getArgs()[0] == null){
      log.error("cannot process check for this method with args:{}", joinPoint.getArgs());
      return joinPoint.proceed(joinPoint.getArgs());
    }
    CustAmcCmpycontactor custAmcCmpycontactor = (CustAmcCmpycontactor) joinPoint.getArgs()[0];
    String province = custAmcCmpycontactor.getProvince();
    Map<String, Integer> userPrivMap = basicInfoService.getAmcUserPrivMap();
    if(!userPrivMap.containsKey(province)){
      log.error("cannot process for province:{}", joinPoint.getArgs());
      return joinPoint.proceed(joinPoint.getArgs());
    }

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if(authentication == null || ! (authentication.getDetails() instanceof OAuth2AuthenticationDetails)){
      throw ExceptionUtils.getAmcException(AmcExceptions.LOGIN_REQUIRE_ERROR);
    }
    log.info(authentication.getDetails().toString());
    Map<String, Object> detailsParam =
        (Map<String, Object>) ((OAuth2AuthenticationDetails)authentication.getDetails()).getDecodedDetails();
    if(detailsParam.containsKey("location") && null != detailsParam.get("location")){
      Integer locationId = (Integer) detailsParam.get("location");
      AmcLocationEnum locationUserEnum =
          AmcLocationEnum.lookupByDisplayIdUtil(locationId) ;
//      if(null != locationEnum){
//        List<ZccDebtpack> zccDebtpacks = amcDebtpackService.queryPacksWithLocation(locationEnum);
//        if(!CollectionUtils.isEmpty(zccDebtpacks)){
//          List<Long> amcDebtPackIds = zccDebtpacks.stream().map( item -> item.getId()).collect(Collectors.toList());
//          queryParam.setDebtPackIds(amcDebtPackIds);
//        }
//      }
      AmcLocationEnum designedLocationEnum = AmcLocationEnum.lookupByDisplayIdUtil(userPrivMap.get(province));
      if(userPrivMap.get(province) != locationId){
        throw new Exception(String.format("您所在的地区:%s 不能处理该省的投资人信息, 按照设计应该由:%s 地区的业务人员来处理",
            locationUserEnum.getCname(), designedLocationEnum.getCname()));
      }
    }

    if(detailsParam.containsKey("ssoUserId") && null != detailsParam.get("ssoUserId")){
      Long ssoUserId = (Long) detailsParam.get("ssoUserId");
      if(joinPoint.getSignature().toString().startsWith("add") || joinPoint.getSignature().toString().startsWith(
          "create")){
        custAmcCmpycontactor.setCreateBy(ssoUserId);
        custAmcCmpycontactor.setCreateTime(AmcDateUtils.getCurrentDate());
      }
      if(joinPoint.getSignature().toString().startsWith("update") || joinPoint.getSignature().toString().startsWith(
          "mod")){
        custAmcCmpycontactor.setUpdateBy(ssoUserId);
        custAmcCmpycontactor.setUpdateTime(AmcDateUtils.getCurrentDate());
      }

    }
    return joinPoint.proceed(joinPoint.getArgs());
  }


}
