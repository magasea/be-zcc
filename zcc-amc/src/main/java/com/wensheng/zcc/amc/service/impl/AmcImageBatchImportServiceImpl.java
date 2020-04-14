package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.service.AmcAssetService;
import com.wensheng.zcc.amc.service.AmcDebtService;
import com.wensheng.zcc.amc.service.AmcImageBatchImportService;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

@Service
@Slf4j
public class AmcImageBatchImportServiceImpl implements AmcImageBatchImportService {

    @Value("${project.params.debt_image_path}")
    String debtImageRepo;

    static final String tempFolder = "unzip_temp";

    @Autowired
    AmcDebtService amcDebtService;



    @Override
    public boolean importBatchImages(MultipartFile multipartFile) throws Exception {

        String targetFolder = String.format("%s%s%s%s",debtImageRepo,File.separator,tempFolder,File.separator);
        File dir = new File(targetFolder);
        // create output directory if it doesn't exist
        if(!dir.exists()) {
            dir.mkdirs();
        }else{
            Path pathToBeDeleted = Paths.get(targetFolder);

            Files.walk(pathToBeDeleted)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }

        File targetFile = null;
        targetFile =
                new File(String.format("%s%s",targetFolder,multipartFile.getOriginalFilename()));

        multipartFile.transferTo(targetFile);

        //Open the file
        try (ArchiveInputStream i = new ArchiveStreamFactory()
                .createArchiveInputStream(ArchiveStreamFactory.ZIP, new FileInputStream(targetFile))) {
            ArchiveEntry entry = null;
            while ((entry = i.getNextEntry()) != null) {
                if (!i.canReadEntryData(entry)) {
                    // log something?
                    continue;
                }
                String name = String.format("%s%s%s",targetFolder, File.separator ,entry);
                File f = new File(name);
                if (entry.isDirectory()) {
                    if (!f.isDirectory() && !f.mkdirs()) {
                        throw new IOException("failed to create directory " + f);
                    }
                } else {
                    File parent = f.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("failed to create directory " + parent);
                    }
                    try (OutputStream o = Files.newOutputStream(f.toPath())) {
                        IOUtils.copy(i, o);
                    }
                }
            }
        }
        traverseTargetFolder(targetFolder);
        return true;

    }

    public void traverseTargetFolder(String targetPath) throws Exception {
        String targetFolder = String.format("%s%s%s%s",debtImageRepo,File.separator,tempFolder,File.separator);
        File dir = new File(targetFolder);
        if(!dir.exists()){
            throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.INVALID_FOLDER);
        }
        traverseFile(dir.listFiles());
    }

    private void traverseFile(File[] files) throws IOException {
        for (File file : files) {
            if (file.isDirectory()) {

                String debtTitle =  file.getParent();;
                String assetTitle = file.getName();
                //begin handle asset images
                System.out.println("Directory: " + file.getName());
                Files.list(file.toPath()).sorted().forEach( item -> uploadAssetImage(item, debtTitle, assetTitle));

            } else {
                System.out.println("File: " + file.getName());
            }
        }
    }
    private void uploadAssetImage(Path itemAssetImage, String debtTitle, String assetTitle) {
//        amcDebtService.
        log.info("itemAssetImage:{}, debtTitle:{}, assetTitle:{}", itemAssetImage, debtTitle, assetTitle);
    }

}
