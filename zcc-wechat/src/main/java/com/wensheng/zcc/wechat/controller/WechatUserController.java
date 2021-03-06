package com.wensheng.zcc.wechat.controller;

import com.wensheng.zcc.common.module.dto.AmcRegionInfo;
import com.wensheng.zcc.common.module.dto.WXUserFavor;
import com.wensheng.zcc.common.module.dto.WXUserWatchObject;
import com.wensheng.zcc.common.params.AmcPage;
import com.wensheng.zcc.common.params.PageInfo;
import com.wensheng.zcc.common.params.PageReqRepHelper;
import com.wensheng.zcc.common.utils.AmcNumberUtils;
import com.wensheng.zcc.wechat.controller.helper.QueryParam;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatUser;
import com.wensheng.zcc.wechat.module.vo.AmcWechatUserInfo;
import com.wensheng.zcc.wechat.module.vo.TagMod;
import com.wensheng.zcc.wechat.module.vo.UserLngLat;
import com.wensheng.zcc.wechat.module.vo.WXBindPhoneVo;
import com.wensheng.zcc.wechat.module.vo.WXUserStatistics;
import com.wensheng.zcc.wechat.module.vo.WXUserWatchCount;
import com.wensheng.zcc.wechat.module.vo.WXUserWatchOnCheckVo;
import com.wensheng.zcc.wechat.module.vo.WXUserWatchOnObject;
import com.wensheng.zcc.wechat.module.vo.WXUserWatchOnVo;
import com.wensheng.zcc.wechat.module.vo.WechatUserVo;
import com.wensheng.zcc.wechat.service.WXBasicService;
import com.wensheng.zcc.wechat.service.WXUserService;
import com.wensheng.zcc.wechat.service.impl.WXMaterialServiceImpl;
import com.wensheng.zcc.wechat.service.impl.WXUserServiceImpl;
import com.wensheng.zcc.wechat.service.impl.WXUserServiceImpl.TagInfoExt;
import com.wensheng.zcc.wechat.service.impl.WXUserServiceImpl.UserIdsResp;
import com.wensheng.zcc.wechat.utils.wechat.AesException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
@Slf4j
@Controller
@RequestMapping(value = "/ws/wechat")
public class WechatUserController {

  @Autowired
  WXUserServiceImpl wxService;

  @Autowired
  WXBasicService wxBasicService;

  @Autowired
  WXMaterialServiceImpl wxMaterialService;

  @Autowired
  WXUserService wxUserService;

  @RequestMapping(value = "/check", method = RequestMethod.GET)
  @ResponseBody
  public String checkWechat(@RequestParam("signature") String signature, @RequestParam("echostr") String echostr,
      @RequestParam("nonce") String nonce, @RequestParam("timestamp") Long timeStamp)
      throws ParserConfigurationException, AesException {

    String response = wxBasicService.checkWechatResp(timeStamp, nonce, echostr, signature);
    log.info(response);
    return response;

  }

  @RequestMapping(value = "/check", method = RequestMethod.POST)
  @ResponseBody
  public String reportLocation(@RequestBody  String xmlMsg) {

    String response = null ;
    log.info(xmlMsg);
    if(xmlMsg.contains("LOCATION")){
      response = wxService.recordLocation(xmlMsg);
    }else if(xmlMsg.contains("MASSSENDJOBFINISH")){
      response = wxMaterialService.recordMsgResult(xmlMsg);
    }else if(xmlMsg.contains("subscribe")){
      log.info("call subscrib service");
      response = wxUserService.userSubscribe(xmlMsg);
    }else if(xmlMsg.contains("text")){
      log.info("call user msg service");
      response = wxUserService.userMsg(xmlMsg);
    }else{
      log.info("maybe it is event");
      response = wxUserService.sendEvent(xmlMsg);
    }
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

  @RequestMapping(value = "/updateWXUser", method = RequestMethod.POST)
  @ResponseBody
  public void updateWXUser(@RequestBody WechatUser wechatUser)
  {

      wxService.updateWXUser(wechatUser);

  }

  @RequestMapping(value = "/tag/get-tagInfo", method = RequestMethod.GET)
  @ResponseBody
  public List<TagInfoExt> getTagInfo()
  {

    return  wxService.getWechatPublicUserTag();

  }

  @RequestMapping(value = "/tag/tagUserWithGeo", method = RequestMethod.GET)
  @ResponseBody
  public void tagUserWithGeo () throws Exception {

      wxService.tagUserTask();

  }

  @RequestMapping(value = "/tag/create-user-tag", method = RequestMethod.POST)
  @ResponseBody
  public Long createUsersTag(@RequestParam("tagName") String tagName) throws Exception {

     return wxService.createWechatPublicUserTag(tagName);

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

  @RequestMapping(value = "/user/sendVerifyCode", method = RequestMethod.POST)
  @ResponseBody
  public boolean sendVerifyCode(@RequestBody WXBindPhoneVo wxBindPhoneVo) throws Exception {


//    return wxService.sendPhoneVcode( wxBindPhoneVo.getOpenId(),  wxBindPhoneVo.getPhone());
    return wxService.sendChangePhoneVerifyCode( wxBindPhoneVo.getOpenId(),  wxBindPhoneVo.getPhone());

  }

  @RequestMapping(value = "/user/sendChangePhoneVerifyCode", method = RequestMethod.POST)
  @ResponseBody
  public boolean sendChangePhoneVerifyCode(@RequestBody WXBindPhoneVo wxBindPhoneVo) throws Exception {


    return wxService.sendChangePhoneVerifyCode( wxBindPhoneVo.getOpenId(),  wxBindPhoneVo.getPhone());

  }

//  @RequestMapping(value = "/user/sendVerifyCodeTest", method = RequestMethod.POST)
//  @ResponseBody
//  public boolean sendVerifyCodeTest(@RequestBody WXBindPhoneVo wxBindPhoneVo) throws Exception {
//
//    String vcode = AmcNumberUtils.getValidationCode();
//    return wxService.sendPhoneVcodeTest(  wxBindPhoneVo.getOpenId(),wxBindPhoneVo.getPhone(), vcode);
//
//  }

  @RequestMapping(value = "/user/bindPhone", method = RequestMethod.POST)
  @ResponseBody
  public boolean bindPhone(@RequestBody WXBindPhoneVo wxBindPhoneVo ) throws Exception {

//    return wxService.bindPhone(wxBindPhoneVo.getOpenId(), wxBindPhoneVo.getPhone(),  wxBindPhoneVo.getVcode());
    return wxService.bindNewPhone(wxBindPhoneVo.getOpenId(), wxBindPhoneVo.getPhone(),  wxBindPhoneVo.getVcode());

  }

  @RequestMapping(value = "/user/watchOn", method = RequestMethod.POST)
  @ResponseBody
  public boolean watchOn(@RequestBody WXUserWatchOnVo wxUserWatchOnVo ) throws Exception {

    return wxService.watchOnObject( wxUserWatchOnVo.getOpenId(), wxUserWatchOnVo.getPhone(), wxUserWatchOnVo.getObjectId(),
        wxUserWatchOnVo.getType());

  }

  @RequestMapping(value = "/user/unWatchOn", method = RequestMethod.POST)
  @ResponseBody
  public boolean unWatchOn(@RequestBody WXUserWatchOnVo wxUserWatchOnVo ) throws Exception {

    return wxService.unWatchOn( wxUserWatchOnVo.getOpenId(), wxUserWatchOnVo.getPhone(), wxUserWatchOnVo.getObjectId(),
        wxUserWatchOnVo.getType());

  }

  @RequestMapping(value = "/user/unWatchOnList", method = RequestMethod.POST)
  @ResponseBody
  public boolean unWatchOnList(@RequestBody List<WXUserWatchOnVo> wxUserWatchOnVos ) throws Exception {

    return wxService.unWatchOnList( wxUserWatchOnVos);

  }

  @RequestMapping(value = "/user/userWatched", method = RequestMethod.POST)
  @ResponseBody
  public List<WXUserWatchObject> userWatched(@RequestBody String openId ) throws Exception {

    return wxService.getUserWatched( openId);

  }
  @RequestMapping(value = "/user/userWatchCount", method = RequestMethod.POST)
  @ResponseBody
  public List<WXUserWatchCount> wxUserWatchCounts(@RequestBody List<WXUserWatchOnObject> objectList ) throws Exception {

    return wxService.getWXUserWatchCount(objectList);

  }

  @RequestMapping(value = "/user/getObjectWatchedUsers", method = RequestMethod.POST)
  @ResponseBody
  public List<WXUserWatchObject> getObjectWatchedUsers(@RequestBody WXUserWatchOnVo wxUserWatchOnVo ) throws Exception {

    return wxService.getObjectWatchedUsers( wxUserWatchOnVo.getObjectId(), wxUserWatchOnVo.getType());

  }

  @RequestMapping(value = "/user/saveUserFavor", method = RequestMethod.POST)
  @ResponseBody
  public  boolean saveUserFavor(@RequestBody WXUserFavor wxUserFavor ) throws Exception {

    return wxService.saveUserFavor( wxUserFavor);

  }

  @RequestMapping(value = "/user/getUserFavor", method = RequestMethod.POST)
  @ResponseBody
  public  WXUserFavor getUserFavor(@RequestBody String openId, @RequestHeader(value = "X-FORWARDED-FOR", required = false) String loginIp ) throws Exception {
    log.info(loginIp);
    return wxService.getUserFavor( openId, loginIp);

  }

  @RequestMapping(value = "/user/checkUserWatchOn", method = RequestMethod.POST)
  @ResponseBody
  public  List<WXUserWatchOnObject> checkUserWatchOn(@RequestBody WXUserWatchOnCheckVo wxUserWatchOnCheckVo ) throws Exception {

    return wxService.checkUserFavor( wxUserWatchOnCheckVo);

  }

  @RequestMapping(value = "/user/getUserLocation", method = RequestMethod.POST)
  @ResponseBody
  public AmcRegionInfo getUserLocation(@RequestBody UserLngLat userLngLat ) throws Exception {


    return wxService.getUserLocation( userLngLat);

  }


  @RequestMapping(value = "/user/getUserInfo", method = RequestMethod.POST)
  @ResponseBody
  public AmcWechatUserInfo getUserInfo(@RequestBody String openId ) throws Exception {

    return wxService.getUserInfo( openId);

  }
  @RequestMapping(value = "/user/getUserInfoByGet", method = RequestMethod.GET)
  @ResponseBody
  public AmcWechatUserInfo getUserInfoByGet(@RequestParam String openId ) throws Exception {

    return wxService.getUserInfo( openId);

  }

  @RequestMapping(value = "/user/getUserInfos", method = RequestMethod.POST)
  @ResponseBody
  public AmcPage<WechatUserVo> getUserInfos(@RequestBody QueryParam queryParam) throws Exception {
//    Map<String, Direction> orderByParam = new HashMap<>();
//    if(queryParam.getPageInfo()!= null && queryParam.getPageInfo().getSort() == null){
//      orderByParam.put("id", Direction.DESC);
//    }else{
//      orderByParam = PageReqRepHelper.getOrderParam(queryParam.getPageInfo());
//    }
//    if (CollectionUtils.isEmpty(orderByParam)) {
//      orderByParam.put("id", Direction.DESC);
//    }

    int offset = PageReqRepHelper.getOffset(queryParam.getPageInfo());
    List<WechatUserVo> wechatUsers;
    try {
      wechatUsers = wxService.getAllWechatUsers(offset, queryParam.getPageInfo().getSize(), queryParam);

    } catch (Exception ex) {
      log.error("got error when query:" + ex.getMessage());
      throw ex;
    }
    Long totalCount = wxService.getAllWechatUserCount(queryParam);
//    Page<AmcOrigCreditor> amcOrigCreditorPage = PageReqRepHelper.getPageResp(totalCount, amcOrigCreditors, pageable);
    return PageReqRepHelper.getAmcPage(wechatUsers, totalCount);

  }

  @RequestMapping(value = "/user/getSimpleUserInfos", method = RequestMethod.POST)
  @ResponseBody
  public AmcPage<WechatUser> getSimpleUserInfos(@RequestBody QueryParam queryParam) throws Exception {
//    Map<String, Direction> orderByParam = new HashMap<>();
//    if(queryParam.getPageInfo()!= null && queryParam.getPageInfo().getSort() == null){
//      orderByParam.put("id", Direction.DESC);
//    }else{
//      orderByParam = PageReqRepHelper.getOrderParam(queryParam.getPageInfo());
//    }
//    if (CollectionUtils.isEmpty(orderByParam)) {
//      orderByParam.put("id", Direction.DESC);
//    }

    int offset = PageReqRepHelper.getOffset(queryParam.getPageInfo());
    List<WechatUser> wechatUsers;
    try {
      wechatUsers = wxService.getSimpleWechatUsers(offset, queryParam.getPageInfo().getSize(), queryParam);

    } catch (Exception ex) {
      log.error("got error when query:" + ex.getMessage());
      throw ex;
    }
    Long totalCount = wxService.getAllWechatUserCount(queryParam);
//    Page<AmcOrigCreditor> amcOrigCreditorPage = PageReqRepHelper.getPageResp(totalCount, amcOrigCreditors, pageable);
    return PageReqRepHelper.getAmcPage(wechatUsers, totalCount);

  }


  @RequestMapping(value = "/user/getUserStatistics", method = RequestMethod.POST)
  @ResponseBody
  public WXUserStatistics getUserStatistics(@RequestParam String startDate, @RequestParam String endDate) throws Exception {
    return wxService.getUserStaticFromWX(startDate, endDate);
  }
}
