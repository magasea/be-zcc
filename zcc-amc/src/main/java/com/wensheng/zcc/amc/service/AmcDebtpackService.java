package com.wensheng.zcc.amc.service;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtpack;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditor;
import com.wensheng.zcc.amc.module.vo.AmcDebtpackExtVo;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort.Direction;

/**
 * @author chenwei on 1/7/19
 * @project zcc-backend
 */
public interface AmcDebtpackService {

  public AmcDebtpack create(AmcDebtpack amcDebtpack) throws Exception;

  public AmcDebtpack del(AmcDebtpack amcDebtpack);

  public AmcDebtpack update(AmcDebtpack amcDebtpack);


  public List<AmcDebtpack> queryAll(int offset, int size);

  public AmcDebtpack get(Long amcDebtpackId);

  public boolean exist(Long amcDebtpackId);

  public List<AmcDebtpack> query(AmcDebtpack queryCond, int offset, int size);





  public List<AmcOrigCreditor> getCreditorByDebtPackId(Long debtPackId);


  public List<AmcDebtpack> queryPacksWithLocation(String locationName);





  List<AmcDebtpack> queryAllDebtPacks(int offset, int size, Map<String, Direction> orderByParam) throws Exception;

  Long getTotalCnt4Debtpacks();

}
