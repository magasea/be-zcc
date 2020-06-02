package com.wensheng.zcc.amc.service;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtContactor;
import com.wensheng.zcc.common.params.AmcPage;
import com.wensheng.zcc.common.params.sso.SSOAmcUser;
import com.wensheng.zcc.common.utils.sso.SSOQueryParam;
import java.util.List;

public interface AmcContactorService {



  AmcDebtContactor uploadContactorImage(String imagePath, String ossPrepath,
      Long amcDebtContactorId, String imageClassName) throws Exception;

  void syncContactorWithSSO();

  void syncContactorWithNewUser(SSOAmcUser amcUser);

  AmcPage<SSOAmcUser> getSsoAmcUsers(SSOQueryParam ssoQueryParam);

  void initializeDebtContactor();

  List<AmcDebtContactor> getAllDebtContactor();

  List<AmcDebtContactor> getDebtContactors();

  AmcDebtContactor addContactor(AmcDebtContactor amcDebtContactor) throws Exception;
  AmcDebtContactor updateContactor(AmcDebtContactor amcDebtContactor);
  boolean deleteContactor(Long contactId) throws Exception;
  boolean changeContactor(Long originContactorId, Long newContactorId) throws Exception;

  String getDebtContactorOssPrePath(String imgClass, Long amcDebtContactorId);
}
