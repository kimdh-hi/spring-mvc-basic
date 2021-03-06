package com.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Slf4j
@RestController
public class RequestHeaderController {

    @GetMapping(value = "/headers")
    public String headers(
            HttpMethod httpMethod,
            Locale locale, // 언어정보
            @RequestHeader MultiValueMap<String, String> headerMap, // 모든 헤더 정보
                // MultiValueMap: 하나의 key에 여러 값을 가지는 Map (get시 List를 반환)
            @RequestHeader("host") String host, // 지정 헤더 정보
            @CookieValue(value="myCookie", required = false) String cookie
            ) {
        log.info("httpMethod={}", httpMethod);
        log.info("locale={}", locale);
        log.info("headerMap={}", headerMap);
        log.info("header host={}", host);
        log.info("myCookie={}", cookie);

        return "ok";
    }

    @GetMapping("/add-cookie")
    public String addCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("myCookie", "myCookieValue");
        response.addCookie(cookie);
        return "ok";
    }
}
