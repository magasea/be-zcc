package com.wensheng.zcc.amc.service;

public interface MailService {

  void sendMail(String text, String targetEmailAdd, String srcEmailAdd);

}
