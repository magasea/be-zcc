package com.wensheng.zcc.wechat.module.vo;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class WXGetTicketResp extends GeneralResp {
  String ticket;
  @SerializedName("expires_in")
  Long expiresIn;

}
