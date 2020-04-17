package com.wensheng.zcc.amc.service.impl;

import com.deliveredtechnologies.rulebook.FactMap;
import com.deliveredtechnologies.rulebook.NameValueReferableMap;
import com.deliveredtechnologies.rulebook.model.RuleBook;
import com.deliveredtechnologies.rulebook.model.runner.RuleBookRunner;
import com.wensheng.zcc.amc.module.dao.helper.EditActionEnum;
import com.wensheng.zcc.amc.module.dao.helper.PublishStateEnum;
import com.wensheng.zcc.amc.service.ZccRulesService;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chenwei on 1/14/19
 * @project zcc-backend
 */
@Slf4j
@Service
public class ZccRulesServiceImpl implements ZccRulesService {

  @Autowired
  RuleBook<PublishStateEnum> ruleBook4ZccEdit;

  @Override
  public PublishStateEnum runActionAndStatus(EditActionEnum action, PublishStateEnum status) throws Exception {

//    NameValueReferableMap facts = new FactMap();
//    facts.setValue("editAction", action);
//    facts.setValue("currentStatus", status);
//    ruleBook4ZccEdit = new RuleBookRunner("com.wensheng.zcc.amc.rules.zccedit");
//    ruleBook4ZccEdit.run(facts);
//    ruleBook4ZccEdit.run(facts);
//    ruleBook4ZccEdit.getResult().ifPresent(result->log.info(result.toString()));
    PublishStateEnum retVal = check(action, status);
    return retVal;
  }

  @Override
  public boolean editAble(PublishStateEnum editStatus) {
//    NameValueReferableMap facts = new FactMap();
//    facts.setValue("editAction", EditActionEnum.ACT_SAVE);
//    facts.setValue("currentStatus", editStatus);
//    ruleBook4ZccEdit.run(facts);
//    ruleBook4ZccEdit.getResult().ifPresent(result->log.info(result.toString()));
    PublishStateEnum retVal = null;
    try {
      retVal = check(EditActionEnum.ACT_SAVE, editStatus);
    }catch (Exception ex){
      log.error("failed to check editable:", ex);
    }
    if(retVal != null){
      return true;
    }
    return false;
  }

  private PublishStateEnum check(EditActionEnum editAction, PublishStateEnum currentStatus) throws Exception {
    PublishStateEnum editStatusResult;
      if(editAction == EditActionEnum.ACT_CREATE  ){
        editStatusResult = PublishStateEnum.DRAFT;
        return editStatusResult;
      }
      if(editAction == EditActionEnum.ACT_SAVE){
        if(currentStatus == PublishStateEnum.DRAFT || currentStatus == PublishStateEnum.DRAFT_CHECK_FAILED || currentStatus == PublishStateEnum.NOTCLEAR) {
          editStatusResult =  PublishStateEnum.DRAFT;
          return editStatusResult;
        }
        if(currentStatus == PublishStateEnum.UNSOLD_OFF_SHELF|| currentStatus == PublishStateEnum.RECORD|| currentStatus == PublishStateEnum.RECORD_CHECK_FAILED){
          editStatusResult = PublishStateEnum.RECORD;
          return editStatusResult;
        }
      }
      if(editAction == EditActionEnum.ACT_REVIEW_FAIL ){
        if( currentStatus == PublishStateEnum.DRAFT_CHECK_WAIT ) {
          editStatusResult =  PublishStateEnum.DRAFT_CHECK_FAILED;
          return editStatusResult;
        }
        if(currentStatus == PublishStateEnum.RECORD_CHECK_WAIT){
          editStatusResult = PublishStateEnum.RECORD_CHECK_FAILED;
          return editStatusResult;
        }
      }
      if(editAction == EditActionEnum.ACT_REVIEW_PASS){
        if(currentStatus == PublishStateEnum.DRAFT_CHECK_WAIT || currentStatus == PublishStateEnum.RECORD_CHECK_WAIT){
          editStatusResult =  PublishStateEnum.PUBLISHED;
          return editStatusResult;
        }
      }
      if(editAction == EditActionEnum.ACT_SUBMIT4PUB){
        if(currentStatus == PublishStateEnum.DRAFT ){
          editStatusResult =  PublishStateEnum.DRAFT_CHECK_WAIT;
          return editStatusResult;
        }
        if(currentStatus == PublishStateEnum.RECORD || currentStatus == PublishStateEnum.UNSOLD_OFF_SHELF){
          editStatusResult = PublishStateEnum.RECORD_CHECK_WAIT;
          return editStatusResult;
        }
      }
      if(editAction == EditActionEnum.ACT_OFF_SHELF){
        if(currentStatus == PublishStateEnum.SOLD ){
          editStatusResult =  PublishStateEnum.SOLD_OFF_SHELF;
          return editStatusResult;
        }
        if(currentStatus == PublishStateEnum.PUBLISHED){
          editStatusResult = PublishStateEnum.UNSOLD_OFF_SHELF;
          return editStatusResult;
        }
      }
      if(editAction == EditActionEnum.ACT_SOLD){
        if(currentStatus == PublishStateEnum.PUBLISHED){
          editStatusResult =  PublishStateEnum.SOLD;
          return editStatusResult;
        }
      }
      if(editAction == EditActionEnum.ACT_DEL){
        if( currentStatus == PublishStateEnum.RECORD || currentStatus == PublishStateEnum.RECORD_CHECK_FAILED || currentStatus == PublishStateEnum.DRAFT_CHECK_FAILED || currentStatus == PublishStateEnum.UNSOLD_OFF_SHELF){
          editStatusResult = PublishStateEnum.DELETED;
          return editStatusResult;
        }
        if(currentStatus == PublishStateEnum.DRAFT || currentStatus == PublishStateEnum.NOTCLEAR){
          editStatusResult = PublishStateEnum.DELETED;
          return editStatusResult;
        }
      }
      if(editAction == EditActionEnum.ACT_HIDE){
        if(currentStatus == PublishStateEnum.PUBLISHED){
          editStatusResult = PublishStateEnum.PUBLISH_HIDED;
          return editStatusResult;
        }
      }
      if(editAction == EditActionEnum.ACT_UNHIDE){
        if(currentStatus == PublishStateEnum.PUBLISH_HIDED){
          editStatusResult = PublishStateEnum.PUBLISHED;
          return editStatusResult;
        }
      }
      throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_ACTION, String.format("currentStatus:%s action:%s",
          currentStatus.getName(), editAction.getCname()));
    }


}
