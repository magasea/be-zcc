package com.wensheng.zcc.amc.controller.helper;

import java.util.List;
import lombok.Data;

/**
 * @author chenwei on 1/8/19
 * @project zcc-backend
 */
@Data
public class AssetQueryParam {
  List<Long> area = null;
  Integer editStatus = -1;
  Integer status = -1;
  String title;

}
