package com.wensheng.zcc.cust.controller;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.service.CustInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chenwei on 4/15/19
 * @project miniapp-backend
 */
@Controller
@RequestMapping("/amc/cust/")
public class CustInfoController {

  @Autowired
  CustInfoService custInfoService;

  @RequestMapping("/addCmpy")
  @ResponseBody
  public String addCompany(CustTrdCmpy custTrdCmpy){

    custInfoService.addCompany(custTrdCmpy);
    return "succeed";
  }

}
