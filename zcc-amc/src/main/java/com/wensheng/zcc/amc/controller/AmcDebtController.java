package com.wensheng.zcc.amc.controller;

import com.wensheng.zcc.amc.controller.helper.PageInfo;
import com.wensheng.zcc.amc.controller.helper.QueryParam;
import com.wensheng.zcc.amc.controller.helper.SortInfo;
import com.wensheng.zcc.amc.service.AmcDebtService;
import com.wensheng.zcc.amc.service.AmcOssFileService;

import com.wensheng.zcc.amc.module.vo.AmcDebtVo;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author chenwei on 1/3/19
 * @project zcc-backend
 */
@Controller
@Slf4j
public class AmcDebtController {

  @Autowired
  AmcOssFileService amcOssFileService;

  @Autowired
  AmcDebtService amcDebtService;

  @RequestMapping(value = "/api/amcid/{amcId}/debt/image", method = RequestMethod.POST)
  @ResponseBody
  public String uploadDebtImage(@PathVariable(name = "amcId") Integer amcId, @RequestParam("debtId") Long debtId,
      @RequestParam("desc") String desc, @RequestParam("imageClass") Integer imageClass,
      @RequestParam("uploadingImages") MultipartFile[] uploadingImages){

    List<String> filePaths = new ArrayList<>();
    for(MultipartFile uploadedImage : uploadingImages) {
      try {
        String filePath = amcOssFileService.handleMultiPartImage(uploadedImage, debtId, "debt" );
        filePaths.add(filePath);
      } catch (Exception e) {
        e.printStackTrace();
        throw new ResponseStatusException(HttpStatus.MULTI_STATUS,e.getStackTrace().toString());
      }
    }
    String prePath = "debt/"+debtId+"/";

    for(String filePath: filePaths){
      try {
        String ossPath =  amcOssFileService.handleFile2Oss(filePath, prePath);
        amcDebtService.saveImageInfo(ossPath, filePath, debtId, desc, imageClass );
      } catch (Exception e) {
        e.printStackTrace();
        throw new ResponseStatusException(HttpStatus.MULTI_STATUS,e.getStackTrace().toString());
      }

    }


    return "successed";

  }


  @RequestMapping(value = "/api/amcid/{id}/debt/create", method = RequestMethod.POST)
  @ResponseBody
  public String createDebt(@RequestBody AmcDebtVo debtVo){

      return "succed";
  }


  @RequestMapping(value = "/api/amcid/{id}/debts", method = RequestMethod.POST)
  @ResponseBody
  public List<AmcDebtVo> queryDebts(@RequestBody QueryParam queryParam)
      throws Exception {
    Map<String, Integer> orderByParam = new HashMap<>();
    if(queryParam.getSortInfo() != null){
      for(SortInfo sortInfoItem: queryParam.getSortInfo()){
        orderByParam.put(sortInfoItem.getFieldName(), sortInfoItem.getDescOrAsc());
      }
    }

    List<AmcDebtVo> queryResults;
    try{
      queryResults = amcDebtService.queryAllExt(queryParam.getPageInfo().getOffset(), queryParam.getPageInfo().getSize(), orderByParam);
    }catch (Exception ex){
      log.error("got error when query:"+ex.getMessage());
      throw ex;
    }


    return queryResults;
  }


}
