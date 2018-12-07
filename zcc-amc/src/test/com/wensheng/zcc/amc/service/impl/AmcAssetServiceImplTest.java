package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.dao.mongo.OriginAssetRepo;
import com.wensheng.zcc.amc.module.dao.mongo.origin.AmcAssetOrigin;
import com.wensheng.zcc.amc.module.dao.mysql.AmcAsset;
import com.wensheng.zcc.amc.service.AmcAssetService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

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




    @Test
    public void getAllAmcAssets() {
        List<AmcAsset> amcAssetList = amcAssetService.getAllAmcAssets();
    }


    @Test
    public void transferMongoDb2MySql(){
        List<AmcAssetOrigin> amcAssetOrigins = primaryMongoTemplate.findAll(AmcAssetOrigin.class, "asset");
        System.out.println(amcAssetOrigins.size());

        // generate pojo for mysql and save


        for(AmcAssetOrigin originItem : amcAssetOrigins){
            AmcAsset amcAssetMysql = new AmcAsset();
            BeanUtils.copyProperties(originItem, amcAssetMysql );

        }


        // generate pojo for mongo and save

    }
}