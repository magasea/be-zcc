package com.wensheng.zcc.comnfunc.module.dto.baidu;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Point{

	@SerializedName("x")
	private String X;

	@SerializedName("y")
	private String Y;


}