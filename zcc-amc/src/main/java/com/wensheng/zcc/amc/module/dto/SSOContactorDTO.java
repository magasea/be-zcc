package com.wensheng.zcc.amc.module.dto;

import lombok.Data;

@Data
public class SSOContactorDTO {
  String contactorName;
  String phoneNum;
  Long ssoUserId;
  boolean found = false;
}
