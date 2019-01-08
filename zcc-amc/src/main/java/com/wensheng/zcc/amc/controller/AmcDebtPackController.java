package com.wensheng.zcc.amc.controller;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtpack;
import com.wensheng.zcc.amc.module.vo.AmcDebtpackVo;
import com.wensheng.zcc.amc.service.AmcDebtpackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    return null;
  }



}
