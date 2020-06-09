package com.wensheng.zcc.amc.service;

import com.wensheng.zcc.amc.module.dto.SSOContactorDTO;
import com.wensheng.zcc.common.params.sso.SSOAmcUser;
import java.util.List;

public interface AmcSSORpcService {

  List<SSOAmcUser> getSSOUsersByIds(List<Long> ids);
  List<SSOContactorDTO> checkSSOContactorInfo(List<SSOContactorDTO> ssoContactorDTOS);

}
