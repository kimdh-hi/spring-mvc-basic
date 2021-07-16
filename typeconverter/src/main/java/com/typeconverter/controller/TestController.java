package com.typeconverter.controller;

import com.typeconverter.type.IpPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @GetMapping("/test1")
    public String testV1(@RequestParam Integer data) {
        System.out.println("data = " + data);
        return "ok";
    }

    @GetMapping("/test2")
    public String StringToIp(@RequestParam IpPort data) {
        System.out.println("data = " + data);
        return "ok";
    }
}
