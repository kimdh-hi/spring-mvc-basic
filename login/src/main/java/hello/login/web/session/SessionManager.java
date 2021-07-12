package hello.login.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 세션 쿠키
 * - 쿠키 난수(UUID)를 응답
 * - 난수를 키 값하고 실제 의미있는 정보를 저장하는 Map을 관리
 */
@Component
public class SessionManager {

    private Map<String, Object> sessionMap = new ConcurrentHashMap<>();
    private final String MY_SESSION_NAME = "mySessionName";

    /**
     * 세션 쿠키 생성
     */
    public void createSession(HttpServletResponse response, Object value) {

        String sessionKey = UUID.randomUUID().toString();
        sessionMap.put(sessionKey, value);

        Cookie cookie = new Cookie(MY_SESSION_NAME, sessionKey);
        response.addCookie(cookie);
    }

    /**
     * 요청받은 쿠키에 저장된 난수를 세션map과 매핑하여 get
     */
    public Object getSession(HttpServletRequest request) {
        Cookie cookie = findCookie(request, MY_SESSION_NAME);
        if (cookie == null) return null;
        return sessionMap.get(cookie.getValue());
    }

    /**
     * 세션 삭제 (만료)
     */
    public void expireCookie(HttpServletRequest request) {
        Cookie cookie = findCookie(request, MY_SESSION_NAME);
        if (cookie != null) sessionMap.remove(cookie.getValue());
    }

    private Cookie findCookie(HttpServletRequest request, String sessionName) {
        if (request.getCookies() == null) return null;

        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(sessionName))
                .findAny()
                .orElse(null);
    }

}
