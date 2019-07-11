package com.wensheng.zcc.comnfunc.module.vo.base;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class GaodeGeneralResp {
  Integer status;
  String info;
  @SerializedName("infocode")
  String infoCode;
  Integer count;
}
