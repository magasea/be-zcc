package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.ZccDebtpack;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.ZccDebtpackExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ZccDebtpackMapper {
    long countByExample(ZccDebtpackExample example);

    int deleteByExample(ZccDebtpackExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ZccDebtpack record);

    int insertSelective(ZccDebtpack record);

    List<ZccDebtpack> selectByExampleWithRowbounds(ZccDebtpackExample example, RowBounds rowBounds);

    List<ZccDebtpack> selectByExample(ZccDebtpackExample example);

    ZccDebtpack selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ZccDebtpack record, @Param("example") ZccDebtpackExample example);

    int updateByExample(@Param("record") ZccDebtpack record, @Param("example") ZccDebtpackExample example);

    int updateByPrimaryKeySelective(ZccDebtpack record);

    int updateByPrimaryKey(ZccDebtpack record);
}