package com.wensheng.zcc.amc.service;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAsset;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAsset;
import com.wensheng.zcc.amc.module.vo.AmcAssetVo;
import java.util.List;

/**
 * @author chenwei on 12/5/18
 * @project zcc-backend
 */
public interface AmcAssetService {

    public AmcAssetVo create(AmcAsset amcAsset);

    public AmcAssetVo del(AmcAsset amcAsset);

    public AmcAssetVo update(AmcAsset amcAsset);

    public List<AmcAssetVo> queryAll(int offset, int size);

    public AmcAssetVo get(Long amcAssetId);

    public List<AmcAssetVo> query(AmcAsset queryCond, int offset, int size);
}
