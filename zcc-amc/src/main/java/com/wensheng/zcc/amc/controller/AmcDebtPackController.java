package com.wensheng.zcc.amc.controller;

import com.wensheng.zcc.amc.controller.helper.PageInfo;
import com.wensheng.zcc.amc.controller.helper.PageReqRepHelper;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtpack;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditor;
import com.wensheng.zcc.amc.module.vo.AmcDebtVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtpackVo;
import com.wensheng.zcc.amc.service.AmcDebtpackService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chenwei on 1/7/19
 * @project zcc-backend
 */
@Controller
@RequestMapping("/api/")
@Slf4j
public class AmcDebtPackController {

  @Autowired
  AmcDebtpackService amcDebtpackService;



  @RequestMapping(value = "amcid/{amcId}/debtpack/add", method = RequestMethod.POST)
  public AmcDebtpack createAmcDebtPack(AmcDebtpackVo amcDebtpackVo){
    amcDebtpackService.create(amcDebtpackVo);
    return null;
  }

  @RequestMapping(value = "amcid/{amcId}/deptpack/creditor/add", method = RequestMethod.POST)
  @ResponseBody
  public AmcOrigCreditor createAmcCreditor(@RequestBody AmcOrigCreditor amcOrigCreditor){
    AmcOrigCreditor amcOrigCreditorResult = amcDebtpackService.createCreditor(amcOrigCreditor);
    return amcOrigCreditorResult;

  }

  @RequestMapping(value = "amcid/{amcId}/deptpack/creditors", method = RequestMethod.GET)
  @ResponseBody
  public Page<AmcOrigCreditor> getAmcCreditor(@RequestBody PageInfo pageable) throws Exception {


    Map<String, Sort.Direction> orderByParam = PageReqRepHelper.getOrderParam(pageable);

    int offset = PageReqRepHelper.getOffset(pageable);
    List<AmcOrigCreditor> amcOrigCreditors ;
    try{
      amcOrigCreditors = amcDebtpackService.getAllCreditors(offset, pageable.getSize(), orderByParam);

    }catch (Exception ex){
      log.error("got error when query:"+ex.getMessage());
      throw ex;
    }
    Long totalCount = amcDebtpackService.getCreditorsCount();
    Page<AmcOrigCreditor> amcOrigCreditorPage = PageReqRepHelper.getPageResp(totalCount, amcOrigCreditors, pageable);


    return amcOrigCreditorPage;
  }




}
