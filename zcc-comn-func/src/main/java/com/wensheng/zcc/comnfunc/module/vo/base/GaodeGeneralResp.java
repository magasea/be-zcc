package com.wensheng.zcc.comnfunc.module.vo.base;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class GaodeGeneralResp {
  @SerializedName("count")
  private String count;

  @SerializedName("infocode")
  private String infocode;



  @SerializedName("status")
  private String status;

  @SerializedName("info")
  private String info;

}
