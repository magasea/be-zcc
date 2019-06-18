package com.wensheng.zcc.wechat.module.vo;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class WXMaterialMod {
  @SerializedName("media_id")
  String mediaId;
  Integer index;
  WXMaterial articles;

}
