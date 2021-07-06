package com.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @RequestMapping => RequestMappingHandlerAdapter의 handler를 호출 (어노테이션 기반 Controller)
 */

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username = {}", username);
        log.info("age = {}", age);

        response.getWriter().write("ok");
    }

    /**
     * @RequestParam 사용
     */
    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(@RequestParam("username") String username, @RequestParam("age") int age) {
        log.info("username = {}", username);
        log.info("age = {}", age);

        return "ok";
    }

    /**
     * @RequestParam의 value 생략
     * body데이터의 key값과 파라미터명이 같다면 @RequestParam까지 생략 가능
     */
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(@RequestParam String username, @RequestParam int age) {
        log.info("username = {}", username);
        log.info("age = {}", age);

        return "ok";
    }

    /**
     * required : 필수포함 여부
     * false로 지정시 해당 파라미터를 보내지 않았을 때 해당 데이터가 사용되었다면 5xx 서버측 오류 발생
     * false로 지정시 해당 파라미터를 보내지 않았을 때 해당 데이터가 사용되었다면 4xx 클라이언트측 오류 발생
     * 필수속성 사용시 null과 ""이 다르다는 것에 유의
     *    username=&age=123  : username은 null이 아닌 ""이기 때문에 에러없이 통과
     * false사용시 primitive타입 사용에 유의
     *    primitive타입은 null이 들어올 수 없으므로 5xx에러를 발생시킴
     *    null을 처리하고 싶다면 Integer 등의 래퍼 데이터 타입을 사용 or defaultValue 사용
     */
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username,
            @RequestParam(required = false) int age) {
        log.info("username = {}", username);
        log.info("age = {}", age);

        return "ok";
    }

    /**
     * required는 username= 과 같은 빈문자열을 null로 처리하지 않았지만 defaultValue는 빈문자열까지 처리하여 기본값으로 치환한다.
     */
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefaultValue(
            @RequestParam(required = true, defaultValue = "defaultUsername") String username,
            @RequestParam(required = false, defaultValue = "defaultAge") int age) {
        log.info("username = {}", username);
        log.info("age = {}", age);

        return "ok";
    }

    /**
     * requestParamMap 전체 요청 파라미터 조회
     */
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> params) {
        log.info("username = {}",params.get("username"));
        log.info("age = {}",params.get("age"));

        return "ok";
    }
}
