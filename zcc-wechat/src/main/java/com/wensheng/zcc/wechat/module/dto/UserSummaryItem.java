package com.wensheng.zcc.wechat.module.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class UserSummaryItem {

	@SerializedName("new_user")
	private int newUser;

	@SerializedName("cancel_user")
	private int cancelUser;

	@SerializedName("user_source")
	private int userSource;

	@SerializedName("ref_date")
	private String refDate;

}