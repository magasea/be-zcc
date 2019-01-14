package com.wensheng.zcc.amc.controller;

import com.wensheng.zcc.amc.controller.helper.AssetQueryParam;
import com.wensheng.zcc.amc.controller.helper.PageInfo;
import com.wensheng.zcc.amc.controller.helper.PageReqRepHelper;
import com.wensheng.zcc.amc.module.vo.AmcAssetDetailVo;
import com.wensheng.zcc.amc.module.vo.AmcAssetVo;
import com.wensheng.zcc.amc.service.AmcAssetService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenwei on 1/3/19
 * @project zcc-backend
 */
@Controller
@Slf4j
@RequestMapping("/api")
public class AmcAssetController {

  @Autowired
  AmcAssetService amcAssetService;

  @RequestMapping(value = "/amcid/{amcid}/assets", method = RequestMethod.POST)
  @ResponseBody
  public Page<AmcAssetVo> getAmcAssets(
      @RequestBody(required = false) AssetQueryParam assetQueryParam) throws Exception {
    Map<String, Direction> orderByParam = PageReqRepHelper.getOrderParam(assetQueryParam.getPageInfo());
    if(CollectionUtils.isEmpty(orderByParam)){
      orderByParam.put("id", Direction.DESC);
    }
    Map<String, Object> queryParam = new HashMap<>();

    if(assetQueryParam.getDebtId() > 0){
      queryParam.put("DebtId", assetQueryParam.getDebtId());
    }
    if(!CollectionUtils.isEmpty(assetQueryParam.getArea()) && assetQueryParam.getArea().size() > 1){
      queryParam.put("Area", assetQueryParam.getArea());
    }
    if(assetQueryParam.getEditStatus() != null && assetQueryParam.getEditStatus() > -1){
      queryParam.put("EditStatus", assetQueryParam.getEditStatus());
    }
    if(assetQueryParam.getStatus() != null && assetQueryParam.getStatus() > -1){
      queryParam.put("Status", assetQueryParam.getStatus());
    }
    if(!StringUtils.isEmpty(assetQueryParam.getTitle())){
      queryParam.put("Title", assetQueryParam.getTitle());
    }

    List<AmcAssetVo> queryResults;
    int offset = PageReqRepHelper.getOffset(assetQueryParam.getPageInfo());
    try{
      queryResults = amcAssetService.queryAssetPage(offset, assetQueryParam.getPageInfo().getSize(), queryParam,
          orderByParam);
    }catch (Exception ex){
      log.error("got error when query:"+ex.getMessage());
      throw ex;
    }
    Long totalCount = amcAssetService.getAssetCount(queryParam);

    Page<AmcAssetVo> page = PageReqRepHelper.getPageResp(totalCount, queryResults, assetQueryParam.getPageInfo());
    return page;
  }

  @RequestMapping(value = "/amcid/{amcid}/allTitles", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, List<Long>> getAmcAssetsAllTitles( @RequestBody AssetQueryParam assetQueryParam) throws Exception{
    return amcAssetService.getAllAssetTitles();
  }

  @RequestMapping(value = "/amcid/{amcid}/asset", method = RequestMethod.POST)
  @ResponseBody
  public AmcAssetDetailVo getAmcAssetDetail( @RequestParam("amcAssetId") Long amcAssetId) throws Exception{

    return amcAssetService.queryAssetDetail(amcAssetId);

  }



}
