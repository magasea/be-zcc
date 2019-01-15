package com.wensheng.zcc.amc.rules.zccedit;

import com.deliveredtechnologies.rulebook.annotation.Given;
import com.deliveredtechnologies.rulebook.annotation.Result;
import com.deliveredtechnologies.rulebook.annotation.Rule;
import com.deliveredtechnologies.rulebook.annotation.Then;
import com.deliveredtechnologies.rulebook.annotation.When;
import com.wensheng.zcc.amc.module.dao.helper.EditActionEnum;
import com.wensheng.zcc.amc.module.dao.helper.EditStatusEnum;
import com.wensheng.zcc.amc.utils.ExceptionUtils;
import com.wensheng.zcc.amc.utils.ExceptionUtils.AmcExceptions;

/**
 * @author chenwei on 1/14/19
 * @project zcc-backend
 */
@Rule
public class EditProcessRule {
  @Result
  EditStatusEnum editStatusResult;


  @Given("editAction")
  EditActionEnum editAction;

  @Given("currentStatus")
  EditStatusEnum currentStatus;

  @When
  public boolean when(){
    if(editAction != null && currentStatus != null){
      return true;
    }
    return false;
  }

  @Then
  public void then() throws Exception {
    if(editAction == EditActionEnum.ACT_CREATE && currentStatus == EditStatusEnum.INIT ){
      editStatusResult = EditStatusEnum.DRAFT;
      return;
    }
    if(editAction == EditActionEnum.ACT_SAVE){
      if(currentStatus == EditStatusEnum.INIT) {
        editStatusResult =  EditStatusEnum.DRAFT;
        return;
      }else if( currentStatus == EditStatusEnum.PUBLISHED ||
          currentStatus == EditStatusEnum.SOLD){
        throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_ACTION);
      }else{
        editStatusResult =  currentStatus;
        return;
      }
    }
    if(editAction == EditActionEnum.ACT_REVIEW_FAIL ){
      if( currentStatus == EditStatusEnum.DRAFT ||
          currentStatus == EditStatusEnum.CHECK_FAILED || currentStatus == EditStatusEnum.CHECK_WAIT ) {
        editStatusResult =  EditStatusEnum.CHECK_FAILED;
        return;
      }else{
        throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_ACTION);
      }
    }
    if(editAction == EditActionEnum.ACT_REVIEW_PASS){
      if(currentStatus == EditStatusEnum.CHECK_FAILED || currentStatus == EditStatusEnum.CHECK_WAIT){
        editStatusResult =  EditStatusEnum.PUBLISHED;
        return;
      }else{
        throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_ACTION);
      }
    }
    if(editAction == EditActionEnum.ACT_SUBMIT4PUB){
      if(currentStatus == EditStatusEnum.DRAFT || currentStatus == EditStatusEnum.CHECK_FAILED){
        editStatusResult =  EditStatusEnum.CHECK_WAIT;
        return;
      }else{
        throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_ACTION);
      }
    }
    if(editAction == EditActionEnum.ACT_OFF_SHELF){
      if(currentStatus == EditStatusEnum.PUBLISHED ){
        editStatusResult =  EditStatusEnum.CHECK_WAIT;
        return;
      }else{
        throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_ACTION);
      }
    }
    if(editAction == EditActionEnum.ACT_SOLD){
      if(currentStatus == EditStatusEnum.PUBLISHED){
        editStatusResult =  EditStatusEnum.SOLD;
        return;
      }else{
        throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_ACTION);
      }
    }
    throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_ACTION);
  }

}
