package com.springmvc.basic.requestMapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MappingController {


    /**
     * @PathVariable
     * url에 값이 명시되는 경우 ex) localhost:8080/mapping/userData
     */
    @GetMapping("/mapping/{user}")
    public String pathVariable(@PathVariable("user") String user) {
        log.info("pathVariable = {}", user);
        return "ok";
    }

    /**
     * Media type mapping (Content-type)
     * 요청되는 body의 미디어 타입을 제한 (consumes="application/json" = json타입 body데이터만 받는다.)
     * consumes = "!application/json" : json이 아닌 모든 미디어 타입
     * !, * 등 사용 가능
     * 지정한 미디어 타입이 아닌 요청이 오는 경우 415 Unsupported Media Type 에러 발생
     */
    @PostMapping(value = "/mapping-consume", consumes = "application/json")
    public String consume() {
        log.info("consume");
        return "ok";
    }

    /**
     * Media type mapping (Accept)
     * controller에서 생산해내는 데이터의 미디어 타입을 지정
     * 클라이언트의 Accept헤더와 매칭되지 않는다면 406 Not Acceptable 에러 발생
     * produces = "text/plain"
     * produces = {"text/plain, "application/*"}
     * produces = MediaType.~
     */
    @PostMapping(value = "/mapping-produces", produces = "text/html")
    public String produces() {
        log.info("produces");
        return "ok";
    }





}
