package hello.login.web.filter;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] unAuthList = {"/", "/members/add", "/login", "/css/*"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();

        HttpSession session = httpRequest.getSession(false);
        try {
            log.info("LoginCheckFilter 시작");
            // 인증이 필요한 요청인지 체크
            if (!PatternMatchUtils.simpleMatch(unAuthList, requestURI)) {
                // 인증 로직 수행 (세션)
                log.info("인증이 필요한 요청");
                if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
                    // 인증 후 이전 요청으로 돌아가기 위해 현재 요청의 uri를 쿼리 파라미터에 포함
                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI);
                    // 미인증 사용자의 경우 로그인페이지로 redirect 후 다음 필터, 서블릿으로 진행시키지 않고 그냥 리턴.
                    // chain을 통해 다음 필터, 서블릿을 호출하지 않는 것이 중요
                    return;
                }
            }

            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("LoginCheckFilter 종료");
        }
    }
}
