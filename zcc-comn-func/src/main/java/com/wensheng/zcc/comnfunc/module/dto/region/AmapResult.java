package com.wensheng.zcc.comnfunc.module.dto.region;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class AmapResult{

	@SerializedName("city")
	private List<Object> city;

	@SerializedName("level")
	private String level;

	@SerializedName("county")
	private String county;

	@SerializedName("lon")
	private String lon;

	@SerializedName("amap_address")
	private String amapAddress;

	@SerializedName("province")
	private String province;

	@SerializedName("towncode")
	private String towncode;

	@SerializedName("address_before")
	private String addressBefore;

	@SerializedName("street_number")
	private String streetNumber;

//	@SerializedName("ne_type")
//	private String neType;
//
//	@SerializedName("neighborhood")
//	private String neighborhood;

	@SerializedName("township")
	private String township;

	@SerializedName("lat")
	private String lat;
}