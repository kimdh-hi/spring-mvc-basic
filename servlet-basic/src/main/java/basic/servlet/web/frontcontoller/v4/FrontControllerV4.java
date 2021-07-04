package basic.servlet.web.frontcontoller.v4;

import basic.servlet.web.frontcontoller.MyView;
import basic.servlet.web.frontcontoller.v4.controller.MemberFormControllerV4;
import basic.servlet.web.frontcontoller.v4.controller.MemberListControllerV4;
import basic.servlet.web.frontcontoller.v4.controller.MemberSaveControllerV4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerV4", urlPatterns = "/front-controller/v4/members/*")
public class FrontControllerV4 extends HttpServlet {

    private Map<String, ControllerV4> controllerMap = new HashMap<>();

    public FrontControllerV4() {
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        ControllerV4 controller = controllerMap.get(requestURI);

        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator().forEachRemaining(item -> {
            paramMap.put(item, request.getParameter(item));
        });

        Map<String, Object> model = new HashMap<>();

        String viewName = controller.process(paramMap, model);

        // ViewResolver
        MyView view = new MyView("/WEB-INF/views/" + viewName + ".jsp");
        view.render(model, request, response);
    }

}
