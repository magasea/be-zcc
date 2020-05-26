package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAssetStatic;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAssetStaticExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AmcAssetStaticMapper {
    long countByExample(AmcAssetStaticExample example);

    int deleteByExample(AmcAssetStaticExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmcAssetStatic record);

    int insertSelective(AmcAssetStatic record);

    List<AmcAssetStatic> selectByExampleWithRowbounds(AmcAssetStaticExample example, RowBounds rowBounds);

    List<AmcAssetStatic> selectByExample(AmcAssetStaticExample example);

    AmcAssetStatic selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmcAssetStatic record, @Param("example") AmcAssetStaticExample example);

    int updateByExample(@Param("record") AmcAssetStatic record, @Param("example") AmcAssetStaticExample example);

    int updateByPrimaryKeySelective(AmcAssetStatic record);

    int updateByPrimaryKey(AmcAssetStatic record);
}