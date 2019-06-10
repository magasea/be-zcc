package com.wensheng.zcc.wechat.controller;

import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatUser;
import com.wensheng.zcc.wechat.service.impl.WXServiceImpl.TagInfoExt;
import com.wensheng.zcc.wechat.service.impl.WXServiceImpl.UserIdsResp;
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


  @RequestMapping(value = "/public/upload", headers = "Content-Type= multipart/form-data",method = RequestMethod.POST)
  @ResponseBody
  public String addAmcAssetImage(
      @RequestParam("assetId") Long assetId, @RequestParam("isToOss") Boolean isToOss,
      @RequestParam("imageClass") Integer tag, @RequestParam("actionId") Long actionId, @RequestParam(value = "description",
      required = false) String description,
      @RequestPart("uploadingImages") MultipartFile[] uploadingImages) throws Exception {
    List<String> filePaths = new ArrayList<>();

//    if(assetId == null){
//      throw ExceptionUtils.getAmcException(AmcExceptions.MISSING_MUST_PARAM,"amcAssetId missing");
//    }
//
//    for(MultipartFile uploadedImage : uploadingImages) {
//      try {
//        String filePath = amcOssFileService.handleMultiPartFile(uploadedImage, assetId,
//            ImagePathClassEnum.ASSET.getName());
//        filePaths.add(filePath);
//      } catch (Exception e) {
//        e.printStackTrace();
//        throw ExceptionUtils.getAmcException(AmcExceptions.FAILED_UPLOADFILE2SERVER, e.getMessage());
//      }
//    }
//    String prePath = ImagePathClassEnum.ASSET.getName()+"/"+assetId+"/";
//
//    List<AssetImage> assetImages = new ArrayList<>();
//    for(String filePath: filePaths){
//      try {
//        String ossPath =  amcOssFileService.handleFile2Oss(filePath, prePath);
//        AssetImage assetImage = new AssetImage();
//
//        assetImage.setOssPath(ossPath);
//        assetImage.setTag(tag);
//        assetImage.setOriginalName(filePath);
//        assetImage.setAmcAssetId(assetId);
//        assetImage.setDescription(description);
//        assetImages.add(amcAssetService.saveImageInfo( assetImage));
//
//      } catch (Exception e) {
//        e.printStackTrace();
//        throw ExceptionUtils.getAmcException(AmcExceptions.FAILED_UPLOADFILE2OSS, e.getMessage());
//
//      }
//    }
    return "";
  }


  @RequestMapping(value = "/get-token", method = RequestMethod.GET)
  @ResponseBody
  public String getToken()
       {

    String response = wxService.getPublicToken();
    return response;

  }

  @RequestMapping(value = "/get-users", method = RequestMethod.GET)
  @ResponseBody
  public UserIdsResp getUsersIds(@RequestParam(value = "openId", required = false) String openId)
  {

    return  wxService.getWechatPublicUserIds(openId);

  }

  @RequestMapping(value = "/get-usersInfo", method = RequestMethod.GET)
  @ResponseBody
  public List<WechatUser> getUsersInfo(@RequestParam(value = "openIds") List<String> openIds)
  {

    return  wxService.getWechatPublicUserInfo(openIds);

  }

  @RequestMapping(value = "/get-tagInfo", method = RequestMethod.GET)
  @ResponseBody
  public List<TagInfoExt> getTagInfo()
  {

    return  wxService.getWechatPublicUserTag();

  }
  @RequestMapping(value = "/create-user-tag", method = RequestMethod.POST)
  @ResponseBody
  public String createUsersTag(@RequestParam("tagName") String tagName)
  {

     wxService.createWechatPublicUserTag(tagName);
     return "success";

  }

  @RequestMapping(value = "/del-user-tag", method = RequestMethod.POST)
  @ResponseBody
  public String delUsersTag(@RequestParam("tagName") Long wechatTagId)
  {

    wxService.delWechatPublicUserTag(wechatTagId);
    return "success";

  }

  @RequestMapping(value = "/batch-untag", method = RequestMethod.POST)
  @ResponseBody
  public String batchUntag(@RequestParam("openIds") List<String> openIds, @RequestParam("tagId") Long tagId)
  {

    return wxService.untagWechatPublicUserBatch(openIds, tagId);

  }
  @RequestMapping(value = "/batch-tag", method = RequestMethod.POST)
  @ResponseBody
  public String batchTag(@RequestParam("openIds") List<String> openIds, @RequestParam("tagId") Long tagId)
  {

    return wxService.tagWechatPublicUserBatch(openIds, tagId);

  }

  @RequestMapping(value = "/get-usertags", method = RequestMethod.POST)
  @ResponseBody
  public List<Long> getUserTag(@RequestParam("openId") String openId)
  {

    return wxService.getTagOfUser(openId);

  }

  @RequestMapping(value = "/get-usersOfTag", method = RequestMethod.POST)
  @ResponseBody
  public UserIdsResp getUserTag(@RequestParam(value = "openId", required = false) String openId,
      @RequestParam("tagId") Long tagId) throws Exception {

    return wxService.getUsersOfTagId( tagId, openId);

  }
}
