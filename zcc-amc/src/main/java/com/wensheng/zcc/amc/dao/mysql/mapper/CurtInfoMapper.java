package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.CurtInfo;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.CurtInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CurtInfoMapper {
    long countByExample(CurtInfoExample example);

    int deleteByExample(CurtInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CurtInfo record);

    int insertSelective(CurtInfo record);

    List<CurtInfo> selectByExampleWithRowbounds(CurtInfoExample example, RowBounds rowBounds);

    List<CurtInfo> selectByExample(CurtInfoExample example);

    CurtInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CurtInfo record, @Param("example") CurtInfoExample example);

    int updateByExample(@Param("record") CurtInfo record, @Param("example") CurtInfoExample example);

    int updateByPrimaryKeySelective(CurtInfo record);

    int updateByPrimaryKey(CurtInfo record);
}