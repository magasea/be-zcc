package com.wensheng.zcc.cust.module.sync;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class BaiduResultBean {

  /**
   * province : 河南省
   * city : 濮阳市
   * district : 南乐县
   * township : 元村镇
   * towncode : 410923102000
   * baidu_address : 河南省濮阳市南乐县
   */

  @SerializedName("province")
  private String province;
  @SerializedName("city")
  private String city;
  @SerializedName("district")
  private String district;
  @SerializedName("township")
  private String township;
  @SerializedName("towncode")
  private long towncode;
  @SerializedName("baidu_address")
  private String baiduAddress;


}
