package com.example.Nadeuri.member.config;

import com.example.Nadeuri.member.MemberService;
import com.example.Nadeuri.member.RefreshTokenRepository;
import com.example.Nadeuri.member.security.filter.JWTCheckFilter;
import com.example.Nadeuri.member.security.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JWTUtil jwtUtil;

    private MemberService memberService;
    private JWTCheckFilter jwtCheckFilter;  // JWT 필터 추가

    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Autowired
    public void setJwtCheckFilter(JWTCheckFilter jwtCheckFilter) {
        this.jwtCheckFilter = jwtCheckFilter;
    }

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers("/static/img/**", "/css/**", "/static/js/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 기본 보안 설정
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // OAuth2는 세션을 사용하지 않음


                // 특정 API에 대한 인증 요구
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/token").permitAll() // 토큰 발급 요청은 모두 허용
                        .requestMatchers("/api/**").authenticated() // 나머지 API는 인증 필요
                        .requestMatchers("/auth/login").permitAll() // 로그인 페이지 접근 허용
                        .requestMatchers("/oauthlogin").permitAll() // /oauthlogin 경로 허용 추가
                        .requestMatchers("/error").permitAll() // /error 요청은 모두 허용
                        .anyRequest().permitAll()) // 그 외 요청은 모두 허용

                // 인증 예외 처리
                .exceptionHandling(exception -> exception
                        .defaultAuthenticationEntryPointFor(
                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                                new AntPathRequestMatcher("/api/**")))

                // JWTCheckFilter 필터 추가
                .addFilterBefore(jwtCheckFilter, UsernamePasswordAuthenticationFilter.class)

                // CORS 설정
                .cors(cors -> {
                    cors.configurationSource(corsConfigurationSource());
                });

        return http.build();
    }

    // CORS 설정 관련 메서드
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();

        // 접근 패턴 - 모든 출처에서의 요청 허락
        corsConfig.setAllowedOriginPatterns(List.of("*"));

        // 허용 메서드
        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));

        // 허용 헤더
        corsConfig.setAllowedHeaders(List.of("Authorization", "Content-Type", "Cache-Control"));

        // 자격 증명 허용 여부
        corsConfig.setAllowCredentials(true);

        // URL 패턴 기반으로 CORS 구성
        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**", corsConfig); // 모든 경로 적용

        return corsSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
