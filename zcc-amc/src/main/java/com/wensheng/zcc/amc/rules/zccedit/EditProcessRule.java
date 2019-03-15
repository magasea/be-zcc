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
    if(editAction == EditActionEnum.ACT_CREATE && currentStatus == PublishStateEnum.INIT ){
      editStatusResult = PublishStateEnum.DRAFT;
      return;
    }
    if(editAction == EditActionEnum.ACT_SAVE){
      if(currentStatus == PublishStateEnum.INIT) {
        editStatusResult =  PublishStateEnum.DRAFT;
        return;
      }else if( currentStatus == PublishStateEnum.PUBLISHED ||
          currentStatus == PublishStateEnum.SOLD){
        throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_ACTION);
      }else{
        editStatusResult =  currentStatus;
        return;
      }
    }
    if(editAction == EditActionEnum.ACT_REVIEW_FAIL ){
      if( currentStatus == PublishStateEnum.DRAFT ||
          currentStatus == PublishStateEnum.CHECK_FAILED || currentStatus == PublishStateEnum.CHECK_WAIT ) {
        editStatusResult =  PublishStateEnum.CHECK_FAILED;
        return;
      }else{
        throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_ACTION);
      }
    }
    if(editAction == EditActionEnum.ACT_REVIEW_PASS){
      if(currentStatus == PublishStateEnum.CHECK_FAILED || currentStatus == PublishStateEnum.CHECK_WAIT){
        editStatusResult =  PublishStateEnum.PUBLISHED;
        return;
      }else{
        throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_ACTION);
      }
    }
    if(editAction == EditActionEnum.ACT_SUBMIT4PUB){
      if(currentStatus == PublishStateEnum.DRAFT || currentStatus == PublishStateEnum.CHECK_FAILED){
        editStatusResult =  PublishStateEnum.CHECK_WAIT;
        return;
      }else{
        throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_ACTION);
      }
    }
    if(editAction == EditActionEnum.ACT_OFF_SHELF){
      if(currentStatus == PublishStateEnum.PUBLISHED ){
        editStatusResult =  PublishStateEnum.CHECK_WAIT;
        return;
      }else{
        throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_ACTION);
      }
    }
    if(editAction == EditActionEnum.ACT_SOLD){
      if(currentStatus == PublishStateEnum.PUBLISHED){
        editStatusResult =  PublishStateEnum.SOLD;
        return;
      }else{
        throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_ACTION);
      }
    }
    throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_ACTION);
  }

}
