package com.wensheng.zcc.cust.controller;

import com.wensheng.zcc.common.params.AmcBranchLocationEnum;
import com.wensheng.zcc.cust.module.helper.AgeRangeEnum;
import com.wensheng.zcc.cust.module.helper.InvestScaleEnum;
import com.wensheng.zcc.cust.module.helper.InvestTypeEnum;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chenwei on 4/16/19
 * @project miniapp-backend
 */
@Controller
@RequestMapping("/amc/cust/basic")
public class BasicInfoController {

  @RequestMapping(value = "/investScales", method = RequestMethod.POST)
  @ResponseBody
  public List<String> getInvestScales(){

    List<String> result = new ArrayList<>();
    for(InvestScaleEnum investScaleEnum : InvestScaleEnum.values()){
      result.add(String.format("%d:%s", investScaleEnum.getId(), investScaleEnum.getName()));
    }
    return result;
  }


  @RequestMapping(value = "/investTypes", method = RequestMethod.POST)
  @ResponseBody
  public List<String> getInvestTypes(){

    List<String> result = new ArrayList<>();
    for(InvestTypeEnum investTypeEnum : InvestTypeEnum.values()){
      result.add(String.format("%d:%s", investTypeEnum.getId(), investTypeEnum.getName()));
    }
    return result;
  }

  @RequestMapping(value = "/ageRange", method = RequestMethod.POST)
  @ResponseBody
  public List<String> getAgeRange(){

    List<String> result = new ArrayList<>();
    for(AgeRangeEnum ageRangeEnum : AgeRangeEnum.values()){
      result.add(String.format("%d:%s:%s", ageRangeEnum.getId(), ageRangeEnum.getName(), ageRangeEnum.getCname()));
    }
    return result;
  }
}
