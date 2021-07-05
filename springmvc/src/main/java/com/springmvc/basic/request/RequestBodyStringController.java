package com.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

/**
 * Body에 String이 담겨서 넘어오는 경우의 데이터 파싱 방법
 */

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyStringV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String str = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("body string = {}", str);

        response.getWriter().write("ok");
    }

    /**
     * InputStream, OutputStream, Writer... 등의 객체로 파라미터 바인딩이 가능하다.
     */
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer writer) throws IOException {
        String str = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("body string = {}", str);

        writer.write("ok");
    }

    /**
     * HTTP Message Converter 사용
     * HttpEntity: HTTP의 header, body 정보를 쉽게 조회할 수 있게 해주는 객체
     */
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {
        String str = httpEntity.getBody();
        log.info("body string = {}", str);

        return new HttpEntity<>("ok");
    }

    /**
     * HttpEntity -> RequestEntity & ResponseEntity
     * RequestEntity: url정보 등 추가 기능 제공
     * ResponseEntity: HttpStatus code 사용 가능
     */
    @PostMapping("/request-body-string-v4")
    public ResponseEntity<String> requestBodyStringV4(RequestEntity<String> requestEntity) throws IOException {
        String str = requestEntity.getBody();

        log.info("body string = {}", str);
        log.info("request url = {}", requestEntity.getUrl());

        return new ResponseEntity<String>("ok", HttpStatus.OK);
    }

    /**
     * @RequestBody 사용
     */
    @ResponseBody
    @PostMapping("/request-body-string-v5")
    public String requestBodyStringV5(@RequestBody String str) {

        log.info("body string = {}", str);

        return "ok";
    }
}
