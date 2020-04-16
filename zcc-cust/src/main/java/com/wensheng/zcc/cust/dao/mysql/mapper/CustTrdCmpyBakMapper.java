package com.wensheng.zcc.cust.dao.mysql.mapper;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpyBak;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpyBakExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CustTrdCmpyBakMapper {
    long countByExample(CustTrdCmpyBakExample example);

    int deleteByExample(CustTrdCmpyBakExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CustTrdCmpyBak record);

    int insertSelective(CustTrdCmpyBak record);

    List<CustTrdCmpyBak> selectByExampleWithRowbounds(CustTrdCmpyBakExample example, RowBounds rowBounds);

    List<CustTrdCmpyBak> selectByExample(CustTrdCmpyBakExample example);

    CustTrdCmpyBak selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CustTrdCmpyBak record, @Param("example") CustTrdCmpyBakExample example);

    int updateByExample(@Param("record") CustTrdCmpyBak record, @Param("example") CustTrdCmpyBakExample example);

    int updateByPrimaryKeySelective(CustTrdCmpyBak record);

    int updateByPrimaryKey(CustTrdCmpyBak record);
}