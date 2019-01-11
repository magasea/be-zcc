package com.wensheng.zcc.amc.controller;

import com.wensheng.zcc.amc.controller.helper.PageInfo;
import com.wensheng.zcc.amc.controller.helper.PageReqRepHelper;
import com.wensheng.zcc.amc.module.vo.AmcDebtCreateVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtVo;
import com.wensheng.zcc.amc.service.AmcDebtService;
import com.wensheng.zcc.amc.service.AmcOssFileService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author chenwei on 1/3/19
 * @project zcc-backend
 */
@RestController
@Slf4j
public class AmcDebtController {

  @Autowired
  AmcOssFileService amcOssFileService;

  @Autowired
  AmcDebtService amcDebtService;

  @RequestMapping(value = "/api/amcid/{amcId}/debt/image", method = RequestMethod.POST)
  @ResponseBody
  public String uploadDebtImage(@PathVariable(name = "amcId") Integer amcId, @RequestParam(value = "debtId",
      required = true) Long debtId,
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
  public String createDebt(@RequestBody AmcDebtCreateVo createVo){

      return "succed";
  }


  @RequestMapping(value = "/api/amcid/{id}/debts", method = RequestMethod.POST)
  @ResponseBody
  public Page<AmcDebtVo> queryDebts(@RequestBody PageInfo pageable)
      throws Exception {
    Map<String, Sort.Direction> orderByParam = PageReqRepHelper.getOrderParam(pageable);
    if(CollectionUtils.isEmpty(orderByParam)){
      orderByParam.put("id", Direction.DESC);
    }


    List<AmcDebtVo> queryResults;
    int offset = PageReqRepHelper.getOffset(pageable);
    try{
      queryResults = amcDebtService.queryAllExt(Long.valueOf(offset), pageable.getSize(), orderByParam);
    }catch (Exception ex){
      log.error("got error when query:"+ex.getMessage());
      throw ex;
    }
    Long totalCount = amcDebtService.getTotalCount();

    Page<AmcDebtVo> page = PageReqRepHelper.getPageResp(totalCount, queryResults, pageable);


    return page;
  }


}
