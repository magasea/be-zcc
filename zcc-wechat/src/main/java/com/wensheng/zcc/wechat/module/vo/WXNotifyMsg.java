package com.wensheng.zcc.wechat.module.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WXNotifyMsg {
  @JsonProperty("ToUserName")
  String toUserName;
  @JsonProperty("FromUserName")
  String fromUserName;
  @JsonProperty("CreateTime")
  Long createTime;
  @JsonProperty("MsgType")
  String msgType;
  @JsonProperty("Event")
  String event;
}
