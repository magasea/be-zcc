package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcInfo;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AmcInfoMapper {
    long countByExample(AmcInfoExample example);

    int deleteByExample(AmcInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmcInfo record);

    int insertSelective(AmcInfo record);

    List<AmcInfo> selectByExampleWithRowbounds(AmcInfoExample example, RowBounds rowBounds);

    List<AmcInfo> selectByExample(AmcInfoExample example);

    AmcInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmcInfo record, @Param("example") AmcInfoExample example);

    int updateByExample(@Param("record") AmcInfo record, @Param("example") AmcInfoExample example);

    int updateByPrimaryKeySelective(AmcInfo record);

    int updateByPrimaryKey(AmcInfo record);
}