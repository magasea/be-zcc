package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcGrntor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcGrntorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AmcGrntorMapper {
    long countByExample(AmcGrntorExample example);

    int deleteByExample(AmcGrntorExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmcGrntor record);

    int insertSelective(AmcGrntor record);

    List<AmcGrntor> selectByExampleWithRowbounds(AmcGrntorExample example, RowBounds rowBounds);

    List<AmcGrntor> selectByExample(AmcGrntorExample example);

    AmcGrntor selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmcGrntor record, @Param("example") AmcGrntorExample example);

    int updateByExample(@Param("record") AmcGrntor record, @Param("example") AmcGrntorExample example);

    int updateByPrimaryKeySelective(AmcGrntor record);

    int updateByPrimaryKey(AmcGrntor record);
}