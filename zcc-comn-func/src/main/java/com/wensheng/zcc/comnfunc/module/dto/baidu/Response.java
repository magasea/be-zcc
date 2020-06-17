package com.wensheng.zcc.comnfunc.module.dto.baidu;

import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("address")
	private String address;

	@SerializedName("content")
	private Content content;

	@SerializedName("status")
	private int status;

	public String getAddress(){
		return address;
	}

	public Content getContent(){
		return content;
	}

	public int getStatus(){
		return status;
	}
}