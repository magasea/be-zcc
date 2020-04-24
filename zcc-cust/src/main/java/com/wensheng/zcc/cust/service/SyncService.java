package com.wensheng.zcc.cust.service;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.module.sync.CustCmpyInfoFromSync;
import java.text.ParseException;
import java.util.List;

public interface SyncService {

  public String syncWithTrdInfo(List<String> provinces,  String dateString);
  public void syncCustInfo();

  public boolean makeUpDataForMissDateOfTrade() throws ParseException;
  public boolean makeUpDataForProvinceCodeOfTrade() throws ParseException;
  public boolean makeCheckProvinceCodeOfTrade() throws ParseException;
  public boolean makeUpBuyerContactorOfTrade() throws Exception;

  public void updateBuyerCompanyInfoByIds(String id);
  String syncWithTrdInfoSchedule();
  /**
   * 修改表结构cust_trd_info里面把 seller_name 新增进去并且把原先cust_trd_seller 表里面的名字填入
   */
  public void updateSellerNameFromCTS();

  void patchTrdUrl();

  void patchTrdDupAdd();
  public CustCmpyInfoFromSync getCmpyInfoByName(String cmpyName);
  public void copyCmpySync2CmpyInfo(CustCmpyInfoFromSync custCmpyInfoFromSync, CustTrdCmpy custTrdCmpy);

}
