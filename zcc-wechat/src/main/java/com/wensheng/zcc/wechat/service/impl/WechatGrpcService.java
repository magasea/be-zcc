package com.wensheng.zcc.wechat.service.impl;

import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

import com.wensheng.zcc.wechat.module.vo.MediaUploadResp;
import com.wenshengamc.zcc.wechat.AmcAssetImage;
import com.wenshengamc.zcc.wechat.ImageUploadResult;
import com.wenshengamc.zcc.wechat.UploadImg2WechatReq;
import com.wenshengamc.zcc.wechat.UploadImg2WechatResp;
import com.wenshengamc.zcc.wechat.WechatAssetImage;
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
  WXMaterialServiceImpl wxMaterialService;

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


}
