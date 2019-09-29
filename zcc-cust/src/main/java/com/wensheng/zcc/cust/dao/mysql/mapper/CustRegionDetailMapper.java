package com.wensheng.zcc.cust.dao.mysql.mapper;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustRegionDetail;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustRegionDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CustRegionDetailMapper {
    long countByExample(CustRegionDetailExample example);

    int deleteByExample(CustRegionDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CustRegionDetail record);

    int insertSelective(CustRegionDetail record);

    List<CustRegionDetail> selectByExampleWithRowbounds(CustRegionDetailExample example, RowBounds rowBounds);

    List<CustRegionDetail> selectByExample(CustRegionDetailExample example);

    CustRegionDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CustRegionDetail record, @Param("example") CustRegionDetailExample example);

    int updateByExample(@Param("record") CustRegionDetail record, @Param("example") CustRegionDetailExample example);

    int updateByPrimaryKeySelective(CustRegionDetail record);

    int updateByPrimaryKey(CustRegionDetail record);
}