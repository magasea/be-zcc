package com.wensheng.zcc.sso.dao.mysql.mapper;

import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcSpecUser;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcSpecUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AmcSpecUserMapper {
    long countByExample(AmcSpecUserExample example);

    int deleteByExample(AmcSpecUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AmcSpecUser record);

    int insertSelective(AmcSpecUser record);

    List<AmcSpecUser> selectByExampleWithRowbounds(AmcSpecUserExample example, RowBounds rowBounds);

    List<AmcSpecUser> selectByExample(AmcSpecUserExample example);

    AmcSpecUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AmcSpecUser record, @Param("example") AmcSpecUserExample example);

    int updateByExample(@Param("record") AmcSpecUser record, @Param("example") AmcSpecUserExample example);

    int updateByPrimaryKeySelective(AmcSpecUser record);

    int updateByPrimaryKey(AmcSpecUser record);
}