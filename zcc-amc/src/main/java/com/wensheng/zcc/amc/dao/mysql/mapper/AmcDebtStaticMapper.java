package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtStatic;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtStaticExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AmcDebtStaticMapper {
    long countByExample(AmcDebtStaticExample example);

    int deleteByExample(AmcDebtStaticExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmcDebtStatic record);

    int insertSelective(AmcDebtStatic record);

    List<AmcDebtStatic> selectByExampleWithRowbounds(AmcDebtStaticExample example, RowBounds rowBounds);

    List<AmcDebtStatic> selectByExample(AmcDebtStaticExample example);

    AmcDebtStatic selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmcDebtStatic record, @Param("example") AmcDebtStaticExample example);

    int updateByExample(@Param("record") AmcDebtStatic record, @Param("example") AmcDebtStaticExample example);

    int updateByPrimaryKeySelective(AmcDebtStatic record);

    int updateByPrimaryKey(AmcDebtStatic record);
}