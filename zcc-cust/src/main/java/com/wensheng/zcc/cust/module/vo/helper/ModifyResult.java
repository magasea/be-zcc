package com.wensheng.zcc.cust.module.vo.helper;

import java.util.List;
import lombok.Data;

@Data
public class ModifyResult<T> {

  /**
   * 调用是否成功
   */
  private boolean success;

  /**
   * 错误码
   */
  private String errCode;

  /**
   * 调用重复的id
   */
  private List<Long> idList;

  /**
   * result
   */
  private T result;
  public ModifyResult(){}

  public ModifyResult(T result){
    this.result=result;
    this.success=true;
  }

}
