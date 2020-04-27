package com.wensheng.zcc.amc.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wensheng.zcc.amc.dao.mysql.mapper.*;
import com.wensheng.zcc.amc.module.dao.helper.FilterTypeEnum;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.*;
import com.wensheng.zcc.amc.module.vo.AmcFilterContentItem;
import com.wensheng.zcc.amc.module.vo.AmcFilterContentTag;
import com.wensheng.zcc.amc.module.vo.AmcSaleFilter;
import com.wensheng.zcc.amc.module.vo.AmcSaleFloorVo;
import com.wensheng.zcc.amc.service.AmcAssetService;
import com.wensheng.zcc.amc.service.AmcDebtService;
import com.wensheng.zcc.amc.service.AmcSaleService;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import java.util.ArrayList;
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
    public List<AmcSaleFloorVo> getFloors(){
        AmcSaleFloorExample amcSaleFloorExample = new AmcSaleFloorExample();
        amcSaleFloorExample.setOrderByClause(" id desc ");
        List<AmcSaleFloor> amcSaleFloors = amcSaleFloorMapper.selectByExample(amcSaleFloorExample);
        List<AmcSaleFloorVo> amcSaleFloorVos = new ArrayList<>();
        TypeToken<ArrayList<AmcFilterContentItem>> token = new TypeToken<ArrayList<AmcFilterContentItem>>() {};
        for(AmcSaleFloor amcSaleFloor : amcSaleFloors){


            AmcSaleFloorVo amcSaleFloorVo = new AmcSaleFloorVo();

            amcSaleFloorVo.setAmcSaleFloor(amcSaleFloor);
            AmcSaleFilter amcSaleFilter =  gson.fromJson(amcSaleFloor.getFilterContent(), AmcSaleFilter.class);

            amcSaleFloorVo.setAmcSaleFilter(amcSaleFilter);
            AmcSaleFilter amcSaleFilterRecomm = gson.fromJson(amcSaleFloor.getRecomItems(), AmcSaleFilter.class);
            amcSaleFloorVo.setAmcRecommItem(amcSaleFilterRecomm);
            amcSaleFloorVos.add(amcSaleFloorVo);


        }

        return amcSaleFloorVos;
    }

    @Override
    public boolean updateFloor(AmcSaleFloorVo amcSaleFloorVo) throws Exception {
        checkFilterContent(amcSaleFloorVo);
        amcSaleFloorMapper.updateByPrimaryKeySelective(amcSaleFloorVo.getAmcSaleFloor());
      return true;
    }

    private void checkFilterContent(AmcSaleFloorVo amcSaleFloorVo) throws Exception {
        AmcSaleFloor amcSaleFloor = new AmcSaleFloor();
        if(amcSaleFloorVo.getAmcRecommItem() != null && (amcSaleFloorVo.getAmcRecommItem().getFilterAssetItemList() != null &&
        !CollectionUtils.isEmpty(amcSaleFloorVo.getAmcRecommItem().getFilterAssetItemList())
        || amcSaleFloorVo.getAmcRecommItem().getFilterDebtItemList() != null &&
            !CollectionUtils.isEmpty(amcSaleFloorVo.getAmcRecommItem().getFilterDebtItemList()))){
            amcSaleFloor.setRecomItems(gson.toJson(amcSaleFloorVo.getAmcRecommItem()));

            if(amcSaleFloorVo.getAmcRecommItem().getFilterTagItemList() != null &&
                !CollectionUtils.isEmpty(amcSaleFloorVo.getAmcRecommItem().getFilterTagItemList())){
                throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_JSON_CONTENT_ERROR);

            }
        }
        if(amcSaleFloorVo.getAmcSaleFilter() != null && (amcSaleFloorVo.getAmcSaleFilter().getFilterDebtItemList() != null &&
        !CollectionUtils.isEmpty(amcSaleFloorVo.getAmcSaleFilter().getFilterDebtItemList())
        || amcSaleFloorVo.getAmcSaleFilter().getFilterAssetItemList() != null &&
            !CollectionUtils.isEmpty(amcSaleFloorVo.getAmcSaleFilter().getFilterAssetItemList()))
        || amcSaleFloorVo.getAmcSaleFilter().getFilterTagItemList() != null &&
            !CollectionUtils.isEmpty(amcSaleFloorVo.getAmcSaleFilter().getFilterTagItemList())) {
            amcSaleFloor.setFilterContent(gson.toJson(amcSaleFloorVo.getAmcSaleFilter()));
        }

    }

    @Override
    public List<AmcSaleTag> getTags(){
        AmcSaleTagExample amcSaleTagExample = new AmcSaleTagExample();
        amcSaleTagExample.setOrderByClause(" id desc ");
        return amcSaleTagMapper.selectByExample(amcSaleTagExample);
    }

    @Override
    public boolean updateFloorAllFilter(Long floorId, AmcSaleFilter amcSaleFloorFilter, AmcSaleFilter recommItems) {
        AmcSaleFloor amcSaleFloor = amcSaleFloorMapper.selectByPrimaryKey(floorId);
        if(amcSaleFloor == null ){
            return false;
        }
        if(null != amcSaleFloor){
            amcSaleFloor.setFilterContent(gson.toJson(amcSaleFloorFilter));
        }
        if(null != recommItems){
            amcSaleFloor.setRecomItems(gson.toJson(recommItems));
        }
        amcSaleFloorMapper.updateByPrimaryKeySelective(amcSaleFloor);
        return true;
    }

    @Override
    public boolean updateFloorFilterRecom(Long floorId, AmcSaleFilter recommItems) {
        return false;
    }

    @Override
    public boolean updateFloorFilter(Long floorId, AmcSaleFilter amcSaleFilter) {
        AmcSaleFloor amcSaleFloor = amcSaleFloorMapper.selectByPrimaryKey(floorId);
        if(amcSaleFloor == null ){
            return false;
        }
        if(CollectionUtils.isEmpty(amcSaleFilter.getFilterAssetItemList()) ||
            CollectionUtils.isEmpty(amcSaleFilter.getFilterDebtItemList()) ||
            CollectionUtils.isEmpty(amcSaleFilter.getFilterTagItemList())
        ){
            log.error("empty filter content item list");
            return false;
        }
        amcSaleFloor.setFilterContent(gson.toJson(amcSaleFilter));

        return true;
    }

    @Override
    public boolean updateFloorBasic(AmcSaleFloor amcSaleFloor) {
        return false;
    }

    @Override
    public AmcSaleFloorVo createFloor(AmcSaleFloorVo amcSaleFloorVo) {
        AmcSaleFloor amcSaleFloor = amcSaleFloorVo.getAmcSaleFloor();

        if(CollectionUtils.isEmpty(amcSaleFloorVo.getAmcSaleFilter().getFilterDebtItemList())||
            CollectionUtils.isEmpty(amcSaleFloorVo.getAmcSaleFilter().getFilterDebtItemList())||
            CollectionUtils.isEmpty(amcSaleFloorVo.getAmcSaleFilter().getFilterDebtItemList())

        ){
            log.info("There is no filter item");
            amcSaleFloor.setFilterContent(null);
        }else{
            amcSaleFloor.setFilterContent(gson.toJson(amcSaleFloorVo.getAmcSaleFilter()));
        }
        if(CollectionUtils.isEmpty(amcSaleFloorVo.getAmcRecommItem().getFilterAssetItemList())
        || CollectionUtils.isEmpty(amcSaleFloorVo.getAmcRecommItem().getFilterDebtItemList())){
            log.info("There is no recomm items");
            amcSaleFloor.setRecomItems(null);
        }else{
            amcSaleFloor.setRecomItems(gson.toJson(amcSaleFloorVo.getAmcRecommItem()));
        }

        amcSaleFloorMapper.insertSelective(amcSaleFloor);
        amcSaleFloorVo.setAmcSaleFloor(amcSaleFloor);
        return amcSaleFloorVo;
    }

    public void updateTag(AmcSaleTag amcSaleTag){
        amcSaleTagMapper.updateByPrimaryKeySelective(amcSaleTag);
    }



    public AmcSaleTag addTag(AmcSaleTag amcSaleTag){
        amcSaleTagMapper.insertSelective(amcSaleTag);
        return amcSaleTag;
    }






}
