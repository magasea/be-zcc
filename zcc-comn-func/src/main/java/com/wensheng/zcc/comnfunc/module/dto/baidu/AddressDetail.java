package com.wensheng.zcc.comnfunc.module.dto.baidu;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class AddressDetail{

	@SerializedName("province")
	private String province;

	@SerializedName("city")
	private String city;

	@SerializedName("street")
	private String street;

	@SerializedName("district")
	private String district;

	@SerializedName("street_number")
	private String streetNumber;

	@SerializedName("city_code")
	private String cityCode;


}