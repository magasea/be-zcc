package com.wensheng.zcc.amc.service.impl;


import com.wensheng.zcc.amc.dao.mysql.mapper.AmcAssetMapper;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAsset;
import com.wensheng.zcc.amc.module.vo.AmcAssetVo;
import com.wensheng.zcc.amc.service.AmcAssetService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chenwei on 12/5/18
 * @project zcc-backend
 */
@Service
public class AmcAssetServiceImpl implements AmcAssetService {
    @Autowired
    AmcAssetMapper amcAssetMapper;



    @Override
    public AmcAssetVo create(AmcAsset amcAsset) {
        Long id = Long.valueOf(amcAssetMapper.insertSelective(amcAsset));

        return null;
    }

    @Override
    public AmcAssetVo del(AmcAsset amcAsset) {
        return null;
    }

    @Override
    public AmcAssetVo update(AmcAsset amcAsset) {
        return null;
    }

    @Override
    public List<AmcAssetVo> queryAll(int offset, int size) {
        return null;
    }

    @Override
    public AmcAssetVo get(Long amcAssetId) {
        return null;
    }

    @Override
    public List<AmcAssetVo> query(AmcAsset queryCond, int offset, int size) {
        return null;
    }
}
