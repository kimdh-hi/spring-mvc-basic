package hello.itemservice.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String form(@ModelAttribute Item item) {
        return "form";
    }

    @PostMapping
    public String add(@Valid @ModelAttribute Item item, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("binding result = {}", bindingResult);
            return "form";
        }

        log.info("****** 비즈니스로직 수행 ********");

        return "form";
    }

}
