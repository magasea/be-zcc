package com.wensheng.zcc.comnfunc.module.vo.base;

import com.google.gson.annotations.SerializedName;
import com.wensheng.zcc.common.module.dto.Building;
import com.wensheng.zcc.common.module.dto.Neighborhood;
import java.util.List;
import lombok.Data;

@Data
public class GaodeGeoQueryVal {
  @SerializedName("formatted_address")
  String formattedAddress;
  String country;
  String province;
  @SerializedName("citycode")
  String cityCode;
  String city;
  Object district;
  List<Object> township;
  Neighborhood neighborhood;
  Building building;
  String adcode;
  String[] street;
  String[] number;
  String location;
  String level;




}
