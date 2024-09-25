package com.example.Nadeuri.member.config;


import com.example.Nadeuri.member.security.filter.JWTCheckFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class CustomSecurityConfig {
    private JWTCheckFilter jwtCheckFilter;  //setter injection

    @Autowired
    public void setJwtCheckFilter(JWTCheckFilter jwtCheckFilter) {
        this.jwtCheckFilter = jwtCheckFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(login ->  login.disable())   //로그인폼 X
                .logout( logout -> logout.disable())    //로그아웃 X
                .csrf( csrf -> csrf.disable())          //CSRF는 세션단위 관리 -> X
                .sessionManagement( sess                //세션 사용 X
                        -> sess.sessionCreationPolicy(SessionCreationPolicy.NEVER));

        //JWTCheckFilter 필터 추가
        http.addFilterBefore(jwtCheckFilter,
                UsernamePasswordAuthenticationFilter.class);

        http.cors(cors -> {
            cors.configurationSource(corsConfigurationSource());
        });

        return http.build();
    }

    //CORS ; Cross Origin Resource Sharing설정 관련 처리 ---------------------------------------
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfig = new CorsConfiguration();

        //접근 패턴 - 모든 출처에서의 요청 허락
        corsConfig.setAllowedOriginPatterns(List.of("*"));

        //허용 메서드
        corsConfig.setAllowedMethods(
                List.of("GET", "POST", "PUT", "DELETE"));

        //허용 헤더
        corsConfig.setAllowedHeaders(
                List.of("Authorization",
                        "Content-Type",
                        "Cache-Control"));

        //자격 증명 허용 여부
        corsConfig.setAllowCredentials(true);

        //URL 패턴 기반으로 CORS 구성
        UrlBasedCorsConfigurationSource corsSource
                = new UrlBasedCorsConfigurationSource();

        corsSource.registerCorsConfiguration("/**",    //모든 경로 적용
                corsConfig);

        return corsSource;
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
