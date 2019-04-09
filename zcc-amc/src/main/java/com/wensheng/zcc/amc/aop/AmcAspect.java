package com.wensheng.zcc.amc.aop;

import com.wensheng.zcc.amc.module.dao.helper.EditActionEnum;
import com.wensheng.zcc.amc.module.dao.helper.PublishStateEnum;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.vo.AmcDebtpackExtVo;
import com.wensheng.zcc.amc.module.vo.base.BaseActionVo;
import com.wensheng.zcc.amc.service.AmcDebtService;
import com.wensheng.zcc.amc.service.KafkaService;
import com.wensheng.zcc.amc.service.ZccRulesService;
import com.wensheng.zcc.common.mq.kafka.module.AmcUserOperation;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
    for(int idx = 0; idx < joinPoint.getArgs().length; idx++){
      if(joinPoint.getArgs()[idx] instanceof BaseActionVo){
        actionObj = (BaseActionVo) joinPoint.getArgs()[idx];
        log.info("now get baseAction with actionId:{}", actionObj.getEditActionId());
        gotActionObject = true;
        break;
      }else if(names[idx].equals("debtId") || names[idx].equals("amcDebtId")){
        Long amcDebtId = (Long)joinPoint.getArgs()[idx];
        log.info("now get the debtId:{}", amcDebtId);
      }
    }
    if(gotActionObject){
      amcUserOperation.setActionId(actionObj.getEditActionId());

      amcUserOperation.setParam(actionObj.getContent());
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
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

  @Around("@annotation(LogExecutionTime)")
  public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable{
    final long start = System.currentTimeMillis();
    final Object proceed = joinPoint.proceed();
    final long executime = System.currentTimeMillis() - start;
    log.info(String.format("%s executed in %d ms", joinPoint.getSignature(), executime));
    return proceed;
  }
}
