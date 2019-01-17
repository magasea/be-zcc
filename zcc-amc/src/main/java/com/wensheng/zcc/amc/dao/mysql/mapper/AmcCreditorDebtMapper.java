package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCreditorDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCreditorDebtExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AmcCreditorDebtMapper {
    long countByExample(AmcCreditorDebtExample example);

    int deleteByExample(AmcCreditorDebtExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmcCreditorDebt record);

    int insertSelective(AmcCreditorDebt record);

    List<AmcCreditorDebt> selectByExampleWithRowbounds(AmcCreditorDebtExample example, RowBounds rowBounds);

    List<AmcCreditorDebt> selectByExample(AmcCreditorDebtExample example);

    AmcCreditorDebt selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmcCreditorDebt record, @Param("example") AmcCreditorDebtExample example);

    int updateByExample(@Param("record") AmcCreditorDebt record, @Param("example") AmcCreditorDebtExample example);

    int updateByPrimaryKeySelective(AmcCreditorDebt record);

    int updateByPrimaryKey(AmcCreditorDebt record);
}