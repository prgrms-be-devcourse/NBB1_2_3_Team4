package com.example.Nadeuri.comment.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentTaskException extends RuntimeException {
    private String message;
    private int code;
}
