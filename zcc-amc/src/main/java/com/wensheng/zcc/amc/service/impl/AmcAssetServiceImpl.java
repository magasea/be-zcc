package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.mapper.AmcAssetMapper;
import com.wensheng.zcc.amc.module.dao.mysql.AmcAsset;
import com.wensheng.zcc.amc.service.AmcAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chenwei on 12/5/18
 * @project zcc-backend
 */
@Service
public class AmcAssetServiceImpl implements AmcAssetService {
    @Autowired
    AmcAssetMapper amcAssetMapper;

    @Override
    public List<AmcAsset> getAllAmcAssets() {
        return amcAssetMapper.getAll();
    }
}
