package com.wensheng.zcc.amc.controller;

import com.wensheng.zcc.amc.module.dao.helper.ImagePathClassEnum;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleBanner;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleFloor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleMenu;
import com.wensheng.zcc.amc.module.vo.AmcAssetVo;
import com.wensheng.zcc.amc.module.vo.AmcSaleBannerPageVo;
import com.wensheng.zcc.amc.module.vo.AmcSaleBannerVo;
import com.wensheng.zcc.amc.module.vo.AmcSaleFloorFrontEndVo;
import com.wensheng.zcc.amc.module.vo.AmcSaleGetListByOpenId;
import com.wensheng.zcc.amc.module.vo.AmcSaleGetRandomListByOpenId;
import com.wensheng.zcc.amc.module.vo.AmcSalePageModVo;
import com.wensheng.zcc.amc.module.vo.AmcSaleFloorPageVo;
import com.wensheng.zcc.amc.module.vo.AmcSaleFloorVo;
import com.wensheng.zcc.amc.module.vo.AmcSaleHomePage;
import com.wensheng.zcc.amc.module.vo.AmcSaleMenuPageVo;
import com.wensheng.zcc.amc.module.vo.AmcSaleMenuVo;
import com.wensheng.zcc.amc.module.vo.AmcSaleWatchonPageVo;
import com.wensheng.zcc.amc.service.AmcOssFileService;
import com.wensheng.zcc.amc.service.AmcSaleService;
import com.wensheng.zcc.common.module.dto.WXUserWatchObject;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author chenwei on 1/3/19
 * @project zcc-backend
 */
@Controller
@Slf4j
@RequestMapping("/api/amc/sale")
public class AmcSaleController {



  @Autowired
  AmcSaleService amcSaleService;

  @Autowired
  AmcOssFileService amcOssFileService;


  @RequestMapping(value = "/createFloor", method = RequestMethod.POST)
  @ResponseBody
  AmcSaleFloorVo createFloor(@RequestBody AmcSaleFloorVo amcSaleFloorVo){
    return amcSaleService.createFloor(amcSaleFloorVo);
  }

  @RequestMapping(value = "/getFloors", method = RequestMethod.POST)
  @ResponseBody
  List<AmcSaleFloorVo> getAmcSaleFloors(){
    return amcSaleService.getFloors();
  }

  @RequestMapping(value = "/getFrontendFloors", method = RequestMethod.POST)
  @ResponseBody
  List<AmcSaleFloorFrontEndVo>  getFrontendFloors( ) throws Exception {
    return amcSaleService.getFrontEndFloors();
  }


  @RequestMapping(value = "/updateFloors", method = RequestMethod.POST)
  @ResponseBody
  boolean updateFloorsSeq(@RequestBody List<Long> floorIds) throws Exception {
    return amcSaleService.updateFloorSeq(floorIds);
  }

  @RequestMapping(value = "/delFloor", method = RequestMethod.POST)
  @ResponseBody
  void getAmcSaleFloors(@RequestBody Long floorId) throws Exception {
    amcSaleService.delFloor(floorId);
  }


  @RequestMapping(value = "/updateFloorWithFilter", method = RequestMethod.POST)
  @ResponseBody
  boolean  updateFloorWithFilter(@RequestBody AmcSaleFloorVo amcSaleFloor) throws Exception {
    return amcSaleService.updateFloor(amcSaleFloor);
  }

  @RequestMapping(value = "/updateFloorBasic", method = RequestMethod.POST)
  @ResponseBody
  boolean  updateFloorBasic(@RequestBody AmcSaleFloor amcSaleFloor ) throws Exception {
    return amcSaleService.updateFloorBasic(amcSaleFloor);
  }


  @RequestMapping(value = "/getSaleMenus", method = RequestMethod.POST)
  @ResponseBody
  List<AmcSaleMenuVo>  getSaleMenus( ) throws Exception {
    return amcSaleService.getSaleMenus();
  }


  @RequestMapping(value = "/updateSaleMenuWithFilter", method = RequestMethod.POST)
  @ResponseBody
  AmcSaleMenuVo  updateSaleMenuWithFilter(@RequestBody AmcSaleMenuVo amcSaleMenuVo ) throws Exception {
    return amcSaleService.updateSaleMenuVo(amcSaleMenuVo);
  }

  @RequestMapping(value = "/updateSaleMenu", method = RequestMethod.POST)
  @ResponseBody
  AmcSaleMenu  updateSaleMenu(@RequestBody AmcSaleMenu amcSaleMenu ) throws Exception {
    return amcSaleService.updateSaleMenu(amcSaleMenu);
  }
  @RequestMapping(value = "/updateSaleMenuSeq", method = RequestMethod.POST)
  @ResponseBody
  boolean  updateSaleMenuSeq(@RequestBody List<Long> menuIds ) throws Exception {
    return amcSaleService.updateSaleMenuSeq(menuIds);
  }

  @RequestMapping(value = "/delSaleMenu", method = RequestMethod.POST)
  @ResponseBody
  boolean  delSaleMenu(@RequestBody Long saleMenuId ) throws Exception {
    return amcSaleService.delSaleMenu(saleMenuId);
  }

  @RequestMapping(value = "/addSaleMenu", method = RequestMethod.POST)
  @ResponseBody
  AmcSaleMenu  addSaleMenu(@RequestBody AmcSaleMenu amcSaleMenu ) throws Exception {
    return amcSaleService.addSaleMenu(amcSaleMenu);
  }


  @RequestMapping(value = "saleMenu/image/add", headers = "Content-Type= multipart/form-data",method =
      RequestMethod.POST)
  @ResponseBody
  public AmcSaleMenu uploadSaleMenuImg(@RequestParam("saleMenuId") Long saleMenuId,
      @RequestParam("images") MultipartFile uploadingImage) throws Exception {
    if (saleMenuId == null || saleMenuId < 0) {
      throw ExceptionUtils.getAmcException(AmcExceptions.MISSING_MUST_PARAM, String.format("saleMenuId %s is not valid",
          saleMenuId));
    }

//    MultipartFile[] uploadingImages = debtImageBaseActionVo.getContent().getMultipartFiles();
    List<String> filePaths = new ArrayList<>();

    try {
      String filePath = amcOssFileService
          .handleMultiPartFile(uploadingImage, saleMenuId, ImagePathClassEnum.SALEMENU.getName());
      String ossPath = amcOssFileService.handleFile2Oss(filePath, amcSaleService.getSaleMenuPrepath(saleMenuId));
      return amcSaleService.updateSaleMenuImage(saleMenuId, ossPath);

    } catch (Exception e) {
      e.printStackTrace();
      throw new ResponseStatusException(HttpStatus.MULTI_STATUS, e.getStackTrace().toString());
    }





  }
  @RequestMapping(value = "saleMenu/pageimage/add", headers = "Content-Type= multipart/form-data",method =
      RequestMethod.POST)
  @ResponseBody
  public AmcSaleMenu uploadSaleMenuPageImg(@RequestParam("saleMenuId") Long saleMenuId,
      @RequestParam("images") MultipartFile uploadingImage) throws Exception {
    if (saleMenuId == null || saleMenuId < 0) {
      throw ExceptionUtils.getAmcException(AmcExceptions.MISSING_MUST_PARAM, String.format("saleMenuId %s is not valid",
          saleMenuId));
    }

//    MultipartFile[] uploadingImages = debtImageBaseActionVo.getContent().getMultipartFiles();
    List<String> filePaths = new ArrayList<>();

    try {
      String filePath = amcOssFileService
          .handleMultiPartFile(uploadingImage, saleMenuId, ImagePathClassEnum.SALEMENUPAGE.getName());
      String ossPath = amcOssFileService.handleFile2Oss(filePath, amcSaleService.getSaleMenuPrepath(saleMenuId));
      return amcSaleService.updateSaleMenuPageImage(saleMenuId, ossPath);

    } catch (Exception e) {
      e.printStackTrace();
      throw new ResponseStatusException(HttpStatus.MULTI_STATUS, e.getStackTrace().toString());
    }





  }


  @RequestMapping(value = "/getMenuPage", method = RequestMethod.POST)
  @ResponseBody
  AmcSaleMenuPageVo getMenuPage( @RequestBody Long menuId ) throws Exception {
    return amcSaleService.getMenuPage(menuId);
  }
  @RequestMapping(value = "/getMenuPageWithFilter", method = RequestMethod.POST)
  @ResponseBody
  AmcSaleMenuPageVo getMenuPageWithFilter( @RequestBody AmcSalePageModVo amcSalePageModVo ) throws Exception {
    return amcSaleService.getMenuPageWithFilter(amcSalePageModVo);
  }

  @RequestMapping(value = "/getSaleBanners", method = RequestMethod.POST)
  @ResponseBody
  List<AmcSaleBannerVo>  getSaleBanners( ) throws Exception {
    return amcSaleService.getSaleBanners();
  }


  @RequestMapping(value = "/updateSaleBanner", method = RequestMethod.POST)
  @ResponseBody
  AmcSaleBanner  updateSaleBanner(@RequestBody AmcSaleBanner amcSaleBanner ) throws Exception {
    return amcSaleService.updateSaleBanner(amcSaleBanner);
  }

  @RequestMapping(value = "/updateSaleBannerWithFilter", method = RequestMethod.POST)
  @ResponseBody
  AmcSaleBannerVo  updateSaleBannerWithFilter(@RequestBody AmcSaleBannerVo amcSaleBannerVo ) throws Exception {
    return amcSaleService.updateSaleBannerWithFilter(amcSaleBannerVo);
  }

  @RequestMapping(value = "/updateSaleBannerSeq", method = RequestMethod.POST)
  @ResponseBody
  boolean  updateSaleBannerSeq(@RequestBody List<Long> bannerIds ) throws Exception {
    return amcSaleService.updateBannerSeq(bannerIds);
  }


  @RequestMapping(value = "/delSaleBanner", method = RequestMethod.POST)
  @ResponseBody
  boolean  delSaleBanner(@RequestBody Long saleBannerId ) throws Exception {
    return amcSaleService.delSaleBanner(saleBannerId);
  }

  @RequestMapping(value = "/addSaleBanner", method = RequestMethod.POST)
  @ResponseBody
   AmcSaleBanner addSaleBanner(@RequestBody AmcSaleBanner amcSaleBanner ) throws Exception {
    return amcSaleService.addSaleBanner(amcSaleBanner);
  }

  @RequestMapping(value = "saleBanner/image/add", headers = "Content-Type= multipart/form-data",method =
      RequestMethod.POST)
  @ResponseBody
  public AmcSaleBanner uploadSaleBannerImg(@RequestParam("saleBannerId") Long saleBannerId,
      @RequestParam("images") MultipartFile uploadingImage) throws Exception {
    if (saleBannerId == null || saleBannerId < 0) {
      throw ExceptionUtils.getAmcException(AmcExceptions.MISSING_MUST_PARAM, String.format("saleMenuId %s is not valid",
          saleBannerId));
    }

//    MultipartFile[] uploadingImages = debtImageBaseActionVo.getContent().getMultipartFiles();
    List<String> filePaths = new ArrayList<>();

    try {
      String filePath = amcOssFileService
          .handleMultiPartFile(uploadingImage, saleBannerId, ImagePathClassEnum.SALEBANNER.getName());
      String ossPath = amcOssFileService.handleFile2Oss(filePath, amcSaleService.getSaleBannerPrepath(saleBannerId));
      return amcSaleService.updateSaleBannerImage(saleBannerId, ossPath);

    } catch (Exception e) {
      e.printStackTrace();
      throw new ResponseStatusException(HttpStatus.MULTI_STATUS, e.getStackTrace().toString());
    }
  }



  @RequestMapping(value = "/getBannerPage", method = RequestMethod.POST)
  @ResponseBody
  AmcSaleBannerPageVo getBannerPage( @RequestBody Long bannerId ) throws Exception {
    return amcSaleService.getBannerPage(bannerId);
  }

  @RequestMapping(value = "/getBannerPageWithFilter", method = RequestMethod.POST)
  @ResponseBody
  AmcSaleBannerPageVo getBannerPageWithFilter( @RequestBody AmcSalePageModVo amcSalePageModVo ) throws Exception {
    return amcSaleService.getBannerPageWithFilter(amcSalePageModVo);
  }


  @RequestMapping(value = "/getHomePage", method = RequestMethod.POST)
  @ResponseBody
  AmcSaleHomePage getSaleHomePage( ) throws Exception {
    return amcSaleService.getAmcSaleHome();
  }


  @RequestMapping(value = "/getFloorPage", method = RequestMethod.POST)
  @ResponseBody
  AmcSaleFloorPageVo getFloorPage( @RequestBody Long floorId ) throws Exception {
    return amcSaleService.getFloorPage(floorId);
  }

  @RequestMapping(value = "/getFloorPageWithFilter", method = RequestMethod.POST)
  @ResponseBody
  AmcSaleFloorPageVo getFloorPageWithFilter( @RequestBody AmcSalePageModVo amcSaleFloorPageModVo) throws Exception {
    return amcSaleService.getFloorPageWithFilter(amcSaleFloorPageModVo);
  }


  @RequestMapping(value = "/getUserWatchonPage", method = RequestMethod.POST)
  @ResponseBody
  AmcSaleWatchonPageVo getUserWatchonPage( @RequestBody List<WXUserWatchObject> wxUserWatchObjects) throws Exception {
    return amcSaleService.getUserWatchonPage(wxUserWatchObjects);
  }

  @RequestMapping(value = "/getUserWatchonPageByOpenId", method = RequestMethod.POST)
  @ResponseBody
  AmcSaleWatchonPageVo getUserWatchonPageByOpenId( @RequestBody String openId) throws Exception {
    return amcSaleService.getUserWatchonPage(openId);
  }

  @RequestMapping(value = "/getUserFavorPageByOpenId", method = RequestMethod.POST)
  @ResponseBody
  AmcSaleWatchonPageVo getUserFavorPageByOpenId( @RequestBody AmcSaleGetListByOpenId amcSaleGetListByOpenId) throws Exception {
    return amcSaleService.getUserFavorPage(amcSaleGetListByOpenId);
  }


  @RequestMapping(value = "/getUserFavorRandomPageByOpenId", method = RequestMethod.POST)
  @ResponseBody
  AmcSaleWatchonPageVo getUserFavorRandomPageByOpenId( @RequestBody AmcSaleGetRandomListByOpenId amcSaleGetRandomListByOpenId) throws Exception {
    return amcSaleService.getUserFavorRandomPage(amcSaleGetRandomListByOpenId);
  }
  @RequestMapping(value = "salefloor/pageimage/add", headers = "Content-Type= multipart/form-data",method =
      RequestMethod.POST)
  @ResponseBody
  public AmcSaleFloor uploadSaleFloorPageImg(@RequestParam("saleFloorId") Long saleFloorId,
      @RequestParam("images") MultipartFile uploadingImage) throws Exception {
    if (saleFloorId == null || saleFloorId < 0) {
      throw ExceptionUtils.getAmcException(AmcExceptions.MISSING_MUST_PARAM, String.format("saleMenuId %s is not valid",
          saleFloorId));
    }

//    MultipartFile[] uploadingImages = debtImageBaseActionVo.getContent().getMultipartFiles();
    List<String> filePaths = new ArrayList<>();

    try {
      String filePath = amcOssFileService
          .handleMultiPartFile(uploadingImage, saleFloorId, ImagePathClassEnum.SALEFLOORPAGE.getName());
      String ossPath = amcOssFileService.handleFile2Oss(filePath, amcSaleService.getSaleMenuPrepath(saleFloorId));
      return amcSaleService.updateSaleFloorPageImage(saleFloorId, ossPath);

    } catch (Exception e) {
      e.printStackTrace();
      throw new ResponseStatusException(HttpStatus.MULTI_STATUS, e.getStackTrace().toString());
    }
  }


  @RequestMapping(value = "salehome/keyword", method = RequestMethod.POST)
  @ResponseBody
  public AmcSaleWatchonPageVo keyWordSearch(@RequestBody String keyWord) throws Exception {
      return amcSaleService.getObjectsByTitle(keyWord);
  }

  @RequestMapping(value = "salefloor/initFixFloors", method = RequestMethod.POST)
  @ResponseBody
  public void initFixFloors() throws Exception {
     amcSaleService.initFixFloors();
  }
}
