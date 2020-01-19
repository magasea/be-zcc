package com.wensheng.zcc.log.controller;

import com.wensheng.zcc.log.module.dao.mongo.AmcUserLogin;
import com.wensheng.zcc.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/amc/zcc/log")
public class LogController {

  @Autowired
  LogService logService;

  @RequestMapping(value = "/userlogin/record", method = RequestMethod.POST)
  public AmcUserLogin getUserLoginRecord(@RequestParam Long userSsoId){
    return logService.getUserLoginRecord(userSsoId);
  }

}
