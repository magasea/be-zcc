package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleFilter;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleFilterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AmcSaleFilterMapper {
    long countByExample(AmcSaleFilterExample example);

    int deleteByExample(AmcSaleFilterExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmcSaleFilter record);

    int insertSelective(AmcSaleFilter record);

    List<AmcSaleFilter> selectByExampleWithRowbounds(AmcSaleFilterExample example, RowBounds rowBounds);

    List<AmcSaleFilter> selectByExample(AmcSaleFilterExample example);

    AmcSaleFilter selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmcSaleFilter record, @Param("example") AmcSaleFilterExample example);

    int updateByExample(@Param("record") AmcSaleFilter record, @Param("example") AmcSaleFilterExample example);

    int updateByPrimaryKeySelective(AmcSaleFilter record);

    int updateByPrimaryKey(AmcSaleFilter record);
}