package com.wensheng.zcc.amc.service;

import com.wensheng.zcc.common.params.AmcPage;
import com.wensheng.zcc.common.params.sso.AmcLocationEnum;
import com.wensheng.zcc.common.params.sso.SSOAmcUser;
import com.wensheng.zcc.common.utils.sso.SSOQueryParam;
import java.util.List;

public interface AmcContactorService {

  void syncContactorWithSSO();

  void syncContactorWithNewUser(SSOAmcUser amcUser);

  AmcPage<SSOAmcUser> getSsoAmcUsers(SSOQueryParam ssoQueryParam);

  void initializeDebtContactor();
}
