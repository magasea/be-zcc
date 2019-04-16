package com.wensheng.zcc.cust.dao.mysql.mapper;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPersonCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPersonCmpyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CustTrdPersonCmpyMapper {
    long countByExample(CustTrdPersonCmpyExample example);

    int deleteByExample(CustTrdPersonCmpyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CustTrdPersonCmpy record);

    int insertSelective(CustTrdPersonCmpy record);

    List<CustTrdPersonCmpy> selectByExampleWithRowbounds(CustTrdPersonCmpyExample example, RowBounds rowBounds);

    List<CustTrdPersonCmpy> selectByExample(CustTrdPersonCmpyExample example);

    CustTrdPersonCmpy selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CustTrdPersonCmpy record, @Param("example") CustTrdPersonCmpyExample example);

    int updateByExample(@Param("record") CustTrdPersonCmpy record, @Param("example") CustTrdPersonCmpyExample example);

    int updateByPrimaryKeySelective(CustTrdPersonCmpy record);

    int updateByPrimaryKey(CustTrdPersonCmpy record);
}