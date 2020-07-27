package com.wensheng.zcc.comnfunc.module.dto.region;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
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
}