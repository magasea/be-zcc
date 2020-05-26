package com.wensheng.zcc.amc.service;

import com.wensheng.zcc.amc.module.vo.DebtBatchImportErr;
import com.wensheng.zcc.common.params.sso.SSOAmcUser;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * @author chenwei on 1/3/19
 * @project zcc-backend
 */
public interface AmcExcelFileService {




  public List<String> importExcel4DebtAsset(MultipartFile multipartFile) throws Exception;

  public List<DebtBatchImportErr> handleMultiPartFilePrecheck(MultipartFile multipartFile) throws Exception;
  public void handleMultiPartFilePatchDebtClue(MultipartFile multipartFile) throws Exception;

  public void handleMultiPartFilePatchDebtCurt(MultipartFile multipartFile) throws Exception;

  public File handleMultiPartFileCurtInfo(MultipartFile multipartFile) throws Exception;

  SSOAmcUser getAmcContactorByName(String name);

}
