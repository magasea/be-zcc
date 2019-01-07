package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCmpy;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCmpyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AmcCmpyMapper {
    long countByExample(AmcCmpyExample example);

    int deleteByExample(AmcCmpyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmcCmpy record);

    int insertSelective(AmcCmpy record);

    List<AmcCmpy> selectByExampleWithRowbounds(AmcCmpyExample example, RowBounds rowBounds);

    List<AmcCmpy> selectByExample(AmcCmpyExample example);

    AmcCmpy selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmcCmpy record, @Param("example") AmcCmpyExample example);

    int updateByExample(@Param("record") AmcCmpy record, @Param("example") AmcCmpyExample example);

    int updateByPrimaryKeySelective(AmcCmpy record);

    int updateByPrimaryKey(AmcCmpy record);
}