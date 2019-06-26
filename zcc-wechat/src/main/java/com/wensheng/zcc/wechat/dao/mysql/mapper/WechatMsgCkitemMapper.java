package com.wensheng.zcc.wechat.dao.mysql.mapper;

import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatMsgCkitem;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatMsgCkitemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WechatMsgCkitemMapper {
    long countByExample(WechatMsgCkitemExample example);

    int deleteByExample(WechatMsgCkitemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WechatMsgCkitem record);

    int insertSelective(WechatMsgCkitem record);

    List<WechatMsgCkitem> selectByExampleWithRowbounds(WechatMsgCkitemExample example, RowBounds rowBounds);

    List<WechatMsgCkitem> selectByExample(WechatMsgCkitemExample example);

    WechatMsgCkitem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WechatMsgCkitem record, @Param("example") WechatMsgCkitemExample example);

    int updateByExample(@Param("record") WechatMsgCkitem record, @Param("example") WechatMsgCkitemExample example);

    int updateByPrimaryKeySelective(WechatMsgCkitem record);

    int updateByPrimaryKey(WechatMsgCkitem record);
}