package com.wensheng.zcc.wechat.dao.mysql.mapper;

import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatUser;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WechatUserMapper {
    long countByExample(WechatUserExample example);

    int deleteByExample(WechatUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WechatUser record);

    int insertSelective(WechatUser record);

    List<WechatUser> selectByExampleWithRowbounds(WechatUserExample example, RowBounds rowBounds);

    List<WechatUser> selectByExample(WechatUserExample example);

    WechatUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WechatUser record, @Param("example") WechatUserExample example);

    int updateByExample(@Param("record") WechatUser record, @Param("example") WechatUserExample example);

    int updateByPrimaryKeySelective(WechatUser record);

    int updateByPrimaryKey(WechatUser record);
}