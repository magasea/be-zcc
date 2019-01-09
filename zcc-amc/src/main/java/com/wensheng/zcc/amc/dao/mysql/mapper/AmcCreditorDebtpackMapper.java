package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCreditorDebtpack;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCreditorDebtpackExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AmcCreditorDebtpackMapper {
    long countByExample(AmcCreditorDebtpackExample example);

    int deleteByExample(AmcCreditorDebtpackExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmcCreditorDebtpack record);

    int insertSelective(AmcCreditorDebtpack record);

    List<AmcCreditorDebtpack> selectByExampleWithRowbounds(AmcCreditorDebtpackExample example, RowBounds rowBounds);

    List<AmcCreditorDebtpack> selectByExample(AmcCreditorDebtpackExample example);

    AmcCreditorDebtpack selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmcCreditorDebtpack record, @Param("example") AmcCreditorDebtpackExample example);

    int updateByExample(@Param("record") AmcCreditorDebtpack record, @Param("example") AmcCreditorDebtpackExample example);

    int updateByPrimaryKeySelective(AmcCreditorDebtpack record);

    int updateByPrimaryKey(AmcCreditorDebtpack record);
}