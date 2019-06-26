package com.wensheng.zcc.wechat.module.vo;

import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatMsg;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatMsgCkitem;
import java.util.List;
import lombok.Data;

@Data
public class AMCWXMsgResult {
  Long id;
  WechatMsg wechatMsg;
  List<WechatMsgCkitem> wechatMsgCkitems;

}
