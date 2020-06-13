package com.wensheng.zcc.comnfunc.module.dto;

import com.google.gson.annotations.SerializedName;

public class BaiduResponse {

	@SerializedName("address")
	private String address;

	@SerializedName("content")
	private BaiduContent content;

	@SerializedName("status")
	private int status;

	public String getAddress(){
		return address;
	}

	public BaiduContent getContent(){
		return content;
	}

	public int getStatus(){
		return status;
	}
}