package com.wensheng.zcc.cust.controller;

import com.wensheng.zcc.cust.config.aop.AddTraceLogId;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustDataDict;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.service.CustDataDictService;
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
@RequestMapping("/amc/cust/custDataDictController")
public class CustDataDictController {

  @Autowired
  CustDataDictService custDataDictService;

  @RequestMapping(value = "/createCustDataDict(", method = RequestMethod.POST)
  @ResponseBody
  public void createCustDataDict(@RequestBody CustDataDict custDataDict) throws Exception {
    custDataDictService.createCustDataDict((custDataDict));
  }

  @RequestMapping(value = "/updateCustDataDict", method = RequestMethod.POST)
  @ResponseBody
  public void updateCustDataDict(@RequestBody CustDataDict custDataDict) throws Exception {
    custDataDictService.updateCustDataDict(custDataDict);
  }

  @RequestMapping(value = "/getParentDataDict", method = RequestMethod.POST)
  @ResponseBody
  public List<CustDataDict> getParentDataDict() throws Exception {
   return custDataDictService.getParentDataDict();
  }

  @RequestMapping(value = "/getDataDictByCode", method = RequestMethod.POST)
  @ResponseBody
  public List<CustDataDict> getDataDictByCode(@RequestBody String code) throws Exception {
    return custDataDictService.getDataDictByCode(code);
  }

}
