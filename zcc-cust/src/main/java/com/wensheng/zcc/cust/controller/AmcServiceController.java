package com.wensheng.zcc.cust.controller;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.service.RecommService;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequestMapping("/amc/cust/amcService")
public class AmcServiceController {

  @Autowired
  RecommService recommService;

  @RequestMapping(value = "/queryDebtCust", method = RequestMethod.POST)
  @ResponseBody
  public List<Object> queryDebtCust(@RequestBody Long debtId){

    return recommService.queryDebtCusts(Arrays.asList(debtId));
  }

}
