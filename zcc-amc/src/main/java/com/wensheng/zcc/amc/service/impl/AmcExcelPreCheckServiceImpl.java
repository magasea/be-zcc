package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.dao.mysql.mapper.AmcAssetPreMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtPreMapper;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAssetPre;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtPre;
import com.wensheng.zcc.amc.service.AmcExcelPreCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AmcExcelPreCheckServiceImpl implements AmcExcelPreCheckService {

    @Autowired
    AmcDebtPreMapper amcDebtPreMapper;

    @Autowired
    AmcAssetPreMapper amcAssetPreMapper;


    @Override
    public boolean checkDebtTitleExist(String debtTitle) {
        return false;
    }

    @Override
    public List<AmcDebtPre> getAllPreDebts() {

        return null;
    }

    @Override
    public List<AmcAssetPre> getAllAssets() {
        return null;
    }

    @Override
    public void saveDebt(AmcDebtPre amcDebtPre) {

    }

    @Override
    public void saveAsset(AmcAssetPre amcAssetPre) {

    }
}
