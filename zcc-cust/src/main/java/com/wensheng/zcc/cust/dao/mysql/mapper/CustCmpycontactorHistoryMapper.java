package com.wensheng.zcc.cust.dao.mysql.mapper;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustCmpycontactorHistory;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustCmpycontactorHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CustCmpycontactorHistoryMapper {
    long countByExample(CustCmpycontactorHistoryExample example);

    int deleteByExample(CustCmpycontactorHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CustCmpycontactorHistory record);

    int insertSelective(CustCmpycontactorHistory record);

    List<CustCmpycontactorHistory> selectByExampleWithRowbounds(CustCmpycontactorHistoryExample example, RowBounds rowBounds);

    List<CustCmpycontactorHistory> selectByExample(CustCmpycontactorHistoryExample example);

    CustCmpycontactorHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CustCmpycontactorHistory record, @Param("example") CustCmpycontactorHistoryExample example);

    int updateByExample(@Param("record") CustCmpycontactorHistory record, @Param("example") CustCmpycontactorHistoryExample example);

    int updateByPrimaryKeySelective(CustCmpycontactorHistory record);

    int updateByPrimaryKey(CustCmpycontactorHistory record);
}