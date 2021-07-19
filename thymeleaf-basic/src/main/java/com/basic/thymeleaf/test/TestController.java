package com.basic.thymeleaf.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String view(Model model) {
        return "test/main";
    }

    @GetMapping("/test-view")
    public String view2(Model model) {
        return "test/testMain";
    }
}
