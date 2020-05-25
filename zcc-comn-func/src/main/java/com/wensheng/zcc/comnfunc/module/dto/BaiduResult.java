package com.wensheng.zcc.comnfunc.module.dto;

import com.google.gson.annotations.SerializedName;

public class BaiduResult{

	@SerializedName("baidu_address")
	private String baiduAddress;

	@SerializedName("province")
	private String province;

	@SerializedName("city")
	private String city;

	@SerializedName("towncode")
	private long towncode;

	@SerializedName("district")
	private String district;

	@SerializedName("township")
	private String township;

	public String getBaiduAddress(){
		return baiduAddress;
	}

	public String getProvince(){
		return province;
	}

	public String getCity(){
		return city;
	}

	public long getTowncode(){
		return towncode;
	}

	public String getDistrict(){
		return district;
	}

	public String getTownship(){
		return township;
	}
}