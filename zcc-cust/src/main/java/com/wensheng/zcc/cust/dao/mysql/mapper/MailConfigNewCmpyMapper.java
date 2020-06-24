package com.wensheng.zcc.cust.dao.mysql.mapper;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.MailConfigNewCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.MailConfigNewCmpyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MailConfigNewCmpyMapper {
    long countByExample(MailConfigNewCmpyExample example);

    int deleteByExample(MailConfigNewCmpyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MailConfigNewCmpy record);

    int insertSelective(MailConfigNewCmpy record);

    List<MailConfigNewCmpy> selectByExampleWithRowbounds(MailConfigNewCmpyExample example, RowBounds rowBounds);

    List<MailConfigNewCmpy> selectByExample(MailConfigNewCmpyExample example);

    MailConfigNewCmpy selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MailConfigNewCmpy record, @Param("example") MailConfigNewCmpyExample example);

    int updateByExample(@Param("record") MailConfigNewCmpy record, @Param("example") MailConfigNewCmpyExample example);

    int updateByPrimaryKeySelective(MailConfigNewCmpy record);

    int updateByPrimaryKey(MailConfigNewCmpy record);
}