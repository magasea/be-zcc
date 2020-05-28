package com.wensheng.zcc.sso.service.impl;

import com.google.gson.Gson;
import com.wensheng.zcc.common.module.dto.AmcSaleFilter;
import com.wensheng.zcc.common.module.dto.WechatUserInfo;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wenshengamc.zcc.wechat.UploadDebtImg2WechatResp;
import com.wenshengamc.zcc.wechat.WXUserFavor;
import com.wenshengamc.zcc.wechat.WXUserReq;
import com.wenshengamc.zcc.wechat.WXUserWatchOnObj;
import com.wenshengamc.zcc.wechat.WXUserWatchOnObjResp;
import com.wenshengamc.zcc.wechat.WXVistorInfo;
import com.wenshengamc.zcc.wechat.WechatGrpcServiceGrpc.WechatGrpcServiceBlockingStub;
import com.wenshengamc.zcc.wechat.WechatGrpcServiceGrpc.WechatGrpcServiceImplBase;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class WechatGrpcService extends WechatGrpcServiceImplBase {


  @Autowired
  WechatGrpcServiceBlockingStub wechatServiceStub;

  private Gson gson = new Gson();



  public WechatUserInfo saveWXVisitorInfo(WechatUserInfo wechatUserInfo){
    WXVistorInfo.Builder wxviBuilder = WXVistorInfo.newBuilder();
    AmcBeanUtils.copyProperties(wechatUserInfo, wxviBuilder);
    WXVistorInfo vistorInfo =  wechatServiceStub.saveWXVistorInfo(wxviBuilder.build());
    if(StringUtils.isEmpty(wechatUserInfo.getNickName())){
      wechatUserInfo.setNickName(vistorInfo.getNickname());
      wechatUserInfo.setHeadImgUrl(vistorInfo.getHeadimgurl());
      wechatUserInfo.setUnionId(vistorInfo.getUnionid());
      wechatUserInfo.setSex(vistorInfo.getSex());
      wechatUserInfo.setLanguage(vistorInfo.getLanguage());
      wechatUserInfo.setCountry(vistorInfo.getCountry());
      wechatUserInfo.setCity(vistorInfo.getCity());
      wechatUserInfo.setPrivilege(vistorInfo.getPrivilegeList());
      wechatUserInfo.setProvince(vistorInfo.getProvince());
    }
    return wechatUserInfo;
  }


  public com.wenshengamc.zcc.wechat.UploadImg2WechatResp uploadImage2Wechat(com.wenshengamc.zcc.wechat.UploadImg2WechatReq request) {
    return wechatServiceStub.uploadImage2Wechat(request);
  }

  public UploadDebtImg2WechatResp uploadDebtImage2Wechat(com.wenshengamc.zcc.wechat.UploadDebtImg2WechatReq request) {
    return wechatServiceStub.uploadDebtImage2Wechat(request);
  }

  public List<WXUserWatchOnObj> getWXUserWatchedOn(String openId){
    WXUserReq.Builder WXURB = WXUserReq.newBuilder();
    WXURB.setOpenId(openId);
    WXUserWatchOnObjResp wxUserWatchOnObjResp = wechatServiceStub.getWXUserWatchOnObjs(WXURB.build());
    return wxUserWatchOnObjResp.getWxUserWatchOnObjsList();
  }

  public AmcSaleFilter getWXUserFavor(String openId){
    WXUserReq.Builder WXURB = WXUserReq.newBuilder();
    WXURB.setOpenId(openId);
    WXUserFavor wxUserFavor  = null;
    try{
      wxUserFavor = wechatServiceStub.getWXUserFavor(WXURB.build());

    }catch (Exception ex){
      log.error("Failed to get user favor with openId:{}",openId, ex);
      return null;
    }
    if(StringUtils.isEmpty(wxUserFavor.getUserFavor())){
      return null;
    }
    AmcSaleFilter amcSaleFilter = gson.fromJson(wxUserFavor.getUserFavor(), AmcSaleFilter.class);
    return amcSaleFilter;
  }
}
