package com.wensheng.zcc.amc.service;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.*;

import com.wensheng.zcc.amc.module.vo.AmcSaleFilter;
import com.wensheng.zcc.amc.module.vo.AmcSaleFloorVo;
import java.util.List;

/**
 * @author chenwei on 1/4/19
 * @project zcc-backend
 */
public interface AmcSaleService {
    public List<AmcSaleFloorVo> getFloors();
    public boolean updateFloor(AmcSaleFloor amcSaleFloor) throws Exception;
    public List<AmcSaleTag> getTags();


    boolean updateFloorFilter(Long floorId, AmcSaleFilter amcSaleFloorFilter, AmcSaleFilter recommItems);

    boolean updateFloorBasic(AmcSaleFloor amcSaleFloor);

    AmcSaleFloorVo createFloor(AmcSaleFloorVo amcSaleFloorVo);
}
