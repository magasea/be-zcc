package com.wensheng.zcc.wechat.module.vo;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
class WXMaterialContent{
  WXMaterialItem content;
  @SerializedName("update_time")
  Long  updateTime;
  @SerializedName("media_id")
  String mediaId;
  String name;
  String url;
}
