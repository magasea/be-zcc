package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleTag;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleTagExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AmcSaleTagMapper {
    long countByExample(AmcSaleTagExample example);

    int deleteByExample(AmcSaleTagExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmcSaleTag record);

    int insertSelective(AmcSaleTag record);

    List<AmcSaleTag> selectByExampleWithRowbounds(AmcSaleTagExample example, RowBounds rowBounds);

    List<AmcSaleTag> selectByExample(AmcSaleTagExample example);

    AmcSaleTag selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmcSaleTag record, @Param("example") AmcSaleTagExample example);

    int updateByExample(@Param("record") AmcSaleTag record, @Param("example") AmcSaleTagExample example);

    int updateByPrimaryKeySelective(AmcSaleTag record);

    int updateByPrimaryKey(AmcSaleTag record);
}