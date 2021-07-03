package basic.servlet.web.frontcontoller.v5.adapter;

import basic.servlet.web.frontcontoller.ModelView;
import basic.servlet.web.frontcontoller.v4.ControllerV4;
import basic.servlet.web.frontcontoller.v5.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV4HandlerAdapter implements MyHandlerAdapter {

    @Override
    public boolean support(Object handler) {
        return handler instanceof ControllerV4;
    }

    @Override
    public ModelView handler(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {

        ControllerV4 controller = (ControllerV4) handler;

        Map<String, String> paramMap = getParamMap(request);

        Map<String, Object> model = new HashMap<>();
        String viewName = controller.process(paramMap, model);

        ModelView modelView = new ModelView(viewName); // Adapter에서 리턴타입을 맞춰주기 위함
        modelView.setModel(model);

        return modelView;
    }

    private Map<String, String> getParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator().forEachRemaining(item -> {
            paramMap.put(item, request.getParameter(item));
        });
        return paramMap;
    }
}
