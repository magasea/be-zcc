package com.wensheng.zcc.comnfunc.module.dto;

import com.google.gson.annotations.SerializedName;

public class Result{

	@SerializedName("input_address")
	private String inputAddress;

	@SerializedName("stats_result")
	private StatsResult statsResult;

	@SerializedName("baidu_result")
	private BaiduResult baiduResult;

	@SerializedName("amap_result")
	private AmapResult amapResult;

	public String getInputAddress(){
		return inputAddress;
	}

	public StatsResult getStatsResult(){
		return statsResult;
	}

	public BaiduResult getBaiduResult(){
		return baiduResult;
	}

	public AmapResult getAmapResult(){
		return amapResult;
	}
}