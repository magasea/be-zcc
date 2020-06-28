package com.wensheng.zcc.comnfunc.module.dto;

import com.google.gson.annotations.SerializedName;

public class BaiduPoint {

	@SerializedName("x")
	private String X;

	@SerializedName("y")
	private String Y;

	public String getX(){
		return X;
	}

	public String getY(){
		return Y;
	}
}