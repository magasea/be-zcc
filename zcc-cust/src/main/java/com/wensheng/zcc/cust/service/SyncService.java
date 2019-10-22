package com.wensheng.zcc.cust.service;

import java.text.ParseException;

public interface SyncService {

  public void syncWithTrdInfo();
  public void syncCustInfo();

  public boolean makeUpDataForMissDateOfTrade() throws ParseException;
  public boolean makeUpDataForProvinceCodeOfTrade() throws ParseException;
  public boolean makeCheckProvinceCodeOfTrade() throws ParseException;

  public void updateBuyerCompanyInfoByIds(String id);
}
