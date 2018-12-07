package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AmcDebtorMapper {
    long countByExample(AmcDebtorExample example);

    int deleteByExample(AmcDebtorExample example);

    int insert(AmcDebtor record);

    int insertSelective(AmcDebtor record);

    List<AmcDebtor> selectByExample(AmcDebtorExample example);

    int updateByExampleSelective(@Param("record") AmcDebtor record, @Param("example") AmcDebtorExample example);

    int updateByExample(@Param("record") AmcDebtor record, @Param("example") AmcDebtorExample example);
}