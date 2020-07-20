package com.wensheng.zcc.amc.controller;

import com.wensheng.zcc.amc.module.dao.helper.ImagePathClassEnum;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtContactor;
import com.wensheng.zcc.amc.service.AmcContactorService;
import com.wensheng.zcc.amc.service.AmcOssFileService;
import com.wensheng.zcc.amc.service.AmcSSOContactorService;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.AmcExceptions;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/debtContactor")
public class AmcDebtContactorController {


  @Autowired
  AmcContactorService amcContactorService;

  @Autowired
  AmcOssFileService amcOssFileService;

  @Autowired
  AmcSSOContactorService amcSSOContactorService;

  @RequestMapping(value = "/getAll", method = RequestMethod.GET)
  @ResponseBody
  public List<AmcDebtContactor> getAllContactor(
      ) throws Exception {
    return  amcContactorService.getAllDebtContactor();
  }

  @RequestMapping(value = "/getContactors", method = RequestMethod.GET)
  @ResponseBody
  public List<AmcDebtContactor> getContactors(
  ) throws Exception {
    return  amcContactorService.getDebtContactors();
  }
  @RequestMapping(value = "/checkContactorWithSSOUser", method = RequestMethod.GET)
  @ResponseBody
  public void initContactor(){

    amcSSOContactorService.checkContactorWithSSOUser();
  }




  @RequestMapping(value = "/addContactor", method = RequestMethod.POST)
  @ResponseBody
  public AmcDebtContactor addContactor(@RequestBody AmcDebtContactor amcDebtContactor)
      throws Exception {

    return  amcContactorService.addContactor(amcDebtContactor);
  }


  @RequestMapping(value = "/delContactor", method = RequestMethod.POST)
  @ResponseBody
  public boolean delContactor(@RequestBody Long amcDebtContactorId) throws Exception {

    return  amcContactorService.deleteContactor(amcDebtContactorId);
  }


  @RequestMapping(value = "/updateContactor", method = RequestMethod.POST)
  @ResponseBody
  public AmcDebtContactor updateContactor(@RequestBody AmcDebtContactor amcDebtContactor){

    return  amcContactorService.updateContactor(amcDebtContactor);
  }


  @RequestMapping(value = "personImage/add", headers = "Content-Type= multipart/form-data",method =
      RequestMethod.POST)
  @ResponseBody
  public AmcDebtContactor uploadPersonImage(
      @RequestParam("contactorId") Long amcDebtContactorId,
      @RequestParam("images") MultipartFile uploadingImage) throws Exception {
    if (amcDebtContactorId == null || amcDebtContactorId < 0) {
      throw ExceptionUtils.getAmcException(AmcExceptions.MISSING_MUST_PARAM, String.format("amcDebtContactorId %s is not valid",
          amcDebtContactorId));
    }

//    MultipartFile[] uploadingImages = debtImageBaseActionVo.getContent().getMultipartFiles();
    List<String> filePaths = new ArrayList<>();

    try {
      String filePath = amcOssFileService
          .handleMultiPartFile(uploadingImage, amcDebtContactorId, ImagePathClassEnum.CONTACTORIMG.getName());
      filePaths.add(filePath);

      String prePath = amcContactorService.getDebtContactorOssPrePath(ImagePathClassEnum.CONTACTORIMG.getName(), amcDebtContactorId);

      return amcContactorService.uploadContactorImage(filePath, prePath, amcDebtContactorId,
          ImagePathClassEnum.CONTACTORIMG.getName());
    } catch (Exception e) {
      e.printStackTrace();
      throw new ResponseStatusException(HttpStatus.MULTI_STATUS, e.getStackTrace().toString());
    }


  }


  @RequestMapping(value = "wxImage/add", headers = "Content-Type= multipart/form-data",method =
      RequestMethod.POST)
  @ResponseBody
  public AmcDebtContactor uploadWXImage(
      @RequestParam("contactorId") Long amcDebtContactorId,
      @RequestParam("images") MultipartFile uploadingImage) throws Exception {
    if (amcDebtContactorId == null || amcDebtContactorId < 0) {
      throw ExceptionUtils.getAmcException(AmcExceptions.MISSING_MUST_PARAM, String.format("amcDebtContactorId %s is not valid",
          amcDebtContactorId));
    }

//    MultipartFile[] uploadingImages = debtImageBaseActionVo.getContent().getMultipartFiles();
    List<String> filePaths = new ArrayList<>();

    try {
      String filePath = amcOssFileService
          .handleMultiPartFile(uploadingImage, amcDebtContactorId, ImagePathClassEnum.CONTACTORWXIMG.getName());
      filePaths.add(filePath);

      String prePath = amcContactorService.getDebtContactorOssPrePath(ImagePathClassEnum.CONTACTORWXIMG.getName(), amcDebtContactorId);

      return amcContactorService.uploadContactorImage(filePath, prePath, amcDebtContactorId, ImagePathClassEnum.CONTACTORWXIMG.getName());
    } catch (Exception e) {
      e.printStackTrace();
      throw new ResponseStatusException(HttpStatus.MULTI_STATUS, e.getStackTrace().toString());
    }


  }

}