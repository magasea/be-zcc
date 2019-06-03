package com.wensheng.zcc.cust.utils;

import com.wensheng.zcc.cust.module.vo.CustTrdInfoExcelVo;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelGenerator {



    public static ByteArrayInputStream customersToExcel(List<CustTrdInfoExcelVo> custTrdInfoExcelVos) throws IOException {
      String[] COLUMNs = {"#", "投资人名称", "投资偏好类型", "偏好地区","交易总额(元)","联系方式","联系地址"};
      try(
          Workbook workbook = new XSSFWorkbook();
          ByteArrayOutputStream out = new ByteArrayOutputStream();
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
          row.createCell(4).setCellValue(custTrdInfoExcelVo.getTrdTotalAmount());
          row.getCell(4).setCellStyle(custCellStyle);
          row.createCell(5).setCellValue(custTrdInfoExcelVo.getPhone());
          row.createCell(6).setCellValue(custTrdInfoExcelVo.getAddress());

        }

        workbook.write(out);
        return new ByteArrayInputStream(out.toByteArray());
      }

  }

  private static String getStringFromMap(Map<String, Integer> param){
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
