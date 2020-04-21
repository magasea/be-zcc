package com.wensheng.zcc.amc.controller;

import com.wensheng.zcc.amc.aop.EditActionChecker;
import com.wensheng.zcc.amc.aop.LogExecutionTime;
import com.wensheng.zcc.amc.aop.QueryChecker;
import com.wensheng.zcc.amc.controller.helper.QueryParam;
import com.wensheng.zcc.amc.module.dao.helper.AreaUnitEnum;
import com.wensheng.zcc.amc.module.dao.helper.ImagePathClassEnum;
import com.wensheng.zcc.amc.module.dao.helper.IsRecommandEnum;
import com.wensheng.zcc.amc.module.dao.helper.PublishStateEnum;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetAdditional;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetDocument;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetImage;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAsset;
import com.wensheng.zcc.amc.module.vo.AmcAssetDetailVo;
import com.wensheng.zcc.amc.module.vo.AmcAssetGeoNear;
import com.wensheng.zcc.amc.module.vo.AmcAssetVo;
import com.wensheng.zcc.amc.module.vo.base.BaseActionVo;
import com.wensheng.zcc.amc.service.AmcAssetService;
import com.wensheng.zcc.amc.service.AmcOssFileService;
import com.wensheng.zcc.amc.service.KafkaService;
import com.wensheng.zcc.amc.utils.SQLUtils;
import com.wensheng.zcc.common.params.AmcPage;
import com.wensheng.zcc.common.params.PageReqRepHelper;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.common.utils.AmcNumberUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author chenwei on 1/3/19
 * @project zcc-backend
 */
@Controller
@Slf4j
@RequestMapping("/api")
public class AmcSaleController {

  @Autowired
  AmcAssetService amcAssetService;





}
