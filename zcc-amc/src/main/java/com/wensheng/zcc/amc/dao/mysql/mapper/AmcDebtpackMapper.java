package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.ZccDebtpack;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtpackExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AmcDebtpackMapper {
    long countByExample(AmcDebtpackExample example);

    int deleteByExample(AmcDebtpackExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ZccDebtpack record);

    int insertSelective(ZccDebtpack record);

    List<ZccDebtpack> selectByExampleWithRowbounds(AmcDebtpackExample example, RowBounds rowBounds);

    List<ZccDebtpack> selectByExample(AmcDebtpackExample example);

    ZccDebtpack selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ZccDebtpack record, @Param("example") AmcDebtpackExample example);

    int updateByExample(@Param("record") ZccDebtpack record, @Param("example") AmcDebtpackExample example);

    int updateByPrimaryKeySelective(ZccDebtpack record);

    int updateByPrimaryKey(ZccDebtpack record);
}