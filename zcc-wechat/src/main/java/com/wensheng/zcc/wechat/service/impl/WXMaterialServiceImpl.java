package com.wensheng.zcc.wechat.service.impl;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.wensheng.zcc.amc.utils.ImageUtils;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import com.wensheng.zcc.common.utils.SystemUtils;
import com.wensheng.zcc.wechat.dao.mysql.mapper.WechatMsgCkitemMapper;
import com.wensheng.zcc.wechat.dao.mysql.mapper.WechatMsgMapper;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatMsg;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatMsgCkitem;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatMsgCkitemExample;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatMsgExample;
import com.wensheng.zcc.wechat.module.vo.AMCWXMsgResult;
import com.wensheng.zcc.wechat.module.vo.Article;
import com.wensheng.zcc.wechat.module.vo.GeneralResp;
import com.wensheng.zcc.wechat.module.vo.MaterialPreviewReq;
import com.wensheng.zcc.wechat.module.vo.MediaUploadResp;
import com.wensheng.zcc.wechat.module.vo.WXMsgCPRCheckResult;
import com.wensheng.zcc.wechat.module.vo.WXCopyrightCheckResults;
import com.wensheng.zcc.wechat.module.vo.WXMaterialBatch;
import com.wensheng.zcc.wechat.module.vo.WXMaterialCount;
import com.wensheng.zcc.wechat.module.vo.WXMaterialItem;
import com.wensheng.zcc.wechat.module.vo.WXMaterialMod;
import com.wensheng.zcc.wechat.module.vo.WXMaterialPreviewResp;
import com.wensheng.zcc.wechat.module.vo.WXMsgGroupResp;
import com.wensheng.zcc.wechat.module.vo.WXMsgGroupTagReq;
import com.wensheng.zcc.wechat.module.vo.helper.MaterialTypeEnum;
import com.wensheng.zcc.wechat.service.WXBasicService;
import com.wensheng.zcc.wechat.service.WXMaterialService;
import com.wensheng.zcc.wechat.service.WXUserService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


@Service
@CacheConfig(cacheNames = {"TOKEN"})
@Slf4j
public class WXMaterialServiceImpl implements WXMaterialService {


  @Value("${weixin.material_add_new_url}")
  String materialAddNewUrl;
  @Value("${weixin.material_uploadimage_url}")
  String materialUploadimageUrl;
  @Value("${weixin.material_addmaterial_url}")
  String materialAddmaterialUrl;
  @Value("${weixin.material_add_tmp_url}")
  String materialAddTempUrl;
  @Value("${weixin.material_get_url}")
  String materialGetUrl;
  @Value("${weixin.material_get_tmp_url}")
  String materialGetTempUrl;
  @Value("${weixin.material_preview_url}")
  String materialPreviewUrl;

  @Value("${weixin.material_del_url}")
  String materialDelUrl;
  @Value("${weixin.material_update_url}")
  String materialUpdateUrl;
  @Value("${weixin.material_get_count_url}")
  String materialGetCountUrl;

  @Value("${weixin.material_batch_get_url}")
  String materialBatchGetUrl;

  @Value("${weixin.material_upload_temp_url}")
  String materialUploadTempUrl;

  @Value("${project.params.wechat_image_path}")
  String wechatImagePath;

  @Value(("${weixin.msg_group_send_tag_url}"))
  String msgGroupSendTagUrl;

  @Value("${weixin.msg_group_send_openid_url}")
  String msgGroupSendOpenidUrl;

  @Value("${weixin.msg_group_del_url}")
  String msgGroupDelUrl;

  @Autowired
  WXUserService wxService;

  @Autowired
  WXBasicService wxBasicService;

  @Autowired
  WechatMsgMapper wechatMsgMapper;

  @Autowired
  WechatMsgCkitemMapper wechatMsgCkitemMapper;

//  private Gson gson = new Gson();

  private RestTemplate restTemplate = new RestTemplate();
  @PostConstruct
  void init(){
    GsonBuilder gson = new GsonBuilder();
    restTemplate.getMessageConverters().removeIf(item -> item instanceof MappingJackson2HttpMessageConverter);
    restTemplate.getMessageConverters().removeIf(item -> item instanceof MappingJackson2XmlHttpMessageConverter);
    GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
    gsonHttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON));

    gsonHttpMessageConverter.setGson(gson.create());
//    ByteArrayHttpMessageConverter byteArrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
//    FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
    restTemplate.getMessageConverters().add(gsonHttpMessageConverter);
//    restTemplate.getMessageConverters().add(formHttpMessageConverter);
//    restTemplate.getMessageConverters().add(byteArrayHttpMessageConverter);
//    restTemplate.getMessageConverters().add(new FormHttpMessageConverter());

  }
  public HttpHeaders getHttpJsonHeader(){
    HttpHeaders headers = new HttpHeaders();
    headers.getAccept().clear();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    return headers;
  }

  public HttpHeaders getHttpMultiFileHeader(){
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    return headers;
  }

  public String addNewMaterial(List<Article> articles) throws Exception {
    String token = wxBasicService.getPublicToken();
    String url = String.format(materialAddNewUrl, token);
    HttpHeaders headers = getHttpJsonHeader();
    Map<String, Object> articlesMap = new HashMap<>();

    articlesMap.put("articles", articles);
    HttpEntity<Map> entity = new HttpEntity<>(articlesMap, headers);

    ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, MatAddNewResp.class);
    MatAddNewResp resp = (MatAddNewResp) response.getBody();
    if(resp.getErrcode() != null && resp.getErrcode() != 0){
      throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_WECHAT_PARAMETER, String.format("%s:%s",
          resp.getErrcode(), resp.getErrmsg()));
    }
    return resp.getMediaId();

  }

  public MediaUploadResp addTempMaterial(List<Article> articles) throws Exception {
    String token = wxBasicService.getPublicToken();
    String url = String.format(materialUploadTempUrl, token);
    HttpHeaders headers = getHttpJsonHeader();
    Map<String, Object> articlesMap = new HashMap<>();

    articlesMap.put("articles", articles);
    HttpEntity<Map> entity = new HttpEntity<>(articlesMap, headers);

    ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, MediaUploadResp.class);
    MediaUploadResp resp = (MediaUploadResp) response.getBody();
    if(resp.getErrcode() != null && resp.getErrcode() != 0){
      throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_WECHAT_PARAMETER, String.format("%s:%s",
          resp.getErrcode(), resp.getErrmsg()));
    }
    return resp;
  }

  public synchronized String uploadImage(MultipartFile mediaFile) throws Exception {
    StringBuilder sb = new StringBuilder(wechatImagePath);
    SystemUtils.checkAndMakeDir(sb.toString());



    File imageFile =
        new File(sb.append(File.separatorChar).append(Instant.now().getEpochSecond()).append(".jpg").toString());
    mediaFile.transferTo(imageFile);


    String token = wxBasicService.getPublicToken();
    String url = String.format(materialUploadimageUrl, token);
    HttpHeaders headers = getHttpMultiFileHeader();
    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    Resource resource = new FileSystemResource(imageFile);
    body.add("media", resource);
    HttpEntity<Map> entity = new HttpEntity<>(body, headers);

    ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, MediaUploadResp.class);

    MediaUploadResp resp = (MediaUploadResp) response.getBody();
    if(resp.getErrcode() != null && resp.getErrcode() != 0){
      throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_WECHAT_PARAMETER, String.format("%s:%s",
          resp.getErrcode(), resp.getErrmsg()));
    }



    return resp.getUrl();

  }

  public synchronized WXMsgGroupResp groupMsgSendWithTag(WXMsgGroupTagReq wxMsgGroupTagReq) throws Exception {
    String token = wxBasicService.getPublicToken();
    String url = String.format(msgGroupSendTagUrl, token);
    HttpHeaders headers = getHttpJsonHeader();
    HttpEntity<WXMsgGroupTagReq> entity = new HttpEntity<>(wxMsgGroupTagReq, headers);
    ResponseEntity<WXMsgGroupResp> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity,
        WXMsgGroupResp.class);
    WXMsgGroupResp resp = responseEntity.getBody();

    if(resp.getErrcode() != null && resp.getErrcode() != 0){
      throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_WECHAT_PARAMETER, String.format("%s:%s",
          resp.getErrcode(), resp.getErrmsg()));
    }
    WechatMsg wechatMsg = new WechatMsg();
    wechatMsg.setMsgId(resp.getMsgId());
    wechatMsg.setMediaId(wxMsgGroupTagReq.getMpNews().getMediaId());
    wechatMsgMapper.insertSelective(wechatMsg);
    return resp;
  }


  public synchronized WXMsgGroupResp groupMsgSendWithOpenid(WXMsgGroupTagReq wxMsgGroupTagReq) throws Exception {
    String token = wxBasicService.getPublicToken();
    String url = String.format(msgGroupSendOpenidUrl, token);
    HttpHeaders headers = getHttpJsonHeader();
    HttpEntity<WXMsgGroupTagReq> entity = new HttpEntity<>(wxMsgGroupTagReq, headers);
    ResponseEntity<WXMsgGroupResp> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity,
        WXMsgGroupResp.class);
    WXMsgGroupResp resp = responseEntity.getBody();
    if(resp.getErrcode() != null && resp.getErrcode() != 0){
      throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_WECHAT_PARAMETER, String.format("%s:%s",
          resp.getErrcode(), resp.getErrmsg()));
    }
    return resp;

  }


  public synchronized MediaUploadResp addMaterial(MultipartFile mediaFile, String title, String introduction,
      String type) throws Exception {
    String token = wxBasicService.getPublicToken();
    String url = String.format(materialAddmaterialUrl, token, type);
    MaterialDesc materialDesc = new MaterialDesc();
    materialDesc.setIntroduction(introduction);
    materialDesc.setTitle(title);
    HttpHeaders headers = getHttpMultiFileHeader();
    MultiValueMap<String, Object> body
        = new LinkedMultiValueMap<>();
    StringBuilder sb = new StringBuilder(wechatImagePath);
    SystemUtils.checkAndMakeDir(sb.toString());

    File imageFile =
        new File(sb.append(File.separatorChar).append(Instant.now().getEpochSecond()).toString());
    mediaFile.transferTo(imageFile);
    String ext = ImageUtils.getImageType(imageFile.getAbsolutePath());
    String newFilePath = String.format("%s.%s",imageFile.getAbsolutePath(),
        ext.toLowerCase());
    Files.move(imageFile.toPath(), Paths.get(newFilePath));
    Resource resource = new FileSystemResource(newFilePath);
    body.add("media",  resource);
//    body.add("description", materialDesc);
    HttpEntity<Map> entity = new HttpEntity<>(body, headers);

    ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, MediaUploadResp.class);
    MediaUploadResp resp = (MediaUploadResp) response.getBody();
    if(resp.getErrcode() != null && resp.getErrcode() != 0){
      throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_WECHAT_PARAMETER, String.format("%s:%s",
          resp.getErrcode(), resp.getErrmsg()));
    }
    return resp;

  }

  public synchronized MediaUploadResp addTempMaterial(MultipartFile mediaFile, String type) throws Exception {
    String token = wxBasicService.getPublicToken();
    String url = String.format(materialAddTempUrl, token, type);

    HttpHeaders headers = getHttpMultiFileHeader();
    MultiValueMap<String, Object> body
        = new LinkedMultiValueMap<>();
    StringBuilder sb = new StringBuilder(wechatImagePath);
    SystemUtils.checkAndMakeDir(sb.toString());
    File imageFile =
        new File(sb.append(File.separatorChar).append(Instant.now().getEpochSecond()).toString());
    mediaFile.transferTo(imageFile);
    String ext = ImageUtils.getImageType(imageFile.getAbsolutePath());
    String newFilePath = String.format("%s.%s",imageFile.getAbsolutePath(),
        ext.toLowerCase());
    Files.move(imageFile.toPath(), Paths.get(newFilePath));
    Resource resource = new FileSystemResource(newFilePath);
    body.add("media",  resource);
    body.add("type", type);
    HttpEntity<Map> entity = new HttpEntity<>(body, headers);

    ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, MediaUploadResp.class);
    MediaUploadResp resp = (MediaUploadResp) response.getBody();
    if(resp.getErrcode() != null && resp.getErrcode() != 0){
      throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_WECHAT_PARAMETER, String.format("%s:%s",
          resp.getErrcode(), resp.getErrmsg()));
    }
    return resp;

  }


  public synchronized WXMaterialItem getMaterial(String mediaId) throws Exception {
    String token = wxBasicService.getPublicToken();
    String url = String.format(materialGetUrl, token);
    Map<String, String> paramMap = new HashMap<>();
    paramMap.put("media_id", mediaId);

    HttpHeaders headers = getHttpJsonHeader();
    HttpEntity<Map> entity = new HttpEntity<>(paramMap, headers);

    ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, WXMaterialItem.class);
    WXMaterialItem resp = (WXMaterialItem) response.getBody();
    if(resp.getErrcode() != null && resp.getErrcode() != 0){
      throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_WECHAT_PARAMETER, String.format("%s:%s",
          resp.getErrcode(), resp.getErrmsg()));
    }
    return resp;

  }
  public synchronized WXMaterialBatch getBatchMaterial(int type, Long offset, Long count) throws Exception {
    String token = wxBasicService.getPublicToken();
    String url = String.format(materialBatchGetUrl, token);

    if(count <= 0 ){
      count = 20L;
    }

    Map<String, Object> requestParam = new HashMap<>();
    requestParam.put("type", MaterialTypeEnum.lookupByDisplayIdUtil(type).getName());
    requestParam.put("offset", offset);
    requestParam.put("count", count);
    HttpHeaders headers = getHttpJsonHeader();
    HttpEntity<Map> entity = new HttpEntity<>(requestParam, headers);
    ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, WXMaterialBatch.class);
    WXMaterialBatch resp = (WXMaterialBatch) response.getBody();
    if(resp.getErrcode() != null && resp.getErrcode() != 0){
      throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_WECHAT_PARAMETER, String.format("%s:%s",
          resp.getErrcode(), resp.getErrmsg()));
    }
    return resp;
//    ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
//    Map resp = (Map) response.getBody();
//    System.out.println(resp.toString());
//    return null;
//    if(resp.getErrcode() != null && resp.getErrcode() != 0){
//      throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_WECHAT_PARAMETER, String.format("%s:%s",
//          resp.getErrcode(), resp.getErrmsg()));
//    }


  }

  public WXMaterialCount getMaterialCount() throws Exception {
    String token = wxBasicService.getPublicToken();
    String url = String.format(materialGetCountUrl, token);
    HttpHeaders headers = getHttpJsonHeader();
    HttpEntity<Object> entity = new HttpEntity<>(null, headers);
    ResponseEntity response = restTemplate.exchange(url, HttpMethod.GET, entity, WXMaterialCount.class);
    WXMaterialCount resp = (WXMaterialCount) response.getBody();
    if(resp.getErrcode() != null && resp.getErrcode() != 0){
      throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_WECHAT_PARAMETER, String.format("%s:%s",
          resp.getErrcode(), resp.getErrmsg()));
    }
    return resp;

  }

  public GeneralResp delMaterial(String mediaId) throws Exception {
    String token = wxBasicService.getPublicToken();
    String url = String.format(materialDelUrl, token);
    HttpHeaders headers = getHttpJsonHeader();
    Map<String, String> paramMap = new HashMap<>();
    paramMap.put("media_id", mediaId);
    HttpEntity<Map> entity = new HttpEntity<>(paramMap, headers);
    ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, GeneralResp.class);
    GeneralResp resp = (GeneralResp) response.getBody();
    if(resp.getErrcode() != null && resp.getErrcode() != 0){
      throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_WECHAT_PARAMETER, String.format("%s:%s",
          resp.getErrcode(), resp.getErrmsg()));
    }
    return resp;
  }

  public GeneralResp modMaterial(WXMaterialMod wxMaterialMod) throws Exception {
    String token = wxBasicService.getPublicToken();
    String url = String.format(materialUpdateUrl, token);
    HttpHeaders headers = getHttpJsonHeader();

    HttpEntity<WXMaterialMod> entity = new HttpEntity<>(wxMaterialMod, headers);
    ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, GeneralResp.class);
    GeneralResp resp = (GeneralResp) response.getBody();
    if(resp.getErrcode() != null && resp.getErrcode() != 0){
      throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_WECHAT_PARAMETER, String.format("%s:%s",
          resp.getErrcode(), resp.getErrmsg()));
    }
    return resp;
  }

  public byte[] getTempMaterial(String mediaId) {
    String token = wxBasicService.getPublicToken();
    String url = String.format(materialGetTempUrl, token, mediaId);
    Map<String, String> paramMap = new HashMap<>();


    HttpHeaders headers = getHttpJsonHeader();
    HttpEntity<Map> entity = new HttpEntity<>(paramMap, headers);

    ResponseEntity response = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);
    return (byte[])response.getBody();

  }




  public GeneralResp delGrpMsg(Long msgId, Long articleIdx){
    String token = wxBasicService.getPublicToken();
    String url = String.format(msgGroupDelUrl, token);
    Map<String, Long> paramMap = new HashMap<>();
    paramMap.put("msg_id", msgId);
    if(articleIdx != null && articleIdx > 0){
      paramMap.put("article_idx", articleIdx);
    }
    HttpHeaders headers = getHttpJsonHeader();
    HttpEntity<Map> entity = new HttpEntity<Map>(paramMap, headers);

    ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, GeneralResp.class);
    return (GeneralResp) response.getBody();
  }

  public WXMaterialPreviewResp previewMaterial(MaterialPreviewReq materialPreviewReq){
    String token = wxBasicService.getPublicToken();
    String url = String.format(materialPreviewUrl, token);


    HttpHeaders headers = getHttpJsonHeader();
    HttpEntity<MaterialPreviewReq> entity = new HttpEntity<MaterialPreviewReq>(materialPreviewReq, headers);

    ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, WXMaterialPreviewResp.class);
    return (WXMaterialPreviewResp)response.getBody();
  }
  public String recordMsgResult(String xmlMsg) {
    XmlMapper xmlMapper = new XmlMapper();
    try {
      WXCopyrightCheckResults wxCopyrightCheckResults
          = xmlMapper.readValue(xmlMsg, WXCopyrightCheckResults.class);

      WechatMsg wechatMsg = new WechatMsg();
      AmcBeanUtils.copyProperties(wxCopyrightCheckResults, wechatMsg);
      wechatMsg.setMsgId(wxCopyrightCheckResults.getMsgID());
      WechatMsgExample wechatMsgExample = new WechatMsgExample();
      wechatMsgExample.createCriteria().andMsgIdEqualTo(wechatMsg.getMsgId());
      List<WechatMsg> wechatMsgs = wechatMsgMapper.selectByExample(wechatMsgExample);
      if(CollectionUtils.isEmpty(wechatMsgs)){
        wechatMsgMapper.insertSelective(wechatMsg);
      }else{
        wechatMsg.setId(wechatMsgs.get(0).getId());
        wechatMsgMapper.updateByPrimaryKeySelective(wechatMsg);
      }

      if(CollectionUtils.isEmpty(wxCopyrightCheckResults.getCopyrightCheckResult().getResultList())){
        for(WXMsgCPRCheckResult item  : wxCopyrightCheckResults.getCopyrightCheckResult().getResultList()){
          WechatMsgCkitem wechatMsgCkitem = new WechatMsgCkitem();

          AmcBeanUtils.copyProperties(item, wechatMsgCkitem);
          wechatMsgCkitem.setMsgId(wechatMsg.getMsgId());

          WechatMsgCkitemExample wechatMsgCkitemExample = new WechatMsgCkitemExample();
          wechatMsgCkitemExample.createCriteria().andArticleIdxEqualTo(wechatMsgCkitem.getArticleIdx()).andMsgIdEqualTo(wechatMsg.getMsgId());
          List<WechatMsgCkitem> wechatMsgCkitems = wechatMsgCkitemMapper.selectByExample(wechatMsgCkitemExample);
          if(CollectionUtils.isEmpty(wechatMsgCkitems)){
            wechatMsgCkitemMapper.insertSelective(wechatMsgCkitem);
          }else{
            wechatMsgCkitem.setId(wechatMsgCkitems.get(0).getId());
            wechatMsgCkitemMapper.updateByPrimaryKeySelective(wechatMsgCkitem);
          }
        }
      }


    } catch (IOException e) {
      log.error(String.format("Failed to parse:%s", xmlMsg), e);
      e.printStackTrace();
    }
    return null;

  }
//Todo: update mapper xml to make the query in one time
  public List<AMCWXMsgResult> querySentMsg() {

    List<AMCWXMsgResult> amcwxMsgResults = new ArrayList<>();
    List<WechatMsg> wechatMsgs = wechatMsgMapper.selectByExample(null);
    WechatMsgCkitemExample wechatMsgCkitemExample = null;
    for(WechatMsg wechatMsg: wechatMsgs){
      AMCWXMsgResult amcwxMsgResult = new AMCWXMsgResult();
      amcwxMsgResult.setWechatMsg(wechatMsg);
      wechatMsgCkitemExample = new WechatMsgCkitemExample();
      wechatMsgCkitemExample.createCriteria().andMsgIdEqualTo(wechatMsg.getMsgId());
      amcwxMsgResult.setWechatMsgCkitems(wechatMsgCkitemMapper.selectByExample(wechatMsgCkitemExample));
      amcwxMsgResults.add(amcwxMsgResult);
    }
    return amcwxMsgResults;
  }


  @Data
  class MatAddNewResp extends GeneralResp {
    @SerializedName("media_id")
    String mediaId;

  }



  @Data
  class MaterialDesc {
    String title;
    String introduction;
  }


}
