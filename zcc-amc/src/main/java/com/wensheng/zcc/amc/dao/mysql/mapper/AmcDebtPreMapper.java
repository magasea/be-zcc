package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtPre;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtPreExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AmcDebtPreMapper {
    long countByExample(AmcDebtPreExample example);

    int deleteByExample(AmcDebtPreExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmcDebtPre record);

    int insertSelective(AmcDebtPre record);

    List<AmcDebtPre> selectByExampleWithRowbounds(AmcDebtPreExample example, RowBounds rowBounds);

    List<AmcDebtPre> selectByExample(AmcDebtPreExample example);

    AmcDebtPre selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmcDebtPre record, @Param("example") AmcDebtPreExample example);

    int updateByExample(@Param("record") AmcDebtPre record, @Param("example") AmcDebtPreExample example);

    int updateByPrimaryKeySelective(AmcDebtPre record);

    int updateByPrimaryKey(AmcDebtPre record);
}