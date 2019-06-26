package com.wensheng.zcc.wechat.dao.mysql.mapper.ext;

import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatMsg;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatMsgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WechatMsgExtMapper {
    long countByExample(WechatMsgExample example);

    int deleteByExample(WechatMsgExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WechatMsg record);

    int insertSelective(WechatMsg record);

    List<WechatMsg> selectByExampleWithRowbounds(WechatMsgExample example, RowBounds rowBounds);

    List<WechatMsg> selectByExample(WechatMsgExample example);

    WechatMsg selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WechatMsg record, @Param("example") WechatMsgExample example);

    int updateByExample(@Param("record") WechatMsg record, @Param("example") WechatMsgExample example);

    int updateByPrimaryKeySelective(WechatMsg record);

    int updateByPrimaryKey(WechatMsg record);
}