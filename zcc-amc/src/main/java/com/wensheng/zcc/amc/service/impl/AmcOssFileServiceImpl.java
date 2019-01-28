package com.wensheng.zcc.amc.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.PutObjectResult;
import com.wensheng.zcc.amc.module.dao.mongo.entity.DebtImage;
import com.wensheng.zcc.amc.service.AmcOssFileService;
import com.wensheng.zcc.amc.utils.ImageUtils;
import java.io.File;
import javax.annotation.PostConstruct;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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

  String ossFilePathBase;





  @PostConstruct
  private void initObjects() {
    DefaultCredentialProvider defaultCredentialProvider = new DefaultCredentialProvider(ossKeyId, ossKeySecret);

    ossClient = new OSSClient(ossEndPoint, defaultCredentialProvider, null);
    StringBuilder sb = new StringBuilder("http://");
    ossFilePathBase = sb.append(bucketName).append(".").append(ossEndPoint).append("/").toString();
  }


  @Override
  public String handleFile2Oss(String filePath, String prePath) throws Exception {
    String ext = ImageUtils.getImageType(filePath);
    if(ext.equals(ImageUtils.UNKNOWNTYPE)){
      ext = FilenameUtils.getExtension(filePath);
    }
    String checkSum = ImageUtils.getCheckSum(filePath);
    StringBuilder sbOssFileName = new StringBuilder(prePath).append(checkSum);
    sbOssFileName.append(".").append(ext);
    File fileUpload = new File(filePath);

    PutObjectResult putObjectResult = ossClient.putObject(bucketName, sbOssFileName.toString(), fileUpload);
    return ossFilePathBase+sbOssFileName;
  }

  @Override
  public void delFileInOss(String ossPath) throws Exception {

    if(ossPath.contains(ossFilePathBase)){
      ossClient.deleteObject(bucketName, ossPath.substring(ossFilePathBase.length() -1));
    }


  }


  @Override
  public String handleMultiPartFile(MultipartFile multipartFile, Long id, String type) throws Exception {
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
