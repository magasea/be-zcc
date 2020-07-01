package com.wensheng.zcc.cust.controller;

import com.wensheng.zcc.cust.config.aop.AddTraceLogId;
import com.wensheng.zcc.cust.dao.mysql.mapper.MailConfigNewCmpyMapper;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.MailConfigNewCmpy;
import com.wensheng.zcc.cust.service.CustMailConfigService;
import com.wensheng.zcc.cust.service.impl.CustMailConfigServiceImpl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@Slf4j
@AddTraceLogId
@RequestMapping("/amc/cust/CustMailConfig")
public class CustMailConfigController {

  @Autowired
  CustMailConfigService custMailConfigService;

  @Autowired
  MailConfigNewCmpyMapper mailConfigNewCmpyMapper;

  @RequestMapping(value = "/createCustMailConfig", method = RequestMethod.POST)
  @ResponseBody
  public MailConfigNewCmpy createCustMailConfig(@RequestBody MailConfigNewCmpy mailConfigNewCmpy) throws Exception {
    return custMailConfigService.createCustMailConfig(mailConfigNewCmpy);
  }

  @RequestMapping(value = "/updateCustMailConfig", method = RequestMethod.POST)
  @ResponseBody
  public void updateCustMailConfig(@RequestBody MailConfigNewCmpy mailConfigNewCmpy) throws Exception {
    custMailConfigService.updateCustMailConfig(mailConfigNewCmpy);
  }

  @RequestMapping(value = "/getAllCustMailConfig", method = RequestMethod.POST)
  @ResponseBody
  public List<MailConfigNewCmpy> getAllCustMailConfig() throws Exception {
    return custMailConfigService.getAllCustMailConfig();
  }

  @RequestMapping(value = "/getCustMailConfigById", method = RequestMethod.POST)
  @ResponseBody
  public MailConfigNewCmpy getCustMailConfigById(@RequestParam Long id) throws Exception {
    return custMailConfigService.getCustMailConfigById(id);
  }

  @RequestMapping(value = "/sendMailById", method = RequestMethod.POST)
  @ResponseBody
  public void sendMailById(@RequestParam Long mailConfigId, @RequestParam String toDayString)
      throws ParseException {
    MailConfigNewCmpy mailConfigNewCmpy = mailConfigNewCmpyMapper.selectByPrimaryKey(mailConfigId);
    if(mailConfigNewCmpy.getStatus() == 0){
      log.error("该配置已停用mailConfigId：",mailConfigId);
      return;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    Date today = sdf.parse(toDayString);
    custMailConfigService.sendMailOfNewCmpy(mailConfigNewCmpy, today);
  }

}
