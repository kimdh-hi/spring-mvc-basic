package basic.servlet.web.frontcontoller.v2.controller;

import basic.servlet.web.frontcontoller.v2.ControllerV2;
import basic.servlet.web.frontcontoller.MyView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MemberFormControllerV2 implements ControllerV2 {

    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        return new MyView("/WEB-INF/views/new-form.jsp");

    }
}
