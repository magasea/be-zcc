package com.wensheng.zcc.cust.dao.mysql.mapper;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcCmpycontactorBak;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcCmpycontactorBakExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CustAmcCmpycontactorBakMapper {
    long countByExample(CustAmcCmpycontactorBakExample example);

    int deleteByExample(CustAmcCmpycontactorBakExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CustAmcCmpycontactorBak record);

    int insertSelective(CustAmcCmpycontactorBak record);

    List<CustAmcCmpycontactorBak> selectByExampleWithRowbounds(CustAmcCmpycontactorBakExample example, RowBounds rowBounds);

    List<CustAmcCmpycontactorBak> selectByExample(CustAmcCmpycontactorBakExample example);

    CustAmcCmpycontactorBak selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CustAmcCmpycontactorBak record, @Param("example") CustAmcCmpycontactorBakExample example);

    int updateByExample(@Param("record") CustAmcCmpycontactorBak record, @Param("example") CustAmcCmpycontactorBakExample example);

    int updateByPrimaryKeySelective(CustAmcCmpycontactorBak record);

    int updateByPrimaryKey(CustAmcCmpycontactorBak record);
}