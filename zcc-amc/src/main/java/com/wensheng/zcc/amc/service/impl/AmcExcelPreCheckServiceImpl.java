package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.aop.QueryAssetPreChecker;
import com.wensheng.zcc.amc.aop.QueryDebtPreChecker;
import com.wensheng.zcc.amc.dao.mysql.mapper.*;
import com.wensheng.zcc.amc.module.dao.helper.DebtorRoleEnum;
import com.wensheng.zcc.amc.module.dao.helper.DebtorTypeEnum;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetAdditional;
import com.wensheng.zcc.amc.module.dao.mongo.entity.DebtAdditional;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.*;
import com.wensheng.zcc.amc.module.vo.AmcAssetVo;
import com.wensheng.zcc.amc.service.*;
import com.wensheng.zcc.common.params.sso.AmcLocationEnum;
import com.wensheng.zcc.common.params.sso.SSOAmcUser;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.common.utils.AmcExceptions;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
@Service
@Slf4j
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

    @Autowired
    AmcExcelFileService amcExcelFileService;

    @Autowired
    AmcDebtorMapper amcDebtorMapper;

    @Autowired
    AmcDebtpackService amcDebtpackService;

    static final String SEP_CHAR = ",";
    static final String KEY_WORD_CMPY = "公司";


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
    @Transactional
    public boolean transferDebtPre2Debt(List<AmcDebtPre> amcDebtPres ) throws Exception {

        for(AmcDebtPre amcDebtPre: amcDebtPres){
            if(checkDebtTitleExist(amcDebtPre.getTitle())){
                throw ExceptionUtils.getAmcException(AmcExceptions.FAILED_TRANSFEREXCEL_TO_DB, String.format("数据库中已经有名字为:%s 的债权", amcDebtPre.getTitle()));
            }
            AmcDebt amcDebt = new AmcDebt();
            AmcBeanUtils.copyProperties(amcDebtPre, amcDebt);
            amcDebt.setAmcContactorId(amcDebtPre.getAmcContactorId());
            amcDebt.setId(null);
            if(StringUtils.isEmpty(amcDebtPre.getAmcContactorName()) ){
                log.error("amc contactor is empty ");
                throw ExceptionUtils.getAmcException(AmcExceptions.FAILED_TRANSFEREXCEL_TO_DB,
                        String.format("amc contactor is %s  ", amcDebtPre.getAmcContactorName()));

            }else if(amcDebtPre.getDebtpackId() <= 0){
               SSOAmcUser ssoAmcUser =  amcExcelFileService.getAmcContactorByName(amcDebtPre.getAmcContactorName());

                List<ZccDebtpack> zccDebtpacks =  amcDebtpackService.queryPacksWithLocation(AmcLocationEnum.lookupByDisplayIdUtil(ssoAmcUser.getLocation()));
                if(CollectionUtils.isEmpty(zccDebtpacks)){
                    throw ExceptionUtils.getAmcException(AmcExceptions.FAILED_TRANSFEREXCEL_TO_DB,
                            String.format("ssoAmcUser location is %s  ", ssoAmcUser.getLocation()));
//                        log.error(String.format("错误提示： %s %s There is no zccDebtPack for ssoAmcUser with location: %s", sheetDebt.getSheetName(), row.getRowNum(), ssoAmcUser.getLocation()));
                }else{
                    amcDebtPre.setDebtpackId(zccDebtpacks.get(0).getId());
                }
            }
            amcDebtMapper.insertSelective(amcDebt);
            DebtAdditional debtAdditional = new DebtAdditional();
            debtAdditional.setDesc(amcDebtPre.getDebtDesc());
            debtAdditional.setDebtClue(amcDebtPre.getDebtClue());
            amcDebtService.saveDebtAddition(debtAdditional, amcDebt.getId());
            if(!StringUtils.isEmpty(amcDebtPre.getGuarantee())){
                makeGrantors(amcDebtPre.getGuarantee(), amcDebt.getId());
            }
            if(!StringUtils.isEmpty(amcDebtPre.getBorrower())){
                makeBrowwers(amcDebtPre.getBorrower(), amcDebt.getId());
            }
            List<AmcAssetPre> amcAssetPres = getAmcAssetPreByDebtPreId(amcDebtPre.getId());
            if(CollectionUtils.isEmpty(amcAssetPres)){
                continue;
            }
            for(AmcAssetPre amcAssetPre: amcAssetPres){
                if(checkAssetTitleExist(amcAssetPre.getTitle(), amcDebt.getId())){
                    throw ExceptionUtils.getAmcException(AmcExceptions.FAILED_TRANSFEREXCEL_TO_DB,
                            String.format("duplicate asset title:%s in debt with title:%s and id:%s", amcDebtPre.getTitle(), amcDebt.getTitle(), amcDebt.getId()));
                }
                AmcAsset amcAsset = new AmcAsset();
                AmcBeanUtils.copyProperties(amcAssetPre, amcAsset);
                amcAsset.setId(null);
                amcAsset.setDebtId(amcDebt.getId());
                amcAsset.setAmcContactorId(amcDebt.getAmcContactorId());
                amcAsset.setAmcContactorName(amcDebt.getAmcContactorName());
                amcAsset.setAmcContactorPhone(amcDebt.getAmcContactorPhone());
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

    @Override
    public void makeGrantors(String cellGrantor, Long debtId) {
        AmcDebtorExample amcDebtorExample = new AmcDebtorExample();
        amcDebtorExample.createCriteria().andDebtIdEqualTo(debtId).andRoleEqualTo(DebtorRoleEnum.GUARANTOR.getId());
        amcDebtorMapper.deleteByExample(amcDebtorExample);

        if(cellGrantor.contains(SEP_CHAR)){
            String[] cellGrantors = cellGrantor.split(SEP_CHAR);
            for(String grantor: cellGrantors){
                makeGrantor(grantor, debtId);
            }
        }else{
            makeGrantor(cellGrantor, debtId);
        }
    }

    private void makeGrantor(String cellGrantor, Long debtId) {
        AmcDebtor amcDebtor = new AmcDebtor();
        amcDebtor.setDebtorName(cellGrantor);
        amcDebtor.setRole(DebtorRoleEnum.GUARANTOR.getId());
        amcDebtor.setDebtId(debtId);
        if(cellGrantor.contains(KEY_WORD_CMPY)){


            amcDebtor.setDebtorType(DebtorTypeEnum.COMPANY.getId());

        }else{
            amcDebtor.setDebtorType(DebtorTypeEnum.PERSON.getId());
        }
        try{
            amcDebtorMapper.insertSelective(amcDebtor);
        }catch (DataIntegrityViolationException ex){
            log.error("Duplication:", ex);
        }
    }

    @Override
    public void makeBrowwers(String cellBrowwer, Long debtId) {
        AmcDebtorExample amcDebtorExample = new AmcDebtorExample();
        amcDebtorExample.createCriteria().andDebtIdEqualTo(debtId).andRoleEqualTo(DebtorRoleEnum.BROWWER.getId());
        amcDebtorMapper.deleteByExample(amcDebtorExample);

        if(cellBrowwer.contains(SEP_CHAR)){
            String[] cellBrowwers = cellBrowwer.split(SEP_CHAR);
            for(String browwer: cellBrowwers){
                makeBrowwer(browwer, debtId);
            }
        }else{
            makeBrowwer(cellBrowwer, debtId);
        }

    }

    private void makeBrowwer(String cellBrowwer, Long debtId) {
        AmcDebtor amcDebtor = new AmcDebtor();
        amcDebtor.setDebtorName(cellBrowwer);
        amcDebtor.setRole(DebtorRoleEnum.BROWWER.getId());
        amcDebtor.setDebtId(debtId);
        if(cellBrowwer.contains(KEY_WORD_CMPY)){


            amcDebtor.setDebtorType(DebtorTypeEnum.COMPANY.getId());

        }else{
            amcDebtor.setDebtorType(DebtorTypeEnum.PERSON.getId());
        }
        try{
            amcDebtorMapper.insertSelective(amcDebtor);
        }catch (DataIntegrityViolationException ex){
            log.error("Duplication:", ex);
        }


    }
}
