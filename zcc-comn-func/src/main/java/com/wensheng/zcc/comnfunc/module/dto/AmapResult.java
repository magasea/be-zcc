package com.wensheng.zcc.comnfunc.module.dto;

import java.util.List;
import com.google.gson.annotations.SerializedName;

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

	@SerializedName("ne_type")
	private List<Object> neType;

	@SerializedName("neighborhood")
	private List<Object> neighborhood;

	@SerializedName("township")
	private String township;

	@SerializedName("lat")
	private String lat;

	public List<Object> getCity(){
		return city;
	}

	public String getLevel(){
		return level;
	}

	public String getCounty(){
		return county;
	}

	public String getLon(){
		return lon;
	}

	public String getAmapAddress(){
		return amapAddress;
	}

	public String getProvince(){
		return province;
	}

	public String getTowncode(){
		return towncode;
	}

	public String getAddressBefore(){
		return addressBefore;
	}

	public String getStreetNumber(){
		return streetNumber;
	}

	public List<Object> getNeType(){
		return neType;
	}

	public List<Object> getNeighborhood(){
		return neighborhood;
	}

	public String getTownship(){
		return township;
	}

	public String getLat(){
		return lat;
	}
}