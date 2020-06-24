package com.wensheng.zcc.cust.service;


import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.MailConfigNewCmpy;

public interface CustMailConfigService {

  /**
   * 新建发送邮件配置
   */
  void createCustMailConfig(MailConfigNewCmpy mailConfigNewCmpy) throws Exception;

  /**
   * 修改发送邮件配置
   */
  void updateCustMailConfig(MailConfigNewCmpy mailConfigNewCmpy) throws Exception;

  /**
   * 查询所有邮件配置
   */
  void getAllCustMailConfig() throws Exception;

  void sendMailOfNewCmpy(MailConfigNewCmpy mailConfigNewCmpy);
}
