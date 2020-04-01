package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAssetPre;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAssetPreExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AmcAssetPreMapper {
    long countByExample(AmcAssetPreExample example);

    int deleteByExample(AmcAssetPreExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmcAssetPre record);

    int insertSelective(AmcAssetPre record);

    List<AmcAssetPre> selectByExampleWithRowbounds(AmcAssetPreExample example, RowBounds rowBounds);

    List<AmcAssetPre> selectByExample(AmcAssetPreExample example);

    AmcAssetPre selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmcAssetPre record, @Param("example") AmcAssetPreExample example);

    int updateByExample(@Param("record") AmcAssetPre record, @Param("example") AmcAssetPreExample example);

    int updateByPrimaryKeySelective(AmcAssetPre record);

    int updateByPrimaryKey(AmcAssetPre record);
}