package hello.login.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    private final Map<String, Object> sessionMap = new ConcurrentHashMap<>();
    private final String MY_SESSION_NAME = "mySessionName";

    public void createSession(HttpServletResponse response, Object value) {

        String sessionKey = UUID.randomUUID().toString();
        sessionMap.put(sessionKey, value);

        Cookie cookie = new Cookie(MY_SESSION_NAME, sessionKey);
        response.addCookie(cookie);
    }

    public Object getSession(HttpServletRequest request) {
        Cookie cookie = findCookie(request, MY_SESSION_NAME);
        if (cookie == null) return null;
        return sessionMap.get(cookie.getValue());
    }

    public void expireCookie(HttpServletRequest request) {
        Cookie cookie = findCookie(request, MY_SESSION_NAME);
        sessionMap.remove(cookie.getValue());
    }

    private Cookie findCookie(HttpServletRequest request, String sessionName) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(sessionName))
                .findAny()
                .orElse(null);
    }

}
