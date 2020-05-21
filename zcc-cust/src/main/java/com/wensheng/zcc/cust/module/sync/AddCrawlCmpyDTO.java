package com.wensheng.zcc.cust.module.sync;

import lombok.Data;

@Data
public class AddCrawlCmpyDTO {
  String appName;
  String batchName;
  String batchId;
  String cmpyNames;
  int cmpyCount;
  String responseType;
}
