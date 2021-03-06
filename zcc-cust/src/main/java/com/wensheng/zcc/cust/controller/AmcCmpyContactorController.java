package com.wensheng.zcc.cust.controller;

import com.wensheng.zcc.cust.config.aop.AddTraceLogId;
import com.wensheng.zcc.cust.config.aop.ModifyCheckerCustCmpycontactor;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcCmpycontactor;
import com.wensheng.zcc.cust.module.vo.CustAmcCmpycontactorExtVo;
import com.wensheng.zcc.cust.module.vo.CustAmcCmpycontactorTrdInfoVo;
import com.wensheng.zcc.cust.module.vo.MergeCustVo;
import com.wensheng.zcc.cust.module.vo.helper.MergeCustRestult;
import com.wensheng.zcc.cust.module.vo.helper.ModifyResult;
import com.wensheng.zcc.cust.service.AmcContactorService;
import com.wensheng.zcc.cust.service.SyncBidService;
import com.wensheng.zcc.cust.service.SyncService;
import com.wensheng.zcc.cust.service.TrdInfoService;
import java.util.List;
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
@AddTraceLogId
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



  @RequestMapping(value = "/addCmpyAmcContactor", method = RequestMethod.POST)
  @ResponseBody
  @ModifyCheckerCustCmpycontactor
  public ModifyResult addCmpyAmcContactor(@RequestBody CustAmcCmpycontactor custAmcCmpycontactor)
      throws Exception {
    amcContactorService.createAmcCmpyContactor(custAmcCmpycontactor);
    ModifyResult modifyResult = new ModifyResult(custAmcCmpycontactor);
    return modifyResult;
  }

  @RequestMapping(value = "/updateCmpyAmcContactor", method = RequestMethod.POST)
  @ResponseBody
  @ModifyCheckerCustCmpycontactor
  public ModifyResult updateCmpyAmcContactor(@RequestBody CustAmcCmpycontactor custAmcCmpycontactor) throws Exception{
   return amcContactorService.updateAmcCmpyContactor(custAmcCmpycontactor);
  }

  @RequestMapping(value = "/mergeCmpyAmcContactor", method = RequestMethod.POST)
  @ResponseBody
  public MergeCustRestult mergeCmpyAmcContactor(@RequestBody MergeCustVo mergeCustVo) {
    MergeCustRestult mergeCustRestult = new MergeCustRestult();
    mergeCustRestult.setSuccess(true);
    try {
      amcContactorService.mergeCmpycontactor(mergeCustRestult, mergeCustVo);
    } catch (Exception e) {
      mergeCustRestult.setSuccess(false);
      log.error("合并失败");
    }
    return mergeCustRestult;
  }


  @RequestMapping(value = "/getCmpyAmcContactorDetails", method = RequestMethod.POST)
  @ResponseBody
  public CustAmcCmpycontactorTrdInfoVo getCmpyAmcContactorDetails(@RequestBody Long cmpyContactorId){
    return amcContactorService.getCmpyAmcContactorDetail(cmpyContactorId);
  }


  @RequestMapping(value = "/getCmpyAmcContactor", method = RequestMethod.POST)
  @ResponseBody
  public List<CustAmcCmpycontactorExtVo> getCmpyAmcContactor(@RequestBody Long cmpyId){
    return amcContactorService.getCmpyAmcContactorNew(cmpyId);
  }

  @RequestMapping(value = "/getContactorByIdlist", method = RequestMethod.POST)
  @ResponseBody
  public List<CustAmcCmpycontactor> getContactorByIdlist(@RequestBody List<Long> idList){
    return amcContactorService.selectContactorByIdlist(idList);
  }

//  @RequestMapping(value = "/initCmpyAmcContactor", method = RequestMethod.POST)
//  @ResponseBody
//  public void initCmpyAmcContactor(){
//     amcContactorService.initCmpyAmcContactor();
//  }

  @RequestMapping(value = "/refactData/patchCmpycontactorRevisePhone", method = RequestMethod.POST)
  @ResponseBody
  public void patchCmpycontactorRevisePhone() throws Exception {
    amcContactorService.patchCmpycontactorRevisePhone();
  }
}
