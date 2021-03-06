package com.wensheng.zcc.wechat.dao.mysql.mapper.ext;

import com.wensheng.zcc.wechat.dao.mysql.mapper.WechatUserMapper;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatUser;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatUserExample;
import com.wensheng.zcc.wechat.module.dao.mysql.ext.WechatUserExtExample;
import com.wensheng.zcc.wechat.module.vo.WechatUserVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WechatUserExtMapper extends WechatUserMapper {


    List<WechatUserVo> selectByExtExample(WechatUserExtExample example);


    List<WechatUserVo> selectByExampleWithRowboundsExt(WechatUserExtExample example, RowBounds rowBounds);
}