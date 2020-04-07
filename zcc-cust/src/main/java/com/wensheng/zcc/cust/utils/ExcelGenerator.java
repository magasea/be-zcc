package com.wensheng.zcc.cust.utils;

import com.wensheng.zcc.cust.module.vo.CustTrdInfoExcelVo;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


@Service
@Slf4j
public class ExcelGenerator {

  @Value("${env.image-repo}")
  String fileBase;

    public  File customersToExcel(List<CustTrdInfoExcelVo> custTrdInfoExcelVos) throws IOException {
      File file = new File(fileBase);
      boolean dirCreated = file.mkdir();

      String[] COLUMNs = {"#", "投资人名称", "投资偏好类型", "偏好地区","债权本息总额(元)","交易总额(元)","联系方式","联系地址"};
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

          row.createCell(0).setCellValue(custTrdInfoExcelVo.getCustId());
          row.createCell(1).setCellValue(custTrdInfoExcelVo.getCustName());
          row.createCell(2).setCellValue(getStringFromMap(custTrdInfoExcelVo.getInvestType2Counts()));
          row.createCell(3).setCellValue(getStringFromMap(custTrdInfoExcelVo.getIntrestCities()));
          if(custTrdInfoExcelVo.getDebtTotalAmount() > 0){
            row.createCell(4).setCellValue(custTrdInfoExcelVo.getDebtTotalAmount());
          }else{
            log.error("The debtTotalAmount:{}  of custId:{} is not valid", custTrdInfoExcelVo.getDebtTotalAmount(),
                custTrdInfoExcelVo.getCustId());
            row.createCell(4);
          }


          row.getCell(4).setCellStyle(custCellStyle);

          if(custTrdInfoExcelVo.getTrdTotalAmount() > 0){
            row.createCell(5).setCellValue(custTrdInfoExcelVo.getTrdTotalAmount());
          }else{
            log.error("The trdTotalAmount:{}  of custId:{} is not valid", custTrdInfoExcelVo.getTrdTotalAmount(),
                custTrdInfoExcelVo.getCustId());
            row.createCell(5);
          }


          row.getCell(5).setCellStyle(custCellStyle);
          if(StringUtils.isEmpty(custTrdInfoExcelVo.getPhone()) || custTrdInfoExcelVo.getPhone().contains("-1;")){
            log.error("The phone info:{} of custId:{} is not valid", custTrdInfoExcelVo.getPhone(),
                custTrdInfoExcelVo.getCustId());
            row.createCell(6);
          }else{
            row.createCell(6).setCellValue(custTrdInfoExcelVo.getPhone());
          }
          row.createCell(7).setCellValue(custTrdInfoExcelVo.getAddress());

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
