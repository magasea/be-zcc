package com.wensheng.zcc.cust.dao.mysql.mapper;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfo;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CustTrdInfoMapper {
    long countByExample(CustTrdInfoExample example);

    int deleteByExample(CustTrdInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CustTrdInfo record);

    int insertSelective(CustTrdInfo record);

    List<CustTrdInfo> selectByExampleWithRowbounds(CustTrdInfoExample example, RowBounds rowBounds);

    List<CustTrdInfo> selectByExample(CustTrdInfoExample example);

    CustTrdInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CustTrdInfo record, @Param("example") CustTrdInfoExample example);

    int updateByExample(@Param("record") CustTrdInfo record, @Param("example") CustTrdInfoExample example);

    int updateByPrimaryKeySelective(CustTrdInfo record);

    int updateByPrimaryKey(CustTrdInfo record);
}