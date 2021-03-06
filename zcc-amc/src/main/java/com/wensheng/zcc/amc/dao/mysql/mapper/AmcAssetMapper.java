package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAsset;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAssetExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AmcAssetMapper {
    long countByExample(AmcAssetExample example);

    int deleteByExample(AmcAssetExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmcAsset record);

    int insertSelective(AmcAsset record);

    List<AmcAsset> selectByExampleWithRowbounds(AmcAssetExample example, RowBounds rowBounds);

    List<AmcAsset> selectByExample(AmcAssetExample example);

    AmcAsset selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmcAsset record, @Param("example") AmcAssetExample example);

    int updateByExample(@Param("record") AmcAsset record, @Param("example") AmcAssetExample example);

    int updateByPrimaryKeySelective(AmcAsset record);

    int updateByPrimaryKey(AmcAsset record);
}