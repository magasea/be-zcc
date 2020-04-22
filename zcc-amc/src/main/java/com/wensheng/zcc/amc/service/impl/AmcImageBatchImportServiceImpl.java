package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.module.dao.helper.ImageClassEnum;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAsset;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class AmcImageBatchImportServiceImpl implements AmcImageBatchImportService {

    @Value("${project.params.debt_image_path}")
    String debtImageRepo;

    static final String tempFolder = "unzip_temp";

    @Autowired
    AmcDebtService amcDebtService;

    @Autowired
    AmcAssetService amcAssetService;



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

    @Override
    public List<String> imageBatchCheck(List<String> zipImagesPaths) {
        List<String> errorInfo = new ArrayList<>();
        for(String path: zipImagesPaths){
            String[] paths = path.split("\\/");
            if(paths.length == 2){
                // it is debt and image
                String debtTitle = paths[0];
                try{
                    amcDebtService.getDebtIdByTitle(debtTitle);
                }catch (Exception ex){
                    log.error("error:", ex);
                    errorInfo.add(String.format("%s 中 %s 债权不存在",path, debtTitle));
                }

            }else if(paths.length == 3){
                // it is debt and asset and image
                String debtTitle = paths[0];
                String assetTitle = paths[1];
                try{
                   List<AmcAsset> amcAssets =  amcAssetService.getAssetByDebtAndAssetTitle(debtTitle, assetTitle);
                   if(CollectionUtils.isEmpty(amcAssets)){
                       errorInfo.add(String.format("%s 中 %s 资产不存在",path, assetTitle));
                   }else if(amcAssets.size() > 1){
                       errorInfo.add(String.format("%s 中 %s 资产有重复",path, assetTitle));
                   }
                }catch (Exception ex){
                    log.error("error:", ex);
                    errorInfo.add(String.format("%s 中 %s 债权不存在",path, debtTitle));
                }
            }
        }
        return errorInfo;
    }

    public void traverseTargetFolder(String targetPath) throws Exception {
        String targetFolder = String.format("%s%s%s%s",debtImageRepo,File.separator,tempFolder,File.separator);
        File dir = new File(targetFolder);
        if(!dir.exists()){
            throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.INVALID_FOLDER);
        }
        traverseFile(dir.listFiles());
    }

    private void traverseFile(File[] files) throws Exception {
        for (File debtLevelFile : files) {
            if (debtLevelFile.isDirectory()) {
                if(debtLevelFile.getName().startsWith("_") || debtLevelFile.getName().startsWith(".")){
                    continue;
                }

                String debtTitle =  debtLevelFile.getName();

                File[] assetsFiles = debtLevelFile.listFiles();
                for(File assetLevelFile: assetsFiles){
                    if(assetLevelFile.isDirectory()){
                        uploadAssetImage(debtLevelFile, assetLevelFile);
                    }else{
                        if(assetLevelFile.getName().startsWith(".")){
                            continue;
                        }
                        uploadDebtImage(debtLevelFile, assetLevelFile);
                    }


                }
            }
        }
    }
    private void uploadAssetImage(File debtDir, File assetDir) throws Exception {



        log.info("debtDir:{}, assetDir:{}",  debtDir.getAbsolutePath(), assetDir.getAbsolutePath());
        //1. check asset with debtTitle and assetTitle
        List<AmcAsset> assetList = amcAssetService.getAssetByDebtAndAssetTitle(debtDir.getName(), assetDir.getName());
        if(CollectionUtils.isEmpty(assetList)){
            log.error("There is no such asset with title:{} under debt with title:{}", assetDir.getName(), debtDir.getName());
            return;
        }else if(assetList.size() > 1){
            throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.DUPLICATE_ITEM_ERROR,
                    String.format("There is no such asset with title:%s under debt with title:%s", assetDir.getName(), debtDir.getName()));
        }
        //2. get oss prepath
        String ossPrePath = amcAssetService.getAssetOssPrepath(assetList.get(0).getId());
        //3. upload image
        for(File assetImage : assetDir.listFiles()){
            if(assetImage.getName().startsWith(".")||assetImage.isDirectory()|| assetImage.getName().startsWith("_")){
                continue;
            }
            if(assetImage.getName().startsWith("1")){
                amcAssetService.uploadAssetImage(assetImage.getAbsolutePath(), ossPrePath, ImageClassEnum.MAIN.getId(), assetList.get(0).getId(), null);
            }
        }


    }

    private void uploadDebtImage( File debtDir, File debtImage) throws Exception {
//        amcDebtService.
        log.info("debtDir:{}, debtImage:{}", debtDir.getAbsolutePath(), debtImage.getAbsolutePath());

        //1. check debtTitle get debtId
        Long debtId = amcDebtService.getDebtIdByTitle(debtDir.getName());
        //2. get oss prepath
        String prePath = amcDebtService.getDebtOssPrePath(debtId);
        //3. upload image
        amcDebtService.uploadDebtImage(debtImage.getAbsolutePath(), prePath, debtId, null);
    }

}
