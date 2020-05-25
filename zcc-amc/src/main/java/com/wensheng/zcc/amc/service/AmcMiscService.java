package com.wensheng.zcc.amc.service;

import java.text.ParseException;

public interface AmcMiscService {
  public void updateClickCountInfo();
  void updateHasImgTag();
  void patchAmcDebtAssetContactor();
  void patchPublishDate() throws ParseException;

  void patchContactorSex(String contactorName, Integer sex);
}
