package com.wensheng.zcc.comnfunc.module.dto;

import com.google.gson.annotations.SerializedName;

public class BaiduContent {

	@SerializedName("address_detail")
	private BaiduAddressDetail addressDetail;

	@SerializedName("address")
	private String address;

	@SerializedName("point")
	private BaiduPoint point;

	public BaiduAddressDetail getAddressDetail(){
		return addressDetail;
	}

	public String getAddress(){
		return address;
	}

	public BaiduPoint getPoint(){
		return point;
	}
}