package com.wensheng.zcc.amc.module.vo;

import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class AmcDebtUploadImg2WXRlt {
  Long debtId;
  String wechatUrl;
  String mediaId;
  Map<Long, List<String>> assetImgs;

}
