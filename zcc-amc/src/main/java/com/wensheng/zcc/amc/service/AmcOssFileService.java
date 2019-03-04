package com.wensheng.zcc.amc.service;

import com.wensheng.zcc.amc.module.dao.helper.ImagePathClassEnum;
import com.wensheng.zcc.amc.module.dao.mongo.entity.DebtImage;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author chenwei on 1/3/19
 * @project zcc-backend
 */
public interface AmcOssFileService {

  public String handleFile2Oss(String filePath, String prePath) throws Exception;


  public String handleMultiPartFile(MultipartFile multipartFile, Long id, String type) throws Exception;

  public void delFileInOss(String ossPath) throws Exception;

  public void listFilesOnOss(ImagePathClassEnum imagePathClassEnum);

}
