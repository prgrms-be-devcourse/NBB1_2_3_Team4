package com.example.Nadeuri.member.exception.advice

import com.example.Nadeuri.member.exception.MemberTaskException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.authorization.AuthorizationDeniedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class TokenControllerAdvice {

    @ExceptionHandler(AuthorizationDeniedException::class)
    fun handleException(e: AuthorizationDeniedException): ResponseEntity<Map<String, Any?>> {
        val errMap = mapOf("message" to e.message)
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errMap)
    }

    @ExceptionHandler(MemberTaskException::class)
    fun handleException(e: MemberTaskException): ResponseEntity<Map<String, Any>> {
        val errMap = mapOf("error" to e.message)
        return ResponseEntity.status(e.code).body(errMap)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleException(e: HttpMessageNotReadableException): ResponseEntity<Map<String, Any?>> {
        val errMap = mapOf("message" to e.message)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errMap)
    }
}