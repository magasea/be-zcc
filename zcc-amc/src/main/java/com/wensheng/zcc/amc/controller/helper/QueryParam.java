package com.wensheng.zcc.amc.controller.helper;

import com.wensheng.zcc.common.aop.SQLInjectionSafe;
import com.wensheng.zcc.common.params.PageInfo;
import java.util.List;
import lombok.Data;

/**
 * @author chenwei on 1/8/19
 * @project zcc-backend
 */
@Data
public class QueryParam {
  Long debtId = -1L;
  Long assetId = -1L;
  Integer debtType = -1;
  String[] courtLocations = null;
  List<Long> area = null;
  List<Long> landArea = null;
  List<String> location = null;
  List<String> locationCode = null;
  List<Integer> publishStates = null;
//  Integer editStatus = -1;
  List<Long> baseAmount = null;
  List<Long> valuation = null;
  Integer sealStatus = -1;
  Integer assetType = -1;
  @SQLInjectionSafe
  String title;
  List<Integer> recommand = null;
//  Long amcContactorId = -1L;
  String amcContactorName = null;
  Long courtId = -1L;
  List<Long> debtPackIds = null;
  PageInfo pageInfo;



}
