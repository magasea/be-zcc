package com.wensheng.zcc.sso.service;

import java.util.List;

/**
 * @author chenwei on 3/14/19
 * @project zcc-backend
 */
public interface AmcUserService {

  public void modifyUserRole(Long userId, List<Long> roleIds);

}
