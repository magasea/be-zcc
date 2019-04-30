package com.wensheng.zcc.cust.dao.mysql.mapper;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdSeller;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdSellerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CustTrdSellerMapper {
    long countByExample(CustTrdSellerExample example);

    int deleteByExample(CustTrdSellerExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CustTrdSeller record);

    int insertSelective(CustTrdSeller record);

    List<CustTrdSeller> selectByExampleWithRowbounds(CustTrdSellerExample example, RowBounds rowBounds);

    List<CustTrdSeller> selectByExample(CustTrdSellerExample example);

    CustTrdSeller selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CustTrdSeller record, @Param("example") CustTrdSellerExample example);

    int updateByExample(@Param("record") CustTrdSeller record, @Param("example") CustTrdSellerExample example);

    int updateByPrimaryKeySelective(CustTrdSeller record);

    int updateByPrimaryKey(CustTrdSeller record);
}