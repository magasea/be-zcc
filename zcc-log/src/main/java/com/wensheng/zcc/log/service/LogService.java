package com.wensheng.zcc.log.service;

import com.wensheng.zcc.log.module.dao.mongo.AmcUserLogin;

public interface LogService {

  AmcUserLogin getUserLoginRecord(Long userSsoId);

}
