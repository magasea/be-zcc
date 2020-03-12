package com.wensheng.zcc.cust.dao.mysql.mapper;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcUserpriv;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcUserprivExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CustAmcUserprivMapper {
    long countByExample(CustAmcUserprivExample example);

    int deleteByExample(CustAmcUserprivExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CustAmcUserpriv record);

    int insertSelective(CustAmcUserpriv record);

    List<CustAmcUserpriv> selectByExampleWithRowbounds(CustAmcUserprivExample example, RowBounds rowBounds);

    List<CustAmcUserpriv> selectByExample(CustAmcUserprivExample example);

    CustAmcUserpriv selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CustAmcUserpriv record, @Param("example") CustAmcUserprivExample example);

    int updateByExample(@Param("record") CustAmcUserpriv record, @Param("example") CustAmcUserprivExample example);

    int updateByPrimaryKeySelective(CustAmcUserpriv record);

    int updateByPrimaryKey(CustAmcUserpriv record);
}