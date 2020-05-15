package com.wensheng.zcc.amc.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcSaleBannerMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcSaleFloorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcSaleMenuMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcSaleTagAssetMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcSaleTagDebtMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcSaleTagMapper;
import com.wensheng.zcc.amc.module.dao.helper.AssetTypeEnum;
import com.wensheng.zcc.amc.module.dao.helper.DebtTypeEnum;
import com.wensheng.zcc.amc.module.dao.helper.FloorPublishStateEnum;
import com.wensheng.zcc.amc.module.dao.helper.GuarantTypeEnum;
import com.wensheng.zcc.amc.module.dao.helper.ImagePathClassEnum;
import com.wensheng.zcc.amc.module.dao.helper.SealStateEnum;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAsset;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleBanner;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleBannerExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleFloor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleFloorExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleMenu;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleMenuExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleTag;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleTagExample;
import com.wensheng.zcc.amc.module.vo.AmcAssetVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtVo;
import com.wensheng.zcc.amc.module.vo.AmcSaleWatchonPageVo;
import com.wensheng.zcc.common.module.dto.AmcFilterContentAsset;
import com.wensheng.zcc.common.module.dto.AmcFilterContentDebt;
import com.wensheng.zcc.common.module.dto.AmcFilterContentItem;
import com.wensheng.zcc.amc.module.vo.AmcSaleBannerPageVo;
import com.wensheng.zcc.amc.module.vo.AmcSaleBannerVo;
import com.wensheng.zcc.common.module.dto.AmcSaleFilter;
import com.wensheng.zcc.amc.module.vo.AmcSaleFloorFrontEndVo;
import com.wensheng.zcc.amc.module.vo.AmcSalePageModVo;
import com.wensheng.zcc.amc.module.vo.AmcSaleFloorPageVo;
import com.wensheng.zcc.amc.module.vo.AmcSaleFloorVo;
import com.wensheng.zcc.amc.module.vo.AmcSaleHomePage;
import com.wensheng.zcc.amc.module.vo.AmcSaleMenuPageVo;
import com.wensheng.zcc.amc.module.vo.AmcSaleMenuVo;
import com.wensheng.zcc.amc.module.vo.AmcSaleRecomItems;
import com.wensheng.zcc.amc.service.AmcAssetService;
import com.wensheng.zcc.amc.service.AmcDebtService;
import com.wensheng.zcc.amc.service.AmcOssFileService;
import com.wensheng.zcc.amc.service.AmcSaleService;
import com.wensheng.zcc.common.module.dto.WXUserWatchObject;
import com.wensheng.zcc.common.params.AmcDebtAssetTypeEnum;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.common.utils.AmcDateUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import com.wenshengamc.zcc.wechat.WXUserWatchOnObj;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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
    AmcOssFileService amcOssFileService;

    @Autowired
    AmcSaleTagDebtMapper amcSaleTagDebtMapper;

    @Autowired
    AmcSaleTagAssetMapper amcSaleTagAssetMapper;

    @Autowired
    AmcSaleMenuMapper amcSaleMenuMapper;

    @Autowired
    AmcSaleBannerMapper amcSaleBannerMapper;

    @Autowired
    WechatGrpcService wechatGrpcService;

    @Value("${env.name}")
    String envName;

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
            try{
                if(!amcSaleFloor.getFilterContent().equals("-1") && amcSaleFloor.getFilterContent().length() > 2){
                    AmcSaleFilter amcSaleFilter =  gson.fromJson(amcSaleFloor.getFilterContent(), AmcSaleFilter.class);
                    amcSaleFloorVo.setAmcSaleFilter(amcSaleFilter);
                }
                if(!amcSaleFloor.getRecomItems().equals("-1") && amcSaleFloor.getRecomItems().length() > 2){
                    AmcSaleRecomItems amcSaleFilterRecomm = gson.fromJson(amcSaleFloor.getRecomItems(), AmcSaleRecomItems.class);
                    amcSaleFloorVo.setAmcRecommItem(amcSaleFilterRecomm);
                }

            }catch (Exception ex){
                log.error("Failed to convert filter content to object", ex);
            }
            amcSaleFloorVos.add(amcSaleFloorVo);
        }

        return amcSaleFloorVos;
    }

    @Override
    public List<AmcSaleFloorFrontEndVo> getFrontEndFloors() throws Exception {
        List<AmcSaleFloorFrontEndVo> amcSaleFloorFrontEndVos = new ArrayList<>();

        // get all published floors
        AmcSaleFloorExample amcSaleFloorExample = new AmcSaleFloorExample();
        amcSaleFloorExample.setOrderByClause(" floor_seq asc ");
        amcSaleFloorExample.createCriteria().andPublishStateEqualTo(FloorPublishStateEnum.PUBLISHED.getStatus())
            .andFloorStartTimeLessThan(AmcDateUtils.getCurrentDate()).andFloorEndTimeGreaterThan(AmcDateUtils.getCurrentDate());

        List<AmcSaleFloor> amcSaleFloors = amcSaleFloorMapper.selectByExample(amcSaleFloorExample);
        // get all recomm items with floors
        for(AmcSaleFloor amcSaleFloor : amcSaleFloors){
            AmcSaleFloorFrontEndVo amcSaleFloorFrontEndVo = new AmcSaleFloorFrontEndVo();
            amcSaleFloorFrontEndVo.setAmcSaleFloor(amcSaleFloor);
            if(amcSaleFloor.getRecomItems() != null && !amcSaleFloor.getRecomItems().equals("-1")){
                AmcSaleRecomItems amcSaleRecomItems = null;
                try{
                    amcSaleRecomItems =  gson.fromJson(amcSaleFloor.getRecomItems(), AmcSaleRecomItems.class);
                }catch (Exception ex){
                    log.error("Failed to convert RecomItems content to AmcSaleFilter:{}", amcSaleFloor.getRecomItems(), ex);
                }
                if(amcSaleRecomItems.getAmcSaleRecommDebts() != null &&
                    !CollectionUtils.isEmpty(amcSaleRecomItems.getAmcSaleRecommDebts().getDebtIds())){
                    amcSaleFloorFrontEndVo.setAmcDebtVos(amcDebtService.queryBySeqIds(amcSaleRecomItems.getAmcSaleRecommDebts().getDebtIds()));
                }
                if(amcSaleRecomItems.getAmcSaleRecommAssets() != null &&
                    !CollectionUtils.isEmpty(amcSaleRecomItems.getAmcSaleRecommAssets().getAssetIds())){
                    amcSaleFloorFrontEndVo.setAmcAssetVos(
                        amcAssetService.getAssetsByIds(amcSaleRecomItems.getAmcSaleRecommAssets().getAssetIds()));

                }

            }
            amcSaleFloorFrontEndVos.add(amcSaleFloorFrontEndVo);
        }
        return amcSaleFloorFrontEndVos;
    }

    @Override
    public boolean updateFloor(AmcSaleFloorVo amcSaleFloorVo) throws Exception {
        checkFilterContent(amcSaleFloorVo);
        amcSaleFloorMapper.updateByPrimaryKeySelective(amcSaleFloorVo.getAmcSaleFloor());
      return true;
    }

    @Override
    public boolean updateFloorSeq(List<Long> floorIds) throws Exception {
        for(int idx = 1; idx <= floorIds.size(); idx++){
            AmcSaleFloor amcSaleFloor =  amcSaleFloorMapper.selectByPrimaryKey(floorIds.get(idx-1));
            amcSaleFloor.setFloorSeq(idx);
            amcSaleFloorMapper.updateByPrimaryKeySelective(amcSaleFloor);
        }
        return true;
    }

    @Override
    public void delFloor(Long floorId) throws Exception {
        AmcSaleFloor amcSaleFloor = amcSaleFloorMapper.selectByPrimaryKey(floorId);
        if(amcSaleFloor.getPublishState() == (FloorPublishStateEnum.PUBLISHED.getStatus())){
            throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_STATE, "当前状态不能被删除");
        }
        amcSaleFloorMapper.deleteByPrimaryKey(floorId);
    }

    private void checkFilterContent(AmcSaleFloorVo amcSaleFloorVo) throws Exception {
        AmcSaleFloor amcSaleFloor = amcSaleFloorVo.getAmcSaleFloor();
        if(amcSaleFloorVo.getAmcRecommItem() != null && (amcSaleFloorVo.getAmcRecommItem().getAmcSaleRecommAssets() != null &&
        !CollectionUtils.isEmpty(amcSaleFloorVo.getAmcRecommItem().getAmcSaleRecommAssets().getAssetIds())
        || amcSaleFloorVo.getAmcRecommItem().getAmcSaleRecommDebts() != null &&
            !CollectionUtils.isEmpty(amcSaleFloorVo.getAmcRecommItem().getAmcSaleRecommDebts().getDebtIds()))){
            amcSaleFloor.setRecomItems(gson.toJson(amcSaleFloorVo.getAmcRecommItem()));


        }
        if(amcSaleFloorVo.getAmcSaleFilter() != null && (amcSaleFloorVo.getAmcSaleFilter().getFilterDebt() != null
        || amcSaleFloorVo.getAmcSaleFilter().getFilterAsset() != null
        || amcSaleFloorVo.getAmcSaleFilter().getFilterTag() != null)) {
            if(amcSaleFloorVo.getAmcSaleFilter().getFilterDebt() != null &&
                !checkFilterContentDebt(amcSaleFloorVo.getAmcSaleFilter().getFilterDebt())){
                throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_FLOOR_FILTER_ERROR,
                    gson.toJson(amcSaleFloorVo.getAmcSaleFilter().getFilterDebt()));
            }

            if(amcSaleFloorVo.getAmcSaleFilter().getFilterAsset() != null &&
                !checkFilterContentAsset(amcSaleFloorVo.getAmcSaleFilter().getFilterAsset())){
                throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_FLOOR_FILTER_ERROR,
                    gson.toJson(amcSaleFloorVo.getAmcSaleFilter().getFilterAsset()));
            }
            amcSaleFloor.setFilterContent(gson.toJson(amcSaleFloorVo.getAmcSaleFilter()));
        }
        amcSaleFloorVo.setAmcSaleFloor(amcSaleFloor);

    }

    private boolean checkFilterContentAsset(AmcFilterContentAsset filterAsset) {
      if(filterAsset.getValuation() != null && filterAsset.getValuation().size() != 2 ){
        log.error("filterAsset valuation size not correct");
        return false;
      }
      if(filterAsset.getCityCode() !=null && !CollectionUtils.isEmpty(filterAsset.getCityCode()) ){
          for(String locationCode: filterAsset.getCityCode()){
              if(Integer.parseInt(locationCode) < 0 ){
                  log.error("filterAsset cityCode not correct");
                  return false;
              }
          }

      }
      if(filterAsset.getAssetTypes() != null){
        for(Integer assetType: filterAsset.getAssetTypes()){
          if(AssetTypeEnum.lookupByIdUtil(assetType) != null){
            continue;
          }else{
            log.error("filterAsset assetType not correct");
            return false;
          }
        }
      }
      if(filterAsset.getSealStatus() != null){
        for(Integer sealStatus: filterAsset.getSealStatus()){
          if(SealStateEnum.lookupByIdUtil(sealStatus) != null){
            continue;
          }else{
            log.error("filterAsset sealStatus not correct");
            return false;
          }
        }
      }
      if(filterAsset.getArea() != null && filterAsset.getArea().size() != 2){
        log.error("filterAsset area size not correct");
        return false;
      }

      if(filterAsset.getLandArea() != null && filterAsset.getLandArea().size() != 2){
        log.error("filterAsset landArea size not correct");
        return false;
      }
      return true;
    }

    private boolean checkFilterContentDebt(AmcFilterContentDebt filterDebt) {

        if(filterDebt.getBaseAmount() != null && filterDebt.getBaseAmount().size() != 2){
          log.error("filterDebt baseAmount size not correct");
          return false;
        }
//        if(filterDebt.getCourtCities() != null && filterDebt.getCourtCities().length < 1){
//            log.error("filterDebt courtCities size error");
//            return false;
//        }
        if(filterDebt.getGuarantType() != null && CollectionUtils.isEmpty(filterDebt.getGuarantType())){
            log.error("filterDebt guarantType size error");
            return false;

        }else if(!CollectionUtils.isEmpty(filterDebt.getGuarantType())){
            for(Integer guarantType: filterDebt.getGuarantType()){
                if(GuarantTypeEnum.lookupByIdUtil(guarantType) == null){
                    log.error("filterDebt guarantType error");
                    return false;
                }
            }
        }

        if(filterDebt.getDebtType() != null && CollectionUtils.isEmpty(filterDebt.getDebtType())){
            log.error("filterDebt getDebtType size error");
            return false;

        }else if(!CollectionUtils.isEmpty(filterDebt.getDebtType())){
            for(Integer debtType: filterDebt.getDebtType()){
                if(DebtTypeEnum.lookupByIdUtil(debtType) == null){
                    log.error("filterDebt guarantType error");
                    return false;
                }
            }
        }
        return true;
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
        if(amcSaleFilter.getFilterAsset() == null &&
            amcSaleFilter.getFilterDebt() == null &&
            amcSaleFilter.getFilterTag() == null){
            log.error("empty filter content item list");
            return false;
        }
        amcSaleFloor.setFilterContent(gson.toJson(amcSaleFilter));

        return true;
    }

    @Override
    public boolean updateFloorBasic(AmcSaleFloor amcSaleFloor) {
        amcSaleFloor.setFilterContent(null);
        amcSaleFloor.setRecomItems(null);
        if(amcSaleFloor.getPublishState().equals(FloorPublishStateEnum.UNPUBLISHED.getStatus())){
            AmcSaleFloor hisFloor = amcSaleFloorMapper.selectByPrimaryKey(amcSaleFloor.getId());
            if(hisFloor.getPublishState().equals(FloorPublishStateEnum.PUBLISHED.getStatus())
                && hisFloor.getFloorStartTime().before(AmcDateUtils.getCurrentDate())
                && hisFloor.getFloorEndTime().after(AmcDateUtils.getCurrentDate())
            ){
                amcSaleFloor.setManualEndTime(AmcDateUtils.getCurrentDate());
            }
        }
        amcSaleFloorMapper.updateByPrimaryKeySelective(amcSaleFloor);
        return true;
    }

    @Override
    public AmcSaleFloorVo createFloor(AmcSaleFloorVo amcSaleFloorVo) {
        AmcSaleFloor amcSaleFloor = amcSaleFloorVo.getAmcSaleFloor();

        if(amcSaleFloorVo.getAmcSaleFilter() == null || ( amcSaleFloorVo.getAmcSaleFilter().getFilterDebt() == null
            && amcSaleFloorVo.getAmcSaleFilter().getFilterAsset() == null &&
            amcSaleFloorVo.getAmcSaleFilter().getFilterTag() == null)){
            log.info("There is no filter item");
            amcSaleFloor.setFilterContent(null);
        }else{
            amcSaleFloor.setFilterContent(gson.toJson(amcSaleFloorVo.getAmcSaleFilter()));
        }
        if(amcSaleFloorVo.getAmcRecommItem() == null
        || (CollectionUtils.isEmpty(amcSaleFloorVo.getAmcRecommItem().getAmcSaleRecommDebts().getDebtIds())
        && CollectionUtils.isEmpty(amcSaleFloorVo.getAmcRecommItem().getAmcSaleRecommAssets().getAssetIds()))){
            log.info("There is no recomm items");
            amcSaleFloor.setRecomItems(null);
        }else{
            amcSaleFloor.setRecomItems(gson.toJson(amcSaleFloorVo.getAmcRecommItem()));
        }

        amcSaleFloorMapper.insertSelective(amcSaleFloor);
        amcSaleFloorVo.setAmcSaleFloor(amcSaleFloor);
        return amcSaleFloorVo;
    }

    @Override
    public List<AmcSaleMenuVo> getSaleMenus() {
        AmcSaleMenuExample amcSaleMenuExample = new AmcSaleMenuExample();
        amcSaleMenuExample.setOrderByClause(" id desc ");
        List<AmcSaleMenu> amcSaleMenus =amcSaleMenuMapper.selectByExample(amcSaleMenuExample);
        List<AmcSaleMenuVo> amcSaleMenuVos = convertMenu2Vos(amcSaleMenus);
        amcSaleMenus = null;
        return amcSaleMenuVos;
    }

    public List<AmcSaleMenu> getSimpleSaleMenus() {
        AmcSaleMenuExample amcSaleMenuExample = new AmcSaleMenuExample();
        amcSaleMenuExample.setOrderByClause(" id desc ");
        return amcSaleMenuMapper.selectByExample(amcSaleMenuExample);

    }

    private List<AmcSaleMenuVo> convertMenu2Vos(List<AmcSaleMenu> amcSaleMenus) {
        List<AmcSaleMenuVo> amcSaleMenuVos = new ArrayList<>();
        for(AmcSaleMenu amcSaleMenu: amcSaleMenus){
            AmcSaleMenuVo amcSaleMenuVo = new AmcSaleMenuVo();
            AmcBeanUtils.copyProperties(amcSaleMenu, amcSaleMenuVo);
            if(!StringUtils.isEmpty(amcSaleMenu.getFilterContent()) && !amcSaleMenu.getFilterContent().equals("-1")){
                amcSaleMenuVo.setAmcSaleFilter(gson.fromJson(amcSaleMenu.getFilterContent(), AmcSaleFilter.class));
            }

            amcSaleMenuVos.add(amcSaleMenuVo);
        }
        return amcSaleMenuVos;
    }

    @Override
    public AmcSaleMenu updateSaleMenu(AmcSaleMenu amcSaleMenu) {
        amcSaleMenuMapper.updateByPrimaryKeySelective(amcSaleMenu);
        return amcSaleMenu;
    }

    @Override
    public boolean updateSaleMenuSeq(List<Long> menuIds) {
        for(int idx = 1; idx <= menuIds.size(); idx++){
            AmcSaleMenu amcSaleMenu =  amcSaleMenuMapper.selectByPrimaryKey(menuIds.get(idx-1));
            amcSaleMenu.setSeq(idx);
            amcSaleMenuMapper.updateByPrimaryKeySelective(amcSaleMenu);
        }
        return true;
    }

    @Override
    public AmcSaleMenu addSaleMenu(AmcSaleMenu amcSaleMenu) {
        amcSaleMenuMapper.insertSelective(amcSaleMenu);
        return amcSaleMenu;
    }

    @Override
    public boolean delSaleMenu(Long saleMenuId) {
        amcSaleMenuMapper.deleteByPrimaryKey(saleMenuId);
        return true;
    }

    @Override
    public List<AmcSaleBannerVo> getSaleBanners() {
        AmcSaleBannerExample amcSaleBannerExample = new AmcSaleBannerExample();
        amcSaleBannerExample.setOrderByClause(" id desc ");

        List<AmcSaleBanner> amcSaleBanners =  amcSaleBannerMapper.selectByExample(amcSaleBannerExample);
        List<AmcSaleBannerVo> amcSaleBannerVos = convertBanner2Vos(amcSaleBanners);
        amcSaleBanners = null;
        return amcSaleBannerVos;

    }

    public List<AmcSaleBanner> getSimpleSaleBanners() {
        AmcSaleBannerExample amcSaleBannerExample = new AmcSaleBannerExample();
        amcSaleBannerExample.createCriteria().andPublishStateEqualTo(FloorPublishStateEnum.PUBLISHED.getStatus())
            .andStartTimeLessThan(AmcDateUtils.getCurrentDate()).andEndTimeGreaterThan(AmcDateUtils.getCurrentDate());
        amcSaleBannerExample.setOrderByClause(" id desc ");

        return  amcSaleBannerMapper.selectByExample(amcSaleBannerExample);

    }

    private List<AmcSaleBannerVo> convertBanner2Vos(List<AmcSaleBanner> amcSaleBanners) {
        List<AmcSaleBannerVo> amcSaleBannerVos = new ArrayList<>();
        for(AmcSaleBanner amcSaleBanner: amcSaleBanners){
            AmcSaleBannerVo amcSaleBannerVo = new AmcSaleBannerVo();
            AmcBeanUtils.copyProperties(amcSaleBanner, amcSaleBannerVo);
            if(!StringUtils.isEmpty(amcSaleBanner.getFilterContent()) && !amcSaleBanner.getFilterContent().equals("-1")){
                amcSaleBannerVo.setAmcSaleFilter(gson.fromJson(amcSaleBanner.getFilterContent(), AmcSaleFilter.class));
            }
            amcSaleBannerVos.add(amcSaleBannerVo);
        }
        return amcSaleBannerVos;
    }


    @Override
    public AmcSaleBanner updateSaleBanner(AmcSaleBanner amcSaleBanner) {
        amcSaleBannerMapper.updateByPrimaryKeySelective(amcSaleBanner);
        return amcSaleBanner;
    }

    @Override
    public boolean updateBannerSeq(List<Long> bannerIds) {
        for(int idx = 1; idx <= bannerIds.size(); idx++){
            AmcSaleBanner amcSaleBanner =  amcSaleBannerMapper.selectByPrimaryKey(bannerIds.get(idx-1));
            amcSaleBanner.setSeq(idx);
            amcSaleBannerMapper.updateByPrimaryKeySelective(amcSaleBanner);
        }
        return true;
    }

    @Override
    public AmcSaleBanner addSaleBanner(AmcSaleBanner amcSaleBanner) {
        amcSaleBannerMapper.insertSelective(amcSaleBanner);
        return amcSaleBanner;
    }

    @Override
    public boolean delSaleBanner(Long amcSaleBannerId) {
        amcSaleBannerMapper.deleteByPrimaryKey(amcSaleBannerId);
        return true;
    }

    public void updateTag(AmcSaleTag amcSaleTag){
        amcSaleTagMapper.updateByPrimaryKeySelective(amcSaleTag);
    }



    public AmcSaleTag addTag(AmcSaleTag amcSaleTag){
        amcSaleTagMapper.insertSelective(amcSaleTag);
        return amcSaleTag;
    }


    @Override
    public String getSaleMenuPrepath(Long saleMenuId){
        return new StringBuilder(ImagePathClassEnum.SALEMENU.getName()).append("/").append(envName).append("/").append(saleMenuId).append("/").toString();
    }

    @Override
    public String getSaleBannerPrepath(Long saleBannerId) {
        return new StringBuilder(ImagePathClassEnum.SALEBANNER.getName()).append("/").append(envName).append("/").append(saleBannerId).append("/").toString();
    }

    @Override
    public AmcSaleMenu updateSaleMenuImage(Long saleMenuId, String ossPath) {
        AmcSaleMenu amcSaleMenu =  amcSaleMenuMapper.selectByPrimaryKey(saleMenuId);
        amcSaleMenu.setImgUrl(ossPath);
        amcSaleMenuMapper.updateByPrimaryKeySelective(amcSaleMenu);
        return amcSaleMenu;
    }

    @Override
    public AmcSaleBanner updateSaleBannerImage(Long saleBannerId, String ossPath) {
        AmcSaleBanner amcSaleBanner = amcSaleBannerMapper.selectByPrimaryKey(saleBannerId);
        amcSaleBanner.setImgUrl(ossPath);
        amcSaleBannerMapper.updateByPrimaryKeySelective(amcSaleBanner);
        return amcSaleBanner;

    }

    @Override
    public AmcSaleHomePage getAmcSaleHome() throws Exception {
        AmcSaleHomePage amcSaleHomePage = new AmcSaleHomePage();
        amcSaleHomePage.setAmcSaleFloorVoList(getFrontEndFloors());
        amcSaleHomePage.setAmcSaleBannerList(getSimpleSaleBanners());
        amcSaleHomePage.setAmcSaleMenuList(getSimpleSaleMenus());

        return amcSaleHomePage;
    }

    @Override
    public AmcSaleFloorPageVo getFloorPage(Long floorId) throws Exception {
        AmcSaleFloorPageVo amcSaleFloorPageVo = new AmcSaleFloorPageVo();
        AmcSaleFloor amcSaleFloor =  amcSaleFloorMapper.selectByPrimaryKey(floorId);
        amcSaleFloorPageVo.setAmcSaleFloor(amcSaleFloor);
        List<Object> resultList = new ArrayList<>();
        if(amcSaleFloor == null){
            return amcSaleFloorPageVo;
        }
        if(amcSaleFloor.getFilterContent() != null && !StringUtils.isEmpty(amcSaleFloor.getFilterContent()) && !amcSaleFloor.getFilterContent().equals("-1")){
            AmcSaleFilter amcSaleFilter =  gson.fromJson(amcSaleFloor.getFilterContent(), AmcSaleFilter.class);
            amcSaleFloorPageVo.setAmcSaleFilter(amcSaleFilter);
            if(amcSaleFilter.getFilterAsset() != null){
                // get filtered assets
                List<AmcAssetVo> amcAssetVos = getFilterAssets(amcSaleFilter.getFilterAsset());
                if(!CollectionUtils.isEmpty(amcAssetVos)){
                    resultList.addAll(amcAssetVos);
                }
            }
            if(amcSaleFilter.getFilterDebt() != null){
                // get filtered debts
                List<AmcDebtVo> amcDebtVos = getFilterDebts(amcSaleFilter.getFilterDebt());
                resultList.addAll(amcDebtVos);
            }

        }

        amcSaleFloorPageVo.setResultList(resultList);

        return amcSaleFloorPageVo;
    }

    @Override
    public AmcSaleFloorPageVo getFloorPageWithFilter(AmcSalePageModVo amcSaleFloorPageModVo)
        throws Exception {
        AmcSaleFloorPageVo amcSaleFloorPageVo = new AmcSaleFloorPageVo();
        List<Object> resultList = new ArrayList<>();
        amcSaleFloorPageVo.setAmcSaleFilter(amcSaleFloorPageModVo.getAmcSaleFilter());
        AmcSaleFilter amcSaleFilter = amcSaleFloorPageModVo.getAmcSaleFilter();
        if(amcSaleFilter.getFilterAsset() != null){
            // get filtered assets
            List<AmcAssetVo> amcAssetVos = getFilterAssets(amcSaleFilter.getFilterAsset());
            if(!CollectionUtils.isEmpty(amcAssetVos)){
                resultList.addAll(amcAssetVos);
            }
        }
        if(amcSaleFilter.getFilterDebt() != null){
            // get filtered debts
            List<AmcDebtVo> amcDebtVos = getFilterDebts(amcSaleFilter.getFilterDebt());
            resultList.addAll(amcDebtVos);
        }
        amcSaleFloorPageVo.setResultList(resultList);
        return amcSaleFloorPageVo;
    }



    @Override
    public AmcSaleMenuPageVo getMenuPage(Long menuId) throws Exception {
        AmcSaleMenuPageVo amcSaleMenuPageVo = new AmcSaleMenuPageVo();
        AmcSaleMenu amcSaleMenu =  amcSaleMenuMapper.selectByPrimaryKey(menuId);
        List<Object> resultList = new ArrayList<>();
        if(amcSaleMenu == null){
            return amcSaleMenuPageVo;
        }
        if(amcSaleMenu.getFilterContent() != null && !StringUtils.isEmpty(amcSaleMenu.getFilterContent())){

            AmcSaleFilter amcSaleFilter =  gson.fromJson(amcSaleMenu.getFilterContent(), AmcSaleFilter.class);

            if(amcSaleFilter.getFilterAsset() != null){
                // get filtered assets
                List<AmcAssetVo> amcAssetVos = getFilterAssets(amcSaleFilter.getFilterAsset());
                if(!CollectionUtils.isEmpty(amcAssetVos)){
                    resultList.addAll(amcAssetVos);
                }
            }
            if(amcSaleFilter.getFilterDebt() != null){
                // get filtered debts
                List<AmcDebtVo> amcDebtVos = getFilterDebts(amcSaleFilter.getFilterDebt());
                resultList.addAll(amcDebtVos);
            }
            amcSaleMenuPageVo.setAmcSaleFilter(amcSaleFilter);
        }
        amcSaleMenuPageVo.setResultList(resultList);
        return amcSaleMenuPageVo;
    }

    @Override
    public AmcSaleMenuPageVo getMenuPageWithFilter(AmcSalePageModVo amcSalePageModVo)
        throws Exception {
        AmcSaleMenuPageVo amcSaleMenuPageVo = new AmcSaleMenuPageVo();
        List<Object> resultList = new ArrayList<>();


        AmcSaleFilter amcSaleFilter =  amcSalePageModVo.getAmcSaleFilter();

        if(amcSaleFilter.getFilterAsset() != null){
            // get filtered assets
            List<AmcAssetVo> amcAssetVos = getFilterAssets(amcSaleFilter.getFilterAsset());
            if(!CollectionUtils.isEmpty(amcAssetVos)){
                resultList.addAll(amcAssetVos);
            }
        }
        if(amcSaleFilter.getFilterDebt() != null){
            // get filtered debts
            List<AmcDebtVo> amcDebtVos = getFilterDebts(amcSaleFilter.getFilterDebt());
            resultList.addAll(amcDebtVos);
        }

        amcSaleMenuPageVo.setAmcSaleFilter(amcSaleFilter);
        amcSaleMenuPageVo.setResultList(resultList);
        return amcSaleMenuPageVo;
    }

    @Override
    public AmcSaleWatchonPageVo getUserWatchonPage(List<WXUserWatchObject> wxUserWatchObjects)
        throws Exception {
        AmcSaleWatchonPageVo amcSaleWatchonPageVo = new AmcSaleWatchonPageVo();
        List<Object> resultList = new ArrayList<>();
        List<Long> debtIds = new ArrayList<>();
        List<Long> assetIds = new ArrayList<>();
        for(WXUserWatchObject wxUserWatchObject: wxUserWatchObjects){
           if(wxUserWatchObject.getType().equals(AmcDebtAssetTypeEnum.AMC_ASSET.getId())){
               assetIds.add(wxUserWatchObject.getObjectId());
               continue;
           }
           if(wxUserWatchObject.getType().equals(AmcDebtAssetTypeEnum.AMC_DEBT.getId())){
               debtIds.add(wxUserWatchObject.getObjectId());
               continue;
           }
        }
        if(!CollectionUtils.isEmpty(assetIds)){
            amcSaleWatchonPageVo.setAssetList(new ArrayList<>());
            amcSaleWatchonPageVo.getAssetList().addAll(amcAssetService.getByIds(assetIds));
        }
        if(!CollectionUtils.isEmpty(debtIds)){
            amcSaleWatchonPageVo.setDebtLists(new ArrayList<>());
            amcSaleWatchonPageVo.getDebtLists().addAll(amcDebtService.getByIdsSimpleWithoutAddition(debtIds));
        }

        return amcSaleWatchonPageVo;
    }

    @Override
    public AmcSaleWatchonPageVo getUserWatchonPage(String openId) throws Exception {
        List<WXUserWatchOnObj> wxUserWatchOnObjs = wechatGrpcService.getWXUserWatchedOn(openId);
        AmcSaleWatchonPageVo amcSaleWatchonPageVo = new AmcSaleWatchonPageVo();
        List<Long> debtIds = new ArrayList<>();
        List<Long> assetIds = new ArrayList<>();
        for(WXUserWatchOnObj wxUserWatchObject: wxUserWatchOnObjs){
            if(wxUserWatchObject.getType() == AmcDebtAssetTypeEnum.AMC_ASSET.getId()){
                assetIds.add(wxUserWatchObject.getObjectId());
                continue;
            }
            if(wxUserWatchObject.getType() == (AmcDebtAssetTypeEnum.AMC_DEBT.getId())){
                debtIds.add(wxUserWatchObject.getObjectId());
                continue;
            }
        }
        if(!CollectionUtils.isEmpty(assetIds)){
            amcSaleWatchonPageVo.setAssetList(new ArrayList<>());
            amcSaleWatchonPageVo.getAssetList().addAll(amcAssetService.getByIds(assetIds));
        }
        if(!CollectionUtils.isEmpty(debtIds)){
            amcSaleWatchonPageVo.setDebtLists(new ArrayList<>());
            amcSaleWatchonPageVo.getDebtLists().addAll(amcDebtService.getByIdsSimpleWithoutAddition(debtIds));
        }

        return amcSaleWatchonPageVo;

    }

    @Override
    public AmcSaleWatchonPageVo getUserFavorPage(String openId) throws Exception {
        AmcSaleWatchonPageVo amcSaleWatchonPageVo = new AmcSaleWatchonPageVo();

        AmcSaleFilter amcSaleFilter = wechatGrpcService.getWXUserFavor(openId);
        if(!CollectionUtils.isEmpty(amcSaleFilter.getFilterAsset().getValuation())){
            log.info("ignore price section for user");
            amcSaleFilter.getFilterAsset().setValuation(null);
        }
        List<AmcAssetVo> amcAssetVos = getFilterAssets(amcSaleFilter.getFilterAsset());

        if(!CollectionUtils.isEmpty(amcAssetVos)){
            amcSaleWatchonPageVo.setAssetList(new ArrayList<>());
            amcSaleWatchonPageVo.getAssetList().addAll(amcAssetVos);
        }
        List<AmcDebtVo> amcDebVos = getFilterDebts(amcSaleFilter.getFilterDebt());
        if(!CollectionUtils.isEmpty(amcDebVos)){
            amcSaleWatchonPageVo.setDebtLists(new ArrayList<>());
            amcSaleWatchonPageVo.getDebtLists().addAll(amcDebVos);
        }

        return amcSaleWatchonPageVo;
    }

    @Override
    public AmcSaleBannerPageVo getBannerPage(Long bannerId) throws Exception {
        AmcSaleBannerPageVo amcSaleBannerPageVo = new AmcSaleBannerPageVo();
        AmcSaleBanner amcSaleBanner =  amcSaleBannerMapper.selectByPrimaryKey(bannerId);
        List<Object> resultList = new ArrayList<>();
        if(amcSaleBanner == null){
            return amcSaleBannerPageVo;
        }
        if(amcSaleBanner.getFilterContent() != null && !StringUtils.isEmpty(amcSaleBanner.getFilterContent())){

            AmcSaleFilter amcSaleFilter =  gson.fromJson(amcSaleBanner.getFilterContent(), AmcSaleFilter.class);
            if(amcSaleFilter.getFilterAsset() != null){
                // get filtered assets
                List<AmcAssetVo> amcAssetVos = getFilterAssets(amcSaleFilter.getFilterAsset());
                if(!CollectionUtils.isEmpty(amcAssetVos)){
                    resultList.addAll(amcAssetVos);
                }
            }
            if(amcSaleFilter.getFilterDebt() != null){
                // get filtered debts
                List<AmcDebtVo> amcDebtVos = getFilterDebts(amcSaleFilter.getFilterDebt());
                resultList.addAll(amcDebtVos);
            }
            amcSaleBannerPageVo.setAmcSaleFilter(amcSaleFilter);

        }
        amcSaleBannerPageVo.setResultList(resultList);
        return amcSaleBannerPageVo;
    }

    @Override
    public AmcSaleBannerPageVo getBannerPageWithFilter(AmcSalePageModVo amcSalePageModVo)
        throws Exception {
        AmcSaleBannerPageVo amcSaleBannerPageVo = new AmcSaleBannerPageVo();


        AmcSaleFilter amcSaleFilter =  amcSalePageModVo.getAmcSaleFilter();
        List<Object> resultList = new ArrayList<>();
        if(amcSaleFilter.getFilterAsset() != null){
            // get filtered assets
            List<AmcAssetVo> amcAssetVos = getFilterAssets(amcSaleFilter.getFilterAsset());
            if(!CollectionUtils.isEmpty(amcAssetVos)){
                resultList.addAll(amcAssetVos);
            }
        }
        if(amcSaleFilter.getFilterDebt() != null){
            // get filtered debts
            List<AmcDebtVo> amcDebtVos = getFilterDebts(amcSaleFilter.getFilterDebt());
            resultList.addAll(amcDebtVos);
        }
        amcSaleBannerPageVo.setAmcSaleFilter(amcSaleFilter);

        amcSaleBannerPageVo.setResultList(resultList);
        return amcSaleBannerPageVo;
    }



    @Override
    public AmcSaleMenuVo updateSaleMenuVo(AmcSaleMenuVo amcSaleMenuVo) throws Exception {
        boolean hasFilter = false;
        if(amcSaleMenuVo.getAmcSaleFilter().getFilterAsset() != null){
            if( !checkFilterContentAsset(amcSaleMenuVo.getAmcSaleFilter().getFilterAsset())){
                throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_MENU_FILTER_ERROR, gson.toJson(amcSaleMenuVo.getAmcSaleFilter()));
            }
            hasFilter = true;
        }
        if(amcSaleMenuVo.getAmcSaleFilter().getFilterDebt() != null){
            if( !checkFilterContentDebt(amcSaleMenuVo.getAmcSaleFilter().getFilterDebt())){
                throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_MENU_FILTER_ERROR, gson.toJson(amcSaleMenuVo.getAmcSaleFilter()));
            }
            hasFilter = true;
        }
        if(hasFilter){
            amcSaleMenuVo.setFilterContent(gson.toJson(amcSaleMenuVo.getAmcSaleFilter()));
        }
        amcSaleMenuMapper.updateByPrimaryKeySelective(amcSaleMenuVo);
        return amcSaleMenuVo;
    }

    @Override
    public AmcSaleBannerVo updateSaleBannerWithFilter(AmcSaleBannerVo amcSaleBannerVo)
        throws Exception {
        if(amcSaleBannerVo.getAmcSaleFilter() != null){
            if(amcSaleBannerVo.getAmcSaleFilter().getFilterDebt() != null){
                if(!checkFilterContentDebt(amcSaleBannerVo.getAmcSaleFilter().getFilterDebt())){
                    throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_BANNER_FILTER_ERROR, gson.toJson(amcSaleBannerVo.getAmcSaleFilter()));
                }
            }
            if(amcSaleBannerVo.getAmcSaleFilter().getFilterAsset() != null){
                if(!checkFilterContentAsset(amcSaleBannerVo.getAmcSaleFilter().getFilterAsset())){
                    throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_BANNER_FILTER_ERROR, gson.toJson(amcSaleBannerVo.getAmcSaleFilter()));
                }
            }
        }
        amcSaleBannerVo.setFilterContent(gson.toJson(amcSaleBannerVo.getAmcSaleFilter()));
        amcSaleBannerMapper.updateByPrimaryKeySelective(amcSaleBannerVo);
        return amcSaleBannerVo;
    }

    @Override
    public String getBase64Code(String imgUrl) throws Exception {
        String tempFilePath = amcOssFileService.downloadFileFromUrl(imgUrl, null, null );
        return amcOssFileService.img2Base64(tempFilePath);

    }



    private List<AmcDebtVo> getFilterDebts(AmcFilterContentDebt filterDebt) throws Exception {
        List<AmcDebtVo> amcDebtVos = amcDebtService.getFloorFilteredDebt(filterDebt);
        return amcDebtVos;
    }

    private List<AmcAssetVo> getFilterAssets(AmcFilterContentAsset filterAsset) throws Exception {
        List<AmcAssetVo> amcAssetVos = amcAssetService.getFloorFilteredAsset(filterAsset);
        return amcAssetVos;
    }


}
