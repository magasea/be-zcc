package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleFloor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleFloorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AmcSaleFloorMapper {
    long countByExample(AmcSaleFloorExample example);

    int deleteByExample(AmcSaleFloorExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmcSaleFloor record);

    int insertSelective(AmcSaleFloor record);

    List<AmcSaleFloor> selectByExampleWithRowbounds(AmcSaleFloorExample example, RowBounds rowBounds);

    List<AmcSaleFloor> selectByExample(AmcSaleFloorExample example);

    AmcSaleFloor selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmcSaleFloor record, @Param("example") AmcSaleFloorExample example);

    int updateByExample(@Param("record") AmcSaleFloor record, @Param("example") AmcSaleFloorExample example);

    int updateByPrimaryKeySelective(AmcSaleFloor record);

    int updateByPrimaryKey(AmcSaleFloor record);
}