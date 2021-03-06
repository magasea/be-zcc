package com.wensheng.zcc.cust.config.aop;

import com.google.gson.Gson;
import com.wensheng.zcc.common.params.sso.AmcDeptEnum;
import com.wensheng.zcc.common.params.sso.AmcLocationEnum;
import com.wensheng.zcc.common.params.sso.AmcSSOTitleEnum;
import com.wensheng.zcc.common.utils.AmcDateUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.AmcExceptions;
import com.wensheng.zcc.cust.controller.helper.QueryParam;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcCmpycontactor;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import com.wensheng.zcc.cust.module.vo.MergeCustVo;
import com.wensheng.zcc.cust.service.BasicInfoService;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.wensheng.zcc.cust.service.impl.KafkaServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
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

  private Map<String, Integer> userPrivMap;
  private Map<Integer, List<String>> amcUserProvsMap;

  Gson gson = new Gson();


  @Autowired
  BasicInfoService basicInfoService;

  @Autowired
  KafkaServiceImpl kafkaService;

  //日志序列号
  static AtomicInteger traceLogSequence = new AtomicInteger(0);

  /**
   *使用注解加入日志id
   */
  @Before(value = "@within(AddTraceLogId) || @annotation(AddTraceLogId)")
  public void before(JoinPoint joinPoint) throws Throwable {
    //时间戳
    Long timeMilli = System.currentTimeMillis();
    //进程id
    RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
    int runtimeId =Integer.valueOf(runtimeMXBean.getName().split("@")[0]).intValue();
    //日志序列号加 1
    int traceLogSequenceInt  = traceLogSequence.addAndGet(1);
    //生成日志号
    String traceLogId = String.format("%d%010d%07d",timeMilli,traceLogSequenceInt,runtimeId);
    MDC.put("TRACE_LOG_ID",traceLogId);
  }



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

     if(null == userPrivMap){
       userPrivMap = basicInfoService.getAmcUserPrivMap();
     }

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if(authentication == null || ! (authentication.getDetails() instanceof OAuth2AuthenticationDetails)){
      throw ExceptionUtils.getAmcException(AmcExceptions.LOGIN_REQUIRE_ERROR);
    }
    log.info(authentication.getDetails().toString());


    Map<String, Object> detailsParam =
        (Map<String, Object>) ((OAuth2AuthenticationDetails)authentication.getDetails()).getDecodedDetails();
    if(detailsParam.containsKey("ssoUserId") && null != detailsParam.get("ssoUserId")){
      Long ssoUserId = Long.valueOf((Integer)detailsParam.get("ssoUserId"));
      if(joinPoint.getSignature().getName().startsWith("add") || joinPoint.getSignature().getName().startsWith(
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

    if(CollectionUtils.isEmpty(userPrivMap) || null == province || StringUtils.isEmpty(province)){

      return joinPoint.proceed(joinPoint.getArgs());
    }


    if(detailsParam.containsKey("location") && null != detailsParam.get("location")){
      Integer locationId = (Integer) detailsParam.get("location");
      AmcLocationEnum locationUserEnum =
          AmcLocationEnum.lookupByDisplayIdUtil(locationId) ;

      AmcLocationEnum designedLocationEnum = AmcLocationEnum.lookupByDisplayIdUtil(userPrivMap.get(province));
      if(userPrivMap.get(province).equals(locationId)){
        throw new Exception(String.format("您所在的地区:%s 不能处理该省的投资人信息, 按照设计应该由:%s 地区的业务人员来处理",
            locationUserEnum.getCname(), designedLocationEnum.getCname()));
      }
    }
    return joinPoint.proceed(joinPoint.getArgs());
  }


  @Around("@annotation(QueryCheckerCmpy) ")
  public Object aroundDoModCmpy(ProceedingJoinPoint joinPoint) throws Throwable {
    log.info("now get the point cut");
    if (joinPoint.getArgs().length < 1 || joinPoint.getArgs()[0] == null) {
      log.error("cannot process check for this method with args:{}", joinPoint.getArgs());
      return joinPoint.proceed(joinPoint.getArgs());
    }
    CustTrdCmpy custTrdCmpy = (CustTrdCmpy) joinPoint.getArgs()[0];
    String province = custTrdCmpy.getCmpyProvince();
    if(null == userPrivMap){
      userPrivMap = basicInfoService.getAmcUserPrivMap();
    }

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !(authentication.getDetails() instanceof OAuth2AuthenticationDetails)) {
      throw ExceptionUtils.getAmcException(AmcExceptions.LOGIN_REQUIRE_ERROR);
    }
    log.info(authentication.getDetails().toString());


    Map<String, Object> detailsParam =
            (Map<String, Object>) ((OAuth2AuthenticationDetails) authentication.getDetails()).getDecodedDetails();
    if (detailsParam.containsKey("ssoUserId") && null != detailsParam.get("ssoUserId")) {
      Long ssoUserId = Long.valueOf((Integer) detailsParam.get("ssoUserId"));
      if (joinPoint.getSignature().getName().startsWith("add") || joinPoint.getSignature().getName().startsWith(
              "create")) {
        custTrdCmpy.setCreateBy(ssoUserId);
        custTrdCmpy.setUpdateTime(AmcDateUtils.getCurrentDate());
        custTrdCmpy.setCreateTime(AmcDateUtils.getCurrentDate());
      }
      if (joinPoint.getSignature().getName().startsWith("update") || joinPoint.getSignature().getName().startsWith(
              "mod")) {
        custTrdCmpy.setUpdateBy(ssoUserId);
        custTrdCmpy.setUpdateTime(AmcDateUtils.getCurrentDate());
      }

    }

    if (CollectionUtils.isEmpty(userPrivMap) || StringUtils.isEmpty(province)) {

      return joinPoint.proceed(joinPoint.getArgs());
    }

    if (detailsParam.containsKey("location") && null != detailsParam.get("location")) {
      Integer locationId = (Integer) detailsParam.get("location");

        if(locationId > 0){
            AmcLocationEnum locationUserEnum =
                AmcLocationEnum.lookupByDisplayIdUtil(locationId);
            AmcLocationEnum designedLocationEnum = AmcLocationEnum.lookupByDisplayIdUtil(userPrivMap.get(province));
          log.info("locationId:{};designedLocationEnum:{};locationUserEnum:{};userPrivMap:{}",locationId,
              designedLocationEnum, locationUserEnum,userPrivMap);
          Integer a = userPrivMap.get(province);
          log.info("province:{};地区:{};",province,a);

            if(locationId == null || locationId.compareTo(0) < 0 || designedLocationEnum ==null || locationUserEnum == null){
              log.error("locationId:{};designedLocationEnum:{};locationUserEnum:{};userPrivMap:{}",locationId,
                  designedLocationEnum, locationUserEnum,userPrivMap);
              throw new RuntimeException(String.format("没有归属地区的用户不能更改投资入库"));
            }

            if (!userPrivMap.get(province).equals(locationId)) {
              throw new RuntimeException(String.format("您所在的地区:%s 不能处理该省的投资人信息, 按照设计应该由:%s 地区的业务人员来处理",
                  locationUserEnum.getCname(), designedLocationEnum.getCname()));
            }
        }
      }


      return joinPoint.proceed(joinPoint.getArgs());
  }

  @Around("@annotation(ModifyCheckerCustPerson) ")
  public Object modifyCheckerCustPerson(ProceedingJoinPoint joinPoint) throws Throwable {
    log.info("now get the point cut");
    if (joinPoint.getArgs().length < 1 || joinPoint.getArgs()[0] == null) {
      log.error("cannot process check for this method with args:{}", joinPoint.getArgs());
      return joinPoint.proceed(joinPoint.getArgs());
    }

    CustTrdPerson custTrdPerson = (CustTrdPerson) joinPoint.getArgs()[0];

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !(authentication.getDetails() instanceof OAuth2AuthenticationDetails)) {
      throw ExceptionUtils.getAmcException(AmcExceptions.LOGIN_REQUIRE_ERROR);
    }
    log.info(authentication.getDetails().toString());


    Map<String, Object> detailsParam =
            (Map<String, Object>) ((OAuth2AuthenticationDetails) authentication.getDetails()).getDecodedDetails();
    if (detailsParam.containsKey("ssoUserId") && null != detailsParam.get("ssoUserId")) {
      Long ssoUserId = Long.valueOf((Integer) detailsParam.get("ssoUserId"));
      if (joinPoint.getSignature().getName().startsWith("add") || joinPoint.getSignature().getName().startsWith(
              "create")) {
        custTrdPerson.setCreateBy(ssoUserId);
        custTrdPerson.setUpdateTime(AmcDateUtils.getCurrentDate());
        custTrdPerson.setCreateTime(AmcDateUtils.getCurrentDate());
      }
      if (joinPoint.getSignature().getName().startsWith("update") || joinPoint.getSignature().getName().startsWith(
              "mod")) {
        custTrdPerson.setUpdateBy(ssoUserId);
        custTrdPerson.setUpdateTime(AmcDateUtils.getCurrentDate());
      }
    }
      return joinPoint.proceed(joinPoint.getArgs());
  }

  @Around("@annotation(MergeCustChecker) ")
  public Object mergeCustChecker(ProceedingJoinPoint joinPoint) throws Throwable {
    log.info("now get the point cut");
    if (joinPoint.getArgs().length < 1 || joinPoint.getArgs()[0] == null) {
      log.error("cannot process check for this method with args:{}", joinPoint.getArgs());
      return joinPoint.proceed(joinPoint.getArgs());
    }

    MergeCustVo mergeCustVo = (MergeCustVo) joinPoint.getArgs()[0];

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !(authentication.getDetails() instanceof OAuth2AuthenticationDetails)) {
      throw ExceptionUtils.getAmcException(AmcExceptions.LOGIN_REQUIRE_ERROR);
    }
    log.info(authentication.getDetails().toString());


    Map<String, Object> detailsParam =
            (Map<String, Object>) ((OAuth2AuthenticationDetails) authentication.getDetails()).getDecodedDetails();
    if (detailsParam.containsKey("ssoUserId") && null != detailsParam.get("ssoUserId")) {
      Long ssoUserId = Long.valueOf((Integer) detailsParam.get("ssoUserId"));
      mergeCustVo.setUpdateBy(ssoUserId);
    }
      return joinPoint.proceed(joinPoint.getArgs());
  }


  @Around("@annotation(ModifyCheckerCustCmpycontactor) ")
  public Object modifyCheckerCustCmpycontactor(ProceedingJoinPoint joinPoint) throws Throwable {
    log.info("now get the point cut");
    if (joinPoint.getArgs().length < 1 || joinPoint.getArgs()[0] == null) {
      log.error("cannot process check for this method with args:{}", joinPoint.getArgs());
      return joinPoint.proceed(joinPoint.getArgs());
    }

    CustAmcCmpycontactor custAmcCmpycontactor = (CustAmcCmpycontactor) joinPoint.getArgs()[0];

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !(authentication.getDetails() instanceof OAuth2AuthenticationDetails)) {
      throw ExceptionUtils.getAmcException(AmcExceptions.LOGIN_REQUIRE_ERROR);
    }
    log.info(authentication.getDetails().toString());


    Map<String, Object> detailsParam =
        (Map<String, Object>) ((OAuth2AuthenticationDetails) authentication.getDetails()).getDecodedDetails();
    if (detailsParam.containsKey("ssoUserId") && null != detailsParam.get("ssoUserId")) {
      Long ssoUserId = Long.valueOf((Integer) detailsParam.get("ssoUserId"));
      if (joinPoint.getSignature().getName().startsWith("add") || joinPoint.getSignature().getName().startsWith(
          "create")) {
        custAmcCmpycontactor.setCreateBy(ssoUserId);
        custAmcCmpycontactor.setUpdateTime(AmcDateUtils.getCurrentDate());
        custAmcCmpycontactor.setCreateTime(AmcDateUtils.getCurrentDate());
      }
      if (joinPoint.getSignature().getName().startsWith("update") || joinPoint.getSignature().getName().startsWith(
          "mod")) {
        custAmcCmpycontactor.setUpdateBy(ssoUserId);
        custAmcCmpycontactor.setUpdateTime(AmcDateUtils.getCurrentDate());
      }
    }
    return joinPoint.proceed(joinPoint.getArgs());
  }


  @Around("@annotation(QueryValidCmpy)")
  public Object aroundQueryValidCmpy(ProceedingJoinPoint joinPoint) throws Throwable {
    log.info("now get the point cut");
    if(joinPoint.getArgs().length < 1 || joinPoint.getArgs()[0] == null){
      log.error("cannot process check for this method with args:{}", joinPoint.getArgs());
      return joinPoint.proceed(joinPoint.getArgs());
    }
    QueryParam queryParam = (QueryParam) joinPoint.getArgs()[0];
    if(null == amcUserProvsMap){
      amcUserProvsMap = basicInfoService.getAmcUserProvsMap();
    }


    if(CollectionUtils.isEmpty(amcUserProvsMap)){
      return joinPoint.proceed(joinPoint.getArgs());
    }
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if(authentication == null || ! (authentication.getDetails() instanceof OAuth2AuthenticationDetails)){
      throw ExceptionUtils.getAmcException(AmcExceptions.LOGIN_REQUIRE_ERROR);
    }
    log.info(authentication.getDetails().toString());

    Integer deptId = -1;
    Integer title = -1;
    Integer location = -1;
    Map<String, Object> detailsParam =
        (Map<String, Object>) ((OAuth2AuthenticationDetails)authentication.getDetails()).getDecodedDetails();
    if(detailsParam.containsKey("title") && null != detailsParam.get("title")){
      title = Integer.valueOf((Integer)detailsParam.get("title"));
    }else{
      return joinPoint.proceed(joinPoint.getArgs());
    }
    if(detailsParam.containsKey("deptId") && null != detailsParam.get("deptId")){
      deptId = Integer.valueOf((Integer)detailsParam.get("deptId"));
    }else{
      return joinPoint.proceed(joinPoint.getArgs());
    }
    if(detailsParam.containsKey("location") && null != detailsParam.get("location")){
      location = Integer.valueOf((Integer)detailsParam.get("location"));
    }else{
      return joinPoint.proceed(joinPoint.getArgs());
    }
    //两个级别不做限制
    if(title.equals(AmcSSOTitleEnum.TITLE_MGR.getId())
        ||title.equals(AmcSSOTitleEnum.TITLE_SYS_ADM.getId())){
      return joinPoint.proceed(joinPoint.getArgs());
    //业务部门做限制
    }else if(deptId.equals(AmcDeptEnum.BUSINESS_DEPT.getId())){
        if(CollectionUtils.isEmpty(queryParam.getCustCity())){
          queryParam.setCustCity(amcUserProvsMap.get(location));
        }else{
          Set<String> provinces =
              amcUserProvsMap.get(location).stream().collect(Collectors.toSet());
          List<String> filteredProvince = new ArrayList<>();
          for(String custCity: queryParam.getCustCity()){
            log.info(custCity);
            if(StringUtils.isEmpty(custCity)){
              continue;
            }
            if(provinces.contains(custCity)){
              filteredProvince.add(custCity);
            }
          }
          if(CollectionUtils.isEmpty(filteredProvince)){
            queryParam.setCustCity(Arrays.asList("0"));
          }else{
            queryParam.setCustCity(filteredProvince);
          }

        }

    }



      return joinPoint.proceed(joinPoint.getArgs());

  }

}
