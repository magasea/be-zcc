package com.wensheng.zcc.cust.service;

import java.text.ParseException;

public interface SyncService {

  public void syncWithTrdInfo();
  public void syncCustInfo();

  public boolean makeUpDataForMissDateOfTrade() throws ParseException;
  public boolean makeUpDataForProvinceCodeOfTrade() throws ParseException;
  public boolean makeCheckProvinceCodeOfTrade() throws ParseException;

  public void updateBuyerCompanyInfoByIds(String id);

  /**
   * 修改表结构cust_trd_info里面把 seller_name 新增进去并且把原先cust_trd_seller 表里面的名字填入
   */
  public void updateSellerNameFromCTS();

  void patchTrdUrl();

}
