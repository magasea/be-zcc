package com.wensheng.zcc.amc.service;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.*;

import com.wensheng.zcc.amc.module.vo.AmcSaleFilter;
import com.wensheng.zcc.amc.module.vo.AmcSaleFloorFrontEndVo;
import com.wensheng.zcc.amc.module.vo.AmcSaleFloorVo;
import com.wensheng.zcc.amc.module.vo.AmcSaleHomePage;
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

    List<AmcSaleMenu> getSaleMenus();

    AmcSaleMenu updateSaleMenu(AmcSaleMenu amcSaleMenu);

    boolean updateSaleMenuSeq(List<Long> menuIds);

    AmcSaleMenu addSaleMenu(AmcSaleMenu amcSaleMenu);

    boolean delSaleMenu(Long saleMenuId);

    List<AmcSaleBanner> getSaleBanners();

    AmcSaleBanner updateSaleBanner(AmcSaleBanner amcSaleBanner);

    boolean updateBannerSeq(List<Long> bannerIds);

    AmcSaleBanner addSaleBanner(AmcSaleBanner amcSaleBanner);

    boolean delSaleBanner(Long amcSaleBannerId);

    String getSaleMenuPrepath(Long saleMenuId);

    String getSaleBannerPrepath(Long saleMenuId);


    AmcSaleMenu updateSaleMenuImage(Long saleMenuId, String ossPath);

    AmcSaleBanner updateSaleBannerImage(Long saleBannerId, String ossPath);

    AmcSaleHomePage getAmcSaleHome() throws Exception;

    List<Object> getFloorPage(Long floorId) throws Exception;
}
