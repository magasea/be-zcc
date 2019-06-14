package com.wensheng.zcc.wechat.controller;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.wechat.module.vo.Article;
import com.wensheng.zcc.wechat.module.vo.ArticleVo;
import com.wensheng.zcc.wechat.module.vo.MediaUploadResp;
import com.wensheng.zcc.wechat.module.vo.WXMaterialBatch;
import com.wensheng.zcc.wechat.module.vo.WXMaterialCount;
import com.wensheng.zcc.wechat.module.vo.helper.MaterialTypeEnum;
import com.wensheng.zcc.wechat.module.vo.helper.NeedOpenCommentEnum;
import com.wensheng.zcc.wechat.module.vo.helper.OnlyFansCanCommentEnum;
import com.wensheng.zcc.wechat.module.vo.helper.ShowCoverPicEnum;
import com.wensheng.zcc.wechat.service.impl.WXMaterialServiceImpl;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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


  @RequestMapping(value = "/material/batchget", method = RequestMethod.POST)
  @ResponseBody
  public WXMaterialBatch uploadImage(@RequestParam("type") Integer type, @RequestParam("offset") Long offset,
      @RequestParam("count") Long count) throws Exception {


    return wxMaterialService.getBatchMaterial(type, offset, count);

  }

  @RequestMapping(value = "/material/getcount", method = RequestMethod.POST)
  @ResponseBody
  public WXMaterialCount getMaterialCount() throws Exception {


    return wxMaterialService.getMaterialCount();

  }


}
