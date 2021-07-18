package com.uploads.service;

import com.uploads.domain.UploadFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class FileUploader {

    /**
     *  환경변수로 설정한 저장경로 Get
     */
    @Value("${file.dir}")
    private String dirPath;

    public String getFullPath(String uploadFileName) {
        return dirPath + uploadFileName;
    }

    /**
     * 단건 파일 저장
     */
    public UploadFile saveFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) return null;

        String uploadFileName = multipartFile.getOriginalFilename();
        String saveFileName = createSaveFileName(uploadFileName);

        multipartFile.transferTo(new File(getFullPath(saveFileName)));

        log.info("FileUploader saveFileName = {}", saveFileName);
        return new UploadFile(uploadFileName, saveFileName);
    }

    /**
     * multiple 파일 저장
     */
    public List<UploadFile> saveFileList(List<MultipartFile> multipartFileList) throws IOException {
        List<UploadFile> uploadFileList = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFileList) {

            String uploadFileName = multipartFile.getOriginalFilename();
            String saveFileName = createSaveFileName(uploadFileName);

            multipartFile.transferTo(new File(getFullPath(saveFileName)));

            log.info("FileUploader saveFileName = {}", saveFileName);

            uploadFileList.add(new UploadFile(uploadFileName, saveFileName));
        }
        return uploadFileList;
    }

    private String createSaveFileName(String filename) {
        String filenameWithExtension = extractExtension(filename);
        return UUID.randomUUID().toString() + filenameWithExtension;
    }

    private String extractExtension(String originalFileName) {
        int idx = originalFileName.lastIndexOf(".");
        return originalFileName.substring(idx);
    }
}
