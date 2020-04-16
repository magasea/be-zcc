package com.wensheng.zcc.cust.dao.mysql.mapper;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfoBak;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfoBakExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CustTrdInfoBakMapper {
    long countByExample(CustTrdInfoBakExample example);

    int deleteByExample(CustTrdInfoBakExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CustTrdInfoBak record);

    int insertSelective(CustTrdInfoBak record);

    List<CustTrdInfoBak> selectByExampleWithRowbounds(CustTrdInfoBakExample example, RowBounds rowBounds);

    List<CustTrdInfoBak> selectByExample(CustTrdInfoBakExample example);

    CustTrdInfoBak selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CustTrdInfoBak record, @Param("example") CustTrdInfoBakExample example);

    int updateByExample(@Param("record") CustTrdInfoBak record, @Param("example") CustTrdInfoBakExample example);

    int updateByPrimaryKeySelective(CustTrdInfoBak record);

    int updateByPrimaryKey(CustTrdInfoBak record);
}