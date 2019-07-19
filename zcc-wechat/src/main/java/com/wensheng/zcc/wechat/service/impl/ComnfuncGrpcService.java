package com.wensheng.zcc.wechat.service.impl;

import com.wensheng.zcc.wechat.module.vo.MediaUploadResp;
import com.wenshengamc.zcc.comnfunc.gaodegeo.ComnFuncServiceGrpc.ComnFuncServiceBlockingStub;
import com.wenshengamc.zcc.comnfunc.gaodegeo.WXPubTokenReq;
import com.wenshengamc.zcc.wechat.AmcAssetImage;
import com.wenshengamc.zcc.wechat.ImageUploadResult;
import com.wenshengamc.zcc.wechat.UploadImg2WechatReq;
import com.wenshengamc.zcc.wechat.UploadImg2WechatResp;
import com.wenshengamc.zcc.wechat.WechatAssetImage;
import com.wenshengamc.zcc.wechat.WechatGrpcServiceGrpc.WechatGrpcServiceImplBase;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class ComnfuncGrpcService extends WechatGrpcServiceImplBase {


  @Autowired
      @Qualifier("comnFuncPubService")
  ComnFuncServiceBlockingStub comnFuncPubServiceStub;

  public String getWXPubToken(){

    return comnFuncPubServiceStub.getWXPublicToken(WXPubTokenReq.newBuilder().build()).getWxPubToken();

  }


}
