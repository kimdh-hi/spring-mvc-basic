package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.argumentresolver.Login;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    private final SessionManager sessionManager;

//    @GetMapping("/")
    public String homeLoginV1(@CookieValue(name="memberId", required = false) Long memberId, Model model){

        if (memberId == null) return "home";

        Member loginMember = memberRepository.findById(memberId);
        if (loginMember == null) return "home";

        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    /**
     * 직접 만든 세션 쿠키 적용
     */
    //@GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model){

        Member member = (Member)sessionManager.getSession(request);
        if (member == null) return "home";

        model.addAttribute("member", member);
        return "loginHome";
    }

    /**
     * HttpSession 적용
     */
//    @GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model){

        HttpSession session = request.getSession(false);
        if (session == null) return "home";

        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (member == null) return "home";

        model.addAttribute("member", member);
        return "loginHome";
    }


    /**
     * @SessionAttribute 적용
     */
//    @GetMapping("/")
    public String homeLoginV3(
            @SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member member,
            Model model) {

        if (member == null) return "home";

        model.addAttribute("member", member);
        return "loginHome";
    }

    //    @GetMapping("/")
    public String homeLogin(
            @SessionAttribute(name="SESSION_KEY", required = false) Member member,
            Model model) {

        if (member == null) return "home";

        model.addAttribute("member", member);
        return "loginHome";
    }

    /**
     * custom annotaion을 이용해 Argument resolver를 적용
     */
    @GetMapping("/")
    public String homeLoginV4(@Login Member member, Model model) {
        if (member == null) return "home";
        model.addAttribute("member", member);
        return "loginHome";
    }

}