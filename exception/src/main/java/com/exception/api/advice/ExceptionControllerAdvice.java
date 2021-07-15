package com.exception.api.advice;

import com.exception.api.ErrorResult;
import com.exception.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 적용 범위 지정
 *
 * 미지정: 모든 컨트롤러의 예외
 * annotations: 특정 어노테이션에만 지정
 * 패키지 경로: 특정 패키지에만 지정
 * assignableTypes={..}: 특정 클래스와 해당 클래스의 자식 클래스에만 지정
 */
@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
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

}
