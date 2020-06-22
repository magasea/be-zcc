package com.wensheng.zcc.wechat.controller;

import com.wensheng.zcc.wechat.module.vo.RecomUserVisitInfo;
import com.wensheng.zcc.wechat.module.vo.WXSign4Url;
import com.wensheng.zcc.wechat.module.vo.helper.MaterialTypeEnum;
import com.wensheng.zcc.wechat.module.vo.helper.NeedOpenCommentEnum;
import com.wensheng.zcc.wechat.module.vo.helper.OnlyFansCanCommentEnum;
import com.wensheng.zcc.wechat.module.vo.helper.ShowCoverPicEnum;
import com.wensheng.zcc.wechat.service.WXToolService;
import com.wensheng.zcc.wechat.service.WXUserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/ws/wechat/tool")
public class WechatToolController {

  @Autowired
  WXToolService wxToolService;

  @Autowired
  WXUserService wxUserService;

  @RequestMapping(value = "/makeSign4Url", method = RequestMethod.POST)
  @ResponseBody
  public WXSign4Url makeSign4Url(@RequestBody String url) throws Exception {

    return wxToolService.makeSignKey(url);
  }


  @RequestMapping(value = "/patchUserFirstLoginTime", method = RequestMethod.POST)
  @ResponseBody
  public void patchUserFirstLoginTime(@RequestBody String jsonItems) throws Exception {

     wxToolService.patchUserFirstLoginTime(jsonItems);
  }


  @RequestMapping(value = "/patchUserGeoRecord", method = RequestMethod.POST)
  @ResponseBody
  public void patchUserGeoRecord() throws Exception {

    wxToolService.patchGeoRecord();
  }

  @RequestMapping(value = "/patchUserFavorCreateTime", method = RequestMethod.POST)
  @ResponseBody
  public void patchUserFavorCreateTime() throws Exception {

    wxToolService.patchUserFavor();
  }

  @RequestMapping(value = "/getIpAddress", method = RequestMethod.POST)
  @ResponseBody
  public void getIpAddress(@RequestParam String openId, @RequestParam String ip) throws Exception {

    wxUserService.getUserFavor(openId, ip);
  }

  @RequestMapping(value = "/synchUserVisitInfo", method = RequestMethod.POST)
  @ResponseBody
  public void synchUserVisitInfo() throws Exception {

    wxToolService.syncUserVisitInfoWithRecomm();
  }

  @RequestMapping(value = "/pushUserVisitInfo", method = RequestMethod.POST)
  @ResponseBody
  public void pushUserVisitInfo(@RequestBody List<RecomUserVisitInfo> recomUserVisitInfos) throws Exception {

    wxToolService.pushUserVisitInfo(recomUserVisitInfos);
  }

  @RequestMapping(value = "/testException", method = RequestMethod.POST)
  @ResponseBody
  public void testException() throws Exception {

    throw new Exception("test exception");
  }
}
