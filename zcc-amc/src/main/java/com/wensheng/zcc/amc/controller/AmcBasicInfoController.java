package com.wensheng.zcc.amc.controller;

import com.wensheng.zcc.amc.dao.mysql.mapper.CurtInfoMapper;
import com.wensheng.zcc.amc.module.dao.helper.EditStatusEnum;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcPerson;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.CurtInfo;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.CurtInfoExample;
import com.wensheng.zcc.amc.module.vo.AmcCourtInfoVo;
import com.wensheng.zcc.amc.service.AmcHelperService;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
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

  @RequestMapping(value = "/editStatus", method = RequestMethod.GET)
  @ResponseBody
  public List<EditStatusEnum> getEditStatusList(){

    return Arrays.asList(EditStatusEnum.values());
  }

  @RequestMapping(value = "/amcid/{amcId}/amccontactors", method = RequestMethod.GET)
  @ResponseBody
  public Page<AmcPerson> getAmcPersons(@RequestBody PageRequest pageRequest){

    List<AmcPerson> amcPersonList = amcHelperService.getAllAmcPerson(pageRequest.getOffset(), pageRequest.getPageSize());
    Long total = amcHelperService.getPersonTotalCount();
    PageImpl page = new PageImpl(amcPersonList, pageRequest, total);
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
