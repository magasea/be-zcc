package com.wensheng.zcc.common.mq.kafka;

import javax.validation.executable.ValidateOnExecution;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author chenwei on 4/1/19
 * @project miniapp-backend
 */
public class KafkaParams {

  @Value("${kafka.topic-prefix}")
  static String topicPrefix;

  public static final String MQ_TOPIC_WECHAT_USERLOCATION = String.format("%s_mq_topic_wechat_userlocation",
      topicPrefix);

}
