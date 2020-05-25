package com.wensheng.zcc.amc.controller;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.*;
import com.wensheng.zcc.amc.service.AmcExcelFileService;
import com.wensheng.zcc.amc.service.AmcExcelPreCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author chenwei on 1/3/19
 * @project zcc-backend
 */
@Controller
@Slf4j
@RequestMapping("/api")
public class AmcExcelPreCheckController {

  @Autowired
  AmcExcelPreCheckService amcExcelPreCheckService;

  @Autowired
  AmcExcelFileService amcExcelFileService;



  @RequestMapping(value = "/amcid/{amcid}/getExcelAssets", method = RequestMethod.POST)
  @ResponseBody

  public List<AmcAssetPre> getAmcAssets() throws Exception {
    AmcAssetPreExample amcAssetPreExample = new AmcAssetPreExample();
    List<AmcAssetPre> amcAssetPres = amcExcelPreCheckService.getAllPreAssets(amcAssetPreExample);
    return amcAssetPres;
  }


  @RequestMapping(value = "/amcid/{amcid}/getExcelDebts", method = RequestMethod.POST)
  @ResponseBody

  public List<AmcDebtPre> getAmcDebts() throws Exception {
    AmcDebtPreExample amcDebtPreExample = new AmcDebtPreExample();
    List<AmcDebtPre> amcDebtPres = amcExcelPreCheckService.getAllPreDebts(amcDebtPreExample);
    return amcDebtPres;
  }

  @RequestMapping(value = "/amcid/{amcid}/sendToAmcFormalDB", method = RequestMethod.POST)
  @ResponseBody
  public boolean sendToAmcFormalDB() throws Exception {
    AmcDebtPreExample amcDebtPreExample = new AmcDebtPreExample();
    List<AmcDebtPre> amcDebtPres = amcExcelPreCheckService.getAllPreDebts(amcDebtPreExample);
    boolean transferResult  = amcExcelPreCheckService.transferDebtPre2Debt(amcDebtPres);
    if(!transferResult){
      log.error("Failed to transferDebtPre2Debt");
      return transferResult;
    }
    AmcAssetPreExample amcAssetPreExample = new AmcAssetPreExample();
    List<AmcAssetPre> amcAssetPres = amcExcelPreCheckService.getAllPreAssets(amcAssetPreExample);
    transferResult = amcExcelPreCheckService.transferAssetPre2Asset(amcAssetPres);
    return transferResult;
  }
  @RequestMapping(value = "/api/amcid/{amcid}/debt/excel/patchDebtClue", headers = "Content-Type= multipart/form-data",method =
      RequestMethod.POST)
  @ResponseBody
  public void patchDebtClue (@PathVariable Long amcid,
      @RequestParam("excel") MultipartFile excelFile) throws Exception {


//    MultipartFile[] uploadingImages = debtImageBaseActionVo.getContent().getMultipartFiles();
    amcExcelFileService.handleMultiPartFilePatchDebtClue(excelFile);


  }

}
