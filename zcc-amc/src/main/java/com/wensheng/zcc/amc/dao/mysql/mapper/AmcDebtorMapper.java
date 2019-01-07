package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AmcDebtorMapper {
    long countByExample(AmcDebtorExample example);

    int deleteByExample(AmcDebtorExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmcDebtor record);

    int insertSelective(AmcDebtor record);

    List<AmcDebtor> selectByExampleWithRowbounds(AmcDebtorExample example, RowBounds rowBounds);

    List<AmcDebtor> selectByExample(AmcDebtorExample example);

    AmcDebtor selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmcDebtor record, @Param("example") AmcDebtorExample example);

    int updateByExample(@Param("record") AmcDebtor record, @Param("example") AmcDebtorExample example);

    int updateByPrimaryKeySelective(AmcDebtor record);

    int updateByPrimaryKey(AmcDebtor record);
}