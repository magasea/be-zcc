package com.wensheng.zcc.amc.service;

import com.wensheng.zcc.amc.module.dao.helper.EditActionEnum;
import com.wensheng.zcc.amc.module.dao.helper.EditStatusEnum;

/**
 * @author chenwei on 1/14/19
 * @project zcc-backend
 */
public interface ZccRulesService {

  public EditStatusEnum runActionAndStatus(EditActionEnum action, EditStatusEnum status);

}
