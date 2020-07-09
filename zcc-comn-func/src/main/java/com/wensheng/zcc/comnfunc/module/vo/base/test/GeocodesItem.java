package com.wensheng.zcc.comnfunc.module.vo.base.test;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GeocodesItem{

	@SerializedName("country")
	private String country;

	@SerializedName("formatted_address")
	private String formattedAddress;

	@SerializedName("city")
	private List<Object> city;

	@SerializedName("adcode")
	private String adcode;

	@SerializedName("level")
	private String level;

	@SerializedName("building")
	private Building building;

	@SerializedName("number")
	private List<Object> number;

	@SerializedName("province")
	private String province;

	@SerializedName("street")
	private List<Object> street;

	@SerializedName("district")
	private List<Object> district;

	@SerializedName("location")
	private String location;

	@SerializedName("neighborhood")
	private Neighborhood neighborhood;

	@SerializedName("township")
	private List<Object> township;
}