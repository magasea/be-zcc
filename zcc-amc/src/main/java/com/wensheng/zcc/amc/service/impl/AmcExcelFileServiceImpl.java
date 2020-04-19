package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.CurtInfoMapper;
import com.wensheng.zcc.amc.module.dao.helper.*;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.*;
import com.wensheng.zcc.amc.module.vo.DebtBatchImportErr;
import com.wensheng.zcc.amc.service.*;
import com.wensheng.zcc.common.module.dto.Region;
import com.wensheng.zcc.common.params.AmcCmpyEnum;
import com.wensheng.zcc.common.params.AmcPage;
import com.wensheng.zcc.common.params.PageInfo;
import com.wensheng.zcc.common.params.sso.AmcLocationEnum;
import com.wensheng.zcc.common.params.sso.SSOAmcUser;
import com.wensheng.zcc.common.utils.AmcNumberUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.StringToolUtils;
import com.wensheng.zcc.common.utils.sso.SSOQueryParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.BatchUpdateException;
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
//    static final String strDebtTotalAmount = "债权本息(元)";
    static final String strDebtInterestAmount = "债权利息(元)";
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
    static final String KEY_WORD_CURT = "法院";

    static final String ERROR_LEVEL_ERR = "错误提示";
    static final String ERROR_LEVEL_WARN = "告警提示";


    @Value("${project.params.debt_image_path}")
    String debtImageRepo;

    @Value("${project.params.asset_image_path}")
    String assetImageRepo;

    private DataFormatter dataFormatter = new DataFormatter();

    @Autowired
    AmcDebtService amcDebtService;

    @Autowired
    AmcExcelPreCheckService amcExcelPreCheckService;

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
    public List<String> importExcel4DebtAsset(MultipartFile multipartFile) throws Exception {
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
        List<String> errorInfo = new ArrayList<>();
//        Map<String, AmcDebtVo> debtMap = handleDebtFromExcel(sheetDebt, errorInfo);
//        Sheet sheetAsset = workbook.getSheetAt(1);
//        handleAssetFromExcel(sheetAsset, debtMap, errorInfo);
        return errorInfo;



    }

    @Override
    @Transactional
    public synchronized List<DebtBatchImportErr>  handleMultiPartFilePrecheck(MultipartFile multipartFile) throws Exception {
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
        List<DebtBatchImportErr> errorInfo = new ArrayList<>();
        Map<String, AmcDebtPre> debtMap = handleDebtPreFromExcel(sheetDebt, errorInfo);
        Sheet sheetAsset = workbook.getSheetAt(1);
        handleAssetPreFromExcel(sheetAsset, debtMap, errorInfo);
        return errorInfo;
    }



    @Override
    public File handleMultiPartFileCurtInfo(MultipartFile multipartFile) throws Exception {
        File targetFile = null;
        targetFile =
                new File(debtImageRepo+File.separator  +multipartFile.getOriginalFilename());


        multipartFile.transferTo(targetFile);





        Long timeStamp = System.currentTimeMillis();

        Workbook workbook =  WorkbookFactory.create(targetFile);
        Sheet sheetCurt =  workbook.getSheet(KEY_WORD_CURT);
        if(null == sheetCurt){
            throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.MISSING_EXCEL_CONTENT_ERROR, String.format("没有找到名字为:%s 的sheet", KEY_WORD_CURT));
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String fileName = debtImageRepo+File.separator+String.format("import-template-%s.xlsx", timeStamp);
        try(

                // Write the output to a file
                FileOutputStream fileOut = new FileOutputStream(fileName);


        ) {
            return handleCurtInfoIntoExcel(sheetCurt, workbook, fileOut, fileName);
        }


    }

    private File handleCurtInfoIntoExcel(Sheet sheetCurt, Workbook workbook, FileOutputStream fileOut, String fileName) throws IOException {
        CurtInfoExample curtInfoExample = new CurtInfoExample();
        curtInfoExample.setOrderByClause(" id desc ");
        int offset = 0;
        int pageSize = 20;
        RowBounds rowBounds = new RowBounds(offset, pageSize);
        List<CurtInfo> curtInfos = curtInfoMapper.selectByExampleWithRowbounds(curtInfoExample, rowBounds);
        boolean hasMore = false;
        if(!CollectionUtils.isEmpty(curtInfos)){
            hasMore = true;
        }
        Map<String, Set<String>> city2CurtNames = new HashMap<>();
        while (hasMore){
            for(CurtInfo curtInfo: curtInfos){
                if(StringUtils.isEmpty(curtInfo.getCurtCity())){
                    continue;
                }
                if(!city2CurtNames.containsKey(curtInfo.getCurtCity())){
                    city2CurtNames.put(curtInfo.getCurtCity(), new HashSet<>());
                }
                city2CurtNames.get(curtInfo.getCurtCity()).add(curtInfo.getCurtName());
            }
            offset += 20;
            rowBounds = new RowBounds(offset, pageSize);
            curtInfos = curtInfoMapper.selectByExampleWithRowbounds(curtInfoExample, rowBounds);
            if(CollectionUtils.isEmpty(curtInfos)){
                hasMore = false;
                break;
            }
        }
        int rowNum = sheetCurt.getLastRowNum();

        for(int idxRow = 0; idxRow < rowNum; idxRow++){
            sheetCurt.removeRowBreak(idxRow);
        }
//        Iterator<Row> rowIterator = sheetCurt.rowIterator();

//        while (rowIterator.hasNext()){
//            Row row = rowIterator.next();
//            sheetCurt.removeRow(row);
//
//        }
        int rowIdx = 0;
        for(Map.Entry<String, Set<String>> entry: city2CurtNames.entrySet()){
            Row row = sheetCurt.createRow(rowIdx);
            Cell cellCity = row.createCell(0);
            cellCity.setCellValue(entry.getKey());
            Cell cellCount = row.createCell(1);
            cellCount.setCellValue(entry.getValue().size());
            int cellCurtNameIdx = 2;
            for(String curtName: entry.getValue()){
                Cell cellCurtName = row.createCell(cellCurtNameIdx);
                cellCurtName.setCellValue(curtName);
                cellCurtNameIdx++;
            }
            rowIdx++;
        }


        workbook.write(fileOut);
//        fileOut.close();
//
//        // Closing the workbook
//        workbook.close();
        return new File(fileName);

    }

    private void handleAssetPreFromExcel(Sheet sheetAsset, Map<String, AmcDebtPre> debtMap, List<DebtBatchImportErr> errorInfo) throws Exception {
//        Map<Long, Map<String, AmcAsset>> historyAssetTitleDebtId = getHistoryAssetTitleDebtIdMap(debtMap.values().stream().map(item->item.getId()).collect(Collectors.toUnmodifiableList()));
        HashMap<String, AmcAssetPre> assetTitles = new HashMap<>();

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
            if(row.getLastCellNum() - row.getFirstCellNum() <= 10 ){
                if(!hasGotHeader){
                    // it is header rows
                    continue;
                }else{
                    break;
                }

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

                AmcAssetPre amcAssetPre = new AmcAssetPre();
                amcAssetPre.setRowNum(row.getRowNum());

                if(StringUtils.isEmpty(cellDebtTitle) && StringUtils.isEmpty(cellAssetName) && StringUtils.isEmpty(cellAssetType)){
                    break;
                }
                if(StringUtils.isEmpty(cellDebtTitle) ){
                    addErrorInfo(errorInfo, DebtPrecheckErrorEnum.FIELD_EMPTY, sheetAsset.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strDebtTitle, cellDebtTitle, null);
//                    break;
                }
                if(StringUtils.isEmpty(cellAssetName)){
                    addErrorInfo(errorInfo, DebtPrecheckErrorEnum.FIELD_EMPTY, sheetAsset.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strAssetName, cellAssetName, null);
//                    break;
                }

                if(!StringUtils.isEmpty(cellDebtTitle) && !debtMap.containsKey(cellDebtTitle)){
                    log.error(String.format("错误提示： %s %s 资产关联的债权名字为：[%s] 但是该债权尚未进入可导入的名单,所以该资产也不能被导入",  sheetAsset.getSheetName(), row.getRowNum(), cellDebtTitle));
                    addErrorInfo(errorInfo, DebtPrecheckErrorEnum.DEBT_PRECHECK_NOTEXIST, sheetAsset.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strDebtTitle, cellDebtTitle, null);
//                    continue;
                }else if(!StringUtils.isEmpty(cellDebtTitle)){
                    amcAssetPre.setDebtId(debtMap.get(cellDebtTitle).getId());
                    amcAssetPre.setDebtTitle(cellDebtTitle);
                }
//                AssetAdditional assetAdditional = new AssetAdditional();
                if(!StringUtils.isEmpty(cellAssetOwner)){
                    amcAssetPre.setOwner(cellAssetOwner);
                }
                if(!StringUtils.isEmpty(cellLandSupplyType)){
                    LandSupplyTypeEnum landSupplyTypeEnum = LandSupplyTypeEnum.lookupByDisplayNameUtil(cellLandSupplyType);
                    if(null == landSupplyTypeEnum){
                        addErrorInfo(errorInfo, DebtPrecheckErrorEnum.TYPE_NOTAVAIL, sheetAsset.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strLandSupplyType, cellLandSupplyType, null);
//                        log.error(String.format("错误提示： %s %s 后台还没设置该土地供应方式枚举值:%s",  sheetAsset.getSheetName(), row.getRowNum(), cellLandSupplyType));
                    }else{
                        amcAssetPre.setLandsupply(landSupplyTypeEnum.getId());
                    }
                }
                if(!StringUtils.isEmpty(cellAssetName)){
                    amcAssetPre.setTitle(cellAssetName);
                }else{
//                    throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.MISSING_EXCEL_CONTENT_ERROR, String.format("没有资产名字:%s", cellAssetName));
//                    errorInfo.add(String.format("错误提示： %s %s 没有资产名字:%s", sheetAsset.getSheetName(), row.getRowNum(), cellAssetName));
                    addErrorInfo(errorInfo, DebtPrecheckErrorEnum.FIELD_EMPTY, sheetAsset.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strAssetName, cellAssetName, null);
//                    log.error(String.format("错误提示： %s %s 没有资产名字:%s", sheetAsset.getSheetName(), row.getRowNum(), cellAssetName));
                    continue;
                }
                amcAssetPre.setAssetNature(AssetNatureEnum.SEAL.getType());
//                if(!StringUtils.isEmpty(cellAssetNature)){
//                    amcAsset.setAssetNature(AssetNatureEnum.lookupByDisplayNameUtil(cellAssetNature).getType());
//                }else{
//                    throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.MISSING_EXCEL_CONTENT_ERROR, String.format("no asset nature:%s", cellAssetNature));
//                }

                if(!StringUtils.isEmpty(cellAssetType)){
                    AssetTypeEnum assetTypeEnum = AssetTypeEnum.lookupByDisplayNameUtil(cellAssetType);
                    if(null == assetTypeEnum){
                        addErrorInfo(errorInfo, DebtPrecheckErrorEnum.TYPE_NOTAVAIL, sheetAsset.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strAssetType, cellAssetType, null);
//                        log.error(String.format("错误提示： %s %s 后台尚未配置该资产类型:%s", sheetAsset.getSheetName(), row.getRowNum(), cellAssetType));
                    }else{
                        amcAssetPre.setType(assetTypeEnum.getType());
                    }

                }else{
//                    log.error(String.format("错误提示： %s %s 资产类型为空:%s", sheetAsset.getSheetName(), row.getRowNum(), cellAssetType));
                    addErrorInfo(errorInfo, DebtPrecheckErrorEnum.FIELD_EMPTY, sheetAsset.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strAssetType, cellAssetType, null);
//                    continue;
                }

                if(!StringUtils.isEmpty(cellAssetLawSealState)){
                    SealStateEnum sealStateEnum = SealStateEnum.lookupByDisplayNameUtil(cellAssetLawSealState);
                    if(null == sealStateEnum){
                        addErrorInfo(errorInfo, DebtPrecheckErrorEnum.TYPE_NOTAVAIL, sheetAsset.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strAssetLawSealState, cellAssetLawSealState, null);
//                        log.error(String.format("错误提示： %s %s 后台尚未配置该查封类型:%s", sheetAsset.getSheetName(), row.getRowNum(), cellAssetLawSealState));
                    }else{
                        amcAssetPre.setSealedState(SealStateEnum.lookupByDisplayNameUtil(cellAssetLawSealState).getStatus());
                    }
                }

                if(!StringUtils.isEmpty(cellAssetProv)){
                    List<Region> regions = regionService.getRegionByName(cellAssetProv);
                    if(CollectionUtils.isEmpty(regions)){
//                        log.error("{} {}",ExceptionUtils.AmcExceptions.INVALID_EXCEL_CONTENT_ERROR, String.format("cellAssetProv:%s",cellAssetProv));
                        addErrorInfo(errorInfo, DebtPrecheckErrorEnum.LOCATIONCODE_NOTAVAIL, sheetAsset.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strAssetProv, cellAssetProv, null);
//                        throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.INVALID_EXCEL_CONTENT_ERROR, String.format("cellAssetCounty:%s",cellAssetCounty));

                    }else{
                        amcAssetPre.setProvince(regions.get(0).getId().toString());
                    }
                }else{
//                    log.error(String.format("错误提示： %s %s 资产所在省份不能为空",sheetAsset.getSheetName(),row.getRowNum()));
                    addErrorInfo(errorInfo, DebtPrecheckErrorEnum.FIELD_EMPTY, sheetAsset.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strAssetProv, cellAssetProv, null);
                }
                if(!StringUtils.isEmpty(cellAssetCity)){
                    List<Region> regions = regionService.getRegionByName(cellAssetCity);
                    if(CollectionUtils.isEmpty(regions)){
//                        log.error("{} {}",ExceptionUtils.AmcExceptions.INVALID_EXCEL_CONTENT_ERROR, String.format("cellAssetCity:%s",cellAssetCity));
                        addErrorInfo(errorInfo, DebtPrecheckErrorEnum.LOCATIONCODE_NOTAVAIL, sheetAsset.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strAssetCity, cellAssetCity, null);
//                        throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.INVALID_EXCEL_CONTENT_ERROR, String.format("cellAssetCounty:%s",cellAssetCounty));

                    }else if(regions.size() > 1){
                        for(Region region: regions){
                            if(region.getId().toString().startsWith(amcAssetPre.getProvince().substring(0,2))){
                                amcAssetPre.setCity(region.getId().toString());
                                break;
                            }
                        }
                    }else{
                        amcAssetPre.setCity(regions.get(0).getId().toString());
                    }
                }else{
//                    log.error(String.format("错误提示： %s %s 资产所在城市不能为空",sheetAsset.getSheetName(),row.getRowNum()));
                    addErrorInfo(errorInfo, DebtPrecheckErrorEnum.FIELD_EMPTY, sheetAsset.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strAssetCity, cellAssetCity, null);
                }

                if(!StringUtils.isEmpty(cellAssetCounty)){
                    List<Region> regions = regionService.getRegionByName(cellAssetCounty);
                    if(CollectionUtils.isEmpty(regions)){
//                        log.error(String.format("错误提示： %s %s 后台没找到对应的资产所在区县的编码:%s",sheetAsset.getSheetName(),row.getRowNum(), cellAssetCounty));
                        addErrorInfo(errorInfo, DebtPrecheckErrorEnum.LOCATIONCODE_NOTAVAIL, sheetAsset.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strAssetCounty, cellAssetCounty, null);
//                        throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.INVALID_EXCEL_CONTENT_ERROR, String.format("cellAssetCounty:%s",cellAssetCounty));

                    }else if(regions.size() > 1){
                        for(Region region: regions){
                            if(region.getId().toString().startsWith(amcAssetPre.getCity().substring(0,3))){
                                amcAssetPre.setCounty(region.getId().toString());
                                break;
                            }
                        }
                    }else{
                        amcAssetPre.setCounty(regions.get(0).getId().toString());
                    }
                }


                if(!StringUtils.isEmpty(cellAssetAddr)){
                    amcAssetPre.setAddress(cellAssetAddr);
                }
                if(!StringUtils.isEmpty(cellBuildingArea)){
                    amcAssetPre.setBuildingArea(AmcNumberUtils.getLongFromStringWithMult100(cellBuildingArea));
                }else{
                    addErrorInfo(errorInfo, DebtPrecheckErrorEnum.FIELD_EMPTY, sheetAsset.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strBuildingArea, cellBuildingArea, null);

                }
                if(!StringUtils.isEmpty(cellLandArea)){
                    amcAssetPre.setLandArea(AmcNumberUtils.getLongFromStringWithMult100(cellLandArea));
                    amcAssetPre.setLandAreaUnit(AreaUnitEnum.SQUAREMETER.getType());
                }else if(StringUtils.isEmpty(cellBuildingArea)){
                    addErrorInfo(errorInfo, DebtPrecheckErrorEnum.FIELD_EMPTY, sheetAsset.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strLandArea, cellLandArea, null);

                }

                if(!StringUtils.isEmpty(cellLandUsage)){
                    LandUsageTypeEnum landUsageTypeEnum = LandUsageTypeEnum.lookupByDisplayNameUtil(cellLandUsage);
                    if(null == landUsageTypeEnum){
//                        log.error(String.format("错误提示： %s %s 后台尚未设置该土地用途枚举值:%s",sheetAsset.getSheetName(),row.getRowNum(), cellLandUsage));
                        addErrorInfo(errorInfo, DebtPrecheckErrorEnum.TYPE_NOTAVAIL, sheetAsset.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strLandUsage, cellLandUsage, null);
                    }else{
                        amcAssetPre.setLandusage(landUsageTypeEnum.getId());
                    }
                }
                if(!StringUtils.isEmpty(cellWarrantInfo)){
                    amcAssetPre.setWarrant(cellWarrantInfo);
                }
                if(!StringUtils.isEmpty(cellNote)){
                    amcAssetPre.setAssetNote(cellNote);
//                    amcAsset.setNote(cellNote);
                }
                if(!assetTitles.containsKey(amcAssetPre.getTitle())){
                    amcExcelPreCheckService.createAsset(amcAssetPre);
                    assetTitles.put(amcAssetPre.getTitle(), amcAssetPre);
                }else{
                    if(assetTitles.get(amcAssetPre.getTitle()).getDebtId() == amcAssetPre.getDebtId()){
                        //same debt id should not have duplicate asset title
//                        log.error(String.format("错误提示： %s %s 有重复的资产名字:%s 在该债权下:%s", sheetAsset.getSheetName(), row.getRowNum(), amcAssetPre.getTitle(), cellDebtTitle));
//                        errorInfo.add(String.format("错误提示： %s %s 有重复的资产名字:%s 在该债权下:%s", sheetAsset.getSheetName(), row.getRowNum(),amcAssetPre.getTitle(), cellDebtTitle));
                        addErrorInfo(errorInfo, DebtPrecheckErrorEnum.DUPLICATEITEM_IN_DEBET, sheetAsset.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strAssetName, amcAssetPre.getTitle(), null);
                        continue;
                    }
                }


            }
        }
    }

    private Map<String, AmcDebtPre> handleDebtPreFromExcel(Sheet sheetDebt, List<DebtBatchImportErr> errorInfo) throws Exception {
        AmcDebtPreExample amcDebtPreExample = new AmcDebtPreExample();
        amcExcelPreCheckService.deleteAllDebtPre(amcDebtPreExample);
        AmcAssetPreExample amcAssetPreExample = new AmcAssetPreExample();
        amcExcelPreCheckService.deleteAllAssetPre(amcAssetPreExample);

        Map<String, AmcDebtPre> debtMap = new HashMap<>();
        Map<String, Long> regionMap = new HashMap<>();
        // 1. You can obtain a rowIterator and columnIterator and iterate over them
        Iterator<Row> rowIterator = sheetDebt.rowIterator();

        int idxDebtTitle = -1;
        int idxBrowwer = -1;
        int idxDebtBaseAmount = -1;
//        int idxDebtTotalAmount = -1;
        int idxDebtInterestAmount = -1;
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
//                        case strDebtTotalAmount:
//                            idxDebtTotalAmount = idxOfCell;
//                            break;
                        case strDebtInterestAmount:
                            idxDebtInterestAmount = idxOfCell;
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
                if(((idxDebtTitle + 1) * (idxBrowwer + 1) * (idxDebtBaseAmount + 1) * (idxDebtInterestAmount +1) * (idxLawState + 1) *
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
//                    if(idxDebtTotalAmount == -1){
//                        missingHeader = strDebtTotalAmount;
//                    }
                    if(idxDebtInterestAmount == -1){
                        missingHeader = strDebtInterestAmount;
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
//                String cellDebtTotalAmount = dataFormatter.formatCellValue(row.getCell(idxDebtTotalAmount));
                String cellDebtInterestAmount = dataFormatter.formatCellValue(row.getCell(idxDebtInterestAmount));
                String cellLawState = dataFormatter.formatCellValue(row.getCell(idxLawState));
                String cellGrantType = dataFormatter.formatCellValue(row.getCell(idxGrantType));
                String cellGrantor = dataFormatter.formatCellValue(row.getCell(idxGrantor));
                String cellCourtProv = dataFormatter.formatCellValue(row.getCell(idxCourtProv));
                String cellCourtCity = dataFormatter.formatCellValue(row.getCell(idxCourtCity));
                String cellCourtCounty = dataFormatter.formatCellValue(row.getCell(idxCourtCounty));
                String cellCourt = dataFormatter.formatCellValue(row.getCell(idxCourt));
                String cellAmcContactor = dataFormatter.formatCellValue(row.getCell(idxAmcContactor));
                String cellDesc = dataFormatter.formatCellValue(row.getCell(idxDesc));


                if(StringUtils.isEmpty(cellDebtTitle) && StringUtils.isEmpty(cellBrowwer)){
                    break;
                }
                if(StringUtils.isEmpty(cellAmcContactor)){
                    addErrorInfo(errorInfo, DebtPrecheckErrorEnum.FIELD_EMPTY, sheetDebt.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strAmcContactor, cellAmcContactor, null);
//                    log.error(String.format("错误提示： %s %s 联系人不能为空", sheetDebt.getSheetName(), row.getRowNum()));
                }
                AmcDebtPre amcDebtPre = new AmcDebtPre();
                amcDebtPre.setAmcId(Long.valueOf(AmcCmpyEnum.CMPY_WENSHENG.getId()));
                amcDebtPre.setPublishState(PublishStateEnum.DRAFT.getStatus());
                amcDebtPre.setRowNum(row.getRowNum());
                SSOAmcUser ssoAmcUser = getAmcContactorByName(cellAmcContactor);


                if(ssoAmcUser == null){
                    addErrorInfo(errorInfo, DebtPrecheckErrorEnum.AMCCONTACTOR_INFO_ERR, sheetDebt.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strAmcContactor, cellAmcContactor, "没有找到该联系人");
//                    log.error(String.format("错误提示： %s %s 后台没有找到对应的联系人信息:%s", sheetDebt.getSheetName(), row.getRowNum(), cellAmcContactor));
//                    break;
                }else{
                    amcDebtPre.setAmcContactorName(cellAmcContactor);
                    amcDebtPre.setAmcContactorPhone(ssoAmcUser.getMobilePhone());
                }

                if(!StringUtils.isEmpty(cellDebtBaseAmount)){
                    amcDebtPre.setBaseAmount(AmcNumberUtils.getLongFromStringWithMult100(cellDebtBaseAmount));
                }else{
                    addErrorInfo(errorInfo, DebtPrecheckErrorEnum.FIELD_EMPTY, sheetDebt.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strDebtBaseAmount, cellDebtBaseAmount, null);
                }
//                if(!StringUtils.isEmpty(cellDebtTotalAmount)){
//                    amcDebt.setTotalAmount(AmcNumberUtils.getLongFromStringWithMult100(cellDebtTotalAmount));
//                }
                if(!StringUtils.isEmpty(cellDebtInterestAmount)){
                    amcDebtPre.setInterestAmount(AmcNumberUtils.getLongFromStringWithMult100(cellDebtInterestAmount));
                }
                if(!StringUtils.isEmpty(cellLawState)){
                    LawstateEnum lawstateEnum = LawstateEnum.lookupByDisplayNameUtil(cellLawState);
                    if(null == lawstateEnum){
                        addErrorInfo(errorInfo, DebtPrecheckErrorEnum.TYPE_NOTAVAIL, sheetDebt.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strLawState, cellLawState, null);
//                        log.error(String.format("错误提示： %s %s 找不到对应的法律状态:%s", sheetDebt.getSheetName(), row.getRowNum(), cellLawState));
                    }else{
                        amcDebtPre.setLawsuitState(LawstateEnum.lookupByDisplayNameUtil(cellLawState).getStatus());
                    }

                }else{
                    addErrorInfo(errorInfo, DebtPrecheckErrorEnum.TYPE_NOTAVAIL, sheetDebt.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strLawState, cellLawState, null);

//                    log.error(String.format("错误提示： %s %s 诉讼状态不能为空", sheetDebt.getSheetName(), row.getRowNum()));
                }
                if(!StringUtils.isEmpty(cellGrantType)){
                    GuarantTypeEnum guarantTypeEnum = GuarantTypeEnum.lookupByDisplayNameUtil(cellGrantType);
                    if(guarantTypeEnum == null){
                        addErrorInfo(errorInfo, DebtPrecheckErrorEnum.TYPE_NOTAVAIL, sheetDebt.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strGrantType, cellGrantType, null);
//                        throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.INVALID_EXCEL_CONTENT_ERROR, String.format("%s %s 后台尚未设置该担保方式枚举值:%s", sheetDebt.getSheetName(), row.getRowNum(), cellGrantType));
                    }
                    amcDebtPre.setGuarantType(GuarantTypeEnum.lookupByDisplayNameUtil(cellGrantType).getType());
                }else{
                    addErrorInfo(errorInfo, DebtPrecheckErrorEnum.FIELD_EMPTY, sheetDebt.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strGrantType, cellGrantType, null);
//                    log.error(String.format("错误提示： %s %s 担保方式不能为空", sheetDebt.getSheetName(), row.getRowNum()));
                }
                if(!StringUtils.isEmpty(cellDebtTitle)){
                    amcDebtPre.setTitle(cellDebtTitle);
                }else{
                    addErrorInfo(errorInfo, DebtPrecheckErrorEnum.FIELD_EMPTY, sheetDebt.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strDebtTitle, cellDebtTitle, null);
                    continue;
                    //
//                    break;
                }

                if(ssoAmcUser != null  && ssoAmcUser.getLocation() == null ){
                    addErrorInfo(errorInfo, DebtPrecheckErrorEnum.AMCCONTACTOR_INFO_ERR, sheetDebt.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strAmcContactor, cellAmcContactor, "没有找到联系人所属的地区(分部)");
//                    break;
                }else if( ssoAmcUser != null ){
                    List<ZccDebtpack> zccDebtpacks =  amcDebtpackService.queryPacksWithLocation(AmcLocationEnum.lookupByDisplayIdUtil(ssoAmcUser.getLocation()));
                    if(CollectionUtils.isEmpty(zccDebtpacks)){

                        addErrorInfo(errorInfo, DebtPrecheckErrorEnum.AMCCONTACTOR_INFO_ERR, sheetDebt.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strAmcContactor, cellAmcContactor, "没有找到联系人所属的地区(分部)");
//                        log.error(String.format("错误提示： %s %s There is no zccDebtPack for ssoAmcUser with location: %s", sheetDebt.getSheetName(), row.getRowNum(), ssoAmcUser.getLocation()));
                    }else{
                        amcDebtPre.setDebtpackId(zccDebtpacks.get(0).getId());
                    }
                }

                if(StringUtils.isEmpty(cellCourtProv)){
                    addErrorInfo(errorInfo, DebtPrecheckErrorEnum.FIELD_EMPTY, sheetDebt.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strCourtProv, cellCourtProv, null);
//                    log.error(String.format("错误提示： %s %s 法院所属省为空", sheetDebt.getSheetName(), row.getRowNum()));
                }

                if(StringUtils.isEmpty(cellCourtCity)){
                    addErrorInfo(errorInfo, DebtPrecheckErrorEnum.FIELD_EMPTY, sheetDebt.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strCourtCity, cellCourtCity, null);
//                    log.error(String.format("错误提示： %s %s 法院所属城市为空", sheetDebt.getSheetName(), row.getRowNum()));
                }
                if(!StringUtils.isEmpty(cellCourt)){
                    Long curtId = getCourt(cellCourtProv, cellCourtCity, cellCourtCounty, cellCourt);
                    if(curtId <= -1L){
                        addErrorInfo(errorInfo, DebtPrecheckErrorEnum.TYPE_NOTAVAIL, sheetDebt.getSheetName(), row.getRowNum(), ERROR_LEVEL_WARN, strCourt, cellCourt, "没有找到该法院");
//                        log.error(String.format("告警提示： %s %s 当前后台系统中没有找到该法院:%s", sheetDebt.getSheetName(), row.getRowNum(), cellCourt));
                    }else{
                        amcDebtPre.setCourtId(curtId);
                    }

                }

                if(!StringUtils.isEmpty(cellCourtProv) && !regionMap.containsKey(cellCourtProv)){
                    List<Region> regions = regionService.getRegionByName(cellCourtProv);
                    if(!CollectionUtils.isEmpty(regions)){
                        amcDebtPre.setCurtProv(regions.get(0).getId());
                        regionMap.put(cellCourtProv, regions.get(0).getId());
                    }else{
//                        log.error(String.format("错误提示： %s %s 找不到对应的法院所属省:%s", sheetDebt.getSheetName(), row.getRowNum(), cellCourtProv));
                        addErrorInfo(errorInfo, DebtPrecheckErrorEnum.LOCATIONCODE_NOTAVAIL, sheetDebt.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strCourtProv, cellCourtProv, null);
                        regionMap.put(cellCourtProv, -1L);
                    }
                }else if(regionMap.containsKey(cellCourtProv)){
                    amcDebtPre.setCurtProv(regionMap.get(cellCourtProv));
                }

                if(!StringUtils.isEmpty(cellCourtCity) && !regionMap.containsKey(cellCourtCity)){
                    List<Region> regions = regionService.getRegionByName(cellCourtCity);
                    if(!CollectionUtils.isEmpty(regions)){
                        amcDebtPre.setCurtCity(regions.get(0).getId());
                        regionMap.put(cellCourtCity, regions.get(0).getId());
                    }else{
                        log.error(String.format("错误提示： %s %s 找不到对应的法院所属市:%s", sheetDebt.getSheetName(), row.getRowNum(), cellCourtCity));
                        addErrorInfo(errorInfo, DebtPrecheckErrorEnum.LOCATIONCODE_NOTAVAIL, sheetDebt.getSheetName(), row.getRowNum(), ERROR_LEVEL_WARN, strCourtCity, cellCourtCity, null);
                        regionMap.put(cellCourtCity, -1L);
                    }
                }else if(regionMap.containsKey(cellCourtCity)){
                    amcDebtPre.setCurtCity(regionMap.get(cellCourtCity));
                }

                if(!StringUtils.isEmpty(cellCourtCounty) && !regionMap.containsKey(cellCourtCounty)){
                    List<Region> regions = regionService.getRegionByName(cellCourtCounty);
                    if(!CollectionUtils.isEmpty(regions)){
                        amcDebtPre.setCurtCounty(regions.get(0).getId());
                        regionMap.put(cellCourtCounty, regions.get(0).getId());
                    }else{
//                        log.error(String.format("错误提示： %s %s 找不到对应的法院所属区县:%s", sheetDebt.getSheetName(), row.getRowNum(), cellCourtCounty));
                        addErrorInfo(errorInfo, DebtPrecheckErrorEnum.LOCATIONCODE_NOTAVAIL, sheetDebt.getSheetName(), row.getRowNum(), ERROR_LEVEL_WARN, strCourtCounty, cellCourtCounty, null);
                        regionMap.put(cellCourtCounty, -1L);
                    }
                }else if(regionMap.containsKey(cellCourtCounty)){
                    amcDebtPre.setCurtCounty(regionMap.get(cellCourtCounty));
                }
                boolean haveFormalDebtTitle = amcExcelPreCheckService.checkDebtTitleExist(amcDebtPre.getTitle());
                if(haveFormalDebtTitle){
                    log.error(String.format("错误提示： %s %s 该债权在正式表AmcDebt里面已经存在,可以前往债权编辑页面去更新它:%s", sheetDebt.getSheetName(), row.getRowNum(), cellDebtTitle));
                    addErrorInfo(errorInfo, DebtPrecheckErrorEnum.DEBT_PRECHECK_FAIL, sheetDebt.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strDebtTitle, cellDebtTitle, null);
//                    continue;
                }
                AmcDebtPre amcDebtPreInDb = null;
                boolean duplicateEvent = false;
                if(!debtMap.containsKey(amcDebtPre.getTitle())){
                    amcDebtPreInDb = amcExcelPreCheckService.createDebt(amcDebtPre);
                    debtMap.put(amcDebtPreInDb.getTitle(), amcDebtPreInDb);
                }else{
                    addErrorInfo(errorInfo, DebtPrecheckErrorEnum.DUPLICATEITEM_IN_DEBET, sheetDebt.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strDebtTitle, cellDebtTitle, "不能创建重复的债权");
                    duplicateEvent = true;

                }





                if(!StringUtils.isEmpty(cellDesc) && !duplicateEvent){
                    amcDebtPreInDb.setDebtDesc(cellDesc);

                }


                // after debt created , then make the related object

                if(!StringUtils.isEmpty(cellGrantor) && !duplicateEvent){
                   amcDebtPreInDb.setGuarantee(checkGrantorsOrBrowwer(cellGrantor, errorInfo, sheetDebt, row, strGrantor));
                }else{
                    addErrorInfo(errorInfo, DebtPrecheckErrorEnum.FIELD_EMPTY, sheetDebt.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strGrantor, cellGrantor, null);
//                    log.error(String.format("错误提示： %s %s 担保人不能为空", sheetDebt.getSheetName(), row.getRowNum()));
                }
                if(!StringUtils.isEmpty(cellBrowwer) && !duplicateEvent){
                    amcDebtPreInDb.setBorrower(checkGrantorsOrBrowwer(cellBrowwer, errorInfo, sheetDebt, row, strBrower));
                }else{
                    addErrorInfo(errorInfo, DebtPrecheckErrorEnum.FIELD_EMPTY, sheetDebt.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, strBrower, cellBrowwer, null);
//                    log.error(String.format("错误提示： %s %s 借款人不能为空", sheetDebt.getSheetName(), row.getRowNum()));
                }
                if(!duplicateEvent){
                    amcExcelPreCheckService.updateDebt(amcDebtPreInDb);
                }

            }

        }
        return debtMap;
    }

    private void addErrorInfo(List<DebtBatchImportErr> errorInfo, DebtPrecheckErrorEnum debtPrecheckErrorEnum,
                              String sheetName, int rowNum, String errorLevel, String fieldName, String itemValue, String reason){
        DebtBatchImportErr debtBatchImportErr = new DebtBatchImportErr();
        debtBatchImportErr.setErrorCode(debtPrecheckErrorEnum.getErrorCode());
        List<Object> hints = new ArrayList<>();
        hints.add(sheetName);
        hints.add(rowNum);
        hints.add(errorLevel);
        hints.add(fieldName);
        hints.add(itemValue);
        if(!StringUtils.isEmpty(reason)){
            hints.add(reason);
        }
        debtBatchImportErr.setHints(hints);
        errorInfo.add(debtBatchImportErr);
        log.error(String.format("ErrorCode:%s sheetName:%s rowNum:%s errorLevel:%s fieldName:%s itemValue:%s reason:%s",debtPrecheckErrorEnum.getErrorCode(),
                sheetName, rowNum, errorLevel, fieldName, itemValue, reason  ));


    }





//    private void handleAssetFromExcel(Sheet sheetAsset, Map<String, AmcDebtVo> debtMap, List<String> errorInfo) throws Exception {
//
//        Map<Long, Map<String, AmcAsset>> historyAssetTitleDebtId = getHistoryAssetTitleDebtIdMap(debtMap.values().stream().map(item->item.getId()).collect(Collectors.toUnmodifiableList()));
//        Set<String> assetTitles = new HashSet<>();
//
//        Iterator<Row> rowIterator = sheetAsset.rowIterator();
//        List<AmcAsset> amcAssets = new ArrayList<>();
//        int idxDebtTitle = -1;
//
//        //资产
//        int idxAssetName = -1;
//        int idxAssetOwner = -1;
////        int idxAssetNature = -1;
//        int idxAssetType = -1;
//        int idxAssetLawSealState = -1;
//        int idxAssetProv = -1;
//        int idxAssetCity = -1;
//        int idxAssetCounty = -1;
//        int idxAssetAddr = -1;
//        int idxBuildingArea = -1;
//        int idxLandArea = -1;
//        int idxLandUsage = -1;
//        int idxLandSupplyType = -1;
//        int idxWarrantInfo = -1;
//        int idxNote = -1;
//
//        boolean hasGotHeader = false;
//        while (rowIterator.hasNext()) {
//
//            Row row = rowIterator.next();
//            if(row.getLastCellNum() - row.getFirstCellNum() <= 10 ){
//                if(!hasGotHeader){
//                    // it is header rows
//                    continue;
//                }else{
//                    break;
//                }
//
//            }
//            if (!hasGotHeader) {
//                log.info("now got first row, it is title row");
//                // Now let's iterate over the columns of the current row
//                Iterator<Cell> cellIterator = row.cellIterator();
//
//                int idxOfCell = -1;
//                while (cellIterator.hasNext()) {
//
//                    Cell cell = cellIterator.next();
//                    idxOfCell++;
//                    String cellValue = dataFormatter.formatCellValue(cell);
//
//                    switch (cellValue) {
//                        case strDebtTitle:
//                            idxDebtTitle = idxOfCell;
//                            break;
//                        case strAssetOwner:
//                            idxAssetOwner = idxOfCell;
//                            break;
//                        case strAssetAddr:
//                            idxAssetAddr = idxOfCell;
//                            break;
//                        case strAssetCity:
//                            idxAssetCity = idxOfCell;
//                            break;
//                        case strAssetCounty:
//                            idxAssetCounty = idxOfCell;
//                            break;
//                        case strAssetLawSealState:
//                            idxAssetLawSealState = idxOfCell;
//                            break;
//                        case strAssetName:
//                            idxAssetName = idxOfCell;
//                            break;
////                        case strAssetNature:
////                            idxAssetNature = idxOfCell;
////                            break;
//                        case strAssetProv:
//                            idxAssetProv = idxOfCell;
//                            break;
//                        case strAssetType:
//                            idxAssetType = idxOfCell;
//                            break;
//                        case strBuildingArea:
//                            idxBuildingArea = idxOfCell;
//
//                        case strLandArea:
//                            idxLandArea = idxOfCell;
//                            break;
//                        case strLandSupplyType:
//                            idxLandSupplyType = idxOfCell;
//                            break;
//                        case strLandUsage:
//                            idxLandUsage = idxOfCell;
//                            break;
//                        case strWarrantInfo:
//                            idxWarrantInfo = idxOfCell;
//                        case strNote:
//                            idxNote = idxOfCell;
//                            break;
//                        default:
//                            throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.INVALID_EXCEL_HEADER_ERROR, cellValue);
//
//                    }
//                }
//
//                // now check if there is missing headers
//                if (((idxDebtTitle + 1) * (idxAssetName + 1)  * (idxAssetType + 1) *
//                        (idxAssetLawSealState + 1) * (idxAssetProv + 1) * (idxAssetCity + 1) * (idxAssetCounty + 1) * (idxAssetAddr + 1) * (idxBuildingArea + 1) *
//                        (idxLandArea + 1) * (idxLandUsage + 1) * (idxLandSupplyType + 1) * (idxWarrantInfo + 1)) == 0) {
//                    String missingHeader = null;
//                    if (idxDebtTitle == -1) {
//                        missingHeader = strDebtTitle;
//                    }
//                    if(idxAssetOwner == -1){
//                        missingHeader = strAssetOwner;
//                    }
//                    if (idxAssetName == -1) {
//                        missingHeader = strAssetName;
//                    }
////                    if (idxAssetNature == -1) {
////                        missingHeader = strAssetNature;
////                    }
//                    if (idxAssetType == -1) {
//                        missingHeader = strAssetType;
//                    }
//                    if (idxAssetLawSealState == -1) {
//                        missingHeader = strAssetLawSealState;
//                    }
//                    if (idxAssetProv == -1) {
//                        missingHeader = strAssetProv;
//                    }
//                    if (idxAssetCity == -1) {
//                        missingHeader = strAssetCity;
//                    }
//                    if (idxAssetCounty == -1) {
//                        missingHeader = strAssetCounty;
//                    }
//                    if (idxAssetAddr == -1) {
//                        missingHeader = strAssetAddr;
//                    }
//                    if (idxBuildingArea == -1) {
//                        missingHeader = strBuildingArea;
//                    }
//                    if (idxLandArea == -1) {
//                        missingHeader = strLandArea;
//                    }
//                    if (idxLandUsage == -1) {
//                        missingHeader = strLandUsage;
//                    }
//                    if (idxLandSupplyType == -1) {
//                        missingHeader = strLandSupplyType;
//                    }
//                    if (idxWarrantInfo == -1) {
//                        missingHeader = strWarrantInfo;
//                    }
//                    if(idxNote == -1){
//                        missingHeader = strNote;
//                    }
//                    throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.MISSING_EXCEL_HEADER_ERROR, missingHeader);
//                }else{
//                    hasGotHeader = true;
//                }
//            } else {
//                // can generate debt and asset
//                String cellDebtTitle = dataFormatter.formatCellValue(row.getCell(idxDebtTitle));
//                String cellAssetOwner = dataFormatter.formatCellValue(row.getCell(idxAssetOwner));
//                String cellAssetName = dataFormatter.formatCellValue(row.getCell(idxAssetName));
////                String cellAssetNature = dataFormatter.formatCellValue(row.getCell(idxAssetNature));
//                String cellAssetType = dataFormatter.formatCellValue(row.getCell(idxAssetType));
//                String cellAssetLawSealState = dataFormatter.formatCellValue(row.getCell(idxAssetLawSealState));
//                String cellAssetProv = dataFormatter.formatCellValue(row.getCell(idxAssetProv));
//                String cellAssetCity = dataFormatter.formatCellValue(row.getCell(idxAssetCity));
//                String cellAssetCounty = dataFormatter.formatCellValue(row.getCell(idxAssetCounty));
//                String cellAssetAddr = dataFormatter.formatCellValue(row.getCell(idxAssetAddr));
//                String cellBuildingArea = dataFormatter.formatCellValue(row.getCell(idxBuildingArea));
//                String cellLandArea = dataFormatter.formatCellValue(row.getCell(idxLandArea));
//                String cellLandUsage = dataFormatter.formatCellValue(row.getCell(idxLandUsage));
//                String cellLandSupplyType = dataFormatter.formatCellValue(row.getCell(idxLandSupplyType));
//                String cellWarrantInfo = dataFormatter.formatCellValue(row.getCell(idxWarrantInfo));
//                String cellNote = dataFormatter.formatCellValue(row.getCell(idxNote));
//
//                AmcAsset amcAsset = new AmcAsset();
//
//                if(StringUtils.isEmpty(cellDebtTitle) && StringUtils.isEmpty(cellAssetName)){
//                    break;
//                }
//
//                if(!debtMap.containsKey(cellDebtTitle)){
//                    log.error(String.format("错误提示： %s %s 资产关联的债权名字为：[%s] 但是该债权尚未进入可导入的名单,所以该资产也不能被导入, 如果该债权在正式已经在正式库中,以前往债权编辑页面去编辑它添加该资产或者删除该债权(须为草稿状态)以重新导入",  sheetAsset.getSheetName(), row.getRowNum(), cellDebtTitle));
//                    errorInfo.add(String.format("错误提示： %s %s 资产关联的债权名字为：[%s] 但是该债权尚未进入可导入的名单,所以该资产也不能被导入, 如果该债权在正式已经在正式库中,可以前往债权编辑页面去编辑它添加该资产或者删除该债权(须为草稿状态)以重新导入",  sheetAsset.getSheetName(), row.getRowNum(), cellDebtTitle));
//                    continue;
//                }else{
//                    amcAsset.setDebtId(debtMap.get(cellDebtTitle).getId());
//                }
//                AssetAdditional assetAdditional = new AssetAdditional();
//                if(!StringUtils.isEmpty(cellAssetOwner)){
//                    assetAdditional.setOwner(cellAssetOwner);
//                }
//                if(!StringUtils.isEmpty(cellLandSupplyType)){
//                    assetAdditional.setLandSupplyType(LandSupplyTypeEnum.lookupByDisplayNameUtil(cellLandSupplyType).getId());
//                }
//                if(!StringUtils.isEmpty(cellAssetName)){
//                    amcAsset.setTitle(cellAssetName);
//                }else{
////                    throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.MISSING_EXCEL_CONTENT_ERROR, String.format("没有资产名字:%s", cellAssetName));
//                    errorInfo.add(String.format("错误提示： %s %s 没有资产名字:%s", sheetAsset.getSheetName(), row.getRowNum(), cellAssetName));
//                    continue;
//                }
//                amcAsset.setAssetNature(AssetNatureEnum.SEAL.getType());
////                if(!StringUtils.isEmpty(cellAssetNature)){
////                    amcAsset.setAssetNature(AssetNatureEnum.lookupByDisplayNameUtil(cellAssetNature).getType());
////                }else{
////                    throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.MISSING_EXCEL_CONTENT_ERROR, String.format("no asset nature:%s", cellAssetNature));
////                }
//
//                if(!StringUtils.isEmpty(cellAssetType)){
//                    amcAsset.setType(AssetTypeEnum.lookupByDisplayNameUtil(cellAssetType).getType());
//                }else{
//                    log.error(String.format("错误提示： %s %s 后台尚未配置该资产类型:%s", sheetAsset.getSheetName(), row.getRowNum(), cellAssetType));
//                    continue;
//                }
//
//                if(!StringUtils.isEmpty(cellAssetLawSealState)){
//                    amcAsset.setSealedState(SealStateEnum.lookupByDisplayNameUtil(cellAssetLawSealState).getStatus());
//                }
//
//                if(!StringUtils.isEmpty(cellAssetProv)){
//                    List<Region> regions = regionService.getRegionByName(cellAssetProv);
//                    if(CollectionUtils.isEmpty(regions)){
//                        log.error("{} {}",ExceptionUtils.AmcExceptions.INVALID_EXCEL_CONTENT_ERROR, String.format("cellAssetProv:%s",cellAssetProv));
//                        errorInfo.add(String.format("错误提示： %s %s 后台没找到对应的资产所在省份的编码:%s",sheetAsset.getSheetName(),row.getRowNum(), cellAssetProv));
////                        throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.INVALID_EXCEL_CONTENT_ERROR, String.format("cellAssetCounty:%s",cellAssetCounty));
//
//                    }else{
//                        amcAsset.setProvince(regions.get(0).getId().toString());
//                    }
//                }
//                if(!StringUtils.isEmpty(cellAssetCity)){
//                    List<Region> regions = regionService.getRegionByName(cellAssetCity);
//                    if(CollectionUtils.isEmpty(regions)){
//                        log.error("{} {}",ExceptionUtils.AmcExceptions.INVALID_EXCEL_CONTENT_ERROR, String.format("cellAssetCity:%s",cellAssetCity));
//                        errorInfo.add(String.format("错误提示： %s %s 后台没找到对应的资产所在省份的编码:%s",sheetAsset.getSheetName(),row.getRowNum(), cellAssetCity));
////                        throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.INVALID_EXCEL_CONTENT_ERROR, String.format("cellAssetCounty:%s",cellAssetCounty));
//
//                    }else if(regions.size() > 1){
//                        for(Region region: regions){
//                            if(region.getId().toString().startsWith(amcAsset.getProvince().substring(0,2))){
//                                amcAsset.setCity(region.getId().toString());
//                                break;
//                            }
//                        }
//                    }else{
//                        amcAsset.setCity(regions.get(0).getId().toString());
//                    }
//                }
//
//                if(!StringUtils.isEmpty(cellAssetCounty)){
//                    List<Region> regions = regionService.getRegionByName(cellAssetCounty);
//                    if(CollectionUtils.isEmpty(regions)){
//                        log.error("{} {}",ExceptionUtils.AmcExceptions.INVALID_EXCEL_CONTENT_ERROR, String.format("cellAssetCounty:%s",cellAssetCounty));
//                        errorInfo.add(String.format("错误提示： %s %s 后台没找到对应的资产所在区县的编码:%s",sheetAsset.getSheetName(),row.getRowNum(), cellAssetCounty));
////                        throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.INVALID_EXCEL_CONTENT_ERROR, String.format("cellAssetCounty:%s",cellAssetCounty));
//
//                    }else if(regions.size() > 1){
//                        for(Region region: regions){
//                            if(region.getId().toString().startsWith(amcAsset.getCity().substring(0,3))){
//                                amcAsset.setCounty(region.getId().toString());
//                                break;
//                            }
//                        }
//                    }else{
//                        amcAsset.setCounty(regions.get(0).getId().toString());
//                    }
//                }
//
//
//                if(!StringUtils.isEmpty(cellAssetAddr)){
//                    amcAsset.setAddress(cellAssetAddr);
//                }
//                if(!StringUtils.isEmpty(cellBuildingArea)){
//                    amcAsset.setBuildingArea(AmcNumberUtils.getLongFromStringWithMult100(cellBuildingArea));
//                }
//                if(!StringUtils.isEmpty(cellLandArea)){
//                    amcAsset.setLandArea(AmcNumberUtils.getLongFromStringWithMult100(cellLandArea));
//                    amcAsset.setLandAreaUnit(AreaUnitEnum.SQUAREMETER.getType());
//                }
//
//                if(!StringUtils.isEmpty(cellLandUsage)){
//                    assetAdditional.setLandUsageType(LandUsageTypeEnum.lookupByDisplayNameUtil(cellLandUsage).getId());
//                }
//                if(!StringUtils.isEmpty(cellWarrantInfo)){
//                    assetAdditional.setWarrant(cellWarrantInfo);
//                }
//                if(!StringUtils.isEmpty(cellNote)){
//                    assetAdditional.setDescription(cellNote);
////                    amcAsset.setNote(cellNote);
//                }
//
//                if(historyAssetTitleDebtId.containsKey(amcAsset.getDebtId())){
//                    assetTitles =  historyAssetTitleDebtId.get((amcAsset.getDebtId())).keySet();
//
//                }
//
//                if(assetTitles.contains(amcAsset.getTitle())){
//                    //update
//                    AmcAsset amcAssetHistory = historyAssetTitleDebtId.get(amcAsset.getDebtId()).get(amcAsset.getTitle());
//                    AmcBeanUtils.copyProperties(amcAsset, amcAssetHistory);
//
//                    amcAssetService.update(amcAssetHistory);
//                    assetAdditional.setAmcAssetId(amcAssetHistory.getId());
//                    amcAssetService.createOrUpdateAssetAddition(assetAdditional);
//
//                }else{
//                    //create
//                    AmcAssetVo amcAssetVo = amcAssetService.create(amcAsset);
//                    assetAdditional.setAmcAssetId(amcAssetVo.getId());
//                    amcAssetService.createOrUpdateAssetAddition(assetAdditional);
//                }
//
//
//
//
//
//
//            }
//        }
//
//    }


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

//    private Map<String, AmcDebtVo> handleDebtFromExcel(Sheet sheetDebt, List<String> errorInfo) throws Exception {
//
//        Map<String, AmcDebtVo> debtMap = new HashMap<>();
//        Map<String, Long> regionMap = new HashMap<>();
//        // 1. You can obtain a rowIterator and columnIterator and iterate over them
//        Iterator<Row> rowIterator = sheetDebt.rowIterator();
//        List<AmcDebt> amcDebts = new ArrayList<>();
//        int idxDebtTitle = -1;
//        int idxBrowwer = -1;
//        int idxDebtBaseAmount = -1;
////        int idxDebtTotalAmount = -1;
//        int idxDebtInterestAmount = -1;
//        int idxLawState = -1;
//        int idxGrantType = -1;
//        int idxGrantor = -1;
//        int idxCourtProv = -1;
//        int idxCourtCity = -1;
//        int idxCourtCounty = -1;
//        int idxCourt = -1;
//        int idxAmcContactor = -1;
//        int idxDesc = -1;
//        boolean hasGotHeader = false;
//        while (rowIterator.hasNext()) {
//            Row row = rowIterator.next();
//            if(row.getLastCellNum() - row.getFirstCellNum() <= 8 ){
//                if(!hasGotHeader){
//                    // it is header rows
//                    continue;
//                }else{
//                    break;
//                }
//
//            }
//            if(!hasGotHeader){
//                log.info("now got first row, it is title row");
//                // Now let's iterate over the columns of the current row
//                Iterator<Cell> cellIterator = row.cellIterator();
//                int idxOfCell = -1;
//                while (cellIterator.hasNext()) {
//                    Cell cell = cellIterator.next();
//                    idxOfCell++;
//                    String cellValue = dataFormatter.formatCellValue(cell);
//                    System.out.print(cellValue + "\t");
//                    switch (cellValue) {
//                        case strDebtTitle:
//                            idxDebtTitle = idxOfCell;
//                            break;
//                        case strAmcContactor:
//                            idxAmcContactor = idxOfCell;
//                            break;
//                        case strBrower:
//                            idxBrowwer = idxOfCell;
//                            break;
//                        case strCourt:
//                            idxCourt = idxOfCell;
//                            break;
//                        case strCourtCity:
//                            idxCourtCity = idxOfCell;
//                            break;
//                        case strCourtCounty:
//                            idxCourtCounty = idxOfCell;
//                            break;
//                        case strCourtProv:
//                            idxCourtProv = idxOfCell;
//                            break;
//                        case strDebtBaseAmount:
//                            idxDebtBaseAmount = idxOfCell;
//                            break;
////                        case strDebtTotalAmount:
////                            idxDebtTotalAmount = idxOfCell;
////                            break;
//                        case strDebtInterestAmount:
//                            idxDebtInterestAmount = idxOfCell;
//                            break;
//                        case strDesc:
//                            idxDesc = idxOfCell;
//                            break;
//                        case strGrantor:
//                            idxGrantor = idxOfCell;
//                            break;
//                        case strGrantType:
//                            idxGrantType = idxOfCell;
//                            break;
//                        case strLawState:
//                            idxLawState = idxOfCell;
//                            break;
//                        default:
//                            throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.INVALID_EXCEL_HEADER_ERROR, cellValue);
//
//                    }
//                }
//                // now check if there is missing headers
//                if(((idxDebtTitle + 1) * (idxBrowwer + 1) * (idxDebtBaseAmount + 1) * (idxDebtInterestAmount +1) * (idxLawState + 1) *
//                        (idxGrantType + 1) * (idxGrantor + 1) * (idxCourtProv + 1) * (idxCourtCity + 1) *
//                        (idxCourtCounty + 1) * (idxCourt + 1) * (idxAmcContactor + 1) * (idxDesc + 1) ) == 0){
//                    String missingHeader = null;
//                    if(idxDebtTitle == -1){
//                        missingHeader = strDebtTitle;
//                    }
//                    if(idxBrowwer == -1){
//                        missingHeader = strBrower;
//                    }
//                    if(idxDebtBaseAmount == -1){
//                        missingHeader = strDebtBaseAmount;
//                    }
////                    if(idxDebtTotalAmount == -1){
////                        missingHeader = strDebtTotalAmount;
////                    }
//                    if(idxDebtInterestAmount == -1){
//                        missingHeader = strDebtInterestAmount;
//                    }
//                    if(idxLawState == -1){
//                        missingHeader = strLawState;
//                    }
//                    if(idxGrantType == -1){
//                        missingHeader = strGrantType;
//                    }
//                    if(idxGrantor == -1){
//                        missingHeader = strGrantor;
//                    }
//                    if(idxCourtProv == -1){
//                        missingHeader = strCourtProv;
//                    }
//                    if(idxCourtCity == -1){
//                        missingHeader = strCourtCity;
//                    }
//                    if(idxCourtCounty == -1){
//                        missingHeader = strCourtCounty;
//                    }
//                    if(idxCourt == -1){
//                        missingHeader = strCourt;
//                    }
//                    if(idxAmcContactor == -1){
//                        missingHeader = strAmcContactor;
//                    }
//                    if(idxDesc == -1){
//                        missingHeader = strDesc;
//                    }
//                    throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.MISSING_EXCEL_HEADER_ERROR, missingHeader);
//                }else{
//                    hasGotHeader = true;
//                }
//            }else{
//                // can generate debt and asset
//                String cellDebtTitle = dataFormatter.formatCellValue(row.getCell(idxDebtTitle));
//                String cellBrowwer = dataFormatter.formatCellValue(row.getCell(idxBrowwer));
//                String cellDebtBaseAmount = dataFormatter.formatCellValue(row.getCell(idxDebtBaseAmount));
////                String cellDebtTotalAmount = dataFormatter.formatCellValue(row.getCell(idxDebtTotalAmount));
//                String cellDebtInterestAmount = dataFormatter.formatCellValue(row.getCell(idxDebtInterestAmount));
//                String cellLawState = dataFormatter.formatCellValue(row.getCell(idxLawState));
//                String cellGrantType = dataFormatter.formatCellValue(row.getCell(idxGrantType));
//                String cellGrantor = dataFormatter.formatCellValue(row.getCell(idxGrantor));
//                String cellCourtProv = dataFormatter.formatCellValue(row.getCell(idxCourtProv));
//                String cellCourtCity = dataFormatter.formatCellValue(row.getCell(idxCourtCity));
//                String cellCourtCounty = dataFormatter.formatCellValue(row.getCell(idxCourtCounty));
//                String cellCourt = dataFormatter.formatCellValue(row.getCell(idxCourt));
//                String cellAmcContactor = dataFormatter.formatCellValue(row.getCell(idxAmcContactor));
//                String cellDesc = dataFormatter.formatCellValue(row.getCell(idxDesc));
//                AmcDebt amcDebt = new AmcDebt();
//                amcDebt.setAmcId(Long.valueOf(AmcCmpyEnum.CMPY_WENSHENG.getId()));
//                amcDebt.setPublishState(PublishStateEnum.DRAFT.getStatus());
//                if(StringUtils.isEmpty(cellAmcContactor)){
//                    errorInfo.add(String.format("错误提示： %s %s 联系人不能为空", sheetDebt.getSheetName(), row.getRowNum(), cellGrantType));
//                    log.error(String.format("错误提示： %s %s 联系人不能为空", sheetDebt.getSheetName(), row.getRowNum(), cellGrantType));
//                }
//                amcDebt.setAmcContactorName(cellAmcContactor);
//                SSOAmcUser ssoAmcUser = getAmcContactorByName(cellAmcContactor);
//                amcDebt.setAmcContactorPhone(ssoAmcUser.getMobilePhone());
//                if(!StringUtils.isEmpty(cellDebtBaseAmount)){
//                    amcDebt.setBaseAmount(AmcNumberUtils.getLongFromStringWithMult100(cellDebtBaseAmount));
//                }
////                if(!StringUtils.isEmpty(cellDebtTotalAmount)){
////                    amcDebt.setTotalAmount(AmcNumberUtils.getLongFromStringWithMult100(cellDebtTotalAmount));
////                }
//                if(!StringUtils.isEmpty(cellDebtInterestAmount)){
//                    amcDebt.setInterestAmount(AmcNumberUtils.getLongFromStringWithMult100(cellDebtInterestAmount));
//                }
//                if(!StringUtils.isEmpty(cellLawState)){
//                    amcDebt.setLawsuitState(LawstateEnum.lookupByDisplayNameUtil(cellLawState).getStatus());
//                }
//                if(!StringUtils.isEmpty(cellGrantType)){
//                    GuarantTypeEnum guarantTypeEnum = GuarantTypeEnum.lookupByDisplayNameUtil(cellGrantType);
//                    if(guarantTypeEnum == null){
//                        errorInfo.add(String.format("错误提示： %s %s 后台尚未设置该担保方式枚举值:%s", sheetDebt.getSheetName(), row.getRowNum(), cellGrantType));
//                        throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.INVALID_EXCEL_CONTENT_ERROR, String.format("%s %s 后台尚未设置该担保方式枚举值:%s", sheetDebt.getSheetName(), row.getRowNum(), cellGrantType));
//                    }
//                    amcDebt.setGuarantType(GuarantTypeEnum.lookupByDisplayNameUtil(cellGrantType).getType());
//                }
//                if(!StringUtils.isEmpty(cellDebtTitle)){
//                    amcDebt.setTitle(cellDebtTitle);
//                }else{
//                    break;
//                }
//
//                List<ZccDebtpack> zccDebtpacks =  amcDebtpackService.queryPacksWithLocation(AmcLocationEnum.lookupByDisplayIdUtil(ssoAmcUser.getLocation()));
//                if(CollectionUtils.isEmpty(zccDebtpacks)){
//
//                    log.error(String.format("错误提示： %s %s There is no zccDebtPack for ssoAmcUser with location: %s", sheetDebt.getSheetName(), row.getRowNum(), ssoAmcUser.getLocation()));
//                }
//                if(!StringUtils.isEmpty(cellCourt)){
//                   Long curtId = getCourt(cellCourtProv, cellCourtCity, cellCourtCounty, cellCourt);
//                   amcDebt.setCourtId(curtId);
//                }
//
//                if(!StringUtils.isEmpty(cellCourtProv) && !regionMap.containsKey(cellCourtProv)){
//                    List<Region> regions = regionService.getRegionByName(cellCourtProv);
//                    if(!CollectionUtils.isEmpty(regions)){
//                        amcDebt.setCurtProv(regions.get(0).getId());
//                        regionMap.put(cellCourtProv, regions.get(0).getId());
//                    }else{
//                        regionMap.put(cellCourtProv, -1L);
//                    }
//                }else if(regionMap.containsKey(cellCourtProv)){
//                    amcDebt.setCurtProv(regionMap.get(cellCourtProv));
//                }
//
//                if(!StringUtils.isEmpty(cellCourtCity) && !regionMap.containsKey(cellCourtCity)){
//                    List<Region> regions = regionService.getRegionByName(cellCourtCity);
//                    if(!CollectionUtils.isEmpty(regions)){
//                        amcDebt.setCurtCity(regions.get(0).getId());
//                        regionMap.put(cellCourtCity, regions.get(0).getId());
//                    }else{
//                        regionMap.put(cellCourtCity, -1L);
//                    }
//                }else if(regionMap.containsKey(cellCourtCity)){
//                    amcDebt.setCurtCity(regionMap.get(cellCourtCity));
//                }
//
//                if(!StringUtils.isEmpty(cellCourtCounty) && !regionMap.containsKey(cellCourtCounty)){
//                    List<Region> regions = regionService.getRegionByName(cellCourtCounty);
//                    if(!CollectionUtils.isEmpty(regions)){
//                        amcDebt.setCurtProv(regions.get(0).getId());
//                        regionMap.put(cellCourtCounty, regions.get(0).getId());
//                    }else{
//                        regionMap.put(cellCourtCounty, -1L);
//                    }
//                }else if(regionMap.containsKey(cellCourtCounty)){
//                    amcDebt.setCurtCounty(regionMap.get(cellCourtCounty));
//                }
//
//                List<AmcDebt> amcDebtsHist =  amcDebtService.queryByTitle(amcDebt.getTitle(), amcDebt.getDebtpackId());
//                AmcDebtVo amcDebtVo = new AmcDebtVo();
//                if(CollectionUtils.isEmpty(amcDebtsHist)){
//                    //can create
//                    amcDebtVo =  amcDebtService.create(amcDebt);
//
//                }else{
//                    // can update
//                    AmcBeanUtils.copyProperties(amcDebt, amcDebtsHist.get(0));
//                    amcDebtService.update(amcDebtsHist.get(0));
//                    AmcBeanUtils.copyProperties(amcDebtsHist.get(0), amcDebtVo);
//
//                }
//                debtMap.put(amcDebtVo.getTitle(), amcDebtVo);
//
//
//                if(!StringUtils.isEmpty(cellDesc)){
//                    amcDebtService.saveDebtDesc(cellDesc, amcDebtVo.getId());
//                }
//
//
//                // after debt created , then make the related object
//
//                if(!StringUtils.isEmpty(cellGrantor)){
//                    makeGrantors(cellGrantor, amcDebtVo.getId());
//                }
//
//                if(!StringUtils.isEmpty(cellBrowwer)){
//                    makeBrowwers(cellBrowwer, amcDebtVo.getId());
//                }else{
//                    errorInfo.add(String.format("错误提示： %s %s 借款人信息为空：%s", sheetDebt.getSheetName(), row.getRowNum(), cellBrowwer));
//                    log.error(String.format("错误提示： %s %s 借款人信息为空：%s", sheetDebt.getSheetName(), row.getRowNum(), cellBrowwer));
//                    continue;
//                }
//            }
//
//        }
//        return debtMap;
//    }
    private String checkGrantorsOrBrowwer(String cellGrantorOrBrowwer, List<DebtBatchImportErr> errorInfo, Sheet sheetDebt, Row row, String columnName) {

        if(!StringToolUtils.isNormalString(cellGrantorOrBrowwer) && !cellGrantorOrBrowwer.contains(SEP_CHAR)){
            addErrorInfo(errorInfo, DebtPrecheckErrorEnum.SPEC_CHAR_ERR, sheetDebt.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, columnName, cellGrantorOrBrowwer, null);
        }
        if(cellGrantorOrBrowwer.contains(SEP_CHAR)){
            Set<String> grantorsInSet = new HashSet<>();
            String[] cellGrantors = cellGrantorOrBrowwer.split(SEP_CHAR);
            for(String grantor: cellGrantors){
               if(grantorsInSet.contains(grantor)){
                   addErrorInfo(errorInfo, DebtPrecheckErrorEnum.DUPLICATENAME_IN_DEBET, sheetDebt.getSheetName(), row.getRowNum(), ERROR_LEVEL_ERR, columnName, grantor, null);
//                   errorInfo.add(String.format("告警提示： %s %s %s 在 %s 中有重复,重复的名字将被去重:", sheetDebt.getSheetName(), row.getRowNum(), grantor, cellGrantorOrBrowwer));
               }else{
                   grantorsInSet.add(grantor);
               }
            }
            return  String.join(",", grantorsInSet);
        }else{
            return cellGrantorOrBrowwer;
        }
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
        }else{
            return curtInfos.get(0).getId();
        }


    }

    @Override
    public SSOAmcUser getAmcContactorByName(String name)  {
        SSOQueryParam ssoQueryParam = new SSOQueryParam();
        ssoQueryParam.setName(name);
        ssoQueryParam.setPageInfo(new PageInfo());
        AmcPage<SSOAmcUser> queryResults;

        try {
            queryResults = amcContactorService.getSsoAmcUsers(ssoQueryParam);
        } catch (Exception ex) {
            log.error("got error when query:" + ex.getMessage());
            return null;
        }
        if(CollectionUtils.isEmpty(queryResults.getContent())){
            return  null;
        }
        if(!CollectionUtils.isEmpty(queryResults.getContent())){
            return queryResults.getContent().get(0);
        }else{
            return null;
        }
    }
}
