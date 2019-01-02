package com.wensheng.zcc.amc.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcAssetMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcGrntctrctMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcGrntorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.CurtInfoMapper;
import com.wensheng.zcc.amc.module.dao.helper.*;
import com.wensheng.zcc.amc.module.dao.helper.base.EnumUtils;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetAdditional;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetImage;
import com.wensheng.zcc.amc.module.dao.mongo.origin.AmcAssetOrigin;
import com.wensheng.zcc.amc.module.dao.mongo.origin.AssetImageOrigin;
import com.wensheng.zcc.amc.module.dao.mongo.origin.DebtOrigin;
import com.wensheng.zcc.amc.module.dao.mongo.origin.Grntor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.*;
import com.wensheng.zcc.amc.service.AmcAssetService;
import com.wensheng.zcc.amc.utils.AmcNumberUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Email;
import java.util.List;

/**
 * @author chenwei on 12/5/18
 * @project zcc-backend
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration

public class AmcAssetServiceImplTest {

    Logger logger = LoggerFactory.getLogger(getClass());
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();



    @Autowired
    AmcAssetService amcAssetService;

    @Autowired
    MongoTemplate primaryMongoTemplate;

    @Autowired
    MongoTemplate secondaryMongoTemplate;


    @Autowired
    AmcAssetMapper amcAssetMapper;

    @Autowired
    CurtInfoMapper curtInfoMapper;

    @Autowired
    AmcDebtMapper amcDebtMapper;



    @Autowired
    AmcGrntorMapper amcGrntorMapper;

    @Autowired
    AmcGrntctrctMapper amcGrntctrctMapper;


//    @Test
//    public void getAllAmcAssets() {
//        List<AmcAsset> amcAssetList = amcAssetService.getAllAmcAssets();
//    }


    @Test
    public void transferMongoDb2MySqlAsset() {
        List<AmcAssetOrigin> amcAssetOrigins = primaryMongoTemplate.findAll(AmcAssetOrigin.class, "asset");
        System.out.println(amcAssetOrigins.size());

        // generate pojo for mysql and save

        AmcAsset amcAssetMysql = null;
        for (AmcAssetOrigin originItem : amcAssetOrigins) {
            try {
                amcAssetMysql = new AmcAsset();

//            BeanUtils.copyProperties(originItem, amcAssetMysql );

                handleAmcAsset(originItem, amcAssetMysql);
                AmcAssetExample amcAssetExample = new AmcAssetExample();
                amcAssetExample.createCriteria().andTitleEqualTo(originItem.getTitle());
                List<AmcAsset> amcAssets = amcAssetMapper.selectByExample(amcAssetExample);
                Long amcAssetId = -1L;
                if (!CollectionUtils.isEmpty(amcAssets)) {
                    System.out.println("this item alredy exists, need update");
                    if (amcAssets.size() > 1) {
                        System.out.println("There is duplicated items for title:" + originItem.getTitle());
                        continue;
                    } else {
                        //try to update the origin record
                        amcAssetId = amcAssets.get(0).getId();
                        amcAssetMysql.setId(amcAssetId);
                        amcAssetMapper.updateByPrimaryKeySelective(amcAssetMysql);
                    }

                } else {
                    amcAssetId = new Long(amcAssetMapper.insertSelective(amcAssetMysql));
                }

                handleAmcAddtional(originItem, amcAssetId);
//            handleAssetComments(originItem, amcAssetId);
                handleAssetImages(originItem, amcAssetId);
                handleAssetDocument(originItem, amcAssetId);
                amcAssetMysql = null;

            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println(originItem.toString());

            }

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
        for (AssetImageOrigin assetImage : assetImages) {
            AssetImage assetImageCur = new AssetImage();
            Query queryCurrImageRepo = new Query();
            queryCurrImageRepo.addCriteria(Criteria.where("assetId").is(amcAssetId).and("originalName").is(assetImage.getOriginalName()));
            List<AssetImage> assetImageList = secondaryMongoTemplate.find(queryCurrImageRepo, AssetImage.class);
            if (!CollectionUtils.isEmpty(assetImageList)) {
                assetImageCur = assetImageList.get(0);
                removeRundtFromSecondMongo(assetImageList);
            }
            assetImageCur.setAssetId(amcAssetId);
            assetImageCur.setDescription(assetImage.getDescription());
            assetImageCur.setIsToOss(assetImage.getIsToOss());
            assetImageCur.setMainPic(assetImage.getMainPic());
            assetImageCur.setOriginalName(assetImage.getOriginalName());
            assetImageCur.setOriginAssetId(assetImage.getAsset());
            assetImageCur.setPath(assetImage.getPath());
            secondaryMongoTemplate.save(assetImage);
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

    private <T> void removeRundtFromSecondMongo(List<T> listInput) {

        if (listInput.size() <= 1) {
            return;
        }
        for (int idx = 1; idx < listInput.size() - 1; idx++) {
            secondaryMongoTemplate.remove(listInput.get(idx));
        }
    }

    private void handleAmcAddtional(AmcAssetOrigin originItem, Long amcAssetId) {
        AssetAdditional assetAdditional = new AssetAdditional();
        Query queryCurrAdditional = new Query();
        queryCurrAdditional.addCriteria(Criteria.where("amcAssetId").is(amcAssetId));
        List<AssetAdditional> assetAdditionals = secondaryMongoTemplate.find(queryCurrAdditional, AssetAdditional.class);
        if (!CollectionUtils.isEmpty(assetAdditionals)) {
            assetAdditional = assetAdditionals.get(0);
            removeRundtFromSecondMongo(assetAdditionals);
        }


        assetAdditional.setAmcAssetId(amcAssetId);
        assetAdditional.setAmcContact1(originItem.getAmcContact1());
        ;
        assetAdditional.setAmcContact2(originItem.getAmcContact2());
        ;
        assetAdditional.setAmcNotes(originItem.getAmcNotes());
        ;
        assetAdditional.setBidLink(originItem.getBidLink());
        ;
        assetAdditional.setCommentCount(originItem.getCommentCount());
        ;
        assetAdditional.setCourtCity(originItem.getCourtCity());
        ;
        assetAdditional.setCourtCounty(originItem.getCourtCity());
        ;
        assetAdditional.setCourtInfo(originItem.getCourtInfo());
        ;
        assetAdditional.setCourtName(originItem.getCourtName());
        ;
        assetAdditional.setCourtProvince(originItem.getCourtProvince());
        ;
        assetAdditional.setDescription(originItem.getDescription());
        ;
        assetAdditional.setEndDate(originItem.getEndDate());
        ;
        assetAdditional.setGpsLat(originItem.getGpsLat());
        assetAdditional.setGpsLng(originItem.getGpsLng());
        assetAdditional.setKeywords(originItem.getKeywords());
        assetAdditional.setLikeCount(originItem.getLikeCount());
        assetAdditional.setLinkUrl(originItem.getLinkUrl());
        assetAdditional.setMainPic(originItem.getMainPic());
        assetAdditional.setOtherCatalog(originItem.getOtherCatalog());
        assetAdditional.setRecommanded(originItem.isRecommanded());
        assetAdditional.setReportPath(originItem.getReportPath());
        assetAdditional.setWatchCount(originItem.getWatchCount());
        assetAdditional.setStartDate(originItem.getStartDate());
        assetAdditional.setZipCode(originItem.getZipCode());
        secondaryMongoTemplate.save(assetAdditional);

    }

    private void handleAmcAsset(AmcAssetOrigin originItem, AmcAsset amcAssetMysql) {
        if (!StringUtils.isEmpty(originItem.getAmcAssetCode())) {
            amcAssetMysql.setAmcAssetCode(originItem.getAmcAssetCode());
        }
        amcAssetMysql.setAmcId(originItem.getAmc());
        if (originItem.getArea() != null) {
            amcAssetMysql.setArea(AmcNumberUtils.getLongFromStringWithMult100(originItem.getArea()));
        }
        amcAssetMysql.setBuildingName(originItem.getBuildingName());
        amcAssetMysql.setCity(originItem.getCity());
        amcAssetMysql.setCounty(originItem.getCounty());
        //debts default only one item
        amcAssetMysql.setDebtId(CollectionUtils.isEmpty(originItem.getDebts()) ? -1L : (Long) originItem.getDebts().get(0));
        amcAssetMysql.setEditStatus(EditStatusEnum.lookupByDisplayNameUtil(originItem.getEditStatus()).getStatus());
        amcAssetMysql.setBuildingName(originItem.getBuildingName());
        if (originItem.getEstimatedPrice() != null) {
            amcAssetMysql.setEstmPrice(AmcNumberUtils.getLongFromStringWithMult100(originItem.getEstimatedPrice().toString()));
        }
        if (!StringUtils.isEmpty(originItem.getGpsLat())) {
            amcAssetMysql.setGpsLat(originItem.getGpsLat().toString());
        }
        if (!StringUtils.isEmpty(originItem.getGpsLng())) {
            amcAssetMysql.setGpsLng(originItem.getGpsLng().toString());
        }

        if (originItem.getInitialPrice() != null) {
            amcAssetMysql.setInitPrice(AmcNumberUtils.getLongFromStringWithMult100(originItem.getInitialPrice().toString()));
        }
        if (originItem.getLandArea() != null) {
            amcAssetMysql.setLandArea(AmcNumberUtils.getLongFromStringWithMult100(originItem.getLandArea()));
        }
        amcAssetMysql.setProvince(originItem.getProvince());
        amcAssetMysql.setPublishDate(originItem.getPublishDate());
        amcAssetMysql.setRestrictions(StringUtils.isEmpty(originItem.getRestrictions()) ? -1 : RestrictionsEnum.valueOf(originItem.getRestrictions()).getStatus());
        if (!StringUtils.isEmpty(originItem.getState())) {
            amcAssetMysql.setState(AssetStateEnum.lookupByDisplayNameUtil(originItem.getState()).getStatus());
        }
        amcAssetMysql.setStreet(originItem.getStreet());
        amcAssetMysql.setTitle(originItem.getTitle());
        if (!StringUtils.isEmpty(originItem.getType())) {
            amcAssetMysql.setType(AssetTypeEnum.lookupByDisplayNameUtil(originItem.getType()).getType());
        }
    }


    @Test
    public void transferMongoDb2MySqlDebt() {
        List<DebtOrigin> amcAssetOrigins = primaryMongoTemplate.findAll(DebtOrigin.class, "debt");
        System.out.println(amcAssetOrigins.size());

        // generate pojo for mysql and save

        AmcDebt amcDebt = null;
        for (DebtOrigin debtOriginItem : amcAssetOrigins) {
            try {

//            BeanUtils.copyProperties(originItem, amcAssetMysql );

                amcDebt = handleAmcDebt(debtOriginItem);
                Long courtId = handleCourtInfo(debtOriginItem);
                amcDebt.setCourtId(courtId);
                handleDebtGrantor(debtOriginItem, amcDebt);
                handleDebtorInfo(debtOriginItem, amcDebt);


            } catch (Exception ex) {
                ex.printStackTrace();
                logger.error(GSON.toJson(debtOriginItem));
            }
        }


        // generate pojo for mongo and save

    }

    private void handleDebtGrantor(DebtOrigin debtOriginItem, AmcDebt amcDebt) {
        Query query = new Query();
        query.addCriteria(Criteria.where("debtNo").is(amcDebt.getOriginId()));
        List<Grntor> grntors = primaryMongoTemplate.find(query, Grntor.class);

        // origin design combined grantor with grantor contract and debt
        // current design seperate grantor to grantor , grantor connect with debt by contract

        for(Grntor grntor : grntors){
          AmcGrntorExample amcGrntorExample = new AmcGrntorExample();
          amcGrntorExample.createCriteria().andNameEqualTo(grntor.getName()).andTypeEqualTo(GrantorTypeEnum.
              lookupByDisplayNameUtil(grntor.getType()).getId());
          List<AmcGrntor> amcGrntors =  amcGrntorMapper.selectByExample(amcGrntorExample);
          AmcGrntor amcGrntor = null;
          Long newGrntorId = null;
          if(!CollectionUtils.isEmpty(amcGrntors)){
            if(amcGrntors.size() >= 2){
                logger.error(" there is redundent item for "+ grntor.getDebtNo() + " and "+ grntor.getName() );
                for(int idx = 1; idx < amcGrntors.size() - 1; idx ++){
                    amcGrntorMapper.deleteByPrimaryKey(amcGrntors.get(idx).getId());
                }
            }else{
                amcGrntor = amcGrntors.get(0);
                amcGrntor.setType(GrantorTypeEnum.lookupByDisplayNameUtil(grntor.getType()).getId());
                amcGrntor.setName(grntor.getName());
                amcGrntorMapper.updateByPrimaryKeySelective(amcGrntor);
                newGrntorId = amcGrntor.getId();
            }
          }else{
              amcGrntor = new AmcGrntor();
              amcGrntor.setType(GrantorTypeEnum.lookupByDisplayNameUtil(grntor.getType()).getId());
              amcGrntor.setName(grntor.getName());
              newGrntorId = Long.valueOf(amcGrntorMapper.insertSelective(amcGrntor));
          }
            //ToDo:need generate contract
          AmcGrntctrctExample amcGrntctrctExample = new AmcGrntctrctExample();
          amcGrntctrctExample.createCriteria().andOriginDebtIdEqualTo(grntor.getDebtNo()).andOriginContractIdEqualTo(grntor.getContract());

          List<AmcGrntctrct> amcGrntctrcts =  amcGrntctrctMapper.selectByExample(amcGrntctrctExample);
          AmcGrntctrct amcGrntctrctNew = null;
          if(!CollectionUtils.isEmpty(amcGrntctrcts)){
              if(amcGrntctrcts.size() >= 2){
                  logger.error("there is redundent grant contract itme for debt:"+ grntor.getDebtNo() + " and "
                      + "contractId:" + grntor.getContract());
                  for(int idx = 1; idx < amcGrntctrcts.size() - 1; idx ++){
                      amcGrntctrctMapper.deleteByPrimaryKey(amcGrntctrcts.get(idx).getId());
                  }
              }else{
                  amcGrntctrctNew = amcGrntctrcts.get(0);
                  amcGrntctrctNew.setDebtId(amcDebt.getId());
                  amcGrntctrctNew.setAmount(AmcNumberUtils.getLongFromDoubleWithMult100(grntor.getAmount()));
                  amcGrntctrctNew.setOriginDebtId(grntor.getDebtNo());
                  amcGrntctrctNew.setOriginContractId(grntor.getContract());
              }

          }
            amcGrntor.setDebtId(amcDebt.getId());
            amcGrntor.setOriginCtrtId(grntor.getContract());

            amcGrntor.setCtrtId(generateContract(grntor));
            //Todo: search company by name ? from origin mongodb
    //                amcGrntor.setOriginCmpyId();
        }

        amcGrntorMapper.selectByExample()
    }
    //Todo: finish coding
    private Long generateContract(Grntor grntor) {
        //find contract from origin database
        Query query = new Query();
        query.addCriteria(Criteria.where("contract").is(contractId));
        primaryMongoTemplate.find(query, Grntor.class);

        // insert into mysql database


        return -1L;
    }

    private AmcDebt handleAmcDebt(DebtOrigin originItem) {
        AmcDebt amcDebt = null;
        boolean isUpdate = false;
        AmcDebtExample amcDebtExample = new AmcDebtExample();
        amcDebtExample.createCriteria().andTitleEqualTo(originItem.getTitle()).andAmcIdEqualTo(originItem.getAmc());
        List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);
        if(!CollectionUtils.isEmpty(amcDebts)){
            if(amcDebts.size() >=2 ){
                logger.error("There is multipule same title debts there");
                for(int idx = 1; idx < amcDebts.size() - 1; idx ++){
                    logger.info("will remove"+GSON.toJson(amcDebts.get(idx)));
                    amcDebtMapper.deleteByPrimaryKey(amcDebts.get(idx).getId());
                }
            }
            amcDebt = amcDebts.get(0);
            isUpdate = true;
        }else{
            amcDebt = new AmcDebt();
        }
        if (!StringUtils.isEmpty(originItem.getAmcContact1())) {
            amcDebt.setAmcContact1(originItem.getAmcContact1());
        }
        if (!StringUtils.isEmpty(originItem.getAmcContact2())) {
            amcDebt.setAmcContact2(originItem.getAmcContact2());
        }
        if (!StringUtils.isEmpty(originItem.getAmcDebtCode())) {
            amcDebt.setAmcDebtCode(originItem.getAmcDebtCode());

        }
        amcDebt.setBaseAmount(AmcNumberUtils.getLongFromStringWithMult100(originItem.getBaseAmount()));
        if (originItem.getBaseDate() != null) {
            amcDebt.setBaseDate(originItem.getBaseDate());
        }
        Long courtId = handleCourtInfo(originItem);
        amcDebt.setCourtId(courtId);
        amcDebt.setDebtpackId(originItem.getDebtpackId());
        amcDebt.setEditStatus(EditStatusEnum.lookupByDisplayNameUtil(originItem.getEditStatus()).getStatus());
        if(originItem.getEndDate() != null){
            amcDebt.setEndDate(originItem.getEndDate());
        }
        amcDebt.setEstimatedPrice(AmcNumberUtils.getLongFromStringWithMult100(originItem.getEstimatedPrice()));
        amcDebt.setIsRecommanded(originItem.getIsRecommanded());
        amcDebt.setLawStatus(LawstatusEnum.lookupByDisplayNameUtil(originItem.getLawStatus()).getStatus());
        amcDebt.setOriginId(originItem.getId());
        amcDebt.setPublishDate(originItem.getPublishDate());
        amcDebt.setSettleDate(originItem.getSettleDate());
        amcDebt.setStartDate(originItem.getStartDate());
        amcDebt.setTitle(originItem.getTitle());
        amcDebt.setTotalAmount(AmcNumberUtils.getLongFromStringWithMult100(originItem.getTotalAmount()));
        Long debtId;
        if( isUpdate ){
            debtId = amcDebt.getId();
            amcDebtMapper.updateByPrimaryKeySelective(amcDebt);
        }else{
            debtId = Long.valueOf(amcDebtMapper.insertSelective(amcDebt));
        }
        return amcDebt;
    }

    private Long handleCourtInfo(DebtOrigin originItem) {
        CurtInfoExample curtInfoExample = new CurtInfoExample();
        curtInfoExample.createCriteria().andCurtNameEqualTo(originItem.getCourtName()).andCurtCityEqualTo(originItem.getCourtCity());
        List<CurtInfo> curtInfos = curtInfoMapper.selectByExample(curtInfoExample);
        if (CollectionUtils.isEmpty(curtInfos)) {
            CurtInfo curtInfo = new CurtInfo();
            curtInfo.setCurtCity(originItem.getCourtCity());
            curtInfo.setCurtName(originItem.getCourtName());
            curtInfo.setCurtProvince(originItem.getCourtProvince());
            Long curtId = Long.valueOf(curtInfoMapper.insertSelective(curtInfo));
            return curtId;
        } else {
            return curtInfos.get(0).getId();
        }
    }
}