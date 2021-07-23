package com.uploads.review;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@Controller
public class TestController {

    private final String uploadDir = "/Users/jeonhyeji/Documents/etc/file/";

    @GetMapping("/review/form")
    public String newForm() {
        return "upload-form";
    }

    @PostMapping("/review/form")
    public String form(@RequestParam String itemName,
                       @RequestParam MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            String filename = file.getOriginalFilename();
            log.info("file.getOriginalFilename = {}", filename);

            String fullPath = uploadDir + filename;
            file.transferTo(new File(fullPath));
        }
        return "upload-form";
    }
}
