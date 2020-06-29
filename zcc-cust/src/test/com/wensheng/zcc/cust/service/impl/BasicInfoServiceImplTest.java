package com.wensheng.zcc.cust.service.impl;

import com.wensheng.zcc.cust.dao.mysql.mapper.MailConfigNewCmpyMapper;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.MailConfigNewCmpy;
import com.wensheng.zcc.cust.service.BasicInfoService;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration
@ActiveProfiles(value = "dev")
@Slf4j
public class BasicInfoServiceImplTest {

@Autowired
BasicInfoService basicInfoService;

@Autowired
JavaMailSenderImpl javaMailSender;

@Autowired
MailConfigNewCmpyMapper mailConfigNewCmpyMapper;

@Autowired
CustMailConfigServiceImpl custMailConfigService;

  @Test
  public void getAmcUserPrivMap() {
    Map<String, Integer> userPrivMap = basicInfoService.getAmcUserPrivMap();
    log.info("{}",userPrivMap);
    Map<Integer, List<String>> userProvsMap = basicInfoService.getAmcUserProvsMap();
    log.info("{}",userProvsMap);

  }

  @Test
  public void sendSimpleMail(){
    try{
      String[] to = new String[]{"1635057818@qq.com","chenwei@wenshengamc.com","wangshouzheng@wenshengamc.com"};
      String[] cc = new String[]{"yeling@wenshengamc.com","xixiaojie@wenshengamc.com"};

      javaMailSender.setUsername("yeling@wenshengamc.com");
      javaMailSender.setPassword("P@ssw0rd");
      MimeMessage mimeMessage = javaMailSender.createMimeMessage();
      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
      mimeMessageHelper.setFrom("yeling@wenshengamc.com");
      mimeMessageHelper.setTo(to);
      mimeMessageHelper.setCc(cc);
      mimeMessageHelper.setSubject("主题");
      mimeMessageHelper.setText("内容");

      //文件路径  spring的FileSystemResource,使用绝对路径访问文件资源
      FileSystemResource file = new FileSystemResource(new File("D:\\file\\test.doc"));
      mimeMessageHelper.addAttachment("test.doc", file);
      javaMailSender.send(mimeMessage);//发送
    }catch(Exception e){
      log.error(""+e);
      log.error("发送失败",e.getMessage());
    }
  }

  @Test
  public void sendMail() throws Exception {
    MailConfigNewCmpy mailConfigNewCmpy=mailConfigNewCmpyMapper.selectByPrimaryKey(1l);
    custMailConfigService.sendMailOfNewCmpy(mailConfigNewCmpy,new Date());

  }

}
