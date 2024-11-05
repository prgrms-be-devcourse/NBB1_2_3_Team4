package com.example.Nadeuri.member.config

import com.example.Nadeuri.member.security.filter.JWTCheckFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val jwtCheckFilter: JWTCheckFilter
) {

    @Bean
    fun configure(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web ->
            web.ignoring()
                .requestMatchers("/static/img/**", "/css/**", "/static/js/**")
        }
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf -> csrf.disable() } // CSRF 비활성화
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // OAuth2는 세션을 사용하지 않음
            }
            // 특정 API에 대한 인증 요구
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/api/token").permitAll() // 토큰 발급 요청은 모두 허용
                    .anyRequest().permitAll() // 그 외 요청은 모두 허용
            }
            // 인증 예외 처리
            .exceptionHandling { exception ->
                exception.defaultAuthenticationEntryPointFor(
                    HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                    AntPathRequestMatcher("/api/**")
                )
            }
            // JWTCheckFilter 필터 추가
            .addFilterBefore(jwtCheckFilter, UsernamePasswordAuthenticationFilter::class.java)
            // CORS 설정
            .cors { cors -> cors.configurationSource(corsConfigurationSource()) }

        return http.build()
    }

    // CORS 설정 관련 메서드
    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val corsConfig = CorsConfiguration()

        // 접근 패턴 - 모든 출처에서의 요청 허락
        corsConfig.allowedOriginPatterns = listOf("*")

        // 허용 메서드
        corsConfig.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")

        // 허용 헤더
        corsConfig.allowedHeaders = listOf("Authorization", "Content-Type", "Cache-Control")

        // 자격 증명 허용 여부
        corsConfig.allowCredentials = true

        // URL 패턴 기반으로 CORS 구성
        val corsSource = UrlBasedCorsConfigurationSource()
        corsSource.registerCorsConfiguration("/**", corsConfig) // 모든 경로 적용

        return corsSource
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}