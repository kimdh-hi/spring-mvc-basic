package hello.login.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("doFilter 호출");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        try{
            log.info("request : [{}][{}]", uuid, requestURI);
            chain.doFilter(request, response); // 다음 필터 or 서블릿->컨트롤러 호출
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("response : [{}][{}]", uuid, requestURI);
        }
    }

    @Override
    public void destroy() {
        log.info("destroy 호출");
    }
}
