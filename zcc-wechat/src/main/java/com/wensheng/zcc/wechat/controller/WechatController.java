package com.wensheng.zcc.wechat.controller;

import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import com.wensheng.zcc.wechat.utils.wechat.AesException;
import com.wensheng.zcc.wechat.service.impl.WXServiceImpl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
  WXServiceImpl wxService;

  @RequestMapping(value = "/check", method = RequestMethod.GET)
  @ResponseBody
  public String checkWechat(@RequestParam("signature") String signature, @RequestParam("echostr") String echostr,
      @RequestParam("nonce") String nonce, @RequestParam("timestamp") Long timeStamp)
      throws ParserConfigurationException, AesException, SAXException, IOException {

    String response = wxService.checkWechatResp(timeStamp, nonce, echostr, signature);
    return response;

  }


  @RequestMapping(value = "/amcid/{amcid}/asset/image/add", headers = "Content-Type= multipart/form-data",method = RequestMethod.POST)
  @ResponseBody
  public List<AssetImage> addAmcAssetImage(
      @RequestParam("assetId") Long assetId, @RequestParam("isToOss") Boolean isToOss,
      @RequestParam("imageClass") Integer tag, @RequestParam("actionId") Long actionId, @RequestParam(value = "description",
      required = false) String description,
      @RequestPart("uploadingImages") MultipartFile[] uploadingImages) throws Exception {
    List<String> filePaths = new ArrayList<>();
//    if(uploadingImages != null && uploadingImages.length >= 3){
//      throw ExceptionUtils.getAmcException(AmcExceptions.LIMTEXCEED_UPLOADFILENUMBER,
//          "upload "+uploadingImages.length + " files at same time");
//    }
    if(assetId == null){
      throw ExceptionUtils.getAmcException(AmcExceptions.MISSING_MUST_PARAM,"amcAssetId missing");
    }

    for(MultipartFile uploadedImage : uploadingImages) {
      try {
        String filePath = amcOssFileService.handleMultiPartFile(uploadedImage, assetId,
            ImagePathClassEnum.ASSET.getName());
        filePaths.add(filePath);
      } catch (Exception e) {
        e.printStackTrace();
        throw ExceptionUtils.getAmcException(AmcExceptions.FAILED_UPLOADFILE2SERVER, e.getMessage());
      }
    }
    String prePath = ImagePathClassEnum.ASSET.getName()+"/"+assetId+"/";

    List<AssetImage> assetImages = new ArrayList<>();
    for(String filePath: filePaths){
      try {
        String ossPath =  amcOssFileService.handleFile2Oss(filePath, prePath);
        AssetImage assetImage = new AssetImage();

        assetImage.setOssPath(ossPath);
        assetImage.setTag(tag);
        assetImage.setOriginalName(filePath);
        assetImage.setAmcAssetId(assetId);
        assetImage.setDescription(description);
        assetImages.add(amcAssetService.saveImageInfo( assetImage));

      } catch (Exception e) {
        e.printStackTrace();
        throw ExceptionUtils.getAmcException(AmcExceptions.FAILED_UPLOADFILE2OSS, e.getMessage());

      }
    }
    return assetImages;
  }



}
