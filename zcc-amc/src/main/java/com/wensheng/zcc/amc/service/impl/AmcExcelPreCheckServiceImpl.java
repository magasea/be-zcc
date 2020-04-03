package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.aop.QueryAssetPreChecker;
import com.wensheng.zcc.amc.aop.QueryDebtPreChecker;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcAssetMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcAssetPreMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtPreMapper;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetAdditional;
import com.wensheng.zcc.amc.module.dao.mongo.entity.DebtAdditional;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.*;
import com.wensheng.zcc.amc.module.vo.AmcAssetVo;
import com.wensheng.zcc.amc.service.AmcAssetService;
import com.wensheng.zcc.amc.service.AmcDebtService;
import com.wensheng.zcc.amc.service.AmcExcelPreCheckService;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
@Service
public class AmcExcelPreCheckServiceImpl implements AmcExcelPreCheckService {

    @Autowired
    AmcDebtPreMapper amcDebtPreMapper;

    @Autowired
    AmcAssetPreMapper amcAssetPreMapper;

    @Autowired
    AmcDebtMapper amcDebtMapper;

    @Autowired
    AmcAssetMapper amcAssetMapper;

    @Autowired
    AmcDebtService amcDebtService;

    @Autowired
    AmcAssetService amcAssetService;


    @Override
    public boolean checkDebtTitleExist(String debtTitle) {
        AmcDebtExample amcDebtExample = new AmcDebtExample();
        amcDebtExample.createCriteria().andTitleEqualTo(debtTitle);
        List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);
        if(CollectionUtils.isEmpty(amcDebts)){
            return false;
        }
        return true;
    }


    private boolean checkAssetTitleExist(String assetTitle, Long debtId){
        AmcAssetExample amcAssetExample = new AmcAssetExample();
        amcAssetExample.createCriteria().andDebtIdEqualTo(debtId).andTitleEqualTo(assetTitle);
        List<AmcAsset> amcAssets = amcAssetMapper.selectByExample(amcAssetExample);
        if(CollectionUtils.isEmpty(amcAssets)){
            return false;
        }
        return true;
    }



    @Override
    @QueryDebtPreChecker
    public List<AmcDebtPre> getAllPreDebts(AmcDebtPreExample amcDebtPreExample) {
        amcDebtPreExample.setOrderByClause(" id desc ");
        return amcDebtPreMapper.selectByExample(amcDebtPreExample);
    }

    @Override
    @QueryAssetPreChecker
    public List<AmcAssetPre> getAllPreAssets(AmcAssetPreExample amcAssetPreExample) {
        amcAssetPreExample.setOrderByClause(" id desc ");
        return amcAssetPreMapper.selectByExample(amcAssetPreExample);
    }

    @Override
    @QueryDebtPreChecker
    public AmcDebtPre createDebt(AmcDebtPre amcDebtPre) {

        amcDebtPreMapper.insertSelective(amcDebtPre);
        return amcDebtPre;

    }

    @Override
    @QueryAssetPreChecker
    public AmcAssetPre createAsset(AmcAssetPre amcAssetPre) {

        amcAssetPreMapper.insertSelective(amcAssetPre);
        return amcAssetPre;
    }

    @Override
    public AmcDebtPre updateDebt(AmcDebtPre amcDebtPre) {
        amcDebtPreMapper.updateByPrimaryKeySelective(amcDebtPre);
        return amcDebtPre;
    }

    @Override
    public AmcAssetPre updateAsset(AmcAssetPre amcAssetPre) {
        amcAssetPreMapper.updateByPrimaryKeySelective(amcAssetPre);
        return amcAssetPre;
    }

    @Override
    @QueryDebtPreChecker
    public void deleteAllDebtPre(AmcDebtPreExample amcDebtPreExample) {
        amcDebtPreMapper.deleteByExample(amcDebtPreExample);

    }

    @Override
    @QueryAssetPreChecker
    public void deleteAllAssetPre(AmcAssetPreExample amcAssetPreExample) {
        amcAssetPreMapper.deleteByExample(amcAssetPreExample);
    }

    @Override
    public boolean transferDebtPre2Debt(List<AmcDebtPre> amcDebtPres ) throws Exception {

        for(AmcDebtPre amcDebtPre: amcDebtPres){
            if(checkDebtTitleExist(amcDebtPre.getTitle())){
                throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.FAILED_TRANSFEREXCEL_TO_DB, String.format("duplicate debt title:%s", amcDebtPre.getTitle()));
            }
            AmcDebt amcDebt = new AmcDebt();
            AmcBeanUtils.copyProperties(amcDebtPre, amcDebt);
            amcDebt.setId(null);
            amcDebtMapper.insertSelective(amcDebt);
            amcDebtService.saveDebtDesc(amcDebtPre.getDebtDesc(), amcDebt.getId());
            List<AmcAssetPre> amcAssetPres = getAmcAssetPreByDebtPreId(amcDebtPre.getId());
            if(CollectionUtils.isEmpty(amcAssetPres)){
                continue;
            }
            for(AmcAssetPre amcAssetPre: amcAssetPres){
                if(checkAssetTitleExist(amcAssetPre.getTitle(), amcDebt.getId())){
                    throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.FAILED_TRANSFEREXCEL_TO_DB,
                            String.format("duplicate asset title:%s in debt with title:%s and id:%s", amcDebtPre.getTitle(), amcDebt.getTitle(), amcDebt.getId()));
                }
                AmcAsset amcAsset = new AmcAsset();
                AmcBeanUtils.copyProperties(amcAssetPre, amcAsset);
                amcAsset.setId(null);
                amcAsset.setDebtId(amcDebt.getId());
                AmcAssetVo amcAssetVo = amcAssetService.create(amcAsset);
                AssetAdditional assetAdditional = new AssetAdditional();
                assetAdditional.setWarrant(amcAssetPre.getWarrant());
                assetAdditional.setLandUsageType(amcAssetPre.getLandusage());
                assetAdditional.setLandSupplyType(amcAssetPre.getLandsupply());
                assetAdditional.setDescription(amcAssetPre.getAssetNote());
                assetAdditional.setAmcAssetId(amcAssetVo.getId());
                assetAdditional.setOwner(amcAssetPre.getOwner());
                amcAssetService.createOrUpdateAssetAddition(assetAdditional);

            }


        }


        return true;
    }

    private List<AmcAssetPre> getAmcAssetPreByDebtPreId(Long amcDebtPreId){
        AmcAssetPreExample amcAssetPreExample = new AmcAssetPreExample();
        amcAssetPreExample.createCriteria().andDebtIdEqualTo(amcDebtPreId);
        List<AmcAssetPre> amcAssetPres = amcAssetPreMapper.selectByExample(amcAssetPreExample);
        return amcAssetPres;
    }

    @Override
    public boolean transferAssetPre2Asset(List<AmcAssetPre> amcAssetPres) {
        for(AmcAssetPre amcAssetPre: amcAssetPres){
//            if()
        }

        return false;
    }


}
