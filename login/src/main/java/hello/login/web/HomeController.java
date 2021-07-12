package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * 쿠키 보안 문제
 * - 클라이언트가 얼마든지 쿠키값을 변경할 수 있다.
 * - 쿠키에 critical한 정보 저장시 해당 정보가 탈취당할 수 있다.
 * - 쿠키를 sequential한 값을 사용시 해커는 다른 쿠키값을 쉽게 예측할 수 있다. (쿠키 탈취)
 *
 * 쿠키 보안 문제 해결
 * - 쿠키의 값을 예측 불가능한 난수로 지정하여야 한다. 서버측에서 난수기반 쿠키와 매핑하여 사용하는 방식
 * - 쿠키의 expiration time을 짧게 한다.
 */

@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {

    private final MemberRepository memberRepository;

    @GetMapping("/")
    public String homeLogin(@CookieValue(name="memberId", required = false) Long memberId, Model model){

        if (memberId == null) return "home";

        Member loginMember = memberRepository.findById(memberId);
        if (loginMember == null) return "home";

        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("memberId", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/";
    }

}