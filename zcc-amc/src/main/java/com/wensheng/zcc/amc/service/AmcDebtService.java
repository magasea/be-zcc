package com.wensheng.zcc.amc.service;

import com.wensheng.zcc.amc.module.dao.mongo.entity.DebtImage;
import com.wensheng.zcc.amc.module.dao.mongo.origin.Grntctrct;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCmpy;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCreditor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcGrntctrct;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcGrntor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditor;
import com.wensheng.zcc.amc.module.vo.AmcDebtVo;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort;

/**
 * @author chenwei on 1/4/19
 * @project zcc-backend
 */
public interface AmcDebtService {

  public int  saveImageInfo(String ossPath, String originName, Long debtId, String fileDesc, int imageClass);

  public AmcDebtVo create(AmcDebt AmcDebt);

  public AmcDebtVo del(AmcDebt AmcDebt);

  public AmcDebtVo update(AmcDebt AmcDebt);

  public List<AmcDebtVo> queryAll(int offset, int size);

  public AmcDebtVo get(Long amcDebtId) throws Exception;

  public List<AmcDebtVo> query(AmcDebt queryCond, int offset, int size);

  List<AmcDebtVo> queryAllExt(Long offset, int size, Map<String, Sort.Direction> orderBy) throws Exception;

  List<AmcDebt> queryByDebtpackId(Long debtPackId);

  Long getTotalCount();

  Long addGrantContract(AmcGrntctrct amcGrntctrct);

  AmcGrntctrct updateGrantContract(AmcGrntctrct amcGrntctrct);

  Boolean isDebtIdExist(Long debtId);

  Boolean isGrntIdExist(Long grantorId, int grantorType) throws Exception;

  boolean isAmcContactexist(Long amcContact1);

  public AmcCreditor create(AmcCreditor creditor);

  public AmcCreditor update(AmcCreditor creditor);

  public AmcCmpy create(AmcCmpy amcCmpy);

  public AmcCmpy update(AmcCmpy amcCmpy);

  public void connDebt2Creditors(List<Long> creditorIds, Long debtId);

  public List<AmcCreditor> getCreditors(Long amcDebtId) throws Exception;

  public List<AmcGrntor> getGrantors(Long amcDebtId);

  public List<AmcOrigCreditor> getOriginCreditor(Long amcDebtId);

  void delImage(DebtImage debtImage);
}
