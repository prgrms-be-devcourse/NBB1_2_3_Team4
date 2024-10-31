package com.example.Nadeuri.member.exception.advice;
import com.example.Nadeuri.member.exception.MemberTaskException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class TokenControllerAdvice {
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<?> handleException(AuthorizationDeniedException e) {
        Map<String, Object> errMap = new HashMap<>();
        errMap.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(errMap);
    }

    @ExceptionHandler(MemberTaskException.class)
    public ResponseEntity<?> handleException(MemberTaskException e) {
        Map<String, Object> errMap = new HashMap<>();
        errMap.put("error", e.getMessage());

        return ResponseEntity.status(e.getCode())
                .body(errMap);
    }

    //요청바디의 role값에 enum값 이외의 값이 들어올 때 예외처리
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleException(HttpMessageNotReadableException e) {
        Map<String, Object> errMap = new HashMap<>();
        errMap.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errMap);
    }
}
