package com.wensheng.zcc.comnfunc.module.dto.region;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Result{

	@SerializedName("input_address")
	private String inputAddress;

	@SerializedName("stats_result")
	private StatsResult statsResult;

	@SerializedName("baidu_result")
	private BaiduResult baiduResult;

	@SerializedName("amap_result")
	private AmapResult amapResult;
}