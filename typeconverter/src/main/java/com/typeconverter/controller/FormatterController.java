package com.typeconverter.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

/**
 * Spring이 제공하는 기본 Formatter
 */

@Controller
public class FormatterController {

    @GetMapping("/formatter/form")
    public String newForm(Model model) {
        model.addAttribute("form", new MyForm(100000000, LocalDateTime.now()));
        return "formatter-form";
    }

    /**
     * 폼 데이터가 문자 형태로 넘어올 때도 Formatter가 적용되어 변환 ex) "10,000" -> 10000
     */
    @PostMapping("/formatter/form")
    public String form(@ModelAttribute MyForm form) {
        return "formatter-form";
    }


    @AllArgsConstructor
    @Data
    static class MyForm{
        @NumberFormat(pattern = "###,###")
        private Integer price;
        @DateTimeFormat(pattern = "yyyy-MM-dd hh시 mm분 ss초")
        private LocalDateTime date;
    }
}
