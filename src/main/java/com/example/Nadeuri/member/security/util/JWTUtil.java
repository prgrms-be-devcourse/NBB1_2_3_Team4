package com.example.Nadeuri.member.security.util;

import com.example.Nadeuri.member.MemberEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

@Component
@Log4j2
public class JWTUtil {
    private static String key//서명에 사용할 키 - 30자 이상으로
            = "12345678901234567890123456789012345678901234567890";
    //JWT 문자열 생성                              //저장 문자열, 만료 시간 - 분 단위


    public String createToken(Map<String, Object> valueMap,
                              int min) {
        SecretKey key = null;
        try {
            key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        //시크릿키 객체를 생성 후 UTF-8 인코딩으로 바이트로 변환

        Date now = new Date();   //토큰 발행 시간
        return Jwts.builder()
                .header().add("alg", "HS256")//HS256알고리즘으로 헤더에 alg필드로 추가
                .add("type", "JWT")//헤더에 타입 필드를 추가하고 값으로 jwt 설정
                .and()
                .issuedAt(now)       //토큰 발행 시간
                .expiration(         //토큰 만료 시간
                        new Date( now.getTime() + Duration.ofMinutes(min).toMillis()) )
                .claims(valueMap)   //저장 데이터 (jwt페이로드에 저장되는 사용자 정보와 같은 추가 데이터)
                .signWith(key)      //서명(서명을위한 비밀키를 key로 설정)
                .compact();
        //헤더,페이로드,서명이 결합되어 jwt문자열을 만듬
    }

    //검증 기능 수행
    public Map<String, Object> validateToken(String token) {
        SecretKey key = null;
        try {
            key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        Claims claims = Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token).getPayload();
        log.info("--- claim " + claims);

        return claims;
    }

}