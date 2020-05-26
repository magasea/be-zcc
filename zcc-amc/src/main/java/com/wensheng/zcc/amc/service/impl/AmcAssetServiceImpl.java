package com.wensheng.zcc.amc.service.impl;


import com.wensheng.zcc.amc.dao.mysql.mapper.AmcAssetMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtContactorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.ext.AmcAssetExtMapper;
import com.wensheng.zcc.amc.module.dao.helper.HasImageEnum;
import com.wensheng.zcc.amc.module.dao.helper.ImageClassEnum;
import com.wensheng.zcc.amc.module.dao.helper.ImagePathClassEnum;
import com.wensheng.zcc.amc.module.dao.helper.OrderByFieldEnum;
import com.wensheng.zcc.amc.module.dao.helper.PublishStateEnum;
import com.wensheng.zcc.amc.module.dao.helper.QueryParamEnum;
import com.wensheng.zcc.amc.module.dao.mongo.entity.*;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.*;
import com.wensheng.zcc.amc.module.dto.AmcContactorDTO;
import com.wensheng.zcc.amc.module.vo.AmcAssetDetailVo;
import com.wensheng.zcc.amc.module.vo.AmcAssetGeoNear;
import com.wensheng.zcc.amc.module.vo.AmcAssetVo;
import com.wensheng.zcc.amc.module.vo.AmcSaleGetListInPage;
import com.wensheng.zcc.amc.module.vo.AmcSaleGetRandomListInPage;
import com.wensheng.zcc.common.module.dto.AmcFilterContentAsset;
import com.wensheng.zcc.amc.service.AmcAssetService;
import com.wensheng.zcc.amc.service.AmcDebtService;
import com.wensheng.zcc.amc.service.AmcOssFileService;
import com.wensheng.zcc.amc.service.RegionService;
import com.wensheng.zcc.amc.service.impl.helper.Dao2VoUtils;
import com.wensheng.zcc.amc.utils.SQLUtils;
import com.wensheng.zcc.common.module.dto.Region;
import com.wensheng.zcc.common.params.AmcDebtAssetTypeEnum;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.common.utils.AmcDateUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import com.wenshengamc.zcc.common.Common.GeoJson;
import com.wenshengamc.zcc.comnfunc.gaodegeo.Address;
import com.wenshengamc.zcc.comnfunc.gaodegeo.ComnFuncServiceGrpc.ComnFuncServiceBlockingStub;
import com.wenshengamc.zcc.wechat.AmcAssetImage;
import com.wenshengamc.zcc.wechat.ImageUploadResult;
import com.wenshengamc.zcc.wechat.UploadImg2WechatReq;
import com.wenshengamc.zcc.wechat.UploadImg2WechatResp;
import com.wenshengamc.zcc.wechat.WechatAssetImage;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @author chenwei on 12/5/18
 * @project zcc-backend
 */
@Service
@Slf4j
@CacheConfig(cacheNames = {"DEBTPACK"})
public class AmcAssetServiceImpl implements AmcAssetService {
    @Autowired
    AmcAssetMapper amcAssetMapper;

    @Autowired
    AmcAssetExtMapper amcAssetExtMapper;

    @Autowired
    AmcDebtContactorMapper amcDebtContactorMapper;

    @Autowired
    MongoTemplate wszccTemplate;

    @Autowired
    MongoTemplate originalMongoTemplate;

    @Autowired
    AmcOssFileService amcOssFileService;

    @Autowired
    WechatGrpcService wechatGrpcService;

    @Autowired
    ComnFuncServiceBlockingStub comnfuncServiceStub;

    @Autowired
    AmcDebtService amcDebtService;

    @Autowired
    RegionService regionService;

    @Value("${env.name}")
    String envName;


    @Override
    @Transactional
    @CacheEvict(allEntries = true)
    public AmcAssetVo create(AmcAsset amcAsset) throws Exception {
        amcAsset.setCreatedDate(AmcDateUtils.getCurrentDate());
        amcAssetMapper.insertSelective(amcAsset);
        delMongoForAmcAsset(amcAsset.getId());
         AmcAssetVo amcAssetVo = Dao2VoUtils.convertDo2Vo(amcAsset);
//        if(amcAsset.getAmcContactorId() != null && amcAsset.getAmcContactorId() > 0){
//            AmcDebtContactorExample amcDebtContactorExample = new AmcDebtContactorExample();
//            amcDebtContactorExample.createCriteria().andIdEqualTo(amcAsset.getAmcContactorId());
//            AmcDebtContactor amcDebtContactor = amcDebtContactorMapper.selectByPrimaryKey(amcAsset.getAmcContactorId());
//            amcAssetVo.setAmcContactorId(amcDebtContactor);
//        }
        amcAssetVo.setAssetAdditional(queryAddtional(amcAsset));
//        amcAssetVo.setAssetImage(queryImage(amcAsset));
        return amcAssetVo;
    }

    private AssetAdditional queryAddtional(AmcAsset asset){
        Query query = new Query();
        query.addCriteria(Criteria.where("amcAssetId").is(asset.getId()));
        List<AssetAdditional> assetAdditionals = wszccTemplate.find(query, AssetAdditional.class);
        if(!CollectionUtils.isEmpty(assetAdditionals)){
            return assetAdditionals.get(0);
        }else{
            return null;
        }
    }


    private AssetAdditional updateAddtional(AmcAsset asset, AssetAdditional assetAdditional){
        Query query = new Query();
        query.addCriteria(Criteria.where("amcAssetId").is(asset.getId()));
        List<AssetAdditional> assetAdditionals = wszccTemplate.find(query, AssetAdditional.class);
        if(CollectionUtils.isEmpty(assetAdditionals)){
            //just insert it
            assetAdditional.setAmcAssetId(asset.getId());
            wszccTemplate.save(assetAdditional);
            return assetAdditional;
        }else{
            AmcBeanUtils.copyProperties(assetAdditional, assetAdditionals.get(0));
            wszccTemplate.save(assetAdditionals.get(0));
            return assetAdditionals.get(0);
        }
    }

    private AssetImage updateImage(AmcAsset asset, AssetImage assetImage){
        Query query = new Query();
        query.addCriteria(Criteria.where("amcAssetId").is(asset.getId()));
        List<AssetImage> assetImages = wszccTemplate.find(query, AssetImage.class);
        if(CollectionUtils.isEmpty(assetImages)){
            //just insert it
            assetImage.setAmcAssetId(asset.getId());
            wszccTemplate.save(assetImage);
            return assetImage;
        }else{
            AmcBeanUtils.copyProperties(assetImage, assetImages.get(0));
            wszccTemplate.save(assetImages.get(0));
            return assetImages.get(0);
        }
    }

    private AssetImage queryImage(AmcAsset asset){
        Query query = new Query();
        query.addCriteria(Criteria.where("amcAssetId").is(asset.getId()).and("tag").is(ImageClassEnum.MAIN.getId()));
        List<AssetImage> assetImages = wszccTemplate.find(query, AssetImage.class);

        return assetImages.get(0);
    }

    @Override
    public AssetAdditional createOrUpdateAssetAddition(AssetAdditional additional) {
        Query query = new Query();
        query.addCriteria(Criteria.where("amcAssetId").is(additional.getAmcAssetId()));
        List<AssetAdditional> assetAdditionals = wszccTemplate.find(query, AssetAdditional.class);

        if(!CollectionUtils.isEmpty(assetAdditionals)){
            AmcBeanUtils.copyProperties(additional, assetAdditionals.get(0));
            wszccTemplate.save(assetAdditionals.get(0));
        }else{
            wszccTemplate.save(additional);
        }
        return additional;
    }



    @Override
    @CacheEvict(allEntries = true)
    @Transactional
    public int delAsset(Long amcAssetId) {
        AmcAsset amcAsset = amcAssetMapper.selectByPrimaryKey(amcAssetId);
        del(amcAsset);
        return 1;
    }

  @Override
  public int del(Long amcDebtId) {
      AmcAssetExample amcAssetExample = new AmcAssetExample();
      amcAssetExample.createCriteria().andDebtIdEqualTo(amcDebtId);
      List<AmcAsset> amcAssets = amcAssetMapper.selectByExample(amcAssetExample);
      int count = 0;
      for(AmcAsset amcAsset: amcAssets){
        del(amcAsset);
        count++;
      }
      return count;
  }

    @Override
    public void updateByDebtState(Long debtId, Integer publishState) {
        AmcAsset amcAsset = new AmcAsset();
        amcAsset.setPublishState(publishState);
        if(publishState.equals(PublishStateEnum.PUBLISHED.getStatus())){
          amcAsset.setPublishDate(AmcDateUtils.getCurrentDate());
        }
        AmcAssetExample amcAssetExample = new AmcAssetExample();
        amcAssetExample.createCriteria().andDebtIdEqualTo(debtId);
        amcAssetMapper.updateByExampleSelective(amcAsset, amcAssetExample);
    }

  @Override
  public void updateContactor4Assets(Long debtId, String amcContactorName, String amcContactorPhone) {
    AmcAssetExample amcAssetExample = new AmcAssetExample();
    amcAssetExample.createCriteria().andDebtIdEqualTo(debtId);
    List<AmcAsset> amcAssets =  amcAssetMapper.selectByExample(amcAssetExample);
    for(AmcAsset amcAsset: amcAssets){
      amcAsset.setAmcContactorName(amcContactorName);
      amcAsset.setAmcContactorPhone(amcContactorPhone);
      amcAssetMapper.updateByPrimaryKeySelective(amcAsset);
    }
  }

  private void del(AmcAsset amcAsset) {

        AmcAssetExample amcAssetExample = new AmcAssetExample();
        Query query= new Query();
        query.addCriteria(Criteria.where("amcAssetId").is(amcAsset.getId()));

        delMongoForAmcAsset(amcAsset.getId());
        amcAssetMapper.deleteByPrimaryKey(amcAsset.getId());

    }

    @CacheEvict(allEntries = true)
    @Override
    public AmcAssetVo update(AmcAsset amcAsset) throws Exception {
        amcAssetMapper.updateByPrimaryKey(amcAsset);
        AmcAssetVo amcAssetVo =  Dao2VoUtils.convertDo2Vo(amcAsset);
        if(amcAsset.getAmcContactorId() != null && amcAsset.getAmcContactorId() > 0){
            AmcDebtContactorExample amcDebtContactorExample = new AmcDebtContactorExample();
            amcDebtContactorExample.createCriteria().andIdEqualTo(amcAsset.getAmcContactorId());
            AmcDebtContactor amcDebtContactor = amcDebtContactorMapper.selectByPrimaryKey(amcAsset.getAmcContactorId());
//            amcAssetVo.setAmcContactorId(amcDebtContactor);
        }
        return amcAssetVo;
    }

    @Override
    public List<AmcAssetVo> queryAll(int offset, int size) {
        return null;
    }

    @Override
    public AmcAssetVo get(Long amcAssetId) {
        return null;
    }

  @Override
  public List<AmcAssetVo> getByIds(List<Long> amcAssetIds) throws Exception {

      List<AmcAsset> amcAssets = getSimpleAssets(amcAssetIds);
    List<AmcAssetVo> amcAssetVos = Dao2VoUtils.convertDoList2VoList(amcAssets);
    if(CollectionUtils.isEmpty(amcAssetVos)){
      return amcAssetVos;
    }

    List<Long> assetIds = amcAssetVos.stream().map(item -> item.getId()).collect(Collectors.toUnmodifiableList());

    Query query = new Query();
    query.addCriteria(Criteria.where("amcAssetId").in(assetIds));
    List<AssetAdditional> assetAdditionals = wszccTemplate.find(query, AssetAdditional.class);
    query = new Query();
    query.addCriteria(Criteria.where("amcAssetId").in(assetIds).and("tag").is(ImageClassEnum.MAIN.getId()));
    List<AssetImage> assetImages = wszccTemplate.find(query, AssetImage.class);
    setAddInfos(assetAdditionals, amcAssetVos);

    setImages(assetImages, amcAssetVos);

    return amcAssetVos;

  }

  @Override
    public List<AmcAssetVo> query(AmcAsset queryCond, int offset, int size) {
        return null;
    }

    @Override
    public AmcAssetDetailVo queryAssetDetail(Long assetId) throws Exception {
        AmcAsset amcAsset =  amcAssetMapper.selectByPrimaryKey(assetId);


        AmcAssetDetailVo amcAssetDetailVo = queryDetailMongoForAmcAsset(amcAsset);
        if(amcAsset.getAmcContactorName() == null ||  StringUtils.isEmpty(amcAsset.getAmcContactorName())){
            List<AmcContactorDTO> amcContactorDTOS = amcDebtService.getDebtContactorByDebtIds(Arrays.asList(amcAsset.getDebtId()));
            if(!CollectionUtils.isEmpty(amcContactorDTOS)){
              amcAsset.setAmcContactorName(amcContactorDTOS.get(0).getContactorName());
              amcAsset.setAmcContactorPhone(amcContactorDTOS.get(0).getContactorPhone());
              amcAssetMapper.updateByPrimaryKeySelective(amcAsset);
            }

        }
        return amcAssetDetailVo;
    }


  private AmcAssetVo queryMongoForAmcAsset(AmcAsset amcAsset) throws Exception {
    AmcAssetVo amcAssetVo = Dao2VoUtils.convertDo2Vo(amcAsset);
    Long assetId = amcAsset.getId();
    Query query = new Query();
    query.addCriteria(Criteria.where("amcAssetId").is(assetId));

    List<AssetAdditional> assetAdditionals =  wszccTemplate.find(query, AssetAdditional.class);



    Query queryAssetImage = new Query();
    queryAssetImage.addCriteria(Criteria.where("amcAssetId").is(assetId).and("tag").is(ImageClassEnum.MAIN.getId()));
    List<AssetImage> assetImages = wszccTemplate.find(queryAssetImage, AssetImage.class);


    if(!CollectionUtils.isEmpty(assetAdditionals)){
      amcAssetVo.setAssetAdditional(assetAdditionals.get(0));
    }


    if(!CollectionUtils.isEmpty(assetImages)){
      amcAssetVo.setAssetImage(assetImages.get(0));
    }
    return amcAssetVo;

  }

  private AmcAssetDetailVo queryDetailMongoForAmcAsset(AmcAsset amcAsset) throws Exception {
        AmcAssetVo amcAssetVo = Dao2VoUtils.convertDo2Vo(amcAsset);
        Long assetId = amcAsset.getId();
        Query query = new Query();
        query.addCriteria(Criteria.where("amcAssetId").is(assetId));

        List<AssetAdditional> assetAdditionals =  wszccTemplate.find(query, AssetAdditional.class);

        Query queryComment = new Query();
        queryComment.addCriteria(Criteria.where("amcAssetId").is(assetId));
        List<AssetComment> assetComments = wszccTemplate.find(queryComment, AssetComment.class);

        Query queryAssetDocument = new Query();
        queryAssetDocument.addCriteria(Criteria.where("amcAssetId").is(assetId));
        List<AssetDocument> assetDocuments = wszccTemplate.find(queryAssetDocument, AssetDocument.class);

        Query queryAssetImage = new Query();
        queryAssetImage.addCriteria(Criteria.where("amcAssetId").is(assetId));
        List<AssetImage> assetImages = wszccTemplate.find(queryAssetImage, AssetImage.class);

        AmcAssetDetailVo amcAssetDetailVo = new AmcAssetDetailVo();
        amcAssetDetailVo.setAmcAssetVo(amcAssetVo);
        if(!CollectionUtils.isEmpty(assetAdditionals)){
            amcAssetDetailVo.setAssetAdditional(assetAdditionals.get(0));
        }
        if(!CollectionUtils.isEmpty(assetComments)){
            amcAssetDetailVo.setAssetComments(assetComments);
        }

        if(!CollectionUtils.isEmpty(assetDocuments)){
            amcAssetDetailVo.setAssetDocument(assetDocuments);
        }

        if(!CollectionUtils.isEmpty(assetImages)){
            amcAssetDetailVo.setAssetImageList(assetImages);
        }
        return amcAssetDetailVo;

    }

    private void delMongoForAmcAsset(Long assetId) {

        Query query = new Query();
        query.addCriteria(Criteria.where("amcAssetId").is(assetId));

        wszccTemplate.remove(query, AssetAdditional.class);

        Query queryComment = new Query();
        queryComment.addCriteria(Criteria.where("amcAssetId").is(assetId));
        wszccTemplate.remove(queryComment, AssetComment.class);

        Query queryAssetDocument = new Query();
        queryAssetDocument.addCriteria(Criteria.where("amcAssetId").is(assetId));
        wszccTemplate.remove(queryAssetDocument, AssetDocument.class);

        Query queryAssetImage = new Query();
        queryAssetImage.addCriteria(Criteria.where("amcAssetId").is(assetId));
        List<AssetImage> assetImages = wszccTemplate.find(queryAssetImage, AssetImage.class);
        if(!CollectionUtils.isEmpty(assetImages)){
            try {
                for (AssetImage assetImage : assetImages) {
                    amcOssFileService.delFileInOss(assetImage.getOssPath());
                    wszccTemplate.remove(assetImage);
                }
            }catch (Exception ex){
                log.error("Failed to delete ossFile:", ex);
            }
        }



    }

    @Override
    public List<AmcAssetDetailVo> queryAssetDetails(Long amcDebtId) throws Exception {
        AmcAssetExample amcAssetExample = new AmcAssetExample();
        amcAssetExample.createCriteria().andDebtIdEqualTo(amcDebtId);
        List<AmcAsset> amcAssets = amcAssetMapper.selectByExample(amcAssetExample);
        List<AmcAssetDetailVo> amcAssetDetailVos = new ArrayList<>();
        for(AmcAsset amcAsset: amcAssets){
            amcAssetDetailVos.add(queryDetailMongoForAmcAsset(amcAsset));
        }
        return amcAssetDetailVos;
    }


    @Override

    public List<AmcAssetVo> queryAssetPage(int offset, int pageSize, Map<String, Object> queryParam,
        Map<String, Direction> orderByParam) throws Exception {


        AmcAssetExample amcAssetExample = getAmcAssetExampleWithQueryParam(queryParam);
        amcAssetExample.setOrderByClause(SQLUtils.getOrderBy(orderByParam));
        RowBounds rowBounds = new RowBounds(offset, pageSize);
        List<AmcAsset> amcAssets = amcAssetMapper.selectByExampleWithRowbounds(amcAssetExample, rowBounds);
        return getAssetListVo(amcAssets);


    }

    private void setImages(List<AssetImage> assetImages, List<AmcAssetVo> amcAssetVos){
      Map<Long, AssetImage> assetImageMap = assetImages.stream().filter(item->item.getAmcAssetId() != null)
          .collect(Collectors.toMap(item->item.getAmcAssetId(), item-> item));

        for(int idx = 0; idx < amcAssetVos.size(); idx++){
          if(assetImageMap.containsKey(amcAssetVos.get(idx).getId())){
            amcAssetVos.get(idx).setAssetImage(assetImageMap.get(amcAssetVos.get(idx).getId()));
          }
        }
    }

  private void setImagesAndKeepOrder(List<AssetImage> assetImages, List<AmcAssetVo> amcAssetVos){

    Iterator<AmcAssetVo> iteratorA =  amcAssetVos.iterator();
    Map<Long, AssetImage> assetImageMap = assetImages.stream().collect(Collectors.toMap(item->item.getAmcAssetId(), item->item));
    while(iteratorA.hasNext()){
      AmcAssetVo amcAssetVoCurrent = iteratorA.next();
      if(assetImageMap.containsKey(amcAssetVoCurrent.getId())){
        amcAssetVoCurrent.setAssetImage(assetImageMap.get(amcAssetVoCurrent.getId()));
      }

    }


  }

    private void setAddInfo(AssetAdditional additional, List<AmcAssetVo> amcAssetVos) {
        Iterator<AmcAssetVo> iterator =  amcAssetVos.iterator();
        while(iterator.hasNext()){
            AmcAssetVo item = iterator.next();
            if(item.getId().equals(additional.getAmcAssetId())){
                item.setAssetAdditional(additional);
                break;
            }
        }
    }

    private void setAddInfos(List<AssetAdditional> assetAdditionals, List<AmcAssetVo> amcAssetVos) {
      Map<Long, AssetAdditional> assetAdditionalMap = assetAdditionals.stream()
          .filter(item -> item.getAmcAssetId() != null)
          .collect(Collectors.toMap(item -> item.getAmcAssetId(), item -> item));
      Iterator<AssetAdditional> iteratorAdd = assetAdditionals.iterator();
      for (int idx = 0; idx < amcAssetVos.size(); idx++) {
        if (assetAdditionalMap.containsKey(amcAssetVos.get(idx).getId())) {
          amcAssetVos.get(idx)
              .setAssetAdditional(assetAdditionalMap.get(amcAssetVos.get(idx).getId()));
        }

      }
    }






    private void removeAssetsById(Long id, List<AmcAssetVo> origList){
        Iterator<AmcAssetVo> iterator =  origList.iterator();
        while(iterator.hasNext()){
            AmcAssetVo item = iterator.next();
            if(item.getId() == id){
                origList.remove(item);
                break;
            }
        }
    }


    @Override
    public Long getAssetCount(Map<String, Object> queryParam) throws Exception {
        AmcAssetExample amcAssetExample = getAmcAssetExampleWithQueryParam(queryParam);
        return amcAssetMapper.countByExample(amcAssetExample);
    }


    @Override
    @Cacheable
    public List<AmcAsset> getSimpleAssets(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)){
            return new ArrayList<>();
        }
        AmcAssetExample amcAssetExample = new AmcAssetExample();
        amcAssetExample.createCriteria().andIdIn(ids);
        return amcAssetMapper.selectByExample(amcAssetExample);
    }

    @Override
    public List<AmcAsset> getAssetByDebtAndAssetTitle(String debtTitle, String assetTitle) throws Exception {
        AmcAssetExample amcAssetExample = new AmcAssetExample();
        List<AmcDebt> amcDebts = amcDebtService.getDebtByTiltle(debtTitle);
        if(CollectionUtils.isEmpty(amcDebts)){
            throw ExceptionUtils.getAmcException(AmcExceptions.NO_AMCDEBT_AVAILABLE, String.format("%s not available", debtTitle));
        }
        amcAssetExample.createCriteria().andDebtIdIn(amcDebts.stream().map(item->item.getId()).collect(Collectors.toList()))
        .andTitleEqualTo(assetTitle);
        List<AmcAsset> amcAssets = amcAssetMapper.selectByExample(amcAssetExample);

        return amcAssets;
    }

  @Override
  public List<AmcAssetVo> getAssetByTitleLike(String assetTitle) throws Exception {
      AmcAssetExample amcAssetExample = new AmcAssetExample();
      StringBuilder sb = new StringBuilder("%");
      amcAssetExample.createCriteria().andTitleLike(sb.append(assetTitle).append("%").toString());
      List<AmcAsset> amcAssets = amcAssetMapper.selectByExample(amcAssetExample);



      return getAssetListVo(amcAssets);
  }

  List<AmcAssetVo> getAssetListVo(List<AmcAsset> amcAssets) throws Exception {
    if(CollectionUtils.isEmpty(amcAssets)){
      return new ArrayList<>();
    }
    List<Long> assetIds = amcAssets.stream().map(item -> item.getId()).collect(Collectors.toUnmodifiableList());

    Query query = new Query();
    query.addCriteria(Criteria.where("amcAssetId").in(assetIds));
    List<AssetAdditional> assetAdditionals = wszccTemplate.find(query, AssetAdditional.class);
    query = new Query();
    query.addCriteria(Criteria.where("amcAssetId").in(assetIds).and("tag").is(ImageClassEnum.MAIN.getId()));
    List<AssetImage> assetImages = wszccTemplate.find(query, AssetImage.class);
    List<AmcAssetVo> amcAssetVos = Dao2VoUtils.convertDoList2VoList(amcAssets);
    setAddInfos(assetAdditionals, amcAssetVos);
    setImages(assetImages, amcAssetVos);
    return amcAssetVos;
  }

  @Override
    public String getAssetOssPrepath(Long assetId){
        return new StringBuilder(ImagePathClassEnum.ASSET.getName()).append("/").append(envName).append("/").append(assetId).append("/").toString();
    }

    @Override
    public AssetImage saveImageInfo( AssetImage assetImage) {

        Query query = new Query();
        if(assetImage.getTag() == ImageClassEnum.MAIN.getId()){
            query.addCriteria(Criteria.where("amcAssetId").is(assetImage.getAmcAssetId()).and("tag").is(ImageClassEnum.MAIN.getId()));
            List<AssetImage> assetImages =  wszccTemplate.find(query, AssetImage.class);
            if(!CollectionUtils.isEmpty(assetImages)){
                log.info("now need change history main image");
                for(AssetImage assetImageItem: assetImages){
                    assetImageItem.setTag(ImageClassEnum.OTHER.getId());
                    wszccTemplate.save(assetImageItem);
//                    try {
//                        amcOssFileService.delFileInOss(assetImage.getOssPath());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        log.error("Failed to del file on oss with osspath:"+ assetImage.getOssPath(), e);
//                    }
                }
            }
//            wszccTemplate.save(assetImage);
        }
        query = new Query();
        query.addCriteria(Criteria.where("ossPath").is(assetImage.getOssPath()).and("amcAssetId").is(assetImage.getAmcAssetId()));
        List<AssetImage> assetImages =  wszccTemplate.find(query, AssetImage.class);

        if(!CollectionUtils.isEmpty(assetImages)){
            log.info("there is duplicate image, just update it");
            AmcBeanUtils.copyProperties(assetImage, assetImages.get(0));
            wszccTemplate.save(assetImages.get(0));
        }else{
            log.info("there is no image, just insert it");
            wszccTemplate.save(assetImage);
        }

        AmcAsset amcAsset = amcAssetMapper.selectByPrimaryKey(assetImage.getAmcAssetId());
        amcAsset.setHasImg(HasImageEnum.HASIMAGE.getStatus());
        amcAssetMapper.updateByPrimaryKeySelective(amcAsset);
        return assetImage;

    }


    @Override
    public AssetDocument saveDoc( AssetDocument assetDocument) {
        Query query = new Query();
        query.addCriteria(Criteria.where("ossPath").is(assetDocument.getOssPath()).and("amcAssetId").is(assetDocument.getAmcAssetId()));
        List<AssetDocument> assetDocuments = wszccTemplate.find(query, AssetDocument.class);
        if(!CollectionUtils.isEmpty(assetDocuments)){
            if(assetDocuments.size() > 1){
                for(int idx = 1; idx < assetDocuments.size(); idx ++){
                    wszccTemplate.remove(assetDocuments.get(idx));
                }
            }
            Update update = new Update();
            update.set(assetDocuments.get(0).get_id(), assetDocuments);
            return wszccTemplate.findAndModify(query, update, AssetDocument.class);
        }else{
            return wszccTemplate.insert(assetDocument);
        }

    }

    @Override
    public void delImage(AssetImage assetImage) {
        Query query = new Query();
        query.addCriteria(Criteria.where("amcAssetId").is(assetImage.getAmcAssetId()).and("ossPath").is(assetImage.getOssPath()));
        wszccTemplate.findAllAndRemove(query, AssetImage.class);
    }

    @Override
    public Long getAssetCountWithDebtIds(List<Long> amcDebtIds) {
        if(CollectionUtils.isEmpty(amcDebtIds)){
            return 0L;
        }
        AmcAssetExample amcAssetExample = new AmcAssetExample();
        amcAssetExample.createCriteria().andDebtIdIn(amcDebtIds);
        return amcAssetMapper.countByExample(amcAssetExample);
    }

    @Override
    public void setRecomm(List<Long> assetIds, int isRecomm) {

        AmcAssetExample amcAssetExample = new AmcAssetExample();
        amcAssetExample.createCriteria().andIdIn(assetIds);
        List<AmcAsset> amcAssets = amcAssetMapper.selectByExample(amcAssetExample);
        if(CollectionUtils.isEmpty(amcAssets)){
            return;
        }
        for(AmcAsset amcAsset: amcAssets){
            amcAsset.setIsRecom(isRecomm);
            amcAssetMapper.updateByPrimaryKey(amcAsset);
        }
    }

    @Override
    public List<AmcAssetVo> getAssetsByIds(List<Long> assetIds) throws Exception {
        if(CollectionUtils.isEmpty(assetIds)){
            return new ArrayList<>();
        }
        AmcAssetExample amcAssetExample = new AmcAssetExample();
        amcAssetExample.createCriteria().andIdIn(assetIds);
        List<AmcAsset> amcAssets = amcAssetMapper.selectByExample(amcAssetExample);
        List<AmcAssetVo> amcAssetVos = Dao2VoUtils.convertDoList2VoList(amcAssets);
        if(CollectionUtils.isEmpty(amcAssetVos)){
            return amcAssetVos;
        }
        Map<Long, AmcAssetVo> amcAssetVoMap = amcAssetVos.stream().collect(Collectors.toMap(item-> item.getId(),
            item->item));
        Query query = new Query();
        query.addCriteria(Criteria.where("amcAssetId").in(amcAssetVoMap.keySet()));
        List<AssetAdditional> assetAdditionals = wszccTemplate.find(query, AssetAdditional.class);
        query = new Query();
        query.addCriteria(Criteria.where("amcAssetId").in(amcAssetVoMap.keySet()).and("tag").is(ImageClassEnum.MAIN.getId()));
        List<AssetImage> assetImages = wszccTemplate.find(query, AssetImage.class);




        for(AssetAdditional additional: assetAdditionals){
            if(amcAssetVoMap.containsKey(additional.getAmcAssetId())){

                amcAssetVoMap.get(additional.getAmcAssetId()).setAssetAdditional(additional);

            }
        }
        for(AssetImage assetImage: assetImages){
            if(amcAssetVoMap.containsKey(assetImage.getAmcAssetId())){
                amcAssetVoMap.get(assetImage.getAmcAssetId()).setAssetImage(assetImage);
            }
        }
        List <AmcAssetVo> amcAssetVosList = new ArrayList<>(amcAssetVoMap.values());
//            Collections.sort(amcAssetVosList, amcAssetVoComparator);
        return amcAssetVosList;

    }

  @Override
  public List<AmcAssetVo> getLatestNumOfAssets(int num) throws Exception {

    AmcAssetExample amcAssetExample = new AmcAssetExample();
    amcAssetExample.createCriteria().andPublishStateEqualTo(PublishStateEnum.PUBLISHED.getStatus());
    StringBuilder stringBuilder = new StringBuilder(" has_img desc ,  publish_date desc limit");
    stringBuilder.append(" ").append(num).append(" ");
    amcAssetExample.setOrderByClause(stringBuilder.toString());
    List<AmcAsset> amcAssets = amcAssetMapper.selectByExample(amcAssetExample);
    List<AmcAssetVo> amcAssetVos = Dao2VoUtils.convertDoList2VoList(amcAssets);
    if(CollectionUtils.isEmpty(amcAssetVos)){
      return amcAssetVos;
    }
    Map<Long, AmcAssetVo> amcAssetVoMap = amcAssetVos.stream().collect(Collectors.toMap(item-> item.getId(),
        item->item));
    Query query = new Query();
    query.addCriteria(Criteria.where("amcAssetId").in(amcAssetVoMap.keySet()));
    List<AssetAdditional> assetAdditionals = wszccTemplate.find(query, AssetAdditional.class);
    query = new Query();
    query.addCriteria(Criteria.where("amcAssetId").in(amcAssetVoMap.keySet()).and("tag").is(ImageClassEnum.MAIN.getId()));
    List<AssetImage> assetImages = wszccTemplate.find(query, AssetImage.class);




    for(AssetAdditional additional: assetAdditionals){
      if(amcAssetVoMap.containsKey(additional.getAmcAssetId())){

        amcAssetVoMap.get(additional.getAmcAssetId()).setAssetAdditional(additional);

      }
    }
    for(AssetImage assetImage: assetImages){
      if(amcAssetVoMap.containsKey(assetImage.getAmcAssetId())){
        amcAssetVoMap.get(assetImage.getAmcAssetId()).setAssetImage(assetImage);
      }
    }
    List <AmcAssetVo> amcAssetVosList = new ArrayList<>(amcAssetVoMap.values());
//            Collections.sort(amcAssetVosList, amcAssetVoComparator);
    return amcAssetVosList;
  }

  @Override
    public AssetImage uploadAssetImage(String imagePath, String ossPrepath, Integer tag, Long assetId, String desc) throws Exception {
//    String prePath = ImagePathClassEnum.DEBT.getName() + "/" + debtId + "/";

        String ossPath =  amcOssFileService.handleFile2Oss(imagePath, ossPrepath);
        AssetImage assetImage = new AssetImage();

        assetImage.setOssPath(ossPath);
        assetImage.setTag(tag);
        assetImage.setOriginalName(imagePath);
        assetImage.setAmcAssetId(assetId);
        assetImage.setDescription(desc);
        return saveImageInfo( assetImage);


    }

    @Override
    public void patchRecomm() {
        int offset = 0;
        int pageSize = 20;
        AmcAssetExample amcAssetExample = new AmcAssetExample();
        amcAssetExample.setOrderByClause(" id desc ");
        RowBounds rowBounds = new RowBounds(offset, pageSize);
        List<AmcAsset> amcAssets = amcAssetMapper.selectByExampleWithRowbounds(amcAssetExample, rowBounds);
        boolean hasMore = true;
        if(CollectionUtils.isEmpty(amcAssets)){
            hasMore = false;
        }
        while(hasMore){
            for(AmcAsset amcAsset: amcAssets){
                Query query = new Query();
                query.addCriteria(Criteria.where("amcAssetId").in(amcAsset.getId()));
                List<AssetAdditional> assetAdditionals = wszccTemplate.find(query, AssetAdditional.class);
                if(CollectionUtils.isEmpty(assetAdditionals)){
                    continue;
                }else {
//                    amcAsset.setIsRecom(assetAdditionals.get(0).getIsRecommanded());
                    amcAssetMapper.updateByPrimaryKey(amcAsset);
                }

            }
            offset += pageSize;
            rowBounds = new RowBounds(offset, pageSize);
            amcAssets = amcAssetMapper.selectByExampleWithRowbounds(amcAssetExample, rowBounds);
            if(CollectionUtils.isEmpty(amcAssets)){
                hasMore = false;
                break;
            }
        }
    }

  @Override
  public List<AmcAssetVo> getFloorFilteredAsset(AmcFilterContentAsset filterAsset)
      throws Exception {
      AmcAssetExample amcAssetExample = getAmcAssetExampleWithFloorFilter(filterAsset);
      List<AmcAsset> amcAssets = amcAssetMapper.selectByExample(amcAssetExample);
    List<AmcAssetVo> amcAssetVos = Dao2VoUtils.convertDoList2VoList(amcAssets);
    if(CollectionUtils.isEmpty(amcAssetVos)){
      return amcAssetVos;
    }
    List<Long> amcAssetIds = amcAssetVos.stream().map(item->item.getId()).collect(Collectors.toList());
    Query query = new Query();
    query.addCriteria(Criteria.where("amcAssetId").in(amcAssetIds));
    List<AssetAdditional> assetAdditionals = wszccTemplate.find(query, AssetAdditional.class);
    query = new Query();
    query.addCriteria(Criteria.where("amcAssetId").in(amcAssetIds).and("tag").is(ImageClassEnum.MAIN.getId()));
    List<AssetImage> assetImages = wszccTemplate.find(query, AssetImage.class);
    setImagesAndKeepOrder(assetImages, amcAssetVos);
    setAddInfos(assetAdditionals, amcAssetVos);


    return amcAssetVos;

  }

  @Override
  public List<AmcAssetVo> getFloorFilteredAsset(AmcFilterContentAsset filterAsset,
      AmcSaleGetListInPage pageInfo) throws Exception {
    AmcAssetExample amcAssetExample = getAmcAssetExampleWithFloorFilter(filterAsset, pageInfo);
    List<AmcAsset> amcAssets = amcAssetMapper.selectByExample(amcAssetExample);
    List<AmcAssetVo> amcAssetVos = Dao2VoUtils.convertDoList2VoList(amcAssets);
    if(CollectionUtils.isEmpty(amcAssetVos)){
      return amcAssetVos;
    }
    List<Long> amcAssetIds = amcAssetVos.stream().map(item->item.getId()).collect(Collectors.toList());
    Query query = new Query();
    query.addCriteria(Criteria.where("amcAssetId").in(amcAssetIds));
    List<AssetAdditional> assetAdditionals = wszccTemplate.find(query, AssetAdditional.class);
    query = new Query();
    query.addCriteria(Criteria.where("amcAssetId").in(amcAssetIds).and("tag").is(ImageClassEnum.MAIN.getId()));
    List<AssetImage> assetImages = wszccTemplate.find(query, AssetImage.class);
    setImagesAndKeepOrder(assetImages, amcAssetVos);
    setAddInfos(assetAdditionals, amcAssetVos);


    return amcAssetVos;
  }

  @Override
  public List<Long> getLatestIds() {
      AmcAssetExample amcAssetExample = new AmcAssetExample();
      amcAssetExample.createCriteria().andPublishStateEqualTo(PublishStateEnum.PUBLISHED.getStatus());
      amcAssetExample.setOrderByClause("has_img desc ,  id desc limit 5");
      List<AmcAsset> amcAssets = amcAssetMapper.selectByExample(amcAssetExample);

    return amcAssets.stream().map(item-> item.getId()).collect(Collectors.toList());
  }

  @Override
  public List<AmcAssetVo> getFloorFilteredRandomAssets(AmcFilterContentAsset filterAsset,
      AmcSaleGetRandomListInPage pageInfo) throws Exception {
    AmcAssetExample amcAssetExample = getAmcAssetExampleWithRandomFloorFilter(filterAsset, pageInfo);
    List<AmcAsset> amcAssets = amcAssetMapper.selectByExample(amcAssetExample);
    List<AmcAssetVo> amcAssetVos = Dao2VoUtils.convertDoList2VoList(amcAssets);
    if(CollectionUtils.isEmpty(amcAssetVos)){
      return amcAssetVos;
    }
    List<Long> amcAssetIds = amcAssetVos.stream().map(item->item.getId()).collect(Collectors.toList());
    Query query = new Query();
    query.addCriteria(Criteria.where("amcAssetId").in(amcAssetIds));
    List<AssetAdditional> assetAdditionals = wszccTemplate.find(query, AssetAdditional.class);
    query = new Query();
    query.addCriteria(Criteria.where("amcAssetId").in(amcAssetIds).and("tag").is(ImageClassEnum.MAIN.getId()));
    List<AssetImage> assetImages = wszccTemplate.find(query, AssetImage.class);
    setImagesAndKeepOrder(assetImages, amcAssetVos);
    setAddInfos(assetAdditionals, amcAssetVos);


    return amcAssetVos;
  }

  private AmcAssetExample getAmcAssetExampleWithRandomFloorFilter(AmcFilterContentAsset filterAsset, AmcSaleGetRandomListInPage pageInfo) {

    AmcAssetExample amcAssetExample = new AmcAssetExample();
    StringBuilder sbOrderBy = new StringBuilder();

    if(filterAsset.getOrderByField() != OrderByFieldEnum.VISITCOUNT.getId()){
      sbOrderBy.append(" rand() ");
    }else{
      sbOrderBy.append(" visit_count desc ");
    }
    if(pageInfo.getPgSize() > 0 ){
      sbOrderBy.append(" limit ").append(pageInfo.getPgSize()).append(" ");
    }else{
      sbOrderBy.append(" limit ").append(0).append(" ");
    }
    amcAssetExample.setOrderByClause(sbOrderBy.toString());

    AmcAssetExample.Criteria criteria = amcAssetExample.createCriteria();



    if(!CollectionUtils.isEmpty(filterAsset.getAssetTypes())){
      criteria.andTypeIn(filterAsset.getAssetTypes());
    }
    if(!CollectionUtils.isEmpty(filterAsset.getCityCode())){
      if(filterAsset.getCityCode().size() == 1){

        criteria.andCityEqualTo(filterAsset.getCityCode().get(0));

      }else{
        criteria.andCityIn(filterAsset.getCityCode());
      }

    }
    if(!CollectionUtils.isEmpty(filterAsset.getProvCode())){
      if(filterAsset.getProvCode().size() == 1){

        criteria.andProvinceEqualTo(filterAsset.getProvCode().get(0));

      }else{
        criteria.andProvinceIn(filterAsset.getProvCode());
      }

    }
    if(filterAsset.getOrderByField() != OrderByFieldEnum.VISITCOUNT.getId()){
      criteria.andHasImgEqualTo(HasImageEnum.HASIMAGE.getStatus());
    }

    if(!CollectionUtils.isEmpty(filterAsset.getValuation())){
      Long lowLimit;
      Long highLimit;
      if(filterAsset.getValuation().get(0) < filterAsset.getValuation().get(1)){
        lowLimit = filterAsset.getValuation().get(0)*100;
        highLimit = filterAsset.getValuation().get(1)*100;
      }else{
        highLimit = filterAsset.getValuation().get(0)*100;
        lowLimit = filterAsset.getValuation().get(1)*100;
      }
      criteria.andTotalValuationBetween(lowLimit, highLimit);
    }


    if(!CollectionUtils.isEmpty(filterAsset.getArea())){
      Long lowLimit;
      Long highLimit;
      if(filterAsset.getArea().get(0) < filterAsset.getArea().get(1)){
        lowLimit = filterAsset.getArea().get(0)*100;
        highLimit = filterAsset.getArea().get(1)*100;
      }else{
        highLimit = filterAsset.getArea().get(0)*100;
        lowLimit = filterAsset.getArea().get(1)*100;
      }
      criteria.andBuildingAreaBetween(lowLimit, highLimit);
    }

    if(!CollectionUtils.isEmpty(filterAsset.getLandArea())){

      Long lowLimit;
      Long highLimit;
      if(filterAsset.getLandArea().get(0) < filterAsset.getLandArea().get(1)){
        lowLimit = filterAsset.getLandArea().get(0)*100;
        highLimit = filterAsset.getLandArea().get(1)*100;
      }else{
        highLimit = filterAsset.getLandArea().get(0)*100;
        lowLimit = filterAsset.getLandArea().get(1)*100;
      }
      criteria.andLandAreaBetween(lowLimit, highLimit);
    }


    if(!CollectionUtils.isEmpty(filterAsset.getSealStatus())){
      criteria.andSealedStateIn(filterAsset.getSealStatus());
    }





    criteria.andPublishStateEqualTo(PublishStateEnum.PUBLISHED.getStatus());
    if(pageInfo.getAssetIds() != null && !CollectionUtils.isEmpty(pageInfo.getAssetIds()) ){
      criteria.andIdNotIn(pageInfo.getAssetIds());
    }

    return amcAssetExample;

    }

  private AmcAssetExample getAmcAssetExampleWithFloorFilter(AmcFilterContentAsset filterAsset, AmcSaleGetListInPage pageInfo) {
    AmcAssetExample amcAssetExample = new AmcAssetExample();
    StringBuilder sbOrderBy = new StringBuilder();

    if(filterAsset.getOrderByField() == OrderByFieldEnum.VISITCOUNT.getId()){
      sbOrderBy.append(" has_img desc , id desc");
    }else{
      sbOrderBy.append(" visit_count desc ");
    }
    if(pageInfo.getPgSize() > 0 ){
      sbOrderBy.append(" limit ").append(pageInfo.getPgSize()).append(" ");
    }else{
      sbOrderBy.append(" limit ").append(0).append(" ");
    }
    amcAssetExample.setOrderByClause(sbOrderBy.toString());

    AmcAssetExample.Criteria criteria = amcAssetExample.createCriteria();



    if(!CollectionUtils.isEmpty(filterAsset.getAssetTypes())){
      criteria.andTypeIn(filterAsset.getAssetTypes());
    }
    if(!CollectionUtils.isEmpty(filterAsset.getCityCode())){
      if(filterAsset.getCityCode().size() == 1){

        criteria.andCityEqualTo(filterAsset.getCityCode().get(0));

      }else{
        criteria.andCityIn(filterAsset.getCityCode());
      }

    }
    if(!CollectionUtils.isEmpty(filterAsset.getProvCode())){
      if(filterAsset.getProvCode().size() == 1){

        criteria.andProvinceEqualTo(filterAsset.getProvCode().get(0));

      }else{
        criteria.andProvinceIn(filterAsset.getProvCode());
      }

    }

    if(!CollectionUtils.isEmpty(filterAsset.getValuation())){
      Long lowLimit;
      Long highLimit;
      if(filterAsset.getValuation().get(0) < filterAsset.getValuation().get(1)){
        lowLimit = filterAsset.getValuation().get(0)*100;
        highLimit = filterAsset.getValuation().get(1)*100;
      }else{
        highLimit = filterAsset.getValuation().get(0)*100;
        lowLimit = filterAsset.getValuation().get(1)*100;
      }
      criteria.andTotalValuationBetween(lowLimit, highLimit);
    }


    if(!CollectionUtils.isEmpty(filterAsset.getArea())){
      Long lowLimit;
      Long highLimit;
      if(filterAsset.getArea().get(0) < filterAsset.getArea().get(1)){
        lowLimit = filterAsset.getArea().get(0)*100;
        highLimit = filterAsset.getArea().get(1)*100;
      }else{
        highLimit = filterAsset.getArea().get(0)*100;
        lowLimit = filterAsset.getArea().get(1)*100;
      }
      criteria.andBuildingAreaBetween(lowLimit, highLimit);
    }

    if(!CollectionUtils.isEmpty(filterAsset.getLandArea())){

      Long lowLimit;
      Long highLimit;
      if(filterAsset.getLandArea().get(0) < filterAsset.getLandArea().get(1)){
        lowLimit = filterAsset.getLandArea().get(0)*100;
        highLimit = filterAsset.getLandArea().get(1)*100;
      }else{
        highLimit = filterAsset.getLandArea().get(0)*100;
        lowLimit = filterAsset.getLandArea().get(1)*100;
      }
      criteria.andLandAreaBetween(lowLimit, highLimit);
    }


    if(!CollectionUtils.isEmpty(filterAsset.getSealStatus())){
      criteria.andSealedStateIn(filterAsset.getSealStatus());
    }





    criteria.andPublishStateEqualTo(PublishStateEnum.PUBLISHED.getStatus());
    if(pageInfo.getLastAssetId() > 0 ){
      criteria.andIdLessThan(pageInfo.getLastAssetId());
    }

    return amcAssetExample;

  }

  private AmcAssetExample getAmcAssetExampleWithFloorFilter(AmcFilterContentAsset filterAsset) {
    AmcAssetExample amcAssetExample = new AmcAssetExample();
    if(filterAsset.getOrderByField() == -1){
      amcAssetExample.setOrderByClause(" has_img desc , id desc");
    }else{
      amcAssetExample.setOrderByClause(" visit_count desc ");
    }

    AmcAssetExample.Criteria criteria = amcAssetExample.createCriteria();
    StringBuilder sb = new StringBuilder();


    if(!CollectionUtils.isEmpty(filterAsset.getAssetTypes())){
      criteria.andTypeIn(filterAsset.getAssetTypes());
    }
    if(!CollectionUtils.isEmpty(filterAsset.getCityCode())){
      if(filterAsset.getCityCode().size() == 1){

          criteria.andCityEqualTo(filterAsset.getCityCode().get(0));

      }else{
        criteria.andCityIn(filterAsset.getCityCode());
      }

    }
    if(!CollectionUtils.isEmpty(filterAsset.getProvCode())){
      if(filterAsset.getProvCode().size() == 1){

          criteria.andProvinceEqualTo(filterAsset.getProvCode().get(0));

      }else{
        criteria.andProvinceIn(filterAsset.getProvCode());
      }

    }

    if(!CollectionUtils.isEmpty(filterAsset.getValuation())){
      Long lowLimit;
      Long highLimit;
      if(filterAsset.getValuation().get(0) < filterAsset.getValuation().get(1)){
        lowLimit = filterAsset.getValuation().get(0)*100;
        highLimit = filterAsset.getValuation().get(1)*100;
      }else{
        highLimit = filterAsset.getValuation().get(0)*100;
        lowLimit = filterAsset.getValuation().get(1)*100;
      }
      criteria.andTotalValuationBetween(lowLimit, highLimit);
    }


    if(!CollectionUtils.isEmpty(filterAsset.getArea())){
      Long lowLimit;
      Long highLimit;
      if(filterAsset.getArea().get(0) < filterAsset.getArea().get(1)){
        lowLimit = filterAsset.getArea().get(0)*100;
        highLimit = filterAsset.getArea().get(1)*100;
      }else{
        highLimit = filterAsset.getArea().get(0)*100;
        lowLimit = filterAsset.getArea().get(1)*100;
      }
      criteria.andBuildingAreaBetween(lowLimit, highLimit);
    }

    if(!CollectionUtils.isEmpty(filterAsset.getLandArea())){

      Long lowLimit;
      Long highLimit;
      if(filterAsset.getLandArea().get(0) < filterAsset.getLandArea().get(1)){
        lowLimit = filterAsset.getLandArea().get(0)*100;
        highLimit = filterAsset.getLandArea().get(1)*100;
      }else{
        highLimit = filterAsset.getLandArea().get(0)*100;
        lowLimit = filterAsset.getLandArea().get(1)*100;
      }
      criteria.andLandAreaBetween(lowLimit, highLimit);
    }


    if(!CollectionUtils.isEmpty(filterAsset.getSealStatus())){
      criteria.andSealedStateIn(filterAsset.getSealStatus());
    }

    if(filterAsset.getOrderByField() == OrderByFieldEnum.VISITCOUNT.getId()){
      if(!StringUtils.isEmpty(amcAssetExample.getOrderByClause())){
        sb.append(amcAssetExample.getOrderByClause()).append(", visit_count desc ");
      }else{
        amcAssetExample.setOrderByClause(" visit_count desc ");
      }
    }



    criteria.andPublishStateEqualTo(PublishStateEnum.PUBLISHED.getStatus());
    return amcAssetExample;
  }

  @Override
    public Map<Long, List<String>> uploadAmcAssetsImage2WechatByIds(List<Long> assetIds) {
        Query query = new Query();
        query.addCriteria(Criteria.where("amcAssetId").in(assetIds));
        List<AssetImage> assetImages = wszccTemplate.find(query, AssetImage.class);
        Map<Long, List<String>> assetImageMap = new HashMap<>();
        Map<Long, String> assetMainImage = new HashMap<>();
        UploadImg2WechatReq.Builder builder = UploadImg2WechatReq.newBuilder();
        for(AssetImage assetImage : assetImages){
            if(!assetImageMap.containsKey(assetImage.getAmcAssetId())){
                assetImageMap.put(assetImage.getAmcAssetId(), new ArrayList<>());
            }


            if( ImageClassEnum.MAIN.getId() == assetImage.getTag()){
                assetMainImage.put(assetImage.getAmcAssetId(), assetImage.getOssPath());
            }else{
                assetImageMap.get(assetImage.getAmcAssetId()).add(assetImage.getOssPath());
            }
        }
        Iterator it = assetImageMap.entrySet().iterator();
        AmcAssetImage.Builder imagesBuilder = AmcAssetImage.newBuilder();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry<Long, List<String>>)it.next();
            imagesBuilder.clear();
            if(!CollectionUtils.isEmpty((List<String>)pair.getValue())){
                ((List<String>)pair.getValue()).stream().forEach(item -> imagesBuilder.addAmcAssetImages(item));
            }
            imagesBuilder.setAmcAssetId((Long)pair.getKey());
            if(assetMainImage.containsKey((Long)pair.getKey())){
                imagesBuilder.setAmcAssetMainImage(assetMainImage.get((Long)pair.getKey()));
            }
            builder.addAmcAssetImages(imagesBuilder);



        }
        UploadImg2WechatResp resp = wechatGrpcService.uploadImage2Wechat(builder.build());
        Map<Long, List<String>> result = new HashMap<>();
        Query querySubItem = null ;
        for(ImageUploadResult item: resp.getResultsList()){
            Long assetId = item.getAmcAssetId();
            result.put(assetId, new ArrayList<>());
            for(WechatAssetImage subItem: item.getWechatAssetImagesList()){
             if(!StringUtils.isEmpty(subItem.getWechatAssetImage())){
                 querySubItem = new Query();
                 querySubItem.addCriteria(Criteria.where("amcAssetId").is(assetId).and("ossPath").is(subItem.getAmcAssetImage()));

                 List<AssetImage> assetImageList = wszccTemplate.find(querySubItem, AssetImage.class);
                 if(CollectionUtils.isEmpty(assetImageList)){
                    log.error("failed to find image for amcAssetId:{} and ossPath:{}", assetId, subItem.getAmcAssetImage());
                 }else{
                     assetImageList.get(0).setWechatPath(subItem.getWechatAssetImage());
//                     if( ImageClassEnum.MAIN.getId() == assetImageList.get(0).getTag() && !StringUtils.isEmpty(item.getMediaId())){
//                         assetImageList.get(0).setMediaId(item.getMediaId());
//                     }
                     wszccTemplate.save(assetImageList.get(0));
                     result.get(assetId).add(subItem.getWechatAssetImage());

                 }
             }

            }
            querySubItem = null;
            if(!StringUtils.isEmpty(item.getMediaId())){
                querySubItem = new Query();
                querySubItem.addCriteria(Criteria.where("amcAssetId").is(assetId).and("tag").is(ImageClassEnum.MAIN.getId()));
                List<AssetImage> assetImageList = wszccTemplate.find(querySubItem, AssetImage.class);
                if(!CollectionUtils.isEmpty(assetImageList)){
                    assetImageList.get(0).setMediaId(item.getMediaId());
                    assetImageList.get(0).setWechatPath(item.getMediaIdUrl());
                    wszccTemplate.save(assetImageList.get(0));
                }
            }

        }
        for(Long assetId : assetIds){
            if(!result.containsKey(assetId)){
                //just for frontend can handle this
                result.put(assetId, new ArrayList<>());
            }
        }
        return result;
    }

    @Override
    public Map<Long, Map<Long, List<AssetImage>>> getAssetImgsByDebtIds(List<Long> debtIds) {
        AmcAssetExample amcAssetExample = new AmcAssetExample();
        amcAssetExample.createCriteria().andDebtIdIn(debtIds);
        List<AmcAsset> amcAssets = amcAssetMapper.selectByExample(amcAssetExample);

        Map<Long, Map<Long, List<AssetImage>>> amcAssetMap = new HashMap<>();
        for(AmcAsset amcAsset: amcAssets){
            if(!amcAssetMap.containsKey(amcAsset.getDebtId())){
                amcAssetMap.put(amcAsset.getDebtId(), new HashMap());
            }
            Query query = new Query();
            query.addCriteria(Criteria.where("amcAssetId").in(amcAsset.getId()));
            List<AssetImage> amcAssetImages = wszccTemplate.find(query, AssetImage.class);
            amcAssetMap.get(amcAsset.getDebtId()).put(amcAsset.getId(), amcAssetImages);
            query = null;
        }



        return amcAssetMap;
    }

    @Override
    public Map<Long, List<AmcAsset>> getSimpleAssetsByDebtId(List<Long> debtIds) {
        AmcAssetExample amcAssetExample = new AmcAssetExample();
        amcAssetExample.createCriteria().andDebtIdIn(debtIds);
        List<AmcAsset> amcAssets = amcAssetMapper.selectByExample(amcAssetExample);
        Map<Long, List<AmcAsset>> assetDebtIdMap = new HashMap<>();
        for(AmcAsset amcAsset: amcAssets){
            if(!assetDebtIdMap.containsKey(amcAsset.getDebtId())){
                assetDebtIdMap.put(amcAsset.getDebtId(), new ArrayList<>());
            }
            assetDebtIdMap.get(amcAsset.getDebtId()).add(amcAsset);
        }
        return assetDebtIdMap;
    }

    private AmcAssetExample getAmcAssetExampleWithQueryParam(Map<String, Object> queryParam) throws Exception {
        AmcAssetExample amcAssetExample = new AmcAssetExample();
        AmcAssetExample.Criteria criteria = amcAssetExample.createCriteria();
        StringBuilder sb = new StringBuilder();
        boolean needDefaultPublishState = true;
        if(!CollectionUtils.isEmpty(queryParam)){
            for(Entry<String, Object> item: queryParam.entrySet()){
                if(item.getKey().equals(QueryParamEnum.PublishStates.name())){
                    criteria.andPublishStateIn((List) item.getValue());
                    needDefaultPublishState = false;
                }
                if(item.getKey().equals("DebtId")){
                    criteria.andDebtIdEqualTo((Long)item.getValue());
                }
                if(item.getKey().equals("EditStatus")){
                    criteria.andPublishStateEqualTo((Integer) item.getValue());
                }

                if(item.getKey().equals("Area")){
                    List<Long> areas = (List) item.getValue();
                    if(areas.get(0) < 0 && areas.get(1) > 0){
                        criteria.andBuildingAreaLessThanOrEqualTo( areas.get(1));
                    }else if(areas.get(0) > 0 && areas.get(1) <= 0){
                        criteria.andBuildingAreaGreaterThan(areas.get(0));
                    }else if(areas.get(0) > 0 && areas.get(1) > 0 && areas.get(0) < areas.get(1)){

                        criteria.andBuildingAreaLessThanOrEqualTo( areas.get(1));
                        criteria.andBuildingAreaGreaterThan(areas.get(0));
                    }
                    else if(areas.get(0) > 0 && areas.get(1) > 0 && areas.get(0) > areas.get(1)){

                        criteria.andBuildingAreaLessThanOrEqualTo( areas.get(0));
                        criteria.andBuildingAreaGreaterThan(areas.get(1));
                    }

                }

                if(item.getKey().equals("Valuation")){
                    List<Long> valuations = SQLUtils.amountFilterUpdate4DB((List)item.getValue());
                    if(valuations.get(0) < 0 && valuations.get(1) > 0){
                        criteria.andTotalValuationLessThanOrEqualTo(valuations.get(1));
                    }else if(valuations.get(1) < 0 && valuations.get(0) > 0){
                        criteria.andTotalValuationGreaterThan(valuations.get(0));
                    }else if(valuations.get(0) > 0 && valuations.get(1) > 0){
                        criteria.andTotalValuationBetween(valuations.get(0), valuations.get(1));
                    }else{
                        throw ExceptionUtils
                            .getAmcException(AmcExceptions.INVALID_AMOUNT_RANGE,  String.format("%d,%d",valuations.get(0), valuations.get(1))
                            );
                    }

                }

                if(item.getKey().equals("LandArea")){
                    List<Long> areas = (List) item.getValue();
                    if(areas.get(0) < 0 && areas.get(1) > 0){
                        criteria.andLandAreaBetween(0L, areas.get(1));
                    }else if(areas.get(0) > 0 && areas.get(1) <= 0){
                        criteria.andLandAreaGreaterThan(areas.get(0));
                    }else if(areas.get(0) > 0 && areas.get(1) > 0 && areas.get(0) < areas.get(1)){
                        criteria.andLandAreaLessThanOrEqualTo(areas.get(1));
                        criteria.andLandAreaGreaterThan(areas.get(0));
                    }else if(areas.get(0) > 0 && areas.get(1) > 0 && areas.get(0) > areas.get(1)){
                        criteria.andLandAreaLessThanOrEqualTo(areas.get(0));
                        criteria.andLandAreaGreaterThan(areas.get(1));
                    }

                }
                if(item.getKey().equals("SealedState")){
                    criteria.andSealedStateEqualTo((Integer) item.getValue());
                }

                if(item.getKey().equals("Title")){
                    sb.setLength(0);
                    criteria.andTitleLike(sb.append("%").append((String)item.getValue()).append("%").toString());
                }
                if(item.getKey().equals("AssetType")){
                    criteria.andTypeEqualTo((Integer) item.getValue());
                }
                if(item.getKey().equals("Location")){
                    List<String> locations = (List<String>) item.getValue();
                    if(locations.size() >=1 &&!StringUtils.isEmpty(locations.get(0))){
                        criteria.andProvinceEqualTo(StringUtils.trimWhitespace(locations.get(0)));
                    }
                    if(locations.size() >=2 && !StringUtils.isEmpty(locations.get(1))){
                        criteria.andCityEqualTo(StringUtils.trimWhitespace(locations.get(1)));
                    }
                    if(locations.size() >= 3 && !StringUtils.isEmpty(locations.get(2))){
                        criteria.andCountyEqualTo(StringUtils.trimWhitespace(locations.get(2)));
                    }
                }
                if(item.getKey().equals(QueryParamEnum.LocationCode.name()) && !CollectionUtils.isEmpty((List<String>)item.getValue())){
                    List<String> locationCodes = (List<String>) item.getValue();
                    String locationCode = locationCodes.get(0);
                    StringBuilder sbCode = new StringBuilder();
                    for(int idx = locationCode.length() -1; idx >= 0; idx--){
                        if(Character.compare(locationCode.charAt(idx), '0') == 0){
                            continue;
                        }else{
                            sbCode.append(locationCode.substring(0, idx+1));
                            break;
                        }

                    }
                    criteria.andCityLike(sbCode.append("%").toString());
                }
                if(item.getKey().equals("AmcContactorId")){
                    criteria.andAmcContactorIdEqualTo((Long)item.getValue());
                }
                if(item.getKey().equals(QueryParamEnum.DebtPackId.name())){
                    List<Long> debtIds = amcDebtService.getDebtIdsByPackIds((List<Long>)item.getValue());
                    if(CollectionUtils.isEmpty(debtIds)){
                        log.error("There is no debtIds for debtPackIds:{}, and it will return emtpy result set",
                            item.getValue().toString());
                        criteria.andDebtIdEqualTo(-1L);
                    }else{
                        criteria.andDebtIdIn(debtIds);
                    }

                }
                if(item.getKey().equals((QueryParamEnum.Recommand.name()))){
                    List<Integer> recomVals = (List<Integer>) item.getValue();
                    if(CollectionUtils.isEmpty(recomVals)){
                        log.error("There is no recom vals");
                    }else{
                        criteria.andIsRecomIn(recomVals);
                    }
                }

            }
        }
        if(needDefaultPublishState){
            criteria.andPublishStateNotEqualTo(PublishStateEnum.DELETED.getStatus());
        }
        return amcAssetExample;
    }



    @Override
    @Scheduled(cron = "${spring.task.scheduling.cronExpr}")
    public void checkGeoInfoWorker(){
        //1. travers asset table get address
        int offset = 0;
        int pageSize = 20;
        RowBounds rowBounds = new RowBounds(offset, pageSize);
        boolean stop = false;
        AmcAssetExample amcAssetExample = new AmcAssetExample();
        amcAssetExample.setOrderByClause("id desc");
        while(!stop){
            List<AmcAsset> amcAssets = amcAssetMapper.selectByExampleWithRowbounds(amcAssetExample, rowBounds);
            rowBounds = null;
            if(CollectionUtils.isEmpty(amcAssets)){
                log.info("geo info finding finished");
                stop = true;

            }else{
                Map<Long, AddressTmp> addresses = new HashMap<>();
                for(AmcAsset amcAsset : amcAssets){
                    AddressTmp addressTmp = new AddressTmp();
                    addressTmp.setAddress(amcAsset.getAddress());
                    addressTmp.setCity(amcAsset.getCity());
                    addresses.put(amcAsset.getId(), addressTmp);
                }
                findGeoAndUpdate(addresses);
                offset = offset + pageSize;
                rowBounds = new RowBounds(offset, pageSize);
            }

        }



        //2. call comnfunc to get GeoJson

        //3. save it into asset additional info

    }

    @Override
    public Map<Long, AssetAdditional> getAssetAdditions(Long amcDebtId) {
        AmcAssetExample amcAssetExample = new AmcAssetExample();
        amcAssetExample.createCriteria().andDebtIdEqualTo(amcDebtId);
        List<AmcAsset> amcAssets = amcAssetMapper.selectByExample(amcAssetExample);
        if(CollectionUtils.isEmpty(amcAssets)){
            log.error("No asset available for debtId:{}", amcDebtId);
            return null;
        }
        List<Long> assetIds = amcAssets.stream().map(item -> item.getId()).collect(Collectors.toList());
        Query query = new Query();
        query.addCriteria(Criteria.where("amcAssetId").in(assetIds));
        List<AssetAdditional> assetAdditionals =  wszccTemplate.find(query, AssetAdditional.class);
        Map<Long, AssetAdditional> assetAdditionalMap =
            assetAdditionals.stream().collect(Collectors.toMap(item-> (item.getAmcAssetId()), item-> item));
        return assetAdditionalMap;
    }

  @Override
  public Map<Long, AssetImage> getAssetImages(Long amcDebtId) {
         AmcAssetExample amcAssetExample = new AmcAssetExample();
      amcAssetExample.createCriteria().andDebtIdEqualTo(amcDebtId);
      List<AmcAsset> amcAssets = amcAssetMapper.selectByExample(amcAssetExample);
      if(CollectionUtils.isEmpty(amcAssets)){
        log.error("No asset available for debtId:{}", amcDebtId);
        return null;
      }
      List<Long> assetIds = amcAssets.stream().map(item -> item.getId()).collect(Collectors.toList());
      Query query = new Query();
      query.addCriteria(Criteria.where("amcAssetId").in(assetIds).and("tag").is(ImageClassEnum.MAIN.getId()));
      List<AssetImage> assetImages =  wszccTemplate.find(query, AssetImage.class);
      Map<Long, AssetImage> assetImageMap =
          assetImages.stream().collect(Collectors.toMap(item-> (item.getAmcAssetId()), item-> item));
      return assetImageMap;

  }

  @Override
    @Cacheable
    public List<AmcAssetGeoNear> queryByGeopoint(GeoJsonPoint geoJsonPoint) throws Exception {

        List<AmcAssetGeoNear> assetGeoNears = new ArrayList<>();
        assetGeoNears.add(queryNearByAssets(geoJsonPoint, new Integer[]{0, 100}));
        assetGeoNears.add(queryNearByAssets(geoJsonPoint, new Integer[]{100, 200}));
        assetGeoNears.add(queryNearByAssets(geoJsonPoint, new Integer[]{200, 300}));
        assetGeoNears.add(queryNearByAssets(geoJsonPoint, new Integer[]{300}));

        return assetGeoNears;
    }

    @Override
    public void patchAssetLocationWithCode() throws Exception {
        AmcAssetExample amcAssetExample = new AmcAssetExample();
        amcAssetExample.setOrderByClause(" id desc ");
        int offset = 0;
        int pageSize = 50;
        RowBounds rowBounds = new RowBounds(offset, pageSize);
        boolean haveMore = false;
        List<AmcAsset> amcAssets = amcAssetMapper.selectByExampleWithRowbounds(amcAssetExample, rowBounds);
        if(!CollectionUtils.isEmpty(amcAssets)){
            haveMore = true;
        }
        while(haveMore){
            for(AmcAsset amcAsset: amcAssets){
                boolean needUpdate = false;
                if(amcAsset.getProvince() != null && !amcAsset.getProvince().equals("-1") && !Character.isDigit(amcAsset.getProvince().charAt(0))){
                    List<Region> regions = null;
                    try{
                        regions =   regionService.getRegionByName(amcAsset.getProvince());
                    }catch (Exception ex){
                        log.error("failed to handle:{}", amcAsset.getProvince(), ex);
//                        continue;
                    }
                    if(CollectionUtils.isEmpty(regions)){
//                        continue;
                    }else{
                        amcAsset.setProvince(regions.get(0).getId().toString());
                        needUpdate = true;
                    }
                }

                if(!StringUtils.isEmpty(amcAsset.getCity()) && !amcAsset.getCity().equals("-1") && !Character.isDigit(amcAsset.getCity().charAt(0))){
                    List<Region> regions = null;
                    try{
                        regions =   regionService.getRegionByName(amcAsset.getCity());
                    }catch (Exception ex){
                        log.error("failed to handle:{}", amcAsset.getCity(), ex);
//                        continue;
                    }

                    if(CollectionUtils.isEmpty(regions)){
                        log.error("{} {}", AmcExceptions.INVALID_COMPANY_NAME_ERROR, String.format("cellAssetCity:%s",amcAsset.getCity()));

                    }else if(regions.size() > 1){
                        for(Region region: regions){
                            if(region.getId().toString().startsWith(amcAsset.getProvince().substring(0,2))){
                                amcAsset.setCity(region.getId().toString());
                                needUpdate = true;
                                break;
                            }
                        }
                    }else{
                        amcAsset.setCity(regions.get(0).getId().toString());
                        needUpdate = true;

                    }
                }

                if(!StringUtils.isEmpty(amcAsset.getCounty()) && !amcAsset.getCounty().equals("-1") && !Character.isDigit(amcAsset.getCounty().charAt(0))){
                    List<Region> regions = null;
                    try{
                        regions =   regionService.getRegionByName(amcAsset.getCounty());;
                    }catch (Exception ex){
                        log.error("failed to handle:{}", amcAsset.getCounty(), ex);
//                        continue;
                    }

                    if(CollectionUtils.isEmpty(regions)){
                        log.error("{} {}",ExceptionUtils.AmcExceptions.INVALID_EXCEL_CONTENT_ERROR, String.format("cellAssetCounty:%s",amcAsset.getCounty()));

                    }else if(regions.size() > 1){
                        for(Region region: regions){
                            if(region.getId().toString().startsWith(amcAsset.getCity().substring(0,3))){
                                amcAsset.setCounty(region.getId().toString());
                                needUpdate = true;
                                break;
                            }
                        }
                    }else{
                        amcAsset.setCounty(regions.get(0).getId().toString());
                        needUpdate = true;

                    }
                }
                if(needUpdate){
                    amcAssetMapper.updateByPrimaryKey(amcAsset);
                }
            }
            offset += 50;
            rowBounds = new RowBounds(offset, pageSize);
            amcAssets = amcAssetMapper.selectByExampleWithRowbounds(amcAssetExample, rowBounds);
            if(CollectionUtils.isEmpty(amcAssets)){
                haveMore = false;
                break;
            }
        }

    }

    private AmcAssetGeoNear queryNearByAssets(GeoJsonPoint geoJsonPoint, Integer[] distances) throws Exception {
        if(distances == null || distances.length < 1){
            throw ExceptionUtils.getAmcException(AmcExceptions.MISSING_MUST_PARAM, "invalid distances params");
        }
        AmcAssetGeoNear amcAssetGeoNear = new AmcAssetGeoNear();
        amcAssetGeoNear.setDistanceRange(distances);
        NearQuery nearQuery = null;
        if(distances.length == 1){
            nearQuery = NearQuery.near(geoJsonPoint).inKilometers().minDistance(distances[0]);
        }else{
            nearQuery = NearQuery.near(geoJsonPoint).inKilometers().maxDistance(distances[1]).minDistance(distances[0]);
        }
        GeoResults<AssetAdditional> assetAdditionalGeoResults = wszccTemplate.geoNear(nearQuery, AssetAdditional.class);
        if(assetAdditionalGeoResults != null && ( distances.length == 2 ?
            assetAdditionalGeoResults.getAverageDistance().getValue() < distances[1] :
            assetAdditionalGeoResults.getAverageDistance().getValue() > distances[0])){
            List<Long> assetIds =
                assetAdditionalGeoResults.getContent().stream().map(item -> item.getContent().getAmcAssetId()).collect(
                    Collectors.toList());
            amcAssetGeoNear.setAmcAssetVoList(getAssetsByIds(assetIds));
        }
        return amcAssetGeoNear;
    }

    public void findGeoAndUpdate(Map<Long, AddressTmp> addresses){

        Query query = null;

        for (Map.Entry<Long, AddressTmp> mapElement : addresses.entrySet()) {
            Long assetId = mapElement.getKey();
                Address.Builder aBuilder = Address.newBuilder();
            String addressVal = mapElement.getValue().getAddress();
            if(StringUtils.isEmpty(addressVal)){
                log.error(" the address is empty:{} of amcId:{}", addressVal, assetId );
            }
            aBuilder.setAddress(mapElement.getValue().getAddress());
            aBuilder.setCity(mapElement.getValue().getCity());
            GeoJson geoJson;
            try{
                geoJson = comnfuncServiceStub.getGeoByAddress(aBuilder.build());
            }catch (Exception ex){
                log.error("", ex);
                continue;
            }


            query = new Query();
            query.addCriteria(Criteria.where("amcAssetId").is(assetId));
            List<AssetAdditional> assetAdditionals = wszccTemplate.find(query, AssetAdditional.class);
            if(CollectionUtils.isEmpty(assetAdditionals)){
                log.error("Failed to find assetAddtional by:{}", assetId);
                query = null;
                continue;
            }
            assetAdditionals.get(0).setLocation(new GeoJsonPoint(geoJson.getCoordinates(0), geoJson.getCoordinates(1)));
            wszccTemplate.save(assetAdditionals.get(0));
            query = null;
        }
    }



    @Data
    class AddressTmp{
        String address;
        String city;
    }

    @Data
    class AssetIdAsset{
        Long assetId;
        AmcAssetVo assetVo;
    }

}
