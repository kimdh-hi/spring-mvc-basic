package basic.servlet.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        // 전체 파라미터 조회
        // localhost:8080?name=kim&age=26
        req.getParameterNames().asIterator().forEachRemaining(item -> {
            System.out.println(item +  "=" + req.getParameter(item));
        });

        // 중복 파라미터
        // localhost:8080?name=kim&age=26&name=lee
        String[] values = req.getParameterValues("name");
        for (String value : values) {
            System.out.println("name = " + value);
        }

        res.getWriter().write("OK");
    }
}
