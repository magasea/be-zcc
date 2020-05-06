package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleBanner;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleBannerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AmcSaleBannerMapper {
    long countByExample(AmcSaleBannerExample example);

    int deleteByExample(AmcSaleBannerExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmcSaleBanner record);

    int insertSelective(AmcSaleBanner record);

    List<AmcSaleBanner> selectByExampleWithRowbounds(AmcSaleBannerExample example, RowBounds rowBounds);

    List<AmcSaleBanner> selectByExample(AmcSaleBannerExample example);

    AmcSaleBanner selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmcSaleBanner record, @Param("example") AmcSaleBannerExample example);

    int updateByExample(@Param("record") AmcSaleBanner record, @Param("example") AmcSaleBannerExample example);

    int updateByPrimaryKeySelective(AmcSaleBanner record);

    int updateByPrimaryKey(AmcSaleBanner record);
}