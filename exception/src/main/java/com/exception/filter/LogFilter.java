package com.exception.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String uuid = UUID.randomUUID().toString();
        DispatcherType dispatcherType = httpRequest.getDispatcherType();
        String requestURI = httpRequest.getRequestURI();

        try{
            log.info("Request : [{}] [{}] [{}]", uuid, requestURI, dispatcherType);
            chain.doFilter(request, response);
        } catch(Exception e) {
            log.info("error message : {}", e.getMessage());
        } finally {
            log.info("Response : [{}] [{}] [{}]", uuid, requestURI, dispatcherType);
        }
    }
}
