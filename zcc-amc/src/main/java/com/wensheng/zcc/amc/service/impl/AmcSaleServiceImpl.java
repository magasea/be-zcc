package com.wensheng.zcc.amc.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcSaleBannerMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcSaleFloorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcSaleMenuMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcSaleTagAssetMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcSaleTagDebtMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcSaleTagMapper;
import com.wensheng.zcc.amc.module.dao.helper.FloorPublishStateEnum;
import com.wensheng.zcc.amc.module.dao.helper.ImagePathClassEnum;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAssetExample;
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
import com.wensheng.zcc.amc.module.vo.AmcFilterContentAsset;
import com.wensheng.zcc.amc.module.vo.AmcFilterContentDebt;
import com.wensheng.zcc.amc.module.vo.AmcFilterContentItem;
import com.wensheng.zcc.amc.module.vo.AmcSaleFilter;
import com.wensheng.zcc.amc.module.vo.AmcSaleFloorFrontEndVo;
import com.wensheng.zcc.amc.module.vo.AmcSaleFloorVo;
import com.wensheng.zcc.amc.module.vo.AmcSaleHomePage;
import com.wensheng.zcc.amc.module.vo.AmcSaleRecomItems;
import com.wensheng.zcc.amc.service.AmcAssetService;
import com.wensheng.zcc.amc.service.AmcDebtService;
import com.wensheng.zcc.amc.service.AmcSaleService;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
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
    AmcSaleTagDebtMapper amcSaleTagDebtMapper;

    @Autowired
    AmcSaleTagAssetMapper amcSaleTagAssetMapper;

    @Autowired
    AmcSaleMenuMapper amcSaleMenuMapper;

    @Autowired
    AmcSaleBannerMapper amcSaleBannerMapper;

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
        amcSaleFloorExample.createCriteria().andPublishStateEqualTo(FloorPublishStateEnum.PUBLISHED.getStatus());
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
                    amcSaleFloorFrontEndVo.setAmcDebtVos(amcDebtService.queryByIds(amcSaleRecomItems.getAmcSaleRecommDebts().getDebtIds()));
                }
                if(amcSaleRecomItems.getAmcSaleRecommAssets() != null &&
                    !CollectionUtils.isEmpty(amcSaleRecomItems.getAmcSaleRecommAssets().getAssetIds())){
                    amcSaleFloorFrontEndVo.setAmcAssetVos(
                        amcAssetService.getAssetsByIds(amcSaleRecomItems.getAmcSaleRecommAssets().getAssetIds()));

                }
                amcSaleFloorFrontEndVos.add(amcSaleFloorFrontEndVo);


            }

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
                throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_JSON_CONTENT_ERROR,
                    gson.toJson(amcSaleFloorVo.getAmcSaleFilter().getFilterDebt()));
            }

            if(amcSaleFloorVo.getAmcSaleFilter().getFilterAsset() != null &&
                !checkFilterContentAsset(amcSaleFloorVo.getAmcSaleFilter().getFilterAsset())){
                throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_JSON_CONTENT_ERROR,
                    gson.toJson(amcSaleFloorVo.getAmcSaleFilter().getFilterDebt()));
            }
            amcSaleFloor.setFilterContent(gson.toJson(amcSaleFloorVo.getAmcSaleFilter()));
        }
        amcSaleFloorVo.setAmcSaleFloor(amcSaleFloor);

    }

    private boolean checkFilterContentAsset(AmcFilterContentAsset filterAsset) {
        return true;
    }

    private boolean checkFilterContentDebt(AmcFilterContentDebt filterDebt) {
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
    public List<AmcSaleMenu> getSaleMenus() {
        AmcSaleMenuExample amcSaleMenuExample = new AmcSaleMenuExample();
        amcSaleMenuExample.setOrderByClause(" id desc ");
        return amcSaleMenuMapper.selectByExample(amcSaleMenuExample);
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
    public boolean addSaleMenu(AmcSaleMenu amcSaleMenu) {
        amcSaleMenuMapper.insertSelective(amcSaleMenu);
        return true;
    }

    @Override
    public boolean delSaleMenu(Long saleMenuId) {
        amcSaleMenuMapper.deleteByPrimaryKey(saleMenuId);
        return true;
    }

    @Override
    public List<AmcSaleBanner> getSaleBanners() {
        AmcSaleBannerExample amcSaleBannerExample = new AmcSaleBannerExample();
        amcSaleBannerExample.setOrderByClause(" id desc ");

        return amcSaleBannerMapper.selectByExample(amcSaleBannerExample);
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
        amcSaleHomePage.setAmcSaleBannerList(getSaleBanners());
        amcSaleHomePage.setAmcSaleMenuList(getSaleMenus());

        return amcSaleHomePage;
    }

    @Override
    public List<Object> getFloorPage(Long floorId) throws Exception {
        AmcSaleFloor amcSaleFloor =  amcSaleFloorMapper.selectByPrimaryKey(floorId);
        List<Object> resultList = new ArrayList<>();
        if(amcSaleFloor == null){
            return resultList;
        }
        if(amcSaleFloor.getFilterContent() != null && !StringUtils.isEmpty(amcSaleFloor.getFilterContent())){

            AmcSaleFilter amcSaleFilter =  gson.fromJson(amcSaleFloor.getFilterContent(), AmcSaleFilter.class);
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



        return resultList;
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
