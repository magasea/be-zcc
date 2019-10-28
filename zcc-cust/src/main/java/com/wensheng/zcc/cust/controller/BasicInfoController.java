package com.wensheng.zcc.cust.controller;

import com.wensheng.zcc.common.params.AmcBranchLocationEnum;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustRegion;
import com.wensheng.zcc.cust.module.helper.AgeRangeEnum;
import com.wensheng.zcc.cust.module.helper.CustTypeEnum;
import com.wensheng.zcc.cust.module.helper.InvestScaleEnum;
import com.wensheng.zcc.cust.module.helper.InvestTypeEnum;
import com.wensheng.zcc.cust.module.helper.PersonSexEnum;
import com.wensheng.zcc.cust.service.BasicInfoService;
import com.wensheng.zcc.cust.service.GeoInfoService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

  @Autowired
  BasicInfoService basicInfoService;

  @Autowired
  GeoInfoService geoInfoService;

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

  @RequestMapping(value = "/sex", method = RequestMethod.POST)
  @ResponseBody
  public List<String> getSex(){

    List<String> result = new ArrayList<>();
    for(PersonSexEnum personSexEnum : PersonSexEnum.values()){
      result.add(String.format("%d:%s:%s", personSexEnum.getId(), personSexEnum.getName(), personSexEnum.getCname()));
    }
    return result;
  }

  @RequestMapping(value = "/custTypes", method = RequestMethod.POST)
  @ResponseBody
  public List<String> getCustTypes(){

    List<String> result = new ArrayList<>();
    for(CustTypeEnum custTypeEnum : CustTypeEnum.values()){
      result.add(String.format("%d:%s", custTypeEnum.getId(), custTypeEnum.getName()));
    }
    return result;
  }

  @RequestMapping(value = "/regions", method = RequestMethod.POST)
  @ResponseBody
  public List<CustRegion> getRegions(@RequestParam(required = false) Long regionId){
    if(regionId == null){
      return basicInfoService.getAllCustRegion();
    }else{
      return basicInfoService.getSubCustRegion(regionId);
    }

  }

  @PreAuthorize("hasAnyRole('SYSTEM_ADMIN','CO_ADMIN','AMC_LOCAL_ADMIN')")
  @RequestMapping(value = "/searchGeoInfo4Trd", method = RequestMethod.POST)
  @ResponseBody
  public void searchGeoInfo4Trd(){
    geoInfoService.searchGeoInfoForTrd();

  }
}
