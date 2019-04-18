package com.wensheng.zcc.cust.controller;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfo;
import com.wensheng.zcc.cust.service.TrdInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chenwei on 4/15/19
 * @project miniapp-backend
 */
@Controller
@Slf4j
@RequestMapping("/amc/cust/trdinfo")
public class TradInfoController {


  @Autowired
  TrdInfoService trdInfoService;


  @RequestMapping(value = "/addTrdInfo", method = RequestMethod.POST)
  @ResponseBody
  public CustTrdInfo addTradInfo(@RequestBody CustTrdInfo custTrdInfo){
    return trdInfoService.addTrdInfo(custTrdInfo);
  }


}
