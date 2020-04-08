package com.wensheng.zcc.amc.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * @author chenwei on 1/3/19
 * @project zcc-backend
 */
public interface AmcImageBatchImportService {




  public boolean importBatchImages(MultipartFile multipartFile) throws Exception;



}
