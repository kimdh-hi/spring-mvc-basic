package com.basic.thymeleaf.basis;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/template")
@Controller
public class TemplateController {

    /**
     * Fragment 사용
     */
    @GetMapping("/fragment")
    public String fragment() {
        return "template/fragment/fragmentMain";
    }

    /**
     * Layout 사용
     */
    @GetMapping("/layout")
    public String layout() {
        return "template/layout/layoutMain";
    }

    /**
     * <html> 태그 자체를 Layout으로 대체
체    */
    @GetMapping("/layoutExtend")
    public String layoutExtends() {
        return "template/layoutExtend/layoutExtendMain";
    }
}
