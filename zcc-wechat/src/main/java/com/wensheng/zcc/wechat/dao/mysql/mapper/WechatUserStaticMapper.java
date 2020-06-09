package com.wensheng.zcc.wechat.dao.mysql.mapper;

import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatUserStatic;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatUserStaticExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WechatUserStaticMapper {
    long countByExample(WechatUserStaticExample example);

    int deleteByExample(WechatUserStaticExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WechatUserStatic record);

    int insertSelective(WechatUserStatic record);

    List<WechatUserStatic> selectByExampleWithRowbounds(WechatUserStaticExample example, RowBounds rowBounds);

    List<WechatUserStatic> selectByExample(WechatUserStaticExample example);

    WechatUserStatic selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WechatUserStatic record, @Param("example") WechatUserStaticExample example);

    int updateByExample(@Param("record") WechatUserStatic record, @Param("example") WechatUserStaticExample example);

    int updateByPrimaryKeySelective(WechatUserStatic record);

    int updateByPrimaryKey(WechatUserStatic record);
}