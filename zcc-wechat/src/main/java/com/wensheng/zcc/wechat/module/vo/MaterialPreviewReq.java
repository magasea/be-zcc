package com.wensheng.zcc.wechat.module.vo;

import com.google.gson.annotations.SerializedName;
import java.util.Map;
import lombok.Data;

@Data
public class MaterialPreviewReq {
  @SerializedName("towxname")
  String toWxName;
  @SerializedName("mpnews")
  Map<String, String> mpNews;
  @SerializedName("msgtype")
  String msgType;

}
