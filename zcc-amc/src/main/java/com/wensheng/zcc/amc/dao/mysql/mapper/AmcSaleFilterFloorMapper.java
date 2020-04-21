package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleFilterFloor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleFilterFloorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AmcSaleFilterFloorMapper {
    long countByExample(AmcSaleFilterFloorExample example);

    int deleteByExample(AmcSaleFilterFloorExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmcSaleFilterFloor record);

    int insertSelective(AmcSaleFilterFloor record);

    List<AmcSaleFilterFloor> selectByExampleWithRowbounds(AmcSaleFilterFloorExample example, RowBounds rowBounds);

    List<AmcSaleFilterFloor> selectByExample(AmcSaleFilterFloorExample example);

    AmcSaleFilterFloor selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmcSaleFilterFloor record, @Param("example") AmcSaleFilterFloorExample example);

    int updateByExample(@Param("record") AmcSaleFilterFloor record, @Param("example") AmcSaleFilterFloorExample example);

    int updateByPrimaryKeySelective(AmcSaleFilterFloor record);

    int updateByPrimaryKey(AmcSaleFilterFloor record);
}