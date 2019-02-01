package com.wensheng.zcc.sso.service;

import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUser;
import java.util.List;

public interface UserService {

  List<AmcUser> getUserByPhone(String phoneNum);

  List<String> getPermissions(AmcUser amcUser);

}
