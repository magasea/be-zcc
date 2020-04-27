package com.wensheng.zcc.amc.service;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.*;

import com.wensheng.zcc.amc.module.vo.AmcSaleFilter;
import com.wensheng.zcc.amc.module.vo.AmcSaleFloorFrontEndVo;
import com.wensheng.zcc.amc.module.vo.AmcSaleFloorVo;
import java.util.List;

/**
 * @author chenwei on 1/4/19
 * @project zcc-backend
 */
public interface AmcSaleService {
    public List<AmcSaleFloorVo> getFloors();
    public List<AmcSaleFloorFrontEndVo> getFrontEndFloors();
    public boolean updateFloor(AmcSaleFloorVo amcSaleFloorVo) throws Exception;
    public boolean updateFloorSeq(List<Long> floorIds) throws Exception;
    public void delFloor(Long floorId) throws Exception;
    public List<AmcSaleTag> getTags();


    boolean updateFloorAllFilter(Long floorId, AmcSaleFilter amcSaleFloorFilter, AmcSaleFilter recommItems);

    boolean updateFloorFilterRecom(Long floorId, AmcSaleFilter recommItems);

    boolean updateFloorFilter(Long floorId, AmcSaleFilter amcSaleFilter);

    boolean updateFloorBasic(AmcSaleFloor amcSaleFloor);

    AmcSaleFloorVo createFloor(AmcSaleFloorVo amcSaleFloorVo);


}
