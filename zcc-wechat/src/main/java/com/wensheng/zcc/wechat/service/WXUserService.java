package com.wensheng.zcc.wechat.service;

import com.wensheng.zcc.common.module.dto.AmcRegionInfo;
import com.wensheng.zcc.common.module.dto.WXUserFavor;
import com.wensheng.zcc.common.module.dto.WXUserWatchObject;
import com.wensheng.zcc.common.module.dto.WechatUserInfo;
import com.wensheng.zcc.wechat.controller.helper.QueryParam;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatUser;
import com.wensheng.zcc.wechat.module.vo.AmcWechatUserInfo;
import com.wensheng.zcc.wechat.module.vo.UserLngLat;
import com.wensheng.zcc.wechat.module.vo.WXUserWatchCount;
import com.wensheng.zcc.wechat.module.vo.WXUserWatchOnCheckVo;
import com.wensheng.zcc.wechat.module.vo.WXUserWatchOnObject;
import com.wensheng.zcc.wechat.module.vo.WXUserWatchOnVo;
import com.wensheng.zcc.wechat.module.vo.WechatUserVo;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import javax.management.Query;
import org.springframework.data.domain.Sort.Direction;

public interface WXUserService {
  public void tagUserTask() throws Exception;

  void syncUserInfoFromWX();

  boolean sendPhoneVcode(String phone,String openId) throws Exception;
//  boolean sendPhoneVcodeTest(String phone, String openId,  String code) throws Exception;

//  boolean bindPhone(String openId, String phone, String code) throws Exception;
  boolean bindNewPhone(String openId, String phone, String code) throws Exception;

  String userSubscribe(String xmlMsg);

  String userMsg(String xmlMsg);

  boolean watchOnObject(String openId, String phone, Long objectId, Integer objectType)
      throws Exception;

  List<WXUserWatchOnObject> getUserWatchedOn(String openId);

  List<WXUserWatchObject> getObjectWatchedUsers(Long objectId, Integer type);

  boolean saveUserFavor(WXUserFavor wxUserFavor);

  public WXUserFavor getUserFavor(String openId, String ipadd);

  public List<WXUserWatchOnObject> checkUserFavor(WXUserWatchOnCheckVo wxUserWatchOnCheckVo);

  AmcRegionInfo getUserLocation(UserLngLat userLngLat);

  public AmcWechatUserInfo getUserInfo(String openId);

  boolean unWatchOn(String openId, String phone, Long objectId, Integer type);

  public List<WechatUserVo> getAllWechatUsers(int offset, int size,
      QueryParam queryParam) throws Exception;

  public List<WechatUser> getSimpleWechatUsers(int offset, int size,
      QueryParam queryParam) throws ParseException;

  public List<WXUserWatchCount> getWXUserWatchCount(List<WXUserWatchOnObject> objectList);

  WechatUserInfo saveWechatUserInfo(String openId, String accessToken, String stateInfo);

  WechatUserInfo saveRpcWXVisitorInfo(WechatUserInfo wechatUserInfo, String accessToken, String state);

  void updateUserVisitInfo(String openId, Long lastTime, Long timeSpent, Long firstLoginTime)
      throws ParseException;

  String sendEvent(String xmlMsg);

  void updateWXUser(WechatUser wechatUser);



  boolean unWatchOnList(List<WXUserWatchOnVo> wxUserWatchOnVos);

  boolean sendChangePhoneVerifyCode(String openId, String phone) throws Exception;
}
