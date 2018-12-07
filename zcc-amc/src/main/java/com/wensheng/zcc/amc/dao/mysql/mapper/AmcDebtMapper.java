package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AmcDebtMapper {
    long countByExample(AmcDebtExample example);

    int deleteByExample(AmcDebtExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmcDebt record);

    int insertSelective(AmcDebt record);

    List<AmcDebt> selectByExample(AmcDebtExample example);

    AmcDebt selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmcDebt record, @Param("example") AmcDebtExample example);

    int updateByExample(@Param("record") AmcDebt record, @Param("example") AmcDebtExample example);

    int updateByPrimaryKeySelective(AmcDebt record);

    int updateByPrimaryKey(AmcDebt record);
}