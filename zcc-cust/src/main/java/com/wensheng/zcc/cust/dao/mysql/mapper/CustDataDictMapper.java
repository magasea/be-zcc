package com.wensheng.zcc.cust.dao.mysql.mapper;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustDataDict;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustDataDictExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CustDataDictMapper {
    long countByExample(CustDataDictExample example);

    int deleteByExample(CustDataDictExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CustDataDict record);

    int insertSelective(CustDataDict record);

    List<CustDataDict> selectByExampleWithRowbounds(CustDataDictExample example, RowBounds rowBounds);

    List<CustDataDict> selectByExample(CustDataDictExample example);

    CustDataDict selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CustDataDict record, @Param("example") CustDataDictExample example);

    int updateByExample(@Param("record") CustDataDict record, @Param("example") CustDataDictExample example);

    int updateByPrimaryKeySelective(CustDataDict record);

    int updateByPrimaryKey(CustDataDict record);
}