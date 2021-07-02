package basic.servlet.web.frontcontoller.v3.controller;

import basic.servlet.web.frontcontoller.ModelView;
import basic.servlet.web.frontcontoller.v3.ControllerV3;

import java.util.Map;

public class MemberFormControllerV3 implements ControllerV3 {

    @Override
    public ModelView process(Map<String, String> paramMap) {
        return new ModelView("new-form");
    }
}
