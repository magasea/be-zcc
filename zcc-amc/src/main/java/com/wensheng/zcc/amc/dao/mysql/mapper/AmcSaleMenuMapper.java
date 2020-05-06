package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleMenu;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleMenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AmcSaleMenuMapper {
    long countByExample(AmcSaleMenuExample example);

    int deleteByExample(AmcSaleMenuExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmcSaleMenu record);

    int insertSelective(AmcSaleMenu record);

    List<AmcSaleMenu> selectByExampleWithRowbounds(AmcSaleMenuExample example, RowBounds rowBounds);

    List<AmcSaleMenu> selectByExample(AmcSaleMenuExample example);

    AmcSaleMenu selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmcSaleMenu record, @Param("example") AmcSaleMenuExample example);

    int updateByExample(@Param("record") AmcSaleMenu record, @Param("example") AmcSaleMenuExample example);

    int updateByPrimaryKeySelective(AmcSaleMenu record);

    int updateByPrimaryKey(AmcSaleMenu record);
}