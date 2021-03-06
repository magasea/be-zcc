package com.wensheng.zcc.amc.service;

import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetAdditional;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetDocument;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetImage;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAsset;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleFloor;
import com.wensheng.zcc.amc.module.dto.grpc.WXUserRegionFavor;
import com.wensheng.zcc.amc.module.vo.AmcAssetDetailVo;
import com.wensheng.zcc.amc.module.vo.AmcAssetGeoNear;
import com.wensheng.zcc.amc.module.vo.AmcAssetVo;
import com.wensheng.zcc.amc.module.vo.AmcSaleGetListInPage;
import com.wensheng.zcc.amc.module.vo.AmcSaleGetRandomListInPage;
import com.wensheng.zcc.common.module.dto.AmcFilterContentAsset;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

/**
 * @author chenwei on 12/5/18
 * @project zcc-backend
 */
public interface AmcAssetService {

    public AmcAssetVo create(AmcAsset amcAsset) throws Exception;

    public int delAsset(Long amcAssetId) throws Exception;

    public int del(Long amcDebtId);

    public void updateByDebtState(Long debtId, Integer publishState);

    void updateContactor4Assets(Long id, String amcContactorName, String amcContactorPhone);

    public AmcAssetVo update(AmcAsset amcAsset) throws Exception;

    public AssetAdditional createOrUpdateAssetAddition(AssetAdditional additional);

    public List<AmcAssetVo> queryAll(int offset, int size);

    public AmcAssetVo get(Long amcAssetId);
  public List<AmcAssetVo> getByIds(List<Long> amcAssetIds) throws Exception;

    public List<AmcAssetVo> query(AmcAsset queryCond, int offset, int size);

    public AmcAssetDetailVo queryAssetDetail(Long assetId) throws Exception;

    public List<AmcAssetDetailVo> queryAssetDetails(Long amcDebtId) throws Exception;


    public List<AmcAssetVo> queryAssetPage( int offset, int pageSize, Map<String, Object> queryParam, Map<String,
        Direction> orderByParam) throws Exception;

    Long getAssetCount(Map<String, Object> queryParam) throws Exception;


   List<AmcAsset> getSimpleAssets(List<Long> ids);

   List<AmcAsset> getAssetByDebtAndAssetTitle(String debtTitle, String assetTitle) throws Exception;

  List<AmcAssetVo> getAssetByTitleLike(String assetTitle) throws Exception;

    String getAssetOssPrepath(Long assetId);

  AssetImage saveImageInfo( AssetImage assetImage);


  AssetDocument saveDoc(AssetDocument assetDocument);

  void delImage(AssetImage assetImage);

  Long getAssetCountWithDebtIds(List<Long> amcDebtIds);

    void setRecomm(List<Long> assetIds, int isRecomm);

  List<AmcAssetVo> getAssetsByIds(List<Long> assetIds) throws Exception;

  List<AmcAssetVo> getLatestNumOfAssets(int num) throws Exception;

  Map<Long, List<String>> uploadAmcAssetsImage2WechatByIds(List<Long> assetIds);

  Map<Long, Map<Long, List<AssetImage>>> getAssetImgsByDebtIds(List<Long> debtIds);

  Map<Long, List<AmcAsset>> getSimpleAssetsByDebtId(List<Long> debtIds);

  public void checkGeoInfoWorker();

  Map<Long, AssetAdditional> getAssetAdditions(Long amcDebtId);
  Map<Long, AssetImage> getAssetImages(Long amcDebtId);

  List<AmcAssetGeoNear> queryByGeopoint(GeoJsonPoint geoJsonPoint) throws Exception;

  List<AmcAssetVo> queryByGeopointWithLimitCount(GeoJsonPoint geoJsonPoint, int limit) throws Exception;

  void patchAssetLocationWithCode() throws Exception;
  AssetImage uploadAssetImage(String imagePath, String ossPrepath, Integer tag, Long assetId, String desc) throws Exception;


    void patchRecomm();

  List<AmcAssetVo> getFloorFilteredAsset(AmcFilterContentAsset filterAsset) throws Exception;

  List<AmcAssetVo> getFloorFilteredAsset(AmcFilterContentAsset filterAsset, AmcSaleGetListInPage pageInfo)
      throws Exception;

  List<Long> getLatestIds();

  List<AmcAssetVo> getFloorFilteredRandomAssets(AmcFilterContentAsset filterAsset, AmcSaleGetRandomListInPage pageInfo)
      throws Exception;

  List<AmcAssetVo> getUserLocalAssets(WXUserRegionFavor wxUserRegionFavor,
      AmcSaleFloor amcSaleFloor) throws Exception;
}
