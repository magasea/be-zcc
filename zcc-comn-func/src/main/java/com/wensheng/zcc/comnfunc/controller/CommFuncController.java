package com.wensheng.zcc.comnfunc.controller;

import com.wensheng.zcc.common.module.dto.AmcRegionInfo;
import com.wensheng.zcc.comnfunc.module.dto.baidu.Content;
import com.wensheng.zcc.comnfunc.module.vo.base.GaodeGeoQueryVal;
import com.wensheng.zcc.comnfunc.service.GaoDeService;
import com.wensheng.zcc.comnfunc.service.RegionService;
import com.wensheng.zcc.comnfunc.service.impl.AmcBaiDuLogisQuery;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/amc/commFunc")
public class CommFuncController {

  @Autowired
  GaoDeService gaoDeService;

  @Autowired
  RegionService regionService;

  @Autowired
  AmcBaiDuLogisQuery amcBaiDuLogisQuery;

  @RequestMapping(value = "/queryGeoInfo", method = RequestMethod.POST)
  @ResponseBody
  public List<GaodeGeoQueryVal> queryGeoInfo(@RequestParam  String city, @RequestParam String address) throws Exception {
    return gaoDeService.getGeoInfoFromAddress(address, city);
  }


  @RequestMapping(value = "/getGeoInfo", method = RequestMethod.POST)
  @ResponseBody
  public AmcRegionInfo queryGeoInfo(@RequestParam  Double lng, @RequestParam Double lat) throws Exception {
    return regionService.getRegionInfoByLngLat(lng, lat);
  }

  @RequestMapping(value = "/getGeoByIp", method = RequestMethod.GET)
  @ResponseBody
  public Content queryGeoInfoByIp(@RequestParam  String ip) throws Exception {
    return amcBaiDuLogisQuery.getAddressByIp(ip);
  }
}
