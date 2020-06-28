package com.wensheng.zcc.comnfunc.module.dto;

import com.google.gson.annotations.SerializedName;
import com.wensheng.zcc.comnfunc.module.dto.baidu.Content;

public class BaiduResponse {

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