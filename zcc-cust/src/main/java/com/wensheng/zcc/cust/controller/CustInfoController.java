package com.wensheng.zcc.cust.controller;

import com.wensheng.zcc.common.params.AmcPage;
import com.wensheng.zcc.common.params.PageReqRepHelper;
import com.wensheng.zcc.cust.controller.helper.QueryParam;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import com.wensheng.zcc.cust.module.helper.CustTypeEnum;
import com.wensheng.zcc.cust.module.vo.CustTrdInfoVo;
import com.wensheng.zcc.cust.service.CustInfoService;
import com.wensheng.zcc.cust.service.ScriptSysService;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chenwei on 4/15/19
 * @project miniapp-backend
 */
@Controller
@Slf4j
@RequestMapping("/amc/cust/custinfo")
public class CustInfoController {

  @Autowired
  CustInfoService custInfoService;

  @Autowired
  ScriptSysService scriptSysService;

  @RequestMapping(value = "/addCmpy", method = RequestMethod.POST)
  @ResponseBody
  public CustTrdCmpy addCompany(@RequestBody CustTrdCmpy custTrdCmpy){

    return custInfoService.addCompany(custTrdCmpy);
  }

  @RequestMapping(value = "/getCmpies", method = RequestMethod.POST)
  @ResponseBody
  public List<CustTrdCmpy> getCmpies(){

      return custInfoService.getCmpies();


  }

  @RequestMapping(value = "/getCmpy", method = RequestMethod.POST)
  @ResponseBody
  public CustTrdCmpy getCmpy(@RequestParam Long companyId){

    return custInfoService.getCompany(companyId);

  }

  @RequestMapping(value = "/getPerson", method = RequestMethod.POST)
  @ResponseBody
  public CustTrdPerson getPerson(@RequestParam Long personId){

    return custInfoService.getPerson(personId);

  }

  @RequestMapping(value = "/getPersons", method = RequestMethod.POST)
  @ResponseBody
  public List<CustTrdPerson> getPersons(){
      return custInfoService.getTrdPersons();

  }

  @RequestMapping(value = "/addPerson", method = RequestMethod.POST)
  @ResponseBody
  public CustTrdPerson addPerson(@RequestBody CustTrdPerson custTrdPerson){

     return custInfoService.addTrdPerson(custTrdPerson);
  }

  @RequestMapping(value = "/doSynchronization", method = RequestMethod.GET)
  @ResponseBody
  public void doSynchronization() throws Exception {
    scriptSysService.doSynchWithScriptOn();
  }


  @RequestMapping(value = "/getCustTrdInfo", method = RequestMethod.POST)
  @ResponseBody
  public AmcPage<CustTrdInfoVo> getCustTrdInfo(@RequestBody QueryParam queryParam) throws Exception {

    Map<String, Direction> orderByParam = PageReqRepHelper.getOrderParam(queryParam.getPageInfo());
    if(CollectionUtils.isEmpty(orderByParam)){
      orderByParam.put("id", Direction.DESC);

    }


    List<CustTrdInfoVo> queryResults = null;
    Long totalCount = null;
    int offset = PageReqRepHelper.getOffset(queryParam.getPageInfo());
    try{
      if(queryParam.getCustType() == CustTypeEnum.COMPANY.getId()){
        queryResults = custInfoService.queryCmpyTradePage(offset, queryParam.getPageInfo().getSize(), queryParam,
            orderByParam);
        totalCount = custInfoService.getCmpyTradeCount(queryParam);
      }else if(queryParam.getCustType() == CustTypeEnum.PERSON.getId()){
        orderByParam.put("mobile_num", Direction.DESC);
        queryResults = custInfoService.queryPersonTradePage(offset, queryParam.getPageInfo().getSize(), queryParam,
            orderByParam);
        totalCount = custInfoService.getPersonTradeCount(queryParam);
      }

    }catch (Exception ex){
      log.error("got error when query:"+ex.getMessage());
      throw ex;
    }

//    Page<AmcAssetVo> page = PageReqRepHelper.getPageResp(totalCount, queryResults, assetQueryParam.getPageInfo());
    return PageReqRepHelper.getAmcPage(queryResults, totalCount );

  }

}
