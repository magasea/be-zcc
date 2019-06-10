package com.wensheng.zcc.wechat.dao.mysql.mapper;

import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatTag;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatTagExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WechatTagMapper {
    long countByExample(WechatTagExample example);

    int deleteByExample(WechatTagExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WechatTag record);

    int insertSelective(WechatTag record);

    List<WechatTag> selectByExampleWithRowbounds(WechatTagExample example, RowBounds rowBounds);

    List<WechatTag> selectByExample(WechatTagExample example);

    WechatTag selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WechatTag record, @Param("example") WechatTagExample example);

    int updateByExample(@Param("record") WechatTag record, @Param("example") WechatTagExample example);

    int updateByPrimaryKeySelective(WechatTag record);

    int updateByPrimaryKey(WechatTag record);
}