package com.typeconverter.controller;

import com.typeconverter.type.IpPort;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class ViewConvertTestController {

    @GetMapping("/view/convert-test")
    public String viewConvertTest(Model model) {
        model.addAttribute("number", 10000);
        model.addAttribute("ipPort", new IpPort("127.0.0.1",8080));
        return "convert-test-view";
    }

    @GetMapping("/view/convert-test/form")
    public String newForm(Model model) {
        IpPort ipPort = new IpPort("127.0.0.1", 8080);
        Form form = new Form(ipPort);
        model.addAttribute("form", form);
        return "convert-test-view-form";
    }

    @Data
    static class Form{
        private IpPort ipPort;

        public Form(IpPort ipPort) {
            this.ipPort = ipPort;
        }
    }
}
