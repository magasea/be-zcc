package com.wensheng.zcc.amc.service.impl;


import com.wensheng.zcc.amc.dao.mysql.mapper.AmcAssetMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtContactorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.ext.AmcAssetExtMapper;
import com.wensheng.zcc.amc.module.dao.helper.ImageClassEnum;
import com.wensheng.zcc.amc.module.dao.helper.PublishStateEnum;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetAdditional;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetComment;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetDocument;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetImage;
import com.wensheng.zcc.amc.module.dao.mongo.entity.DebtImage;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAsset;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAssetExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtContactor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtContactorExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtor;
import com.wensheng.zcc.amc.module.vo.AmcAssetDetailVo;
import com.wensheng.zcc.amc.module.vo.AmcAssetVo;
import com.wensheng.zcc.amc.service.AmcAssetService;
import com.wensheng.zcc.amc.service.AmcOssFileService;
import com.wensheng.zcc.amc.service.impl.helper.Dao2VoUtils;
import com.wensheng.zcc.amc.utils.AmcBeanUtils;
import com.wensheng.zcc.amc.utils.SQLUtils;
import java.awt.Image;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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


    @Override
    @Transactional
    public AmcAssetVo create(AmcAsset amcAsset) throws Exception {
        amcAssetMapper.insertSelective(amcAsset);

         AmcAssetVo amcAssetVo = Dao2VoUtils.convertDo2Vo(amcAsset);
        if(amcAsset.getAmcContactorId() != null && amcAsset.getAmcContactorId() > 0){
            AmcDebtContactorExample amcDebtContactorExample = new AmcDebtContactorExample();
            amcDebtContactorExample.createCriteria().andIdEqualTo(amcAsset.getAmcContactorId());
            AmcDebtContactor amcDebtContactor = amcDebtContactorMapper.selectByPrimaryKey(amcAsset.getAmcContactorId());
            amcAssetVo.setAmcContactorId(amcDebtContactor);
        }
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

    private void del(AmcAsset amcAsset) {

        AmcAssetExample amcAssetExample = new AmcAssetExample();
        Query query= new Query();
        query.addCriteria(Criteria.where("amcAssetId").is(amcAsset.getId()));

        if(amcAsset.getPublishState() == PublishStateEnum.DRAFT.getStatus()){
            amcAssetMapper.deleteByPrimaryKey(amcAsset.getId());
            wszccTemplate.remove(query, AssetAdditional.class);
            List<AssetImage> assetImages = wszccTemplate.find(query, AssetImage.class);
            for(AssetImage assetImage: assetImages){

                try {
                    amcOssFileService.delFileInOss(assetImage.getOssPath());
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("Failed to del file on oss with osspath:"+ assetImage.getOssPath(), e);
                }
            }
            wszccTemplate.remove(query, AssetImage.class);
        }else{
            AmcAsset amcAssetUpdate = new AmcAsset();
            amcAssetUpdate.setPublishState(PublishStateEnum.DELETED.getStatus());
            amcAssetUpdate.setId(amcAsset.getId());
            amcAssetMapper.updateByPrimaryKeySelective(amcAssetUpdate);
        }


    }


    @Override
    public AmcAssetVo update(AmcAsset amcAsset) throws Exception {
        amcAssetMapper.updateByPrimaryKeySelective(amcAsset);
        AmcAssetVo amcAssetVo =  Dao2VoUtils.convertDo2Vo(amcAsset);
        if(amcAsset.getAmcContactorId() != null && amcAsset.getAmcContactorId() > 0){
            AmcDebtContactorExample amcDebtContactorExample = new AmcDebtContactorExample();
            amcDebtContactorExample.createCriteria().andIdEqualTo(amcAsset.getAmcContactorId());
            AmcDebtContactor amcDebtContactor = amcDebtContactorMapper.selectByPrimaryKey(amcAsset.getAmcContactorId());
            amcAssetVo.setAmcContactorId(amcDebtContactor);
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
    public List<AmcAssetVo> query(AmcAsset queryCond, int offset, int size) {
        return null;
    }

    @Override
    public AmcAssetDetailVo queryAssetDetail(Long assetId) throws Exception {
        AmcAsset amcAsset =  amcAssetMapper.selectByPrimaryKey(assetId);

        AmcAssetDetailVo amcAssetDetailVo = queryMongoForAmcAsset(amcAsset);
        if(amcAsset.getAmcContactorId() != null && amcAsset.getAmcContactorId() > 0){
            AmcDebtContactorExample amcDebtContactorExample = new AmcDebtContactorExample();
            amcDebtContactorExample.createCriteria().andIdEqualTo(amcAsset.getAmcContactorId());
            AmcDebtContactor amcDebtContactor = amcDebtContactorMapper.selectByPrimaryKey(amcAsset.getAmcContactorId());
            amcAssetDetailVo.getAmcAssetVo().setAmcContactorId(amcDebtContactor);
        }
        return amcAssetDetailVo;
    }

    private AmcAssetDetailVo queryMongoForAmcAsset(AmcAsset amcAsset) throws Exception {
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

    @Override
    public List<AmcAssetDetailVo> queryAssetDetails(Long amcDebtId) throws Exception {
        AmcAssetExample amcAssetExample = new AmcAssetExample();
        amcAssetExample.createCriteria().andDebtIdEqualTo(amcDebtId);
        List<AmcAsset> amcAssets = amcAssetMapper.selectByExample(amcAssetExample);
        List<AmcAssetDetailVo> amcAssetDetailVos = new ArrayList<>();
        for(AmcAsset amcAsset: amcAssets){
            amcAssetDetailVos.add(queryMongoForAmcAsset(amcAsset));
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

//    Comparator<AmcAssetVo> amcAssetVoComparator = new Comparator<AmcAssetVo>() {
//        @Override
//        public int compare(AmcAssetVo e1, AmcAssetVo e2) {
//            if( e1.getAssetImage() == null && e2.getAssetImage() == null){
//                return 0;
//            }else if( e1.getAssetImage() != null && e2.getAssetImage() == null){
//                return 1;
//            }else if( e1.getAssetImage() != null && e2.getAssetImage() != null && e1.getAssetImage().getOssPath() != null && e2.getAssetImage().getOssPath() == null ) {
//                return 1;
//            }else if( e1.getAssetImage() != null && e2.getAssetImage() != null && e1.getAssetImage().getOssPath() != null && e2.getAssetImage().getOssPath() != null ){
//                return 0;
//            }else{
//                return -1;
//            }
//
//        }
//    };

    @Override
    public Long getAssetCount(Map<String, Object> queryParam) {
        AmcAssetExample amcAssetExample = getAmcAssetExampleWithQueryParam(queryParam);
        return amcAssetMapper.countByExample(amcAssetExample);
    }

    @Override
    public List<AmcAssetVo> queryForHomePage(int size) {
        //query AssetImage to get top sizes images containing asset ids
        Query query = new Query();
//        query.addCriteria(Criteria.where("amcAssetId").is())

        //query amcAssetMapper to get top size amcAssets which is not deleted


        //join them

        return null;
    }

    @Override
    public Map<String, List<Long>> getAllAssetTitles() {
        AmcAssetExample amcAssetExample = new AmcAssetExample();
//        amcAssetExample.setDistinct(true);
        List<AmcAsset> amcAssets =  amcAssetExtMapper.selectAllTitlesByExample(amcAssetExample);
        Map<String, List<Long>> titleMap = new HashMap<>();
        for(AmcAsset amcAsset: amcAssets){
            if(!titleMap.containsKey(amcAsset.getTitle())){
                titleMap.put(amcAsset.getTitle(), new ArrayList<Long>());
            }
            titleMap.get(amcAsset.getTitle()).add(amcAsset.getId());

        }
        return titleMap;
    }

    @Override
    public AssetImage saveImageInfo( AssetImage assetImage) {

        Query query = new Query();
        if(assetImage.getTag() == ImageClassEnum.MAIN.getId()){
            query.addCriteria(Criteria.where("amcAssetId").is(assetImage.getAmcAssetId()).and("tag").is(ImageClassEnum.MAIN.getId()));
            List<AssetImage> assetImages =  wszccTemplate.find(query, AssetImage.class);
            if(!CollectionUtils.isEmpty(assetImages)){
                log.info("now need delete history main image");
                for(AssetImage assetImageItem: assetImages){

                    wszccTemplate.remove(assetImageItem);
                }
            }
            wszccTemplate.save(assetImage);

        }else{
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


        }

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

    private AmcAssetExample getAmcAssetExampleWithQueryParam(Map<String, Object> queryParam){
        AmcAssetExample amcAssetExample = new AmcAssetExample();
        AmcAssetExample.Criteria criteria = amcAssetExample.createCriteria();
        criteria.andPublishStateNotEqualTo(PublishStateEnum.DELETED.getStatus());
        if(!CollectionUtils.isEmpty(queryParam)){
            for(Entry<String, Object> item: queryParam.entrySet()){
                if(item.getKey().equals("DebtId")){
                    criteria.andDebtIdEqualTo((Long)item.getValue());
                }
                if(item.getKey().equals("EditStatus")){
                    criteria.andPublishStateEqualTo((Integer) item.getValue());
                }
                if(item.getKey().equals("Area")){
                    if((Long)((List)item.getValue()).get(0) < 0 && (Long)((List)item.getValue()).get(1) > 0){
                        criteria.andAreaLessThan((Long)((List)item.getValue()).get(1));
                    }else if((Long)((List)item.getValue()).get(0) > 0 && (Long)((List)item.getValue()).get(1) <= 0){
                        criteria.andAreaGreaterThan((Long)((List)item.getValue()).get(0));
                    }else if((Long)((List)item.getValue()).get(0) > 0 && (Long)((List)item.getValue()).get(1) > 0){
                        criteria.andAreaBetween((Long)((List)item.getValue()).get(0), (Long)((List)item.getValue()).get(1));
                    }

                }
                if(item.getKey().equals("LandArea")){
                    if((Long)((List)item.getValue()).get(0) < 0 && (Long)((List)item.getValue()).get(1) > 0){
                        criteria.andLandAreaLessThan((Long)((List)item.getValue()).get(1));
                    }else if((Long)((List)item.getValue()).get(0) > 0 && (Long)((List)item.getValue()).get(1) <= 0){
                        criteria.andLandAreaGreaterThan((Long)((List)item.getValue()).get(0));
                    }else if((Long)((List)item.getValue()).get(0) > 0 && (Long)((List)item.getValue()).get(1) > 0){
                        criteria.andLandAreaBetween((Long)((List)item.getValue()).get(0),
                            (Long)((List)item.getValue()).get(1));
                    }

                }
                if(item.getKey().equals("SealedState")){
                    criteria.andSealedStateEqualTo((Integer) item.getValue());
                }
                if(item.getKey().equals("Title")){

                    criteria.andTitleLike((String)item.getValue());
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
            }
        }
        return amcAssetExample;
    }




}
