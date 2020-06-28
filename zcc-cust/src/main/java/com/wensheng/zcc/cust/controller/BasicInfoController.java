package com.wensheng.zcc.cust.controller;

import com.wensheng.zcc.cust.config.aop.AddTraceLogId;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcUserpriv;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustRegionDetail;
import com.wensheng.zcc.cust.module.helper.AgeRangeEnum;
import com.wensheng.zcc.cust.module.helper.CustTypeEnum;
import com.wensheng.zcc.cust.module.helper.InvestScaleEnum;
import com.wensheng.zcc.cust.module.helper.ItemTypeEnum;
import com.wensheng.zcc.cust.module.helper.PersonSexEnum;
import com.wensheng.zcc.cust.module.helper.SyncTrdTypeEnum;
import com.wensheng.zcc.cust.module.helper.sync.BidTypeEnum;
import com.wensheng.zcc.cust.service.BasicInfoService;
import com.wensheng.zcc.cust.service.GeoInfoService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chenwei on 4/16/19
 * @project miniapp-backend
 */
@Controller
@AddTraceLogId
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
    for(ItemTypeEnum itemTypeEnum : ItemTypeEnum.values()){
      result.add(String.format("%d:%s", itemTypeEnum.getId(), itemTypeEnum.getName()));
    }
    return result;
  }

  @RequestMapping(value = "/itemSubType", method = RequestMethod.POST)
  @ResponseBody
  public List<String> getItemSubType(){

    List<String> result = new ArrayList<>();
    for(BidTypeEnum bidTypeEnum : BidTypeEnum.values()){
      result.add(String.format("%d:%s", bidTypeEnum.getId(), bidTypeEnum.getCname()));
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

  @RequestMapping(value = "/trdTypes", method = RequestMethod.POST)
  @ResponseBody
  public List<String> getTrdTypes(){

    List<String> result = new ArrayList<>();
    for(SyncTrdTypeEnum syncTrdTypeEnum : SyncTrdTypeEnum.values()){
      result.add(String.format("%d:%s", syncTrdTypeEnum.getId(), syncTrdTypeEnum.getName()));
    }
    return result;
  }

  @RequestMapping(value = "/regions", method = RequestMethod.POST)
  @ResponseBody
  public List<CustRegionDetail> getRegions(@RequestParam(required = false) Long regionId){
    if(regionId == null){
      return basicInfoService.getAllCustRegion();
    }else{
      return basicInfoService.getSubCustRegion(regionId);
    }

  }

  @RequestMapping(value = "/regionsByName", method = RequestMethod.POST)
  @ResponseBody
  public List<CustRegionDetail> getRegionByName(@RequestBody(required = false) String name){
    if(name == null){
      return basicInfoService.getAllCustRegion();
    }else{
      return basicInfoService.getRegionByName(name);
    }

  }

  @RequestMapping(value = "/regionsById", method = RequestMethod.GET)
  @ResponseBody
  public CustRegionDetail getRegionById(@RequestParam(required = false) Long id){
    if(id == null){
      return null;
    }else{
      return basicInfoService.getRegionById(id);
    }

  }

  @PreAuthorize("hasAnyRole('SYSTEM_ADMIN','CO_ADMIN','AMC_LOCAL_ADMIN')")
  @RequestMapping(value = "/searchGeoInfo4Trd", method = RequestMethod.POST)
  @ResponseBody
  public void searchGeoInfo4Trd(){
    geoInfoService.searchGeoInfoForTrd();

  }

//  @PreAuthorize("hasAnyRole('SYSTEM_ADMIN','CO_ADMIN','AMC_LOCAL_ADMIN')")
  @RequestMapping(value = "/getAmcUserPriv", method = RequestMethod.POST)
  @ResponseBody
  public List<CustAmcUserpriv> getAmcUserPriv(){
    return basicInfoService.getAmcUserPriv();
  }

  @RequestMapping(value = "/createOrUpdateAmcUserPriv", method = RequestMethod.POST)
  @ResponseBody
  public void createOrUpdateAmcUserPriv(@RequestBody List<CustAmcUserpriv> custAmcUserprivs){
     basicInfoService.createOrUpdateAmcUserPriv(custAmcUserprivs);
  }
}
