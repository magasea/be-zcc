package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleTagDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleTagDebtExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AmcSaleTagDebtMapper {
    long countByExample(AmcSaleTagDebtExample example);

    int deleteByExample(AmcSaleTagDebtExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmcSaleTagDebt record);

    int insertSelective(AmcSaleTagDebt record);

    List<AmcSaleTagDebt> selectByExampleWithRowbounds(AmcSaleTagDebtExample example, RowBounds rowBounds);

    List<AmcSaleTagDebt> selectByExample(AmcSaleTagDebtExample example);

    AmcSaleTagDebt selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmcSaleTagDebt record, @Param("example") AmcSaleTagDebtExample example);

    int updateByExample(@Param("record") AmcSaleTagDebt record, @Param("example") AmcSaleTagDebtExample example);

    int updateByPrimaryKeySelective(AmcSaleTagDebt record);

    int updateByPrimaryKey(AmcSaleTagDebt record);
}