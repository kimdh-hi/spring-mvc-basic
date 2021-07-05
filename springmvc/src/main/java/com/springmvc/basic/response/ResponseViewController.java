package com.springmvc.basic.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Controller 클래스에서 @ResponseBody 없이 String을 반환하게 되면 반환되는 String은 view의 논리적 이름으로 판단한다.
 * 때문에 반환되는 String 값을 ViewResolver를 통해 실제 view의 경로를 찾아 랜더링하게 된다.
 */

@Slf4j
@Controller
public class ResponseViewController {

    // ModelView
    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView modelAndView = new ModelAndView("response/hello").addObject("data", "test");
        return modelAndView;
    }

    // Model
    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {
        model.addAttribute("data", "test");
        return "response/hello";
    }
}
