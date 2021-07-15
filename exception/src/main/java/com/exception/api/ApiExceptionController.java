package com.exception.api;

import com.exception.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ApiExceptionController {

    /**
     * 이 컨트롤러에서 IllegalArgumentException 발생시 바로 이 메서드가 실행되는 것은 아님
     * 일단 예외는 DispatcherServlet까지 전파되고 DispatcherServlet에서 적합한 ExceptionResolver를 찾음
     * @ExceptionHandler가 붙어있는 경우 ExceptionHandlerExceptionResolver를 사용
     * ExceptionHandlerExceptionResolver를가 어노테이션을 분석하여 알맞는 메서드를 실행시켜 주는 것.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 따로 설정해주지 않으면 아래 메서드로 예외를 잡아 처리하여도 200 OK로 됨 (정상흐름이기 때문)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalArgumentExceptionHandler(IllegalArgumentException e) {
        log.info("[ExceptionHandler: IllegalArgumentException", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExceptionHandler(UserException e) {
        log.info("[ExceptionHandler: UserExceptionHandler", e);
        ErrorResult errorResult = new ErrorResult("USER-EXCEPTION", e.getMessage());
        return new ResponseEntity<ErrorResult>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exceptionHandler(Exception e) {
        log.info("[ExceptionHandler: Exception", e);
        ErrorResult errorResult = new ErrorResult("EX", e.getMessage());
        return errorResult;
    }

    @GetMapping("/api/members/{memberId}")
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
