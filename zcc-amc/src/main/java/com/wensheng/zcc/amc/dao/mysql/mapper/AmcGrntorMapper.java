package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcGrntor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcGrntorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AmcGrntorMapper {
    long countByExample(AmcGrntorExample example);

    int deleteByExample(AmcGrntorExample example);

    int insert(AmcGrntor record);

    int insertSelective(AmcGrntor record);

    List<AmcGrntor> selectByExample(AmcGrntorExample example);

    int updateByExampleSelective(@Param("record") AmcGrntor record, @Param("example") AmcGrntorExample example);

    int updateByExample(@Param("record") AmcGrntor record, @Param("example") AmcGrntorExample example);
}