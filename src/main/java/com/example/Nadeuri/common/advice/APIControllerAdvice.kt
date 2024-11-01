package com.example.Nadeuri.common.advice

import com.example.Nadeuri.board.kotlin.exception.BoardTaskException2
import com.example.Nadeuri.comment.exception.CommentTaskException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class APIControllerAdvice {

    @ExceptionHandler(BoardTaskException2::class, CommentTaskException::class)
    fun handleException(e: RuntimeException): ResponseEntity<Map<String, String?>> {
        val statusCode = when (e) {
//            is BoardTaskException -> e.code
            is CommentTaskException -> e.code
            else -> 500 // 기본적으로 내부 서버 오류 코드 설정
        }

        val errMap = mapOf("error" to e.message)
        return ResponseEntity.status(statusCode).body(errMap)
    }
}











