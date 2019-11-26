package com.wensheng.zcc.amc.service;

import com.wensheng.zcc.common.params.sso.SSOAmcUser;

public interface AmcContactorService {

  void syncContactorWithSSO();

  void syncContactorWithNewUser(SSOAmcUser amcUser);
}
