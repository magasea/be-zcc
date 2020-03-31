package com.wensheng.zcc.common.mq.kafka.module;

import lombok.Data;

@Data
public class AmcUserOpInfo {

    Long userId;
    String method;
    Object operObj;
    Long opTime;

}
