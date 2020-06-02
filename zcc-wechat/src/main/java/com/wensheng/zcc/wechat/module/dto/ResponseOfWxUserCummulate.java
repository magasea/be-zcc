package com.wensheng.zcc.wechat.module.dto;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

@Data
public class ResponseOfWxUserCummulate {
	@SerializedName("list")
	private List<UserCumulateItem> list;
}