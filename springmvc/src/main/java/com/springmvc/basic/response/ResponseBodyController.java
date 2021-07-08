package com.springmvc.basic.response;

import com.springmvc.basic.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class ResponseBodyController {

    @RequestMapping("/response-body-string-v1")
    public void responseBodyStringV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().write("ok");
    }

    @RequestMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyStringV2() {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @RequestMapping("/response-body-string-v3")
    public String responseBodyStringV3() {
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/response-body-json-v1")
    public ResponseEntity<UserDto> responseBodyJsonV1() {
        UserDto userDto = new UserDto();
        userDto.setUsername("kim");
        userDto.setAge(26);


        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RequestMapping("/response-body-json-v2")
    public UserDto responseBodyJsonV2() {
        UserDto userDto = new UserDto();
        userDto.setUsername("kim");
        userDto.setAge(26);

        return userDto;
    }
}
