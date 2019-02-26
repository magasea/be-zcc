package com.wensheng.zcc.sso.service;

import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUser;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  List<AmcUser> getUserByPhone(String phoneNum);

  List<String> getPermissions(AmcUser amcUser);

  /**
   *
   * @param amcUser
   * @return default permissions for default amc user
   */
  AmcUser createDefaultAmcUser(AmcUser amcUser);

  Map<String, String> getTokens(AmcUser amcUser);

}
