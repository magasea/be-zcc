package com.wensheng.zcc.amc.service;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtpack;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditor;
import com.wensheng.zcc.amc.module.vo.AmcDebtpackVo;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

/**
 * @author chenwei on 1/7/19
 * @project zcc-backend
 */
public interface AmcDebtpackService {

  public AmcDebtpackVo create(AmcDebtpackVo amcDebtpackVo);

  public AmcDebtpackVo del(AmcDebtpack amcDebtpack);

  public AmcDebtpackVo update(AmcDebtpack amcDebtpack);

  public List<AmcDebtpackVo> queryAll(int offset, int size);

  public AmcDebtpackVo get(Long amcDebtpackId);

  public List<AmcDebtpackVo> query(AmcDebtpack queryCond, int offset, int size);


  public AmcOrigCreditor createCreditor(AmcOrigCreditor amcOrigCreditor);

  public List<AmcOrigCreditor> getAllCreditors(int offset, int pageSize, Map<String, Direction> orderByParam)
      throws Exception;

  public Long getCreditorsCount();


}
