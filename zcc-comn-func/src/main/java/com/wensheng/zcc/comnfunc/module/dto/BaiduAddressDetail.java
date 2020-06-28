package com.wensheng.zcc.comnfunc.module.dto;

import com.google.gson.annotations.SerializedName;

public class BaiduAddressDetail {

	@SerializedName("province")
	private String province;

	@SerializedName("city")
	private String city;

	@SerializedName("city_code")
	private int cityCode;

	public String getProvince(){
		return province;
	}

	public String getCity(){
		return city;
	}

	public int getCityCode(){
		return cityCode;
	}
}