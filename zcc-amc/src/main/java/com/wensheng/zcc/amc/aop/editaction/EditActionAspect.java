package com.wensheng.zcc.amc.aop.editaction;

import com.wensheng.zcc.amc.module.dao.helper.EditActionEnum;
import com.wensheng.zcc.amc.module.dao.helper.EditStatusEnum;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.vo.AmcAssetVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtpackExtVo;
import com.wensheng.zcc.amc.module.vo.base.BaseActionVo;
import com.wensheng.zcc.amc.service.AmcDebtService;
import com.wensheng.zcc.amc.service.ZccRulesService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenwei on 1/15/19
 * @project zcc-backend
 */
@Aspect
@Configuration
@Slf4j
public class EditActionAspect {
  @Autowired
  ZccRulesService zccRulesService;

  @Autowired
  AmcDebtService amcDebtService;


  @Before("execution(* com.wensheng.zcc.amc.controller.*.* (com.wensheng.zcc.amc.module.vo.base.BaseActionVo<com"
      + ".wensheng.zcc.amc.module.vo.AmcDebtpackExtVo>, ..)) && args(baseActionVo)")
  public void beforeDoDebtPackAction(BaseActionVo<AmcDebtpackExtVo> baseActionVo) throws Exception {
    log.info("now get the point cut");
    List<AmcDebt> amcDebts = amcDebtService.queryByDebtpackId(baseActionVo.getContent().getAmcDebtpackInfo().getId());
    for(AmcDebt amcDebt: amcDebts){
      EditStatusEnum editStatusEnum =
          zccRulesService.runActionAndStatus(EditActionEnum.lookupByDisplayIdUtil(baseActionVo.getEditActionId()),
          EditStatusEnum.lookupByDisplayStatusUtil(amcDebt.getEditStatus() ));
      if(editStatusEnum == null){
        log.error(String.format("actionId:%s with current editStatus:%s is not applicable",
            baseActionVo.getEditActionId(), amcDebt.getEditStatus() ));
        throw new Exception(String.format("actionId:%s with current editStatus:%s is not applicable",
            baseActionVo.getEditActionId(), amcDebt.getEditStatus() ));
      }
    }
  }

  @Before("execution(* com.wensheng.zcc.amc.controller.*.* (com.wensheng.zcc.amc.module.vo.base.BaseActionVo<com"
      + ".wensheng.zcc.amc.module.vo.AmcAssetVo>, ..)) && args(baseActionVo)")
  public void beforeDoDebtAction(BaseActionVo<AmcAssetVo> baseActionVo) throws Exception {
    log.info("now get the point cut");
    AmcDebtVo amcDebtVo = amcDebtService.get(baseActionVo.getContent().getDebtId());

    EditStatusEnum editStatusEnum =
        zccRulesService.runActionAndStatus(EditActionEnum.lookupByDisplayIdUtil(baseActionVo.getEditActionId()),
            EditStatusEnum.lookupByDisplayStatusUtil(baseActionVo.getContent().getEditStatus() ));
    if(editStatusEnum == null) {
      log.error(String.format("actionId:%s with current editStatus:%s is not applicable",
          baseActionVo.getEditActionId(), baseActionVo.getContent().getEditStatus()));
      throw new Exception(String.format("actionId:%s with current editStatus:%s is not applicable",
          baseActionVo.getEditActionId(), baseActionVo.getContent().getEditStatus()));
    }

  }
}
