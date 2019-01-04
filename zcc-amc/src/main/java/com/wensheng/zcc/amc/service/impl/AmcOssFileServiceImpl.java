package com.wensheng.zcc.amc.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.wensheng.zcc.amc.service.AmcOssFileService;
import java.io.File;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author chenwei on 1/3/19
 * @project zcc-backend
 */
@Service
public class AmcOssFileServiceImpl implements AmcOssFileService {

  @Value("${project.params.debt_image_path}")
  String debtImageRepo;

  @Value("${project.params.asset_image_path}")
  String assetImageRepo;

  /**
   * oss_end_point: oss-cn-beijing.aliyuncs.com
   *     oss_key_id: LTAIr4FuhRp7SciF
   *     oss_key_secret: jTtg1M1J39fTJewIwNDNnz09rGeBzC
   *     bucket_name: ws-zcc
   */

  @Value("${project.params.oss_end_point}")
  String ossEndPoint;

  @Value("${project.params.oss_key_id}")
  String ossKeyId;

  @Value("${project.params.oss_key_secret}")
  String ossKeySecret;

  @Value("${project.params.bucket_name}")
  String bucketName;

  OSSClient ossClient;





  @PostConstruct
  private void initObjects() {
    DefaultCredentialProvider defaultCredentialProvider = new DefaultCredentialProvider(ossKeyId, ossKeySecret);

    ossClient = new OSSClient(ossEndPoint, defaultCredentialProvider, null);
  }


  @Override
  public String handleFile2Oss(String filePath) throws Exception {
    String fileName = UUID.fromString(filePath).toString();
    File fileUpload = new File(filePath);
    ossClient.putObject(bucketName, fileName, fileUpload);
    return fileName;
  }

  @Override
  public String handleMultiPartImage(MultipartFile multipartFile, Long id, String type) throws Exception {
    File targetFile = null;
    switch (type){
      case "debt":
        targetFile =
            new File(debtImageRepo+File.separator + id+ File.pathSeparator +multipartFile.getOriginalFilename());
        break;
      case "asset":
        targetFile =
            new File(assetImageRepo+File.separator + id+ File.pathSeparator +multipartFile.getOriginalFilename());
        break;
        default:
          throw new Exception("type"+type + "is not debt or asset");
    }

    multipartFile.transferTo(targetFile);


    return targetFile.getCanonicalPath();
  }


}
