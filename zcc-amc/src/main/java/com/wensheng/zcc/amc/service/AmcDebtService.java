package com.wensheng.zcc.amc.service;

/**
 * @author chenwei on 1/4/19
 * @project zcc-backend
 */
public interface AmcDebtService {

  public int  saveImageInfo(String ossName, String originName, Long debtId, String fileDesc, int imageClass);

}
