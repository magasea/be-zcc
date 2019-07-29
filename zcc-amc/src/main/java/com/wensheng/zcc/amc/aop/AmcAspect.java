package com.wensheng.zcc.amc.aop;

import com.google.gson.Gson;
import com.wensheng.zcc.amc.controller.helper.QueryParam;
import com.wensheng.zcc.amc.module.dao.helper.EditActionEnum;
import com.wensheng.zcc.amc.module.dao.helper.PublishStateEnum;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtpack;
import com.wensheng.zcc.amc.module.vo.AmcAssetVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtCreateVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtpackExtVo;
import com.wensheng.zcc.amc.module.vo.base.BaseActionVo;
import com.wensheng.zcc.amc.service.AmcDebtService;
import com.wensheng.zcc.amc.service.AmcDebtpackService;
import com.wensheng.zcc.amc.service.KafkaService;
import com.wensheng.zcc.amc.service.ZccRulesService;
import com.wensheng.zcc.common.mq.kafka.module.AmcUserOperation;
import com.wensheng.zcc.common.params.AmcBranchLocationEnum;
import com.wensheng.zcc.common.utils.AmcDateUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.hibernate.mapping.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.util.CollectionUtils;

/**
 * @author chenwei on 1/15/19
 * @project zcc-backend
 */
@Aspect
@Configuration
@Slf4j
public class AmcAspect {
  @Autowired
  ZccRulesService zccRulesService;

  @Autowired
  AmcDebtService amcDebtService;

  @Autowired
  AmcDebtpackService amcDebtpackService;

  @Autowired
  KafkaService kafkaService;

  Gson gson = new Gson();


  @Before("execution(* com.wensheng.zcc.amc.controller.*.* (com.wensheng.zcc.amc.module.vo.base.BaseActionVo<com"
      + ".wensheng.zcc.amc.module.vo.AmcDebtpackExtVo>, ..)) && args(baseActionVo)")
  public void beforeDoDebtPackAction(BaseActionVo<AmcDebtpackExtVo> baseActionVo) throws Exception {
    log.info("now get the point cut");
    List<AmcDebt> amcDebts = amcDebtService.queryByDebtpackId(baseActionVo.getContent().getAmcDebtpackInfo().getId());
    for(AmcDebt amcDebt: amcDebts){
      PublishStateEnum publishStateEnum =
          zccRulesService.runActionAndStatus(EditActionEnum.lookupByDisplayIdUtil(baseActionVo.getEditActionId()),
          PublishStateEnum.lookupByDisplayStatusUtil(amcDebt.getPublishState() ));
      if(publishStateEnum == null){
        log.error(String.format("actionId:%s with current publishState:%s is not applicable",
            baseActionVo.getEditActionId(), amcDebt.getPublishState() ));
        throw new Exception(String.format("actionId:%s with current publishState:%s is not applicable",
            baseActionVo.getEditActionId(), amcDebt.getPublishState() ));
      }
    }
  }

  @Before("@annotation(EditActionChecker) && args(baseActionVo)")
  public void beforeDoDebtAction(BaseActionVo baseActionVo) throws Exception {
    log.info("now get the beforeDoDebtAction point cut");



//    AmcDebtExtVo amcDebtExtVo = amcDebtService.get(baseActionVo.getContent().getDebtId());
//
//    PublishStateEnum publishStateEnum =
//        zccRulesService.runActionAndStatus(EditActionEnum.lookupByDisplayIdUtil(baseActionVo.getEditActionId()),
//            PublishStateEnum.lookupByDisplayStatusUtil(baseActionVo.getContent().getPublishState() ));
//    if(publishStateEnum == null) {
//      log.error(String.format("actionId:%s with current publishState:%s is not applicable",
//          baseActionVo.getEditActionId(), baseActionVo.getContent().getPublishState()));
//      throw new Exception(String.format("actionId:%s with current publishState:%s is not applicable",
//          baseActionVo.getEditActionId(), baseActionVo.getContent().getPublishState()));
//    }

  }

  @Around("@annotation(QueryChecker) ")
  public Object aroundDoQuery(ProceedingJoinPoint joinPoint) throws Throwable {
    log.info("now get the point cut");
    QueryParam queryParam = (QueryParam) joinPoint.getArgs()[0];

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if(authentication == null || ! (authentication.getDetails() instanceof OAuth2AuthenticationDetails)){
      log.error("it is web user");
      return joinPoint.proceed(new Object[]{queryParam});
    }
    log.info(authentication.getDetails().toString());
    Map<String, Object> detailsParam =
        (Map<String, Object>) ((OAuth2AuthenticationDetails)authentication.getDetails()).getDecodedDetails();
    if(detailsParam.containsKey("location") && null != detailsParam.get("location")){
      Integer locationId = (Integer) detailsParam.get("location");
      AmcBranchLocationEnum locationEnum = AmcBranchLocationEnum.lookupByIdUtil(locationId) ;
      if(null != locationEnum){
        List<AmcDebtpack>  amcDebtpacks = amcDebtpackService.queryPacksWithLocation(locationEnum.getCname());
        if(!CollectionUtils.isEmpty(amcDebtpacks)){
          List<Long> amcDebtPackIds = amcDebtpacks.stream().map( item -> item.getId()).collect(Collectors.toList());
          queryParam.setDebtPackIds(amcDebtPackIds);
        }
      }

    }
    return joinPoint.proceed(new Object[]{queryParam});
  }

  @Around("@annotation(EditActionChecker))")
  public Object testAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
    AmcUserOperation amcUserOperation = new AmcUserOperation();

    BaseActionVo actionObj = null;
    log.info("now get the point cut testAnnotation");
    CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
    String[] names = codeSignature.getParameterNames();
    String reviewComment = "";

    boolean gotActionObject = false;
    boolean needLog = false;
    amcUserOperation.setParam(new ArrayList());
    for(int idx = 0; idx < joinPoint.getArgs().length; idx++){
      if(joinPoint.getArgs()[idx] instanceof BaseActionVo){
        actionObj = (BaseActionVo) joinPoint.getArgs()[idx];
        log.info("now get baseAction with actionId:{}", actionObj.getEditActionId());
        gotActionObject = true;
        break;
      }else if(names[idx].equals("debtId") || names[idx].equals("amcDebtId")){
        Long amcDebtId = (Long)joinPoint.getArgs()[idx];
        log.info("now get the debtId:{}", amcDebtId);
      }else if(names[idx].equals("reviewComment")){
        reviewComment = (String) joinPoint.getArgs()[idx];
        amcUserOperation.getParam().add(reviewComment);
      }else if(names[idx].equals(("contactorIds")) || names[idx].equals("amcPerson")){
        amcUserOperation.setMethodName(joinPoint.getSignature().getName());
        amcUserOperation.getParam().add(joinPoint.getArgs()[idx]);
        needLog = true;
      }
    }
    if(gotActionObject || needLog){
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      Long userId = -1L;

      if(authentication.getDetails() != null && ((OAuth2AuthenticationDetails)authentication.getDetails()).getDecodedDetails() != null){
        Map details = (Map) ((OAuth2AuthenticationDetails)authentication.getDetails()).getDecodedDetails();
        if(details.containsKey("userId")){
          userId = Long.valueOf( String.format("%d",details.get("userId")));
          amcUserOperation.setUserId(userId);

        }

      }
      amcUserOperation.setUserName(authentication.getPrincipal().toString());
      if(gotActionObject){
        amcUserOperation.setActionId(actionObj.getEditActionId());
        amcUserOperation.getParam().add(actionObj.getContent());

        updatePublishStateByRuleBook(actionObj.getContent(), actionObj.getEditActionId(), userId);
//      AmcUserDetail amcUserDetail = (AmcUserDetail) authentication.getPrincipal();

        amcUserOperation.setDateTime(AmcDateUtils.getLocalDateTime());
//      if(((HashMap)authentication.getDetails()).containsKey("userId")){
//        amcUserOperation.setUserId((Long)((Map)authentication.getDetails()).get("userId"));
//      }

        if(! (actionObj.getContent() instanceof AmcDebtCreateVo)){
          amcDebtService.saveOperLog(actionObj,reviewComment);
        }else{
          log.info("It is create debt action, need get debtId later");
        }

      }
    }

    final Object proceed = joinPoint.proceed();
    if(proceed instanceof AmcDebtVo && actionObj.getContent() instanceof AmcDebtCreateVo){
      ((AmcDebtCreateVo)actionObj.getContent()).setId(((AmcDebtVo) proceed).getId());
      amcDebtService.saveOperLog(actionObj,reviewComment);
    }
    if(gotActionObject || needLog){
      kafkaService.send(amcUserOperation);
    }

    return proceed;


  }

  private void updatePublishStateByRuleBook(Object param, int actionId, Long userId) throws Exception {
    if(param instanceof List){
      for(Object item: (List)param ){
        doUpdateSinglePublishState(item, actionId, userId);
      }
    }else{
      doUpdateSinglePublishState(param, actionId, userId);
    }
  }

  private void doUpdateSinglePublishState(Object param, int actionId, Long userId) throws Exception {
    if(param instanceof AmcDebtCreateVo ){
      AmcDebtCreateVo debtCreateVo = (AmcDebtCreateVo) param;
      PublishStateEnum publishStateEnum =
          zccRulesService.runActionAndStatus(EditActionEnum.lookupByDisplayIdUtil(actionId),
              PublishStateEnum.lookupByDisplayStatusUtil( debtCreateVo.getPublishState() ));
      log.info("will update debt publish state from:{} to {}", debtCreateVo.getPublishState(), publishStateEnum.getStatus());
      debtCreateVo.setPublishState(publishStateEnum.getStatus());
      debtCreateVo.setUpdateBy(userId);

    }else if(param instanceof AmcAssetVo){
      AmcAssetVo assetVo = (AmcAssetVo) param;
      AmcDebt amcDebt = amcDebtService.getDebt(assetVo.getDebtId());
      PublishStateEnum publishStateEnum = zccRulesService.runActionAndStatus(EditActionEnum.ACT_SAVE,
          PublishStateEnum.lookupByDisplayStatusUtil(amcDebt.getPublishState()));
      if(publishStateEnum == null){
        throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_ACTION, String.format("do action:%s on "
            + "state:%s is not allowed", EditActionEnum.ACT_SAVE.getCname(),
            PublishStateEnum.lookupByDisplayStatusUtil(amcDebt.getPublishState()).getName()));
      }
      amcDebt.setPublishState(publishStateEnum.getStatus());
      amcDebt.setUpdateBy(userId);
      log.info("will update debt publish state from:{} to {}", amcDebt.getPublishState(), publishStateEnum.getStatus());
      amcDebtService.update(amcDebt);
      assetVo.setPublishState(publishStateEnum.getStatus());
      assetVo.setUpdateBy(userId);
    }else if(param instanceof AmcDebtVo){
      AmcDebtVo debtVo = (AmcDebtVo) param;
      PublishStateEnum publishStateEnum =
          zccRulesService.runActionAndStatus(EditActionEnum.lookupByDisplayIdUtil(actionId),
              PublishStateEnum.lookupByDisplayStatusUtil( debtVo.getPublishState() ));
      log.info("will update debt publish state from:{} to {}", debtVo.getPublishState(), publishStateEnum.getStatus());
      debtVo.setUpdateBy(userId);
      debtVo.setPublishState(publishStateEnum.getStatus());
    }

  }


  @Around("@annotation(LogExecutionTime)")
  public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable{
    final long start = System.currentTimeMillis();
    final Object proceed = joinPoint.proceed();
    final long executime = System.currentTimeMillis() - start;
    log.info(String.format("%s executed in %d ms", joinPoint.getSignature(), executime));
    return proceed;
  }



}
