package com.wensheng.zcc.cust.module.sync;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ResultBean {

  /**
   * input_address : 河南省濮阳市南乐县元村镇留胄村
   * amap_result : {"province":"河南省","city":"濮阳市","county":"南乐县","township":"元村镇","towncode":"410923102000","neighborhood":[],"ne_type":[],"street_number":[],"amap_address":"河南省濮阳市南乐县元村镇留胄村","level":"村庄","lat":"36.087555","lon":"115.076538","address_before":"河南省濮阳市南乐县留胄村"}
   * baidu_result : {"province":"河南省","city":"濮阳市","district":"南乐县","township":"元村镇","towncode":410923102000,"baidu_address":"河南省濮阳市南乐县"}
   * stats_result : {"province":[410000000000,"河南省"],"city":[410900000000,"濮阳市"],"county":[410923000000,"南乐县"],"township":[410923102000,"元村镇"]}
   */

  @SerializedName("input_address")
  private String inputAddress;
//  @SerializedName("amap_result")
//  private AmapResultBean amapResult;
//  @SerializedName("baidu_result")
//  private BaiduResultBean baiduResult;
  @SerializedName("stats_result")
  private StatsResultBean statsResult;

}
