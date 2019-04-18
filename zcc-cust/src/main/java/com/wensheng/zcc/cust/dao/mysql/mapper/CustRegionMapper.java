package com.wensheng.zcc.cust.dao.mysql.mapper;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustRegion;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustRegionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CustRegionMapper {
    long countByExample(CustRegionExample example);

    int deleteByExample(CustRegionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CustRegion record);

    int insertSelective(CustRegion record);

    List<CustRegion> selectByExampleWithRowbounds(CustRegionExample example, RowBounds rowBounds);

    List<CustRegion> selectByExample(CustRegionExample example);

    CustRegion selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CustRegion record, @Param("example") CustRegionExample example);

    int updateByExample(@Param("record") CustRegion record, @Param("example") CustRegionExample example);

    int updateByPrimaryKeySelective(CustRegion record);

    int updateByPrimaryKey(CustRegion record);
}