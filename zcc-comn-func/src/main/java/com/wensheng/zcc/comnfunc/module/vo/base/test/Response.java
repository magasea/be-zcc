package com.wensheng.zcc.comnfunc.module.vo.base.test;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("count")
	private String count;

	@SerializedName("infocode")
	private String infocode;

	@SerializedName("geocodes")
	private List<GeocodesItem> geocodes;

	@SerializedName("status")
	private String status;

	@SerializedName("info")
	private String info;
}