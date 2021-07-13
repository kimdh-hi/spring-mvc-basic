package hello.login.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    /**
     * 컨트롤러 호출 전 호출
     * @return true: 다음 인터셉터 or 컨트롤러(핸들러어댑터)로 진행
     *         false: 진행 ❌
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        String uuid = UUID.randomUUID().toString(); // 요청을 구분하기 위한 uuid
        // 인터셉터는 싱글톤으로 관리되므로 전역변수를 사용하면 위험 (다른 인터셉터와 값을 공유하기 위해 request의 Attribute사용)
        request.setAttribute("uuid", uuid);

        // @RequestMapping 을 사용하는 메서드 => HandlerMethod
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 요청 메서드에 대한 여러 정보 조회 가능
        }

        log.info("요청 [{}] [{}] [{}]",uuid, requestURI, handler);

        return true;
    }

    /**
     * 컨트롤러 호출 후 호출
     *
     * 컨트롤러에서 예외 발생시 호출되지 않는다.
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("ModelAndView = {}", modelAndView);
    }

    /**
     * 요청 완료 후 호출 (뷰가 랜더링 된 후 호출)
     *
     * 컨트롤러에서 예외 발생 시에도 호출된다. (항상 호출) => 예외와 무관한 공통처리 가능
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String uuid = (String) request.getAttribute("uuid");
        String requestURI = request.getRequestURI();

        log.info("응답 [{}] [{}]", uuid, requestURI);

        if (ex != null) log.error("afterCompletion ", ex);
    }
}
