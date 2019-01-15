package com.wensheng.zcc.amc.service;

import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetAdditional;
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

    public AmcAssetVo create(AmcAsset amcAsset);

    public AssetAdditional createAssetAddition(AssetAdditional additional);

    public AmcAssetVo del(AmcAsset amcAsset);

    public AmcAssetVo update(AmcAsset amcAsset);

    public List<AmcAssetVo> queryAll(int offset, int size);

    public AmcAssetVo get(Long amcAssetId);

    public List<AmcAssetVo> query(AmcAsset queryCond, int offset, int size);

    public AmcAssetDetailVo queryAssetDetail(Long assetId);

    public List<AmcAssetDetailVo> queryAssetDetails(Long amcDebtId);


    public List<AmcAssetVo> queryAssetPage( int offset, int pageSize, Map<String, Object> queryParam, Map<String,
        Direction> orderByParam) throws Exception;

    Long getAssetCount(Map<String, Object> queryParam);

    Map<String, List<Long>> getAllAssetTitles();

  AssetImage saveImageInfo( AssetImage assetImage);
}
