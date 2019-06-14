package com.wensheng.zcc.wechat.controller;

import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatUser;
import com.wensheng.zcc.wechat.module.vo.TagMod;
import com.wensheng.zcc.wechat.service.WXBasicService;
import com.wensheng.zcc.wechat.service.impl.WXUserServiceImpl;
import com.wensheng.zcc.wechat.service.impl.WXUserServiceImpl.TagInfoExt;
import com.wensheng.zcc.wechat.service.impl.WXUserServiceImpl.UserIdsResp;
import com.wensheng.zcc.wechat.utils.wechat.AesException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

@Controller
@RequestMapping(value = "/ws/wechat")
public class WechatController {

  @Autowired
  WXUserServiceImpl wxService;

  @Autowired
  WXBasicService wxBasicService;

  @RequestMapping(value = "/check", method = RequestMethod.GET)
  @ResponseBody
  public String checkWechat(@RequestParam("signature") String signature, @RequestParam("echostr") String echostr,
      @RequestParam("nonce") String nonce, @RequestParam("timestamp") Long timeStamp)
      throws ParserConfigurationException, AesException {

    String response = wxBasicService.checkWechatResp(timeStamp, nonce, echostr, signature);
    return response;

  }

  @RequestMapping(value = "/check", method = RequestMethod.POST)
  @ResponseBody
  public String reportLocation(@RequestBody  String xmlLocation) {

    String response = wxService.recordLocation(xmlLocation);
    return response;

  }


  @RequestMapping(value = "/public/upload", headers = "Content-Type= multipart/form-data",method = RequestMethod.POST)
  @ResponseBody
  public String addAmcAssetImage(
      @RequestParam("assetId") Long assetId, @RequestParam("isToOss") Boolean isToOss,
      @RequestParam("imageClass") Integer tag, @RequestParam("actionId") Long actionId, @RequestParam(value = "description",
      required = false) String description,
      @RequestPart("uploadingImages") MultipartFile[] uploadingImages) throws Exception {
    List<String> filePaths = new ArrayList<>();

    return "";
  }


  @RequestMapping(value = "/get-token", method = RequestMethod.GET)
  @ResponseBody
  public String getToken()
       {

    String response = wxBasicService.getPublicToken();
    return response;

  }

  @RequestMapping(value = "/get-users", method = RequestMethod.GET)
  @ResponseBody
  public UserIdsResp getUsersIds(@RequestParam(value = "openId", required = false) String openId)
  {

    return  wxService.getWechatPublicUserIds(openId);

  }

  @RequestMapping(value = "/get-usersInfo", method = RequestMethod.POST)
  @ResponseBody
  public List<WechatUser> getUsersInfo(@RequestBody List<String> openIds)
  {

    return  wxService.getWechatPublicUserInfo(openIds);

  }

  @RequestMapping(value = "/tag/get-tagInfo", method = RequestMethod.GET)
  @ResponseBody
  public List<TagInfoExt> getTagInfo()
  {

    return  wxService.getWechatPublicUserTag();

  }
  @RequestMapping(value = "/tag/create-user-tag", method = RequestMethod.POST)
  @ResponseBody
  public String createUsersTag(@RequestParam("tagName") String tagName)
  {

     wxService.createWechatPublicUserTag(tagName);
     return "success";

  }

  @RequestMapping(value = "/tag/del-user-tag", method = RequestMethod.POST)
  @ResponseBody
  public String delUsersTag(@RequestParam("tagId") Long wechatTagId) throws Exception {

    wxService.delWechatPublicUserTag(wechatTagId);
    return "success";

  }

  @RequestMapping(value = "/tag/mod-user-tag", method = RequestMethod.POST)
  @ResponseBody
  public String modUsersTag(@RequestBody TagMod tagMod) throws Exception {

    wxService.modWechatPublicUserTag(tagMod);
    return "success";

  }

  @RequestMapping(value = "/tag/batch-untag", method = RequestMethod.POST)
  @ResponseBody
  public String batchUntag(@RequestBody List<String> openIds, @RequestParam("tagId") Long tagId)
  {

    return wxService.untagWechatPublicUserBatch(openIds, tagId);

  }
  @RequestMapping(value = "/tag/batch-tag", method = RequestMethod.POST)
  @ResponseBody
  public String batchTag(@RequestBody List<String> openIds, @RequestParam("tagId") Long tagId)
  {

    return wxService.tagWechatPublicUserBatch(openIds, tagId);

  }

  @RequestMapping(value = "/tag/get-usertags", method = RequestMethod.POST)
  @ResponseBody
  public List<Long> getUserTag(@RequestParam("openId") String openId) throws Exception {

    return wxService.getTagOfUser(openId);

  }

  @RequestMapping(value = "/tag/get-usersOfTag", method = RequestMethod.POST)
  @ResponseBody
  public UserIdsResp getUserTag(@RequestParam(value = "openId", required = false) String openId,
      @RequestParam("tagId") Long tagId) throws Exception {

    return wxService.getUsersOfTagId( tagId, openId);

  }
}
