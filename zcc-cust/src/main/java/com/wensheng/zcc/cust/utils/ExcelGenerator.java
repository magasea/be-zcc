package com.wensheng.zcc.cust.utils;

import com.wensheng.zcc.cust.dao.mysql.mapper.CustRegionDetailMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustRegionMapper;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustRegion;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustRegionDetail;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustRegionDetailExample;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustRegionExample;
import com.wensheng.zcc.cust.module.dao.mysql.ext.CustTrdCmpyTrdExt;
import com.wensheng.zcc.cust.module.helper.CustTypeEnum;
import com.wensheng.zcc.cust.module.vo.CustTrdInfoExcelVo;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


@Service
@Slf4j
public class ExcelGenerator {

  @Value("${env.image-repo}")
  String fileBase;

  @Autowired
  CustRegionDetailMapper custRegionMapper;

    public  File customersToExcel(List<CustTrdInfoExcelVo> custTrdInfoExcelVos, List<String> cmpyProvinceList) throws IOException {
      String[] COLUMNs = {"#", "投资人名称", "投资偏好类型(统计)", "偏好地区(统计)","债权本息总额(元)","交易总额(元)","联系方式","联系地址","更新时间"};
      Map<Long, String> provinceName = new HashMap<>();
      if(CustTypeEnum.COMPANY.getId() == custTrdInfoExcelVos.get(0).getCustType()){
        //数据库中查询地区信息
        CustRegionDetailExample custRegionDetailExample = new CustRegionDetailExample();
        List<Long> cmpyProvinceIdList = new ArrayList<>(cmpyProvinceList.size());
        cmpyProvinceList.forEach(a->cmpyProvinceIdList.add(Long.valueOf(a)));
        custRegionDetailExample.createCriteria().andIdIn(cmpyProvinceIdList);
        List<CustRegionDetail>  custRegionDetailList = custRegionMapper.selectByExample(custRegionDetailExample);
        //成map
        provinceName = custRegionDetailList.stream().collect(
            Collectors.toMap(CustRegionDetail::getId, custRegion -> custRegion.getName()));
        COLUMNs = new  String[]{"#", "投资人名称","所属地区", "投资偏好类型(统计)", "偏好地区(统计)","债权本息总额(元)","交易总额(元)","联系方式","联系地址","更新时间"};
      }

      File file = new File(fileBase);
      boolean dirCreated = file.mkdir();
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

      Long timeStamp = System.currentTimeMillis();
      try(
          Workbook workbook = new XSSFWorkbook();
          // Write the output to a file
          FileOutputStream fileOut = new FileOutputStream(fileBase+File.separator+String.format("custInfo-%s.xlsx",
              timeStamp));
//          ByteArrayOutputStream out = new ByteArrayOutputStream();
      ){
        CreationHelper createHelper = workbook.getCreationHelper();

        Sheet sheet = workbook.createSheet("custInfo");


        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.BLUE.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Row for Header
        Row headerRow = sheet.createRow(0);

        // Header
        for (int col = 0; col < COLUMNs.length; col++) {
          Cell cell = headerRow.createCell(col);
          cell.setCellValue(COLUMNs[col]);
          cell.setCellStyle(headerCellStyle);
        }


        CellStyle custCellStyle = workbook.createCellStyle();
        custCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));

        int rowIdx = 1;
        for (CustTrdInfoExcelVo custTrdInfoExcelVo : custTrdInfoExcelVos) {
          Row row = sheet.createRow(rowIdx++);

          int line = 0;
          row.createCell(line).setCellValue(custTrdInfoExcelVo.getCustId());
          line++;
          row.createCell(line).setCellValue(custTrdInfoExcelVo.getCustName());
          if(CustTypeEnum.COMPANY.getId() == custTrdInfoExcelVo.getCustType()){
            line++;
            String cmpyProvinceCode = custTrdInfoExcelVo.getCmpyProvince();
            Long cmpyProvinceId =0L;
            try {
              cmpyProvinceId = Long.valueOf(cmpyProvinceCode);
            }catch (Exception e){
              log.error("地址转换出错cmpyProvinceCode：{}", cmpyProvinceCode);
            }
            if(!StringUtils.isEmpty(cmpyProvinceCode)){
              row.createCell(line).setCellValue(provinceName.get(cmpyProvinceId));
            }
          }
          line++;
          row.createCell(line).setCellValue(getStringFromMap(custTrdInfoExcelVo.getInvestType2Counts()));
          line++;
          row.createCell(line).setCellValue(getStringFromMap(custTrdInfoExcelVo.getIntrestCities()));
          line++;
          if(custTrdInfoExcelVo.getDebtTotalAmount() > 0){
            row.createCell(line).setCellValue(custTrdInfoExcelVo.getDebtTotalAmount());
          }else{
            log.error("The debtTotalAmount:{}  of custId:{} is not valid", custTrdInfoExcelVo.getDebtTotalAmount(),
                custTrdInfoExcelVo.getCustId());
            row.createCell(line);
          }
          row.getCell(line).setCellStyle(custCellStyle);
          line++;
          if(custTrdInfoExcelVo.getTrdTotalAmount() > 0){
            row.createCell(line).setCellValue(custTrdInfoExcelVo.getTrdTotalAmount());
          }else{
            log.error("The trdTotalAmount:{}  of custId:{} is not valid", custTrdInfoExcelVo.getTrdTotalAmount(),
                custTrdInfoExcelVo.getCustId());
            row.createCell(line);
          }
          row.getCell(line).setCellStyle(custCellStyle);
          line++;
          if(StringUtils.isEmpty(custTrdInfoExcelVo.getPhone()) || custTrdInfoExcelVo.getPhone().contains("-1;")){
            log.error("The phone info:{} of custId:{} is not valid", custTrdInfoExcelVo.getPhone(),
                custTrdInfoExcelVo.getCustId());
            row.createCell(line);
          }else{
            row.createCell(line).setCellValue(custTrdInfoExcelVo.getPhone());
          }
          line++;
          row.createCell(line).setCellValue(custTrdInfoExcelVo.getAddress());
          line++;
          row.createCell(line).setCellValue(simpleDateFormat.format(custTrdInfoExcelVo.getUpdateTime()));
        }

        workbook.write(fileOut);
//        fileOut.close();
//
//        // Closing the workbook
//        workbook.close();
        return new File(fileBase+File.separator+String.format("custInfo-%s.xlsx",
            timeStamp));
      }

  }

  private  String getStringFromMap(Map<String, Integer> param){
      if(CollectionUtils.isEmpty(param)){
        log.error(" it is empty param:{}", param);
        return "";
      }
      StringBuilder sb = new StringBuilder();
      Iterator<Entry<String, Integer>> iterator = param.entrySet().iterator();
      while(iterator.hasNext()){
        Entry item =  iterator.next();
        sb.append(item.getKey()).append(" ").append("(").append(item.getValue()).append("次)").append("\n");
      }
      sb.setLength(sb.length() -1);
      return sb.toString();
  }


}
