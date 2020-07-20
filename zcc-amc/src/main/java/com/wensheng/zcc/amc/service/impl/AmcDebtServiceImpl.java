package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.dao.mysql.mapper.AmcCmpyMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtContactorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcInfoMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcOrigCreditorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.CurtInfoMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.ext.AmcDebtExtMapper;
import com.wensheng.zcc.amc.module.dao.helper.DebtorTypeEnum;
import com.wensheng.zcc.amc.module.dao.helper.HasImageEnum;
import com.wensheng.zcc.amc.module.dao.helper.ImageClassEnum;
import com.wensheng.zcc.amc.module.dao.helper.ImagePathClassEnum;
import com.wensheng.zcc.amc.module.dao.helper.OrderByFieldEnum;
import com.wensheng.zcc.amc.module.dao.helper.PublishStateEnum;
import com.wensheng.zcc.amc.module.dao.helper.QueryParamEnum;
import com.wensheng.zcc.amc.module.dao.helper.SaleFloorLocalTitleEnum;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AmcOperLog;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetAdditional;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetImage;
import com.wensheng.zcc.amc.module.dao.mongo.entity.DebtAdditional;
import com.wensheng.zcc.amc.module.dao.mongo.entity.DebtImage;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCmpy;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCmpyExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtContactor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtorExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcInfo;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditorExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleFloor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.CurtInfo;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.CurtInfoExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.ext.AmcDebtExt;
import com.wensheng.zcc.amc.module.dto.AmcContactorDTO;
import com.wensheng.zcc.amc.module.dto.grpc.WXUserRegionFavor;
import com.wensheng.zcc.amc.module.vo.AmcAssetVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtCreateVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtExtVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtSummary;
import com.wensheng.zcc.amc.module.vo.AmcDebtUploadImg2WXRlt;
import com.wensheng.zcc.amc.module.vo.AmcDebtVo;
import com.wensheng.zcc.amc.module.vo.AmcSaleGetListInPage;
import com.wensheng.zcc.amc.module.vo.AmcSaleGetRandomListInPage;
import com.wensheng.zcc.amc.module.vo.base.BaseActionVo;
import com.wensheng.zcc.amc.service.AmcAssetService;
import com.wensheng.zcc.amc.service.AmcDebtService;
import com.wensheng.zcc.amc.service.AmcDebtpackService;
import com.wensheng.zcc.amc.service.AmcHelperService;
import com.wensheng.zcc.amc.service.AmcOssFileService;
import com.wensheng.zcc.amc.service.RegionService;
import com.wensheng.zcc.amc.service.impl.helper.Dao2VoUtils;
import com.wensheng.zcc.amc.utils.SQLUtils;
import com.wensheng.zcc.common.module.dto.AmcFilterContentDebt;
import com.wensheng.zcc.common.module.dto.Region;
import com.wensheng.zcc.common.params.sso.SSOAmcUser;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.common.utils.AmcDateUtils;
import com.wensheng.zcc.common.utils.AmcNumberUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.AmcExceptions;
import com.wenshengamc.zcc.common.Common.GeoJson;
import com.wenshengamc.zcc.comnfunc.gaodegeo.Address;
import com.wenshengamc.zcc.comnfunc.gaodegeo.ComnFuncServiceGrpc.ComnFuncServiceBlockingStub;
import com.wenshengamc.zcc.wechat.AmcAssetImage;
import com.wenshengamc.zcc.wechat.AmcDebtImage;
import com.wenshengamc.zcc.wechat.DebtImageUploadResult;
import com.wenshengamc.zcc.wechat.UploadDebtImg2WechatReq;
import com.wenshengamc.zcc.wechat.UploadDebtImg2WechatResp;
import com.wenshengamc.zcc.wechat.WechatAssetImage;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * @author chenwei on 1/4/19
 * @project zcc-backend
 */
@Service
@Slf4j
@CacheConfig(cacheNames = {"DEBT"})
public class AmcDebtServiceImpl implements AmcDebtService {

  

  @Autowired
  AmcHelperService amcHelperService;

  @Autowired
  MongoTemplate wszccTemplate;

  @Autowired
  AmcDebtExtMapper amcDebtExtMapper;

  @Autowired
  AmcDebtMapper amcDebtMapper;

  @Autowired
  AmcDebtorMapper amcDebtorMapper;

  @Autowired
  AmcDebtContactorMapper amcDebtContactorMapper;


  @Autowired
  RegionService regionService;



  @Autowired
  AmcCmpyMapper amcCmpyMapper;



  private RestTemplate restTemplate;

  @Autowired
  AmcOrigCreditorMapper amcOrigCreditorMapper;

  @Autowired
  AmcInfoMapper amcInfoMapper;

  @Autowired
  AmcDebtpackService amcDebtpackService;

  @Autowired
  AmcAssetService amcAssetService;

  @Autowired
  AmcOssFileService amcOssFileService;

  @Autowired
  WechatGrpcService wechatGrpcService;

  @Autowired
  AmcSSORpcServiceImpl amcSSORpcService;

  @Autowired
  CurtInfoMapper curtInfoMapper;

  @Autowired
  ComnFuncServiceBlockingStub comnfuncServiceStub;


  @Autowired
  AmcMiscServiceImpl amcMiscService;

  @Autowired


  @Value("${recom.urls.getTopVisited}")
  String getTopVisited;

  @Value("${env.name}")
  String envName;
  
  private final int PAGE_ITEM_SIZE = 15;

  @PostConstruct
  void init(){
    restTemplate = new RestTemplate();
    GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
    gsonHttpMessageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL) );
    restTemplate.getMessageConverters().removeIf(item -> item instanceof MappingJackson2HttpMessageConverter);
    restTemplate.getMessageConverters().removeIf(item -> item instanceof MappingJackson2XmlHttpMessageConverter);
    restTemplate.getMessageConverters().add(gsonHttpMessageConverter);
  }

  @Override
  public synchronized DebtImage  saveImageInfo(String ossPath, String originName, Long debtId, String fileDesc,
      ImageClassEnum imageClass) {
    DebtImage debtImage = new DebtImage();
    debtImage.setDebtId(debtId);
    debtImage.setOriginalName(originName);
    debtImage.setDescription(fileDesc);
    debtImage.setOssPath(ossPath);
    debtImage.setIsToOss(!StringUtils.isEmpty(ossPath));
    debtImage.setTag(imageClass.getId());
    Query query = new Query();
    if(imageClass == ImageClassEnum.MAIN){
      query.addCriteria(Criteria.where("debtId").is(debtId).and("tag").is(ImageClassEnum.MAIN.getId()));
      List<DebtImage> debtImageList =  wszccTemplate.find(query, DebtImage.class);
      if(!CollectionUtils.isEmpty(debtImageList)){

        for(DebtImage debtImageItem: debtImageList){

          try {
            if(!debtImageItem.getOssPath().equals(ossPath)){
              log.info("now need delete history main image");
              amcOssFileService.delFileInOss(debtImageItem.getOssPath());
              wszccTemplate.remove(debtImageItem);
            }

          } catch (Exception e) {
            e.printStackTrace();
            log.error("Failed to del ossFile with osspath:"+ debtImage.getOssPath(), e);
          }

        }
      }
    }
    query = new Query();
    query.addCriteria(Criteria.where("ossPath").is(ossPath).and("debtId").is(debtId));
    List<DebtImage> debtImageList =  wszccTemplate.find(query, DebtImage.class);
    Update update = new Update();

    if(!CollectionUtils.isEmpty(debtImageList)){
      log.info("there is duplicate image, just update it");
      AmcBeanUtils.copyProperties(debtImage, debtImageList.get(0));
      wszccTemplate.save(debtImageList.get(0));
    }else{
      log.info("there is no image, just insert it");
      wszccTemplate.save(debtImage);
    }
    AmcDebt amcDebt = amcDebtMapper.selectByPrimaryKey(debtId);
    if(amcDebt != null){
      amcDebt.setHasImg(HasImageEnum.HASIMAGE.getStatus());
      amcDebtMapper.updateByPrimaryKeySelective(amcDebt);
    }

    return debtImage;
  }

  @Override
  @Transactional
  @CacheEvict(allEntries = true)
  public AmcDebtVo create(AmcDebt amcDebt) {
    amcDebt.setCreatedDate(AmcDateUtils.getCurrentDate());
    CurtInfo curtInfo = curtInfoMapper.selectByPrimaryKey(amcDebt.getCourtId());
    if(curtInfo != null){
      if(!StringUtils.isEmpty(curtInfo.getCurtProvince()) ){
        List<Region> regions = regionService.getRegionByName(curtInfo.getCurtProvince());
        if(!CollectionUtils.isEmpty(regions)){
          amcDebt.setCurtProv(regions.get(0).getId());

        }
      }

      if(!StringUtils.isEmpty(curtInfo.getCurtCity()) ){
        List<Region> regions = regionService.getRegionByName(curtInfo.getCurtCity());
        if(!CollectionUtils.isEmpty(regions)){
          amcDebt.setCurtCity(regions.get(0).getId());
        }
      }
    }

    amcDebtMapper.insertSelective(amcDebt);
    amcDebt.setZccDebtCode(amcMiscService.generateZccDebtCode(amcDebt.getDebtpackId(), amcDebt.getId()));
    amcDebtMapper.updateByPrimaryKeySelective(amcDebt);
    return convertDo2Vo(amcDebt);
  }

  @Override
  public List<AmcDebt> queryByTitle(String debtTitle, Long deptPackId) {

    AmcDebtExample amcDebtExample = new AmcDebtExample();
    AmcDebtExample.Criteria criteria =  amcDebtExample.createCriteria().andTitleEqualTo(debtTitle);
    if(deptPackId != null && deptPackId > 0){
      criteria.andDebtpackIdEqualTo(deptPackId);
    }
    List<AmcDebt> amcDebts =  amcDebtMapper.selectByExample(amcDebtExample);
    return amcDebts;
  }

  @Override
  @Transactional
  @CacheEvict(allEntries = true)
  public int del(Long amcDebtId) {
    AmcDebt amcDebt = amcDebtMapper.selectByPrimaryKey(amcDebtId);
    if(amcDebt != null && (amcDebt.getPublishState() == PublishStateEnum.DRAFT.getStatus()||amcDebt.getPublishState() == PublishStateEnum.NOTCLEAR.getStatus())){
      amcAssetService.del(amcDebtId);
      AmcDebtorExample amcDebtorExample = new AmcDebtorExample();
      amcDebtorExample.createCriteria().andDebtIdEqualTo(amcDebtId);
      amcDebtorMapper.deleteByExample(amcDebtorExample);
      Query queryNormal = new Query();
      queryNormal.addCriteria(Criteria.where("debtId").is(amcDebtId));
      wszccTemplate.remove(queryNormal, DebtAdditional.class);
      Query queryImage = new Query();
      queryImage.addCriteria(Criteria.where("debtId").is(amcDebtId));
      List<DebtImage> debtImages = wszccTemplate.find(queryImage, DebtImage.class);
      for(DebtImage debtImage: debtImages){
        try {
          amcOssFileService.delFileInOss(debtImage.getOssPath());
        } catch (Exception e) {
          e.printStackTrace();
          log.error("Failed to del ossFile with osspath:"+ debtImage.getOssPath(), e);
        }
      }
      wszccTemplate.remove(queryNormal, DebtImage.class);
      amcDebtMapper.deleteByPrimaryKey(amcDebtId);
    }else{
      log.error(String.format("Cannot del not %s debt with id:%s",
          PublishStateEnum.lookupByDisplayStatusUtil(amcDebt.getPublishState()).getName(), amcDebtId));
    }

    return 1;
  }

  @Override
  @CacheEvict(allEntries = true)
  public AmcDebtVo update(AmcDebt amcDebt) {
    AmcDebt amcDebtHistory = amcDebtMapper.selectByPrimaryKey(amcDebt.getId());
    if(!amcDebtHistory.getAmcContactorName().equals(amcDebt.getAmcContactorName()) ||
        !amcDebtHistory.getAmcContactorPhone().equals(amcDebt.getAmcContactorPhone())){
      updateContactor4Assets(amcDebt.getId(), amcDebt.getAmcContactorName(), amcDebt.getAmcContactorPhone());
    }
    int result = amcDebtMapper.updateByPrimaryKeySelective(amcDebt);
    amcAssetService.updateByDebtState(amcDebt.getId(), amcDebt.getPublishState());

    return null;
  }

  private void updateContactor4Assets(Long debtId, String amcContactorName, String amcContactorPhone) {
    amcAssetService.updateContactor4Assets(debtId, amcContactorName, amcContactorPhone);
  }

  @Override
  public AmcDebtVo updatePublishState(AmcDebt amcDebt) {

    amcDebtMapper.updateByPrimaryKeySelective(amcDebt);
    amcAssetService.updateByDebtState(amcDebt.getId(),
        amcDebt.getPublishState());

    return null;


  }

  @Override
  public List<AmcDebt> queryAllByUserId( Long userId) {
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.createCriteria().andUpdateByEqualTo(userId);
    amcDebtExample.or(amcDebtExample.createCriteria().andCreatedByEqualTo(userId));
    List<AmcDebt>  amcDebts =  amcDebtMapper.selectByExample(amcDebtExample);
    return amcDebts;
  }

  private DebtAdditional queryAddtional(Long debtId){
    Query query = new Query();
    query.addCriteria(Criteria.where("amcDebtId").is(debtId));
    List<DebtAdditional> debtAdditionals = wszccTemplate.find(query, DebtAdditional.class);
    return debtAdditionals.get(0);
  }

  private DebtImage queryImage(Long debtId){
    Query query = new Query();
    query.addCriteria(Criteria.where("debtId").is(debtId));
    List<DebtImage> debtImages = wszccTemplate.find(query, DebtImage.class);
    return debtImages.get(0);
  }

  private List<DebtImage> queryImages(List<Long> debtIds){
    Query query = new Query();
    query.addCriteria(Criteria.where("debtId").in(debtIds));
    List<DebtImage> debtImages = wszccTemplate.find(query, DebtImage.class);
    return debtImages;
  }

  @Override
  public AmcDebtExtVo get(Long amcDebtId, boolean needAdditionInfo) throws Exception {

    List<AmcDebtExt> amcDebtExts = amcDebtExtMapper.selectByPrimaryKeyExt(amcDebtId);
    List<SSOAmcUser> ssoUsersByIds = amcSSORpcService.getSSOUsersByIds(
        Arrays.asList(amcDebtExts.get(0).getDebtInfo().getAmcContactorId()));
    if(CollectionUtils.isEmpty(amcDebtExts )){
      throw ExceptionUtils.getAmcException(AmcExceptions.NO_AMCDEBT_AVAILABLE);
    }
    if(!CollectionUtils.isEmpty(ssoUsersByIds)){
      amcDebtExts.get(0).setAmcDebtContactor(ssoUsersByIds.get(0));
    }
    AmcDebtExtVo amcDebtExtVo = new AmcDebtExtVo();

    AmcDebtVo amcDebtVo = Dao2VoUtils.convertDo2Vo(amcDebtExts.get(0).getDebtInfo());







    Query query = new Query();
    query.addCriteria(Criteria.where("amcDebtId").is(amcDebtId));
    if(needAdditionInfo){
      amcDebtVo.setAssetVos(Dao2VoUtils.convertDoList2VoList(amcDebtExts.get(0).getAmcAssets()));
      List<DebtAdditional> debtAdditionals = wszccTemplate.find(query, DebtAdditional.class);
      if(!CollectionUtils.isEmpty(debtAdditionals)){
        amcDebtVo.setDebtAdditional(debtAdditionals.get(0));
      }

    }

    query = new Query();
    query.addCriteria(Criteria.where("debtId").is(amcDebtId).and("tag").is(ImageClassEnum.MAIN.getId()));
    List<DebtImage> debtImages = wszccTemplate.find(query, DebtImage.class);
    if(!CollectionUtils.isEmpty(debtImages)){
      amcDebtVo.setDebtImage(debtImages.get(0));
    }
    amcDebtExtVo.setAmcDebtVo(amcDebtVo);

    Map<Long, AssetAdditional> assetAdditionalMap = getAssetAdditions(amcDebtId);
    Map<Long, AssetImage> assetImageMap = getAssetImgs(amcDebtId);
    for( AmcAssetVo amcAssetVo:  amcDebtExtVo.getAmcDebtVo().getAssetVos()){
      if(assetAdditionalMap.containsKey(amcAssetVo.getId())){
        amcAssetVo.setAssetAdditional(assetAdditionalMap.get(amcAssetVo.getId()));
      }
      if(assetImageMap.containsKey(amcAssetVo.getId())){
        amcAssetVo.setAssetImage(assetImageMap.get(amcAssetVo.getId()));
      }
    }
    AmcInfo amcInfo = getAmcInfo(amcDebtId);

    List<AmcDebtor> amcDebtors = getDebtors(amcDebtId);


    AmcOrigCreditor origCreditor = getOriginCreditor(amcDebtId);



    amcDebtExtVo.setAmcInfos(amcInfo);
    amcDebtExtVo.setAmcDebtors(amcDebtors);
    amcDebtExtVo.setOrigCreditor(origCreditor);
    amcDebtExtVo.setAmcDebtContactor(amcDebtExts.get(0).getAmcDebtContactor());
    return amcDebtExtVo;
  }

  private Map<Long, AssetAdditional> getAssetAdditions(Long amcDebtId) {

    return amcAssetService.getAssetAdditions(amcDebtId);
  }


  private Map<Long, AssetImage> getAssetImgs(Long amcDebtId) {

    return amcAssetService.getAssetImages(amcDebtId);
  }
  @Override
  public List<AmcDebtExtVo> getByIds(List<Long> amcDebtIds) throws Exception {
    List<AmcDebtExtVo> amcDebtExtVos = new ArrayList<>();
    for(Long amcDebtId: amcDebtIds){
      try{
        AmcDebtExtVo amcDebtExtVo = get(amcDebtId, true);
        amcDebtExtVos.add(amcDebtExtVo);
      }catch (Exception ex){
        log.error("Met error when try to get debt by:{}", amcDebtId, ex);
      }
    }
    return amcDebtExtVos;
  }


  @Override
  public List<AmcDebtVo> query(AmcDebt queryCond, int offset, int size) {

    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.createCriteria().andPublishStateNotEqualTo(PublishStateEnum.DELETED.getStatus());
    RowBounds rowBounds = new RowBounds(offset, size);

    List<AmcDebt> amcDebts = amcDebtMapper.selectByExampleWithRowbounds(amcDebtExample, rowBounds);



    if(!CollectionUtils.isEmpty(amcDebts)){
      return doList2VoList(amcDebts);
    }

    return null;
  }

  @Override
  public List<AmcDebtVo> queryBySeqIds(List<Long> debtIds) {

    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.createCriteria().andPublishStateEqualTo(PublishStateEnum.PUBLISHED.getStatus()).andIdIn(debtIds);


    List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);



    if(!CollectionUtils.isEmpty(amcDebts)){
      return doSeqList2VoList(amcDebts, debtIds);
    }

    return null;
  }

  @Override
  public List<AmcDebtVo> getMostVisitedDebts(int num) throws Exception {

    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.createCriteria().andPublishStateEqualTo(PublishStateEnum.PUBLISHED.getStatus());
    StringBuilder stringBuilder = new StringBuilder("has_img desc , visit_count desc limit ");
    stringBuilder.append(" ").append(num).append(" ");
    amcDebtExample.setOrderByClause(stringBuilder.toString());
    List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);
    if(!CollectionUtils.isEmpty(amcDebts)){
      List<AmcDebtVo> amcDebtVos = doList2VoList(amcDebts);
      updateDebtVosWithCurtInfoFixOrder(amcDebtVos);
      return amcDebtVos;
    }

    return null;
  }

  @Override
  public List<AmcDebtVo> getMostVisitedDebtsByRecomm(int num) {
    String url = String.format(getTopVisited, "debt", num);
    List<Long> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Long>>() {
    } ).getBody();
    AmcDebtExample amcDebtExample = new AmcDebtExample();



    if(response != null && CollectionUtils.isEmpty(response) && response.size() >= num){

      amcDebtExample.createCriteria().andPublishStateEqualTo(PublishStateEnum.PUBLISHED.getStatus()).andIdIn(response);

    }else{
      amcDebtExample.createCriteria().andPublishStateEqualTo(PublishStateEnum.PUBLISHED.getStatus());
      StringBuilder stringBuilder = new StringBuilder("has_img desc , visit_count desc limit ");
      stringBuilder.append(" ").append(num).append(" ");
      amcDebtExample.setOrderByClause(stringBuilder.toString());
    }
    List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);
    if(!CollectionUtils.isEmpty(amcDebts)){
      return doList2VoList(amcDebts);
    }
    return null;
  }

  private List<AmcDebtVo> doSeqList2VoList(List<AmcDebt> originList, List<Long> originIds){
    List<AmcDebtVo> amcDebtVos = new ArrayList<>();

    Query query;
    for(AmcDebt amcDebt: originList){
      AmcDebtVo amcDebtVo = convertDo2Vo(amcDebt);

      amcDebtVos.add(amcDebtVo);
    }

    updateDebtVosWithMongo(amcDebtVos);

    return amcDebtVos;

  }

  private List<AmcDebtVo> doList2VoList(List<AmcDebt> originList){
    List<AmcDebtVo> amcDebtVos = new ArrayList<>();

    Query query;
    for(AmcDebt amcDebt: originList){
      AmcDebtVo amcDebtVo = convertDo2Vo(amcDebt);

      amcDebtVos.add(amcDebtVo);
    }

    updateDebtVosWithMongo(amcDebtVos);

    return amcDebtVos;

  }

  private AmcDebtVo convertDo2Vo(AmcDebt amcDebt) {
    AmcDebtVo amcDebtVo = new AmcDebtVo();
    AmcBeanUtils.copyProperties(amcDebt, amcDebtVo);
    if(amcDebt.getBaseAmount() > 0 ){
      amcDebtVo.setBaseAmount(AmcNumberUtils.getDecimalFromLongDiv100(amcDebt.getBaseAmount()));

    }else if( 0 == amcDebt.getBaseAmount()){
      amcDebtVo.setBaseAmount(BigDecimal.ZERO);
    }
    if(amcDebt.getValuation() !=null && amcDebt.getValuation() > 0 ){
      amcDebtVo.setValuation(AmcNumberUtils.getDecimalFromLongDiv100(amcDebt.getValuation()));

    }else if(amcDebt.getValuation() != null &&  0 == amcDebt.getValuation() ){
      amcDebtVo.setValuation(BigDecimal.ZERO);
    }
    if( amcDebt.getBaseAmount() != null && amcDebt.getBaseAmount() > 0 && amcDebt.getInterestAmount() != null &&
            amcDebt.getInterestAmount() > 0 && (amcDebt.getTotalAmount() == null || amcDebt.getTotalAmount () <= 0L)){

    }
    if(amcDebt.getTotalAmount() !=null && amcDebt.getTotalAmount() > 0 ){
      amcDebtVo.setTotalAmount(AmcNumberUtils.getDecimalFromLongDiv100(amcDebt.getTotalAmount()));
    }else if(0 == amcDebt.getTotalAmount()){
      amcDebtVo.setTotalAmount(BigDecimal.ZERO);
    }

    if(amcDebt.getInterestAmount() !=null && amcDebt.getInterestAmount() > 0 ){
      amcDebtVo.setInterestAmount(AmcNumberUtils.getDecimalFromLongDiv100(amcDebt.getInterestAmount()));

    }else if(amcDebt.getInterestAmount() != null && 0 == amcDebt.getInterestAmount()){
      amcDebtVo.setInterestAmount(BigDecimal.ZERO);
    }
//    if(amcDebt.getAmcContactorId() > 0){
//      amcDebtVo.setAmcContactorId(amcHelperService.getAmcDebtContactor(amcDebt.getAmcContactorId()));
//    }
//
//    if(amcDebt.getAmcContactor2Id() != null && amcDebt.getAmcContactor2Id() > 0){
//      amcDebtVo.setAmcContactor2Id(amcHelperService.getAmcDebtContactor(amcDebt.getAmcContactor2Id()));
//    }
    return amcDebtVo;
  }




  private AmcDebtVo convertDo2Vo(AmcDebtExt amcDebtExt) {
    AmcDebtVo amcDebtVo = new AmcDebtVo();
    AmcBeanUtils.copyProperties(amcDebtExt.getDebtInfo(), amcDebtVo);
    if(amcDebtExt.getDebtInfo().getBaseAmount() > 0 ){
      amcDebtVo.setBaseAmount(AmcNumberUtils.getDecimalFromLongDiv100(amcDebtExt.getDebtInfo().getBaseAmount()));

    }else if( 0 == amcDebtExt.getDebtInfo().getBaseAmount() ){
      amcDebtVo.setBaseAmount(BigDecimal.ZERO);
    }
    if(amcDebtExt.getDebtInfo().getValuation() !=null && amcDebtExt.getDebtInfo().getValuation() > 0 ){
      amcDebtVo.setValuation(AmcNumberUtils.getDecimalFromLongDiv100(amcDebtExt.getDebtInfo().getValuation()));

    }else if(0 == amcDebtExt.getDebtInfo().getValuation()){
      amcDebtVo.setValuation(BigDecimal.ZERO);
    }
    if(amcDebtExt.getDebtInfo().getTotalAmount() > 0 ){
      amcDebtVo.setTotalAmount(AmcNumberUtils.getDecimalFromLongDiv100(amcDebtExt.getDebtInfo().getTotalAmount()));

    }else if(0 == amcDebtExt.getDebtInfo().getTotalAmount()){
      amcDebtVo.setTotalAmount(BigDecimal.ZERO);
    }
    if(amcDebtExt.getDebtInfo().getInterestAmount() > 0){
      amcDebtVo.setInterestAmount(AmcNumberUtils.getDecimalFromLongDiv100(amcDebtExt.getDebtInfo().getInterestAmount()));
    }else if(0 == amcDebtExt.getDebtInfo().getInterestAmount()){
      amcDebtVo.setInterestAmount(BigDecimal.ZERO);
    }

    if(CollectionUtils.isEmpty(amcDebtExt.getAmcAssets())){
      return amcDebtVo;
    }
    try {
      amcDebtVo.setAssetVos(Dao2VoUtils.convertDoList2VoList(amcDebtExt.getAmcAssets()));
    } catch (Exception e) {
      log.error("convert asset to assetVo met exception:", e);
    }

    return amcDebtVo;
  }

  private AmcDebtVo convertDoExt2Vo(AmcDebtExt amcDebtExt) throws Exception {
    AmcDebtVo amcDebtVo = convertDo2Vo(amcDebtExt.getDebtInfo());
    amcDebtVo.setAssetVos(Dao2VoUtils.convertDoList2VoList(amcDebtExt.getAmcAssets()));
    amcDebtVo.setDebtImage(queryImage(amcDebtVo.getId()));
    amcDebtVo.setDebtAdditional(queryAddtional(amcDebtVo.getId()));
    return amcDebtVo;

  }



  @Override
  public List<AmcDebtVo> queryAllExt(Long offset, int size, Map<String, Sort.Direction> orderBy,
      Map<String, Object> queryParam) throws Exception {


    AmcDebtExample amcDebtExample = getAmcDebtExampleWithQueryParam(queryParam);
    RowBounds rowBounds = new RowBounds(offset.intValue(), size);
    try{
      amcDebtExample.setOrderByClause(SQLUtils.getOrderBy(orderBy, rowBounds));
    }catch (Exception ex){
      log.error("there is no orderBy params:" + ex.getMessage());
    }
    List<AmcDebt> amcDebtList = amcDebtMapper.selectByExample(amcDebtExample);
    return getDebtVos(amcDebtList);

  }

  private List<AmcDebtVo> getDebtVos(List<AmcDebt> amcDebtList) throws Exception {
    List<Long> debtIds = amcDebtList.stream().map(item -> item.getId()).collect(Collectors.toList());

    Query query = new Query();


    query.addCriteria(Criteria.where("debtId").in(debtIds));
    List<DebtImage> debtImages = wszccTemplate.find(query, DebtImage.class);
    Map<Long, DebtImage> debtImageMap = new HashMap<>();
    debtImages.forEach(item -> debtImageMap.put(item.getDebtId(), item));

    List<AmcDebtVo> amcDebtVos = new ArrayList<>();
    for(AmcDebt amcDebt: amcDebtList){
      AmcDebtVo amcDebtVo = convertDo2Vo(amcDebt);
      if(debtImageMap.containsKey(amcDebt.getId())){
        amcDebtVo.setDebtImage(debtImageMap.get(amcDebt.getId()));
      }


      amcDebtVos.add(amcDebtVo);
    }
    updateDebtVosWithCurtInfoFixOrder(amcDebtVos);
    return amcDebtVos;
  }


  @Override
  public List<AmcDebtVo> queryAllDebt(Long offset, int size, Map<String, Sort.Direction> orderBy) throws Exception {


    AmcDebtExample amcDebtExample = new AmcDebtExample();
    RowBounds rowBounds = new RowBounds(offset.intValue(), size);
    try{
      String orderByStr = SQLUtils.getOrderBy(orderBy, rowBounds);
      amcDebtExample.setOrderByClause(orderByStr);
    }catch (Exception ex){
      log.error("there is no orderBy params:" + ex.getMessage());
    }



    List<AmcDebtExt> amcDebtExtList = amcDebtExtMapper.selectSimpleByExampleExt(amcDebtExample);

    List<AmcDebtVo> amcDebtVos = new ArrayList<>();
    for(AmcDebtExt amcDebtExt: amcDebtExtList){
      amcDebtVos.add(convertDo2Vo(amcDebtExt));
    }

    updateDebtVosWithMongo(amcDebtVos);
    return amcDebtVos;
  }


  private void updateDebtVosWithMongo(List<AmcDebtVo> amcDebtVos){
    Map<Long, AmcDebtVo> amcDebtVosMap = amcDebtVos.stream().collect(
        Collectors.toMap(item-> item.getId(), item -> item));
//    Set<Long> debtIds = amcDebtVos.stream().map(item -> item.getId()).collect(Collectors.toSet());
    Query query = new Query();
    query.addCriteria(Criteria.where("debtId").in(amcDebtVosMap.keySet()));
    List<DebtImage> debtImages = wszccTemplate.find(query, DebtImage.class);
    for(DebtImage debtImage: debtImages){
      if(amcDebtVosMap.containsKey(debtImage.getDebtId())){
        amcDebtVosMap.get(debtImage.getDebtId()).setDebtImage(debtImage);
      }
    }

    List<DebtAdditional> additionals = wszccTemplate.find(query, DebtAdditional.class);
    for(DebtAdditional additional: additionals){
      if(amcDebtVosMap.containsKey(additional.getAmcDebtId())){
        amcDebtVosMap.get(additional.getAmcDebtId()).setDebtAdditional(additional);
      }
    }
  }

  @Override
  public List<AmcDebt> queryByDebtpackId(Long debtPackId) {
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.createCriteria().andDebtpackIdEqualTo(debtPackId);
    List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);
    return amcDebts;
  }

  @Override
  public Long getTotalCount(Map<String, Object> queryParamMap) throws Exception {
    AmcDebtExample amcDebtExample = getAmcDebtExampleWithQueryParam(queryParamMap);
    return amcDebtMapper.countByExample(amcDebtExample);
  }


  @Override
  public boolean isAmcContactexist(Long amcContact1) {
    AmcDebtContactor AmcDebtContactor = amcDebtContactorMapper.selectByPrimaryKey(amcContact1);
    if(AmcDebtContactor == null){
      return false;
    }

    return true;
  }



  @Override
  public AmcDebtor create(AmcDebtor amcDebtor) throws Exception {

    //if the creditor is company, need check if company exists
    if( amcDebtor.getDebtorType() == DebtorTypeEnum.COMPANY.getId() && (amcDebtor.getCompanyId() == null || amcDebtor.getCompanyId() <= 0)){
      //need create company
      if (StringUtils.isEmpty(amcDebtor.getDebtorName())){
        throw ExceptionUtils.getAmcException(AmcExceptions.MISSING_MUST_PARAM,"company name is must");
      }
      AmcCmpyExample amcCmpyExample = new AmcCmpyExample();
      amcCmpyExample.createCriteria().andNameEqualTo(amcDebtor.getDebtorName());
      List<AmcCmpy> amcCmpies = amcCmpyMapper.selectByExample(amcCmpyExample);
      if(!CollectionUtils.isEmpty(amcCmpies)){
        log.error(String.format("the company to create already exists:%s", amcDebtor.getDebtorName()));
        amcDebtor.setCompanyId(amcCmpies.get(0).getId());
      }else{
        AmcCmpy amcCmpy = new AmcCmpy();
        amcCmpy.setName(amcDebtor.getDebtorName());
        amcCmpyMapper.insertSelective(amcCmpy);
        amcDebtor.setCompanyId(amcCmpy.getId());
      }
    }
    boolean gotDuplicate = false;
    try {
      amcDebtor.setId(null);
      amcDebtorMapper.insertSelective(amcDebtor);

      return amcDebtor;
    }
    catch (DataIntegrityViolationException e) {
      log.error(String.format("got duplicate insert for :%s", amcDebtor.getDebtorName()), e);
      if(e.toString().contains("Duplicate")){
        gotDuplicate = true;
      }
    }
    if(gotDuplicate){
      AmcDebtorExample amcDebtorExample = new AmcDebtorExample();
      amcDebtorExample.createCriteria().andDebtorNameEqualTo(amcDebtor.getDebtorName());
      List<AmcDebtor> amcDebtors = amcDebtorMapper.selectByExample(amcDebtorExample);
      return amcDebtors.get(0);
    }else{
      throw ExceptionUtils.getAmcException(AmcExceptions.INSERT_DB_ERROR);
    }

  }

  @Override
  public AmcDebtor update(AmcDebtor debtor) {
    amcDebtorMapper.updateByPrimaryKey(debtor);
    return debtor;
  }

  @Override
  public AmcCmpy create(AmcCmpy amcCmpy) {
    boolean needQuery = false;
    try{
      amcCmpyMapper.insertSelective(amcCmpy);
    }
    catch (Exception e) {
      log.error(" item to insert already exists", e);
      needQuery = true;
    }
    if(needQuery){
      AmcCmpyExample amcCmpyExample = new AmcCmpyExample();
      amcCmpyExample.createCriteria().andNameEqualTo(amcCmpy.getName());
      List<AmcCmpy> amcCmpyHistories = amcCmpyMapper.selectByExample(amcCmpyExample);
      return amcCmpyHistories.get(0);
    }
    return amcCmpy;
  }

  @Override
  public AmcCmpy update(AmcCmpy amcCmpy) {
    amcCmpyMapper.updateByPrimaryKey(amcCmpy);
    return amcCmpy;
  }

  @Override
  @Transactional
  public void connDebt2Debtors(List<AmcDebtor> debtorFrontend, Long debtId) {

    Map<Long, AmcDebtor> debtorMap = new HashMap<>();
    List<AmcDebtor> newMembers = new ArrayList();
    for(AmcDebtor amcDebtor : debtorFrontend){
      amcDebtor.setDebtId(debtId);
      if(amcDebtor.getId() != null && amcDebtor.getId() > 0 ){

        if(debtorMap.containsKey(amcDebtor.getId())){
          log.error("Duplicated amcDebtor id:{} will just use last one", amcDebtor.getId());
        }
        debtorMap.put(amcDebtor.getId(), amcDebtor);
      }else{
        amcDebtor.setId(null);
        newMembers.add(amcDebtor);
      }
    }

    AmcDebtorExample amcDebtorExample = new AmcDebtorExample();
    amcDebtorExample.createCriteria().andDebtIdEqualTo(debtId);
    List<AmcDebtor> amcDebtors = amcDebtorMapper.selectByExample(amcDebtorExample);
    for(AmcDebtor amcDebtor: amcDebtors){
      if(debtorMap.containsKey(amcDebtor.getId())){
        AmcBeanUtils.copyProperties(debtorMap.get(amcDebtor.getId()), amcDebtor);
        amcDebtorMapper.updateByPrimaryKeySelective(amcDebtor);
      }else{
        amcDebtorMapper.deleteByPrimaryKey(amcDebtor.getId());
      }
      debtorMap.remove(amcDebtor.getId());
    }
    if(!CollectionUtils.isEmpty(debtorMap)){
      debtorMap.entrySet().forEach(item -> {
        item.getValue().setId(null);
        newMembers.add(item.getValue());
      });
    }


    for(AmcDebtor amcDebtor: newMembers){
      amcDebtorMapper.insertSelective(amcDebtor);
    }


  }

  private void updateDebtId4Debtors(List<Long> debtorIds, Long debtId){
    if(!CollectionUtils.isEmpty(debtorIds) && debtId > 0){
      AmcDebtorExample amcDebtorExample = new AmcDebtorExample();
      amcDebtorExample.createCriteria().andIdIn(debtorIds);
      AmcDebtor amcDebtor = new AmcDebtor();
      amcDebtor.setDebtId(debtId);
      int count = amcDebtorMapper.updateByExampleSelective(amcDebtor, amcDebtorExample);
      if(count <= 0){
        log.error(String.format("Failed to update debtors by %s", debtorIds.stream().map(Object::toString).collect(
            Collectors.joining(", "))));
      }
    }
  }

//  @Override
//  public void saveDebtDesc(String debtDesc, Long debtId) {
//    DebtAdditional debtAdditional = new DebtAdditional();
//    debtAdditional.setAmcDebtId(debtId);
//    debtAdditional.setDesc(debtDesc);
//    Query query = new Query();
//    query.addCriteria(Criteria.where("amcDebtId").is(debtId.longValue()));
//    List<DebtAdditional> histAdds = wszccTemplate.find(query, DebtAdditional.class);
//    if(CollectionUtils.isEmpty(histAdds)){
//      wszccTemplate.save(debtAdditional);
//    }else{
//      histAdds.get(0).setDesc(debtDesc);
//      wszccTemplate.save(histAdds.get(0));
//    }
//
//
//    return;
//
//  }
  @Override
  public void saveDebtAddition(DebtAdditional debtAdditional, Long debtId) {
    DebtAdditional debtAdditionalLocal = new DebtAdditional();
    AmcBeanUtils.copyProperties(debtAdditional, debtAdditionalLocal);
    debtAdditional.setAmcDebtId(debtId);

    Query query = new Query();
    query.addCriteria(Criteria.where("amcDebtId").is(debtId.longValue()));
    List<DebtAdditional> histAdds = wszccTemplate.find(query, DebtAdditional.class);
    if(CollectionUtils.isEmpty(histAdds)){
      wszccTemplate.save(debtAdditional);
    }else{
      debtAdditionalLocal.set_id(histAdds.get(0).get_id());
      wszccTemplate.save(debtAdditionalLocal);
    }

    return;

  }

  @Override
  public AmcInfo getAmcInfo(Long amcDebtId) throws Exception {
    AmcDebt amcDebt = amcDebtMapper.selectByPrimaryKey(amcDebtId);
    if(null == amcDebt){
      throw ExceptionUtils.getAmcException(AmcExceptions.NO_AMCDEBT_AVAILABLE, amcDebtId.toString());
    }
    AmcInfo amcInfo = amcInfoMapper.selectByPrimaryKey(amcDebt.getAmcId());

    return amcInfo;
  }

  @Override
  public List<AmcDebtor> getDebtors(Long amcDebtId) {
    AmcDebtorExample amcDebtorExample = new AmcDebtorExample();
    amcDebtorExample.createCriteria().andDebtIdEqualTo(amcDebtId);
    List<AmcDebtor> amcDebtors = amcDebtorMapper.selectByExample(amcDebtorExample);


    return amcDebtors;
  }

  @Override
  public AmcOrigCreditor getOriginCreditor(Long amcDebtId) {
    AmcDebt amcDebt = amcDebtMapper.selectByPrimaryKey(amcDebtId);
    AmcOrigCreditor amcOrigCreditor = amcOrigCreditorMapper.selectByPrimaryKey(amcDebt.getOrigCreditorId());

    return amcOrigCreditor;
  }

  @Override
  public List<AmcOrigCreditor> getAllOrigCreditors(int offset, int size, Map<String, Direction> orderByParam)
      throws Exception {
    AmcOrigCreditorExample amcOrigCreditorExample = new AmcOrigCreditorExample();
    amcOrigCreditorExample.createCriteria().andIdGreaterThan(0L);
    amcOrigCreditorExample.setOrderByClause(SQLUtils.getOrderBy(orderByParam));
    RowBounds rowBounds = new RowBounds(offset, size);
    List<AmcOrigCreditor> amcOrigCreditors = amcOrigCreditorMapper.selectByExampleWithRowbounds(amcOrigCreditorExample, rowBounds);
    return amcOrigCreditors;
  }

  @Override
  public Long getCreditorsCount(){

    return amcOrigCreditorMapper.countByExample(null);
  }

  @Override
  public void delImage(DebtImage debtImage) {


    Query query = new Query();
    query.addCriteria(Criteria.where("debtId").is(debtImage.getDebtId()).and("ossPath").is(debtImage.getOssPath()));
    wszccTemplate.findAllAndRemove(query, DebtImage.class);
    AmcDebt amcDebt = new AmcDebt();
    amcDebt.setId(debtImage.getDebtId());
    amcDebt.setHasImg(HasImageEnum.NOIMAGE.getStatus());
    amcDebtMapper.updateByPrimaryKeySelective(amcDebt);

  }


  @Override
  public AmcOrigCreditor createOrigCreditor(AmcOrigCreditor amcOrigCreditor) {
    if (StringUtils.isEmpty(amcOrigCreditor.getBranchName() )){
      String instant = Instant.now().toString();
      String rand = instant.substring(instant.length() - 19);
      amcOrigCreditor.setBranchName(rand);
    }
    amcOrigCreditorMapper.insertSelective(amcOrigCreditor);

    return amcOrigCreditor;
  }

  @Override
  public List<AmcDebtor> getAllUnasignedDebtors(Long offset, int size, int type, Map<String, Direction> orderByParam)
      throws Exception {
    AmcDebtorExample amcDebtorExample = new AmcDebtorExample();
    if(type > 0 ){
      //unasigned Debtor is assigned with debtId as 0L
      amcDebtorExample.createCriteria().andDebtorTypeEqualTo(type).andDebtIdEqualTo(0L);
    }
    amcDebtorExample.setOrderByClause(SQLUtils.getOrderBy(orderByParam));
    return amcDebtorMapper.selectByExample(amcDebtorExample);
  }

  @Override
  public Long getDebtorCount() {
    AmcDebtorExample amcDebtorExample = new AmcDebtorExample();
    return amcDebtorMapper.countByExample(amcDebtorExample);
  }

  @Override
  public List<AmcCmpy> getAllCompanies(Long offset, int size, Map<String, Direction> orderByParam) throws Exception {
    AmcCmpyExample amcCmpyExample = new AmcCmpyExample();
    amcCmpyExample.setOrderByClause(SQLUtils.getOrderBy(orderByParam));
    return amcCmpyMapper.selectByExample(amcCmpyExample);
  }

  @Override
  public Long getTotalCompanyCount() {
    return amcCmpyMapper.countByExample(null);
  }

  @Override
  public Map<String, List<Long>> getAllTitles() {
    List<AmcDebt> amcDebts = amcDebtExtMapper.selectAllTitlesByExample(null);
    Map<String, List<Long>> result = new HashMap<>();
    for(AmcDebt amcDebt: amcDebts){

      if(!result.containsKey(amcDebt.getTitle())){

        result.put(amcDebt.getTitle(), new ArrayList<Long>());
      }
      result.get(amcDebt.getTitle()).add(amcDebt.getId());

    }
    return result;
  }

  @Override
  public AmcDebtSummary getSummaryInfo() {
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.createCriteria().andPublishStateNotEqualTo(PublishStateEnum.DELETED.getStatus()).andBaseAmountIsNotNull();
    List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);
    Long totalBaseAmount = 0L;
    for(AmcDebt amcDebt: amcDebts){
        totalBaseAmount += amcDebt.getBaseAmount();
    }
    amcDebtExample.clear();
    amcDebtExample.createCriteria().andPublishStateEqualTo(PublishStateEnum.PUBLISHED.getStatus());
    List<AmcDebt> amcDebtsPublished = amcDebtMapper.selectByExample(amcDebtExample);
    int totalPublishedDebts = amcDebtsPublished.size();
    Long totalPublishedAssets = amcAssetService.getAssetCountWithDebtIds(amcDebtsPublished.stream().map(item -> item.getId()).collect(Collectors.toUnmodifiableList()));

    BigDecimal totalBaseAmountBigDecimal = AmcNumberUtils.getDecimalFromLongDiv100(totalBaseAmount);
    AmcDebtSummary amcDebtSummary = new AmcDebtSummary();
    amcDebtSummary.setDebtTotalAmount(totalBaseAmountBigDecimal);
    amcDebtSummary.setDebtTotalCount(Long.valueOf(amcDebts.size()));
    if(CollectionUtils.isEmpty(amcDebts)){
      amcDebtSummary.setAssetTotalCount(0L);
      return amcDebtSummary;
    }

    List<Long> amcDebtIds = amcDebts.stream().map(iitem -> iitem.getId()).collect(Collectors.toList());
    Long assetCount = amcAssetService.getAssetCountWithDebtIds(amcDebtIds);
    amcDebtSummary.setAssetTotalCount(assetCount);
    amcDebtSummary.setAssetPublishedCount(totalPublishedAssets);
    amcDebtSummary.setDebtPublishedCount(Long.valueOf(totalPublishedDebts));
    return amcDebtSummary;
  }

//  @Override
//  public void connDebt2Cmpys(List<AmcDebtorCmpy> newCompanies, Long id) {
//    for(AmcDebtorCmpy cmpyItem: newCompanies){
//      AmcDebtor amcDebtor = new AmcDebtor();
//      amcDebtor.setDebtId(id);
//      amcDebtor.setCompanyId(cmpyItem.getCmpyId());
//      amcDebtor.setDebtorType(DebtorTypeEnum.COMPANY.getId());
//      amcDebtor.setRole(cmpyItem.getRole().getId());
//      amcDebtor.setDebtorName(cmpyItem.getName());
//      amcDebtorMapper.insertSelective(amcDebtor);
//    }
//  }
//
//  @Override
//  public void connDebt2Persons(List<AmcDebtorPerson> newPersons, Long id) {
//    for(AmcDebtorPerson personItem: newPersons){
//      AmcDebtor amcDebtor = new AmcDebtor();
//      amcDebtor.setDebtId(id);
//      amcDebtor.setRole(personItem.getRole().getId());
//      amcDebtor.setDebtorName(personItem.getName());
//      amcDebtor.setDebtorType(DebtorTypeEnum.PERSON.getId());
//      amcDebtor.setDebtorName(personItem.getName());
//      amcDebtorMapper.insertSelective(amcDebtor);
//    }
//  }

  @Override
  public AmcDebt getDebt(Long debtId) {
    return amcDebtMapper.selectByPrimaryKey(debtId);
  }

  @Override
  public List<AmcDebt> getDebtByTiltle(String debtTitle) {
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.createCriteria().andTitleEqualTo(debtTitle);
    return amcDebtMapper.selectByExample(amcDebtExample);
  }

  @Override
  public <T> void saveOperLog(BaseActionVo<T> amcDebtVoBaseActionVo, String reviewComment) {
    AmcOperLog amcOperLog = new AmcOperLog();
    amcOperLog.setActionId(amcDebtVoBaseActionVo.getEditActionId());
    amcOperLog.setComment(reviewComment);
    if(amcDebtVoBaseActionVo.getContent() instanceof AmcDebtVo){
      amcOperLog.setDebtId(((AmcDebtVo)amcDebtVoBaseActionVo.getContent()).getId());
      amcOperLog.setUserId(((AmcDebtVo)amcDebtVoBaseActionVo.getContent()).getUpdateBy());
    }else if(amcDebtVoBaseActionVo.getContent() instanceof AmcDebtCreateVo){
      amcOperLog.setDebtId(((AmcDebtCreateVo)amcDebtVoBaseActionVo.getContent()).getId());
      amcOperLog.setUserId(((AmcDebtCreateVo)amcDebtVoBaseActionVo.getContent()).getUpdateBy());
    }else if(amcDebtVoBaseActionVo.getContent() instanceof List){
      for(AmcDebtVo item: (List<AmcDebtVo>)amcDebtVoBaseActionVo.getContent()){
        amcOperLog = new AmcOperLog();
        amcOperLog.setDebtId(item.getId());
        amcOperLog.setUserId(item.getUpdateBy());
        amcOperLog.setActionId(amcDebtVoBaseActionVo.getEditActionId());
        amcOperLog.setDateTime(LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC));
        wszccTemplate.save(amcOperLog);
      }
      return;
    }
    amcOperLog.setDateTime(LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC));
    wszccTemplate.save(amcOperLog);
  }

  @Override
  public List<AmcOperLog> getOperLog(Long debtId, Integer actionId) {
    Query query = new Query();
    Criteria criteria = Criteria.where("debtId").is(debtId);

    if(actionId > 0){
      criteria.and("actionId").is(actionId);
    }
    query.addCriteria(criteria);
    return wszccTemplate.find(query, AmcOperLog.class);
  }

  @Override
  public void setRecomm(List<Long> debtIds, int id) {
    AmcDebt amcDebt = new AmcDebt();
    amcDebt.setIsRecommanded(id);
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.createCriteria().andIdIn(debtIds);
    amcDebtMapper.updateByExampleSelective(amcDebt, amcDebtExample);
  }

  @Override
  @Cacheable
  public List<Long> getDebtIdsByPackIds(List<Long> debtPackIds){
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    if(debtPackIds.size() > 1){
      amcDebtExample.createCriteria().andDebtpackIdIn(debtPackIds);
    }else{
      amcDebtExample.createCriteria().andDebtpackIdEqualTo(debtPackIds.get(0));
    }

    List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);
    if(CollectionUtils.isEmpty(amcDebts)){
      return new ArrayList<>();
    }
    return amcDebts.stream().map(item -> item.getId()).collect(Collectors.toList());
  }

  @Override
  public List<AmcDebtUploadImg2WXRlt> uploadAmcDebtImage2WechatByIds(List<Long> debtIds) {



    Query query = new Query();
    query.addCriteria(Criteria.where("debtId").in(debtIds));
    List<DebtImage> debtImages = wszccTemplate.find(query, DebtImage.class);
    Map<Long, Map<Long, List<AssetImage>>> assetImageMap = amcAssetService.getAssetImgsByDebtIds(debtIds);
    Map<Long, List<String>> debtImageMap = new HashMap<>();
    for(DebtImage debtImage: debtImages){
      if(!debtImageMap.containsKey(debtImage.getDebtId())){
        debtImageMap.put(debtImage.getDebtId(), new ArrayList<String>());
      }
      debtImageMap.get(debtImage.getDebtId()).add(debtImage.getOssPath());
    }
    for(Long debtId : debtIds){
      if(!debtImageMap.containsKey(debtId)){
        log.error("Havent found image for debtId:{}", debtId);

        debtImageMap.put(debtId, new ArrayList<>());
      }
    }
//    for(Map.Entry<Long, Map<Long,List<AssetImage>>> entry: assetImageMap.entrySet()){
//      if(!debtImageMap.containsKey(entry.getKey())){
//        debtImageMap.put(entry.getKey(), new ArrayList<String>());
//      }
//      if(CollectionUtils.isEmpty(entry.getValue())){
//        entry.getValue().forEach( item -> debtImageMap.get(entry.getKey()).add(item.getOssPath()));
//      }
//
//    }

    UploadDebtImg2WechatReq.Builder builder = UploadDebtImg2WechatReq.newBuilder();

    Iterator it = debtImageMap.entrySet().iterator();
    AmcDebtImage.Builder imagesBuilder = AmcDebtImage.newBuilder();
    for (Map.Entry<Long, List<String>> pair: debtImageMap.entrySet()) {

      imagesBuilder.clear();
      imagesBuilder.setAmcDebtId(pair.getKey());
      if(!CollectionUtils.isEmpty(pair.getValue())){
        imagesBuilder.setAmcDebtMainImage(pair.getValue().get(0));
      }

      if(assetImageMap.containsKey(pair.getKey())){
        Map<Long, List<AssetImage>> assetImages = assetImageMap.get(pair.getKey());

        if(!CollectionUtils.isEmpty(assetImages)){
          AmcAssetImage.Builder assetImageBuilder =  AmcAssetImage.newBuilder();
          for(Map.Entry<Long, List<AssetImage>> entry: assetImages.entrySet()){
            assetImageBuilder.clear();
            assetImageBuilder.setAmcAssetId(entry.getKey());
            if(!CollectionUtils.isEmpty(entry.getValue())){
              List<String> assetImageUrls = entry.getValue().stream().map(item -> item.getOssPath()).collect(Collectors.toList());
              assetImageBuilder.addAllAmcAssetImages(assetImageUrls);
              imagesBuilder.addAmcAssetImages(assetImageBuilder);
            }

          }

        }
      }



      builder.addAmcDebtImages(imagesBuilder);



    }
    UploadDebtImg2WechatResp resp = wechatGrpcService.uploadDebtImage2Wechat(builder.build());
    List<AmcDebtUploadImg2WXRlt> result = new ArrayList<AmcDebtUploadImg2WXRlt>();

    Query querySubItem = null ;
    for(DebtImageUploadResult item: resp.getResultsList()){
      Long debtId = item.getAmcDebtId();
      AmcDebtUploadImg2WXRlt amcDebtUploadImg2WXRlt = new AmcDebtUploadImg2WXRlt();
      amcDebtUploadImg2WXRlt.setDebtId(debtId);
      amcDebtUploadImg2WXRlt.setMediaId(item.getDebtMediaId());
      amcDebtUploadImg2WXRlt.setWechatUrl(item.getDebtMediaIdUrl());
      amcDebtUploadImg2WXRlt.setAssetImgs(new HashMap<>());

      if(StringUtils.isEmpty(item.getAmcDebtImageUrl())){
        log.error("no image for this debtId:{}", debtId);
      }



      querySubItem = new Query();
      querySubItem.addCriteria(Criteria.where("debtId").is(debtId).and("ossPath").is(item.getAmcDebtImageUrl()));

      List<DebtImage> debtImageList = wszccTemplate.find(querySubItem, DebtImage.class);
      if(CollectionUtils.isEmpty(debtImageList)){
        log.error("failed to find image for debtId:{} and ossPath:{}", debtId,
            item.getAmcDebtImageUrl());
      }else{
        debtImageList.get(0).setWechatPath(item.getDebtMediaIdUrl());
        debtImageList.get(0).setMediaId(item.getDebtMediaId());
        wszccTemplate.save(debtImageList.get(0));
      }


        for(WechatAssetImage subItem: item.getWechatAssetImagesList()){
          if(!StringUtils.isEmpty(subItem.getAmcAssetImage())){
            querySubItem = new Query();
            querySubItem.addCriteria(Criteria.where("amcAssetId").is(subItem.getAmcAssetId()).and("ossPath").is(subItem.getAmcAssetImage()));

            List<AssetImage> assetImageList = wszccTemplate.find(querySubItem, AssetImage.class);
            if(CollectionUtils.isEmpty(assetImageList)){
              log.error("failed to find image for amcAssetId:{} and ossPath:{}", subItem.getAmcAssetId(), subItem.getAmcAssetImage());
            }else{
              assetImageList.get(0).setWechatPath(subItem.getWechatAssetImage());
//                     if( ImageClassEnum.MAIN.getId() == assetImageList.get(0).getTag() && !StringUtils.isEmpty(item.getMediaId())){
//                         assetImageList.get(0).setMediaId(item.getMediaId());
//                     }
              wszccTemplate.save(assetImageList.get(0));
              if( !amcDebtUploadImg2WXRlt.getAssetImgs().containsKey(subItem.getAmcAssetId())){
                amcDebtUploadImg2WXRlt.getAssetImgs().put(subItem.getAmcAssetId(), new ArrayList<>());
              }
              amcDebtUploadImg2WXRlt.getAssetImgs().get(subItem.getAmcAssetId()).add(subItem.getWechatAssetImage());
            }
          }

        }

        result.add(amcDebtUploadImg2WXRlt);



    }

    return result;
  }

  @Override
  @Scheduled(cron = "${spring.task.scheduling.cronExpr}")
  public void searchGeoInfoForDebtByCourt() {
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.setOrderByClause("id desc");
    int offset = 0;
    int pageSize = 20;
    Long count = amcDebtMapper.countByExample(null);
    RowBounds rowBounds = null;
    Map<Long, Long> debt2Court = new HashMap<>();
    for(;offset  < count + pageSize;){
      rowBounds = new RowBounds(offset, pageSize);
      List<AmcDebt> amcDebts = amcDebtMapper.selectByExampleWithRowbounds(amcDebtExample, rowBounds);
      if(!CollectionUtils.isEmpty(amcDebts)){
        offset += pageSize;
      }else{
        break;
      }
      debt2Court.clear();
      amcDebts.forEach(item -> debt2Court.put(item.getId(), item.getCourtId()));
      handleCourtGeoInfo(debt2Court);
    }



  }


  @Override
  public List<AmcDebtVo> queryNearByDebtsWithLimit(GeoJsonPoint geoJsonPoint, int limit) throws Exception {


    List<AmcDebtVo> amcDebtVos = new ArrayList<>();
    int stepLimit = 60;
    int initR = 0;
    int rStep = 100;
    int stepIdx = 0;
    int circleLowR = initR;
    int circleHighR = initR+rStep;
    while(amcDebtVos.size() < limit && stepIdx < stepLimit ){

      initR += initR + rStep;
      stepIdx++;
      List<AmcDebtVo> amcDebtVoList = queryNearByDebts(geoJsonPoint, new Integer[]{circleLowR, circleHighR});

      circleLowR = initR;
      circleHighR = initR + rStep;
      if(!CollectionUtils.isEmpty(amcDebtVoList)){
        amcDebtVos.addAll(amcDebtVos.size(), amcDebtVoList);
      }
    }




    return amcDebtVos;
    
    

  }

  private List<AmcDebtVo> queryNearByDebts(GeoJsonPoint geoJsonPoint, Integer[] distances) throws Exception {
    if(distances == null || distances.length < 1){
      throw ExceptionUtils.getAmcException(AmcExceptions.MISSING_MUST_PARAM, "invalid distances params");
    }
    List<AmcDebtVo> amcDebtVos = new ArrayList<>();

    NearQuery nearQuery = null;
    if(distances.length == 1){
      nearQuery = NearQuery.near(geoJsonPoint).inKilometers().minDistance(distances[0]);
    }else{
      nearQuery = NearQuery.near(geoJsonPoint).inKilometers().maxDistance(distances[1]).minDistance(distances[0]);
    }
    GeoResults<DebtAdditional> debtAdditionalGeoResults = wszccTemplate.geoNear(nearQuery, DebtAdditional.class);
    if(debtAdditionalGeoResults != null && ( distances.length == 2 ?
        debtAdditionalGeoResults.getAverageDistance().getValue() < distances[1] :
        debtAdditionalGeoResults.getAverageDistance().getValue() > distances[0])){
      List<Long> debtIds =
          debtAdditionalGeoResults.getContent().stream().map(item -> item.getContent().getAmcDebtId()).collect(
              Collectors.toList());
      amcDebtVos.addAll(getDebtByIds(debtIds));
    }
    return amcDebtVos;
  }

  private List<AmcDebtVo> getDebtByIds(List<Long> debtIds) throws Exception {
    List<AmcDebtVo> amcDebtVos = new ArrayList<>();
    if(CollectionUtils.isEmpty(debtIds)){
      return amcDebtVos;
    }
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.createCriteria().andIdIn(debtIds).andPublishStateEqualTo(PublishStateEnum.PUBLISHED.getStatus());
    amcDebtExample.setOrderByClause(" has_img desc , id desc ");
    List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);
    amcDebtVos = getDebtVos(amcDebts);
    return amcDebtVos;

  }


  @Override
  public List<AmcDebtVo> queryAllNearByDebts(GeoJsonPoint geoJsonPoint, Long[] distances) throws Exception {
    Long shortDistance = 0L;
    Long longDistance = 1000L;
    if(distances != null && distances.length >= 2){
      if(distances[0].compareTo(distances[1]) > 0){
        shortDistance = distances[1];
        longDistance = distances[0];
      }else{
        longDistance = distances[1];
        shortDistance = distances[0];
      }
    }


    NearQuery nearQuery =  NearQuery.near(geoJsonPoint).minDistance(new Distance(shortDistance, Metrics.KILOMETERS)).maxDistance(new Distance(longDistance,
        Metrics.KILOMETERS)).inKilometers();
    GeoResults<DebtAdditional> debtAdditionalGeoResults = wszccTemplate.geoNear(nearQuery, DebtAdditional.class);
    List<AmcDebtVo> amcDebtVos = new ArrayList<>();
    List<Long> debtIds = debtAdditionalGeoResults.getContent().stream().map(item->item.getContent().getAmcDebtId()).collect(
        Collectors.toUnmodifiableList());
    if(CollectionUtils.isEmpty(debtIds)){
      log.error("There is no nearby debts");
      return new ArrayList<>();
    }
    List<AmcDebtVo> amcDebtVosQuery = getByIdsSimpleWithoutAddition(debtIds);
    Map<Long, AmcDebtVo> amcDebtVoMap = new HashMap<>();
    amcDebtVosQuery.forEach(item-> amcDebtVoMap.put(item.getId(), item));
    for(GeoResult<DebtAdditional> geoResult: debtAdditionalGeoResults.getContent()) {
      try {

        AmcDebtVo amcDebtVoFinal = amcDebtVoMap.get(geoResult.getContent().getAmcDebtId());
        if(amcDebtVoFinal == null){
          continue;
        }
        if(geoResult.getContent() != null){
          amcDebtVoFinal.setDebtAdditional(geoResult.getContent());
        }
        amcDebtVos.add(amcDebtVoFinal);
      } catch (Exception e) {
        log.error("Failed to get amcDebtExtVo ", e);
      }
    }
    return amcDebtVos;
  }

  @Override
  @Cacheable
  public List<AmcDebt> getDebtSimpleByIds(List<Long> debtIds) {
    if(CollectionUtils.isEmpty(debtIds)){
      return new ArrayList<>();
    }
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.createCriteria().andIdIn(debtIds).andPublishStateNotEqualTo(PublishStateEnum.DELETED.getStatus());;
    List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);
    return amcDebts;
  }

  @Override
  public List<AmcDebtVo> getByIdsSimpleWithoutAddition(List<Long> debtIds) throws Exception {
    if(CollectionUtils.isEmpty(debtIds)){
      return new ArrayList<>();
    }
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.createCriteria().andIdIn(debtIds);
    List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);
    Map<Long, Long> amcDebtId2ssoUserIdMap = amcDebts.stream().collect(Collectors.toMap(item->item.getId(), item->item.getAmcContactorId()));
    Set<Long> ssoUserIdsSet = amcDebtId2ssoUserIdMap.values().stream().collect(Collectors.toSet());
    List<SSOAmcUser> ssoAmcUsers = amcSSORpcService.getSSOUsersByIds(new ArrayList(ssoUserIdsSet));
    Map<Long, SSOAmcUser> ssoAmcUserMap = ssoAmcUsers.stream()
        .collect(Collectors.toMap(item -> item.getId(), item -> item));
    List<DebtImage> debtImages = queryImages(debtIds);
    Map<Long, DebtImage> debtImageMap = new HashMap<>();
    debtImages.forEach(item -> debtImageMap.put(item.getDebtId(), item));
    List<AmcDebtVo> amcDebtVos = new ArrayList<>();
    for(AmcDebt amcDebt: amcDebts){
      AmcDebtVo amcDebtVo = convertDo2Vo(amcDebt);
      if(debtImageMap.containsKey(amcDebtVo.getId())){
        amcDebtVo.setDebtImage(debtImageMap.get(amcDebtVo.getId()));
      }
      if(ssoAmcUserMap.containsKey(amcDebtVo.getAmcContactorId())){
        amcDebtVo.setDebtContactor(ssoAmcUserMap.get(amcDebtVo.getAmcContactorId()));
      }
      amcDebtVos.add(amcDebtVo);

    }
    updateDebtVosWithCurtInfoFixOrder(amcDebtVos);
    return amcDebtVos;
  }

  @Override
  public List<AmcDebt> getDebtSimpleByTitleLike(String title) {
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    StringBuilder sb = new StringBuilder("%");
    sb.append(title).append("%");
    amcDebtExample.createCriteria().andPublishStateEqualTo(PublishStateEnum.PUBLISHED.getStatus()).andTitleLike(sb.toString()).andPublishStateNotEqualTo(PublishStateEnum.DELETED.getStatus());
    List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);
    return amcDebts;
  }

  @Override
  public List<AmcDebtVo> getDebtsByTitleLike(String title) throws Exception {
    List<AmcDebt> amcDebts = getDebtSimpleByTitleLike(title);
    return getDebtVos(amcDebts);
  }

  @Override
  public Long getDebtIdByTitle(String title) throws Exception {
    AmcDebtExample amcDebtExample = new AmcDebtExample();

    amcDebtExample.createCriteria().andTitleEqualTo(title);
    List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);
    if(CollectionUtils.isEmpty(amcDebts)){
      return -1L;
    }else if(amcDebts.size() > 1){
      throw ExceptionUtils.getAmcException(AmcExceptions.DUPLICATE_ITEM_ERROR,
              String.format("there is :%s debt with the same debtTitle:%s", amcDebts.size(), title));
    }else{
      return amcDebts.get(0).getId();
    }
  }

  @Override
  public DebtImage uploadDebtImage(String imagePath, String ossPrepath, Long debtId, String desc) throws Exception {
//    String prePath = ImagePathClassEnum.DEBT.getName() + "/" + debtId + "/";
    String ossPath = null;
    try {
      ossPath = amcOssFileService.handleFile2Oss(imagePath, ossPrepath);

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    try{
      return saveImageInfo(ossPath, imagePath, debtId, desc, ImageClassEnum.MAIN);
    }catch (Exception e) {
      e.printStackTrace();
      if(e.getMessage().contains("duplicate")){
        throw ExceptionUtils.getAmcException(AmcExceptions.DUPLICATE_IMAGE_ERROR, ossPath);
      }
      throw e;
    }

  }

  @Override
  public String getDebtOssPrePath(Long debtId) {
    String prePath = new StringBuilder(ImagePathClassEnum.DEBT.getName()).append("/").append(envName).append("/").
            append(debtId).append( "/").toString();
    return prePath;
  }

  @Override
  public List<AmcContactorDTO> getDebtContactorByDebtIds(List<Long> debtIds) {
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.createCriteria().andIdIn(debtIds);
    List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);
    List<AmcContactorDTO> amcContactorDTOS = new ArrayList<>();
    for(AmcDebt amcDebt: amcDebts){
      AmcContactorDTO amcContactorDTO = new AmcContactorDTO();
      amcContactorDTO.setContactorName(amcDebt.getAmcContactorName());
      amcContactorDTO.setDebtId(amcDebt.getId());
      amcContactorDTO.setContactorPhone(amcDebt.getAmcContactorPhone());
      amcContactorDTOS.add(amcContactorDTO);
    }
    return amcContactorDTOS;
  }

  @Override
  public SSOAmcUser getDebtContactorByDebtId(Long debtId) {

    AmcDebt amcDebt = amcDebtMapper.selectByPrimaryKey(debtId);
    if(amcDebt.getAmcContactorId() < 0){
      return null;
    }else{
      List<SSOAmcUser> ssoUsersByIds = amcSSORpcService
          .getSSOUsersByIds(Arrays.asList(amcDebt.getAmcContactorId()));
      if(CollectionUtils.isEmpty(ssoUsersByIds)){
        return null;
      }else {
        return ssoUsersByIds.get(0);
      }
    }

  }

  @Override
  public List<AmcDebtVo> getFloorFilteredDebt(AmcFilterContentDebt filterDebt) throws Exception {
    AmcDebtExample amcDebtExample = getAmcDebtExampleWithFloorFilter(filterDebt);
    List<AmcDebtExt> amcDebtExts = amcDebtExtMapper.selectSimpleByExampleExt(amcDebtExample);
    List<AmcDebtVo> amcDebtVos = new ArrayList<>();
    for(AmcDebtExt amcDebtExt: amcDebtExts){
      amcDebtVos.add(convertDo2Vo(amcDebtExt));
    }

    updateDebtVosWithMongoFixOrder(amcDebtVos);
    updateDebtVosWithCurtInfoFixOrder(amcDebtVos);
    return amcDebtVos;
  }

  @Override
  public List<AmcDebtVo> getFloorFilteredDebt(AmcFilterContentDebt filterDebt,
      AmcSaleGetListInPage pageInfo) throws Exception {
    AmcDebtExample amcDebtExample = getAmcDebtExampleWithFloorFilter(filterDebt, pageInfo);
    List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);
    List<AmcDebtVo> amcDebtVos = new ArrayList<>();
    for(AmcDebt amcDebt: amcDebts){
      amcDebtVos.add(convertDo2Vo(amcDebt));
    }

    updateDebtVosWithMongoFixOrder(amcDebtVos);
    updateDebtVosWithCurtInfoFixOrder(amcDebtVos);
    return amcDebtVos;
  }

  @Override
  public List<Long> getLatestIds() {

    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.createCriteria().andPublishStateEqualTo(PublishStateEnum.PUBLISHED.getStatus());
    amcDebtExample.setOrderByClause("has_img desc , id desc limit 5");
    List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);

    return amcDebts.stream().map(item-> item.getId()).collect(Collectors.toList());
  }

  @Override
  public List<AmcDebtVo> getFloorFilteredRandomDebts(AmcFilterContentDebt filterDebt,
      AmcSaleGetRandomListInPage pageInfo) throws Exception {
    AmcDebtExample amcDebtExample = getAmcDebtExampleWithFloorRandomFilter(filterDebt, pageInfo);
    List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);
    List<AmcDebtVo> amcDebtVos = new ArrayList<>();
    for(AmcDebt amcDebt: amcDebts){
      amcDebtVos.add(convertDo2Vo(amcDebt));
    }

    updateDebtVosWithMongoFixOrder(amcDebtVos);
    updateDebtVosWithCurtInfoFixOrder(amcDebtVos);
    return amcDebtVos;
  }

  @Override
  public void patchDebtClue(String cellDebtTitle, String cellDebtClue) throws Exception {
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.createCriteria().andTitleEqualTo(cellDebtTitle);
    List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);
    if(CollectionUtils.isEmpty(amcDebts)){
      throw ExceptionUtils.getAmcException(AmcExceptions.MISSING_MUST_PARAM, String.format("%s not found",cellDebtTitle));
    }

    if(amcDebts.size() > 1){
      throw ExceptionUtils.getAmcException(AmcExceptions.MISSING_MUST_PARAM, String.format("%s found more than one:%s",
          cellDebtTitle, amcDebts.stream().map(item->item.getId().toString()).reduce("", String::concat)));
    }
    Long debtId = amcDebts.get(0).getId();
    Query query = new Query();
    query.addCriteria(Criteria.where("amcDebtId").is(debtId));
    List<DebtAdditional> debtAdditionals = wszccTemplate.find(query, DebtAdditional.class);
    DebtAdditional debtAdditional = null;
    if(CollectionUtils.isEmpty(debtAdditionals)){
      debtAdditional = new DebtAdditional();
      debtAdditional.setAmcDebtId(debtId);
    }else{
      debtAdditional = debtAdditionals.get(0);
    }
    debtAdditional.setDebtClue(cellDebtClue);

    wszccTemplate.save(debtAdditional);

  }

  @Override
  public void patchDebtCourt(String cellDebtTitle, Long courtId) throws Exception {
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.createCriteria().andTitleEqualTo(cellDebtTitle);
    List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);
    if(CollectionUtils.isEmpty(amcDebts)){
      throw ExceptionUtils.getAmcException(AmcExceptions.MISSING_MUST_PARAM, String.format("%s not found",cellDebtTitle));
    }

    if(amcDebts.size() > 1){
      throw ExceptionUtils.getAmcException(AmcExceptions.MISSING_MUST_PARAM, String.format("%s found more than one:%s",
              cellDebtTitle, amcDebts.stream().map(item->item.getId().toString()).reduce("", String::concat)));
    }
    amcDebts.get(0).setCourtId(courtId);
    amcDebtMapper.updateByPrimaryKeySelective(amcDebts.get(0));
  }

  private List<AmcDebt> getDebtByCurtCityCode(String cityCode, int limit){
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.createCriteria().andCurtCityEqualTo(Long.valueOf(cityCode)).andPublishStateEqualTo(PublishStateEnum.PUBLISHED.getStatus());
    amcDebtExample.setOrderByClause(String.format(" has_img desc, id desc limit %s ", limit));
    List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);
    return amcDebts;
  }

  private List<AmcDebt> getDebtByCurtCityCodeLike(String provCode, int limit){
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    Long valueLowLimit = provCode.length() >= 6? Long.valueOf(provCode.substring(0,2))*10000: Long.valueOf(provCode)*10000;
    Long valueHighLimit = valueLowLimit + 9999;
    amcDebtExample.createCriteria().andCurtCityGreaterThan(valueLowLimit).andCurtCityLessThan(valueHighLimit).andPublishStateEqualTo(PublishStateEnum.PUBLISHED.getStatus());
    amcDebtExample.setOrderByClause(String.format(" has_img desc, id desc limit %s ", limit));
    List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);
    return amcDebts;
  }

  @Override
  public List<AmcDebtVo> getUserLocalDebts(WXUserRegionFavor wxUserRegionFavor,
      AmcSaleFloor amcSaleFloor) throws Exception {
    // 1. location city location prov and finally lat lng
    // 2. ip city, ip prov and finally ip city lat lng

    List<AmcDebtVo> amcDebtVos = new ArrayList<>();
    String localCity =
        !StringUtils.isEmpty(wxUserRegionFavor.getUserPrefCityName()) ? wxUserRegionFavor
            .getUserPrefCityName() :
            !StringUtils.isEmpty(wxUserRegionFavor.getLocationCityName()) ? wxUserRegionFavor
                .getLocationCityName() : wxUserRegionFavor.getLastIpCity();

    String localCityCode =
        !StringUtils.isEmpty(wxUserRegionFavor.getUserPrefCityCode()) ? wxUserRegionFavor
            .getUserPrefCityCode() :
            !StringUtils.isEmpty(wxUserRegionFavor.getLocationCode()) ? wxUserRegionFavor
                .getLocationCode() : wxUserRegionFavor.getLastIpCityCode();
    String localProv =
        !StringUtils.isEmpty(wxUserRegionFavor.getUserPrefProvName()) ? wxUserRegionFavor
            .getUserPrefProvName() :
            !StringUtils.isEmpty(wxUserRegionFavor.getLocationProvName()) ? wxUserRegionFavor
                .getLocationProvName() : wxUserRegionFavor.getLastIpProv();
    String locationProvCode =
        StringUtils.isEmpty(localCityCode) ? null : localCityCode.substring(0, 2);
    boolean needUserName = false;
    if (!StringUtils.isEmpty(localCityCode)) {
      List<AmcDebt> amcDebts = getDebtByCurtCityCode(localCityCode, PAGE_ITEM_SIZE);
      if (amcDebts != null && amcDebts.size() < PAGE_ITEM_SIZE) {
        amcDebts = getDebtByCurtCityCodeLike(locationProvCode, PAGE_ITEM_SIZE);
        if (amcDebts != null && amcDebts.size() < PAGE_ITEM_SIZE) {
          log.info("Failed to get enough debt by {}", localCityCode);
          if (amcDebts.size() < PAGE_ITEM_SIZE) {
            log.error("need to use geo query now");
            amcSaleFloor.setTitle(StringUtils.isEmpty(localProv) ?
                SaleFloorLocalTitleEnum.LOCAL_LEVEL_RANGE.getTitle()
                : String.format("%s%s", localProv,
                    SaleFloorLocalTitleEnum.LOCAL_LEVEL_RANGE.getTitle()));
            amcSaleFloor.setSlogan(SaleFloorLocalTitleEnum.LOCAL_LEVEL_RANGE.getSlogon());
            GeoJsonPoint geoJsonPoint = null;
            if (wxUserRegionFavor.getUserPrefCityLng() != null) {
              geoJsonPoint = new GeoJsonPoint(wxUserRegionFavor.getUserPrefCityLng(),
                  wxUserRegionFavor.getUserPrefCityLat());
            } else if (wxUserRegionFavor.getLastLng() != null
                && wxUserRegionFavor.getLastLat() != null) {
              geoJsonPoint = new GeoJsonPoint(wxUserRegionFavor.getLastLng(),
                  wxUserRegionFavor.getLastLat());

            } else if (wxUserRegionFavor.getLastIpLat() != null
                && wxUserRegionFavor.getLastIpLng() != null) {
              geoJsonPoint = new GeoJsonPoint(wxUserRegionFavor.getLastIpLng(),
                  wxUserRegionFavor.getLastIpLat());

            }
            return queryNearByDebtsWithLimit(geoJsonPoint, PAGE_ITEM_SIZE);
          } else if (amcDebts != null) {
            amcSaleFloor.setSlogan(SaleFloorLocalTitleEnum.LOCAL_LEVEL_PROV.getSlogon());
            amcSaleFloor.setTitle(SaleFloorLocalTitleEnum.LOCAL_LEVEL_PROV.getTitle());
            return getDebtVos(amcDebts);
          }

        } else if (amcDebts != null) {
          amcSaleFloor.setSlogan(SaleFloorLocalTitleEnum.LOCAL_LEVEL_CITY.getSlogon());
          amcSaleFloor.setTitle(SaleFloorLocalTitleEnum.LOCAL_LEVEL_CITY.getTitle());
          return getDebtVos(amcDebts);
        }

      }
//    if( !StringUtils.isEmpty(localCity) && needUserName ){
//      List<CurtInfo> curtInfos = amcHelperService
//          .queryCurtInfoByCityNames(Arrays.asList(localCity));
//      if(CollectionUtils.isEmpty(curtInfos)){
//        return amcDebtVos;
//      }
//      List<AmcDebt> amcDebts = getDebtVosByCurtInfos(
//          curtInfos.stream().map(item -> item.getId()).collect(Collectors.toList()), PAGE_ITEM_SIZE);
//
//
//
//      if(amcDebts!= null && amcDebts.size() < PAGE_ITEM_SIZE){
//        //not enough , need call geo
//        if(StringUtils.isEmpty(wxUserRegionFavor.getLocationProvName())){
//          curtInfos = amcHelperService.queryCurtInfoByProvNames(Arrays.asList(localProv));
//          amcDebts = getDebtVosByCurtInfos(
//              curtInfos.stream().map(item -> item.getId()).collect(Collectors.toList()), PAGE_ITEM_SIZE);
//        }
//      }else if(amcDebts != null){
//        amcSaleFloor.setSlogan(SaleFloorLocalTitleEnum.LOCAL_LEVEL_CITY.getSlogon());
//        amcSaleFloor.setTitle(SaleFloorLocalTitleEnum.LOCAL_LEVEL_CITY.getTitle());
//        return getDebtVos(amcDebts);
//      }
////      String provName = !StringUtils.isEmpty(wxUserRegionFavor.getUserPrefProvName()) ? wxUserRegionFavor.getUserPrefProvName():
////          !StringUtils.isEmpty(wxUserRegionFavor.getLocationProvName()) ? wxUserRegionFavor.getLocationProvName(): wxUserRegionFavor.getLastIpProv();
//
//
//      if( !StringUtils.isEmpty(localProv)){
//        curtInfos = amcHelperService
//            .queryCurtInfoByProvNames(Arrays.asList(localProv));
//        amcDebts = getDebtVosByCurtInfos(
//            curtInfos.stream().map(item -> item.getId()).collect(Collectors.toList()), PAGE_ITEM_SIZE);
//      }
//
//      if(amcDebts.size() < PAGE_ITEM_SIZE){
//        log.error("need to use geo query now");
//        amcSaleFloor.setTitle(StringUtils.isEmpty(localProv) ?
//            SaleFloorLocalTitleEnum.LOCAL_LEVEL_RANGE.getTitle():String.format("%s%s", localProv,
//            SaleFloorLocalTitleEnum.LOCAL_LEVEL_RANGE.getTitle()));
//        amcSaleFloor.setSlogan(SaleFloorLocalTitleEnum.LOCAL_LEVEL_RANGE.getSlogon());
//        GeoJsonPoint geoJsonPoint = null;
//        if(wxUserRegionFavor.getUserPrefCityLng() != null){
//          geoJsonPoint = new GeoJsonPoint(wxUserRegionFavor.getUserPrefCityLng(), wxUserRegionFavor.getUserPrefCityLat());
//        }
//        else if(wxUserRegionFavor.getLastLng() != null && wxUserRegionFavor.getLastLat() != null){
//          geoJsonPoint = new GeoJsonPoint(wxUserRegionFavor.getLastLng(), wxUserRegionFavor.getLastLat());
//
//        } else if(wxUserRegionFavor.getLastIpLat() != null && wxUserRegionFavor.getLastIpLng() != null){
//          geoJsonPoint = new GeoJsonPoint(wxUserRegionFavor.getLastIpLng(), wxUserRegionFavor.getLastIpLat());
//
//        }
//        return queryNearByDebtsWithLimit(geoJsonPoint, PAGE_ITEM_SIZE);
////        return amcDebtVos;
//
//      }else if(amcDebts != null){
//        amcSaleFloor.setSlogan(SaleFloorLocalTitleEnum.LOCAL_LEVEL_PROV.getSlogon());
//        amcSaleFloor.setTitle(SaleFloorLocalTitleEnum.LOCAL_LEVEL_PROV.getTitle());
//        return getDebtVos(amcDebts);
//      }
//
//    }
//
    }
    return amcDebtVos;
  }

  @Override
  public List<AmcDebtExt> findDebtsAssetsOfUser(Long userId) {


      AmcDebtExample amcDebtExample = new AmcDebtExample();
      amcDebtExample.createCriteria().andAmcContactorIdEqualTo(userId);
      List<AmcDebtExt> amcDebts = amcDebtExtMapper.selectSimpleByExampleExt(amcDebtExample);
      return amcDebts;


  }

  @Cacheable
  public List<AmcDebt> getDebtVosByCurtInfos(List<Long> curtIds, Integer limit) throws Exception {
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.setOrderByClause(String.format(" has_img desc, id desc limit %s ", limit));
    amcDebtExample.createCriteria().andPublishStateEqualTo(PublishStateEnum.PUBLISHED.getStatus()).andCourtIdIn(curtIds);
    List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);
    return amcDebts;
  }


  private AmcDebtExample getAmcDebtExampleWithFloorRandomFilter(AmcFilterContentDebt floorDebtFilter, AmcSaleGetRandomListInPage pageInfo)
      throws Exception {
    AmcDebtExample amcDebtExample = new AmcDebtExample();

    AmcDebtExample.Criteria criteria = amcDebtExample.createCriteria();


    List<CurtInfo> curtInfos = null;
    List<CurtInfo> curtInfosTotal = new ArrayList<>();
    if(floorDebtFilter.getCourtCities() != null && !CollectionUtils.isEmpty(floorDebtFilter.getCourtCities())){
      curtInfos = amcHelperService.queryCurtInfoByCityNames(floorDebtFilter.getCourtCities());
      if(!CollectionUtils.isEmpty(curtInfos)){
        curtInfosTotal.addAll(curtInfos);
      }
    }

    if(floorDebtFilter.getCourtProvs() != null && !CollectionUtils.isEmpty(floorDebtFilter.getCourtProvs())){
      curtInfos = amcHelperService.queryCurtInfoByProvNames(floorDebtFilter.getCourtProvs());
      curtInfosTotal.addAll(curtInfos);
    }
    if( !CollectionUtils.isEmpty(curtInfosTotal)){
      criteria.andCourtIdIn(curtInfosTotal.stream().map(item->item.getId()).collect(Collectors.toList()));
    }
    if(!CollectionUtils.isEmpty(floorDebtFilter.getGuarantType())){
      criteria.andGuarantTypeIn(floorDebtFilter.getGuarantType());
    }

    if(!CollectionUtils.isEmpty(floorDebtFilter.getDebtType())){
      criteria.andDebtTypeIn(floorDebtFilter.getDebtType());
    }
    if(!CollectionUtils.isEmpty(floorDebtFilter.getBaseAmount())){

      Long lowLimit = -1L;
      Long highLimit = -1L;
      if(floorDebtFilter.getBaseAmount().get(0) < floorDebtFilter.getBaseAmount().get(1)){
        lowLimit = floorDebtFilter.getBaseAmount().get(0)*100;
        highLimit = floorDebtFilter.getBaseAmount().get(1)*100;
      }else{
        lowLimit = floorDebtFilter.getBaseAmount().get(1)*100;
        highLimit = floorDebtFilter.getBaseAmount().get(0)*100;
      }
      criteria.andBaseAmountBetween(lowLimit, highLimit);
    }
    criteria.andPublishStateEqualTo(PublishStateEnum.PUBLISHED.getStatus());
    StringBuilder sbOrderBy = new StringBuilder();
    if(floorDebtFilter.getOrderByField() == OrderByFieldEnum.VISITCOUNT.getId()){
      sbOrderBy.append(" visit_count desc ");
    }else{
      sbOrderBy.append("  rand() ");
      criteria.andHasImgEqualTo(HasImageEnum.HASIMAGE.getStatus());

    }
    if(pageInfo.getDebtIds() != null && !CollectionUtils.isEmpty(pageInfo.getDebtIds())  ){

        criteria.andIdNotIn(pageInfo.getDebtIds());


    }
    if(pageInfo.getPgSize() != null && pageInfo.getPgSize() > 0){
      sbOrderBy.append(" limit ").append(pageInfo.getPgSize());
    }
    else{
      sbOrderBy.append(" limit ").append(0);
    }

    amcDebtExample.setOrderByClause(sbOrderBy.toString());
    return amcDebtExample;

  }

  private AmcDebtExample getAmcDebtExampleWithFloorFilter(AmcFilterContentDebt floorDebtFilter, AmcSaleGetListInPage pageInfo)
      throws Exception {
    AmcDebtExample amcDebtExample = new AmcDebtExample();

    AmcDebtExample.Criteria criteria = amcDebtExample.createCriteria();


    List<CurtInfo> curtInfos = null;
    List<CurtInfo> curtInfosTotal = new ArrayList<>();
    if(floorDebtFilter.getCourtCities() != null && !CollectionUtils.isEmpty(floorDebtFilter.getCourtCities())){
      curtInfos = amcHelperService.queryCurtInfoByCityNames(floorDebtFilter.getCourtCities());
      if(!CollectionUtils.isEmpty(curtInfos)){
        curtInfosTotal.addAll(curtInfos);
      }
    }

    if(floorDebtFilter.getCourtProvs() != null && !CollectionUtils.isEmpty(floorDebtFilter.getCourtProvs())){
      curtInfos = amcHelperService.queryCurtInfoByProvNames(floorDebtFilter.getCourtProvs());
      curtInfosTotal.addAll(curtInfos);
    }
    if( !CollectionUtils.isEmpty(curtInfosTotal)){
      criteria.andCourtIdIn(curtInfosTotal.stream().map(item->item.getId()).collect(Collectors.toList()));
    }
    if(!CollectionUtils.isEmpty(floorDebtFilter.getGuarantType())){
      criteria.andGuarantTypeIn(floorDebtFilter.getGuarantType());
    }

    if(!CollectionUtils.isEmpty(floorDebtFilter.getDebtType())){
      criteria.andDebtTypeIn(floorDebtFilter.getDebtType());
    }
    if(!CollectionUtils.isEmpty(floorDebtFilter.getBaseAmount())){

      Long lowLimit = -1L;
      Long highLimit = -1L;
      if(floorDebtFilter.getBaseAmount().get(0) < floorDebtFilter.getBaseAmount().get(1)){
        lowLimit = floorDebtFilter.getBaseAmount().get(0)*100;
        highLimit = floorDebtFilter.getBaseAmount().get(1)*100;
      }else{
        lowLimit = floorDebtFilter.getBaseAmount().get(1)*100;
        highLimit = floorDebtFilter.getBaseAmount().get(0)*100;
      }
      criteria.andBaseAmountBetween(lowLimit, highLimit);
    }
    criteria.andPublishStateEqualTo(PublishStateEnum.PUBLISHED.getStatus());
    StringBuilder sbOrderBy = new StringBuilder();
    if(floorDebtFilter.getOrderByField() == OrderByFieldEnum.VISITCOUNT.getId()){
      sbOrderBy.append(" visit_count desc ");
    }else{
      sbOrderBy.append(" has_img desc, id desc ");
    }
    if(pageInfo.getLastDebtId() != null  ){
      if(pageInfo.getLastDebtId() > 0){
        criteria.andIdLessThan(pageInfo.getLastDebtId());
      }
      sbOrderBy.append(" limit ").append(pageInfo.getPgSize());
    }else{
      sbOrderBy.append(" limit ").append(0);
    }

    amcDebtExample.setOrderByClause(sbOrderBy.toString());

    return amcDebtExample;
  }

  private void updateDebtVosWithCurtInfoFixOrder(List<AmcDebtVo> amcDebtVos) throws Exception {
    if(CollectionUtils.isEmpty(amcDebtVos)){
      return;
    }
    List<CurtInfo> curtInfos =
amcHelperService.getCurtByIds(amcDebtVos.stream().map(item->item.getCourtId()).collect(Collectors.toUnmodifiableList()));
    if(CollectionUtils.isEmpty(curtInfos)){
      return;
    }
    Map<Long, String> curtId2NameMap = curtInfos.stream().collect(Collectors.toMap(item-> item.getId(), item-> item.getCurtName()));
    for(AmcDebtVo amcDebtVo: amcDebtVos){
      if(curtId2NameMap.containsKey(amcDebtVo.getCourtId())){
        amcDebtVo.setCourtName(curtId2NameMap.get(amcDebtVo.getCourtId()));
      }
    }
  }

  private void updateDebtVosWithMongoFixOrder(List<AmcDebtVo> amcDebtVos){
    List<Long> debtIds = amcDebtVos.stream().map(item->item.getId()).collect(Collectors.toUnmodifiableList());
//    Map<Long, AmcDebtVo> amcDebtVosMap = amcDebtVos.stream().collect(
//        Collectors.toMap(item-> item.getId(), item -> item));
//    Set<Long> debtIds = amcDebtVos.stream().map(item -> item.getId()).collect(Collectors.toSet());
    Query query = new Query();
    query.addCriteria(Criteria.where("debtId").in(debtIds));
    List<DebtImage> debtImages = wszccTemplate.find(query, DebtImage.class);
    Map<Long, DebtImage> debtImageMap = debtImages.stream().collect(Collectors.toMap(item->item.getDebtId(), item->item));


    List<AmcDebtVo> amcDebtVoList = new ArrayList<>();
    Iterator<AmcDebtVo> iteratorDebt = amcDebtVos.iterator();
//    Iterator<DebtImage> iteratorI = debtImages.iterator();
    while(iteratorDebt.hasNext()){
     AmcDebtVo amcDebtVoCurrent = iteratorDebt.next();
      if(debtImageMap.containsKey(amcDebtVoCurrent.getId())){
        amcDebtVoCurrent.setDebtImage(debtImageMap.get(amcDebtVoCurrent.getId()));
      }
    }

    List<DebtAdditional> additionals = wszccTemplate.find(query, DebtAdditional.class);
    setAddInfos(additionals, amcDebtVos);

  }
  private void setAddInfos(List<DebtAdditional> debtAdditionals, List<AmcDebtVo> amcDebtVos){
    Iterator<DebtAdditional> iteratorAdd =  debtAdditionals.iterator();
    for(int idx = 0; idx < amcDebtVos.size(); idx++){
      iteratorAdd =  debtAdditionals.iterator();
      while(iteratorAdd.hasNext()){
        DebtAdditional debtAdditional = iteratorAdd.next();

        if( amcDebtVos.get(idx).getId() != null && debtAdditional.getAmcDebtId() != null &&
            amcDebtVos.get(idx).getId().equals(debtAdditional.getAmcDebtId())){
          amcDebtVos.get(idx).setDebtAdditional(debtAdditional);
//                    assetImages.remove(assetImage);
          iteratorAdd.remove();
          break;
        }

      }
    }
  }
  private void handleCourtGeoInfo(Map<Long, Long> debt2Courts) {
    long courtId = -1;
    Iterator<Map.Entry<Long, Long>> iterator = debt2Courts.entrySet().iterator();
    HashMap<Long, AddressTmp> courtAddress = new HashMap<>();
    while (iterator.hasNext()){
      Map.Entry<Long, Long> item = iterator.next();
      courtId = item.getValue();
      if(courtId > 0){
        CurtInfo curtInfo =  curtInfoMapper.selectByPrimaryKey(courtId);
        if(curtInfo == null){
          log.error("cannot find courtInfo by :{}", courtId);
          continue;
        }
        AddressTmp addressTmp = new AddressTmp(curtInfo.getCurtName(), curtInfo.getCurtCity());
        courtAddress.put(item.getKey(), addressTmp);
      }
    }
    updateGeoInfo4Debt(courtAddress);

  }

  public void updateGeoInfo4SpeicialDebt(Long debtId){
    AmcDebt amcDebt = amcDebtMapper.selectByPrimaryKey(debtId);
    Long curtId =  amcDebt.getCourtId();
    CurtInfo curtInfo = curtInfoMapper.selectByPrimaryKey(curtId);
    Address.Builder aBuilder = Address.newBuilder();
    String courtName = curtInfo.getCurtName();
    if(StringUtils.isEmpty(courtName)){
      log.error(" the courtName is empty:{} of amcId:{}", courtName, debtId );
    }
    aBuilder.setAddress(courtName);
    aBuilder.setCity(curtInfo.getCurtCity());
    GeoJson geoJson = null;
    try{
      geoJson = comnfuncServiceStub.getGeoByAddress(aBuilder.build());
    }catch (Exception ex){
      log.error("", ex);

    }


    Query query = new Query();
    query.addCriteria(Criteria.where("amcDebtId").is(debtId));
    List<DebtAdditional> debtAdditionals = wszccTemplate.find(query, DebtAdditional.class);
    if(CollectionUtils.isEmpty(debtAdditionals)){
      log.error("Failed to find debtAdditionals by:{}", debtId);
      query = null;

    }
    debtAdditionals.get(0).setLocation(new GeoJsonPoint(geoJson.getCoordinates(0), geoJson.getCoordinates(1)));
    wszccTemplate.save(debtAdditionals.get(0));
    query = null;
  }


  private void updateGeoInfo4Debt(HashMap<Long, AddressTmp> courtAddress) {
    Query query = new Query();
    for (Map.Entry<Long, AddressTmp> mapElement : courtAddress.entrySet()) {
      Long debtId = mapElement.getKey();
      Address.Builder aBuilder = Address.newBuilder();
      String courtName = mapElement.getValue().getCourtName();
      if(StringUtils.isEmpty(courtName)){
        log.error(" the courtName is empty:{} of amcId:{}", courtName, debtId );
      }
      aBuilder.setAddress(courtName);
      aBuilder.setCity(mapElement.getValue().getCity());
      GeoJson geoJson;
      try{
        geoJson = comnfuncServiceStub.getGeoByAddress(aBuilder.build());
      }catch (Exception ex){
        log.error("", ex);
        continue;
      }


      query = new Query();
      query.addCriteria(Criteria.where("amcDebtId").is(debtId));
      List<DebtAdditional> debtAdditionals = wszccTemplate.find(query, DebtAdditional.class);
      if(CollectionUtils.isEmpty(debtAdditionals)){
        log.error("Failed to find debtAdditionals by:{}", debtId);
        query = null;
        continue;
      }
      debtAdditionals.get(0).setLocation(new GeoJsonPoint(geoJson.getCoordinates(0), geoJson.getCoordinates(1)));
      wszccTemplate.save(debtAdditionals.get(0));
      query = null;
    }

  }


  public AmcDebtExample getAmcDebtExampleWithFloorFilter(AmcFilterContentDebt floorDebtFilter) throws Exception {
    AmcDebtExample amcDebtExample = new AmcDebtExample();

    AmcDebtExample.Criteria criteria = amcDebtExample.createCriteria();


    List<CurtInfo> curtInfos = null;
    List<CurtInfo> curtInfosTotal = new ArrayList<>();
    if(floorDebtFilter.getCourtCities() != null && !CollectionUtils.isEmpty(floorDebtFilter.getCourtCities())){
      curtInfos = amcHelperService.queryCurtInfoByCityNames(floorDebtFilter.getCourtCities());
      if(!CollectionUtils.isEmpty(curtInfos)){
        curtInfosTotal.addAll(curtInfos);
      }
    }

    if(floorDebtFilter.getCourtProvs() != null && !CollectionUtils.isEmpty(floorDebtFilter.getCourtProvs())){
      curtInfos = amcHelperService.queryCurtInfoByProvNames(floorDebtFilter.getCourtProvs());
      curtInfosTotal.addAll(curtInfos);
    }
    if( !CollectionUtils.isEmpty(curtInfosTotal)){
      criteria.andCourtIdIn(curtInfosTotal.stream().map(item->item.getId()).collect(Collectors.toList()));
    }
    if(!CollectionUtils.isEmpty(floorDebtFilter.getGuarantType())){
      criteria.andGuarantTypeIn(floorDebtFilter.getGuarantType());
    }

    if(!CollectionUtils.isEmpty(floorDebtFilter.getDebtType())){
      criteria.andDebtTypeIn(floorDebtFilter.getDebtType());
    }
    if(!CollectionUtils.isEmpty(floorDebtFilter.getBaseAmount())){

      Long lowLimit = -1L;
      Long highLimit = -1L;
      if(floorDebtFilter.getBaseAmount().get(0) < floorDebtFilter.getBaseAmount().get(1)){
        lowLimit = floorDebtFilter.getBaseAmount().get(0)*100;
        highLimit = floorDebtFilter.getBaseAmount().get(1)*100;
      }else{
        lowLimit = floorDebtFilter.getBaseAmount().get(1)*100;
        highLimit = floorDebtFilter.getBaseAmount().get(0)*100;
      }
      criteria.andBaseAmountBetween(lowLimit, highLimit);
    }
    criteria.andPublishStateEqualTo(PublishStateEnum.PUBLISHED.getStatus());

    if(floorDebtFilter.getOrderByField() == OrderByFieldEnum.VISITCOUNT.getId()){
      amcDebtExample.setOrderByClause(" visit_count desc ");
    }else{
      amcDebtExample.setOrderByClause(" has_img desc, id desc ");
    }

    return amcDebtExample;
  }



  public AmcDebtExample getAmcDebtExampleWithQueryParam(Map<String, Object> queryParam) throws Exception {
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    AmcDebtExample.Criteria criteria = amcDebtExample.createCriteria();
    boolean needDefaultPublishState = true;
    if(!CollectionUtils.isEmpty(queryParam)){
      for(Entry<String, Object> item: queryParam.entrySet()){

        if(item.getKey().equals(QueryParamEnum.BaseAmount.name())){
          List<Long> amounts = (List) item.getValue();
          if(amounts.get(0) < 0 && amounts.get(1) > 0){
            criteria.andBaseAmountLessThanOrEqualTo(amounts.get(1));
          }else if(amounts.get(1) < 0 && amounts.get(0) > 0){
            criteria.andBaseAmountGreaterThan(amounts.get(0));
          }else if(amounts.get(0) > 0 && amounts.get(1) > 0){
            criteria.andBaseAmountBetween(amounts.get(0), amounts.get(1));
          }else{
            throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_AMOUNT_RANGE,  String.format("%d,%d",amounts.get(0), amounts.get(1))
            );
          }
        }

        if(item.getKey().equals(QueryParamEnum.PublishStates.name())){
          criteria.andPublishStateIn((List) item.getValue());
          needDefaultPublishState = false;
        }

        if(item.getKey().equals(QueryParamEnum.DebtType.name())){
          criteria.andDebtTypeEqualTo((Integer) item.getValue());
        }

        if(item.getKey().equals(QueryParamEnum.CourtLocations.name())){
          String[] courtLocations = (String[]) item.getValue();
          List<Long> curtIds = new ArrayList<>();
          CurtInfoExample curtInfoExample = new CurtInfoExample();
          if(courtLocations.length == 2){
            //省 市
            curtInfoExample.createCriteria().andCurtProvinceEqualTo(courtLocations[0]).andCurtCityEqualTo(courtLocations[1]);

          }else if(courtLocations.length == 3){
            //省 市 区
            curtInfoExample.createCriteria().andCurtProvinceEqualTo(courtLocations[0])
                .andCurtCityEqualTo(courtLocations[1]).andCurtCountyEqualTo(courtLocations[2]);
          }
          List<CurtInfo> curtInfos =  curtInfoMapper.selectByExample(curtInfoExample);
          if(!CollectionUtils.isEmpty(curtInfos)){
            curtIds = curtInfos.stream().map(court->court.getId()).collect(Collectors.toUnmodifiableList());
            criteria.andCourtIdIn(curtIds);
          }else{
            log.error("Failed find curt info for :{}", courtLocations);
          }

        }

        if(item.getKey().equals(QueryParamEnum.AmcContactorName.name())){
          StringBuilder sb = new StringBuilder().append("%").append(item.getValue()).append("%");
          criteria.andAmcContactorNameLike(sb.toString());
        }

        if(item.getKey().equals(QueryParamEnum.Title.name())){
          StringBuilder sb = new StringBuilder().append("%").append(item.getValue()).append("%");
          criteria.andTitleLike(sb.toString());
        }

        if(item.getKey().equalsIgnoreCase(QueryParamEnum.Recommand.name())){
          criteria.andIsRecommandedIn((List) item.getValue());
        }

        if(item.getKey().equals(QueryParamEnum.CourtId.name())){
          criteria.andCourtIdEqualTo((Long)item.getValue());
        }
        if(item.getKey().equals(QueryParamEnum.DebtPackId.name())){
          List<Long> debtPackIds = (List<Long>)item.getValue();
          if(CollectionUtils.isEmpty(debtPackIds)){
            log.error("There is no debtIds for debtPackIds:{}, and it will return emtpy result set",
                item.getValue().toString());
            criteria.andDebtpackIdEqualTo(-1L);
          }else if(debtPackIds.size() > 1){
            criteria.andDebtpackIdIn(debtPackIds);
          }else{
            criteria.andDebtpackIdEqualTo(debtPackIds.get(0));
          }

        }
      }
    }
    if(needDefaultPublishState){
      criteria.andPublishStateNotEqualTo(PublishStateEnum.DELETED.getStatus());
    }
    return amcDebtExample;
  }

  @Data

  class AddressTmp{
    String courtName;
    String city;

    AddressTmp(String courtName, String city){
      this.courtName = courtName;
      this.city = city;

    }
  }
}
