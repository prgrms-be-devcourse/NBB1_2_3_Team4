package com.example.Nadeuri.common.advice;


import com.example.Nadeuri.board.exception.BoardTaskException;
import com.example.Nadeuri.comment.exception.CommentTaskException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class APIControllerAdvice {
    @ExceptionHandler(BoardTaskException.class)
    public ResponseEntity<Map<String ,String>> handleException(BoardTaskException e){

        Map<String, String> errMap = Map.of("error", e.getMessage());

        return ResponseEntity.status(e.getCode()).body(errMap);
    }
    @ExceptionHandler(CommentTaskException.class)
    public ResponseEntity<Map<String ,String>> handleException(CommentTaskException e){

        Map<String, String> errMap = Map.of("error", e.getMessage());

        return ResponseEntity.status(e.getCode()).body(errMap);
    }
}











