package com.wensheng.zcc.amc.rules.zccedit;

import com.deliveredtechnologies.rulebook.annotation.Given;
import com.deliveredtechnologies.rulebook.annotation.Result;
import com.deliveredtechnologies.rulebook.annotation.Rule;
import com.deliveredtechnologies.rulebook.annotation.Then;
import com.deliveredtechnologies.rulebook.annotation.When;
import com.wensheng.zcc.amc.module.dao.helper.EditActionEnum;
import com.wensheng.zcc.amc.module.dao.helper.PublishStateEnum;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;

/**
 * @author chenwei on 1/14/19
 * @project zcc-backend
 */
@Rule
public class EditProcessRule {
  @Result
  PublishStateEnum editStatusResult;


  @Given("editAction")
  EditActionEnum editAction;

  @Given("currentStatus")
  PublishStateEnum currentStatus;

  @When
  public boolean when(){
    if(editAction != null && currentStatus != null){
      return true;
    }
    return false;
  }

  @Then
  public void then() throws Exception {
    if(editAction == EditActionEnum.ACT_CREATE  ){
      editStatusResult = PublishStateEnum.DRAFT;
      return;
    }
    if(editAction == EditActionEnum.ACT_SAVE){
      if(currentStatus == PublishStateEnum.DRAFT || currentStatus == PublishStateEnum.DRAFT_CHECK_FAILED) {
        editStatusResult =  PublishStateEnum.DRAFT;
        return;
      }
    }
    if(editAction == EditActionEnum.ACT_REVIEW_FAIL ){
      if( currentStatus == PublishStateEnum.DRAFT_CHECK_WAIT ) {
        editStatusResult =  PublishStateEnum.DRAFT_CHECK_FAILED;
        return;
      }
      if(currentStatus == PublishStateEnum.RECORD_CHECK_WAIT){
        editStatusResult = PublishStateEnum.RECORD_CHECK_FAILED;
      }
    }
    if(editAction == EditActionEnum.ACT_REVIEW_PASS){
      if(currentStatus == PublishStateEnum.DRAFT_CHECK_WAIT || currentStatus == PublishStateEnum.RECORD_CHECK_WAIT){
        editStatusResult =  PublishStateEnum.PUBLISHED;
        return;
      }
    }
    if(editAction == EditActionEnum.ACT_SUBMIT4PUB){
      if(currentStatus == PublishStateEnum.DRAFT ){
        editStatusResult =  PublishStateEnum.DRAFT_CHECK_WAIT;
        return;
      }
      if(currentStatus == PublishStateEnum.RECORD || currentStatus == PublishStateEnum.UNSOLD_OFF_SHELF){
        editStatusResult = PublishStateEnum.RECORD_CHECK_WAIT;
      }
    }
    if(editAction == EditActionEnum.ACT_OFF_SHELF){
      if(currentStatus == PublishStateEnum.SOLD ){
        editStatusResult =  PublishStateEnum.SOLD_OFF_SHELF;
        return;
      }
      if(currentStatus == PublishStateEnum.PUBLISHED){
        editStatusResult = PublishStateEnum.SOLD_OFF_SHELF;
      }
    }
    if(editAction == EditActionEnum.ACT_SOLD){
      if(currentStatus == PublishStateEnum.PUBLISHED){
        editStatusResult =  PublishStateEnum.SOLD;
        return;
      }
    }
    throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_ACTION, String.format("currentStatus:%s action:%s",
        currentStatus.getName(), editAction.getCname()));
  }

}
