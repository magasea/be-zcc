package com.wensheng.zcc.cust.dao.mysql.mapper;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpyHistory;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpyHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CustTrdCmpyHistoryMapper {
    long countByExample(CustTrdCmpyHistoryExample example);

    int deleteByExample(CustTrdCmpyHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CustTrdCmpyHistory record);

    int insertSelective(CustTrdCmpyHistory record);

    List<CustTrdCmpyHistory> selectByExampleWithRowbounds(CustTrdCmpyHistoryExample example, RowBounds rowBounds);

    List<CustTrdCmpyHistory> selectByExample(CustTrdCmpyHistoryExample example);

    CustTrdCmpyHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CustTrdCmpyHistory record, @Param("example") CustTrdCmpyHistoryExample example);

    int updateByExample(@Param("record") CustTrdCmpyHistory record, @Param("example") CustTrdCmpyHistoryExample example);

    int updateByPrimaryKeySelective(CustTrdCmpyHistory record);

    int updateByPrimaryKey(CustTrdCmpyHistory record);
}