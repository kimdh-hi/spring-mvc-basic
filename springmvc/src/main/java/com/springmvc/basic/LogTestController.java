package com.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LogTestController {

    @GetMapping("/log-test")
    public String logTest() {

        String str = "test";

        /**
         * 두 방법 모두 같은 결과의 로그를 출력함 ( 해당 log가 logging level이 달라 출력되지 않을 때를 가정)
         * but, 2번의 경우 문자열 concat연산을 수행함 (cpu, 메모리의 낭비)
         *      1번의 파라미터를 이용해 찍는 방법은 의미없는 연산을 수행하지 않음.
         * {} 로 파라미터 바인딩을 이용하여 로그에 변수를 사용하는 것을 권장
         */
        // 로그 찍는 찍는 법 1
        log.trace("log = {}", str);
        // 로그 찍는 찍는 법 2
        log.trace("log = " + str);


        log.trace("log = {}", str);
        log.debug("log = {}", str);
        log.info("log = {}", str);
        log.warn("log = {}", str);
        log.error("log = {}", str);

        return "test";
    }
}
