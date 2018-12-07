package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCmpyShareholder;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCmpyShareholderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AmcCmpyShareholderMapper {
    long countByExample(AmcCmpyShareholderExample example);

    int deleteByExample(AmcCmpyShareholderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmcCmpyShareholder record);

    int insertSelective(AmcCmpyShareholder record);

    List<AmcCmpyShareholder> selectByExample(AmcCmpyShareholderExample example);

    AmcCmpyShareholder selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmcCmpyShareholder record, @Param("example") AmcCmpyShareholderExample example);

    int updateByExample(@Param("record") AmcCmpyShareholder record, @Param("example") AmcCmpyShareholderExample example);

    int updateByPrimaryKeySelective(AmcCmpyShareholder record);

    int updateByPrimaryKey(AmcCmpyShareholder record);
}