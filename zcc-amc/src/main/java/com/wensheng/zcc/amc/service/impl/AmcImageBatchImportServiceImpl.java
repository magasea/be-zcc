package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.service.AmcImageBatchImportService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class AmcImageBatchImportServiceImpl implements AmcImageBatchImportService {

    @Value("${project.params.debt_image_path}")
    String debtImageRepo;

    @Override
    public boolean importBatchImages(MultipartFile multipartFile) throws Exception {

        File targetFile = null;
        targetFile =
                new File(debtImageRepo+File.separator  +multipartFile.getOriginalFilename());


        multipartFile.transferTo(targetFile);

        return false;
    }
}
