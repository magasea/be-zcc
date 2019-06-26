package com.wensheng.zcc.wechat.controller;

import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.wechat.module.vo.AMCWXMsgResult;
import com.wensheng.zcc.wechat.module.vo.Article;
import com.wensheng.zcc.wechat.module.vo.ArticleVo;
import com.wensheng.zcc.wechat.module.vo.GeneralResp;
import com.wensheng.zcc.wechat.module.vo.MaterialPreviewReq;
import com.wensheng.zcc.wechat.module.vo.MediaUploadResp;
import com.wensheng.zcc.wechat.module.vo.WXMaterialBatch;
import com.wensheng.zcc.wechat.module.vo.WXMaterialCount;
import com.wensheng.zcc.wechat.module.vo.WXMaterialItem;
import com.wensheng.zcc.wechat.module.vo.WXMaterialMod;
import com.wensheng.zcc.wechat.module.vo.WXMaterialPreviewResp;
import com.wensheng.zcc.wechat.module.vo.WXMsgGroupResp;
import com.wensheng.zcc.wechat.module.vo.WXMsgGroupTagReq;
import com.wensheng.zcc.wechat.module.vo.helper.MaterialTypeEnum;
import com.wensheng.zcc.wechat.module.vo.helper.NeedOpenCommentEnum;
import com.wensheng.zcc.wechat.module.vo.helper.OnlyFansCanCommentEnum;
import com.wensheng.zcc.wechat.module.vo.helper.ShowCoverPicEnum;
import com.wensheng.zcc.wechat.service.impl.WXMaterialServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/ws/wechat/material")
public class WechatMaterialController {

  @Autowired
  WXMaterialServiceImpl wxMaterialService;

  @RequestMapping(value = "/addnew", method = RequestMethod.POST)
  @ResponseBody
  public String addNew(@RequestBody List<ArticleVo> articleVos) throws Exception {
    List<Article> articles = new ArrayList<>();
    Article article = null;
    if(!CollectionUtils.isEmpty(articleVos)){
      for(ArticleVo articleVo: articleVos){
        article = new Article();
        AmcBeanUtils.copyProperties(articleVo, article);
          if(articleVo.getNeedOpenComment() != null){
            article.setNeedOpenComment(articleVo.getNeedOpenComment().getId());
          }else{
            article.setNeedOpenComment(NeedOpenCommentEnum.CLOSE.getId());
          }
          if(articleVo.getOnlyFansCanComment() != null ){
            article.setOnlyFansCanComment(articleVo.getOnlyFansCanComment().getId());
          }else{
            article.setOnlyFansCanComment(OnlyFansCanCommentEnum.ONLY_FANS.getId());
          }
          if(articleVo.getShowCoverPic() != null){
            article.setShowCoverPic(articleVo.getShowCoverPic().getId());
          }else{
            article.setShowCoverPic(ShowCoverPicEnum.SHOW.getId());
          }
          articles.add(article);
          article = null;
      }
      return wxMaterialService.addNewMaterial(articles);
    }else{
      return "bad input";
    }
  }


  @RequestMapping(value = "/temp/addnew", method = RequestMethod.POST)
  @ResponseBody
  public MediaUploadResp tempAddNew(@RequestBody List<ArticleVo> articleVos) throws Exception {
    List<Article> articles = new ArrayList<>();
    Article article = null;
    if(!CollectionUtils.isEmpty(articleVos)){
      for(ArticleVo articleVo: articleVos){
        article = new Article();
        AmcBeanUtils.copyProperties(articleVo, article);
        if(articleVo.getNeedOpenComment() != null){
          article.setNeedOpenComment(articleVo.getNeedOpenComment().getId());
        }else{
          article.setNeedOpenComment(NeedOpenCommentEnum.CLOSE.getId());
        }
        if(articleVo.getOnlyFansCanComment() != null ){
          article.setOnlyFansCanComment(articleVo.getOnlyFansCanComment().getId());
        }else{
          article.setOnlyFansCanComment(OnlyFansCanCommentEnum.ONLY_FANS.getId());
        }
        if(articleVo.getShowCoverPic() != null){
          article.setShowCoverPic(articleVo.getShowCoverPic().getId());
        }else{
          article.setShowCoverPic(ShowCoverPicEnum.SHOW.getId());
        }
        articles.add(article);
        article = null;
      }
      return wxMaterialService.addTempMaterial(articles);
    }else{
      return null;
    }
  }


  @RequestMapping(value = "/delMaterial", method = RequestMethod.POST)
  @ResponseBody
  public GeneralResp delMaterial(@RequestParam("mediaId") String mediaId) throws Exception {


    return wxMaterialService.delMaterial(mediaId);

  }

  @RequestMapping(value = "/modMaterial", method = RequestMethod.POST)
  @ResponseBody
  public GeneralResp modMaterial(@RequestBody() WXMaterialMod wxMaterialMod) throws Exception {


    return wxMaterialService.modMaterial(wxMaterialMod);

  }

  @RequestMapping(value = "/uploadImage", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST)
  @ResponseBody
  public String uploadImage(@RequestParam("fileName") String fileName,  @RequestPart MultipartFile imagePart) throws Exception {


    return wxMaterialService.uploadImage(imagePart);

  }

  @RequestMapping(value = "/uploadOtherMaterial", headers = "Content-Type= multipart/form-data", method =
      RequestMethod.POST)
  @ResponseBody
  public MediaUploadResp uploadImage(@RequestParam("fileName") String fileName,
      @RequestParam Integer materialType,
      @RequestParam(value = "title", required =  false) String title,
      @RequestParam(value = "introduction", required = false) String introduction,
      @RequestPart MultipartFile imagePart) throws Exception {


    return wxMaterialService.addMaterial(imagePart, title, introduction,
        MaterialTypeEnum.lookupByDisplayIdUtil(materialType).getName() );

  }

  @RequestMapping(value = "/temp/material/upload", headers = "Content-Type= multipart/form-data", method =
      RequestMethod.POST)
  @ResponseBody
  public MediaUploadResp tempMaterialUpload(@RequestParam("fileName") String fileName,
      @RequestParam Integer materialType,
      @RequestPart MultipartFile imagePart) throws Exception {


    return wxMaterialService.addTempMaterial(imagePart,
        MaterialTypeEnum.lookupByDisplayIdUtil(materialType).getName() );

  }
  @RequestMapping(value = "/temp/material/get", method = RequestMethod.GET)

  public ResponseEntity<byte[]>  tempMaterialGet(@RequestParam("mediaId") String mediaId) throws Exception {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.IMAGE_JPEG);
    httpHeaders.setContentDisposition(ContentDisposition.builder("attachment").filename(mediaId).build());
    byte[] in = wxMaterialService.getTempMaterial(mediaId);
    ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(in, httpHeaders, HttpStatus.OK);
    return responseEntity;

  }

  @RequestMapping(value = "/temp/material/preview", method = RequestMethod.POST)
  @ResponseBody
  public WXMaterialPreviewResp tempMaterialGet(@RequestBody MaterialPreviewReq materialPreviewReq) throws Exception {


    return wxMaterialService.previewMaterial(materialPreviewReq);

  }

  @RequestMapping(value = "/material/batchget", method = RequestMethod.POST)
  @ResponseBody
  public WXMaterialBatch batchGet(@RequestParam("type") Integer type, @RequestParam("offset") Long offset,
      @RequestParam("count") Long count) throws Exception {


    return wxMaterialService.getBatchMaterial(type, offset, count);

  }

  @RequestMapping(value = "/material/get", method = RequestMethod.GET)
  @ResponseBody
  public WXMaterialItem materialGet(@RequestParam("mediaId") String mediaId) throws Exception {


    return wxMaterialService.getMaterial(mediaId);

  }

  @RequestMapping(value = "/material/getcount", method = RequestMethod.POST)
  @ResponseBody
  public WXMaterialCount getMaterialCount() throws Exception {


    return wxMaterialService.getMaterialCount();

  }

  @RequestMapping(value = "/material/msg/send-tag", method = RequestMethod.POST)
  @ResponseBody
  public WXMsgGroupResp sendMsgWithTag(@RequestBody  WXMsgGroupTagReq wxMsgGroupTagReq) throws Exception {


    return wxMaterialService.groupMsgSendWithTag(wxMsgGroupTagReq);

  }

  @RequestMapping(value = "/material/msg/send-openid", method = RequestMethod.POST)
  @ResponseBody
  public WXMsgGroupResp sendMsgWithOpenid(@RequestBody  WXMsgGroupTagReq wxMsgGroupTagReq) throws Exception {


    return wxMaterialService.groupMsgSendWithOpenid(wxMsgGroupTagReq);

  }

  @RequestMapping(value = "/material/msg/del", method = RequestMethod.POST)
  @ResponseBody
  public GeneralResp delGrpMsg(@RequestParam("msgId")Long msgId, @RequestParam("articleIdx") Long articleIdx) throws Exception {


    return wxMaterialService.delGrpMsg(msgId, articleIdx);

  }

  @RequestMapping(value = "/material/msg/query", method = RequestMethod.POST)
  @ResponseBody
  public List<AMCWXMsgResult> querySentMsg() throws Exception {


    return wxMaterialService.querySentMsg();

  }
}
