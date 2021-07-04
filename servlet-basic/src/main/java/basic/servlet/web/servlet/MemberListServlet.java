package basic.servlet.web.servlet;

import basic.servlet.domain.member.Member;
import basic.servlet.domain.member.MemberRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "memberListServlet", urlPatterns = "/servlet/members")
public class MemberListServlet extends HttpServlet {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        List<Member> members = memberRepository.findAll();

        res.setContentType("text/html");
        res.setCharacterEncoding("utf-8");

        PrintWriter w = res.getWriter();
        w.write("<html>");
        w.write("<body>");
        for (Member member : members) {
            w.write("<ul>");
            w.write("<li>");
            w.write("<span> username: "+member.getUsername()+" age: "+ member.getAge()+"</span>");
            w.write("</li>");
            w.write("</ul>");
        }
        w.write("</body>");
        w.write("</html>");
    }
}
