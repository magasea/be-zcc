package com.wensheng.zcc.cust.dao.mysql.mapper;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcCmpycontactor;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcCmpycontactorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CustAmcCmpycontactorMapper {
    long countByExample(CustAmcCmpycontactorExample example);

    int deleteByExample(CustAmcCmpycontactorExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CustAmcCmpycontactor record);

    int insertSelective(CustAmcCmpycontactor record);

    List<CustAmcCmpycontactor> selectByExampleWithRowbounds(CustAmcCmpycontactorExample example, RowBounds rowBounds);

    List<CustAmcCmpycontactor> selectByExample(CustAmcCmpycontactorExample example);

    CustAmcCmpycontactor selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CustAmcCmpycontactor record, @Param("example") CustAmcCmpycontactorExample example);

    int updateByExample(@Param("record") CustAmcCmpycontactor record, @Param("example") CustAmcCmpycontactorExample example);

    int updateByPrimaryKeySelective(CustAmcCmpycontactor record);

    int updateByPrimaryKey(CustAmcCmpycontactor record);
}