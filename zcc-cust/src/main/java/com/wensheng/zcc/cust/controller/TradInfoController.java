package com.wensheng.zcc.cust.controller;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfo;
import com.wensheng.zcc.cust.module.helper.CustTypeEnum;
import com.wensheng.zcc.cust.service.TrdInfoService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

  @RequestMapping(value = "/getTrdInfos", method = RequestMethod.POST)
  @ResponseBody
  public List<CustTrdInfo> getTradInfos(){
    return trdInfoService.getTrdInfo();
  }

  @RequestMapping(value = "/getTrdInfoByCustId", method = RequestMethod.POST)
  @ResponseBody
  public List<CustTrdInfo> getTradInfo(@RequestParam Long custId, @RequestParam CustTypeEnum custTypeEnum){
    return trdInfoService.getTrdInfo(custId, custTypeEnum.getId());
  }
}
