package com.wensheng.zcc.comnfunc.module.dto;

import com.google.gson.annotations.SerializedName;

public class Response{

  @SerializedName("result")
  private Result result;

  @SerializedName("status")
  private String status;

  @SerializedName("info")
  private String info;

  public Result getResult(){
    return result;
  }

  public String getStatus(){
    return status;
  }

  public String getInfo(){
    return info;
  }
}
