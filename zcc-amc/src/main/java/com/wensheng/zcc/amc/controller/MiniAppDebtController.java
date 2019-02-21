package com.wensheng.zcc.amc.controller;

import com.wensheng.zcc.amc.controller.helper.PageInfo;
import com.wensheng.zcc.amc.controller.helper.PageReqRepHelper;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCmpy;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCreditor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcGrntor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditor;
import com.wensheng.zcc.amc.module.vo.AmcDebtExtVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtVo;
import com.wensheng.zcc.amc.service.AmcDebtService;
import com.wensheng.zcc.amc.service.AmcDebtpackService;
import com.wensheng.zcc.amc.service.AmcOssFileService;
import com.wensheng.zcc.amc.service.ZccRulesService;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chenwei on 2/21/19
 * @project zcc-backend
 */
@Controller
@Slf4j
@RequestMapping("/api/client/miniapp/debt")
public class MiniAppDebtController {
  @Autowired
  AmcOssFileService amcOssFileService;

  @Autowired
  AmcDebtService amcDebtService;

  @Autowired
  AmcDebtpackService amcDebtpackService;


  @Autowired
  ZccRulesService zccRulesService;





  @RequestMapping(value = "/creditors", method = RequestMethod.POST)
  @ResponseBody
  public Page<AmcCreditor> getAmcCreditors(@RequestBody PageInfo pageable) throws Exception {

    Map<String, Direction> orderByParam = PageReqRepHelper.getOrderParam(pageable);
    if (CollectionUtils.isEmpty(orderByParam)) {
      orderByParam.put("id", Direction.DESC);
    }

    List<AmcCreditor> queryResults;
    int offset = PageReqRepHelper.getOffset(pageable);
    try {
      queryResults = amcDebtService.getAllCreditors(Long.valueOf(offset), pageable.getSize(), orderByParam);
    } catch (Exception ex) {
      log.error("got error when query:" + ex.getMessage());
      throw ex;
    }
    Long totalCount = amcDebtService.getTotalCreditorCount();

    Page<AmcCreditor> page = PageReqRepHelper.getPageResp(totalCount, queryResults, pageable);

    return page;
  }


  @RequestMapping(value = "/companies", method = RequestMethod.POST)
  @ResponseBody
  public Page<AmcCmpy> getAmcCompanies(@RequestBody PageInfo pageable) throws Exception {

    Map<String, Sort.Direction> orderByParam = PageReqRepHelper.getOrderParam(pageable);
    if (CollectionUtils.isEmpty(orderByParam)) {
      orderByParam.put("id", Direction.DESC);
    }

    List<AmcCmpy> queryResults;
    int offset = PageReqRepHelper.getOffset(pageable);
    try {
      queryResults = amcDebtService.getAllCompanies(Long.valueOf(offset), pageable.getSize(), orderByParam);
    } catch (Exception ex) {
      log.error("got error when query:" + ex.getMessage());
      throw ex;
    }
    Long totalCount = amcDebtService.getTotalCompanyCount();

    Page<AmcCmpy> page = PageReqRepHelper.getPageResp(totalCount, queryResults, pageable);

    return page;
  }




  @RequestMapping(value = "/api/amcid/{id}/debt/get", method = RequestMethod.POST)
  @ResponseBody
  public AmcDebtExtVo queryDebt(@RequestParam("debtId") Long debtId)
      throws Exception {

    AmcDebtExtVo amcDebtExtVo = new AmcDebtExtVo();

    AmcDebtVo amcDebtVo = amcDebtService.get(debtId);
    amcDebtExtVo.setAmcDebtVo(amcDebtVo);

    try {
      List<AmcCreditor> creditors = amcDebtService.getCreditors(debtId);

      List<AmcGrntor> amcGrntors = amcDebtService.getGrantors(debtId);

      AmcOrigCreditor origCreditor = amcDebtService.getOriginCreditor(debtId);
      amcDebtExtVo.setCreditors(creditors);
      amcDebtExtVo.setAmcGrntors(amcGrntors);
      amcDebtExtVo.setOrigCreditor(origCreditor);
    } catch (Exception ex) {
      log.error("failed to get creditor or grantor", ex);
    }

    return amcDebtExtVo;
  }



  @RequestMapping(value = "/api/amcid/{id}/debts", method = RequestMethod.POST)
  @ResponseBody
  public Page<AmcDebtVo> queryDebts(@RequestBody PageInfo pageable)
      throws Exception {
    Map<String, Sort.Direction> orderByParam = PageReqRepHelper.getOrderParam(pageable);
    if (CollectionUtils.isEmpty(orderByParam)) {
      orderByParam.put("id", Direction.DESC);
    }

    List<AmcDebtVo> queryResults;
    int offset = PageReqRepHelper.getOffset(pageable);
    try {
      queryResults = amcDebtService.queryAllExt(Long.valueOf(offset), pageable.getSize(), orderByParam);
    } catch (Exception ex) {
      log.error("got error when query:" + ex.getMessage());
      throw ex;
    }
    Long totalCount = amcDebtService.getTotalCount();

    Page<AmcDebtVo> page = PageReqRepHelper.getPageResp(totalCount, queryResults, pageable);

    return page;
  }




  @RequestMapping(value = "/origcreditors", method = RequestMethod.POST)
  @ResponseBody
  public Page<AmcOrigCreditor> getAmcCreditor(@RequestBody PageInfo pageable) throws Exception {

    Map<String, Sort.Direction> orderByParam = PageReqRepHelper.getOrderParam(pageable);
    if (CollectionUtils.isEmpty(orderByParam)) {
      orderByParam.put("id", Direction.DESC);
    }

    int offset = PageReqRepHelper.getOffset(pageable);
    List<AmcOrigCreditor> amcOrigCreditors;
    try {
      amcOrigCreditors = amcDebtService.getAllOrigCreditors(offset, pageable.getSize(), orderByParam);

    } catch (Exception ex) {
      log.error("got error when query:" + ex.getMessage());
      throw ex;
    }
    Long totalCount = amcDebtService.getCreditorsCount();
    Page<AmcOrigCreditor> amcOrigCreditorPage = PageReqRepHelper.getPageResp(totalCount, amcOrigCreditors, pageable);

    return amcOrigCreditorPage;
  }

  @RequestMapping(value = "/allTitles", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, List<Long>> getAllDebtTitles() throws Exception {

    return amcDebtService.getAllTitles();

  }
}
