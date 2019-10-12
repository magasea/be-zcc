package com.wensheng.zcc.common.mq.kafka;

import javax.validation.executable.ValidateOnExecution;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author chenwei on 4/1/19
 * @project miniapp-backend
 */
public class KafkaParams {


  public static final String MQ_TOPIC_WECHAT_USERLOCATION = "topic_wechat_userLocation";
  public static final String MQ_TOPIC_AMC_USEROPER = "topic_amc_userOper";
  public static final String MQ_TOPIC_WECHAT_USERCREATE = "topic_wechat_usercreate";
  public static final String MQ_TOPIC_SSO_USERCHANGED = "topic_sso_userchanged";


}
