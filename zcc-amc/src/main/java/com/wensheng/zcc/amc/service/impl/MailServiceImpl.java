package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.service.MailService;
import java.io.File;
import javax.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field.Str;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class MailServiceImpl implements MailService {

  @Autowired
  JavaMailSenderImpl javaMailSender;


  @Value("${spring.mail.sso-user-change.targetMail}")
  String targetMail;

  @Value("${spring.mail.sso-user-change.ccMail}")
  String ccMail;

  @Value("${spring.mail.sso-user-change.subject}")
  String subject;

  @Override
  public void sendMail(String text, String targetEmailAdd, String srcEmailAdd) {

    if(StringUtils.isEmpty(targetEmailAdd)){
      targetEmailAdd = targetMail;
    }


    try{

      javaMailSender.setUsername("chenwei@wenshengamc.com");
      javaMailSender.setPassword("Wenhua12345");
      MimeMessage mimeMessage = javaMailSender.createMimeMessage();
      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
      if(StringUtils.isEmpty(srcEmailAdd)){
        mimeMessageHelper.setFrom("chenwei@wenshengamc.com");
      }else{
        mimeMessageHelper.setFrom(srcEmailAdd);
      }
      mimeMessageHelper.setTo(targetEmailAdd.split(","));
      if(!StringUtils.isEmpty(ccMail)){
        mimeMessageHelper.setCc(ccMail.split(","));
      }
      if(!StringUtils.isEmpty(subject)){
        mimeMessageHelper.setSubject(subject);
      }
      mimeMessageHelper.setText(text);

      javaMailSender.send(mimeMessage);//发送
    }catch(Exception e){
      log.error(""+e);
      log.error("发送失败",e.getMessage());
    }

  }
}
