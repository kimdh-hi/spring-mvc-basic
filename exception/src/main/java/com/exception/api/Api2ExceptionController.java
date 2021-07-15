package com.exception.api;

import com.exception.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ControllerAdvice에 예외처리
 */
@Slf4j
@RestController
public class Api2ExceptionController {
    @GetMapping("/api2/members/{memberId}")
    public String getMember(@PathVariable String memberId) {
        if (memberId.equals("runtime"))
            throw new RuntimeException("잘못된 사용자입니다.");
        if (memberId.equals("bad"))
            throw new IllegalArgumentException("잘못된 입력입니다.");
        if (memberId.equals("user-ex"))
            throw new UserException("사용자 오류입니다.");

        return "ok";
    }
}
