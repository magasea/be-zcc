package com.wensheng.zcc.cust.dao.mysql.mapper;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPersonHistory;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPersonHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CustTrdPersonHistoryMapper {
    long countByExample(CustTrdPersonHistoryExample example);

    int deleteByExample(CustTrdPersonHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CustTrdPersonHistory record);

    int insertSelective(CustTrdPersonHistory record);

    List<CustTrdPersonHistory> selectByExampleWithRowbounds(CustTrdPersonHistoryExample example, RowBounds rowBounds);

    List<CustTrdPersonHistory> selectByExample(CustTrdPersonHistoryExample example);

    CustTrdPersonHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CustTrdPersonHistory record, @Param("example") CustTrdPersonHistoryExample example);

    int updateByExample(@Param("record") CustTrdPersonHistory record, @Param("example") CustTrdPersonHistoryExample example);

    int updateByPrimaryKeySelective(CustTrdPersonHistory record);

    int updateByPrimaryKey(CustTrdPersonHistory record);
}