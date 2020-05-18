package com.wensheng.zcc.cust.module.sync;

import java.util.List;
import lombok.Data;

@Data
public class AddCrawlCmpyResultDTO {
  String appName;
  String packageName;
  String packageId;
  List<CmpyBizInfoResult> cmpyBizInfoResultList;
}
