package com.wensheng.zcc.cust.controller;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import com.wensheng.zcc.cust.service.CustInfoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chenwei on 4/15/19
 * @project miniapp-backend
 */
@Controller
@RequestMapping("/amc/cust/custinfo")
public class CustInfoController {

  @Autowired
  CustInfoService custInfoService;

  @RequestMapping(value = "/addCmpy", method = RequestMethod.POST)
  @ResponseBody
  public CustTrdCmpy addCompany(@RequestBody CustTrdCmpy custTrdCmpy){

    return custInfoService.addCompany(custTrdCmpy);
  }

  @RequestMapping(value = "/getCmpies", method = RequestMethod.POST)
  @ResponseBody
  public List<CustTrdCmpy> getCmpies(){

      return custInfoService.getCmpies();


  }

  @RequestMapping(value = "/getPersons", method = RequestMethod.POST)
  @ResponseBody
  public List<CustTrdPerson> getPersons(){
      return custInfoService.getTrdPersons();

  }

  @RequestMapping(value = "/addPerson", method = RequestMethod.POST)
  @ResponseBody
  public CustTrdPerson addPerson(@RequestBody CustTrdPerson custTrdPerson){

     return custInfoService.addTrdPerson(custTrdPerson);
  }


}
