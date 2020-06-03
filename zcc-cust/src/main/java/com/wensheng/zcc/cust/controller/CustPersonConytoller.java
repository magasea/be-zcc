package com.wensheng.zcc.cust.controller;

import com.wensheng.zcc.cust.config.aop.AddTraceLogId;
import com.wensheng.zcc.cust.config.aop.ModifyCheckerCustCmpycontactor;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcCmpycontactor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@AddTraceLogId
@RequestMapping("/amc/cust/custPersonConytoller")
public class CustPersonConytoller {

  @RequestMapping(value = "/addCmpyAmcContactor", method = RequestMethod.POST)
  @ResponseBody
  @ModifyCheckerCustCmpycontactor
  public void addCmpyAmcContactor(@RequestBody CustAmcCmpycontactor custAmcCmpycontactor) throws Exception {


  }

}
