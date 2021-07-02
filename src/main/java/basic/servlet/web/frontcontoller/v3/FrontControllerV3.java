package basic.servlet.web.frontcontoller.v3;

import basic.servlet.web.frontcontoller.ModelView;
import basic.servlet.web.frontcontoller.MyView;
import basic.servlet.web.frontcontoller.v3.controller.MemberFormControllerV3;
import basic.servlet.web.frontcontoller.v3.controller.MemberListControllerV3;
import basic.servlet.web.frontcontoller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        ControllerV3 controller = controllerMap.get(requestURI);

        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator().forEachRemaining(item -> {
            paramMap.put(item, request.getParameter(item));
        });

        ModelView modelView = controller.process(paramMap);

        // ViewResolver
        String viewName = modelView.getViewName();
        MyView view = new MyView("/WEB-INF/views/" + viewName + ".jsp");
        view.render(modelView.getModel(), request, response);
    }
}
