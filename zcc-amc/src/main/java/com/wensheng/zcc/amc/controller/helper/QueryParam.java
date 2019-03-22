package com.wensheng.zcc.amc.controller.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import org.springframework.util.CollectionUtils;

/**
 * @author chenwei on 1/8/19
 * @project zcc-backend
 */
@Data
public class QueryParam {
  Long debtId = -1L;
  Long assetId = -1L;
  List<Long> area = null;
  List<Long> landArea = null;
  List<String> location = null;
  Integer editStatus = -1;
  Integer sealStatus = -1;
  Integer assetType = -1;
  @SQLInjectionSafe
  String title;
  Integer recommand = -1;
  Long amcContactorId = -1L;


  PageInfo pageInfo;



}