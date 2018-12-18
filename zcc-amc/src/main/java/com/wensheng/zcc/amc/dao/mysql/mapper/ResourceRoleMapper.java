package com.wensheng.zcc.amc.dao.mysql.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.ResourceRole;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.ResourceRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ResourceRoleMapper {
    long countByExample(ResourceRoleExample example);

    int deleteByExample(ResourceRoleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ResourceRole record);

    int insertSelective(ResourceRole record);

    List<ResourceRole> selectByExample(ResourceRoleExample example);

    ResourceRole selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ResourceRole record, @Param("example") ResourceRoleExample example);

    int updateByExample(@Param("record") ResourceRole record, @Param("example") ResourceRoleExample example);

    int updateByPrimaryKeySelective(ResourceRole record);

    int updateByPrimaryKey(ResourceRole record);
}