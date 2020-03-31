package com.wensheng.zcc.amc.service;

import com.wensheng.zcc.amc.module.dao.helper.ImagePathClassEnum;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author chenwei on 1/3/19
 * @project zcc-backend
 */
public interface AmcExcelFileService {




  public String handleMultiPartFile(MultipartFile multipartFile) throws Exception;



}
