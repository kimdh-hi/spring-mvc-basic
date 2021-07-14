package com.exception.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 에러 페이지
 *
 * 404에러 발생시
     * 1. templates/error/404.html
     * 2. templates/error/4xx.html
     * 3. static/error/404.html
     * 4. static/error/4xx.html
 * 순서로 에러페이지를 찾음
 */

@Slf4j
@Controller(value = "/error-page")
public class ExceptionController {

    @RequestMapping("/error-boom")
    public String errorRuntime() {
        throw new RuntimeException("에러 에러");
    }

    @RequestMapping("/error-404")
    public void error404(HttpServletResponse response) throws IOException {
        response.sendError(404, "404 에러 발생 메시지");
    }

    @RequestMapping("/error-400")
    public void error400(HttpServletResponse response) throws IOException {
        response.sendError(400, "400 에러 발생 메시지");
    }

    @RequestMapping("/error-403")
    public void error403(HttpServletResponse response) throws IOException {
        response.sendError(403, "403 에러 발생 메시지");
    }

    @RequestMapping("/error-500")
    public void error500(HttpServletResponse response) throws IOException {
        response.sendError(500, "500 에러 발생 메시지");
    }
}
