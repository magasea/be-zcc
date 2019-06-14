package com.wensheng.zcc.wechat.controller;

import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.wechat.module.vo.Article;
import com.wensheng.zcc.wechat.module.vo.ArticleVo;
import com.wensheng.zcc.wechat.module.vo.helper.MaterialTypeEnum;
import com.wensheng.zcc.wechat.module.vo.helper.NeedOpenCommentEnum;
import com.wensheng.zcc.wechat.module.vo.helper.OnlyFansCanCommentEnum;
import com.wensheng.zcc.wechat.module.vo.helper.ShowCoverPicEnum;
import com.wensheng.zcc.wechat.service.impl.WXMaterialServiceImpl;
import java.io.File;
import java.util.ArrayList;
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
@RequestMapping(value = "/ws/wechat/basic-info")
public class WechatBasicInfoController {

  @RequestMapping(value = "/needOpenComment", method = RequestMethod.GET)
  @ResponseBody
  public List<String> getNeedOpenCommentList(){

    List<String> result = new ArrayList<>();
    for(NeedOpenCommentEnum needOpenCommentEnum : NeedOpenCommentEnum.values()){
      result.add(String.format("%d:%s", needOpenCommentEnum.getId(), needOpenCommentEnum.getName()));
    }
    return result;
  }

  @RequestMapping(value = "/onlyFansCanComment", method = RequestMethod.GET)
  @ResponseBody
  public List<String> getOnlyFansCanCommentEnumList(){

    List<String> result = new ArrayList<>();
    for(OnlyFansCanCommentEnum onlyFansCanCommentEnum : OnlyFansCanCommentEnum.values()){
      result.add(String.format("%d:%s", onlyFansCanCommentEnum.getId(), onlyFansCanCommentEnum.getName()));
    }
    return result;
  }

  @RequestMapping(value = "/showCoverPicEnum", method = RequestMethod.GET)
  @ResponseBody
  public List<String> getShowCoverPicEnumList(){

    List<String> result = new ArrayList<>();
    for(ShowCoverPicEnum showCoverPicEnum : ShowCoverPicEnum.values()){
      result.add(String.format("%d:%s", showCoverPicEnum.getId(), showCoverPicEnum.getName()));
    }
    return result;
  }

  @RequestMapping(value = "/materialType", method = RequestMethod.GET)
  @ResponseBody
  public List<String> getMaterialTypes(){

    List<String> result = new ArrayList<>();
    for(MaterialTypeEnum materialTypeEnum : MaterialTypeEnum.values()){
      result.add(String.format("%d:%s", materialTypeEnum.getId(), materialTypeEnum.getName()));
    }
    return result;
  }
}
