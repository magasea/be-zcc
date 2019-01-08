package com.wensheng.zcc.amc.service;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.ext.AmcDebtExt;
import com.wensheng.zcc.amc.module.vo.AmcDebtVo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

  public AmcDebtVo get(Long AmcDebtId);

  public List<AmcDebtVo> query(AmcDebt queryCond, int offset, int size);

  List<AmcDebtVo> queryAllExt(int offset, int size, Map<String, Integer> orderBy) throws Exception;

}
