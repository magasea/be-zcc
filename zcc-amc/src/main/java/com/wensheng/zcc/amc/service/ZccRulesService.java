package com.wensheng.zcc.amc.service;

import com.wensheng.zcc.amc.module.dao.helper.EditActionEnum;
import com.wensheng.zcc.amc.module.dao.helper.PublishStateEnum;

/**
 * @author chenwei on 1/14/19
 * @project zcc-backend
 */
public interface ZccRulesService {

  public PublishStateEnum runActionAndStatus(EditActionEnum action, PublishStateEnum status);

  public boolean editAble(PublishStateEnum editStatus);

}
