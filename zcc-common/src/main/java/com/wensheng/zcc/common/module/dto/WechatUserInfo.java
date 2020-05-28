package com.wensheng.zcc.common.module.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

@Data
public class WechatUserInfo {
  @SerializedName("country")
  private String country;

  @SerializedName("unionid")
  private String unionId;

  @SerializedName("province")
  private String province;

  @SerializedName("city")
  private String city;

  @SerializedName("openid")
  private String openId;

  @SerializedName("sex")
  private int sex;

  @SerializedName("nickname")
  private String nickName;

  @SerializedName("headimgurl")
  private String headImgUrl;

  @SerializedName("language")
  private String language;

  @SerializedName("privilege")
  private List<String> privilege;


}
