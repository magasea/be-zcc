package com.wensheng.zcc.wechat.module.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class WXCopyrightCheckResults extends WXNotifyMsg {

  @JsonProperty("MsgID")
  Long MsgID;
  @JsonProperty("Status")


  String Status;
  @JsonProperty("TotalCount")
  Integer TotalCount;
  @JsonProperty("FilterCount")

  Integer FilterCount;
  @JsonProperty("SentCount")

  Integer SentCount;
  @JsonProperty("ErrorCount")

  Integer ErrorCount;
  @JsonProperty("CheckState")
  Integer checkState;
  @JsonProperty("CopyrightCheckResult")
  CopyrightCheckResult copyrightCheckResult;
}
