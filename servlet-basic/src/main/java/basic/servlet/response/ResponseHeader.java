package basic.servlet.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "responseHeader", urlPatterns = "/response-header")
public class ResponseHeader extends HttpServlet {


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Cookie 설정
        //cookie(response);

        // Redirect 설정
        redirect(response);

    }

    private void cookie(HttpServletResponse response) {

        Cookie cookie = new Cookie("myCookie", "myCookie");
        cookie.setMaxAge(1000);
        response.addCookie(cookie);
    }

    private void redirect(HttpServletResponse response) throws IOException {
//        response.setStatus(HttpServletResponse.SC_FOUND);
//        response.setHeader("Location","/");
        response.sendRedirect("/");
    }
}
