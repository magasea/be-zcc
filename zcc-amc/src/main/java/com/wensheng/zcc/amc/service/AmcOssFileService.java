package com.wensheng.zcc.amc.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author chenwei on 1/3/19
 * @project zcc-backend
 */
public interface AmcOssFileService {

  public String handleFile2Oss(String filePath) throws Exception;


  public String handleMultiPartImage(MultipartFile multipartFile, Long id, String type) throws Exception;


}
