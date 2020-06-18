package com.wensheng.zcc.cust.controller;

import com.wensheng.zcc.cust.config.aop.AddTraceLogId;
import com.wensheng.zcc.cust.config.aop.ModifyCheckerCustPerson;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import com.wensheng.zcc.cust.module.vo.helper.ModifyResult;
import com.wensheng.zcc.cust.service.CustPersonService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@AddTraceLogId
@RequestMapping("/amc/cust/custPersonConytoller")
public class CustPersonConytoller {

  @Autowired
  CustPersonService custPersonService;

  @RequestMapping(value = "/addCmpyAmcContactor", method = RequestMethod.POST)
  @ResponseBody
  @ModifyCheckerCustPerson
  public ModifyResult addCustPerson(@RequestBody CustTrdPerson custTrdPerson) throws Exception {
    custPersonService.createCustPerson(custTrdPerson);
    ModifyResult modifyResult = new ModifyResult();
    modifyResult.setSuccess(true);
    return modifyResult;
  }

  @RequestMapping(value = "/updateCustPerson", method = RequestMethod.POST)
  @ResponseBody
  @ModifyCheckerCustPerson
  public ModifyResult updateCustPerson(@RequestBody CustTrdPerson custTrdPerson) throws Exception {
   return custPersonService.updateCustPerson(custTrdPerson);
  }

  @RequestMapping(value = "/getPersonByIdList", method = RequestMethod.POST)
  @ResponseBody
  public List<CustTrdPerson> getPersonByIdList(@RequestBody List<Long> idList) throws Exception {
    return custPersonService.selectPersonByIdList(idList);
  }

  @RequestMapping(value = "/mergeCustPerson", method = RequestMethod.POST)
  @ResponseBody
//  @MergeCustChecker
  public void mergeCustPerson(@RequestBody List<Long> fromPersonIds, @RequestParam Long toPersonId,
      @RequestParam Long updateBy) throws Exception {
    custPersonService.mergeCustPerson(fromPersonIds, toPersonId, updateBy);
  }
}
