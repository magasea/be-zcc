package com.wensheng.zcc.amc.service;

import com.wensheng.zcc.amc.module.dao.helper.ImagePathClassEnum;
import com.wensheng.zcc.amc.module.dao.mongo.entity.DebtImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author chenwei on 1/3/19
 * @project zcc-backend
 */
public interface AmcOssFileService {

  public String handleFile2Oss(String filePath, String prePath) throws Exception;


  public String handleMultiPartFile(MultipartFile multipartFile, Long id, String ImagePathClassEnumType) throws Exception;

  public String handleMultiPartFile4Base64(MultipartFile multipartFile) throws Exception;

  public void delFileInOss(String ossPath) throws Exception;

  public void listFilesOnOss(ImagePathClassEnum imagePathClassEnum);

  public void backUpOssFiles();

  String img2Base64(String filePath);

  String downloadFileFromUrl(String srcUrl, String resizeParam, String baseRepo)
      throws Exception;

}
