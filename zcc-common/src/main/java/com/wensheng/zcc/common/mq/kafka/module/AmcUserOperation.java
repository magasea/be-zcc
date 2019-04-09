package com.wensheng.zcc.common.mq.kafka.module;

import java.time.LocalDateTime;
import lombok.Data;
import org.apache.tomcat.jni.Local;

/**
 * @author chenwei on 4/3/19
 * @project miniapp-backend
 */
@Data
public class AmcUserOperation<T> {
  Long userId;
  String userName;
  Integer actionId;
  T param;
  LocalDateTime dateTime;

}
