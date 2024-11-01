package com.example.Nadeuri.member.security.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import lombok.extern.log4j.Log4j2
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.UnsupportedEncodingException
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.util.*
import javax.crypto.SecretKey

@Component
@Log4j2
class JWTUtil {

    @Value("\${jwt.secret.key}")
    private lateinit var key: String // 서명에 사용할 키 - 30자 이상으로

    fun createToken(valueMap: Map<String, Any>, min: Int): String {
        val secretKey = Keys.hmacShaKeyFor(key.toByteArray(StandardCharsets.UTF_8))

        val now = Date() // 토큰 발행 시간
        return Jwts.builder()
            .setHeaderParam("alg", "HS256") // HS256 알고리즘으로 헤더에 alg 필드로 추가
            .setHeaderParam("type", "JWT") // 헤더에 타입 필드를 추가하고 값으로 jwt 설정
            .setIssuedAt(now) // 토큰 발행 시간
            .setExpiration(Date(now.time + Duration.ofMinutes(min.toLong()).toMillis())) // 토큰 만료 시간
            .setClaims(valueMap) // 저장 데이터 (jwt 페이로드에 저장되는 사용자 정보와 같은 추가 데이터)
            .signWith(secretKey) // 서명(서명을 위한 비밀키를 key로 설정)
            .compact() // 헤더, 페이로드, 서명이 결합되어 jwt 문자열을 만듬
    }

    // 검증 기능 수행
    fun validateToken(token: String): Map<String, Any> {
        val secretKey = Keys.hmacShaKeyFor(key.toByteArray(StandardCharsets.UTF_8))

        val claims: Claims = Jwts.parser()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body

        return claims
    }
}