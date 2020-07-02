package com.wensheng.zcc.cust.service.impl;

import com.wensheng.zcc.cust.dao.mysql.mapper.MailConfigNewCmpyMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.ext.CustTrdCmpyExtMapper;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcCmpycontactorExample;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.MailConfigNewCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.MailConfigNewCmpyExample;
import com.wensheng.zcc.cust.module.vo.CustTrdInfoExcelVo;
import com.wensheng.zcc.cust.service.BasicInfoService;
import com.wensheng.zcc.cust.service.CustMailConfigService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class CustMailConfigServiceImpl implements CustMailConfigService {

  @Value("${env.image-repo}")
  String fileBase;

  @Autowired
  JavaMailSenderImpl javaMailSender;

  @Autowired
  MailConfigNewCmpyMapper mailConfigNewCmpyMapper;

  @Autowired
  BasicInfoService basicInfoService;

  @Autowired
  CustTrdCmpyExtMapper custTrdCmpyExtMapper;

  @Value("${cust.syncUrls.cmpyLinkMould}")
  String cmpyLinkMould;

  @Override
  public MailConfigNewCmpy createCustMailConfig(MailConfigNewCmpy mailConfigNewCmpy) throws Exception {
    mailConfigNewCmpyMapper.insertSelective(mailConfigNewCmpy);
    return mailConfigNewCmpy;
  }

  @Override
  public void updateCustMailConfig(MailConfigNewCmpy mailConfigNewCmpy) throws Exception {
    mailConfigNewCmpyMapper.updateByPrimaryKeySelective(mailConfigNewCmpy);
  }

  @Override
  public List<MailConfigNewCmpy> getAllCustMailConfig() throws Exception {
    return mailConfigNewCmpyMapper.selectByExample(null);
  }

  @Override
  public MailConfigNewCmpy getCustMailConfigById(Long id) throws Exception {
    return mailConfigNewCmpyMapper.selectByPrimaryKey(id);
  }


  @Override
  public void sendMailOfNewCmpy(MailConfigNewCmpy mailConfigNewCmpy, Date today) {

    try {
      //根据配置信息生成临时文件
      boolean creatAttachment = creatAttachment(mailConfigNewCmpy, today);
      if(!creatAttachment){
        return;
      }
      //根据配置信息发送邮件
      sendMail(mailConfigNewCmpy);
      log.info("新增公司发邮件定时任务完成");
    } catch (IOException e) {
      log.error("根据配置信息生成临时文件异常e:{}",e);
    }catch (MessagingException e) {
      log.error("根据配置信息发送邮件异常e:{}",e);
    }

  }

  /**
   * 根据配置信息生成临时文件
   * @param mailConfigNewCmpy
   * @return
   */
  public boolean creatAttachment(MailConfigNewCmpy mailConfigNewCmpy,  Date today) throws IOException {
    File file = new File(fileBase);
    boolean dirCreated = file.mkdir();

    String[] COLUMNs = {"公司名称", "信用代码", "联系电话", "联系地址","年报电话","年报地址","链接"};

    //生成主题
    Map<String, String> provinceNameMap = basicInfoService.getProvinceNameMap();
    String provice = mailConfigNewCmpy.getProvince();
    String[] proviceArray = provice.split(";");
    StringBuffer sbProvice = new StringBuffer();

    for (int i = 0; i < proviceArray.length; i++) {
      if(sbProvice.length() > 0){
        sbProvice.append("、");
      }
      sbProvice.append(provinceNameMap.get(proviceArray[i]));
    }
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMdd");
    String dateString = simpleDateFormat.format(today);
    String subject= String.format("%s新增投资人数据%s",sbProvice.toString(),dateString);
    mailConfigNewCmpy.setSubject(subject);

    Calendar calendar = Calendar.getInstance();
    //1.过去七天
    calendar.setTime(today);
    calendar.add(Calendar.DATE, -7);
    //查询对应省的新增公司数据
    List<CustTrdCmpy>  newTrdCmpyList = custTrdCmpyExtMapper.selectNewCmpyByProvince(
                                            calendar.getTime(), today, Arrays.asList(proviceArray));
    if(newTrdCmpyList.size() == 0){
      log.info("暂无查询到新增公司信息，省份为proviceArray：{}", proviceArray);
      return false;
    }

    try(
        Workbook workbook = new XSSFWorkbook();
        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream(fileBase+File.separator+
            String.format("%s.xlsx", subject));
    ){
      CreationHelper createHelper = workbook.getCreationHelper();

      for (int i = 0; i < proviceArray.length; i++) {
        List<String> provinceList = new ArrayList<String>();
        provinceList.add(proviceArray[i]);
        List<CustTrdCmpy>  sheetTrdCmpyList = custTrdCmpyExtMapper.selectNewCmpyByProvince(
                                                          calendar.getTime(), today, provinceList);
        Sheet sheet = workbook.createSheet(provinceNameMap.get(proviceArray[i]));
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

        for (CustTrdCmpy custTrdCmpy : sheetTrdCmpyList) {
          Row row = sheet.createRow(rowIdx++);

          //"公司名称", "信用代码", "联系电话", "联系地址","年报电话","年报地址","链接"
          row.createCell(0).setCellValue(custTrdCmpy.getCmpyName());
          row.createCell(1).setCellValue(custTrdCmpy.getUniSocialCode());

          row.createCell(2).setCellValue(checkValue(custTrdCmpy.getCmpyPhone()));
          row.createCell(3).setCellValue(checkValue(custTrdCmpy.getCmpyAddr()));
          row.createCell(4).setCellValue(checkValue(custTrdCmpy.getAnnuReptPhone()));
          row.createCell(5).setCellValue(checkValue(custTrdCmpy.getAnnuReptAddr()));

          //链接
          String cmpyLink = String.format(cmpyLinkMould,custTrdCmpy.getId(),custTrdCmpy.getCmpyName());
          row.createCell(6).setCellValue(cmpyLink);
        }
      }

      workbook.write(fileOut);
    }
    return true;
  }

  /**
   * 交易字符串
   * @param value
   * @return
   */
  public String checkValue(String value){
    String[] illegalString = {"-1","-","暂无信息"};
    for (int i = 0; i <illegalString.length ; i++) {
      if(illegalString[i].equals(value)){
        return "";
      }
    }
    return value;
  }


  /**
   * 根据配置信息发送邮件
   * @param mailConfigNewCmpy
   */
  public void sendMail(MailConfigNewCmpy mailConfigNewCmpy) throws MessagingException {
    String[] to = mailConfigNewCmpy.getToMail().split(";");
    String[] cc = mailConfigNewCmpy.getCcMail().split(";");
    String subject = mailConfigNewCmpy.getSubject();
    javaMailSender.setUsername(mailConfigNewCmpy.getUserMail());
    javaMailSender.setPassword(mailConfigNewCmpy.getPassword());
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
    mimeMessageHelper.setFrom(mailConfigNewCmpy.getUserMail());
    mimeMessageHelper.setTo(to);
    mimeMessageHelper.setCc(cc);
    mimeMessageHelper.setSubject(subject);
    mimeMessageHelper.setText(mailConfigNewCmpy.getText());

    //文件路径  spring的FileSystemResource,使用绝对路径访问文件资源
    FileSystemResource file = new FileSystemResource(new File(fileBase+File.separator+String.format("%s.xlsx", subject)));
    mimeMessageHelper.addAttachment(String.format("%s.xlsx",subject), file);
    javaMailSender.send(mimeMessage);//发送
  }


  @Scheduled(cron = "${spring.task.scheduling.sendNewCmpyMailScheduled}")
  public void sendNewCmpyMailScheduled(){
    log.info("新增公司发邮件定时任务开始");
    //搜索条件
    Date today = new Date();
    Calendar c = Calendar.getInstance();
    c.setTime(today);
    int weekday = c.get(Calendar.DAY_OF_WEEK);
    int hour = c.get(Calendar.HOUR_OF_DAY);
    weekday = weekday-1;
    if(weekday == 0){
      weekday =7;
    }

    //查询数据库
    MailConfigNewCmpyExample mailConfigNewCmpyExample = new MailConfigNewCmpyExample();
    mailConfigNewCmpyExample.createCriteria().andSendWeekEqualTo(weekday+"")
                      .andSendHourEqualTo(hour+"").andStatusEqualTo(1);
    List<MailConfigNewCmpy> configNewCmpieList = mailConfigNewCmpyMapper.selectByExample(mailConfigNewCmpyExample);

    if(configNewCmpieList.size() == 0){
      log.info("暂无配置发送新增公司邮件，星期weekday：{}，时间hour：{}", weekday, hour);
      return;
    }

    //发送邮件
    for (MailConfigNewCmpy mailConfigNewCmpy: configNewCmpieList){
      sendMailOfNewCmpy(mailConfigNewCmpy,today);
    }
  }

}

