package basic.servlet.web.frontcontoller.v3;

import basic.servlet.web.frontcontoller.ModelView;

import java.util.Map;

public interface ControllerV3 {

    /**
     * @param paramMap @<RequestName,RequestValue>
     * @return
     */
    ModelView process(Map<String, String> paramMap);
}
