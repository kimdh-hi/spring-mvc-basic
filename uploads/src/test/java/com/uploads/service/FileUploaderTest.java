package com.uploads.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileUploaderTest {

    @Test
    void substringTest() {
        String path = "abc.jpeg";
        int idx = path.lastIndexOf(".");
        String substring = path.substring(idx);
        Assertions.assertThat(substring).isEqualTo(".jpeg");
    }

}