package com.wensheng.zcc.cust.dao.mysql.mapper;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustIntrstInfo;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustIntrstInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CustIntrstInfoMapper {
    long countByExample(CustIntrstInfoExample example);

    int deleteByExample(CustIntrstInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CustIntrstInfo record);

    int insertSelective(CustIntrstInfo record);

    List<CustIntrstInfo> selectByExampleWithRowbounds(CustIntrstInfoExample example, RowBounds rowBounds);

    List<CustIntrstInfo> selectByExample(CustIntrstInfoExample example);

    CustIntrstInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CustIntrstInfo record, @Param("example") CustIntrstInfoExample example);

    int updateByExample(@Param("record") CustIntrstInfo record, @Param("example") CustIntrstInfoExample example);

    int updateByPrimaryKeySelective(CustIntrstInfo record);

    int updateByPrimaryKey(CustIntrstInfo record);
}