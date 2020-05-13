package com.wensheng.zcc.amc.service;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.*;

import com.wensheng.zcc.amc.module.vo.AmcSaleBannerPageVo;
import com.wensheng.zcc.amc.module.vo.AmcSaleBannerVo;
import com.wensheng.zcc.amc.module.vo.AmcSaleFilter;
import com.wensheng.zcc.amc.module.vo.AmcSaleFloorFrontEndVo;
import com.wensheng.zcc.amc.module.vo.AmcSalePageModVo;
import com.wensheng.zcc.amc.module.vo.AmcSaleFloorPageVo;
import com.wensheng.zcc.amc.module.vo.AmcSaleFloorVo;
import com.wensheng.zcc.amc.module.vo.AmcSaleHomePage;
import com.wensheng.zcc.amc.module.vo.AmcSaleMenuPageVo;
import com.wensheng.zcc.amc.module.vo.AmcSaleMenuVo;
import java.util.List;

/**
 * @author chenwei on 1/4/19
 * @project zcc-backend
 */
public interface AmcSaleService {
    public List<AmcSaleFloorVo> getFloors();
    public List<AmcSaleFloorFrontEndVo> getFrontEndFloors() throws Exception;
    public boolean updateFloor(AmcSaleFloorVo amcSaleFloorVo) throws Exception;
    public boolean updateFloorSeq(List<Long> floorIds) throws Exception;
    public void delFloor(Long floorId) throws Exception;
    public List<AmcSaleTag> getTags();


    boolean updateFloorAllFilter(Long floorId, AmcSaleFilter amcSaleFloorFilter, AmcSaleFilter recommItems);

    boolean updateFloorFilterRecom(Long floorId, AmcSaleFilter recommItems);

    boolean updateFloorFilter(Long floorId, AmcSaleFilter amcSaleFilter);

    boolean updateFloorBasic(AmcSaleFloor amcSaleFloor);

    AmcSaleFloorVo createFloor(AmcSaleFloorVo amcSaleFloorVo);

    List<AmcSaleMenuVo> getSaleMenus();

    AmcSaleMenu updateSaleMenu(AmcSaleMenu amcSaleMenu);

    boolean updateSaleMenuSeq(List<Long> menuIds);

    AmcSaleMenu addSaleMenu(AmcSaleMenu amcSaleMenu);

    boolean delSaleMenu(Long saleMenuId);

    List<AmcSaleBannerVo> getSaleBanners();

    AmcSaleBanner updateSaleBanner(AmcSaleBanner amcSaleBanner);

    boolean updateBannerSeq(List<Long> bannerIds);

    AmcSaleBanner addSaleBanner(AmcSaleBanner amcSaleBanner);

    boolean delSaleBanner(Long amcSaleBannerId);

    String getSaleMenuPrepath(Long saleMenuId);

    String getSaleBannerPrepath(Long saleMenuId);


    AmcSaleMenu updateSaleMenuImage(Long saleMenuId, String ossPath);

    AmcSaleBanner updateSaleBannerImage(Long saleBannerId, String ossPath);

    AmcSaleHomePage getAmcSaleHome() throws Exception;

    AmcSaleFloorPageVo getFloorPage(Long floorId) throws Exception;

    AmcSaleMenuPageVo getMenuPage(Long menuId) throws Exception;

    AmcSaleBannerPageVo getBannerPage(Long bannerId) throws Exception;

  AmcSaleMenuVo updateSaleMenuVo(AmcSaleMenuVo amcSaleMenuVo) throws Exception;

    AmcSaleBannerVo updateSaleBannerWithFilter(AmcSaleBannerVo amcSaleBannerVo) throws Exception;

  String getBase64Code(String imgUrl) throws Exception;

  AmcSaleFloorPageVo getFloorPageWithFilter(AmcSalePageModVo amcSaleFloorPageModVo)
      throws Exception;

    AmcSaleBannerPageVo getBannerPageWithFilter(AmcSalePageModVo amcSalePageModVo) throws Exception;

    AmcSaleMenuPageVo getMenuPageWithFilter(AmcSalePageModVo amcSalePageModVo) throws Exception;
}
