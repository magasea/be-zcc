package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcGrntctrct;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcGrntctrctExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AmcGrntctrctMapper {
    long countByExample(AmcGrntctrctExample example);

    int deleteByExample(AmcGrntctrctExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmcGrntctrct record);

    int insertSelective(AmcGrntctrct record);

    List<AmcGrntctrct> selectByExample(AmcGrntctrctExample example);

    AmcGrntctrct selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmcGrntctrct record, @Param("example") AmcGrntctrctExample example);

    int updateByExample(@Param("record") AmcGrntctrct record, @Param("example") AmcGrntctrctExample example);

    int updateByPrimaryKeySelective(AmcGrntctrct record);

    int updateByPrimaryKey(AmcGrntctrct record);
}