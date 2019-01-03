package com.wensheng.zcc.amc.controller;

import com.wensheng.zcc.amc.service.AmcOssFileService;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
public class AmcDebtController {

  @Autowired
  AmcOssFileService amcOssFileService;

  @RequestMapping(value = "/api/amcid/{id}/debt/image", method = RequestMethod.POST)
  @ResponseBody
  public String uploadDebtImage(@PathVariable Integer amcId, @RequestParam("debtId") Long debtId,
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
    for(String filePath: filePaths){
      try {
        amcOssFileService.handleFile2Oss(filePath);
      } catch (Exception e) {
        e.printStackTrace();
        throw new ResponseStatusException(HttpStatus.MULTI_STATUS,e.getStackTrace().toString());
      }

    }


    return "successed";

  }


}
