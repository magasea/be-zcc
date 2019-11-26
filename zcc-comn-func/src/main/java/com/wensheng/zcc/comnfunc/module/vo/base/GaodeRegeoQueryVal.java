package com.wensheng.zcc.comnfunc.module.vo.base;

import com.google.gson.annotations.SerializedName;
import com.wensheng.zcc.common.module.dto.AddressComponent;
import com.wensheng.zcc.common.module.dto.Building;
import com.wensheng.zcc.common.module.dto.Neighborhood;
import com.wenshengamc.zcc.comnfunc.gaodegeo.Address;
import java.util.List;
import lombok.Data;

@Data
public class GaodeRegeoQueryVal {
  @SerializedName("formatted_address")
  String formattedAddress;

  AddressComponent addressComponent;





}

