package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcPerson;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcPersonExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AmcPersonMapper {
    long countByExample(AmcPersonExample example);

    int deleteByExample(AmcPersonExample example);

    int insert(AmcPerson record);

    int insertSelective(AmcPerson record);

    List<AmcPerson> selectByExample(AmcPersonExample example);

    int updateByExampleSelective(@Param("record") AmcPerson record, @Param("example") AmcPersonExample example);

    int updateByExample(@Param("record") AmcPerson record, @Param("example") AmcPersonExample example);
}