package com.wensheng.zcc.amc.controller;

import com.wensheng.zcc.amc.aop.QueryContactorChecker;
import com.wensheng.zcc.amc.aop.QuerySSOContactorChecker;
import com.wensheng.zcc.amc.controller.helper.QueryCurtParam;
import com.wensheng.zcc.amc.dao.mysql.mapper.CurtInfoMapper;
import com.wensheng.zcc.amc.module.dao.helper.*;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtContactor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.CurtInfo;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.CurtInfoExample;
import com.wensheng.zcc.common.module.dto.AmcFilterContentAsset;
import com.wensheng.zcc.common.module.dto.AmcFilterContentDebt;
import com.wensheng.zcc.common.module.dto.AmcFilterContentTag;
import com.wensheng.zcc.amc.service.AmcAssetService;
import com.wensheng.zcc.amc.service.AmcContactorService;
import com.wensheng.zcc.amc.service.AmcExcelFileService;
import com.wensheng.zcc.amc.service.AmcHelperService;
import com.wensheng.zcc.amc.service.AmcOssFileService;
import com.wensheng.zcc.amc.service.AmcSaleService;
import com.wensheng.zcc.common.params.AmcBranchLocationEnum;
import com.wensheng.zcc.common.params.AmcDebtAssetTypeEnum;
import com.wensheng.zcc.common.params.AmcPage;
import com.wensheng.zcc.common.params.AmcSexEnum;
import com.wensheng.zcc.common.params.PageInfo;
import com.wensheng.zcc.common.params.PageReqRepHelper;
import com.wensheng.zcc.common.params.sso.SSOAmcUser;
import com.wensheng.zcc.common.utils.sso.SSOQueryParam;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author chenwei on 1/4/19
 * @project zcc-backend
 */
@Controller
@RequestMapping(value = "/api/basic")
@Slf4j

public class AmcBasicInfoController {

  @Autowired
  CurtInfoMapper curtInfoMapper;

  @Autowired
  AmcHelperService amcHelperService;

  @Autowired
  AmcAssetService amcAssetService;

  @Autowired
  AmcContactorService amcContactorService;

  @Autowired
  AmcExcelFileService amcExcelFileService;

  @Autowired
  AmcOssFileService amcOssFileService;

  @Autowired
  AmcSaleService amcSaleService;

  @RequestMapping(value = "/all_court_info", method = RequestMethod.GET)
  @ResponseBody

  public List<CurtInfo> getAllCourtInfo() throws Exception {

    return amcHelperService.getAllCurts();
  }


  @RequestMapping(value = "/debtAssetTypeEnum", method = RequestMethod.GET)
  @ResponseBody
  public List<String> getDebtAssetTypeEnum(){

    List<String> result = new ArrayList<>();
    for(AmcDebtAssetTypeEnum debtAssetTypeEnum : AmcDebtAssetTypeEnum.values()){
      result.add(String.format("%d:%s", debtAssetTypeEnum.getId(), debtAssetTypeEnum.getName()));
    }
    return result;
  }
  @RequestMapping(value = "/saleFloorTypeEnum", method = RequestMethod.GET)
  @ResponseBody
  public List<String> getSaleFloorTypeEnum(){

    List<String> result = new ArrayList<>();
    for(SaleFloorEnum saleFloorEnum : SaleFloorEnum.values()){
      result.add(String.format("%d:%s", saleFloorEnum.getId(), saleFloorEnum.getName()));
    }
    return result;
  }
  @RequestMapping(value = "/amcSex", method = RequestMethod.GET)
  @ResponseBody
  public List<String> getAmcSex(){

    List<String> result = new ArrayList<>();
    for(AmcSexEnum amcSexEnum : AmcSexEnum.values()){
      result.add(String.format("%d:%s", amcSexEnum.getId(), amcSexEnum.getName()));
    }
    return result;
  }

  @RequestMapping(value = "/publishStates", method = RequestMethod.GET)
  @ResponseBody
  public List<String> getPublishStateList(){

    List<String> result = new ArrayList<>();
    for(PublishStateEnum publishStateEnum : PublishStateEnum.values()){
      result.add(String.format("%d:%s", publishStateEnum.getStatus(), publishStateEnum.getName()));
    }
    return result;
  }

  @RequestMapping(value = "/status", method = RequestMethod.GET)
  @ResponseBody
  public List<String> getStatusList(){

    List<String> result = new ArrayList<>();
    for(LawstateEnum lawstateEnum : LawstateEnum.values()){
      result.add(String.format("%d:%s", lawstateEnum.getStatus(), lawstateEnum.getName()));
    }
    return result;
  }


  @RequestMapping(value = "/editActions", method = RequestMethod.GET)
  @ResponseBody
  public List<String> getEditActions(){

    List<String> result = new ArrayList<>();
    for(EditActionEnum editActionEnum: EditActionEnum.values()){
      result.add(String.format("%d:%s:%s", editActionEnum.getId(), editActionEnum.getName(),
          editActionEnum.getCname() ));
    }
    return result;
  }

  @RequestMapping(value = "/assetSealedStates", method = RequestMethod.GET)
  @ResponseBody
  public List<String> getAssetSealedStatesList(){

    List<String> result = new ArrayList<>();
    for(SealStateEnum enumItem: SealStateEnum.values()){
      result.add(String.format("%d:%s", enumItem.getStatus(), enumItem.getName()));
    }
    return result;
  }

  @RequestMapping(value = "/isRecommand", method = RequestMethod.GET)
  @ResponseBody
  public List<String> getIsRecommand(){

    List<String> result = new ArrayList<>();
    for(IsRecommandEnum enumItem: IsRecommandEnum.values()){
      result.add(String.format("%d:%s", enumItem.getId(), enumItem.getName()));
    }
    return result;
  }

  @RequestMapping(value = "/getDebtPrecheckErrorEnum", method = RequestMethod.GET)
  @ResponseBody
  public List<String> getDebtPrecheckErrorEnum(){

    List<String> result = new ArrayList<>();
    for(DebtPrecheckErrorEnum debtPrecheckErrorEnum: DebtPrecheckErrorEnum.values()){
      result.add(String.format("%d:%s", debtPrecheckErrorEnum.getErrorCode(), debtPrecheckErrorEnum.getCerrorInfo()));
    }
    return result;
  }

  @RequestMapping(value = "/debtorRoleType", method = RequestMethod.GET)
  @ResponseBody
  public List<String> getDebtorRoleType(){

    List<String> result = new ArrayList<>();
    for(DebtorRoleEnum enumItem: DebtorRoleEnum.values()){
      result.add(String.format("%d:%s", enumItem.getId(), enumItem.getName()));
    }
    return result;
  }

  @RequestMapping(value = "/assetType", method = RequestMethod.GET)
  @ResponseBody
  public List<String> getAssetType(){

    List<String> result = new ArrayList<>();
    for(AssetTypeEnum enumItem: AssetTypeEnum.values()){
      result.add(String.format("%d:%s", enumItem.getType(), enumItem.getName()));
    }
    return result;
  }



  @RequestMapping(value = "/imageClass", method = RequestMethod.GET)
  @ResponseBody
  public List<String> getImageClass(){

    List<String> result = new ArrayList<>();
    for(ImageClassEnum enumItem: ImageClassEnum.values()){
      result.add(String.format("%d:%s", enumItem.getId(), enumItem.getName()));
    }
    return result;
  }


  @RequestMapping(value = "/areaUnit", method = RequestMethod.GET)
  @ResponseBody
  public List<String> getAreaUnit(){

    List<String> result = new ArrayList<>();
    for(AreaUnitEnum enumItem: AreaUnitEnum.values()){
      result.add(String.format("%d:%s", enumItem.getType(), enumItem.getName()));
    }
    return result;
  }

  @RequestMapping(value = "/guarantType", method = RequestMethod.GET)
  @ResponseBody
  public List<String> getGuarantType(){

    List<String> result = new ArrayList<>();
    for(GuarantTypeEnum guarantTypeEnum: GuarantTypeEnum.values()){
      result.add(String.format("%d:%s", guarantTypeEnum.getType(), guarantTypeEnum.getName()));
    }
    return result;
  }


  @RequestMapping(value = "/assetNature", method = RequestMethod.GET)
  @ResponseBody
  public List<String> getAssetNature(){

    List<String> result = new ArrayList<>();
    for(AssetNatureEnum assetNatureEnum: AssetNatureEnum.values()){
      result.add(String.format("%d:%s", assetNatureEnum.getType(), assetNatureEnum.getName()));
    }
    return result;
  }


  @RequestMapping(value = "/landSupplyType", method = RequestMethod.GET)
  @ResponseBody
  public List<String> getLandSupplyType(){

    List<String> result = new ArrayList<>();
    for(LandSupplyTypeEnum landSupplyTypeEnum: LandSupplyTypeEnum.values()){
      result.add(String.format("%d:%s", landSupplyTypeEnum.getId(), landSupplyTypeEnum.getName()));
    }
    return result;
  }
  //landUsageType
  @RequestMapping(value = "/landUsageType", method = RequestMethod.GET)
  @ResponseBody
  public List<String> getLandUsageType(){

    List<String> result = new ArrayList<>();
    for(LandUsageTypeEnum landUsageTypeEnum: LandUsageTypeEnum.values()){
      result.add(String.format("%d:%s", landUsageTypeEnum.getId(), landUsageTypeEnum.getName()));
    }
    return result;
  }

  @RequestMapping(value = "/filterType", method = RequestMethod.GET)
  @ResponseBody
  public List<String> getFilterType(){

    List<String> result = new ArrayList<>();
    for(FilterTypeEnum filterTypeEnum: FilterTypeEnum.values()){
      result.add(String.format("%d:%s", filterTypeEnum.getType(), filterTypeEnum.getName()));
    }
    return result;
  }

  @RequestMapping(value = "/debtType", method = RequestMethod.GET)
  @ResponseBody
  public List<String> getDebtType(){

    List<String> result = new ArrayList<>();
    for(DebtTypeEnum debtTypeEnum: DebtTypeEnum.values()){
      result.add(String.format("%d:%s", debtTypeEnum.getType(), debtTypeEnum.getName()));
    }
    return result;
  }

  @RequestMapping(value = "/floorPublishEnum", method = RequestMethod.GET)
  @ResponseBody
  public List<String> getFloorPublishEnum(){

    List<String> result = new ArrayList<>();
    for(FloorPublishStateEnum floorPublishStateEnum: FloorPublishStateEnum.values()){
      result.add(String.format("%d:%s", floorPublishStateEnum.getStatus(), floorPublishStateEnum.getName()));
    }
    return result;
  }


  @RequestMapping(value = "/renovation", method = RequestMethod.GET)
  @ResponseBody
  public List<String> getRenovation(){

    List<String> result = new ArrayList<>();
    for(RenovationEnum renovationEnum: RenovationEnum.values()){
      result.add(String.format("%d:%s", renovationEnum.getId(), renovationEnum.getName()));
    }
    return result;
  }
  @RequestMapping(value = "/debtorRoles", method = RequestMethod.GET)
  @ResponseBody
  public List<String> getDebtorRoles(){

    List<String> result = new ArrayList<>();
    for(DebtorRoleEnum debtorRoleEnum: DebtorRoleEnum.values()){
      result.add(String.format("%d:%s", debtorRoleEnum.getId(), debtorRoleEnum.getName()));
    }
    return result;
  }

  @RequestMapping(value = "/amcid/{amcId}/amccontactors", method = RequestMethod.POST)
  @ResponseBody
  @QueryContactorChecker
  public AmcPage<AmcDebtContactor> getAmcDebtContactors( @RequestBody PageInfo pageable) throws Exception {

    Map<String, Direction> orderByParam = PageReqRepHelper.getOrderParam(pageable);
    if (CollectionUtils.isEmpty(orderByParam)) {
      orderByParam.put("id", Direction.DESC);
    }

    List<AmcDebtContactor> queryResults;
    int offset = PageReqRepHelper.getOffset(pageable);
    try {
      queryResults = amcHelperService.getAllAmcDebtContactor(Long.valueOf(offset), pageable.getSize(),
          orderByParam, pageable.getLocation());
    } catch (Exception ex) {
      log.error("got error when query:" + ex.getMessage());
      throw ex;
    }
    Long totalCount = amcHelperService.getPersonTotalCount(pageable.getLocation());
//
//    Page<AmcDebtContactor> page = PageReqRepHelper.getPageResp(totalCount, queryResults, pageable);
    return PageReqRepHelper.getAmcPage(queryResults, totalCount);
//    return queryResults;

  }

  @RequestMapping(value = "/amcid/{amcId}/amccontactors4debt", method = RequestMethod.POST)
  @ResponseBody
  @QuerySSOContactorChecker
  public AmcPage<SSOAmcUser> getAmcDebtContactors( @RequestBody SSOQueryParam ssoQueryParam) throws Exception {

    Map<String, Direction> orderByParam = PageReqRepHelper.getOrderParam(ssoQueryParam.getPageInfo());
    if (CollectionUtils.isEmpty(orderByParam)) {
      orderByParam.put("id", Direction.DESC);
    }

    AmcPage<SSOAmcUser> queryResults;

    try {
      queryResults = amcHelperService.getSsoUserList(ssoQueryParam);
    } catch (Exception ex) {
      log.error("got error when query:" + ex.getMessage());
      throw ex;
    }
//
    return queryResults;

  }
  @PreAuthorize("hasAnyRole('SYSTEM_ADMIN','CO_ADMIN') or hasPermission(#amcId, 'PERM_DEBTCNTR_MOD')")
  @RequestMapping(value = "/amcid/{amcId}/amccontactor/add", method = RequestMethod.POST)
  @ResponseBody
  public AmcDebtContactor getAmcDebtContactors(@RequestBody AmcDebtContactor amcPerson, @PathVariable Long amcId){
    return amcHelperService.createPerson(amcPerson);
  }

//  @EditActionChecker
//  @PreAuthorize("hasRole('SYSTEM_ADMIN') or hasPermission(#amcId, 'PERM_DEBTCNTR_MOD')")
//  @RequestMapping(value = "/amcid/{amcId}/amccontactor/update", method = RequestMethod.POST)
//  @ResponseBody
//  public AmcDebtContactor updateAmcDebtContactors(@RequestBody AmcDebtContactor amcPerson, @PathVariable Long amcId){
//    return amcHelperService.updatePerson(amcPerson);
//  }
//
//  @EditActionChecker
//  @PreAuthorize("hasRole('SYSTEM_ADMIN') or hasPermission(#amcId, 'PERM_DEBTCNTR_MOD')")
//  @RequestMapping(value = "/amcid/{amcId}/amccontactor/del", method = RequestMethod.POST)
//  @ResponseBody
//  public String delAmcDebtContactors(@RequestBody Long[] contactorIds, @PathVariable Long amcId) throws Exception {
//    if(contactorIds != null && contactorIds.length > 0){
//      amcHelperService.deletePersons(contactorIds);
//    }
//    return "succeed";
//
//  }

  @RequestMapping(value = "/amcBranchLocations", method = RequestMethod.POST)
  @ResponseBody
  public List<String> getAmcBranchLocations(){

    List<String> result = new ArrayList<>();
    for(AmcBranchLocationEnum amcBranchLocationEnum : AmcBranchLocationEnum.values()){
      result.add(String.format("%d:%s:%s", amcBranchLocationEnum.getId(), amcBranchLocationEnum.getName(),
          amcBranchLocationEnum.getCname()));
    }
    return result;
  }
  @RequestMapping(value = "/orderByField", method = RequestMethod.POST)
  @ResponseBody
  public List<String> orderByField(){

    List<String> result = new ArrayList<>();
    for(OrderByFieldEnum orderByFieldEnum : OrderByFieldEnum.values()){
      result.add(String.format("%d:%s", orderByFieldEnum.getId(), orderByFieldEnum.getName()));
    }
    return result;
  }

  @RequestMapping(value = "/cssTypeEnum", method = RequestMethod.POST)
  @ResponseBody
  public List<String> cssTypeEnum(){

    List<String> result = new ArrayList<>();
    for(CSSTypeEnum cssTypeEnum :  CSSTypeEnum.values()){
      result.add(String.format("%d:%s", cssTypeEnum.getId(), cssTypeEnum.getName()));
    }
    return result;
  }

  @RequestMapping(value = "/bannderLinkType", method = RequestMethod.POST)
  @ResponseBody
  public List<String> bannderLinkType(){

    List<String> result = new ArrayList<>();
    for(BannerLinkTypeEnum bannerLinkTypeEnum : BannerLinkTypeEnum.values()){
      result.add(String.format("%d:%s", bannerLinkTypeEnum.getId(), bannerLinkTypeEnum.getName()));
    }
    return result;
  }



  @RequestMapping(value = "/checkGeoInfo", method = RequestMethod.POST)
  @ResponseBody
  public void checkGeoInfo(){

   amcAssetService.checkGeoInfoWorker();
  }

  @RequestMapping(value = "/syncContactorWithSSO", method = RequestMethod.POST)
  @ResponseBody
  public void syncContactorWithSSO(){

    amcContactorService.syncContactorWithSSO();
  }

  @RequestMapping(value = "/court_info/getCurtInfo", method = RequestMethod.POST)
  @ResponseBody
  public  AmcPage<CurtInfo>  getCurtInfo(@RequestBody QueryCurtParam queryCurtParam) throws Exception {

     List<CurtInfo> curtInfos = amcHelperService.queryCurtInfo(queryCurtParam);
     Long total = amcHelperService.queryCurtInfoCount(queryCurtParam);
     return PageReqRepHelper.getAmcPage(curtInfos, total);
  }

  @RequestMapping(value = "/court_info", method = RequestMethod.GET)
  @ResponseBody
  public List<CurtInfo> getCourtInfo(@RequestParam("courtId") Long courtId){
    List<CurtInfo> curtInfos ;
    CurtInfoExample curtInfoExample = new CurtInfoExample();
    curtInfoExample.createCriteria().andIdEqualTo(courtId);
    curtInfos = curtInfoMapper.selectByExample(curtInfoExample);
    return curtInfos;
  }
  @RequestMapping(value = "/court_info/delCurtInfoByQuery", method = RequestMethod.POST)
  @ResponseBody
  public boolean  delCurtInfoByQuery(@RequestBody QueryCurtParam queryCurtParam) throws Exception {

    return amcHelperService.delCurtByQuery(queryCurtParam);

  }

  @RequestMapping(value = "/court_info/delCurtInfoById", method = RequestMethod.POST)
  @ResponseBody
  public boolean  delCurtInfoById(@RequestBody Long curtId) throws Exception {

    return amcHelperService.delCurt(curtId);

  }
  @RequestMapping(value = "/court_info/add", method = RequestMethod.POST)
  @ResponseBody
  public CurtInfo addCourtInfo(@RequestBody CurtInfo curtInfo) throws Exception {
    return amcHelperService.addCurt(curtInfo);

  }

  @RequestMapping(value = "/court_info/update", method = RequestMethod.POST)
  @ResponseBody
  public CurtInfo updateCourtInfo(@RequestBody CurtInfo curtInfo){
    curtInfoMapper.updateByPrimaryKeySelective(curtInfo);
    return curtInfo;
  }

  @RequestMapping(value = "/court_info/excel/refreshExcel", headers = "Content-Type= multipart/form-data",method =
          RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<Resource> updateCurtInfo (
                                         @RequestParam("excel") MultipartFile excelFile) throws Exception {


//    MultipartFile[] uploadingImages = debtImageBaseActionVo.getContent().getMultipartFiles();
    File resultFile =  amcExcelFileService.handleMultiPartFileCurtInfo(excelFile);
    Resource resource = new UrlResource(resultFile.toPath().toUri());
    if(resource.exists()) {
      return ResponseEntity.ok()
              .contentType(MediaType.parseMediaType("application/octet-stream"))
              .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
              .body(resource);
    } else {
      throw new Exception("File not found " + resultFile.toPath());
    }


  }
  @RequestMapping(value = "/base64Image", headers = "Content-Type= multipart/form-data",method =
      RequestMethod.POST)
  @ResponseBody
  public String base64Image(@RequestParam("images") MultipartFile uploadingImage) throws Exception {
      String filePath = amcOssFileService.handleMultiPartFile4Base64(uploadingImage);
      return amcOssFileService.img2Base64(filePath);
    }

  @RequestMapping(value = "/base64ImageUrl",method =
      RequestMethod.POST)
  @ResponseBody
  public String base64ImageUrl(@RequestBody String imgUrl) throws Exception {

    return  amcSaleService.getBase64Code(imgUrl);

  }

}
