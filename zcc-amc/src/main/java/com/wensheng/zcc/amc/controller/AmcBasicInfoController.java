package com.wensheng.zcc.amc.controller;

import com.wensheng.zcc.amc.aop.EditActionChecker;
import com.wensheng.zcc.amc.service.AmcAssetService;
import com.wensheng.zcc.amc.service.impl.AmcBaiDuLogisQuery;
import com.wensheng.zcc.common.params.AmcBranchLocationEnum;
import com.wensheng.zcc.common.params.AmcPage;
import com.wensheng.zcc.common.params.PageInfo;
import com.wensheng.zcc.common.params.PageReqRepHelper;
import com.wensheng.zcc.amc.dao.mysql.mapper.CurtInfoMapper;
import com.wensheng.zcc.amc.module.dao.helper.AreaUnitEnum;
import com.wensheng.zcc.amc.module.dao.helper.AssetNatureEnum;
import com.wensheng.zcc.amc.module.dao.helper.AssetTypeEnum;
import com.wensheng.zcc.amc.module.dao.helper.DebtorRoleEnum;
import com.wensheng.zcc.amc.module.dao.helper.EditActionEnum;
import com.wensheng.zcc.amc.module.dao.helper.GuarantTypeEnum;
import com.wensheng.zcc.amc.module.dao.helper.ImageClassEnum;
import com.wensheng.zcc.amc.module.dao.helper.IsRecommandEnum;
import com.wensheng.zcc.amc.module.dao.helper.LawstateEnum;
import com.wensheng.zcc.amc.module.dao.helper.PublishStateEnum;
import com.wensheng.zcc.amc.module.dao.helper.SealStateEnum;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtContactor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.CurtInfo;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.CurtInfoExample;
import com.wensheng.zcc.amc.service.AmcHelperService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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



  @RequestMapping(value = "/all_court_info", method = RequestMethod.GET)
  @ResponseBody
  public List<CurtInfo> getAllCourtInfo(){
    List<CurtInfo> curtInfos;
    CurtInfoExample curtInfoExample = new CurtInfoExample();
    curtInfoExample.createCriteria().andCurtNameIsNotNull();
    curtInfos = curtInfoMapper.selectByExample(curtInfoExample);
    return curtInfos;
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

  @RequestMapping(value = "/court_info/add", method = RequestMethod.POST)
  @ResponseBody
  public CurtInfo addCourtInfo(@RequestBody CurtInfo curtInfo){
    curtInfoMapper.insert(curtInfo);
    return curtInfo;
  }

  @RequestMapping(value = "/court_info/update", method = RequestMethod.POST)
  @ResponseBody
  public CurtInfo updateCourtInfo(@RequestBody CurtInfo curtInfo){
    curtInfoMapper.updateByPrimaryKeySelective(curtInfo);
    return curtInfo;
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
  public AmcPage<AmcDebtContactor> getAmcDebtContactors( @RequestBody PageInfo pageable) throws Exception {

    Map<String, Direction> orderByParam = PageReqRepHelper.getOrderParam(pageable);
    if (CollectionUtils.isEmpty(orderByParam)) {
      orderByParam.put("id", Direction.DESC);
    }

    List<AmcDebtContactor> queryResults;
    int offset = PageReqRepHelper.getOffset(pageable);
    try {
      queryResults = amcHelperService.getAllAmcDebtContactor(Long.valueOf(offset), pageable.getSize(),
          orderByParam);
    } catch (Exception ex) {
      log.error("got error when query:" + ex.getMessage());
      throw ex;
    }
    Long totalCount = amcHelperService.getPersonTotalCount();
//
//    Page<AmcDebtContactor> page = PageReqRepHelper.getPageResp(totalCount, queryResults, pageable);
    return PageReqRepHelper.getAmcPage(queryResults, totalCount);
//    return queryResults;

  }

  @RequestMapping(value = "/amcid/{amcId}/amccontactor/add", method = RequestMethod.POST)
  @ResponseBody
  public AmcDebtContactor getAmcDebtContactors(@RequestBody AmcDebtContactor amcPerson){
    return amcHelperService.createPerson(amcPerson);
  }

  @EditActionChecker
  @PreAuthorize("hasRole('SYSTEM_ADMIN') or hasPermission(#amcId, 'AMC_CRUD')")
  @RequestMapping(value = "/amcid/{amcId}/amccontactor/update", method = RequestMethod.POST)
  @ResponseBody
  public AmcDebtContactor updateAmcDebtContactors(@RequestBody AmcDebtContactor amcPerson, @PathVariable Long amcId){
    return amcHelperService.updatePerson(amcPerson);
  }

  @EditActionChecker
  @PreAuthorize("hasRole('SYSTEM_ADMIN') or hasPermission(#amcId, 'AMC_CRUD')")
  @RequestMapping(value = "/amcid/{amcId}/amccontactor/del", method = RequestMethod.POST)
  @ResponseBody
  public String delAmcDebtContactors(@RequestBody Long[] contactorIds, @PathVariable Long amcId) throws Exception {
    if(contactorIds != null && contactorIds.length > 0){
      amcHelperService.deletePersons(contactorIds);
    }
    return "succeed";

  }

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


  @RequestMapping(value = "/checkGeoInfo", method = RequestMethod.POST)
  @ResponseBody
  public void checkGeoInfo(){

   amcAssetService.checkGeoInfoWorker();
  }

}
