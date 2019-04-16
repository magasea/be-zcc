package com.wensheng.zcc.cust.dao.mysql.mapper;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfoTotal;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfoTotalExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CustTrdInfoTotalMapper {
    long countByExample(CustTrdInfoTotalExample example);

    int deleteByExample(CustTrdInfoTotalExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CustTrdInfoTotal record);

    int insertSelective(CustTrdInfoTotal record);

    List<CustTrdInfoTotal> selectByExampleWithRowbounds(CustTrdInfoTotalExample example, RowBounds rowBounds);

    List<CustTrdInfoTotal> selectByExample(CustTrdInfoTotalExample example);

    CustTrdInfoTotal selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CustTrdInfoTotal record, @Param("example") CustTrdInfoTotalExample example);

    int updateByExample(@Param("record") CustTrdInfoTotal record, @Param("example") CustTrdInfoTotalExample example);

    int updateByPrimaryKeySelective(CustTrdInfoTotal record);

    int updateByPrimaryKey(CustTrdInfoTotal record);
}