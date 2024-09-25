package com.example.Nadeuri.member.security.filter;


import com.example.Nadeuri.member.security.auth.CustomUserPrincipal;
import com.example.Nadeuri.member.security.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    @Override             //필터링 적용 X
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        log.info("--- shouldNotFilter()");
        log.info("--- requestURI : " + request.getRequestURI());

        if (request.getRequestURI().startsWith("/v1/token/")) {
            //토큰 발급 관련 경로는 제외
            return true;
        }

        if (request.getRequestURI().startsWith("/v1/members/sign-up")) {
            //토큰 발급 관련 경로는 제외
            return true;
        }
        if (request.getRequestURI().startsWith("/v1/members/login")) {
            //토큰 발급 관련 경로는 제외
            return true;
        }

        if(!request.getRequestURI().startsWith("/v1/")) {   //application.properties에서 static설정도 추가해줘야함
            return true;
        }

        return true;    //그 외 모든 경로는 필터링 false // true 모든 경로 허용
    }

    @Override             //필터링 적용 O - 액세스 토큰 확인
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("--- doFilterInternal() ");
        log.info("--- requestURI : " + request.getRequestURI());

        String headerAuth = request.getHeader("Authorization");
        log.info("--- headerAuth : " + headerAuth);

        //액세스 토큰이 없거나 'Bearer ' 가 아니면 403 예외 발생
        if (headerAuth == null || !headerAuth.startsWith("Bearer ")) {   //Bearer 옆에 공백 필수 !
            handleException(response, new Exception("ACCESS TOKEN NOT FOUND"));
            return;
        }

        //토큰 유효성 검증 -----------------------------------------------
        String accessToken = headerAuth.substring(7);  //"Bearer " 를 제외하고 토큰값 저장
        try {
            Map<String, Object> claims = jwtUtil.validateToken(accessToken);
            log.info("--- 토큰 유효성 검증 완료 ---");

            //SecurityContext 처리 ------------------------------------------
            String userId = claims.get("userId").toString();
            String[] roles = claims.get("role").toString().split(",");

            //토큰을 이용하여 인증된 정보 저장
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    new CustomUserPrincipal(userId), null, Arrays.stream(roles)
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList())
            );

            //SecurityContext에 인증/인가 정보 저장
            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(request, response); //검증 결과 문제가 없는 경우
        } catch (Exception e) {
            handleException(response, e);
        }
    }

    public void handleException(HttpServletResponse response, Exception e) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().println("{\"error\": \"" + e.getMessage() + "\"}");
    }

}
