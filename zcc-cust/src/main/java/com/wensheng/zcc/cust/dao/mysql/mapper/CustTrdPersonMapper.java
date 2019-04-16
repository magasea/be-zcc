package com.wensheng.zcc.cust.dao.mysql.mapper;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPersonExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CustTrdPersonMapper {
    long countByExample(CustTrdPersonExample example);

    int deleteByExample(CustTrdPersonExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CustTrdPerson record);

    int insertSelective(CustTrdPerson record);

    List<CustTrdPerson> selectByExampleWithRowbounds(CustTrdPersonExample example, RowBounds rowBounds);

    List<CustTrdPerson> selectByExample(CustTrdPersonExample example);

    CustTrdPerson selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CustTrdPerson record, @Param("example") CustTrdPersonExample example);

    int updateByExample(@Param("record") CustTrdPerson record, @Param("example") CustTrdPersonExample example);

    int updateByPrimaryKeySelective(CustTrdPerson record);

    int updateByPrimaryKey(CustTrdPerson record);
}