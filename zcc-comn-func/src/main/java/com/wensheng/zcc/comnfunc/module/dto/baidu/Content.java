package com.wensheng.zcc.comnfunc.module.dto.baidu;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Content{

	@SerializedName("address_detail")
	private AddressDetail addressDetail;

	@SerializedName("address")
	private String address;

	@SerializedName("point")
	private Point point;

	public AddressDetail getAddressDetail(){
		return addressDetail;
	}

	public String getAddress(){
		return address;
	}

	public Point getPoint(){
		return point;
	}
}