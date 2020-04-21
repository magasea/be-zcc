package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleTagAsset;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleTagAssetExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AmcSaleTagAssetMapper {
    long countByExample(AmcSaleTagAssetExample example);

    int deleteByExample(AmcSaleTagAssetExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmcSaleTagAsset record);

    int insertSelective(AmcSaleTagAsset record);

    List<AmcSaleTagAsset> selectByExampleWithRowbounds(AmcSaleTagAssetExample example, RowBounds rowBounds);

    List<AmcSaleTagAsset> selectByExample(AmcSaleTagAssetExample example);

    AmcSaleTagAsset selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmcSaleTagAsset record, @Param("example") AmcSaleTagAssetExample example);

    int updateByExample(@Param("record") AmcSaleTagAsset record, @Param("example") AmcSaleTagAssetExample example);

    int updateByPrimaryKeySelective(AmcSaleTagAsset record);

    int updateByPrimaryKey(AmcSaleTagAsset record);
}