package com.wensheng.zcc.amc.service;

import com.wensheng.zcc.amc.module.dao.helper.PublishStateEnum;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetAdditional;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetDocument;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetImage;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAsset;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAsset;
import com.wensheng.zcc.amc.module.vo.AmcAssetDetailVo;
import com.wensheng.zcc.amc.module.vo.AmcAssetVo;
import com.wensheng.zcc.amc.module.vo.base.BaseActionVo;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

/**
 * @author chenwei on 12/5/18
 * @project zcc-backend
 */
public interface AmcAssetService {

    public AmcAssetVo create(AmcAsset amcAsset) throws Exception;

    public int delAsset(Long amcAssetId) throws Exception;

    public int del(Long amcDebtId);

    public void updateByDebtState(Long debtId, Integer publishState);

    public AmcAssetVo update(AmcAsset amcAsset) throws Exception;

    public AssetAdditional createOrUpdateAssetAddition(AssetAdditional additional);

    public List<AmcAssetVo> queryAll(int offset, int size);

    public AmcAssetVo get(Long amcAssetId);

    public List<AmcAssetVo> query(AmcAsset queryCond, int offset, int size);

    public AmcAssetDetailVo queryAssetDetail(Long assetId) throws Exception;

    public List<AmcAssetDetailVo> queryAssetDetails(Long amcDebtId) throws Exception;


    public List<AmcAssetVo> queryAssetPage( int offset, int pageSize, Map<String, Object> queryParam, Map<String,
        Direction> orderByParam) throws Exception;

    Long getAssetCount(Map<String, Object> queryParam) throws Exception;

    public List<AmcAssetVo> queryForHomePage(int size);

    Map<String, List<Long>> getAllAssetTitles();

  AssetImage saveImageInfo( AssetImage assetImage);


  AssetDocument saveDoc(AssetDocument assetDocument);

  void delImage(AssetImage assetImage);

  Long getAssetCountWithDebtIds(List<Long> amcDebtIds);

    void setRecomm(List<Long> assetIds, int isRecomm);

  List<AmcAssetVo> getAssetsByIds(List<Long> assetIds) throws Exception;
}
