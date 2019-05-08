package com.wensheng.zcc.amc.controller;

import com.wensheng.zcc.amc.controller.helper.QueryParam;
import com.wensheng.zcc.amc.module.vo.AmcAssetDetailVo;
import com.wensheng.zcc.amc.service.AmcAssetService;
import com.wensheng.zcc.amc.service.AmcOssFileService;
import com.wensheng.zcc.amc.service.impl.AmcBaiDuLogisQuery;
import com.wensheng.zcc.common.module.LatLng;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/api/client/miniapp/")
public class MiniAppAssetController {
  @Autowired
  AmcAssetService amcAssetService;

  @Autowired
  AmcOssFileService amcOssFileService;

  @Autowired
  AmcBaiDuLogisQuery amcBaiDuLogisQuery;

  @RequestMapping(value = "/asset/allTitles", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, List<Long>> getAmcAssetsAllTitles( @RequestBody QueryParam queryParam) throws Exception{
    return amcAssetService.getAllAssetTitles();
  }

  @RequestMapping(value = "/asset", method = RequestMethod.POST)
  @ResponseBody
  public AmcAssetDetailVo getAmcAssetDetail( @RequestParam("amcAssetId") Long amcAssetId) throws Exception{

    return amcAssetService.queryAssetDetail(amcAssetId);

  }


  @RequestMapping(value = "/baidu/geo/", method = RequestMethod.POST)
  @ResponseBody
  public LatLng getLatLng(@RequestParam String address) throws UnsupportedEncodingException {
    return  amcBaiDuLogisQuery.getLogisByAddress(address);
  }



}
