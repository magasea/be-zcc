package com.wensheng.zcc.cust.controller;

import com.wensheng.zcc.cust.config.aop.QueryChecker;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcCmpycontactor;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfo;
import com.wensheng.zcc.cust.module.helper.CustTypeEnum;
import com.wensheng.zcc.cust.service.AmcContactorService;
import com.wensheng.zcc.cust.service.SyncBidService;
import com.wensheng.zcc.cust.service.SyncService;
import com.wensheng.zcc.cust.service.TrdInfoService;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
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
@Slf4j
@RequestMapping("/amc/cust/amcContactor")
public class AmcCmpyContactorController {


  @Autowired
  TrdInfoService trdInfoService;

  @Autowired
  SyncService syncService;


  @Autowired
  SyncBidService syncBidService;

  @Autowired
  AmcContactorService amcContactorService;

  private volatile ConcurrentHashMap<String, String> province

  @QueryChecker
  @RequestMapping(value = "/addCmpyAmcContactor", method = RequestMethod.POST)
  @ResponseBody
  public void addCmpyAmcContactor(@RequestBody CustAmcCmpycontactor custAmcCmpycontactor, String cmpyProvinceCode){
    amcContactorService.createAmcCmpyContactor(custAmcCmpycontactor);
  }


  @QueryChecker
  @RequestMapping(value = "/updateCmpyAmcContactor", method = RequestMethod.POST)
  @ResponseBody
  public void updateCmpyAmcContactor(@RequestBody CustAmcCmpycontactor custAmcCmpycontactor, String cmpyProvinceCode){
    amcContactorService.updateAmcCmpyContactor(custAmcCmpycontactor);
  }


  @RequestMapping(value = "/getCmpyAmcContactor", method = RequestMethod.POST)
  @ResponseBody
  public List<CustAmcCmpycontactor> getCmpyAmcContactor(@RequestBody String cmpyName){
    return amcContactorService.getCmpyAmcContactor(cmpyName);
  }


}
