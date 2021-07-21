package com.basic.thymeleaf.form;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class FormController {

    @GetMapping("/form")
    public String newForm() {
        return "form/test-form";
    }
}
