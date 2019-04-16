package com.wensheng.zcc.cust.dao.mysql.mapper;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustWechatInfo;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustWechatInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CustWechatInfoMapper {
    long countByExample(CustWechatInfoExample example);

    int deleteByExample(CustWechatInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CustWechatInfo record);

    int insertSelective(CustWechatInfo record);

    List<CustWechatInfo> selectByExampleWithRowbounds(CustWechatInfoExample example, RowBounds rowBounds);

    List<CustWechatInfo> selectByExample(CustWechatInfoExample example);

    int updateByExampleSelective(@Param("record") CustWechatInfo record, @Param("example") CustWechatInfoExample example);

    int updateByExample(@Param("record") CustWechatInfo record, @Param("example") CustWechatInfoExample example);
}