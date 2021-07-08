package com.springmvc.basic.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springmvc.basic.UserDto;
import lombok.extern.slf4j.Slf4j;
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
import java.nio.charset.StandardCharsets;

/**
 * Body에 Json타입 데이터가 담겨서 넘어오는 경우의 데이터 파싱 방법
 * v4 권장
 */

@Slf4j
@Controller
public class RequestBodyJsonController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String bodyString = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        UserDto userDto = objectMapper.readValue(bodyString, UserDto.class);

        log.info("username = {}", userDto.getUsername());
        log.info("age = {}", userDto.getAge());

        response.getWriter().write("ok");
    }


    // RequestBody 생략 불가 (생략시 원시타입에는 @RequestParam이 객체의 경우 @ModelAttribute가 기본적으로 붙게 됨)
    // ==> message body가 아닌 요청 파라미터를 처리하게 됨
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody String bodyString) throws JsonProcessingException {

        log.info("ObjectMapper 이전 bodyString = {}", bodyString);

        UserDto userDto = objectMapper.readValue(bodyString, UserDto.class);

        log.info("username = {}", userDto.getUsername());
        log.info("age = {}", userDto.getAge());

        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(@RequestBody UserDto userDto) {
        log.info("username = {}", userDto.getUsername());
        log.info("age = {}", userDto.getAge());

        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public ResponseEntity<String> requestBodyJsonV5(RequestEntity<UserDto> requestEntity) {
        log.info("username = {}", requestEntity.getBody().getUsername());
        log.info("age = {}", requestEntity.getBody().getAge());

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}
