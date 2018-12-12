package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.dao.mysql.mapper.AmcAssetMapper;
import com.wensheng.zcc.amc.module.dao.helper.AssetStateEnum;
import com.wensheng.zcc.amc.module.dao.helper.AssetTypeEnum;
import com.wensheng.zcc.amc.module.dao.helper.EditStatusEnum;
import com.wensheng.zcc.amc.module.dao.helper.RestrictionsEnum;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetAdditional;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetComment;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetImage;
import com.wensheng.zcc.amc.module.dao.mongo.origin.AmcAssetOrigin;
import com.wensheng.zcc.amc.module.dao.mongo.origin.AssetImageOrigin;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAsset;
import com.wensheng.zcc.amc.service.AmcAssetService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author chenwei on 12/5/18
 * @project zcc-backend
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration
public class AmcAssetServiceImplTest {

    @Autowired
    AmcAssetService amcAssetService;

    @Autowired
    MongoTemplate primaryMongoTemplate;

    @Autowired
    MongoTemplate secondaryMongoTemplate;


    @Autowired
    AmcAssetMapper amcAssetMapper;


//    @Test
//    public void getAllAmcAssets() {
//        List<AmcAsset> amcAssetList = amcAssetService.getAllAmcAssets();
//    }


    @Test
    public void transferMongoDb2MySql(){
        List<AmcAssetOrigin> amcAssetOrigins = primaryMongoTemplate.findAll(AmcAssetOrigin.class, "asset");
        System.out.println(amcAssetOrigins.size());

        // generate pojo for mysql and save


        for(AmcAssetOrigin originItem : amcAssetOrigins){
            AmcAsset amcAssetMysql = new AmcAsset();
            AssetAdditional assetAdditional = new AssetAdditional();
            BeanUtils.copyProperties(originItem, amcAssetMysql );
            handleAmcAsset(originItem, amcAssetMysql);
            Long amcAssetId = new Long (amcAssetMapper.insert(amcAssetMysql)) ;
            handleAmcAddtional(originItem, assetAdditional, amcAssetId);
//            handleAssetComments(originItem, amcAssetId);
            handleAssetImages(originItem, amcAssetId);
            handleAssetDocument(originItem, amcAssetId);

        }


        // generate pojo for mongo and save

    }

    private void handleAssetDocument(AmcAssetOrigin originItem, Long amcAssetId) {
        //1. query asset document based on originItem in origin database

        //2. generate assetDocuments and insert them into current datasource with current amcAssetId
    }

    private void handleAssetImages(AmcAssetOrigin originItem, Long amcAssetId) {
        //1. query asset images based on originItem in origin database
        Query queryAssetImage = new Query();
        queryAssetImage.addCriteria(Criteria.where("asset").is(originItem.getId()));
        List<AssetImageOrigin> assetImages = primaryMongoTemplate.find(queryAssetImage, AssetImageOrigin.class);
        //2. generate assetImages and insert them into current datasource with current amcAssetId
        for (AssetImageOrigin assetImage: assetImages){
            AssetImage assetImageCur = new AssetImage();
            assetImageCur.setAssetId(amcAssetId);
            assetImageCur.setDescription(assetImage.getDescription());
            assetImageCur.setIsToOss(assetImage.getIsToOss());
            assetImageCur.setMainPic(assetImage.getMainPic());
            assetImageCur.setOriginalName(assetImage.getOriginalName());
            assetImageCur.setOriginAssetId(assetImage.getAsset());
            assetImageCur.setPath(assetImage.getPath());
            secondaryMongoTemplate.save(assetImageCur);
        }
    }

    private void handleAssetComments(AmcAssetOrigin originItem, Long amcAssetId) {
        // 1 query asset comments based on originItem in origin datasource
//        Query queryAsset = new Query();
//        queryAsset.addCriteria(Criteria.where())
//        primaryMongoTemplate.find()
        // 2 generate asset comments and insert them into current datasource with current amcAssetId
        // and origin
    }

    private void handleAmcAddtional(AmcAssetOrigin originItem, AssetAdditional assetAdditional, Long amcAssetId) {
        assetAdditional.setAmc(amcAssetId);
        assetAdditional.setAmcContact1(originItem.getAmcContact1());;
        assetAdditional.setAmcContact2(originItem.getAmcContact2());;
        assetAdditional.setAmcNotes(originItem.getAmcNotes());;
        assetAdditional.setBidLink(originItem.getBidLink());;
        assetAdditional.setCommentCount(originItem.getCommentCount());;
        assetAdditional.setCourtCity(originItem.getCourtCity());;
        assetAdditional.setCourtCounty(originItem.getCourtCity());;
        assetAdditional.setCourtInfo(originItem.getCourtInfo());;
        assetAdditional.setCourtName(originItem.getCourtName());;
        assetAdditional.setCourtProvince(originItem.getCourtProvince());;
        assetAdditional.setDescription(originItem.getDescription());;
        assetAdditional.setEndDate(originItem.getEndDate());;
        assetAdditional.setGpsLat(originItem.getGpsLat());
        assetAdditional.setGpsLng(originItem.getGpsLng());
        assetAdditional.setKeywords(originItem.getKeywords());
        assetAdditional.setLikeCount(originItem.getLikeCount());
        assetAdditional.setLinkUrl(originItem.getLinkUrl());
        assetAdditional.setMainPic(originItem.getMainPic());

    }

    private void handleAmcAsset(AmcAssetOrigin originItem, AmcAsset amcAssetMysql) {

        amcAssetMysql.setAmcCode(originItem.getAmcAssetCode());
        amcAssetMysql.setAmcId(originItem.getAmc());
        amcAssetMysql.setArea(originItem.getArea());
        amcAssetMysql.setBuildingName(originItem.getBuildingName());
        amcAssetMysql.setCity(originItem.getCity());
        amcAssetMysql.setCounty(originItem.getCounty());
        //debts default only one item
        amcAssetMysql.setDebtId(CollectionUtils.isEmpty(originItem.getDebts())?-1L:(Long)originItem.getDebts().get(0));
        amcAssetMysql.setEditStatus(EditStatusEnum.lookupByDisplayNameUtil(originItem.getEditStatus()).getStatus());
        amcAssetMysql.setBuildingName(originItem.getBuildingName());
        amcAssetMysql.setEstmPrice(originItem.getEstimatedPrice().toString());
        amcAssetMysql.setGpsLat(originItem.getGpsLat().toString());
        amcAssetMysql.setGpsLng(originItem.getGpsLng().toString());
        amcAssetMysql.setInitPrice(originItem.getInitialPrice().toString());
        amcAssetMysql.setLandArea(originItem.getLandArea());
        amcAssetMysql.setProvince(originItem.getProvince());
        amcAssetMysql.setPublishDate(originItem.getPublishDate());
        amcAssetMysql.setRestrictions(StringUtils.isEmpty(originItem.getRestrictions())? -1: RestrictionsEnum.valueOf(originItem.getRestrictions()).getStatus());
        amcAssetMysql.setState(StringUtils.isEmpty(originItem.getState())? -1: AssetStateEnum.valueOf(originItem.getState()).getStatus());
        amcAssetMysql.setStreet(originItem.getStreet());
        amcAssetMysql.setTitle(originItem.getTitle());
        amcAssetMysql.setType(StringUtils.isEmpty(originItem.getType())?-1:AssetTypeEnum.valueOf(originItem.getType()).getType());


    }
}