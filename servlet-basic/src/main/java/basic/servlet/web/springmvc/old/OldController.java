package basic.servlet.web.springmvc.old;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// BeanNameUrlHandlerMapping에 의해 등록된 Bean의 이름으로 url 매핑을 수행
//   - HandlerMapping 정책: 우선적으로 Annotation 기반 매핑정보를 찾고 두 번째로 BeanName 기반 매핑정보를 찾음
@Component("/springmvc/old-controller")
public class OldController implements Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        System.out.println("OldController.handleRequest");

        return new ModelAndView("new-form");
    }
}
