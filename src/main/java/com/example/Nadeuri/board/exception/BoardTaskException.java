package com.example.Nadeuri.board.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class BoardTaskException extends RuntimeException{
    private String message;
    private int code;
}
