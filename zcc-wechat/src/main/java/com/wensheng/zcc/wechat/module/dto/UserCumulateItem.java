package com.wensheng.zcc.wechat.module.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class UserCumulateItem {
	@SerializedName("cumulate_user")
	private int cumulateUser;
	@SerializedName("ref_date")
	private String refDate;

}
