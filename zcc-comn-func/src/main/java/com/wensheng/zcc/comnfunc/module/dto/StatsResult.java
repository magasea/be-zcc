package com.wensheng.zcc.comnfunc.module.dto;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class StatsResult{

	@SerializedName("province")
	private List<String> province;

	@SerializedName("city")
	private List<String> city;

	@SerializedName("county")
	private List<String> county;

	@SerializedName("township")
	private List<String> township;




}