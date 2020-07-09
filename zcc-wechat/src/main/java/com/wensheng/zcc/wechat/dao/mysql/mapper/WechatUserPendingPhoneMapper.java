package com.wensheng.zcc.wechat.dao.mysql.mapper;

import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatUserPendingPhone;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatUserPendingPhoneExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WechatUserPendingPhoneMapper {
    long countByExample(WechatUserPendingPhoneExample example);

    int deleteByExample(WechatUserPendingPhoneExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WechatUserPendingPhone record);

    int insertSelective(WechatUserPendingPhone record);

    List<WechatUserPendingPhone> selectByExampleWithRowbounds(WechatUserPendingPhoneExample example, RowBounds rowBounds);

    List<WechatUserPendingPhone> selectByExample(WechatUserPendingPhoneExample example);

    WechatUserPendingPhone selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WechatUserPendingPhone record, @Param("example") WechatUserPendingPhoneExample example);

    int updateByExample(@Param("record") WechatUserPendingPhone record, @Param("example") WechatUserPendingPhoneExample example);

    int updateByPrimaryKeySelective(WechatUserPendingPhone record);

    int updateByPrimaryKey(WechatUserPendingPhone record);
}