package com.wensheng.zcc.cust.controller;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfo;
import com.wensheng.zcc.cust.module.helper.CustTypeEnum;
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
@RequestMapping("/amc/cust/trdinfo")
public class TradInfoController {


  @Autowired
  TrdInfoService trdInfoService;

  @Autowired
  SyncService syncService;


  @Autowired
  SyncBidService syncBidService;


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
    List<CustTrdInfo> result = trdInfoService.getTrdInfo(custId, custTypeEnum.getId());
    return result;
  }

  @RequestMapping(value = "/doSynchronization", method = RequestMethod.POST)
  @ResponseBody
  public String doSynchronization(@RequestBody List<String> provinces) throws Exception {
    return syncService.syncWithTrdInfo(provinces);
  }

  @RequestMapping(value = "/doBidSynchronization", method = RequestMethod.POST)
  @ResponseBody
  public String doBidSynchronization(@RequestBody List<String> provinces) throws Exception {
    return syncBidService.syncWithTrdInfo(provinces);
  }
  @RequestMapping(value = "/makeUpData/trdDate", method = RequestMethod.GET)
  @ResponseBody
  public void makeupDataTrdDate() throws Exception {
    syncService.makeUpDataForMissDateOfTrade();
  }
  @RequestMapping(value = "/makeUpData/province", method = RequestMethod.GET)
  @ResponseBody
  public void makeupDataProvince() throws Exception {
    syncService.makeCheckProvinceCodeOfTrade();
  }


  @RequestMapping(value = "/makeUpData/companyById", method = RequestMethod.POST)
  @ResponseBody
  public void makeUpBuyerCmpyInfo(@RequestBody List<String> ids) throws Exception {
    if(!CollectionUtils.isEmpty(ids)){
      for(String id: ids){
        syncService.updateBuyerCompanyInfoByIds(id);
      }
    }
  }

  @RequestMapping(value = "/refactData/patchSellName", method = RequestMethod.POST)
  @ResponseBody
  public void patchSellName() throws Exception {
    syncService.updateSellerNameFromCTS();
  }

  @RequestMapping(value = "/refactData/patchTrdUrl", method = RequestMethod.POST)
  @ResponseBody
  public void patchTrdUrl() throws Exception {
    syncService.patchTrdUrl();
  }

  @RequestMapping(value = "/refactData/patchTrdContactor", method = RequestMethod.POST)
  @ResponseBody
  public void patchTrdContactor() throws Exception {
    syncService.makeUpBuyerContactorOfTrade();
  }

  @RequestMapping(value = "/refactData/patchTrdContactorPhone", method = RequestMethod.POST)
  @ResponseBody
  public void patchTrdContactorPhone() throws Exception {
    trdInfoService.patchPhoneAndAddress();
  }
}
