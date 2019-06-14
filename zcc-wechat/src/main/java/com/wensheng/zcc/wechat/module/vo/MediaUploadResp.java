package com.wensheng.zcc.wechat.module.vo;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public
class MediaUploadResp extends GeneralResp{
  @SerializedName("url")
  String url;

  @SerializedName("media_id")
  String mediaId;
}
