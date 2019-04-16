package com.wensheng.zcc.cust.dao.mysql.mapper;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CustTrdCmpyMapper {
    long countByExample(CustTrdCmpyExample example);

    int deleteByExample(CustTrdCmpyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CustTrdCmpy record);

    int insertSelective(CustTrdCmpy record);

    List<CustTrdCmpy> selectByExampleWithRowbounds(CustTrdCmpyExample example, RowBounds rowBounds);

    List<CustTrdCmpy> selectByExample(CustTrdCmpyExample example);

    CustTrdCmpy selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CustTrdCmpy record, @Param("example") CustTrdCmpyExample example);

    int updateByExample(@Param("record") CustTrdCmpy record, @Param("example") CustTrdCmpyExample example);

    int updateByPrimaryKeySelective(CustTrdCmpy record);

    int updateByPrimaryKey(CustTrdCmpy record);
}