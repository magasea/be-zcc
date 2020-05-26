package com.wensheng.zcc.comnfunc.module.dto;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class StatsResult{

	@SerializedName("province")
	private List<String> province;

	@SerializedName("city")
	private List<String> city;

	@SerializedName("county")
	private List<String> county;

	@SerializedName("township")
	private List<String> township;

	public List<String> getProvince(){
		return province;
	}

	public List<String> getCity(){
		return city;
	}

	public List<String> getCounty(){
		return county;
	}

	public List<String> getTownship(){
		return township;
	}
}