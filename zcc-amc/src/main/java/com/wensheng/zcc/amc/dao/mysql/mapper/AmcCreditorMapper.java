package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCreditor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCreditorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AmcCreditorMapper {
    long countByExample(AmcCreditorExample example);

    int deleteByExample(AmcCreditorExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmcCreditor record);

    int insertSelective(AmcCreditor record);

    List<AmcCreditor> selectByExampleWithRowbounds(AmcCreditorExample example, RowBounds rowBounds);

    List<AmcCreditor> selectByExample(AmcCreditorExample example);

    AmcCreditor selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmcCreditor record, @Param("example") AmcCreditorExample example);

    int updateByExample(@Param("record") AmcCreditor record, @Param("example") AmcCreditorExample example);

    int updateByPrimaryKeySelective(AmcCreditor record);

    int updateByPrimaryKey(AmcCreditor record);
}