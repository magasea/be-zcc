package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.dao.mysql.mapper.*;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.*;
import com.wensheng.zcc.amc.service.AmcAssetService;
import com.wensheng.zcc.amc.service.AmcDebtService;
import com.wensheng.zcc.amc.service.AmcSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmcSaleServiceImpl implements AmcSaleService {
    @Autowired
    AmcAssetService amcAssetService;

    @Autowired
    AmcDebtService amcDebtService;


    @Autowired
    AmcSaleFloorMapper amcSaleFloorMapper;

    @Autowired
    AmcSaleFilterMapper amcSaleFilterMapper;

    @Autowired
    AmcSaleTagMapper amcSaleTagMapper;

    @Autowired
    AmcSaleFilterFloorMapper amcSaleFilterFloorMapper;

    @Autowired
    AmcSaleTagDebtMapper amcSaleTagDebtMapper;

    @Autowired
    AmcSaleTagAssetMapper amcSaleTagAssetMapper;

    public List<AmcSaleFloor> getFloors(){
        AmcSaleFloorExample amcSaleFloorExample = new AmcSaleFloorExample();
        amcSaleFloorExample.setOrderByClause(" id desc ");
        return amcSaleFloorMapper.selectByExample(amcSaleFloorExample);
    }

    public void updateFloor(AmcSaleFloor amcSaleFloor){
        amcSaleFloorMapper.updateByPrimaryKeySelective(amcSaleFloor);
    }

    public List<AmcSaleTag> getTags(){
        AmcSaleTagExample amcSaleTagExample = new AmcSaleTagExample();
        amcSaleTagExample.setOrderByClause(" id desc ");
        return amcSaleTagMapper.selectByExample(amcSaleTagExample);
    }
    public void updateTag(AmcSaleTag amcSaleTag){
        amcSaleTagMapper.updateByPrimaryKeySelective(amcSaleTag);
    }

    public List<AmcSaleFilter> getFilters(){
        AmcSaleFilterExample amcSaleFilterExample = new AmcSaleFilterExample();
        amcSaleFilterExample.setOrderByClause(" id desc ");
        return amcSaleFilterMapper.selectByExample(amcSaleFilterExample);
    }

    public void updateFilter(AmcSaleFilter amcSaleFilter){
        amcSaleFilterMapper.updateByPrimaryKeySelective(amcSaleFilter);
    }

    public AmcSaleFloor addFloor(AmcSaleFloor amcSaleFloor){
        amcSaleFloorMapper.insertSelective(amcSaleFloor);
        return amcSaleFloor;
    }

    public AmcSaleFilter addFilter(AmcSaleFilter amcSaleFilter){
        amcSaleFilterMapper.insertSelective(amcSaleFilter);
        return amcSaleFilter;
    }

    public AmcSaleTag addTag(AmcSaleTag amcSaleTag){
        amcSaleTagMapper.insertSelective(amcSaleTag);
        return amcSaleTag;
    }






}
