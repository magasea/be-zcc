package com.wensheng.zcc.cust.module.sync;

import lombok.Data;

@Data
public class BidTrdInfoFromSync {
  String trdProvinceCode;
  String auctionTitle;
  Long trdAmount;
  Long trdDate;
  String bidCityCode;
  Long updateTime;
  String buyerName;
  String buyerId;
  Integer bidType;
  String auctionID;
  String bidProvinceCode;
  Integer buyerType;
  String trdContentUrl;

}
