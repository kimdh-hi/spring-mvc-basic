package hello.login.web.login;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Controller
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("binding result = {}", bindingResult);
            return "login/loginForm";
        }

        Member loginInfo = memberService.login(form.getLoginId(), form.getPassword());
        if (loginInfo == null) {
            bindingResult.reject("loginFail", "아이디 또는 패스워드가 일치하지 않습니다.");
            return "login/loginForm";
        }

        return "redirect:/";
    }
}
