package basic.servlet.web.frontcontoller.v5.adapter;

import basic.servlet.web.frontcontoller.ModelView;
import basic.servlet.web.frontcontoller.v3.ControllerV3;
import basic.servlet.web.frontcontoller.v5.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV3HandlerAdapter implements MyHandlerAdapter {

    @Override
    public boolean support(Object handler) {
        return handler instanceof ControllerV3;
    }

    @Override
    public ModelView handler(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        ControllerV3 controller = (ControllerV3) handler;

        Map<String, String> paramMap = getParamMap(request);

        ModelView modelView = controller.process(paramMap);

        return modelView;
    }

    private Map<String, String>  getParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator().forEachRemaining(item -> {
            paramMap.put(item, request.getParameter(item));
        });
        return paramMap;
    }
}
