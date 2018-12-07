package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCmpy;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCmpyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AmcCmpyMapper {
    long countByExample(AmcCmpyExample example);

    int deleteByExample(AmcCmpyExample example);

    int insert(AmcCmpy record);

    int insertSelective(AmcCmpy record);

    List<AmcCmpy> selectByExample(AmcCmpyExample example);

    int updateByExampleSelective(@Param("record") AmcCmpy record, @Param("example") AmcCmpyExample example);

    int updateByExample(@Param("record") AmcCmpy record, @Param("example") AmcCmpyExample example);
}