package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AmcOrigCreditorMapper {
    long countByExample(AmcOrigCreditorExample example);

    int deleteByExample(AmcOrigCreditorExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmcOrigCreditor record);

    int insertSelective(AmcOrigCreditor record);

    List<AmcOrigCreditor> selectByExampleWithRowbounds(AmcOrigCreditorExample example, RowBounds rowBounds);

    List<AmcOrigCreditor> selectByExample(AmcOrigCreditorExample example);

    AmcOrigCreditor selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmcOrigCreditor record, @Param("example") AmcOrigCreditorExample example);

    int updateByExample(@Param("record") AmcOrigCreditor record, @Param("example") AmcOrigCreditorExample example);

    int updateByPrimaryKeySelective(AmcOrigCreditor record);

    int updateByPrimaryKey(AmcOrigCreditor record);
}