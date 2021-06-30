package basic.servlet.response;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Json객체로 응답
 */
@WebServlet(name = "responseJsonData", urlPatterns = "/response-json")
public class ResponseJsonData extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        // 응답할 객체 생성
        ResponseDto dto = new ResponseDto("kim", 26);
        // 응답할 객체를 String으로 매핑
        String result = objectMapper.writeValueAsString(dto);

        res.setContentType("application/json");
        res.setCharacterEncoding("utf-8");
        res.getWriter().write(result);
    }
}
