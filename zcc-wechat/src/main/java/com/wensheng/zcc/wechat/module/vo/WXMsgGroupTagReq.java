package com.wensheng.zcc.wechat.module.vo;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

@Data
public class WXMsgGroupTagReq {

  WXMsgGroupTagFilter filter;
  @SerializedName("mpnews")
  MpNews mpNews;
  @SerializedName("msgtype")
  String msgType;
  @SerializedName("send_ignore_reprint")
  Integer sendIgnoreRepprint;
  @SerializedName("touser")
  List<String> toUser;
}
