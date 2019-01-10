package com.wensheng.zcc.amc.controller;

import com.wensheng.zcc.amc.controller.helper.AssetQueryParam;
import com.wensheng.zcc.amc.controller.helper.PageInfo;
import com.wensheng.zcc.amc.controller.helper.PageReqRepHelper;
import com.wensheng.zcc.amc.module.vo.AmcAssetVo;
import com.wensheng.zcc.amc.service.AmcAssetService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenwei on 1/3/19
 * @project zcc-backend
 */
@RestController
@Slf4j
@RequestMapping("/api/amcid/{amcid}")
public class AmcAssetController {

  @Autowired
  AmcAssetService amcAssetService;

  @RequestMapping(value = "/assets", method = RequestMethod.POST)
  public Page<AmcAssetVo> getAmcAssets(@RequestBody  PageInfo pageInfo, @RequestBody AssetQueryParam assetQueryParam) throws Exception {
    Map<String, Direction> orderByParam = PageReqRepHelper.getOrderParam(pageInfo);
    Map<String, Object> queryParam = new HashMap<>();
    if(!CollectionUtils.isEmpty(assetQueryParam.getArea()) && assetQueryParam.getArea().size() > 1){
      queryParam.put("Area", assetQueryParam.getArea());
    }
    if(assetQueryParam.getEditStatus() != null && assetQueryParam.getEditStatus() > -1){
      queryParam.put("EditStatus", assetQueryParam.getEditStatus());
    }
    if(assetQueryParam.getStatus() != null && assetQueryParam.getStatus() > -1){
      queryParam.put("Status", assetQueryParam.getStatus());
    }

    List<AmcAssetVo> queryResults;
    int offset = PageReqRepHelper.getOffset(pageInfo);
    try{
      queryResults = amcAssetService.queryAssetPage(offset, pageInfo.getSize(), queryParam, orderByParam);
    }catch (Exception ex){
      log.error("got error when query:"+ex.getMessage());
      throw ex;
    }
    Long totalCount = amcAssetService.getAssetCount(queryParam);

    Page<AmcAssetVo> page = PageReqRepHelper.getPageResp(totalCount, queryResults, pageInfo);
    return page;
  }

}
