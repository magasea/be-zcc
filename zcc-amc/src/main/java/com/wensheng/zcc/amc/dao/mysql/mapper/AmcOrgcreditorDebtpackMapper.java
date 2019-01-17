package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrgcreditorDebtpack;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrgcreditorDebtpackExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AmcOrgcreditorDebtpackMapper {
    long countByExample(AmcOrgcreditorDebtpackExample example);

    int deleteByExample(AmcOrgcreditorDebtpackExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmcOrgcreditorDebtpack record);

    int insertSelective(AmcOrgcreditorDebtpack record);

    List<AmcOrgcreditorDebtpack> selectByExampleWithRowbounds(AmcOrgcreditorDebtpackExample example, RowBounds rowBounds);

    List<AmcOrgcreditorDebtpack> selectByExample(AmcOrgcreditorDebtpackExample example);

    AmcOrgcreditorDebtpack selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmcOrgcreditorDebtpack record, @Param("example") AmcOrgcreditorDebtpackExample example);

    int updateByExample(@Param("record") AmcOrgcreditorDebtpack record, @Param("example") AmcOrgcreditorDebtpackExample example);

    int updateByPrimaryKeySelective(AmcOrgcreditorDebtpack record);

    int updateByPrimaryKey(AmcOrgcreditorDebtpack record);
}