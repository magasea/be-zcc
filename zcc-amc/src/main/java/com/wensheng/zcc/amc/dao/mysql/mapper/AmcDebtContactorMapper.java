package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtContactor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtContactorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AmcDebtContactorMapper {
    long countByExample(AmcDebtContactorExample example);

    int deleteByExample(AmcDebtContactorExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmcDebtContactor record);

    int insertSelective(AmcDebtContactor record);

    List<AmcDebtContactor> selectByExampleWithRowbounds(AmcDebtContactorExample example, RowBounds rowBounds);

    List<AmcDebtContactor> selectByExample(AmcDebtContactorExample example);

    AmcDebtContactor selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmcDebtContactor record, @Param("example") AmcDebtContactorExample example);

    int updateByExample(@Param("record") AmcDebtContactor record, @Param("example") AmcDebtContactorExample example);

    int updateByPrimaryKeySelective(AmcDebtContactor record);

    int updateByPrimaryKey(AmcDebtContactor record);
}