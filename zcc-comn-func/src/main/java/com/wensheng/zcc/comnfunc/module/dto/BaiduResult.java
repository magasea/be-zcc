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

	public void setBaiduAddress(String baiduAddress){
		this.baiduAddress = baiduAddress;
	}

	public String getBaiduAddress(){
		return baiduAddress;
	}

	public void setProvince(String province){
		this.province = province;
	}

	public String getProvince(){
		return province;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setTowncode(long towncode){
		this.towncode = towncode;
	}

	public long getTowncode(){
		return towncode;
	}

	public void setDistrict(String district){
		this.district = district;
	}

	public String getDistrict(){
		return district;
	}

	public void setTownship(String township){
		this.township = township;
	}

	public String getTownship(){
		return township;
	}
}