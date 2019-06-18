package com.wensheng.zcc.wechat.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.SerializedName;
import com.wensheng.zcc.amc.utils.ImageUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import com.wensheng.zcc.common.utils.SystemUtils;
import com.wensheng.zcc.wechat.module.vo.Article;
import com.wensheng.zcc.wechat.module.vo.GeneralResp;
import com.wensheng.zcc.wechat.module.vo.MediaUploadResp;
import com.wensheng.zcc.wechat.module.vo.WXMaterialBatch;
import com.wensheng.zcc.wechat.module.vo.WXMaterialCount;
import com.wensheng.zcc.wechat.module.vo.WXMaterialItem;
import com.wensheng.zcc.wechat.module.vo.WXMaterialMod;
import com.wensheng.zcc.wechat.module.vo.helper.MaterialTypeEnum;
import com.wensheng.zcc.wechat.service.WXBasicService;
import com.wensheng.zcc.wechat.service.WXMaterialService;
import com.wensheng.zcc.wechat.service.WXUserService;
import java.io.File;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Service;
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
  @Value("${weixin.material_get_url}")
  String materialGetUrl;
  @Value("${weixin.material_del_url}")
  String materialDelUrl;
  @Value("${weixin.material_update_url}")
  String materialUpdateUrl;
  @Value("${weixin.material_get_count_url}")
  String materialGetCountUrl;

  @Value("${weixin.material_batch_get_url}")
  String materialBatchGetUrl;


  @Value("${project.params.wechat_image_path}")
  String wechatImagePath;


  @Autowired
  WXUserService wxService;

  @Autowired
  WXBasicService wxBasicService;

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

//  public synchronized String uploadImageTest(MultipartFile mediaFile) throws Exception {
//    String urlTest = "http://localhost:10001/api/amcid/7/asset/image/add";
//    StringBuilder sb = new StringBuilder(wechatImagePath);
//    SystemUtils.checkAndMakeDir(sb.toString());
//
//    File imageFile = new File("/home/chenwei/Pictures/Screenshot from 2019-05-13 16-51-21.jpg");
//
//
//    HttpHeaders headers = getHttpMultiFileHeader();
//    MultiValueMap<String, Object> body
//        = new LinkedMultiValueMap<>();
////    FileSystemResource fileSystemResource = new FileSystemResource(imageFile);
//    FileSystemResource fileSystemResource = new FileSystemResource(imageFile);
//
//
//    body.add("uploadingImages", getUserImageFileResource() );
//    body.add("isToOss", 1);
//    body.add("imageClass", 1);
//    body.add("description", "test");
//    body.add("assetId", 98);
//    body.add("actionId", 1);
//
//
//    HttpEntity<Map> entity = new HttpEntity<>(body, headers);
//
//    ResponseEntity response = restTemplate.exchange(urlTest, HttpMethod.POST, entity, String.class);
//
//    System.out.println(response.getBody());
//    return response.getBody().toString();
//
//
//  }
//  public static Resource getUserFileResource() throws IOException {
//    //todo replace tempFile with a real file
//    Path tempFile = Files.createTempFile("upload-test-file", ".txt");
//    Files.write(tempFile, "some test content...\nline1\nline2".getBytes());
//    System.out.println("uploading: " + tempFile);
//    File file = tempFile.toFile();
//    //to upload in-memory bytes use ByteArrayResource instead
//    return new FileSystemResource(file);
//  }
//
//  public static Resource getUserImageFileResource() throws IOException {
//    //todo replace tempFile with a real file
//    File imageFile = new File("/home/chenwei/Pictures/Screenshot from 2019-05-13 16-51-21.jpg");
//    //to upload in-memory bytes use ByteArrayResource instead
//    return new FileSystemResource(imageFile);
//  }



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


  public synchronized MediaUploadResp getMaterial(String mediaId) throws Exception {
    String token = wxBasicService.getPublicToken();
    String url = String.format(materialGetUrl, token);
    Map<String, String> paramMap = new HashMap<>();
    paramMap.put("media_id", mediaId);

    HttpHeaders headers = getHttpJsonHeader();
    HttpEntity<Map> entity = new HttpEntity<>(paramMap, headers);

    ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, WXMaterialBatch.class);
    MediaUploadResp resp = (MediaUploadResp) response.getBody();
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
