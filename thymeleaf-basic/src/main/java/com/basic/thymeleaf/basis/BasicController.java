package com.basic.thymeleaf.basis;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/basic")
public class BasicController {

    @GetMapping("/text-basic")
    public String textBasic(Model model) {
        model.addAttribute("data", "hello <b>thymeleaf</b>");
        return "basic/text-basic";
    }

    /**
     * 보안상의 문제 등으로 escape 처리가 기본적으로 해주어야 함
     * escaped   => '<' -> &lt; '>' -> &gt;
     * escaped   => th:text  or [[${data}]]
     * unescaped => th:utext or [(${data})]
     */
    @GetMapping("/text-unescaped")
    public String textUnescaped(Model model) {
        model.addAttribute("data", "hello <b>thymeleaf</b>");
        return "basic/text-unescaped";
    }

    @GetMapping("/variable")
    public String variable(Model model) {
        User userA = new User("userA", 10);
        User userB = new User("userB", 20);

        List<User> list = new ArrayList<>();
        list.add(userA);
        list.add(userB);

        Map<String, User> map = new HashMap<>();
        map.put("userA", userA);
        map.put("userB", userB);

        model.addAttribute("user", userA); // Object
        model.addAttribute("users", list); // List<Object>
        model.addAttribute("userMap", map); // Map<String, <Object>
        return "basic/variable";
    }

    /**
     * Thymeleaf의 기본 객체로 session정보를 확인할 수 있는지 테스트하기 위함
     */
    @GetMapping("/basic-objects")
    public String basicObjects(HttpSession session) {
        session.setAttribute("sessionData", "Hello Session");
        return "basic/basic-objects";
    }

    /**
     * Thymeleaf의 날짜 라이브러리 테스트 ( #temporals )
     */
    @GetMapping("/date")
    public String date(Model model) {
        model.addAttribute("localDateTime", LocalDateTime.now());

        return "basic/date";
    }

    /**
     * Thymeleaf에서 등록된 Bean에 바로 접근이 가능한지 테스트하기 위함
     */
    @Component("testBean")
    static class TestBean{
        public String test(String message) {
            return "Test messag\ne : " + message;
        }
    }

    /**
     *    Thymeleaf에서 link 조작을 테스트해보기 위함
     *    Path Varaible link ( localhost:8080/test/data1 )
     *    Query Parameter link ( localhost:8080/test?param1=data1 )
     */

    @GetMapping("/link")
    public String link(Model model) {
        model.addAttribute("param1", "data1");
        model.addAttribute("param2", "data2");
        return "basic/link";
    }

    /**
     * Thymeleaf에서 리터럴 처리를 테스트하기 위함
     */
    @GetMapping("/literal")
    public String literal(Model model) {
        model.addAttribute("data", "hello");
        return "basic/literal";
    }

    /**
     * Thymeleaf에서 연산을 테스트하기 위함
     */
    @GetMapping("/operation")
    public String operation(Model model) {
        model.addAttribute("nullData", null);
        model.addAttribute("data", "Spring!");
        return "basic/operation";
    }

    /**
     * Thymeleaf에서 HTML속성 값을 조작을 테스트하기 위함
     */
    @GetMapping("/attribute")
    public String attribute() {
        return "basic/attribute";
    }

    /**
     * Thymeleaf 반복문
     */
    @GetMapping("/each")
    public String each(Model model) {
        addUsersToModel(model);
        return "basic/each";
    }

    /**
     * Thymeleaf 조건문
     */
    @GetMapping("/condition")
    public String condition(Model model) {
        addUsersToModel(model);
        return "basic/condition";
    }

    /**
     * Thymeleaf에서 임의로 scope를 지정하기 위한 th:block 테스트를 위함
     */
    @GetMapping("/block")
    public String block(Model model) {
        addUsersToModel(model);
        return "basic/block";
    }

    /**
     * Thymeleaf에서 편하게 javascript를 사용하기 위한 inline 테스트
     */
    @GetMapping("/javascript")
    public String javascript(Model model) {
        model.addAttribute("user", new User("userA", 10));
        addUsersToModel(model);
        return "basic/javascript";
    }

    void addUsersToModel(Model model) {
        List<User> list = new ArrayList<>();
        list.add(new User("user1", 1));
        list.add(new User("user2", 2));
        list.add(new User("user3", 3));
        list.add(new User("user4", 10));
        list.add(new User("user5", 20));
        model.addAttribute("users", list);

    }

    @Data
    static class User {
        private String username;
        private int age;
        public User(String username, int age) {
            this.username = username;
            this.age = age;
        }
    }
}
