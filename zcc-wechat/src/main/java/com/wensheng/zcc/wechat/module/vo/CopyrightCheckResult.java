package com.wensheng.zcc.wechat.module.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class CopyrightCheckResult {
  @JsonProperty("Count")
  Integer count;
  @JsonProperty("CheckState")
  Integer checkState;

  @JsonProperty("ResultList")
  List<WXMsgCPRCheckResult> resultList;

}
