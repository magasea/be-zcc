package com.wensheng.zcc.wechat.service.impl;

import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

import com.google.gson.Gson;
import com.wensheng.zcc.common.module.dto.AmcSaleFilter;
import com.wensheng.zcc.common.module.dto.WechatUserInfo;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import com.wensheng.zcc.wechat.module.vo.MediaUploadResp;
import com.wensheng.zcc.wechat.module.vo.WXUserWatchOnObject;
import com.wensheng.zcc.wechat.service.WXUserService;
import com.wenshengamc.zcc.wechat.AmcAssetImage;
import com.wenshengamc.zcc.wechat.AmcDebtImage;
import com.wenshengamc.zcc.wechat.DebtImageUploadResult;
import com.wenshengamc.zcc.wechat.ImageUploadResult;
import com.wenshengamc.zcc.wechat.UploadDebtImg2WechatResp;
import com.wenshengamc.zcc.wechat.UploadImg2WechatReq;
import com.wenshengamc.zcc.wechat.UploadImg2WechatResp;
import com.wenshengamc.zcc.wechat.WXUserFavor;
import com.wenshengamc.zcc.wechat.WXUserWatchOnObj;
import com.wenshengamc.zcc.wechat.WXUserWatchOnObjResp;
import com.wenshengamc.zcc.wechat.WXVistorInfo;
import com.wenshengamc.zcc.wechat.WechatAssetImage;
import com.wenshengamc.zcc.wechat.WechatAssetImageOrBuilder;
import com.wenshengamc.zcc.wechat.WechatGrpcServiceGrpc.WechatGrpcServiceImplBase;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
@Slf4j
@GRpcService(interceptors = LogInterceptor.class)
public class WechatGrpcService extends WechatGrpcServiceImplBase {


  @Autowired
  WXMaterialServiceImpl wxMaterialService;

  @Autowired
  WXUserService wxUserService;

  private Gson gson = new Gson();

  @Override
  public void uploadImage2Wechat(com.wenshengamc.zcc.wechat.UploadImg2WechatReq request,
      io.grpc.stub.StreamObserver<com.wenshengamc.zcc.wechat.UploadImg2WechatResp> responseObserver) {
    try{
      List<AmcAssetImage> amcAssetImageList = request.getAmcAssetImagesList();
      UploadImg2WechatResp.Builder rspBuilder = UploadImg2WechatResp.newBuilder();
      ImageUploadResult.Builder iurBuilder = ImageUploadResult.newBuilder();
      WechatAssetImage.Builder waiBuilder = WechatAssetImage.newBuilder();
      for(AmcAssetImage amcAssetImage : amcAssetImageList){
        List<String> amcImageUrls =  amcAssetImage.getAmcAssetImagesList();
        for(String url: amcImageUrls){
          waiBuilder.clear();
          String resultUrl = wxMaterialService.uploadImageByUrlSrc(url);
          waiBuilder.setAmcAssetImage(url).setWechatAssetImage(resultUrl);
          iurBuilder.addWechatAssetImages(waiBuilder);

        }
        if(!StringUtils.isEmpty(amcAssetImage.getAmcAssetMainImage())){
          MediaUploadResp mediaUploadResp =
              wxMaterialService.uploadMaterialByUrlSrc(amcAssetImage.getAmcAssetMainImage());

          iurBuilder.setMediaId(mediaUploadResp.getMediaId());
          iurBuilder.setMediaIdUrl(mediaUploadResp.getUrl());

        }

        iurBuilder.setAmcAssetId(amcAssetImage.getAmcAssetId());
        rspBuilder.addResults(iurBuilder);
      }
      responseObserver.onNext(rspBuilder.build());
      responseObserver.onCompleted();
    }catch (Exception ex){
      log.error("exception:", ex);

      responseObserver.onError(ex);
    }

//    wxMaterialService.uploadImage()

  }

  @Override
  public void uploadDebtImage2Wechat(com.wenshengamc.zcc.wechat.UploadDebtImg2WechatReq request,
      io.grpc.stub.StreamObserver<com.wenshengamc.zcc.wechat.UploadDebtImg2WechatResp> responseObserver) {

    try{
      UploadDebtImg2WechatResp.Builder uploadRspBdr = UploadDebtImg2WechatResp.newBuilder();
      DebtImageUploadResult.Builder debtImgUploadRltBdr = DebtImageUploadResult.newBuilder();
      for (AmcDebtImage amcDebtImage:  request.getAmcDebtImagesList()){
        debtImgUploadRltBdr.clear();
        if(!StringUtils.isEmpty(amcDebtImage.getAmcDebtMainImage())){
          MediaUploadResp mediaUploadResp =
              wxMaterialService.uploadMaterialByUrlSrc(amcDebtImage.getAmcDebtMainImage());
          debtImgUploadRltBdr.setDebtMediaId(mediaUploadResp.getMediaId());
          debtImgUploadRltBdr.setDebtMediaIdUrl(mediaUploadResp.getUrl());
          debtImgUploadRltBdr.setAmcDebtId(amcDebtImage.getAmcDebtId());
        }
        List<AmcAssetImage> amcAssetImages =  amcDebtImage.getAmcAssetImagesList();
        WechatAssetImage.Builder waiBuilder = WechatAssetImage.newBuilder();
        List<WechatAssetImage.Builder> wechatAssetImageOrBuilders = new ArrayList<>();
        for(AmcAssetImage amcAssetImage: amcAssetImages){

          for(String url: amcAssetImage.getAmcAssetImagesList()){
            waiBuilder.clear();
            String resultUrl = wxMaterialService.uploadImageByUrlSrc(url);
            waiBuilder.setAmcAssetId(amcAssetImage.getAmcAssetId()).setAmcAssetImage(url).setWechatAssetImage(resultUrl);
            debtImgUploadRltBdr.addWechatAssetImages(waiBuilder);

          }

        }
        uploadRspBdr.addResults(debtImgUploadRltBdr);
      }
      responseObserver.onNext(uploadRspBdr.build());
      responseObserver.onCompleted();
    }catch (Exception ex){
      log.error("exception:", ex);

      responseObserver.onError(ex);
    }


  }
  /**
   */
  @Override
  public void getWXUserWatchOnObjs(com.wenshengamc.zcc.wechat.WXUserReq request,
      io.grpc.stub.StreamObserver<com.wenshengamc.zcc.wechat.WXUserWatchOnObjResp> responseObserver) {
    if(StringUtils.isEmpty(request.getOpenId())){
      responseObserver.onError(ExceptionUtils.getAmcException(AmcExceptions.MISSING_MUST_PARAM,"openId为空"));
      return;
    }
    List<WXUserWatchOnObject> wxUserWatchOnObjects =  wxUserService.getUserWatchedOn(request.getOpenId());
    WXUserWatchOnObjResp.Builder WXWOORB = WXUserWatchOnObjResp.newBuilder();
    if(CollectionUtils.isEmpty(wxUserWatchOnObjects)){
      responseObserver.onNext(WXWOORB.build());
      responseObserver.onCompleted();
      return;
    }
    for (WXUserWatchOnObject wxUserWatchOnObj: wxUserWatchOnObjects){
      WXUserWatchOnObj.Builder WXUWOOB = WXUserWatchOnObj.newBuilder();
      WXUWOOB.setObjectId(wxUserWatchOnObj.getObjectId());
      WXUWOOB.setType(wxUserWatchOnObj.getType());
      WXWOORB.addWxUserWatchOnObjs(WXUWOOB);
    }
    responseObserver.onNext(WXWOORB.build());
    responseObserver.onCompleted();

  }

  /**
   */
  @Override
  public void getWXUserFavor(com.wenshengamc.zcc.wechat.WXUserReq request,
      io.grpc.stub.StreamObserver<com.wenshengamc.zcc.wechat.WXUserFavor> responseObserver) {
    com.wensheng.zcc.common.module.dto.WXUserFavor wxUserFavor = wxUserService.getUserFavor(request.getOpenId());
    if(wxUserFavor == null){
      log.error("Failed to get user favor for:{}", request.getOpenId());
      responseObserver.onError(new Exception(String.format("Failed to get user favor for:%s", request.getOpenId())));
      return;
    }
    AmcSaleFilter amcSaleFilter = wxUserService.getUserFavor(request.getOpenId()).getAmcSaleFilter();
      WXUserFavor.Builder WXUFB = WXUserFavor.newBuilder();
     if(amcSaleFilter != null){
       WXUFB.setUserFavor(gson.toJson(amcSaleFilter));
     }
     responseObserver.onNext(WXUFB.build());
     responseObserver.onCompleted();
  }

  /**
   */
  @Override
  public void saveWXVistorInfo(com.wenshengamc.zcc.wechat.WXVistorInfo request,
      io.grpc.stub.StreamObserver<com.wenshengamc.zcc.wechat.WXVistorInfo> responseObserver) {
    WechatUserInfo wechatUserInfo = new WechatUserInfo();
    wechatUserInfo.setCity(request.getCity());
    wechatUserInfo.setCountry(request.getCountry());
    wechatUserInfo.setHeadImgUrl(request.getHeadimgurl());
    wechatUserInfo.setLanguage(request.getLanguage());
    wechatUserInfo.setSex(request.getSex());
    wechatUserInfo.setOpenId(request.getOpenid());
    wechatUserInfo.setNickName(request.getNickname());
    wechatUserInfo.setUnionId(request.getUnionid());

    try{
      WechatUserInfo result = wxUserService.saveRpcWXVisitorInfo(wechatUserInfo, request.getAccessToken());
      WXVistorInfo.Builder wxviBuilder = WXVistorInfo.newBuilder();
      AmcBeanUtils.copyProperties(result, wxviBuilder);
      responseObserver.onNext(wxviBuilder.build());
      responseObserver.onCompleted();
    } catch (Exception ex){
      log.error("Failed to save:", ex);
      responseObserver.onError(ex);
    }



  }

}
