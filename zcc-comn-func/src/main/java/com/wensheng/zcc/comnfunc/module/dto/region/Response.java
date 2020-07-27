package com.wensheng.zcc.comnfunc.module.dto.region;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Response{

	@SerializedName("result")
	private Result result;

	@SerializedName("status")
	private String status;

	@SerializedName("info")
	private String info;
}