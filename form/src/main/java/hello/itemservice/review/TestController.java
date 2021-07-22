package hello.itemservice.review;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/test")
public class TestController {

    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute( "form", new FormDto());
        return "review/form";
    }

    @ModelAttribute("hobbies")
    private Map<String, String> favorite() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("movie", "영화보기");
        map.put("music", "음악듣기");
        map.put("running", "런닝하기");
        map.put("game", "게임하기");
        return map;
    }

    @ModelAttribute("language")
    private Language[] languages() {
        return Language.values();
    }

    @ModelAttribute("country")
    private Country[] countries() {
        return Country.values();
    }


    @PostMapping("/form")
    public String form(@ModelAttribute("form") FormDto formDto) {
        log.info("formDto.name = {}", formDto.getName());
        log.info("formDto.tnf = {}", formDto.isTnf());
        List<String> hobbies = formDto.getHobbies();
        for (String hobby : hobbies) {
            log.info("formDto.hobby = {}", hobby);
        }
        log.info("formDto.language = {}", formDto.getLanguage());
        log.info("formDto.country = {}", formDto.getCountry());
        return "review/form";
    }
}
