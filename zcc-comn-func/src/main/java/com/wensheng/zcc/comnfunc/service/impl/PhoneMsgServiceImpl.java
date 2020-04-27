package com.wensheng.zcc.comnfunc.service.impl;

import com.wensheng.zcc.comnfunc.service.PhoneMsgService;
import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author chenwei on 2/21/19
 * @project zcc-backend
 */
@Service
@Slf4j
public class PhoneMsgServiceImpl implements PhoneMsgService {



  private static final Long timeOut = 1200L;


  @Value("${message.commond}")
  String commond;
  @Value("${message.multiCommond}")
  String multiCommond;
  @Value("${message.spid}")
  String spid;
  @Value("${message.sppassword}")
  String sppassword;
  @Value("${message.url}")
  String url;

  private RestTemplate restTemplate = new RestTemplate();

  @Override
  public String generateVerificationCodeToPhone(String phoneNum, String vcode) {

    String da = String.format("86%s",phoneNum);
    String dc = "15";//GBK
    String orgMessage ="验证码为：{0}，{1}分钟内有效。严防诈骗，请勿泄露验证码。";
    String msg = orgMessage.replace("{0}",vcode).replace("{1}",""+timeOut/60);
//        println msg
    String message = new String(Hex.encodeHex(msg.getBytes(Charset.forName("GBK"))));
    StringBuilder smsBuilder = new StringBuilder();
    smsBuilder.append(url)
        .append("?command=").append(commond)
        .append("&spid=").append(spid)
        .append("&sppassword=").append(sppassword)
        .append("&da=").append(da)
        .append("&dc=").append(dc)
        .append("&sm=").append(message);
//        println smsBuilder.toString()
    String res = null;
    ResponseEntity<String> resp = restTemplate.getForEntity(smsBuilder.toString(), String.class);
    if(resp.getBody().contains("errorcode=0")){
      return vcode;
    }else{
      log.error(String.format("call message service failed with:%s", resp.getBody()));
      return resp.getBody();
    }
  }




}
