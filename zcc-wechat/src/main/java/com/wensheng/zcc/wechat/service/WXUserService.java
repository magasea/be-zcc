package com.wensheng.zcc.wechat.service;

import com.wensheng.zcc.common.module.dto.AmcRegionInfo;
import com.wensheng.zcc.common.module.dto.WXUserFavor;
import com.wensheng.zcc.common.module.dto.WXUserWatchObject;
import com.wensheng.zcc.wechat.module.vo.UserLngLat;
import com.wensheng.zcc.wechat.module.vo.WXUserWatchOnCheckVo;
import com.wensheng.zcc.wechat.module.vo.WXUserWatchOnObject;
import java.util.List;

public interface WXUserService {
  public void tagUserTask() throws Exception;

  void syncUserInfoFromWX();

  boolean sendPhoneVcode(String phone,String openId,  String code) throws Exception;
  boolean sendPhoneVcodeTest(String phone, String openId,  String code) throws Exception;

  boolean bindPhone(String openId, String phone, String code) throws Exception;

  String userSubscribe(String xmlMsg);

  String userMsg(String xmlMsg);

  boolean watchOnObject(String openId, String phone, Long objectId, Integer objectType);

  List<WXUserWatchOnObject> getUserWatchedOn(String openId);

  List<WXUserWatchObject> getObjectWatchedUsers(Long objectId, Integer type);

  boolean saveUserFavor(WXUserFavor wxUserFavor);

  public WXUserFavor getUserFavor(String openId);

  public List<WXUserWatchOnObject> checkUserFavor(WXUserWatchOnCheckVo wxUserWatchOnCheckVo);

  AmcRegionInfo getUserLocation(UserLngLat userLngLat);
}
