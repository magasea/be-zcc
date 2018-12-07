package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtpack;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtpackExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AmcDebtpackMapper {
    long countByExample(AmcDebtpackExample example);

    int deleteByExample(AmcDebtpackExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmcDebtpack record);

    int insertSelective(AmcDebtpack record);

    List<AmcDebtpack> selectByExample(AmcDebtpackExample example);

    AmcDebtpack selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmcDebtpack record, @Param("example") AmcDebtpackExample example);

    int updateByExample(@Param("record") AmcDebtpack record, @Param("example") AmcDebtpackExample example);

    int updateByPrimaryKeySelective(AmcDebtpack record);

    int updateByPrimaryKey(AmcDebtpack record);
}