package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.service.AmcOssFileService;
import java.io.File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author chenwei on 1/3/19
 * @project zcc-backend
 */
@Service
public class AmcOssFileServiceImpl implements AmcOssFileService {

  @Value("${project.params.debt_image_path}")
  String debtImageRepo;

  @Value("${project.params.debt_image_path}")

  @Override
  public int handleFile2Oss(String filePath) throws Exception {
    return 0;
  }

  @Override
  public String handleMultiPartImage(MultipartFile multipartFile, Long id, String type) throws Exception {

    File targetFile =
        new File(debtImageRepo+File.separator +type+ File.pathSeparator + id+ File.pathSeparator +multipartFile.getOriginalFilename());

    multipartFile.transferTo(targetFile);

    return targetFile.getCanonicalPath();
  }


}
