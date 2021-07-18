package com.uploads.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

/**
 * 파일 업로드 주요 메서드
 *
 * reqeust.getParts() : 모든 multi-part 데이터를 Part단위로 Collection타입으로 반환
 * part.getSubmittedFileName() : Part에 파일이 있다면 해당 파일의 이름을 반환
 * part.write(path) : 지정한 경로에 파일을 저장
 */

@Slf4j
@Controller
@RequestMapping("/servlet/v2")
public class ServletUploadControllerV2 {

    @Value("${file.dir}") // properties에 정의한 값 바인딩
    private String uploadDir;

    @GetMapping("/upload")
    public String newFile() {
        return "upload-form";
    }

    @PostMapping("/upload")
    public String saveFileV1(HttpServletRequest request) throws ServletException, IOException {
        Collection<Part> parts = request.getParts();
        for (Part part : parts) {
            log.info("part.getName() = {}", part.getName());
            Collection<String> headerNames = part.getHeaderNames();
            for (String headerName : headerNames) {
                log.info("{}: {}", headerName, part.getHeader(headerName));
            }
            log.info("part.getSubmittedFileName() = {}", part.getSubmittedFileName()); // 전송된 파일의 이름

            InputStream inputStream = part.getInputStream(); // body 파싱
            String body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8); // stream to string

            if (StringUtils.hasText(part.getSubmittedFileName())) { // 파일을 갖고 있다면
                String uploadDirPath = uploadDir + part.getSubmittedFileName(); // 업로드 디렉토리 + 파일이름 지정
                log.info("파일 업로드 full-path = {}", uploadDirPath);
                part.write(uploadDirPath); // 파일 저장
            }
        }
        return "upload-form";
    }
}
