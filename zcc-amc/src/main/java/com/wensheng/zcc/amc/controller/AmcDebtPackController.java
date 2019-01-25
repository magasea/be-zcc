package com.wensheng.zcc.amc.controller;

import com.wensheng.zcc.amc.controller.helper.PageInfo;
import com.wensheng.zcc.amc.controller.helper.PageReqRepHelper;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtpack;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditor;
import com.wensheng.zcc.amc.module.vo.AmcDebtVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtpackExtVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtpackVo;
import com.wensheng.zcc.amc.module.vo.base.BaseActionVo;
import com.wensheng.zcc.amc.service.AmcDebtpackService;
import com.wensheng.zcc.amc.utils.AmcNumberUtils;
import com.wensheng.zcc.amc.utils.ExceptionUtils;
import com.wensheng.zcc.amc.utils.ExceptionUtils.AmcExceptions;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
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
  @ResponseBody
  public AmcDebtpackExtVo createAmcDebtPack(@RequestBody AmcDebtpackExtVo amcDebtpackExtVo) throws Exception {
    return  amcDebtpackService.create(amcDebtpackExtVo);
  }

  @RequestMapping(value = "amcid/{amcId}/debtpack/update", method = RequestMethod.POST)
  @ResponseBody
  public AmcDebtpackVo updateAmcDebtPack(@RequestBody BaseActionVo<AmcDebtpackExtVo>  amcBaseDebtpackVo) throws Exception {
    AmcDebtpack amcDebtpack = new AmcDebtpack();
    AmcDebtpackExtVo amcDebtpackExtVo = amcBaseDebtpackVo.getContent();
    BeanUtils.copyProperties(amcDebtpackExtVo, amcDebtpack);
    AmcDebtpackVo amcDebtpackVo = amcDebtpackService.update(amcDebtpack);

    amcDebtpackService.updateDebtpackCreditorRel(amcDebtpackExtVo.getAmcDebtpackInfo().getId(), amcDebtpackExtVo.getAmcOrigCreditorList());
    return amcDebtpackVo;
  }


  @RequestMapping(value = "amcid/{amcId}/debtpack/origcreditor/add", method = RequestMethod.POST)
  @ResponseBody
  public AmcOrigCreditor createAmcCreditor(@RequestBody AmcOrigCreditor amcOrigCreditor) throws Exception {
    if(StringUtils.isEmpty(amcOrigCreditor.getBankName())){
      throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_ORIG_CREDITOR, "bankname is empty");
    }

    AmcOrigCreditor amcOrigCreditorResult;
    amcOrigCreditorResult = amcDebtpackService.createCreditor(amcOrigCreditor);
    return amcOrigCreditorResult;
  }

  @RequestMapping(value = "amcid/{amcId}/debtpack/origcreditors", method = RequestMethod.POST)
  @ResponseBody
  public Page<AmcOrigCreditor> getAmcCreditor(@RequestBody PageInfo pageable) throws Exception {


    Map<String, Sort.Direction> orderByParam = PageReqRepHelper.getOrderParam(pageable);
    if(CollectionUtils.isEmpty(orderByParam)){
      orderByParam.put("id", Direction.DESC);
    }

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
