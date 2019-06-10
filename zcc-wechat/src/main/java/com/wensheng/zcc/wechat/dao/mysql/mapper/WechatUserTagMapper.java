package com.wensheng.zcc.wechat.dao.mysql.mapper;

import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatUserTag;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatUserTagExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WechatUserTagMapper {
    long countByExample(WechatUserTagExample example);

    int deleteByExample(WechatUserTagExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WechatUserTag record);

    int insertSelective(WechatUserTag record);

    List<WechatUserTag> selectByExampleWithRowbounds(WechatUserTagExample example, RowBounds rowBounds);

    List<WechatUserTag> selectByExample(WechatUserTagExample example);

    WechatUserTag selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WechatUserTag record, @Param("example") WechatUserTagExample example);

    int updateByExample(@Param("record") WechatUserTag record, @Param("example") WechatUserTagExample example);

    int updateByPrimaryKeySelective(WechatUserTag record);

    int updateByPrimaryKey(WechatUserTag record);
}