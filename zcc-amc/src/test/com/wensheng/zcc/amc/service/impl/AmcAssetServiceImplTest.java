package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.dao.mysql.mapper.AmcAssetMapper;
import com.wensheng.zcc.amc.module.dao.helper.AssetStateEnum;
import com.wensheng.zcc.amc.module.dao.helper.AssetTypeEnum;
import com.wensheng.zcc.amc.module.dao.helper.EditStatusEnum;
import com.wensheng.zcc.amc.module.dao.helper.RestrictionsEnum;
import com.wensheng.zcc.amc.module.dao.mongo.origin.AmcAssetOrigin;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAsset;
import com.wensheng.zcc.amc.service.AmcAssetService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
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
            BeanUtils.copyProperties(originItem, amcAssetMysql );
            handleAmcAsset(originItem, amcAssetMysql);
            Long amcAssetId = new Long (amcAssetMapper.insert(amcAssetMysql)) ;

        }


        // generate pojo for mongo and save

    }

    private void handleAmcAsset(AmcAssetOrigin originItem, AmcAsset amcAssetMysql) {

        amcAssetMysql.setAmcCode(originItem.getAmcAssetCode());
        amcAssetMysql.setAmcId(originItem.getAmc());
        amcAssetMysql.setArea(originItem.getArea());
        amcAssetMysql.setBuildingName(originItem.getBuildingName());
        amcAssetMysql.setCity(originItem.getCity());
        amcAssetMysql.setCounty(originItem.getCounty());
        //debts default only one item
        amcAssetMysql.setDebtId(CollectionUtils.isEmpty(originItem.getDebts())?-1, originItem.getDebts().get(0));
        amcAssetMysql.setEditStatus(EditStatusEnum.valueOf(originItem.getEditStatus()).getStatus());
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