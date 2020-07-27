package com.wensheng.zcc.comnfunc.module.dto.region;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class StatsResult{

	@SerializedName("province")
	private List<Object> province;

	@SerializedName("city")
	private List<Object> city;

	@SerializedName("county")
	private List<Object> county;

	@SerializedName("township")
	private List<Object> township;
}