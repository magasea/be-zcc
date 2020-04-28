package com.wensheng.zcc.cust.module.sync;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

@Data
public class AmapResultBean {

  /**
   * province : 河南省
   * city : 濮阳市
   * county : 南乐县
   * township : 元村镇
   * towncode : 410923102000
   * neighborhood : []
   * ne_type : []
   * street_number : []
   * amap_address : 河南省濮阳市南乐县元村镇留胄村
   * level : 村庄
   * lat : 36.087555
   * lon : 115.076538
   * address_before : 河南省濮阳市南乐县留胄村
   */

  @SerializedName("province")
  private String province;
  @SerializedName("city")
  private String city;
  @SerializedName("county")
  private String county;
  @SerializedName("township")
  private String township;
  @SerializedName("towncode")
  private String towncode;
  @SerializedName("amap_address")
  private String amapAddress;
  @SerializedName("level")
  private String level;
  @SerializedName("lat")
  private String lat;
  @SerializedName("lon")
  private String lon;
  @SerializedName("address_before")
  private String addressBefore;
  @SerializedName("neighborhood")
  private List<?> neighborhood;
  @SerializedName("ne_type")
  private List<?> neType;
  @SerializedName("street_number")
  private List<?> streetNumber;
}
