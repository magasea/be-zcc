package com.wensheng.zcc.amc.controller;

import com.wensheng.zcc.amc.aop.EditActionChecker;
import com.wensheng.zcc.amc.aop.LogExecutionTime;
import com.wensheng.zcc.amc.aop.QueryChecker;
import com.wensheng.zcc.amc.module.dao.helper.PublishStateEnum;
import com.wensheng.zcc.common.params.AmcPage;
import com.wensheng.zcc.common.params.PageReqRepHelper;
import com.wensheng.zcc.amc.controller.helper.QueryParam;
import com.wensheng.zcc.amc.module.dao.helper.AreaUnitEnum;
import com.wensheng.zcc.amc.module.dao.helper.ImagePathClassEnum;
import com.wensheng.zcc.amc.module.dao.helper.IsRecommandEnum;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetAdditional;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetDocument;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetImage;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAsset;
import com.wensheng.zcc.amc.module.vo.AmcAssetDetailVo;
import com.wensheng.zcc.amc.module.vo.AmcAssetVo;
import com.wensheng.zcc.amc.module.vo.base.BaseActionVo;
import com.wensheng.zcc.amc.service.AmcAssetService;
import com.wensheng.zcc.amc.service.AmcOssFileService;
import com.wensheng.zcc.amc.utils.SQLUtils;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.common.utils.AmcNumberUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author chenwei on 1/3/19
 * @project zcc-backend
 */
@Controller
@Slf4j
@RequestMapping("/api")
public class AmcAssetController {

  @Autowired
  AmcAssetService amcAssetService;

  @Autowired
  AmcOssFileService amcOssFileService;

  @RequestMapping(value = "/amcid/{amcid}/assets", method = RequestMethod.POST)
  @ResponseBody
  @LogExecutionTime
  @QueryChecker
  public AmcPage<AmcAssetVo> getAmcAssets(
      @RequestBody(required = false) QueryParam assetQueryParam) throws Exception {
    Map<String, Direction> orderByParam = PageReqRepHelper.getOrderParam(assetQueryParam.getPageInfo());
    if(CollectionUtils.isEmpty(orderByParam)){
      orderByParam.put("id", Direction.DESC);
    }
    Map<String, Object> queryParam = SQLUtils.getQueryParams(assetQueryParam);

    List<AmcAssetVo> queryResults;
    int offset = PageReqRepHelper.getOffset(assetQueryParam.getPageInfo());
    try{
      queryResults = amcAssetService.queryAssetPage(offset, assetQueryParam.getPageInfo().getSize(), queryParam,
          orderByParam);
    }catch (Exception ex){
      log.error("got error when query:"+ex.getMessage());
      throw ex;
    }
    Long totalCount = amcAssetService.getAssetCount(queryParam);

//    Page<AmcAssetVo> page = PageReqRepHelper.getPageResp(totalCount, queryResults, assetQueryParam.getPageInfo());
    return PageReqRepHelper.getAmcPage(queryResults, totalCount );
//    return queryResults;
  }

  @RequestMapping(value = "/amcid/{amcid}/upload2wechat/assetids", method = RequestMethod.POST)
  @ResponseBody
  @LogExecutionTime
  public Map<Long, List<String>> uploadAmcAssetsImage2WechatByIds(
      @RequestBody(required = false) List<Long> assetIds) throws Exception {

    Map<Long, List<String>> assetResultIds = amcAssetService.uploadAmcAssetsImage2WechatByIds(assetIds);

    return assetResultIds;
  }

  @RequestMapping(value = "/amcid/{amcid}/assetids", method = RequestMethod.POST)
  @ResponseBody
  @LogExecutionTime
  public List<AmcAssetVo> getAmcAssetsByIds(
      @RequestBody(required = false) List<Long> assetIds) throws Exception {

    List<AmcAssetVo> queryResults = amcAssetService.getAssetsByIds(assetIds);

    return queryResults;
  }

  @RequestMapping(value = "/amcid/{amcid}/assets/recommand", method = RequestMethod.POST)
  @ResponseBody
  public List<AmcAssetVo> getAmcAssets(
      @RequestBody(required = false) Integer size) throws Exception {
//    Map<String, Direction> orderByParam = PageReqRepHelper.getOrderParam(assetQueryParam.getPageInfo());



    List<AmcAssetVo> amcAssetVos = amcAssetService.queryForHomePage(size);

    return amcAssetVos;
  }



  @RequestMapping(value = "/amcid/{amcid}/asset/allTitles", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, List<Long>> getAmcAssetsAllTitles( @RequestBody QueryParam queryParam) throws Exception{
    return amcAssetService.getAllAssetTitles();
  }

  @RequestMapping(value = "/amcid/{amcid}/asset", method = RequestMethod.POST)
  @ResponseBody
  public AmcAssetDetailVo getAmcAssetDetail( @RequestParam("amcAssetId") Long amcAssetId) throws Exception{

    return amcAssetService.queryAssetDetail(amcAssetId);

  }


  @EditActionChecker
  @PreAuthorize("hasAnyRole('SYSTEM_ADMIN','AMC_ADMIN','AMC_USER')")
  @RequestMapping(value = "/amcid/{amcid}/asset/add", method = RequestMethod.POST)
  @ResponseBody
  public AmcAssetVo addAmcAsset(
      @RequestBody BaseActionVo<AmcAssetVo> amcAssetVo) throws Exception {
    AmcAsset amcAsset = getAssetFromVo(amcAssetVo.getContent());
    amcAsset.setPublishState(PublishStateEnum.DRAFT.getStatus());

    AmcAssetVo assetVo = amcAssetService.create(amcAsset);
    amcAssetVo.getContent().getAssetAdditional().setAmcAssetId(assetVo.getId());
    AssetAdditional assetAdditional =
        amcAssetService.createOrUpdateAssetAddition(amcAssetVo.getContent().getAssetAdditional());
    assetVo.setAssetAdditional(assetAdditional);
    return assetVo;
  }

  @PreAuthorize("hasRole('SYSTEM_ADMIN') or hasPermission(#amcid, 'AMC_CRUD')")
  @RequestMapping(value = "/amcid/{amcid}/asset/setRecomm", method = RequestMethod.POST)
  @ResponseBody
  public void recommAmcAsset(
          @RequestBody BaseActionVo<List<Long>> amcAssetVo,
      @PathVariable("amcid") Long amcid, @RequestParam IsRecommandEnum isRecommandEnum) throws Exception {

    if(CollectionUtils.isEmpty(amcAssetVo.getContent())){
      return;
    }
    amcAssetService.setRecomm(amcAssetVo.getContent(), isRecommandEnum.getId());


  }




  @EditActionChecker
  @RequestMapping(value = "/amcid/{amcid}/asset/update", method = RequestMethod.POST)
  @ResponseBody
  public AmcAssetVo updateAmcAsset(
      @RequestBody BaseActionVo<AmcAssetVo> amcAssetVo) throws Exception {
    AmcAsset amcAsset = getAssetFromVo(amcAssetVo.getContent());
    AmcAssetVo assetVo = amcAssetService.update(amcAsset);
    amcAssetVo.getContent().getAssetAdditional().setAmcAssetId(assetVo.getId());
    AssetAdditional assetAdditional =
        amcAssetService.createOrUpdateAssetAddition(amcAssetVo.getContent().getAssetAdditional());
    assetVo.setAssetAdditional(assetAdditional);
    return assetVo;
  }

  private AmcAsset getAssetFromVo(AmcAssetVo amcAssetVo) throws Exception {
    AmcAsset amcAsset = new AmcAsset();
    AmcBeanUtils.copyProperties(amcAssetVo, amcAsset);
    AmcBeanUtils.fillNullObjects(amcAsset);
    if(amcAssetVo.getValuation() != null ){
      amcAsset.setValuation(AmcNumberUtils.getLongFromDecimalWithMult100(amcAssetVo.getValuation()));
    }
    if(amcAssetVo.getLandArea() != null ){
      if(amcAssetVo.getLandAreaUnit() == null){
        throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_LANDAREA_UNIT, "" +amcAssetVo.getLandAreaUnit());
      }
      if(amcAssetVo.getLandAreaUnit() != null || AreaUnitEnum.lookupByDisplayTypeUtil(amcAssetVo.getLandAreaUnit()) != null){
        switch (AreaUnitEnum.lookupByDisplayTypeUtil(amcAssetVo.getLandAreaUnit())){
          case SQUAREMETER:
            amcAsset.setLandArea(AmcNumberUtils.getLongFromDecimalWithMult100(amcAssetVo.getLandArea()));
            break;
          case MU:
            amcAsset.setLandArea(AmcNumberUtils.getSQMFromMu(amcAssetVo.getLandArea()));
            break;
          case TENTHOUNDSQUM:
            amcAsset.setLandArea(AmcNumberUtils.getLongFromDecimalWithMult1000000(amcAssetVo.getLandArea()));
            break;
          default:
            log.error("amcAssetVo.getLandAreaUnit():" + amcAssetVo.getLandAreaUnit());
            throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_LANDAREA_UNIT);

        }
      }
    }
    if(amcAssetVo.getArea() != null){
      amcAsset.setArea(AmcNumberUtils.getLongFromDecimalWithMult100(amcAssetVo.getArea()));
    }
    if(amcAssetVo.getAmcContactorId() != null && amcAssetVo.getAmcContactorId().getId() > 0){
      amcAsset.setAmcContactorId(amcAssetVo.getAmcContactorId().getId());
    }
    return amcAsset;
  }

  @RequestMapping(value = "/amcid/{amcid}/asset/image/add", headers = "Content-Type= multipart/form-data",method = RequestMethod.POST)
  @ResponseBody
  public List<AssetImage> addAmcAssetImage(
       @RequestParam("assetId") Long assetId, @RequestParam("isToOss") Boolean isToOss,
      @RequestParam("imageClass") Integer tag, @RequestParam("actionId") Long actionId, @RequestParam(value = "description",
      required = false) String description,
      @RequestPart("uploadingImages") MultipartFile[] uploadingImages) throws Exception {
    List<String> filePaths = new ArrayList<>();
//    if(uploadingImages != null && uploadingImages.length >= 3){
//      throw ExceptionUtils.getAmcException(AmcExceptions.LIMTEXCEED_UPLOADFILENUMBER,
//          "upload "+uploadingImages.length + " files at same time");
//    }
    if(assetId == null){
      throw ExceptionUtils.getAmcException(AmcExceptions.MISSING_MUST_PARAM,"amcAssetId missing");
    }

    for(MultipartFile uploadedImage : uploadingImages) {
      try {
        String filePath = amcOssFileService.handleMultiPartFile(uploadedImage, assetId,
            ImagePathClassEnum.ASSET.getName());
        filePaths.add(filePath);
      } catch (Exception e) {
        e.printStackTrace();
        throw ExceptionUtils.getAmcException(AmcExceptions.FAILED_UPLOADFILE2SERVER, e.getMessage());
      }
    }
    String prePath = ImagePathClassEnum.ASSET.getName()+"/"+assetId+"/";

    List<AssetImage> assetImages = new ArrayList<>();
    for(String filePath: filePaths){
      try {
        String ossPath =  amcOssFileService.handleFile2Oss(filePath, prePath);
        AssetImage assetImage = new AssetImage();

        assetImage.setOssPath(ossPath);
        assetImage.setTag(tag);
        assetImage.setOriginalName(filePath);
        assetImage.setAmcAssetId(assetId);
        assetImage.setDescription(description);
        assetImages.add(amcAssetService.saveImageInfo( assetImage));

      } catch (Exception e) {
        e.printStackTrace();
        throw ExceptionUtils.getAmcException(AmcExceptions.FAILED_UPLOADFILE2OSS, e.getMessage());

      }
    }
    return assetImages;
  }

  @RequestMapping(value = "/amcid/{amcid}/asset/image/modify",method =
      RequestMethod.POST)
  @ResponseBody
  public String modifyAssetImage(
      @RequestBody AssetImage assetImage) throws Exception {



        amcAssetService.saveImageInfo( assetImage);


    return "succeed";
  }

  @LogExecutionTime
  @RequestMapping(value = "/amcid/{amcid}/asset/doc/add", method = RequestMethod.POST)
  @ResponseBody
  public List<AssetDocument> addDoc(
      @RequestPart BaseActionVo<AssetDocument> assetDocumentBaseActionVo,
      @RequestParam("uploadingDocuments") MultipartFile[] uploadingDocuments) throws Exception {
    AssetDocument assetDocument = assetDocumentBaseActionVo.getContent();
    List<String> filePaths = new ArrayList<>();
    if(assetDocument.getAmcAssetId() == null){
      throw ExceptionUtils.getAmcException(AmcExceptions.MISSING_MUST_PARAM,"amcAssetId missing");
    }
    for(MultipartFile uploadingDocument : uploadingDocuments) {
      try {
        String filePath = amcOssFileService.handleMultiPartFile(uploadingDocument, assetDocument.getAmcAssetId(),
            ImagePathClassEnum.ASSET.getName());
        filePaths.add(filePath);
      } catch (Exception e) {
        e.printStackTrace();
        throw new ResponseStatusException(HttpStatus.MULTI_STATUS,e.getStackTrace().toString());
      }
    }
    String prePath = ImagePathClassEnum.ASSET.getName()+"/"+assetDocument.getAmcAssetId() +"/";

    List<AssetDocument> assetDocuments = new ArrayList<>();
    for(String filePath: filePaths){
      try {
        String ossPath =  amcOssFileService.handleFile2Oss(filePath, prePath);



        assetDocument.setOssPath(ossPath);
        assetDocuments.add(amcAssetService.saveDoc( assetDocument));

      } catch (Exception e) {
        e.printStackTrace();
        throw new ResponseStatusException(HttpStatus.MULTI_STATUS,e.getStackTrace().toString());
      }
    }
    return assetDocuments;
  }



  @RequestMapping(value = "/amcid/{amcid}/asset/doc/del", method = RequestMethod.POST)
  @ResponseBody
  public void delAmcAssetDocument(@RequestBody BaseActionVo<List<AssetDocument>> listBaseActionVo ) throws Exception{
    for(AssetDocument assetDocument: listBaseActionVo.getContent()){

      amcOssFileService.delFileInOss(assetDocument.getOssPath());
    }
  }


  @RequestMapping(value = "/amcid/{amcid}/asset/del", method = RequestMethod.POST)
  @ResponseBody
  public void delAmcAsset(@RequestBody BaseActionVo<Long> delAssetBaseActionVo ) throws Exception{
    amcAssetService.delAsset(delAssetBaseActionVo.getContent());
  }

  @RequestMapping(value = "/amcid/{amcid}/asset/image/del", method = RequestMethod.POST)
  @ResponseBody
  public void delAmcAssetImage(@RequestBody BaseActionVo<List<AssetImage>> assetImagesVo ) throws Exception{
    for(AssetImage assetImage: assetImagesVo.getContent()){

      amcOssFileService.delFileInOss(assetImage.getOssPath());
      amcAssetService.delImage(assetImage);
    }
  }






}
