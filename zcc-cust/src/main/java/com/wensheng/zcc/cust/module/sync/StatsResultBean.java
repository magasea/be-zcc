package com.wensheng.zcc.cust.module.sync;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

@Data
public class StatsResultBean {

  @SerializedName("province")
  private List<String> province;
  @SerializedName("city")
  private List<String> city;
  @SerializedName("county")
  private List<String> county;
  @SerializedName("township")
  private List<String> township;

}
