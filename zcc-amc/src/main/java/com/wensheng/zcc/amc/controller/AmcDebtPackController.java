package com.wensheng.zcc.amc.controller;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtpack;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditor;
import com.wensheng.zcc.amc.module.vo.AmcDebtpackVo;
import com.wensheng.zcc.amc.service.AmcDebtpackService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chenwei on 1/7/19
 * @project zcc-backend
 */
@Controller
@RequestMapping("/api/")
public class AmcDebtPackController {

  @Autowired
  AmcDebtpackService amcDebtpackService;



  @RequestMapping(value = "amcid/{amcId}/debtpack/add", method = RequestMethod.POST)
  public AmcDebtpack createAmcDebtPack(AmcDebtpackVo amcDebtpackVo){
    amcDebtpackService.create(amcDebtpackVo);
    return null;
  }

  @RequestMapping(value = "amcid/{amcId}/deptpack/creditor/add", method = RequestMethod.POST)
  @ResponseBody
  public AmcOrigCreditor createAmcCreditor(@RequestBody AmcOrigCreditor amcOrigCreditor){
    AmcOrigCreditor amcOrigCreditorResult = amcDebtpackService.createCreditor(amcOrigCreditor);
    return amcOrigCreditorResult;

  }

  @RequestMapping(value = "amcid/{amcId}/deptpack/creditors", method = RequestMethod.GET)
  @ResponseBody
  public List<AmcOrigCreditor> getAmcCreditor(){
    List<AmcOrigCreditor> amcOrigCreditors = amcDebtpackService.getCreditors();
    return amcOrigCreditors;
  }




}
