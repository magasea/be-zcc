package com.wensheng.zcc.amc.service;

import com.wensheng.zcc.amc.module.dao.mongo.entity.DebtImage;
import com.wensheng.zcc.amc.module.dao.mongo.origin.Grntctrct;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCmpy;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCreditor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtContactor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcGrntctrct;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcGrntor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcInfo;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditor;
import com.wensheng.zcc.amc.module.vo.AmcDebtExtVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtVo;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

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

  public AmcDebtExtVo get(Long amcDebtId) throws Exception;

  public List<AmcDebtVo> query(AmcDebt queryCond, int offset, int size);

  List<AmcDebtVo> queryAllExt(Long offset, int size, Map<String, Sort.Direction> orderBy) throws Exception;

  List<AmcDebtVo> queryAllDebt(Long offset, int size, Map<String, Sort.Direction> orderBy) throws Exception;

  List<AmcDebt> queryByDebtpackId(Long debtPackId);

  Long getTotalCount();



  boolean isAmcContactexist(Long amcContact1);

  public AmcDebtor create(AmcDebtor amcDebtor) throws Exception;

  public AmcDebtor update(AmcDebtor debtor);

  public AmcCmpy create(AmcCmpy amcCmpy);

  public AmcCmpy update(AmcCmpy amcCmpy);

  public void connDebt2Debtors(List<Long> creditorIds, Long debtId);

  public void saveDebtDesc(String debtDesc, Long debtId);

  public AmcInfo getAmcInfo(Long amcDebtId) throws Exception;

  public List<AmcDebtor> getDebtors(Long amcDebtId);

  public AmcOrigCreditor getOriginCreditor(Long amcDebtId);

  public Long getCreditorsCount();

  public List<AmcOrigCreditor> getAllOrigCreditors(int offset, int size, Map<String, Direction> orderByParam)
      throws Exception;

  void delImage(DebtImage debtImage);

  AmcOrigCreditor createOrigCreditor(AmcOrigCreditor amcOrigCreditor);

  List<AmcDebtor> getAllUnasignedDebtors(Long offset, int size, int type, Map<String, Direction> orderByParam) throws Exception;

  Long getDebtorCount();

  List<AmcCmpy> getAllCompanies(Long offset, int size, Map<String, Direction> orderByParam) throws Exception;

  Long getTotalCompanyCount();

  Map<String, List<Long>> getAllTitles();

}
