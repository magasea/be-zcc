package com.wensheng.zcc.cust.module.sync;

import java.util.List;
import lombok.Data;

@Data
public class AddCrawlCmpyResultDTO {
  String appName;
  String batchName;
  String batchId;
  String mqRequestName;
  int cmpyCount;
  int responseType;
  List<CmpyBizInfoResult> cmpyBizInfoResults;
}
