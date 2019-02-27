package com.wensheng.zcc.amc.controller;

import com.wensheng.zcc.amc.controller.helper.PageInfo;
import com.wensheng.zcc.amc.controller.helper.PageReqRepHelper;
import com.wensheng.zcc.amc.dao.mysql.mapper.CurtInfoMapper;
import com.wensheng.zcc.amc.module.dao.helper.DebtorRoleEnum;
import com.wensheng.zcc.amc.module.dao.helper.GuarantTypeEnum;
import com.wensheng.zcc.amc.module.dao.helper.SealStateEnum;
import com.wensheng.zcc.amc.module.dao.helper.AssetTypeEnum;
import com.wensheng.zcc.amc.module.dao.helper.PublishStateEnum;
import com.wensheng.zcc.amc.module.dao.helper.IsRecommandEnum;
import com.wensheng.zcc.amc.module.dao.helper.LawstatusEnum;
import com.wensheng.zcc.amc.module.dao.helper.EditActionEnum;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcPerson;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.CurtInfo;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.CurtInfoExample;
import com.wensheng.zcc.amc.module.vo.AmcCourtInfoVo;
import com.wensheng.zcc.amc.service.AmcHelperService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
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
  public Long addCourtInfo(@RequestBody AmcCourtInfoVo amcCourtInfoVo){
    return -1L;
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
    for(LawstatusEnum lawstatusEnum: LawstatusEnum.values()){
      result.add(String.format("%d:%s", lawstatusEnum.getStatus(), lawstatusEnum.getName()));
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

  @RequestMapping(value = "/grantorType", method = RequestMethod.GET)
  @ResponseBody
  public List<String> getGrantorType(){

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


  @RequestMapping(value = "/guarantType", method = RequestMethod.GET)
  @ResponseBody
  public List<String> getGuarantType(){

    List<String> result = new ArrayList<>();
    for(GuarantTypeEnum guarantTypeEnum: GuarantTypeEnum.values()){
      result.add(String.format("%d:%s", guarantTypeEnum.getType(), guarantTypeEnum.getName()));
    }
    return result;
  }


  @RequestMapping(value = "/amcid/{amcId}/amccontactors", method = RequestMethod.POST)
  @ResponseBody
  public Page<AmcPerson> getAmcPersons( @RequestBody PageInfo pageable) throws Exception {

    Map<String, Direction> orderByParam = PageReqRepHelper.getOrderParam(pageable);
    if (CollectionUtils.isEmpty(orderByParam)) {
      orderByParam.put("id", Direction.DESC);
    }

    List<AmcPerson> queryResults;
    int offset = PageReqRepHelper.getOffset(pageable);
    try {
      queryResults = amcHelperService.getAllAmcPerson(Long.valueOf(offset), pageable.getSize(),
          orderByParam);
    } catch (Exception ex) {
      log.error("got error when query:" + ex.getMessage());
      throw ex;
    }
    Long totalCount = amcHelperService.getPersonTotalCount();

    Page<AmcPerson> page = PageReqRepHelper.getPageResp(totalCount, queryResults, pageable);

    return page;

  }

  @RequestMapping(value = "/amcid/{amcId}/amccontactor/add", method = RequestMethod.POST)
  @ResponseBody
  public AmcPerson getAmcPersons(@RequestBody AmcPerson amcPerson){
    return amcHelperService.createPerson(amcPerson);
  }

  @RequestMapping(value = "/amcid/{amcId}/amccontactor/update", method = RequestMethod.POST)
  @ResponseBody
  public AmcPerson updateAmcPersons(@RequestBody AmcPerson amcPerson){
    return amcHelperService.updatePerson(amcPerson);
  }

}
