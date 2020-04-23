package com.wensheng.zcc.amc.service;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.ZccDebtpack;
import com.wensheng.zcc.common.params.sso.AmcLocationEnum;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort.Direction;

/**
 * @author chenwei on 1/7/19
 * @project zcc-backend
 */
public interface AmcDebtpackService {

  public ZccDebtpack create(ZccDebtpack zccDebtpack) throws Exception;

  public ZccDebtpack del(ZccDebtpack zccDebtpack);

  public ZccDebtpack update(ZccDebtpack zccDebtpack);


  public List<ZccDebtpack> queryAll(int offset, int size);

  public ZccDebtpack get(Long amcDebtpackId);

  public boolean exist(Long amcDebtpackId);

  public List<ZccDebtpack> query(ZccDebtpack queryCond, int offset, int size);





  public List<AmcOrigCreditor> getCreditorByDebtPackId(Long debtPackId);


  public List<ZccDebtpack> queryPacksWithLocation(AmcLocationEnum locationName);





  List<ZccDebtpack> queryAllDebtPacks(int offset, int size, Map<String, Direction> orderByParam) throws Exception;

  Long getTotalCnt4Debtpacks();

}
