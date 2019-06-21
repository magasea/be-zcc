package com.wensheng.zcc.wechat.module.vo;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class WXMaterialPreviewResp extends GeneralResp {
  @SerializedName("msg_id")
  String msgId;
}
