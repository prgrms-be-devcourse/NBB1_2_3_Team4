package com.example.Nadeuri.board.controllerAdvice;

import com.example.Nadeuri.board.BoardController;
import com.example.Nadeuri.board.exception.BoardTaskException;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@Log4j2
public class APIControllerAdvice  {

    @ExceptionHandler(BoardTaskException.class)
    public ResponseEntity<Map<String ,String >> handleException(BoardTaskException e){
        log.info("--- BoardTaskException");
        log.info("--- e.getClass().getName() : " + e.getClass().getName());
        log.info("--- e.getMessage() : " + e.getMessage());

        Map<String ,String > errMap =  Map.of("error",e.getMessage());

        return ResponseEntity.status(e.getCode()).body(errMap);
    }
}
