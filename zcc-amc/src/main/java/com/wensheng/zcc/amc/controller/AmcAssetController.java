package com.wensheng.zcc.amc.controller;

import com.wensheng.zcc.amc.aop.LogExecutionTime;
import com.wensheng.zcc.amc.controller.helper.AssetQueryParam;
import com.wensheng.zcc.amc.controller.helper.PageReqRepHelper;
import com.wensheng.zcc.amc.module.dao.helper.ImagePathClassEnum;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetAdditional;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetDocument;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetImage;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAsset;
import com.wensheng.zcc.amc.module.vo.AmcAssetDetailVo;
import com.wensheng.zcc.amc.module.vo.AmcAssetVo;
import com.wensheng.zcc.amc.module.vo.base.BaseActionVo;
import com.wensheng.zcc.amc.service.AmcAssetService;
import com.wensheng.zcc.amc.service.AmcOssFileService;
import com.wensheng.zcc.amc.utils.AmcNumberUtils;
import com.wensheng.zcc.amc.utils.ExceptionUtils;
import com.wensheng.zcc.amc.utils.ExceptionUtils.AmcExceptions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
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
  public Page<AmcAssetVo> getAmcAssets(
      @RequestBody(required = false) AssetQueryParam assetQueryParam) throws Exception {
    Map<String, Direction> orderByParam = PageReqRepHelper.getOrderParam(assetQueryParam.getPageInfo());
    if(CollectionUtils.isEmpty(orderByParam)){
      orderByParam.put("id", Direction.DESC);
    }
    Map<String, Object> queryParam = new HashMap<>();

    if(assetQueryParam.getDebtId() > 0){
      queryParam.put("DebtId", assetQueryParam.getDebtId());
    }
    if(!CollectionUtils.isEmpty(assetQueryParam.getArea()) && assetQueryParam.getArea().size() > 1){
      queryParam.put("Area", assetQueryParam.getArea());
    }
    if(!CollectionUtils.isEmpty(assetQueryParam.getLandArea()) && assetQueryParam.getLandArea().size() > 1){
      queryParam.put("LandArea", assetQueryParam.getLandArea());
    }
    if(assetQueryParam.getEditStatus() != null && assetQueryParam.getEditStatus() > -1){
      queryParam.put("EditStatus", assetQueryParam.getEditStatus());
    }
    if(assetQueryParam.getStatus() != null && assetQueryParam.getStatus() > -1){
      queryParam.put("Status", assetQueryParam.getStatus());
    }
    if(!StringUtils.isEmpty(assetQueryParam.getTitle())){
      queryParam.put("Title", assetQueryParam.getTitle());
    }

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

    Page<AmcAssetVo> page = PageReqRepHelper.getPageResp(totalCount, queryResults, assetQueryParam.getPageInfo());
    return page;
  }

  @RequestMapping(value = "/amcid/{amcid}/asset/allTitles", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, List<Long>> getAmcAssetsAllTitles( @RequestBody AssetQueryParam assetQueryParam) throws Exception{
    return amcAssetService.getAllAssetTitles();
  }

  @RequestMapping(value = "/amcid/{amcid}/asset", method = RequestMethod.POST)
  @ResponseBody
  public AmcAssetDetailVo getAmcAssetDetail( @RequestParam("amcAssetId") Long amcAssetId) throws Exception{

    return amcAssetService.queryAssetDetail(amcAssetId);

  }

  @RequestMapping(value = "/amcid/{amcid}/asset/add", method = RequestMethod.POST)
  @ResponseBody
  public AmcAssetVo addAmcAsset(
      @RequestBody BaseActionVo<AmcAssetVo> amcAssetVo) throws Exception {
    AmcAsset amcAsset = getAssetFromVo(amcAssetVo.getContent());
    AmcAssetVo assetVo = amcAssetService.create(amcAsset);
    amcAssetVo.getContent().getAssetAdditional().setAmcAssetId(assetVo.getId());
    AssetAdditional assetAdditional =
        amcAssetService.createOrUpdateAssetAddition(amcAssetVo.getContent().getAssetAdditional());
    assetVo.setAssetAdditional(assetAdditional);
    return assetVo;
  }

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

  private AmcAsset getAssetFromVo(AmcAssetVo amcAssetVo){
    AmcAsset amcAsset = new AmcAsset();
    BeanUtils.copyProperties(amcAssetVo, amcAsset);
    amcAsset.setValuation(AmcNumberUtils.getLongFromDecimalWithMult100(amcAssetVo.getValuation()));
    amcAsset.setStartPrice(AmcNumberUtils.getLongFromDecimalWithMult100(amcAssetVo.getStartPrice()));
    amcAsset.setLandArea(AmcNumberUtils.getLongFromDecimalWithMult100(amcAssetVo.getLandArea()));
    amcAsset.setArea(AmcNumberUtils.getLongFromDecimalWithMult100(amcAssetVo.getArea()));
    return amcAsset;
  }

  @RequestMapping(value = "/amcid/{amcid}/asset/image/add", method = RequestMethod.POST)
  @ResponseBody
  public List<AssetImage> addAmcAssetImage(
       @RequestParam("assetId") Long assetId, @RequestParam("isToOss") Boolean isToOss,
      @RequestParam("actionId") Long actionId,
      @RequestParam("uploadingImages") MultipartFile[] uploadingImages) throws Exception {
    List<String> filePaths = new ArrayList<>();
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
        throw new ResponseStatusException(HttpStatus.MULTI_STATUS,e.getStackTrace().toString());
      }
    }
    String prePath = ImagePathClassEnum.ASSET.getName()+"/"+assetId+"/";

    List<AssetImage> assetImages = new ArrayList<>();
    for(String filePath: filePaths){
      try {
        String ossPath =  amcOssFileService.handleFile2Oss(filePath, prePath);
        AssetImage assetImage = new AssetImage();

        assetImage.setOssPath(ossPath);
        assetImages.add(amcAssetService.saveImageInfo( assetImage));

      } catch (Exception e) {
        e.printStackTrace();
        throw new ResponseStatusException(HttpStatus.MULTI_STATUS,e.getStackTrace().toString());
      }
    }
    return assetImages;
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

  @RequestMapping(value = "/amcid/{amcid}/asset/image/del", method = RequestMethod.POST)
  @ResponseBody
  public void delAmcAssetImage(@RequestBody BaseActionVo<List<AssetImage>> assetImagesVo ) throws Exception{
    for(AssetImage assetImage: assetImagesVo.getContent()){

      amcOssFileService.delFileInOss(assetImage.getOssPath());
      amcAssetService.delImage(assetImage);
    }
  }






}
