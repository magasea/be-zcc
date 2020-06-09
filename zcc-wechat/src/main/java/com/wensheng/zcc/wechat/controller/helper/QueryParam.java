package com.wensheng.zcc.wechat.controller.helper;

import com.wensheng.zcc.common.aop.SQLInjectionSafe;
import com.wensheng.zcc.common.params.PageInfo;
import java.util.Date;
import java.util.List;
import lombok.Data;

/**
 * @author chenwei on 1/8/19
 * @project zcc-backend
 */
@Data
public class QueryParam {
  String wechatNickName = null;
  List<Date> registTime = null;
  String stateInfo = null;
  Boolean phoneBinded = false;
  PageInfo pageInfo;

}
