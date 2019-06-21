package com.wensheng.zcc.wechat.module.vo;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class WXMsgGroupResp extends GeneralResp {
  @SerializedName("msg_id")
  Long msgId;
  @SerializedName("msg_data_id")
  Long msgDataId;
}
