package com.springmvc.basic.request;

import com.springmvc.basic.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ModelAttributeController {

    /**
     * UserDto생성 -> 요청된 파라미터의 이름으로 객체의 프로퍼티의 setter 호출하여 요청 데이터를 바인딩
     */
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute UserDto userDto) {
        log.info("username = {}", userDto.getUsername());
        log.info("age = {}", userDto.getAge());

        return "ok";
    }

    /**
     * @ModelAttribute 생략가능
     */
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(UserDto userDto) {
        log.info("username = {}", userDto.getUsername());
        log.info("age = {}", userDto.getAge());

        return "ok";
    }
}
