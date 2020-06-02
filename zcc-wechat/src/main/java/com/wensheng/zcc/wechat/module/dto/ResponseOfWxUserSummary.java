package com.wensheng.zcc.wechat.module.dto;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ResponseOfWxUserSummary {

	@SerializedName("list")
	private List<UserSummaryItem> list;

}