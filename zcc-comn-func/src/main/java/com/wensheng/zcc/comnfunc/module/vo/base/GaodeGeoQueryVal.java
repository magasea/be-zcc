package com.wensheng.zcc.comnfunc.module.vo.base;

import com.google.gson.annotations.SerializedName;
import com.wensheng.zcc.comnfunc.module.vo.base.test.Building;
import com.wensheng.zcc.comnfunc.module.vo.base.test.Neighborhood;
import java.util.List;
import lombok.Data;

@Data
public class GaodeGeoQueryVal {
  @SerializedName("country")
  private String country;

  @SerializedName("formatted_address")
  private String formattedAddress;

  @SerializedName("city")
  private String city;

  @SerializedName("adcode")
  private String adcode;

  @SerializedName("level")
  private String level;

  @SerializedName("building")
  private Building building;

  @SerializedName("number")
  private List<Object> number;

  @SerializedName("province")
  private String province;

//  @SerializedName("citycode")
//  private String citycode;

//  @SerializedName("street")
//  private List<Object> street;

//  @SerializedName("district")
//  private String district;

  @SerializedName("location")
  private String location;

  @SerializedName("neighborhood")
  private Neighborhood neighborhood;

  @SerializedName("township")
  private List<Object> township;



}
