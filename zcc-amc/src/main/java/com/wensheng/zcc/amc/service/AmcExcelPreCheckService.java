package com.wensheng.zcc.amc.service;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.*;
import com.wensheng.zcc.common.params.AmcPage;
import com.wensheng.zcc.common.params.sso.SSOAmcUser;
import com.wensheng.zcc.common.utils.sso.SSOQueryParam;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;
import java.util.Map;

/**
 * @author chenwei on 1/9/19
 * @project zcc-backend
 */
public interface AmcExcelPreCheckService {

  public boolean checkDebtTitleExist(String debtTitle);
  List<AmcDebtPre> getAllPreDebts(AmcDebtPreExample amcDebtPreExample);
  List<AmcAssetPre> getAllAssets(AmcAssetPreExample amcAssetPreExample);
  void saveDebt(AmcDebtPre amcDebtPre);
  void saveAsset(AmcAssetPre amcAssetPre);
}
