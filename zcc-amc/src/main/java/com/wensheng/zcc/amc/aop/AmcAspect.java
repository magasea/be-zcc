package com.wensheng.zcc.amc.aop;

import com.wensheng.zcc.amc.module.dao.helper.EditActionEnum;
import com.wensheng.zcc.amc.module.dao.helper.PublishStateEnum;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.vo.AmcAssetVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtCreateVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtpackExtVo;
import com.wensheng.zcc.amc.module.vo.base.BaseActionVo;
import com.wensheng.zcc.amc.service.AmcDebtService;
import com.wensheng.zcc.amc.service.KafkaService;
import com.wensheng.zcc.amc.service.ZccRulesService;
import com.wensheng.zcc.common.mq.kafka.module.AmcUserOperation;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

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
  KafkaService kafkaService;


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
    log.info("now get the point cut");


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

  @Around("@annotation(EditActionChecker))")
  public Object testAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
    AmcUserOperation amcUserOperation = new AmcUserOperation();

    BaseActionVo actionObj = null;
    log.info("now get the point cut testAnnotation");
    CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
    String[] names = codeSignature.getParameterNames();
    boolean gotActionObject = false;
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
        String reviewComment = (String) joinPoint.getArgs()[idx];
        amcUserOperation.getParam().add(reviewComment);
      }
    }
    if(gotActionObject){
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      amcUserOperation.setActionId(actionObj.getEditActionId());
      Long userId = -1L;

      amcUserOperation.getParam().add(actionObj.getContent());
      if(authentication.getDetails() != null && ((OAuth2AuthenticationDetails)authentication.getDetails()).getDecodedDetails() != null){
        Map details = (Map) ((OAuth2AuthenticationDetails)authentication.getDetails()).getDecodedDetails();
        if(details.containsKey("userId")){
          userId = Long.valueOf( String.format("%d",details.get("userId")));
          amcUserOperation.setUserId(userId);
        }
      }
      updatePublishStateByRuleBook(actionObj.getContent(), actionObj.getEditActionId(), userId);
//      AmcUserDetail amcUserDetail = (AmcUserDetail) authentication.getPrincipal();
      amcUserOperation.setUserName(authentication.getPrincipal().toString());
//      if(((HashMap)authentication.getDetails()).containsKey("userId")){
//        amcUserOperation.setUserId((Long)((Map)authentication.getDetails()).get("userId"));
//      }
      kafkaService.send(amcUserOperation);


    }
    final Object proceed = joinPoint.proceed();
    return proceed;
//    AmcUserOperation amcUserOperation = new AmcUserOperation();
//    amcUserOperation.setActionId(baseActionVo.getEditActionId());
//    amcUserOperation.setParam(baseActionVo.getContent());
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//    kafkaService.send(amcUserOperation);

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
