package com.wensheng.zcc.amc.controller.helper;

import java.util.List;
import lombok.Data;

/**
 * @author chenwei on 1/8/19
 * @project zcc-backend
 */
@Data
public class AssetQueryParam {
  Long debtId = -1L;
  Long assetId = -1L;
  List<Long> area = null;
  List<Long> landArea = null;
  List<String> location = null;
  Integer editStatus = -1;
  Integer status = -1;
  Integer assetType = -1;
  String title;
  Integer recommand = -1;


  PageInfo pageInfo;

}
