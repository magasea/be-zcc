package com.wensheng.zcc.amc.service.impl;


import com.wensheng.zcc.amc.dao.mysql.mapper.AmcAssetMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtContactorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.ext.AmcAssetExtMapper;
import com.wensheng.zcc.amc.module.dao.helper.ImageClassEnum;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetAdditional;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetComment;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetDocument;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetImage;
import com.wensheng.zcc.amc.module.dao.mongo.entity.DebtImage;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAsset;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAssetExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtContactor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtContactorExample;
import com.wensheng.zcc.amc.module.vo.AmcAssetDetailVo;
import com.wensheng.zcc.amc.module.vo.AmcAssetVo;
import com.wensheng.zcc.amc.service.AmcAssetService;
import com.wensheng.zcc.amc.service.impl.helper.Dao2VoUtils;
import com.wensheng.zcc.amc.utils.AmcBeanUtils;
import com.wensheng.zcc.amc.utils.SQLUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
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



    @Override
    public AmcAssetVo create(AmcAsset amcAsset) throws Exception {
        amcAssetMapper.insertSelective(amcAsset);

         AmcAssetVo amcAssetVo = Dao2VoUtils.convertDo2Vo(amcAsset);
        if(amcAsset.getAmcContactorId() != null && amcAsset.getAmcContactorId() > 0){
            AmcDebtContactorExample amcDebtContactorExample = new AmcDebtContactorExample();
            amcDebtContactorExample.createCriteria().andIdEqualTo(amcAsset.getAmcContactorId());
            AmcDebtContactor amcDebtContactor = amcDebtContactorMapper.selectByPrimaryKey(amcAsset.getAmcContactorId());
            amcAssetVo.setAmcContactorId(amcDebtContactor);
        }

        return amcAssetVo;
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
    public AmcAssetVo del(AmcAsset amcAsset) {
        return null;
    }

    @Override
    public AmcAssetVo update(AmcAsset amcAsset) throws Exception {
        amcAssetMapper.updateByPrimaryKey(amcAsset);
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
        return amcAssetVos;
    }

    @Override
    public Long getAssetCount(Map<String, Object> queryParam) {
        AmcAssetExample amcAssetExample = getAmcAssetExampleWithQueryParam(queryParam);
        return amcAssetMapper.countByExample(amcAssetExample);
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
        if(CollectionUtils.isEmpty(queryParam)){
            for(Entry<String, Object> item: queryParam.entrySet()){
                if(item.getKey().equals("DebtId")){
                    amcAssetExample.createCriteria().andDebtIdEqualTo((Long)item.getValue());
                }
                if(item.getKey().equals("EditStatus")){
                    amcAssetExample.createCriteria().andPublishStateEqualTo((Integer) item.getValue());
                }
                if(item.getKey().equals("Area")){
                    if((Long)((List)item.getValue()).get(0) < 0 && (Long)((List)item.getValue()).get(1) > 0){
                        amcAssetExample.createCriteria().andAreaLessThan((Long)((List)item.getValue()).get(1));
                    }else if((Long)((List)item.getValue()).get(0) > 0 && (Long)((List)item.getValue()).get(1) <= 0){
                        amcAssetExample.createCriteria().andAreaGreaterThan((Long)((List)item.getValue()).get(0));
                    }else if((Long)((List)item.getValue()).get(0) > 0 && (Long)((List)item.getValue()).get(1) > 0){
                        amcAssetExample.createCriteria().andAreaBetween((Long)((List)item.getValue()).get(0), (Long)((List)item.getValue()).get(1));
                    }

                }
                if(item.getKey().equals("LandArea")){
                    if((Long)((List)item.getValue()).get(0) < 0 && (Long)((List)item.getValue()).get(1) > 0){
                        amcAssetExample.createCriteria().andLandAreaLessThan((Long)((List)item.getValue()).get(1));
                    }else if((Long)((List)item.getValue()).get(0) > 0 && (Long)((List)item.getValue()).get(1) <= 0){
                        amcAssetExample.createCriteria().andLandAreaGreaterThan((Long)((List)item.getValue()).get(0));
                    }else if((Long)((List)item.getValue()).get(0) > 0 && (Long)((List)item.getValue()).get(1) > 0){
                        amcAssetExample.createCriteria().andLandAreaBetween((Long)((List)item.getValue()).get(0),
                            (Long)((List)item.getValue()).get(1));
                    }

                }
                if(item.getKey().equals("SealedState")){
                    amcAssetExample.createCriteria().andSealedStateEqualTo((Integer) item.getValue());
                }
                if(item.getKey().equals("Title")){

                    amcAssetExample.createCriteria().andTitleLike((String)item.getValue());
                }
            }
        }
        return amcAssetExample;
    }


//    private <T>void  removeDuplicatItems(Query query, Object obj){
//
//        List<T> listOfT = (List<T>) wszccTemplate.find(query, obj.getClass());
//        if(!CollectionUtils.isEmpty(listOfT)){
//            if(listOfT.size() > 1){
//                for(int idx = 1; idx < listOfT.size(); idx ++){
//                    wszccTemplate.remove(listOfT.get(idx));
//                }
//            }
//            Update update = new Update();
//            update.set(listOfT.get(0).get_id(), obj);
//            wszccTemplate.findAndModify(query, update, AssetImage.class);
//        }else{
//            wszccTemplate.insert(assetImage);
//        }
//    }

}
