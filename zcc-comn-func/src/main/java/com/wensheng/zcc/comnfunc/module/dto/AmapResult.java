package com.wensheng.zcc.comnfunc.module.dto;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AmapResult{

	@SerializedName("city")
	private String city;

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

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setLevel(String level){
		this.level = level;
	}

	public String getLevel(){
		return level;
	}

	public void setCounty(String county){
		this.county = county;
	}

	public String getCounty(){
		return county;
	}

	public void setLon(String lon){
		this.lon = lon;
	}

	public String getLon(){
		return lon;
	}

	public void setAmapAddress(String amapAddress){
		this.amapAddress = amapAddress;
	}

	public String getAmapAddress(){
		return amapAddress;
	}

	public void setProvince(String province){
		this.province = province;
	}

	public String getProvince(){
		return province;
	}

	public void setTowncode(String towncode){
		this.towncode = towncode;
	}

	public String getTowncode(){
		return towncode;
	}

	public void setAddressBefore(String addressBefore){
		this.addressBefore = addressBefore;
	}

	public String getAddressBefore(){
		return addressBefore;
	}

	public void setStreetNumber(String streetNumber){
		this.streetNumber = streetNumber;
	}

	public String getStreetNumber(){
		return streetNumber;
	}

	public void setNeType(List<Object> neType){
		this.neType = neType;
	}

	public List<Object> getNeType(){
		return neType;
	}

	public void setNeighborhood(List<Object> neighborhood){
		this.neighborhood = neighborhood;
	}

	public List<Object> getNeighborhood(){
		return neighborhood;
	}

	public void setTownship(String township){
		this.township = township;
	}

	public String getTownship(){
		return township;
	}

	public void setLat(String lat){
		this.lat = lat;
	}

	public String getLat(){
		return lat;
	}
}