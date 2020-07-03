package com.wensheng.zcc.common.mq.kafka.module;

import lombok.Data;

@Data
public class SSOUserModDto {

  SSOUserDto amcUserDtoHis;
  SSOUserDto amcUserDtoCurr;
}
