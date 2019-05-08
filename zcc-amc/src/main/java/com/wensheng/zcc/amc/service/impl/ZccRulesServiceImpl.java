package com.wensheng.zcc.amc.service.impl;

import com.deliveredtechnologies.rulebook.FactMap;
import com.deliveredtechnologies.rulebook.NameValueReferableMap;
import com.deliveredtechnologies.rulebook.model.RuleBook;
import com.deliveredtechnologies.rulebook.model.runner.RuleBookRunner;
import com.wensheng.zcc.amc.module.dao.helper.EditActionEnum;
import com.wensheng.zcc.amc.module.dao.helper.PublishStateEnum;
import com.wensheng.zcc.amc.service.ZccRulesService;
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
  public PublishStateEnum runActionAndStatus(EditActionEnum action, PublishStateEnum status) {

    NameValueReferableMap facts = new FactMap();
    facts.setValue("editAction", action);
    facts.setValue("currentStatus", status);
    ruleBook4ZccEdit = new RuleBookRunner("com.wensheng.zcc.amc.rules.zccedit");
//    ruleBook4ZccEdit.run(facts);
    ruleBook4ZccEdit.run(facts);
    ruleBook4ZccEdit.getResult().ifPresent(result->log.info(result.toString()));
    PublishStateEnum retVal = ruleBook4ZccEdit.getResult().get().getValue();
    return retVal;
  }

  @Override
  public boolean editAble(PublishStateEnum editStatus) {
    NameValueReferableMap facts = new FactMap();
    facts.setValue("editAction", EditActionEnum.ACT_SAVE);
    facts.setValue("currentStatus", editStatus);
    ruleBook4ZccEdit.run(facts);
    ruleBook4ZccEdit.getResult().ifPresent(result->log.info(result.toString()));
    PublishStateEnum retVal = ruleBook4ZccEdit.getResult().get().getValue();
    if(retVal != null){
      return true;
    }
    return false;
  }


}
