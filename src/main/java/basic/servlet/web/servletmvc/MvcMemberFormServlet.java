package basic.servlet.web.servletmvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * dispatcher.forward(request, response);
 *  redirect와 다름
 *  redirect의 경우 리다이랙팅 응답을 브라우저로 보내고 브라우저로 부터 다시 요청을 받아 view페이지를 랜더링 함
 *  forward의 경우 브라우저로 요청을 보내지 않고 서버에서 내부적으로 요청을 보내고 받음
 *  즉, 클라이언트의 브라우저는 이동되는 것을 모름 (브라우저 측의 url이 변하지 않는 것을 확인)
 */

@WebServlet(name = "mvcMemberFormServlet", urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String viewPath = "/WEB-INF/views/new-form.jsp";
        RequestDispatcher dispatcher = req.getRequestDispatcher(viewPath);
        dispatcher.forward(req, res);
    }
}
