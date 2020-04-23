package com.wensheng.zcc.amc.service.impl;

import com.google.gson.Gson;
import com.wensheng.zcc.amc.dao.mysql.mapper.*;
import com.wensheng.zcc.amc.module.dao.helper.FilterTypeEnum;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.*;
import com.wensheng.zcc.amc.module.vo.AmcFilterContentDebt;
import com.wensheng.zcc.amc.module.vo.AmcFilterContentItem;
import com.wensheng.zcc.amc.module.vo.AmcFilterContentTag;
import com.wensheng.zcc.amc.module.vo.AmcSaleFilter;
import com.wensheng.zcc.amc.service.AmcAssetService;
import com.wensheng.zcc.amc.service.AmcDebtService;
import com.wensheng.zcc.amc.service.AmcSaleService;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class AmcSaleServiceImpl implements AmcSaleService {
    @Autowired
    AmcAssetService amcAssetService;

    @Autowired
    AmcDebtService amcDebtService;


    @Autowired
    AmcSaleFloorMapper amcSaleFloorMapper;



    @Autowired
    AmcSaleTagMapper amcSaleTagMapper;



    @Autowired
    AmcSaleTagDebtMapper amcSaleTagDebtMapper;

    @Autowired
    AmcSaleTagAssetMapper amcSaleTagAssetMapper;

    private Gson gson = new Gson();

    @Override
    public List<AmcSaleFloor> getFloors(){
        AmcSaleFloorExample amcSaleFloorExample = new AmcSaleFloorExample();
        amcSaleFloorExample.setOrderByClause(" id desc ");
        return amcSaleFloorMapper.selectByExample(amcSaleFloorExample);
    }

    @Override
    public void updateFloor(AmcSaleFloor amcSaleFloor) throws Exception {
        checkFilterContent(amcSaleFloor);
        amcSaleFloorMapper.updateByPrimaryKeySelective(amcSaleFloor);
    }

    private void checkFilterContent(AmcSaleFloor amcSaleFloor) throws Exception {
        amcSaleFloor.getFilterContent();
        AmcSaleFilter amcSaleFilter = gson.fromJson(amcSaleFloor.getFilterContent(), AmcSaleFilter.class);
        if(amcSaleFilter == null || StringUtils.isEmpty(amcSaleFloor.getFilterContent())){
            throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_JSON_CONTENT_ERROR, amcSaleFloor.getFilterContent());
        }
        if(CollectionUtils.isEmpty(amcSaleFilter.getFilterContentItemList())){
            throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_JSON_CONTENT_ERROR, amcSaleFloor.getFilterContent());
        }
        for(AmcFilterContentItem amcFilterContentItem: amcSaleFilter.getFilterContentItemList()){
            if(amcFilterContentItem.getFilterType() == FilterTypeEnum.TAG.getType()){
                AmcFilterContentTag amcFilterContentTag = (AmcFilterContentTag) amcFilterContentItem;
                if(CollectionUtils.isEmpty(amcFilterContentTag.getTagIds())){

                }
            }
        }
    }

    @Override
    public List<AmcSaleTag> getTags(){
        AmcSaleTagExample amcSaleTagExample = new AmcSaleTagExample();
        amcSaleTagExample.setOrderByClause(" id desc ");
        return amcSaleTagMapper.selectByExample(amcSaleTagExample);
    }
    public void updateTag(AmcSaleTag amcSaleTag){
        amcSaleTagMapper.updateByPrimaryKeySelective(amcSaleTag);
    }



    public AmcSaleTag addTag(AmcSaleTag amcSaleTag){
        amcSaleTagMapper.insertSelective(amcSaleTag);
        return amcSaleTag;
    }






}
