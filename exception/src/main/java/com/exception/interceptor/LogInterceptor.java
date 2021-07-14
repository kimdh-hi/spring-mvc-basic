package com.exception.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("preHandler 호출");

        String uuid = UUID.randomUUID().toString();
        String requestURI = request.getRequestURI();
        DispatcherType dispatcherType = request.getDispatcherType();

        request.setAttribute("LOG_ID", uuid);

        log.info("Request : [{}] [{}] [{}]", uuid, requestURI, dispatcherType);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("afterCompletion 호출");

        String requestURI = request.getRequestURI();
        String uuid = (String) request.getAttribute("LOG_ID");
        DispatcherType dispatcherType = request.getDispatcherType();

        log.info("Response : [{}] [{}] [{}]", uuid, requestURI, dispatcherType);

        if (ex != null) {
            log.info("afterCompletion error : {}", ex);
        }
    }
}
