package com.wensheng.zcc.cust.service;

import java.text.ParseException;
import java.util.List;

public interface SyncBidService {

  public String syncWithTrdInfo(List<String> provinces);
  public void syncCustInfo();



  public void updateBuyerCompanyInfoByIds(String id);
  String syncWithTrdInfoSchedule();
  /**
   * 修改表结构cust_trd_info里面把 seller_name 新增进去并且把原先cust_trd_seller 表里面的名字填入
   */
  public void updateSellerNameFromCTS();

  void patchTrdUrl();

}
