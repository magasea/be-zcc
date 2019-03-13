package com.wensheng.zcc.amc.controller;

import com.wensheng.zcc.amc.controller.helper.AmcPage;
import com.wensheng.zcc.amc.controller.helper.PageInfo;
import com.wensheng.zcc.amc.controller.helper.PageReqRepHelper;
import com.wensheng.zcc.amc.module.dao.mongo.origin.Debtpack;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtpack;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditor;
import com.wensheng.zcc.amc.module.vo.AmcDebtVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtpackExtVo;
import com.wensheng.zcc.amc.module.vo.base.BaseActionVo;
import com.wensheng.zcc.amc.service.AmcDebtpackService;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
  public AmcDebtpack createAmcDebtPack(@RequestBody AmcDebtpack amcDebtpack) throws Exception {
    return  amcDebtpackService.create(amcDebtpack);
  }

  @RequestMapping(value = "amcid/{amcId}/debtpacks", method = RequestMethod.POST)
  @ResponseBody
  public AmcPage<AmcDebtpack> getAllDebtPacks(@RequestBody PageInfo pageable) throws Exception {
    Map<String, Sort.Direction> orderByParam = PageReqRepHelper.getOrderParam(pageable);
    if(CollectionUtils.isEmpty(orderByParam)){
      orderByParam.put("id", Direction.DESC);
    }

    int offset = PageReqRepHelper.getOffset(pageable);
    List<AmcDebtpack> debtpacks =  amcDebtpackService.queryAllDebtPacks(offset, pageable.getSize(), orderByParam);
    Long count = amcDebtpackService.getTotalCnt4Debtpacks();
//    Page<AmcDebtpack> page = PageReqRepHelper.getPageResp(count, debtpacks, pageable);
    AmcPage<AmcDebtpack> page = new AmcPage<>();
    page.setContent(debtpacks);
    page.setTotal(count);

    return page;
  }

  @RequestMapping(value = "amcid/{amcId}/debtpack", method = RequestMethod.POST)
  @ResponseBody
  public AmcDebtpack getDebtPack(@RequestParam("debtPackId") Long debtPackId) throws Exception {


    return amcDebtpackService.get(debtPackId);

  }

  @RequestMapping(value = "amcid/{amcId}/debtpack/update", method = RequestMethod.POST)
  @ResponseBody
  public AmcDebtpack updateAmcDebtPack(@RequestBody BaseActionVo<AmcDebtpack>  amcBaseDebtpack) throws Exception {
    AmcDebtpack amcDebtpack = amcBaseDebtpack.getContent();
    amcDebtpackService.update(amcDebtpack);
    return amcDebtpack;
  }









}
