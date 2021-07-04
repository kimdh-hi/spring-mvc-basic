package basic.servlet.web.frontcontoller.v5;

import basic.servlet.web.frontcontoller.ModelView;
import basic.servlet.web.frontcontoller.MyView;
import basic.servlet.web.frontcontoller.v3.controller.MemberFormControllerV3;
import basic.servlet.web.frontcontoller.v3.controller.MemberListControllerV3;
import basic.servlet.web.frontcontoller.v3.controller.MemberSaveControllerV3;
import basic.servlet.web.frontcontoller.v4.controller.MemberFormControllerV4;
import basic.servlet.web.frontcontoller.v4.controller.MemberListControllerV4;
import basic.servlet.web.frontcontoller.v4.controller.MemberSaveControllerV4;
import basic.servlet.web.frontcontoller.v5.adapter.ControllerV3HandlerAdapter;
import basic.servlet.web.frontcontoller.v5.adapter.ControllerV4HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerV5 extends HttpServlet {

    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList();

    public FrontControllerV5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 요청된 URI를 파싱하여 적합한 Handler(Controller)를 얻어온다.
        Object handler = getHandler(request);
        // Handler를 처리할 수 있는 Adapter를 얻어온다.
        MyHandlerAdapter adapter = getHandlerAdapter(handler);

        ModelView modelView = adapter.handler(request, response, handler);

        MyView myView = viewResolver(modelView);

        myView.render(modelView.getModel(), request, response);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        // 모든 Adapter를 조회
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.support(handler)) { // handler를 처리할 수 있는 (같은 타입의) adapter를 리턴
                return adapter;
            }
        }
        throw new IllegalArgumentException(handler + "not Founded.");
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

    // URI 요청 정보에 따른 Controller 매핑정보 초기화
    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

    // 사용하고자 하는 Adapter 초기화
    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    private MyView viewResolver(ModelView modelView) {
        String viewName = modelView.getViewName();
        MyView view = new MyView("/WEB-INF/views/" + viewName + ".jsp");
        return view;
    }
}
