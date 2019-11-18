package com.wensheng.zcc.amc.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.CopyObjectRequest;
import com.aliyun.oss.model.CopyObjectResult;
import com.aliyun.oss.model.DownloadFileRequest;
import com.aliyun.oss.model.DownloadFileResult;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.PutObjectResult;
import com.wensheng.zcc.amc.module.dao.helper.ImagePathClassEnum;
import com.wensheng.zcc.amc.service.AmcOssFileService;
import com.wensheng.zcc.amc.utils.ImageUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import com.wensheng.zcc.common.utils.SystemUtils;
import java.io.File;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author chenwei on 1/3/19
 * @project zcc-backend
 */
@Service
@Slf4j
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

  @Value("${project.params.oss_end_point_bak}")
  String ossEndPointBak;

  @Value("${project.params.bucket_name_bak}")
  String bucketNameBak;

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
  public  synchronized String  handleFile2Oss(String filePath, String prePath) throws Exception {
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
      String key = ossPath.substring(ossFilePathBase.length());
      log.info(String.format("delete file with key:%s", key));
      ossClient.deleteObject(bucketName, key);
    }


  }

  @Override
  public void listFilesOnOss(ImagePathClassEnum imagePathClassEnum) {

    ObjectListing objectListing = ossClient.listObjects(bucketName, imagePathClassEnum.getName());
    List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
    for (OSSObjectSummary s : sums) {
      System.out.println("\t" + s.getKey());
    }

  }

  @Override
  public void backUpOssFiles() {
    ObjectListing objectListing = ossClient.listObjects(bucketName);
    List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
    StringBuilder sb = new StringBuilder(debtImageRepo).append(File.separatorChar).append("back").append(File.separatorChar);
    for (OSSObjectSummary s : sums) {
      log.info("before copy:{}",s.getKey());
      DownloadFileRequest downloadFileRequest = new DownloadFileRequest(bucketName, s.getKey(), String.format("%s%s",
       sb, s.getKey()),1000000   );
      try {
        DownloadFileResult downloadFileResult = ossClient.downloadFile(downloadFileRequest);

      } catch (Throwable throwable) {
        throwable.printStackTrace();
      }


    }

  }


  @Override
  public String handleMultiPartFile(MultipartFile multipartFile, Long id, String type) throws Exception {
    File targetFile = null;
    switch (type){
      case "debt":
        SystemUtils.checkAndMakeDir(debtImageRepo+File.separator + id);
        targetFile =
            new File(debtImageRepo+File.separator + id+ File.separatorChar +multipartFile.getOriginalFilename());
        break;
      case "asset":
        SystemUtils.checkAndMakeDir(assetImageRepo+File.separator + id);
        targetFile =
            new File(assetImageRepo+File.separator + id+ File.separatorChar +multipartFile.getOriginalFilename());
        break;
        default:
          throw new Exception("type"+type + "is not debt or asset");
    }

    multipartFile.transferTo(targetFile);


    return targetFile.getCanonicalPath();
  }





}
