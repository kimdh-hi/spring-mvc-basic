package hello.login.web.login;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberService;
import hello.login.web.SessionConst;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Controller
public class LoginController {

    private final MemberService memberService;
    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        return "login/loginForm";
    }

    /**
     * 쿠키 적용 Login
     */
    //@PostMapping("/login")
    public String loginV1(
            @Valid @ModelAttribute LoginForm form,
            BindingResult bindingResult,
            HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            log.info("binding result = {}", bindingResult);
            return "login/loginForm";
        }

        Member loginMember = memberService.login(form.getLoginId(), form.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 패스워드가 일치하지 않습니다.");
            return "login/loginForm";
        }

        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
        response.addCookie(idCookie);

        return "redirect:/";
    }

    //@PostMapping("/logout")
    public String logoutV1(HttpServletResponse response) {
        Cookie cookie = new Cookie("memberId", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/";
    }

    /**
     * 세션 쿠키 적용
     */
//    @PostMapping("/login")
    public String loginV2(
            @Valid @ModelAttribute LoginForm form,
            BindingResult bindingResult,
            HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            log.info("binding result = {}", bindingResult);
            return "login/loginForm";
        }

        Member loginMember = memberService.login(form.getLoginId(), form.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 패스워드가 일치하지 않습니다.");
            return "login/loginForm";
        }

        sessionManager.createSession(response, loginMember);

        return "redirect:/";
    }

//    @PostMapping("/logout")
    public String logoutV2(HttpServletRequest request) {
        sessionManager.expireCookie(request);

        return "redirect:/";
    }

    /**
     * HttpSession 적용
     *
     * 타임아웃 설정 (default: lastAccessedTime으로부터 30분)
     */
    @PostMapping("/login")
    public String loginV3(
            @Valid @ModelAttribute LoginForm form,
            BindingResult bindingResult,
            HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            log.info("binding result = {}", bindingResult);
            return "login/loginForm";
        }

        Member loginMember = memberService.login(form.getLoginId(), form.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 패스워드가 일치하지 않습니다.");
            return "login/loginForm";
        }

        // default: true (기존 세션이 있을 시 기존 세션 사용, 없을 시 세션 생성)
        //          false (기존 세션이 있을 시 기존 세션 사용, 없을 시 null 반환)
        HttpSession session = request.getSession();
        // 세션에 보관할 객체 저장
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        //session.setMaxInactiveInterval(1800);

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request) {
        // 기존 세션이 없을 때 세션을 생성하지 않도록.
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 제거
        }

        return "redirect:/";
    }
}
