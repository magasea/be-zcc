package com.wensheng.zcc.amc.controller;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.ZccDebtpack;
import com.wensheng.zcc.common.params.AmcPage;
import com.wensheng.zcc.common.params.PageInfo;
import com.wensheng.zcc.common.params.PageReqRepHelper;
import com.wensheng.zcc.amc.module.vo.base.BaseActionVo;
import com.wensheng.zcc.amc.service.AmcDebtpackService;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
  public ZccDebtpack createAmcDebtPack(@RequestBody ZccDebtpack zccDebtpack) throws Exception {
    return  amcDebtpackService.create(zccDebtpack);
  }

  @RequestMapping(value = "amcid/{amcId}/debtpacks", method = RequestMethod.POST)
  @ResponseBody
  public AmcPage<ZccDebtpack> getAllDebtPacks(@RequestBody PageInfo pageable) throws Exception {
    Map<String, Sort.Direction> orderByParam = PageReqRepHelper.getOrderParam(pageable);
    if(CollectionUtils.isEmpty(orderByParam)){
      orderByParam.put("id", Direction.DESC);
    }

    int offset = PageReqRepHelper.getOffset(pageable);
    List<ZccDebtpack> debtpacks =  amcDebtpackService.queryAllDebtPacks(offset, pageable.getSize(), orderByParam);
    Long count = amcDebtpackService.getTotalCnt4Debtpacks();
//    Page<ZccDebtpack> page = PageReqRepHelper.getPageResp(count, debtpacks, pageable);
    AmcPage<ZccDebtpack> page = new AmcPage<>();
    page.setContent(debtpacks);
    page.setTotal(count);

    return page;
  }

  @RequestMapping(value = "amcid/{amcId}/debtpack", method = RequestMethod.POST)
  @ResponseBody
  public ZccDebtpack getDebtPack(@RequestParam("debtPackId") Long debtPackId) throws Exception {


    return amcDebtpackService.get(debtPackId);

  }

  @RequestMapping(value = "amcid/{amcId}/debtpack/update", method = RequestMethod.POST)
  @ResponseBody
  public ZccDebtpack updateAmcDebtPack(@RequestBody BaseActionVo<ZccDebtpack>  amcBaseDebtpack) throws Exception {
    ZccDebtpack zccDebtpack = amcBaseDebtpack.getContent();
    amcDebtpackService.update(zccDebtpack);
    return zccDebtpack;
  }









}
