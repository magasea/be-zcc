package com.wensheng.zcc.amc.service;

import com.wensheng.zcc.amc.module.dao.mysql.AmcAsset;

import java.util.List;

/**
 * @author chenwei on 12/5/18
 * @project zcc-backend
 */
public interface AmcAssetService {

    List<AmcAsset> getAllAmcAssets();
}
