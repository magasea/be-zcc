package com.wensheng.zcc.cust.dao.mysql.mapper;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustRegionGov;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustRegionGovExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CustRegionGovMapper {
    long countByExample(CustRegionGovExample example);

    int deleteByExample(CustRegionGovExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CustRegionGov record);

    int insertSelective(CustRegionGov record);

    List<CustRegionGov> selectByExampleWithRowbounds(CustRegionGovExample example, RowBounds rowBounds);

    List<CustRegionGov> selectByExample(CustRegionGovExample example);

    CustRegionGov selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CustRegionGov record, @Param("example") CustRegionGovExample example);

    int updateByExample(@Param("record") CustRegionGov record, @Param("example") CustRegionGovExample example);

    int updateByPrimaryKeySelective(CustRegionGov record);

    int updateByPrimaryKey(CustRegionGov record);
}