package com.wensheng.zcc.cust.module.vo.helper;

import java.util.List;
import lombok.Data;

@Data
public class MergeCustRestult {

  /**
   * 调用是否成功
   */
  private boolean success;

  /**
   * 错误码
   */
  private String errCode;

  /**
   *  固话号
   */
  private String phoneUpdate;

  /**
   * 手机号
   */
  private String mobileUpdate;

}
