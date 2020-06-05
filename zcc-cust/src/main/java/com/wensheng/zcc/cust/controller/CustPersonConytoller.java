package com.wensheng.zcc.cust.controller;

import com.wensheng.zcc.cust.config.aop.AddTraceLogId;
import com.wensheng.zcc.cust.config.aop.ModifyCheckerCustCmpycontactor;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcCmpycontactor;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import com.wensheng.zcc.cust.service.CustPersonService;
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
@AddTraceLogId
@RequestMapping("/amc/cust/custPersonConytoller")
public class CustPersonConytoller {

  @Autowired
  CustPersonService custPersonService;

  @RequestMapping(value = "/addCmpyAmcContactor", method = RequestMethod.POST)
  @ResponseBody
  public void addCustPerson(@RequestBody CustTrdPerson custTrdPerson) throws Exception {
    custPersonService.createCustPerson(custTrdPerson);
  }

  @RequestMapping(value = "/updateCustPerson", method = RequestMethod.POST)
  @ResponseBody
  public void updateCustPerson(@RequestBody CustTrdPerson custTrdPerson) throws Exception {
    custPersonService.updateCustPerson(custTrdPerson);
  }

  @RequestMapping(value = "/mergeCustPerson", method = RequestMethod.POST)
  @ResponseBody
  public void mergeCustPerson(@RequestBody List<Long> fromPersonIds,@RequestBody Long toPersonId) throws Exception {
    custPersonService.mergeCustPerson(fromPersonIds, toPersonId);
  }
}
