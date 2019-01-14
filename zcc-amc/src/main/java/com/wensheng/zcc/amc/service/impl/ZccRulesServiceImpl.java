package com.wensheng.zcc.amc.service.impl;

import com.deliveredtechnologies.rulebook.FactMap;
import com.deliveredtechnologies.rulebook.NameValueReferableMap;
import com.deliveredtechnologies.rulebook.model.RuleBook;
import com.wensheng.zcc.amc.module.dao.helper.EditActionEnum;
import com.wensheng.zcc.amc.module.dao.helper.EditStatusEnum;
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
  RuleBook<EditStatusEnum> ruleBook4ZccEdit;

  @Override
  public EditStatusEnum runActionAndStatus(EditActionEnum action, EditStatusEnum status) {

    NameValueReferableMap facts = new FactMap();
    facts.setValue("editAction", action);
    facts.setValue("currentStatus", status);
    ruleBook4ZccEdit.run(facts);
    ruleBook4ZccEdit.getResult().ifPresent(result->log.info(result.toString()));
    EditStatusEnum retVal = ruleBook4ZccEdit.getResult().get().getValue();
    return retVal;
  }
}
