package com.wensheng.zcc.amc.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface AmcMiscService {
  public void updateClickCountInfo();
  void updateHasImgTag();
  void patchAmcDebtAssetContactor();
  void patchPublishDate() throws ParseException;

  void patchContactorSex(String contactorName, Integer sex);
  Map<String, Integer> getDebtAssetStatus(List<String> debtAssetList);
}
