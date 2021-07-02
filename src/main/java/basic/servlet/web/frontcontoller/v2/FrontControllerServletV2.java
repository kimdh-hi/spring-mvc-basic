package basic.servlet.web.frontcontoller.v2;

import basic.servlet.web.frontcontoller.MyView;
import basic.servlet.web.frontcontoller.v2.controller.MemberFormControllerV2;
import basic.servlet.web.frontcontoller.v2.controller.MemberListControllerV2;
import basic.servlet.web.frontcontoller.v2.controller.MemberSaveControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet {

    private Map<String, ControllerV2> map = new HashMap<>();

    public FrontControllerServletV2() {
        map.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        map.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        map.put("/front-controller/v2/members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        System.out.println("requestURI = " + requestURI);
        ControllerV2 controller = map.get(requestURI);
        MyView view = controller.process(request, response);

        view.render(request, response);
    }
}
