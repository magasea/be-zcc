package com.wensheng.zcc.amc.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author chenwei on 1/3/19
 * @project zcc-backend
 */
public interface AmcExcelFileService {




  public List<String> handleMultiPartFile(MultipartFile multipartFile) throws Exception;

  public List<String> handleMultiPartFilePrecheck(MultipartFile multipartFile) throws Exception;

}
