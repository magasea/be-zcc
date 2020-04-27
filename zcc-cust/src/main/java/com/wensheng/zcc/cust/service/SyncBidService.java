package com.wensheng.zcc.cust.service;

import java.text.ParseException;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;

public interface SyncBidService {

  public String syncWithTrdInfo(List<String> provinces, String dateString);
  public void syncCustInfo();



  public void updateBuyerCompanyInfoByIds(String id);
  String syncWithTrdInfoSchedule();
  /**
   * 修改表结构cust_trd_info里面把 seller_name 新增进去并且把原先cust_trd_seller 表里面的名字填入
   */
  public void updateSellerNameFromCTS();

  void patchTrdUrl();

  public void patchTrdPersonRevisePhone();

}
