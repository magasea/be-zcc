package com.wensheng.zcc.amc.service.impl;

import com.google.common.collect.Lists;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.CurtInfoMapper;
import com.wensheng.zcc.amc.module.dao.helper.*;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetAdditional;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.*;
import com.wensheng.zcc.amc.module.vo.AmcAssetVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtVo;
import com.wensheng.zcc.amc.service.*;
import com.wensheng.zcc.common.module.dto.Region;
import com.wensheng.zcc.common.params.AmcCmpyEnum;
import com.wensheng.zcc.common.params.AmcPage;
import com.wensheng.zcc.common.params.PageInfo;
import com.wensheng.zcc.common.params.sso.AmcLocationEnum;
import com.wensheng.zcc.common.params.sso.SSOAmcUser;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.common.utils.AmcNumberUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.sso.SSOQueryParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AmcExcelFileServiceImpl implements AmcExcelFileService {

    @Autowired
    AmcContactorService amcContactorService;

    static final String strDebtTitle = "债权名称";
    static final String strBrower = "借款人";
    static final String strDebtBaseAmount = "债权本金(元)";
    static final String strDebtTotalAmount = "债权本息(元)";
    static final String strLawState = "诉讼状态";
    static final String strGrantType = "担保方式";
    static final String strGrantor = "担保人";
    static final String strCourtProv = "法院所属省";
    static final String strCourtCity = "法院所属市";
    static final String strCourtCounty = "法院所属区";
    static final String strCourt = "管辖法院";
    static final String strAmcContactor = "联系人";
    static final String strDesc = "项目亮点";
    //资产
    static final String strAssetOwner = "所有权人";
    static final String strAssetName = "资产名称";
//    static final String strAssetNature = "资产性质";
    static final String strAssetType = "资产类型";
    static final String strAssetLawSealState = "查封状态";
    static final String strAssetProv = "资产所在地省";
    static final String strAssetCity = "资产所在地市";
    static final String strAssetCounty = "资产所在地区";
    static final String strAssetAddr = "地址";
    static final String strBuildingArea = "建筑物面积(平米)";
    static final String strLandArea = "土地面积(平米)";
    static final String strLandUsage = "土地用途";
    static final String strLandSupplyType = "供地方式";
    static final String strWarrantInfo = "权证信息";
    static final String strNote = "备注";

    static final String SEP_CHAR = ",";
    static final String KEY_WORD_CMPY = "公司";

    @Value("${project.params.debt_image_path}")
    String debtImageRepo;

    @Value("${project.params.asset_image_path}")
    String assetImageRepo;

    private DataFormatter dataFormatter = new DataFormatter();

    @Autowired
    AmcDebtService amcDebtService;

    @Autowired
    AmcDebtpackService amcDebtpackService;

    @Autowired
    CurtInfoMapper curtInfoMapper;

    @Autowired
    AmcDebtorMapper amcDebtorMapper;

    @Autowired
    RegionService regionService;

    @Autowired
    AmcAssetService amcAssetService;

    private Map<Long, List<AmcAsset>> assetDebtIdHistoryMap = new ConcurrentHashMap<>();

    private Map<Long, Set<String>> assetTitleDebtIdHistorhSet = new ConcurrentHashMap<>();



    @Override
    public String handleMultiPartFile(MultipartFile multipartFile) throws Exception {
        File targetFile = null;
        targetFile =
                new File(debtImageRepo+File.separator  +multipartFile.getOriginalFilename());


        multipartFile.transferTo(targetFile);

// Creating a Workbook from an Excel file (.xls or .xlsx)
        Workbook workbook = WorkbookFactory.create(targetFile);

        // Retrieving the number of sheets in the Workbook
        System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

     /*
           ==================================================================
           Iterating over all the rows and columns in a Sheet (Multiple ways)
           ==================================================================
        */

        // Getting the Sheet at index zero
        Sheet sheetDebt = workbook.getSheetAt(0);
        Map<String, AmcDebtVo> debtMap = handleDebtFromExcel(sheetDebt);
        Sheet sheetAsset = workbook.getSheetAt(1);
        handleAssetFromExcel(sheetAsset, debtMap);



        return targetFile.getCanonicalPath();
    }

    private void handleAssetFromExcel(Sheet sheetAsset, Map<String, AmcDebtVo> debtMap) throws Exception {

        Map<Long, Map<String, AmcAsset>> historyAssetTitleDebtId = getHistoryAssetTitleDebtIdMap(debtMap.values().stream().map(item->item.getId()).collect(Collectors.toUnmodifiableList()));
        Set<String> assetTitles = new HashSet<>();

        Iterator<Row> rowIterator = sheetAsset.rowIterator();
        List<AmcAsset> amcAssets = new ArrayList<>();
        int idxDebtTitle = -1;

        //资产
        int idxAssetName = -1;
        int idxAssetOwner = -1;
//        int idxAssetNature = -1;
        int idxAssetType = -1;
        int idxAssetLawSealState = -1;
        int idxAssetProv = -1;
        int idxAssetCity = -1;
        int idxAssetCounty = -1;
        int idxAssetAddr = -1;
        int idxBuildingArea = -1;
        int idxLandArea = -1;
        int idxLandUsage = -1;
        int idxLandSupplyType = -1;
        int idxWarrantInfo = -1;
        int idxNote = -1;

        boolean hasGotHeader = false;
        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            if (row.getLastCellNum() < 5) {
                // it is header rows
                continue;
            }

            if (!hasGotHeader) {
                log.info("now got first row, it is title row");
                // Now let's iterate over the columns of the current row
                Iterator<Cell> cellIterator = row.cellIterator();

                int idxOfCell = -1;
                while (cellIterator.hasNext()) {

                    Cell cell = cellIterator.next();
                    idxOfCell++;
                    String cellValue = dataFormatter.formatCellValue(cell);
                    System.out.print(cellValue + "\t");
                    switch (cellValue) {
                        case strDebtTitle:
                            idxDebtTitle = idxOfCell;
                            break;
                        case strAssetOwner:
                            idxAssetOwner = idxOfCell;
                            break;
                        case strAssetAddr:
                            idxAssetAddr = idxOfCell;
                            break;
                        case strAssetCity:
                            idxAssetCity = idxOfCell;
                            break;
                        case strAssetCounty:
                            idxAssetCounty = idxOfCell;
                            break;
                        case strAssetLawSealState:
                            idxAssetLawSealState = idxOfCell;
                            break;
                        case strAssetName:
                            idxAssetName = idxOfCell;
                            break;
//                        case strAssetNature:
//                            idxAssetNature = idxOfCell;
//                            break;
                        case strAssetProv:
                            idxAssetProv = idxOfCell;
                            break;
                        case strAssetType:
                            idxAssetType = idxOfCell;
                            break;
                        case strBuildingArea:
                            idxBuildingArea = idxOfCell;

                        case strLandArea:
                            idxLandArea = idxOfCell;
                            break;
                        case strLandSupplyType:
                            idxLandSupplyType = idxOfCell;
                            break;
                        case strLandUsage:
                            idxLandUsage = idxOfCell;
                            break;
                        case strWarrantInfo:
                            idxWarrantInfo = idxOfCell;
                        case strNote:
                            idxNote = idxOfCell;
                            break;
                        default:
                            throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.INVALID_EXCEL_HEADER_ERROR, cellValue);

                    }
                }

                // now check if there is missing headers
                if (((idxDebtTitle + 1) * (idxAssetName + 1)  * (idxAssetType + 1) *
                        (idxAssetLawSealState + 1) * (idxAssetProv + 1) * (idxAssetCity + 1) * (idxAssetCounty + 1) * (idxAssetAddr + 1) * (idxBuildingArea + 1) *
                        (idxLandArea + 1) * (idxLandUsage + 1) * (idxLandSupplyType + 1) * (idxWarrantInfo + 1)) == 0) {
                    String missingHeader = null;
                    if (idxDebtTitle == -1) {
                        missingHeader = strDebtTitle;
                    }
                    if(idxAssetOwner == -1){
                        missingHeader = strAssetOwner;
                    }
                    if (idxAssetName == -1) {
                        missingHeader = strAssetName;
                    }
//                    if (idxAssetNature == -1) {
//                        missingHeader = strAssetNature;
//                    }
                    if (idxAssetType == -1) {
                        missingHeader = strAssetType;
                    }
                    if (idxAssetLawSealState == -1) {
                        missingHeader = strAssetLawSealState;
                    }
                    if (idxAssetProv == -1) {
                        missingHeader = strAssetProv;
                    }
                    if (idxAssetCity == -1) {
                        missingHeader = strAssetCity;
                    }
                    if (idxAssetCounty == -1) {
                        missingHeader = strAssetCounty;
                    }
                    if (idxAssetAddr == -1) {
                        missingHeader = strAssetAddr;
                    }
                    if (idxBuildingArea == -1) {
                        missingHeader = strBuildingArea;
                    }
                    if (idxLandArea == -1) {
                        missingHeader = strLandArea;
                    }
                    if (idxLandUsage == -1) {
                        missingHeader = strLandUsage;
                    }
                    if (idxLandSupplyType == -1) {
                        missingHeader = strLandSupplyType;
                    }
                    if (idxWarrantInfo == -1) {
                        missingHeader = strWarrantInfo;
                    }
                    if(idxNote == -1){
                        missingHeader = strNote;
                    }
                    throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.MISSING_EXCEL_HEADER_ERROR, missingHeader);
                }else{
                    hasGotHeader = true;
                }
            } else {
                // can generate debt and asset
                String cellDebtTitle = dataFormatter.formatCellValue(row.getCell(idxDebtTitle));
                String cellAssetOwner = dataFormatter.formatCellValue(row.getCell(idxAssetOwner));
                String cellAssetName = dataFormatter.formatCellValue(row.getCell(idxAssetName));
//                String cellAssetNature = dataFormatter.formatCellValue(row.getCell(idxAssetNature));
                String cellAssetType = dataFormatter.formatCellValue(row.getCell(idxAssetType));
                String cellAssetLawSealState = dataFormatter.formatCellValue(row.getCell(idxAssetLawSealState));
                String cellAssetProv = dataFormatter.formatCellValue(row.getCell(idxAssetProv));
                String cellAssetCity = dataFormatter.formatCellValue(row.getCell(idxAssetCity));
                String cellAssetCounty = dataFormatter.formatCellValue(row.getCell(idxAssetCounty));
                String cellAssetAddr = dataFormatter.formatCellValue(row.getCell(idxAssetAddr));
                String cellBuildingArea = dataFormatter.formatCellValue(row.getCell(idxBuildingArea));
                String cellLandArea = dataFormatter.formatCellValue(row.getCell(idxLandArea));
                String cellLandUsage = dataFormatter.formatCellValue(row.getCell(idxLandUsage));
                String cellLandSupplyType = dataFormatter.formatCellValue(row.getCell(idxLandSupplyType));
                String cellWarrantInfo = dataFormatter.formatCellValue(row.getCell(idxWarrantInfo));
                String cellNote = dataFormatter.formatCellValue(row.getCell(idxNote));

                AmcAsset amcAsset = new AmcAsset();

                if(!debtMap.containsKey(cellDebtTitle)){
                    log.error("There is no debt with title:{}", cellDebtTitle);
                    throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.MISSING_EXCEL_CONTENT_ERROR, String.format("no such debtTitle:%s",cellDebtTitle));
                }else{
                    amcAsset.setDebtId(debtMap.get(cellDebtTitle).getId());
                }
                AssetAdditional assetAdditional = new AssetAdditional();
                if(!StringUtils.isEmpty(cellAssetOwner)){
                    assetAdditional.setOwner(cellAssetOwner);
                }
                if(!StringUtils.isEmpty(cellLandSupplyType)){
                    assetAdditional.setLandSupplyType(LandSupplyTypeEnum.lookupByDisplayNameUtil(cellLandSupplyType).getId());
                }
                if(!StringUtils.isEmpty(cellAssetName)){
                    amcAsset.setTitle(cellAssetName);
                }else{
                    throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.MISSING_EXCEL_CONTENT_ERROR, String.format("no asset name:%s", cellAssetName));
                }
                amcAsset.setAssetNature(AssetNatureEnum.SEAL.getType());
//                if(!StringUtils.isEmpty(cellAssetNature)){
//                    amcAsset.setAssetNature(AssetNatureEnum.lookupByDisplayNameUtil(cellAssetNature).getType());
//                }else{
//                    throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.MISSING_EXCEL_CONTENT_ERROR, String.format("no asset nature:%s", cellAssetNature));
//                }

                if(!StringUtils.isEmpty(cellAssetType)){
                    amcAsset.setType(AssetTypeEnum.lookupByDisplayNameUtil(cellAssetType).getType());
                }else{
                    throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.MISSING_EXCEL_CONTENT_ERROR, String.format("no asset type:%s", cellAssetType));
                }

                if(!StringUtils.isEmpty(cellAssetLawSealState)){
                    amcAsset.setSealedState(SealStateEnum.lookupByDisplayNameUtil(cellAssetLawSealState).getStatus());
                }
                boolean failed_get_county = true;
                if(!StringUtils.isEmpty(cellAssetCounty)){
                    List<Region> regions = regionService.getRegionByName(cellAssetCounty);
                    if(CollectionUtils.isEmpty(regions)){
                        log.error("{} {}",ExceptionUtils.AmcExceptions.INVALID_EXCEL_CONTENT_ERROR, String.format("cellAssetCounty:%s",cellAssetCounty));
//                        throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.INVALID_EXCEL_CONTENT_ERROR, String.format("cellAssetCounty:%s",cellAssetCounty));
                        failed_get_county = true;
                    }else if(regions.size() > 1){
                        for(Region region: regions){
                            Region parentRegion = regionService.getRegionById(region.getParentId());
                            if(parentRegion.getName().equals(cellAssetCity)){
                                amcAsset.setCounty(region.getId().toString());
                                amcAsset.setCity(parentRegion.getId().toString());
                                amcAsset.setProvince(parentRegion.getParentId().toString());
                                failed_get_county = false;
                            }
                        }
                    }
                }
                if(!StringUtils.isEmpty(cellAssetCity) && failed_get_county ){
                    List<Region> regions = regionService.getRegionByName(cellAssetCity);
                    if(CollectionUtils.isEmpty(regions)){
                        throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.INVALID_EXCEL_CONTENT_ERROR, String.format("cellAssetCity:%s",cellAssetCity));
                    }else if(regions.size() > 1){
                        for(Region region: regions){
                            Region parentRegion = regionService.getRegionById(region.getParentId());
                            if(parentRegion.getName().equals(cellAssetProv)){
                                amcAsset.setCity(region.getId().toString());
                                amcAsset.setProvince(region.getParentId().toString());
                            }
                        }
                    }
                }

                if(!StringUtils.isEmpty(cellAssetAddr)){
                    amcAsset.setAddress(cellAssetAddr);
                }
                if(!StringUtils.isEmpty(cellBuildingArea)){
                    amcAsset.setBuildingArea(AmcNumberUtils.getLongFromStringWithMult100(cellBuildingArea));
                }
                if(!StringUtils.isEmpty(cellLandArea)){
                    amcAsset.setLandArea(AmcNumberUtils.getLongFromStringWithMult100(cellLandArea));
                    amcAsset.setLandAreaUnit(AreaUnitEnum.SQUAREMETER.getType());
                }

                if(!StringUtils.isEmpty(cellLandUsage)){
                    assetAdditional.setLandUsageType(LandUsageTypeEnum.lookupByDisplayNameUtil(cellLandUsage).getId());
                }
                if(!StringUtils.isEmpty(cellWarrantInfo)){
                    assetAdditional.setWarrant(cellWarrantInfo);
                }
                if(!StringUtils.isEmpty(cellNote)){
                    assetAdditional.setDescription(cellNote);
//                    amcAsset.setNote(cellNote);
                }

                if(historyAssetTitleDebtId.containsKey(amcAsset.getDebtId())){
                    assetTitles =  historyAssetTitleDebtId.get((amcAsset.getDebtId())).keySet();

                }

                if(assetTitles.contains(amcAsset.getTitle())){
                    //update
                    AmcAsset amcAssetHistory = historyAssetTitleDebtId.get(amcAsset.getDebtId()).get(amcAsset.getTitle());
                    AmcBeanUtils.copyProperties(amcAsset, amcAssetHistory);

                    amcAssetService.update(amcAssetHistory);
                    amcAssetService.createOrUpdateAssetAddition(assetAdditional);

                }else{
                    //create
                    amcAssetService.create(amcAsset);
                    amcAssetService.createOrUpdateAssetAddition(assetAdditional);
                }






            }
        }

    }


    private Map<Long, Map<String, AmcAsset>> getHistoryAssetTitleDebtIdMap(List<Long> debtIds){
        Map<Long, List<AmcAsset>> amcAssetDebtIdMap = amcAssetService.getSimpleAssetsByDebtId(debtIds);
        Map<Long, Map<String, AmcAsset>> amcAssetTitlesDebtId = new HashMap<>();
        for(Map.Entry<Long, List<AmcAsset>> entry:  amcAssetDebtIdMap.entrySet()){
            if(!amcAssetTitlesDebtId.containsKey(entry.getKey())){
                Map<String, AmcAsset> amcAssetMap = new HashMap<>();
                for(AmcAsset amcAsset: entry.getValue()){
                    amcAssetMap.put(amcAsset.getTitle(), amcAsset);
                }

                amcAssetTitlesDebtId.put(entry.getKey(), amcAssetMap);
            }
            amcAssetTitlesDebtId.get(entry.getKey());
        }
        return amcAssetTitlesDebtId;
    }

    private Map<String, AmcDebtVo> handleDebtFromExcel(Sheet sheetDebt) throws Exception {

        Map<String, AmcDebtVo> debtMap = new HashMap<>();
        Map<String, Long> regionMap = new HashMap<>();
        // 1. You can obtain a rowIterator and columnIterator and iterate over them
        Iterator<Row> rowIterator = sheetDebt.rowIterator();
        List<AmcDebt> amcDebts = new ArrayList<>();
        int idxDebtTitle = -1;
        int idxBrowwer = -1;
        int idxDebtBaseAmount = -1;
        int idxDebtTotalAmount = -1;
        int idxLawState = -1;
        int idxGrantType = -1;
        int idxGrantor = -1;
        int idxCourtProv = -1;
        int idxCourtCity = -1;
        int idxCourtCounty = -1;
        int idxCourt = -1;
        int idxAmcContactor = -1;
        int idxDesc = -1;
        boolean hasGotHeader = false;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if(row.getLastCellNum() - row.getFirstCellNum() <= 8 ){
                if(!hasGotHeader){
                    // it is header rows
                    continue;
                }else{
                    break;
                }

            }
            if(!hasGotHeader){
                log.info("now got first row, it is title row");
                // Now let's iterate over the columns of the current row
                Iterator<Cell> cellIterator = row.cellIterator();
                int idxOfCell = -1;
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    idxOfCell++;
                    String cellValue = dataFormatter.formatCellValue(cell);
                    System.out.print(cellValue + "\t");
                    switch (cellValue) {
                        case strDebtTitle:
                            idxDebtTitle = idxOfCell;
                            break;
                        case strAmcContactor:
                            idxAmcContactor = idxOfCell;
                            break;
                        case strBrower:
                            idxBrowwer = idxOfCell;
                            break;
                        case strCourt:
                            idxCourt = idxOfCell;
                            break;
                        case strCourtCity:
                            idxCourtCity = idxOfCell;
                            break;
                        case strCourtCounty:
                            idxCourtCounty = idxOfCell;
                            break;
                        case strCourtProv:
                            idxCourtProv = idxOfCell;
                            break;
                        case strDebtBaseAmount:
                            idxDebtBaseAmount = idxOfCell;
                            break;
                        case strDebtTotalAmount:
                            idxDebtTotalAmount = idxOfCell;
                            break;
                        case strDesc:
                            idxDesc = idxOfCell;
                            break;
                        case strGrantor:
                            idxGrantor = idxOfCell;
                            break;
                        case strGrantType:
                            idxGrantType = idxOfCell;
                            break;
                        case strLawState:
                            idxLawState = idxOfCell;
                            break;
                        default:
                            throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.INVALID_EXCEL_HEADER_ERROR, cellValue);

                    }
                }
                // now check if there is missing headers
                if(((idxDebtTitle + 1) * (idxBrowwer + 1) * (idxDebtBaseAmount + 1) * (idxDebtTotalAmount + 1) * (idxLawState + 1) *
                        (idxGrantType + 1) * (idxGrantor + 1) * (idxCourtProv + 1) * (idxCourtCity + 1) *
                        (idxCourtCounty + 1) * (idxCourt + 1) * (idxAmcContactor + 1) * (idxDesc + 1) ) == 0){
                    String missingHeader = null;
                    if(idxDebtTitle == -1){
                        missingHeader = strDebtTitle;
                    }
                    if(idxBrowwer == -1){
                        missingHeader = strBrower;
                    }
                    if(idxDebtBaseAmount == -1){
                        missingHeader = strDebtBaseAmount;
                    }
                    if(idxDebtTotalAmount == -1){
                        missingHeader = strDebtBaseAmount;
                    }
                    if(idxLawState == -1){
                        missingHeader = strLawState;
                    }
                    if(idxGrantType == -1){
                        missingHeader = strGrantType;
                    }
                    if(idxGrantor == -1){
                        missingHeader = strGrantor;
                    }
                    if(idxCourtProv == -1){
                        missingHeader = strCourtProv;
                    }
                    if(idxCourtCity == -1){
                        missingHeader = strCourtCity;
                    }
                    if(idxCourtCounty == -1){
                        missingHeader = strCourtCounty;
                    }
                    if(idxCourt == -1){
                        missingHeader = strCourt;
                    }
                    if(idxAmcContactor == -1){
                        missingHeader = strAmcContactor;
                    }
                    if(idxDesc == -1){
                        missingHeader = strDesc;
                    }
                    throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.MISSING_EXCEL_HEADER_ERROR, missingHeader);
                }else{
                    hasGotHeader = true;
                }
            }else{
                // can generate debt and asset
                String cellDebtTitle = dataFormatter.formatCellValue(row.getCell(idxDebtTitle));
                String cellBrowwer = dataFormatter.formatCellValue(row.getCell(idxBrowwer));
                String cellDebtBaseAmount = dataFormatter.formatCellValue(row.getCell(idxDebtBaseAmount));
                String cellDebtTotalAmount = dataFormatter.formatCellValue(row.getCell(idxDebtTotalAmount));
                String cellLawState = dataFormatter.formatCellValue(row.getCell(idxLawState));
                String cellGrantType = dataFormatter.formatCellValue(row.getCell(idxGrantType));
                String cellGrantor = dataFormatter.formatCellValue(row.getCell(idxGrantor));
                String cellCourtProv = dataFormatter.formatCellValue(row.getCell(idxCourtProv));
                String cellCourtCity = dataFormatter.formatCellValue(row.getCell(idxCourtCity));
                String cellCourtCounty = dataFormatter.formatCellValue(row.getCell(idxCourtCounty));
                String cellCourt = dataFormatter.formatCellValue(row.getCell(idxCourt));
                String cellAmcContactor = dataFormatter.formatCellValue(row.getCell(idxAmcContactor));
                String cellDesc = dataFormatter.formatCellValue(row.getCell(idxDesc));
                AmcDebt amcDebt = new AmcDebt();
                amcDebt.setAmcId(Long.valueOf(AmcCmpyEnum.CMPY_WENSHENG.getId()));
                amcDebt.setPublishState(PublishStateEnum.DRAFT.getStatus());
                amcDebt.setAmcContactorName(cellAmcContactor);
                SSOAmcUser ssoAmcUser = getAmcContactorByName(cellAmcContactor);
                amcDebt.setAmcContactorPhone(ssoAmcUser.getMobilePhone());
                if(!StringUtils.isEmpty(cellDebtBaseAmount)){
                    amcDebt.setBaseAmount(AmcNumberUtils.getLongFromStringWithMult100(cellDebtBaseAmount));
                }
                if(!StringUtils.isEmpty(cellDebtTotalAmount)){
                    amcDebt.setTotalAmount(AmcNumberUtils.getLongFromStringWithMult100(cellDebtTotalAmount));
                }
                if(!StringUtils.isEmpty(cellLawState)){
                    amcDebt.setLawsuitState(LawstateEnum.lookupByDisplayNameUtil(cellLawState).getStatus());
                }
                if(!StringUtils.isEmpty(cellGrantType)){
                    amcDebt.setGuarantType(GuarantTypeEnum.lookupByDisplayNameUtil(cellGrantType).getType());
                }
                if(!StringUtils.isEmpty(cellDebtTitle)){
                    amcDebt.setTitle(cellDebtTitle);
                }else{
                    break;
                }

                List<ZccDebtpack> zccDebtpacks =  amcDebtpackService.queryPacksWithLocation(AmcLocationEnum.lookupByDisplayIdUtil(ssoAmcUser.getLocation()));
                if(CollectionUtils.isEmpty(zccDebtpacks)){
                    log.error("There is no zccDebtPack for ssoAmcUser with location:{}", ssoAmcUser.getLocation());
                }
                if(!StringUtils.isEmpty(cellCourt)){
                   Long curtId = getCourt(cellCourtProv, cellCourtCity, cellCourtCounty, cellCourt);
                   amcDebt.setCourtId(curtId);
                }

                if(!StringUtils.isEmpty(cellCourtProv) && !regionMap.containsKey(cellCourtProv)){
                    List<Region> regions = regionService.getRegionByName(cellCourtProv);
                    if(!CollectionUtils.isEmpty(regions)){
                        amcDebt.setCurtProv(regions.get(0).getId());
                        regionMap.put(cellCourtProv, regions.get(0).getId());
                    }else{
                        regionMap.put(cellCourtProv, -1L);
                    }
                }else if(regionMap.containsKey(cellCourtProv)){
                    amcDebt.setCurtProv(regionMap.get(cellCourtProv));
                }

                if(!StringUtils.isEmpty(cellCourtCity) && !regionMap.containsKey(cellCourtCity)){
                    List<Region> regions = regionService.getRegionByName(cellCourtCity);
                    if(!CollectionUtils.isEmpty(regions)){
                        amcDebt.setCurtProv(regions.get(0).getId());
                        regionMap.put(cellCourtCity, regions.get(0).getId());
                    }else{
                        regionMap.put(cellCourtCity, -1L);
                    }
                }else if(regionMap.containsKey(cellCourtCity)){
                    amcDebt.setCurtCity(regionMap.get(cellCourtCity));
                }

                if(!StringUtils.isEmpty(cellCourtCounty) && !regionMap.containsKey(cellCourtCounty)){
                    List<Region> regions = regionService.getRegionByName(cellCourtCounty);
                    if(!CollectionUtils.isEmpty(regions)){
                        amcDebt.setCurtProv(regions.get(0).getId());
                        regionMap.put(cellCourtCounty, regions.get(0).getId());
                    }else{
                        regionMap.put(cellCourtCounty, -1L);
                    }
                }else if(regionMap.containsKey(cellCourtCounty)){
                    amcDebt.setCurtCounty(regionMap.get(cellCourtCounty));
                }

                List<AmcDebt> amcDebtsHist =  amcDebtService.queryByTitle(amcDebt.getTitle(), amcDebt.getDebtpackId());
                AmcDebtVo amcDebtVo = new AmcDebtVo();
                if(CollectionUtils.isEmpty(amcDebtsHist)){
                    //can create
                    amcDebtVo =  amcDebtService.create(amcDebt);

                }else{
                    // can update
                    AmcBeanUtils.copyProperties(amcDebt, amcDebtsHist.get(0));
                    amcDebtService.update(amcDebtsHist.get(0));
                    AmcBeanUtils.copyProperties(amcDebtsHist.get(0), amcDebtVo);

                }
                debtMap.put(amcDebtVo.getTitle(), amcDebtVo);


                if(!StringUtils.isEmpty(cellDesc)){
                    amcDebtService.saveDebtDesc(cellDesc, amcDebtVo.getId());
                }


                // after debt created , then make the related object

                if(!StringUtils.isEmpty(cellGrantor)){
                    makeGrantors(cellGrantor, amcDebtVo.getId());
                }

                if(!StringUtils.isEmpty(cellBrowwer)){
                    makeBrowwers(cellBrowwer, amcDebtVo.getId());
                }else{
                    throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.MISSING_EXCEL_CONTENT_ERROR, String.format(" the cellBrowwer is %s", cellBrowwer));
                }
            }

        }
        return debtMap;
    }



    private void makeGrantors(String cellGrantor, Long debtId) {
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

    private void makeBrowwers(String cellBrowwer, Long debtId) {
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


    private Long getCourt(String cellCourtProv, String cellCourtCity, String cellCourtCounty, String cellCourt) {

        CurtInfoExample curtInfoExample = new CurtInfoExample();
        if(StringUtils.isEmpty(cellCourt)){
            return -1L;
        }
        CurtInfoExample.Criteria criteria = curtInfoExample.createCriteria().andCurtNameEqualTo(cellCourt);
        List<CurtInfo> curtInfos = curtInfoMapper.selectByExample(curtInfoExample);
        if(CollectionUtils.isEmpty(curtInfos)){
            log.error("cannot find court for :{}", cellCourt);
            return -1L;
        }
        if(curtInfos.size() > 1){
            log.error(" there is more than one court name as:{}, now check province and city", cellCourt);
            criteria.andCurtProvinceEqualTo(cellCourtProv).andCurtCityEqualTo(cellCourtCity);
            curtInfos = curtInfoMapper.selectByExample(curtInfoExample);
            if(!CollectionUtils.isEmpty(curtInfos)){
                return curtInfos.get(0).getId();
            }else{
                log.error(" there is no curt:{} in province:{} and city:{}", cellCourt, cellCourtProv, cellCourtCity);
                return -1L;
            }
        }
        return -1L;

    }

    SSOAmcUser getAmcContactorByName(String name) throws Exception {
        SSOQueryParam ssoQueryParam = new SSOQueryParam();
        ssoQueryParam.setName(name);
        ssoQueryParam.setPageInfo(new PageInfo());
        AmcPage<SSOAmcUser> queryResults;

        try {
            queryResults = amcContactorService.getSsoAmcUsers(ssoQueryParam);
        } catch (Exception ex) {
            log.error("got error when query:" + ex.getMessage());
            throw ex;
        }
        if(CollectionUtils.isEmpty(queryResults.getContent())){
            throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.NO_SUCHUSER, name);
        }
        if(!CollectionUtils.isEmpty(queryResults.getContent())){
            return queryResults.getContent().get(0);
        }else{
            return null;
        }
    }
}
