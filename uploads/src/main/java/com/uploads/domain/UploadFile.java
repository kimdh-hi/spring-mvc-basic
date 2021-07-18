package com.uploads.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadFile {

    private String uploadFileName; // 사용자가 업로드한 파일 이름
    private String saveFileName; // 서버에 저장될 파일 이름 (중복방지를 위해 uuid를 사용)
}
