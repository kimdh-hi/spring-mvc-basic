package com.uploads.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Spring을 이용한 파일 업로드 주요 키워드
 *
 * @RequestParam MultipartFile
 * multipartFile.getOriginalFileName()
 * multipartFile.transferTo(new File(path))
 */

@Slf4j
@Controller
@RequestMapping("/spring")
public class SpringUploadController {

    @Value("${file.dir}")
    private String uploadDir;

    @GetMapping("/upload")
    public String uploadForm(Model model) {
        return "upload-form";
    }

    @PostMapping("/upload")
    public String upload(
            @RequestParam String itemName,
            @RequestParam MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            String originalFilename = file.getOriginalFilename(); // filename.확장자
            log.info("originalFilename = {}", originalFilename);

            String fullPath = uploadDir + originalFilename;
            log.info("fullPath = {}", fullPath);

            file.transferTo(new File(fullPath));
        }

        return "upload-form";
    }

}
