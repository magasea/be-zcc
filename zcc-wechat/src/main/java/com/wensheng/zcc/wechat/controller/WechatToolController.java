package com.wensheng.zcc.wechat.controller;

import com.wensheng.zcc.wechat.module.vo.WXSign4Url;
import com.wensheng.zcc.wechat.module.vo.helper.MaterialTypeEnum;
import com.wensheng.zcc.wechat.module.vo.helper.NeedOpenCommentEnum;
import com.wensheng.zcc.wechat.module.vo.helper.OnlyFansCanCommentEnum;
import com.wensheng.zcc.wechat.module.vo.helper.ShowCoverPicEnum;
import com.wensheng.zcc.wechat.service.WXToolService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/ws/wechat/tool")
public class WechatToolController {

  @Autowired
  WXToolService wxToolService;

  @RequestMapping(value = "/makeSign4Url", method = RequestMethod.POST)
  @ResponseBody
  public WXSign4Url makeSign4Url(@RequestParam String url) throws Exception {

    return wxToolService.makeSignKey(url);
  }


}
